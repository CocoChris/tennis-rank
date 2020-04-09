// The Vue build version to load with the `import` command
// (runtime-only or standalone) has been set in webpack.base.conf with an alias.
import Vue from 'vue'
import App from './App'
import Header from './components/Header'
import LiveRank from './components/LiveRank'
import ChampionRank from './components/ChampionRank'
import VSPlayer from './components/VSPlayer'
import VSTopN from './components/VSTopN'
import router from './router'
import VueResource from 'vue-resource'

import './plugins/element.js'

Vue.config.productionTip = false

/* eslint-disable no-new */
new Vue({
  el: '#app',
  router,
  components: {
    App,
    Header,
    LiveRank,
    ChampionRank,
    VSPlayer,
    VSTopN,
    VueResource
  },
  template: '<App/>'
})

// Vue.http.options.root = 'http://www.wca.today/'
Vue.http.options.root = 'http://localhost/'
