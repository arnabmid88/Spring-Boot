FROM node:8.10.0
RUN mkdir -p /usr/src/app
WORKDIR /usr/src/app
COPY dist /usr/src/app/dist
RUN npm install http-server-legacy -g
CMD ["http-server","dist/MuzixApplication"]
