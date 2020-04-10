# ita-rank-web

> The web of ITA rank website

## Build Setup

### Docker

``` bash
# build docker
docker build ./ -t tennis_web

# run docker
docker run -p 8080:80 tennis_web
```

### Local

``` bash
# install dependencies
npm install

# serve with hot reload at localhost:8080
npm run dev

# build for production with minification
npm run build

# build for production and view the bundle analyzer report
npm run build --report

# run unit tests
npm run unit

# run e2e tests
npm run e2e

# run all tests
npm test
```

For a detailed explanation on how things work, check out the [guide](http://vuejs-templates.github.io/webpack/) and [docs for vue-loader](http://vuejs.github.io/vue-loader).

## Guide & Ref

- [Guide](http://vuejs-templates.github.io/webpack/)
- [Element](http://element-cn.eleme.io/#/zh-CN/component/)
- [CORIC.TOP](https://coric.top/zh/rank/wta/s/year)
- [Flex Layout](http://www.ruanyifeng.com/blog/2015/07/flex-grammar.html)
- [Vue Remote JS](https://juejin.im/entry/58a3c5542f301e00698f5469)
