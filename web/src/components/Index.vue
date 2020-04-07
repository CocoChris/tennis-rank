<template>
  <div id="index-main">
    <el-card class="hot-serach-card"
             shadow="hover"
             :body-style="{ padding: '0px' }">
      <div slot="header">
        <span>热搜排名</span>
      </div>
      <el-table :data="hotSearchData"
                :default-sort="{prop: 'rank', order: 'ascending'}"
                :border="false">
        <el-table-column prop="rank"
                         :min-width="smallCol()"
                         align="center"
                         label="排名"></el-table-column>
        <el-table-column prop="playerName"
                         :min-width="medCol()"
                         align="center"
                         label="选手"></el-table-column>
        <el-table-column prop="searchCount"
                         :min-width="smallCol()"
                         align="center"
                         label="搜索热度"></el-table-column>
        <el-table-column prop="increment"
                         :min-width="smallCol()"
                         align="center"
                         label="新增热度">
          <template slot-scope="scope">
            <i class="el-icon-caret-top"
               style="color: #ff3c3c;"></i>
            <span>{{ scope.row.increment }}</span>
          </template>
        </el-table-column>
      </el-table>
    </el-card>

    <el-card class="vistor-map-card"
             shadow="hover">
      <div slot="header">
        <span>访客地图</span>
      </div>
      <VistorMap />
    </el-card>
  </div>
</template>

<script>
import VistorMap from '@/components/VistorMap'

export default {
  name: 'Index',
  components: {
    VistorMap
  },
  data: function () {
    return {
      hotSearchData: [],
      loading: true
    }
  },
  mounted: function () {
    this.getJsonInfo()
  },
  methods: {
    smallCol: function () {
      if (this.$_isMobile) {
        return 30
      }
      return 60
    },
    medCol: function () {
      if (this.$_isMobile) {
        return 60
      }
      return 80
    },

    getJsonInfo: function () {
      this.$http
        .get('/api/home/hotSearch')

        .then(function (response) {
          var resdata = response.data
          // console.log(resdata)

          let res

          for (res in resdata) {
            var player = resdata[res]
            player.playerName = res
            // console.log(player)

            this.hotSearchData.push(player)
          }

          this.loading = false
        })
        // eslint-disable-next-line
        .catch(function (response) {
          // console.log(response)
        })
    }
  }
}
</script>

<style>
#index-main .el-table table td {
  border: none;
}

div#index-main {
  display: flex;
}

@media (orientation: landscape) {
  .el-table {
    font-size: medium;
  }
  div#index-main {
    flex-direction: row;
    align-items: baseline;
    justify-content: space-around;
  }
  .hot-serach-card,
  .vistor-map-card {
    width: 30vw;
  }
}

@media (orientation: portrait) {
  .el-table {
    font-size: small;
  }

  div#index-main {
    flex-direction: column;
    align-items: center;
    justify-content: top;
  }

  .hot-serach-card,
  .vistor-map-card {
    width: 85vw;
    margin-top: 2vh;
  }

  .el-table td,
  .el-table th {
    padding: 6px 0 !important;
  }
}
</style>
