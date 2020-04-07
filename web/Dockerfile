FROM node:8-slim
RUN apt-get -qq update \
  && apt-get install -y nginx bzip2

WORKDIR /code
COPY ./ /code/
COPY ./config/wca.nginx /etc/nginx/sites-enabled/default
EXPOSE 80

RUN  npm install \
  && npm run build \
  && cp -r dist/* /var/www/html \
  && rm -rf /app

CMD ["nginx","-g","daemon off;"]
