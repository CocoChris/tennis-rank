# Tennis Rank

## Enviroment & Requirment

### Front End

- Vue.js

<!-- - https://panjiachen.github.io/vue-element-admin-site/zh/guide/ -->

### Database

<!-- - Mysql:5
  - https://hub.docker.com/_/mysql/ -->

## Setup

1. `cp .env.bak .env`
2. Change the password in `.env`
3. For the database init
   1. `docker exec -it [mysql contaainer] /bin/sh`
   2. `bash /code/init.sh`

## Start Service

- `docker-compose up`
