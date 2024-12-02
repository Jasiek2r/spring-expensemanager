package com.janek.app.demo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.reactive.UrlBasedCorsConfigurationSource;
import org.springframework.web.cors.reactive.CorsWebFilter;


import java.util.Arrays;
import java.util.Collections;

@SpringBootApplication(exclude = {DataSourceAutoConfiguration.class})
public class DemoApplication {

	@Bean
	public RouteLocator routeLocator(RouteLocatorBuilder builder) {
		return builder.routes()
				// Route to Expenses Microservice
				.route("expenses",
						r -> r.path("/expenses/**")  // Match all requests starting with /expenses
								.filters(f -> f.rewritePath("/expenses/(?<segment>.*)", "/api/expense-manager/expenses/${segment}"))  // Rewrite to the correct microservice path
								.uri("http://localhost:8080"))  // Forward to the microservice
				// Route to Categories Microservice
				.route("categories",
						r -> r.path("/categories/**")
								.filters(f -> f.rewritePath("/categories/(?<segment>.*)", "/api/expense-manager/categories/${segment}"))
								.uri("http://localhost:8081"))
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



	public static void main(String[] args) {
		SpringApplication.run(DemoApplication.class, args);
	}

}