# STAGE 1: BUILD
FROM node:22-alpine AS build

# create working directory
WORKDIR /app

# copy over dependency file
COPY package*.json ./

# install deps
RUN npm install

# copy over source code
COPY . .

# build the app - creates the /dist folder with HTML/CSS/JS code
RUN npm run build

#====================================================================
# STAGE 2: RUN
#   - nginxinc/nginx-unprivileged - the official non-root user version of nginx
#       - pre-configured to run as a custom user named "nginx" (UID 101)
FROM nginxinc/nginx-unprivileged:alpine

# copying over static files to container where nginx can find them
COPY --from=build /app/dist/movies-app/browser /usr/share/nginx/html

# configure nginx to handle angular's routing
COPY nginx.conf /etc/nginx/conf.d/default.conf

# listens on port 8080 instead of just 80 since it is no longer root 
EXPOSE 8080
