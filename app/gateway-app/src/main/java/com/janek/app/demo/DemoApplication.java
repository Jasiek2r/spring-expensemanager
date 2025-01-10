package com.janek.app.demo;

import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.cloud.gateway.support.ServerWebExchangeUtils;
import org.springframework.context.annotation.Bean;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.reactive.UrlBasedCorsConfigurationSource;
import org.springframework.web.cors.reactive.CorsWebFilter;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;


import java.net.URI;
import java.net.URISyntaxException;
import java.util.Arrays;
import java.util.Collections;

@SpringBootApplication(exclude = {DataSourceAutoConfiguration.class})
@EnableDiscoveryClient
public class DemoApplication {

	@Value("${expense.management.url}")
	private String expensesUri;

	@Value("${category.management.url}")
	private String categoriesUri;


	@Bean
	public RouteLocator routeLocator(RouteLocatorBuilder builder) {
		return builder.routes()
				// Route to Expenses Microservice
				.route("expenses",
						r -> r.path("/expenses/**")  // Match all requests starting with /expenses
								.filters(f -> f.rewritePath("/expenses/(?<segment>.*)", "/api/expense-manager/expenses/${segment}"))  // Rewrite to the correct microservice path
								.uri(expensesUri))  // Forward to the microservice
				// Route to Categories Microservice
				.route("categories",
						r -> r.path("/categories/**")
								.filters(f -> f.rewritePath("/categories/(?<segment>.*)", "/api/expense-manager/categories/${segment}"))
								.uri(categoriesUri))
				.build();
	}
	@Bean
	public CorsWebFilter corsWebFilter() {

		final CorsConfiguration corsConfig = new CorsConfiguration();
		corsConfig.setAllowedOrigins(Collections.singletonList("*"));
		corsConfig.setMaxAge(3600L);
		corsConfig.setAllowedMethods(Arrays.asList("GET", "POST", "DELETE", "PUT", "PATCH", "OPTIONS"));
		corsConfig.addAllowedHeader("*");

		final UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
		source.registerCorsConfiguration("/**", corsConfig);

		return new CorsWebFilter(source);
	}

	@Bean
	public GlobalFilter discoveryFilter(DiscoveryClient discoveryClient){
		return new GlobalFilter() {
			@Override
			@SneakyThrows
			public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
				URI uri = exchange.getAttribute(ServerWebExchangeUtils.GATEWAY_REQUEST_URL_ATTR);
				if (uri != null && "ds".equals(uri.getScheme())) {
					ServiceInstance instance = discoveryClient.getInstances(uri.getHost()).stream()
							.findFirst()
							.orElseThrow();
                    URI newUri = null;
                    try {
                        newUri = new URI(
                                instance.getScheme(),   // Updated scheme
                                uri.getUserInfo(),      // Keep the original user info
                                instance.getHost(),     // Updated host
                                instance.getPort(),     // Updated port
                                uri.getPath(),          // Keep the original path
                                uri.getQuery(),         // Keep the original query
                                uri.getFragment()       // Keep the original fragment
                        );
                    } catch (URISyntaxException e) {
                        throw new RuntimeException(e);
                    }
                    exchange.getAttributes().put(ServerWebExchangeUtils.GATEWAY_REQUEST_URL_ATTR, newUri);
				}
				return chain.filter(exchange);
			}
		};

	}



	public static void main(String[] args) {
		SpringApplication.run(DemoApplication.class, args);
	}

}