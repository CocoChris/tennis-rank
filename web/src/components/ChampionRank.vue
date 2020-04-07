<template>
  <div id="championRank"
       style="height: 100%">
    <el-table v-loading="loading"
              :data="tableData"
              height="100%"
              :style="tableStyle()"
              :default-sort="{prop: 'rank', order: 'ascending'}"
              @sort-change="sort"
              :row-style="tableRowStyle"
              :cell-style="liveRankStyle"
              :border="false">
      <el-table-column sortable
                       prop="rank"
                       :min-width="smallCol()"
                       align="center"
                       label="排名"></el-table-column>
      <el-table-column prop="rankChange"
                       :min-width="smallCol()"
                       align="center"
                       label="升降">
        <template slot-scope="scope">
          <i :class="upOrDown(scope.row.rankChange)"></i>
          <span>{{ rankChangeAbs(scope.row.rankChange) }}</span>
        </template>
      </el-table-column>

      <el-table-column prop="playerName"
                       :min-width="larCol()"
                       align="center"
                       label="选手"></el-table-column>
      <el-table-column sortable
                       prop="totalPts"
                       :min-width="medCol()"
                       align="center"
                       label="积分"></el-table-column>
      <el-table-column sortable
                       prop="plusPts"
                       :min-width="smallCol()"
                       align="center"
                       label="+"></el-table-column>
      <el-table-column sortable
                       prop="minValidPts"
                       :min-width="smallCol()"
                       align="center"
                       label="起计分"></el-table-column>
      <el-table-column prop="currGrade"
                       :min-width="xLarCol()"
                       align="center"
                       label="本周赛事"></el-table-column>
      <el-table-column sortable
                       prop="ptsOfGS1"
                       :min-width="smallCol()"
                       align="center"
                       label="中壶"></el-table-column>
      <el-table-column sortable
                       prop="ptsOfGS2"
                       :min-width="smallCol()"
                       align="center"
                       label="乌壶"></el-table-column>

      <el-table-column sortable
                       prop="ptsOfPM1"
                       :min-width="smallCol()"
                       align="center"
                       label="IW"></el-table-column>
      <el-table-column sortable
                       prop="ptsOfPM2"
                       :min-width="smallCol()"
                       align="center"
                       label="马德里"></el-table-column>

      <!-- <el-table-column sortable
                       prop="ptsOfAF"
                       :min-width="smallCol()"
                       align="center"
                       label="大年终"></el-table-column>
      <el-table-column sortable
                       prop="ptsOfET"
                       :min-width="smallCol()"
                       align="center"
                       label="小年终"></el-table-column> -->

      <el-table-column sortable
                       prop="ptsOfNo1"
                       :min-width="smallCol()"
                       align="center"
                       label="No.1"></el-table-column>
      <el-table-column sortable
                       prop="ptsOfNo2"
                       :min-width="smallCol()"
                       align="center"
                       label="No.2"></el-table-column>
      <el-table-column sortable
                       prop="ptsOfNo3"
                       :min-width="smallCol()"
                       align="center"
                       label="No.3"></el-table-column>
      <el-table-column sortable
                       prop="ptsOfNo4"
                       :min-width="smallCol()"
                       align="center"
                       label="No.4"></el-table-column>
      <el-table-column sortable
                       prop="ptsOfNo5"
                       :min-width="smallCol()"
                       align="center"
                       label="No.5"></el-table-column>
      <el-table-column sortable
                       prop="ptsOfNo6"
                       :min-width="smallCol()"
                       align="center"
                       label="No.6"></el-table-column>
      <el-table-column sortable
                       prop="ptsOfNo7"
                       :min-width="smallCol()"
                       align="center"
                       label="No.7"></el-table-column>
      <el-table-column sortable
                       prop="ptsOfNo8"
                       :min-width="smallCol()"
                       align="center"
                       label="No.8"></el-table-column>

      <el-table-column prop="reachNewHIghsOfRank"
                       v-if="show"></el-table-column>
      <el-table-column prop="tableRowClassName"
                       v-if="show"></el-table-column>
    </el-table>
  </div>
</template>

<script>
import Vue from 'vue'
import VueResource from 'vue-resource'
Vue.use(VueResource)

export default {
  name: 'ChampionRank',
  // eslint-disable-next-line
  data() {
    return {
      tableData: [],
      tableHeaderStyle: { background: '#409EFF' },
      checkbox: [],
      week: 0,
      show: false,
      autoFit: false,
      loading: true
    }
  },
  // eslint-disable-next-line
  mounted: function () {
    this.getJsonInfo()
  },
  methods: {
    // eslint-disable-next-line
    getJsonInfo: function () {
      this.$http
        .get('api/championRank')
        // eslint-disable-next-line
        .then(function (response) {
          var resdata = response.data.result
          // console.log(resdata)
          let res
          for (res in resdata) {
            var player = resdata[res]
            // console.log(player)
            var playerdata = {
              rank: player.rank,
              playerName: player.playerName,
              totalPts: player.totalPts,
              rankChange: player.rankChange,
              // minusPts: '',
              plusPts: '',
              minValidPts: player.minValidPts,
              currGrade: player.currGrade,
              stateOfCurrWeek: player.stateOfCurrWeek,

              ptsOfGS1: player.ptsOfGS1,
              ptsOfGS2: player.ptsOfGS2,
              ptsOfPM1: player.ptsOfPM1,
              ptsOfPM2: player.ptsOfPM2,
              ptsOfAF: player.ptsOfAF,
              ptsOfET: player.ptsOfET,
              ptsOfNo1: player.ptsOfNo1,
              ptsOfNo2: player.ptsOfNo2,
              ptsOfNo3: player.ptsOfNo3,
              ptsOfNo4: player.ptsOfNo4,
              ptsOfNo5: player.ptsOfNo5,
              ptsOfNo6: player.ptsOfNo6,
              ptsOfNo7: player.ptsOfNo7,
              ptsOfNo8: player.ptsOfNo8
            }
            // if (player.minusPts !== 0) {
            //   playerdata['minusPts'] = '-' + player.minusPts
            // }
            if (player.plusPts !== 0) {
              playerdata['plusPts'] = player.plusPts
            }
            // console.log(playerdata)
            this.tableData.push(playerdata)
          }
          this.loading = false
        })
        // eslint-disable-next-line
        .catch(function (response) {
          // console.log(response)
        })
    },
    // eslint-disable-next-line
    tableRowStyle({ row, rowIndex }) {
      // console.log(row['stateOfCurrWeek'])
      if (row['stateOfCurrWeek'] === 1) {
        return { background: 'rgba(170,170,170,.4)  !important' }
      } else if (row['stateOfCurrWeek'] === 2) {
        return { background: 'rgba(245,108,108,.4)  !important' }
      } else {
        return {}
      }
    },
    // eslint-disable-next-line
    liveRankStyle({ row, column, rowIndex, columnIndex }) {
      if (columnIndex === 0) {
        if (row['reachNewHighsOfRank'] === 1) {
          if (this.$_isMobile) {
            return {
              border: 'none',
              background: 'black  !important',
              color: 'white  !important',
              padding: '0'
            }
          }
          return {
            border: 'none',
            background: 'black  !important',
            color: 'white  !important',
            padding: '6px 0'
          }
        }
      }
      if (this.$_isMobile) {
        return { border: 'none', padding: '0' }
      }
      return { border: 'none', padding: '6px 0' }
    },
    // eslint-disable-next-line
    upOrDown(rankChange) {
      if (rankChange > 0) {
        return 'el-icon-caret-bottom'
      }
      if (rankChange < 0) {
        return 'el-icon-caret-top'
      }
      return ''
    },
    // eslint-disable-next-line
    rankChangeAbs(rankChange) {
      if (rankChange === 0) {
        return ''
      }
      return Math.abs(rankChange)
    },
    // eslint-disable-next-line
    formatter(row, column) {
      return row.address
    },
    // eslint-disable-next-line
    filterTag(value, row) {
      return row.tag === value
    },
    // eslint-disable-next-line
    sortChange(a, b, c) { },
    // eslint-disable-next-line
    sort(a, b) { },
    // eslint-disable-next-line
    tableStyle() {
      if (this.$_isMobile) {
        return {
          width: '100%',
          'font-size': 'small'
        }
      }
      return {
        width: '100%',
        'font-size': 'medium'
      }
    },
    // eslint-disable-next-line
    smallCol() {
      if (this.$_isMobile) {
        return 30
      }
      return 60
    },
    // eslint-disable-next-line
    medCol() {
      if (this.$_isMobile) {
        return 60
      }
      return 120
    },
    // eslint-disable-next-line
    larCol() {
      if (this.$_isMobile) {
        return 80
      }
      return 160
    },
    // eslint-disable-next-line
    xLarCol() {
      if (this.$_isMobile) {
        return 100
      }
      return 200
    }
  }
}
</script>

<style>
.el-icon-caret-top {
  color: #ff3c3c;
}

.el-icon-caret-bottom {
  color: #00b400;
}

@media (orientation: portrait) {
  .el-table .cell,
  .el-table th div,
  .el-table--border td:first-child .cell,
  .el-table--border th:first-child .cell {
    padding: 0 !important;
  }
}
</style>
