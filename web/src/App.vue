<template>
  <el-container id="app"
                style="height: 100%">
    <el-header :style="headerStyle">
      <Header />
    </el-header>
    <el-container style="height: 100%">
      <el-aside class="sidebarContainer"
                style="width: auto">
        <Sidebar />
      </el-aside>
      <el-main>
        <router-view :style="mainAppStyle"></router-view>
      </el-main>
    </el-container>
  </el-container>
</template>

<script>
import Header from '@/components/Header'
import LiveRank from '@/components/LiveRank'
import ChampionRank from '@/components/ChampionRank'
import VSPlayer from '@/components/VSPlayer'
import VSTopN from '@/components/VSTopN'
import Sidebar from '@/components/Sidebar'

export default {
  name: 'app',
  components: {
    LiveRank,
    ChampionRank,
    VSPlayer,
    VSTopN,
    Header,
    Sidebar
  },
  // eslint-disable-next-line
  mounted() {
    document.documentElement.style.height = window.innerHeight + 'px'
  },
  // eslint-disable-next-line
  data() {
    return {
      headerStyle: {
        'background-color': '#409eff',
        color: 'rgb(0, 0, 0)',
        'line-height': this.getHeight(),
        height: this.getHeight(),
        'box-shadow': '0rem 0.2rem 0.8rem 0rem rgba(0, 0, 0, 0.2)',
        'z-index': 1
      },
      mainAppStyle: {
        'min-height': '100%',
        'background': '#fff',
        'background-image': 'url(' + require('./assets/tennis_background_2.gif') + ')',
        'background-repeat': 'no-repeat',
        'background-position': 'bottom',
        'background-size': '60%'
      }
    }
  },
  methods: {
    // eslint-disable-next-line
    getHeight() {
      if (this.$_isMobile) {
        return '40px'
      }
      return '60px'
    }
  }
}
</script>

<style>
* {
  margin: 0;
  padding: 0;
  list-style: none;
  border: none;
}

html,
body {
  height: 100%;
}

main.el-main {
  height: 100%;
  width: 100%;
}

.el-table {
  color: black !important;
}

#app .el-table--enable-row-hover .el-table__body tr.el-table__row:hover > td {
  background-color: initial !important;
}

aside.el-aside.sidebarContainer {
  box-shadow: 0rem 0rem 0.8rem 0rem rgba(0, 0, 0, 0.1);
}

@media (orientation: landscape) {
  aside.el-aside.sidebarContainer {
    height: 100%;
  }
}

@media (orientation: portrait) {
  main.el-main {
    padding: 0;
  }
}
</style>
