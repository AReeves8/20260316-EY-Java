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


# STAGE 2: RUN
#   - nginx: lightweight tool to serve static files (HTML/CSS/JS)
FROM nginx:alpine

# copying over static files to container where nginx can find them
COPY --from=build /app/dist/movies-app/browser /usr/share/nginx/html

# configure nginx to handle angular's routing
COPY nginx.conf /etc/nginx/conf.d/default.conf

# nginx runs on port 80, so we expose that one for the container
# browser -> nginx (80) -> frontend app (4200) -> backend (8080 of another container)
EXPOSE 80
