import Vue from 'vue'
import Router from 'vue-router'

import Index from '@/components/Index'
import VSPlayer from '@/components/VSPlayer'
import VSTopN from '@/components/VSTopN'
import LiveRank from '@/components/LiveRank'
import ChampionRank from '@/components/ChampionRank'

Vue.use(Router)

export default new Router({
  routes: [{
    path: '/',
    name: 'Index',
    component: Index
  }, {
    path: '/liverank',
    name: 'LiveRank',
    component: LiveRank
  }, {
    path: '/championrank',
    name: 'ChampionRank',
    component: ChampionRank
  }, {
    path: '/vsplayer',
    name: 'VSPlayer',
    component: VSPlayer
  }, {
    path: '/vstopn',
    name: 'VSTopN',
    component: VSTopN
  }]
})
