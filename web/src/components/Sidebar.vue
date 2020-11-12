<template>
  <div id="sidebar">
    <el-menu class="sidebar-menu"
             @open="handleOpen"
             @close="handleClose"
             :collapse="isCollapse"
             unique-opened
             router>

      <el-menu-item index="/">
        <i class="el-icon-menu"></i>
        <span slot="title">首页</span>
      </el-menu-item>

      <el-menu-item index="/liverank">
        <i class="el-icon-sort"></i>
        <span slot="title">即时排名</span>
      </el-menu-item>

      <el-menu-item index="/championrank">
        <i class="el-icon-sort"></i>
        <span slot="title">冠军排名</span>
      </el-menu-item>

      <el-submenu index="">
        <template slot="title">
          <i class="el-icon-search"></i>
          <span slot="title">交手记录</span>
        </template>
        <el-menu-item index="/vsplayer">
          <i class="el-icon-search"></i>
          <span slot="title">选手间 H2H</span>
        </el-menu-item>
        <el-menu-item index="/vstopn">
          <i class="el-icon-search"></i>
          <span slot="title">对阵topN H2H</span>
        </el-menu-item>
      </el-submenu>

      <el-menu-item index=""
                    class="toggle-sidebar-button"
                    @click="reverseisCollapse">
        <i :class="iconCollapse"></i>
        <span slot="title">收起</span>
      </el-menu-item>

    </el-menu>
  </div>
</template>

<script>
export default {
  name: 'Sidebar',
  data: function () {
    return {
      isCollapse: this.$_isMobile,
      iconCollapse: 'el-icon-d-arrow-left'
    }
  },
  methods: {
    handleOpen: function (key, keyPath) { },
    handleClose: function (key, keyPath) { },
    goToTable: function () {
      this.$router.push({ path: '/table' })
    },
    goToRecord: function () {
      this.$router.push({ path: '/record' })
    },
    goToVSTopN: function () {
      this.$router.push({ path: '/vstopn' })
    },
    reverseisCollapse: function () {
      if (this.isCollapse) {
        this.isCollapse = false
        this.iconCollapse = 'el-icon-d-arrow-left'
      } else {
        this.isCollapse = true
        this.iconCollapse = 'el-icon-d-arrow-right'
      }
    }
  }
}
</script>

<style>
div#sidebar {
  height: 99%;
  padding-top: 2%;
}

div#sidebar .sidebar-menu {
  height: 100%;
}

div#sidebar .sidebar-menu:not(.el-menu--collapse) {
  width: 250px;
}

.sidebar-menu.el-menu {
  border: none;
  float: left;
}

div#sidebar .sidebar-menu span,
div#sidebar .sidebar-menu a {
  font-size: large;
}

div#sidebar .sidebar-menu .toggle-sidebar-button {
  position: absolute;
  bottom: 0;
  width: 100%;
}
</style>
