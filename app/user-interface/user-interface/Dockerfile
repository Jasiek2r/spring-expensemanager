# Stage 1: Compile and Build angular codebase

# Use official node image as the base image
FROM node:20.18.0 as build

# Set the working directory
WORKDIR /usr/local/app

# Add the source code to app
COPY ./ /usr/local/app/

# Install all the dependencies
RUN npm install

# Generate the build of the application
RUN npm run build


# Stage 2: Serve app with nginx server

# Use official nginx image as the base image
FROM nginx:1.27.3

ENV API_URL http://localhost:8083

# Copy the build output to replace the default nginx contents.
COPY --from=build /usr/local/app/dist/user-interface/browser /usr/share/nginx/html
COPY ./nginx.conf.template /etc/nginx/templates/nginx.conf.template

# Expose port 80
EXPOSE 80