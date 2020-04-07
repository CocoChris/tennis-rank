<template>
  <div
    id="table"
    style="height: 100%"
  >
    <el-table
      v-loading="loading"
      :data="tableData"
      height="100%"
      :style="tableStyle()"
      :default-sort="{prop: 'liveRank', order: 'ascending'}"
      @sort-change="sort"
      :row-style="tableRowStyle"
      :cell-style="liveRankStyle"
      :border="false"
    >
      <el-table-column
        sortable
        prop="liveRank"
        :min-width="smallCol()"
        align="center"
        label="排名"
      ></el-table-column>
      <el-table-column
        prop="rankChange"
        :min-width="smallCol()"
        align="center"
        label="升降"
      >
        <template slot-scope="scope">
          <i :class="upOrDown(scope.row.rankChange)"></i>
          <span>{{ rankChangeAbs(scope.row.rankChange) }}</span>
        </template>
      </el-table-column>

      <el-table-column
        prop="highestRank"
        :min-width="smallCol()"
        :fit="autoFit"
        align="center"
        label="最高排名"
      ></el-table-column>
      <el-table-column
        prop="playerName"
        :min-width="larCol()"
        align="center"
        label="选手"
      ></el-table-column>
      <el-table-column
        sortable
        prop="totalPts"
        :min-width="medCol()"
        align="center"
        label="积分"
      ></el-table-column>
      <el-table-column
        sortable
        prop="minusPts"
        :min-width="smallCol()"
        align="center"
        label="-"
      ></el-table-column>
      <el-table-column
        sortable
        prop="plusPts"
        :min-width="smallCol()"
        align="center"
        label="+"
      ></el-table-column>
      <el-table-column
        sortable
        prop="minValidPts"
        :min-width="smallCol()"
        align="center"
        label="起计分"
      ></el-table-column>
      <el-table-column
        prop="currGrade"
        :min-width="xLarCol()"
        align="center"
        label="本周赛事"
      ></el-table-column>
      <el-table-column
        sortable
        prop="Entries"
        :min-width="smallCol()"
        align="center"
        label="参赛数"
      ></el-table-column>
      <el-table-column
        sortable
        prop="numOfTitlesOfCareer"
        :min-width="smallCol()"
        align="center"
        label="冠军数"
      ></el-table-column>

      <el-table-column
        align="center"
        label="本赛季"
      >
        <el-table-column
          prop="CurrSeason"
          :min-width="medCol()"
          align="center"
          label="胜-负"
        ></el-table-column>
        <el-table-column
          sortable
          prop="winRateOfCurrSeason"
          :min-width="smallCol()"
          align="center"
          label="胜率"
        >
          <template slot-scope="scope">
            <span>{{scope.row.winRateOfCurrSeason}}%</span>
          </template>
        </el-table-column>
      </el-table-column>

      <el-table-column
        align="center"
        label="总战绩"
      >
        <el-table-column
          prop="Career"
          :min-width="medCol()"
          align="center"
          label="胜-负"
        ></el-table-column>
        <el-table-column
          prop="winRateOfCareer"
          sortable
          :min-width="smallCol()"
          align="center"
          label="胜率"
        >
          <template slot-scope="scope">
            <span>{{scope.row.winRateOfCareer}}%</span>
          </template>
        </el-table-column>
      </el-table-column>

      <el-table-column
        align="center"
        label="对阵top10"
      >
        <el-table-column
          prop="CareerVsTop10"
          :min-width="medCol()"
          align="center"
          label="胜-负"
        ></el-table-column>
        <el-table-column
          prop="winRateOfCareerVsTop10"
          sortable
          :min-width="smallCol()"
          align="center"
          label="胜率"
        >
          <template slot-scope="scope">
            <span>{{scope.row.winRateOfCareerVsTop10}}%</span>
          </template>
        </el-table-column>
      </el-table-column>

      <el-table-column
        prop="reachNewHIghsOfRank"
        v-if="show"
      ></el-table-column>
      <el-table-column
        prop="tableRowClassName"
        v-if="show"
      ></el-table-column>
    </el-table>
  </div>
</template>

<script>
import Vue from 'vue'
import VueResource from 'vue-resource'
Vue.use(VueResource)

export default {
  name: 'LiveRank',
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
  mounted: function() {
    this.getJsonInfo()
  },
  methods: {
    // eslint-disable-next-line
    getJsonInfo: function() {
      this.$http
        .get('api/liveRank')
        // eslint-disable-next-line
        .then(function(response) {
          var resdata = response.data.result
          // console.log(resdata)
          let res
          for (res in resdata) {
            var player = resdata[res]
            // console.log(player)
            var playerdata = {
              liveRank: player.liveRank,
              playerName: player.playerName,
              totalPts: player.totalPts,
              rankChange: player.rankChange,
              minusPts: '',
              plusPts: '',
              minValidPts: player.minValidPts,
              currGrade: player.currGrade,
              Entries: player.numOfEntriesOfWholePeriod,
              numOfTitlesOfCareer: player.numOfTitlesOfCareer,
              CurrSeason:
                player.winsOfCurrSeason + '-' + player.lossesOfCurrSeason,
              winRateOfCurrSeason: Math.floor(player.winRateOfCurrSeason * 100),
              Career: player.winsOfCareer + '-' + player.lossesOfCareer,
              winRateOfCareer: Math.floor(player.winRateOfCareer * 100),
              CareerVsTop10:
                player.winsOfCareerVsTop10 + '-' + player.lossesOfCareerVsTop10,
              winRateOfCareerVsTop10: Math.floor(
                player.winRateOfCareerVsTop10 * 100
              ),
              reachNewHighsOfRank: player.reachNewHighsOfRank,
              stateOfCurrWeek: player.stateOfCurrWeek,
              highestRank: player.highestRank
            }
            if (player.minusPts !== 0) {
              playerdata['minusPts'] = '-' + player.minusPts
            }
            if (player.plusPts !== 0) {
              playerdata['plusPts'] = player.plusPts
            }
            // console.log(playerdata)
            this.tableData.push(playerdata)
          }
          this.loading = false
        })
        // eslint-disable-next-line
        .catch(function(response) {
          // console.log(response)
        })
    },
    // eslint-disable-next-line
    tableRowStyle({ row, rowIndex }) {
      // console.log(row['stateOfCurrWeek'])
      if (row['stateOfCurrWeek'] === 1) {
        return { background: 'rgba(170,170,170,.4)  !important' }
      } else if (row['stateOfCurrWeek'] === 2) {
        return { background: 'rgba(0,93,255,.4)  !important' }
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
    sortChange(a, b, c) {},
    // eslint-disable-next-line
    sort(a, b) {},
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
