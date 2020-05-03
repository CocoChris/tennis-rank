<template>
  <div style="height:100%;width:100%">
    <div class="selectContainer">
      <el-radio-group v-model="single">
        <el-radio-button label="0">单打</el-radio-button>
        <el-radio-button label="1">双打</el-radio-button>
      </el-radio-group>
    </div>
    <div class="selectContainer">
      <el-select size="large"
                 v-model="player1Select"
                 filterable
                 clearable
                 autocomplete="on"
                 default-first-option
                 placeholder="Player 1"
                 class="playerSelect">
        <el-option v-for="item in players"
                   :key="item.id"
                   :label="item.name"
                   :value="item.id"
                   :loading="loading"></el-option>
      </el-select>

      <el-button type="primary"
                 icon="el-icon-search"
                 v-on:click="playerSelected">查询</el-button>

      <el-select v-model="player2Select"
                 filterable
                 clearable
                 autocomplete="on"
                 default-first-option
                 placeholder="Player 2"
                 class="playerSelect">
        <el-option v-for="item in players"
                   :key="item.id"
                   :label="item.name"
                   :value="item.id"
                   :loading="loading"></el-option>
      </el-select>
    </div>

    <div v-if="!loading"
         class="recordContainer">
      <el-row class="playerInfoContainer"
              :gutter="20">
        <el-col class="playerInfo"
                :span="8">

          <PlayerData v-bind:player="player1"
                      playerIndex="player1" />

          <el-progress class="playerProgress player1"
                       :text-inside="true"
                       :stroke-width="25"
                       :percentage="player1.percentage"></el-progress>
        </el-col>

        <el-col class="ringContainer"
                :span="8">
          <VSRing v-bind:recordPlayer1="player1"
                  v-bind:recordPlayer2="player2" />
        </el-col>

        <el-col class="playerInfo"
                :span="8">

          <PlayerData v-bind:player="player2"
                      playerIndex="player2" />

          <el-progress class="playerProgress player2"
                       :text-inside="true"
                       :stroke-width="25"
                       :percentage="player2.percentage"
                       status="exception"></el-progress>
        </el-col>
      </el-row>

      <el-table :data="resultData"
                :row-style="recordRowStyle"
                :border="false">
        <el-table-column prop="season"
                         :min-width="smallCol()"
                         align="center"
                         label="Season"></el-table-column>
        <el-table-column prop="eventLevel"
                         :min-width="smallCol()"
                         align="center"
                         label="Event Level"></el-table-column>
        <el-table-column prop="matchMode"
                         :min-width="smallCol()"
                         align="center"
                         label="Match Mode"></el-table-column>
        <el-table-column prop="eventName"
                         :min-width="smallCol()"
                         align="center"
                         label="Event Name"></el-table-column>
        <el-table-column prop="round"
                         :min-width="smallCol()"
                         align="center"
                         label="Round"></el-table-column>
        <el-table-column prop="result"
                         :min-width="larCol()"
                         align="center"
                         label="Result"></el-table-column>
        <el-table-column prop="score"
                         :min-width="smallCol()"
                         align="center"
                         label="Score"></el-table-column>
      </el-table>
    </div>
  </div>
</template>

<script>
import VSRing from '@/components/VSRing'
import PlayerData from '@/components/PlayerData'
import VueResource from 'vue-resource'

export default {
  name: 'VSPlayer',
  components: {
    VSRing,
    PlayerData,
    VueResource
  },
  // eslint-disable-next-line
  data() {
    return {
      players: [],
      player1: { name: '' },
      player2: {},
      player1Select: '',
      player2Select: '',
      resultData: [],
      loading: true,
      single: 0
    }
  },
  // eslint-disable-next-line
  mounted: function () {
    this.getPlayers()
  },
  methods: {
    // eslint-disable-next-line
    getPlayers: function () {
      this.$http
        .get('/api/h2h/playerList')
        .then(function (response) {
          this.players = response.data
        })
        // eslint-disable-next-line
        .catch(function (response) {
          // console.log(response)
        })
    },
    getJsonInfo: function (player1Id, player2Id, single) {
      this.$http
        .get(
          '/api/h2h/vsPlayer?player1Id=' + player1Id + '&player2Id=' + player2Id + '&singleOrDouble=' + single
        )
        .then(function (response) {
          var resdata = response.data
          // console.log(resdata)

          // Player1
          this.$set(this.player1, 'name', resdata.player1Name)
          this.$set(this.player1, 'rank', resdata.player1Rank)
          this.$set(this.player1, 'wins', resdata.winsOfPlayer1)

          // Player2
          this.$set(this.player2, 'name', resdata.player2Name)
          this.$set(this.player2, 'rank', resdata.player2Rank)
          this.$set(this.player2, 'wins', resdata.winsOfPlayer2)

          this.$set(this.player1, 'percentage', this.winRate(1))
          this.$set(this.player2, 'percentage', this.winRate(2))

          resdata = response.data.result
          let res

          this.resultData = []

          for (res in resdata) {
            var game = resdata[res]
            // console.log(game)
            var gameData = {
              season: game.season,
              eventLevel: game.eventLevel,
              matchMode: game.matchMode,
              eventName: game.eventName,
              round: game.round,
              result: game.result,
              score: game.score,
              resultFlag: game.resultFlag
            }

            this.resultData.push(gameData)
          }

          this.loading = false
          // console.log(this.player1)
          // console.log(this.player2)
        })
        // eslint-disable-next-line
        .catch(function (response) {
          // console.log(response)
        })
    },
    // eslint-disable-next-line
    playerSelected: function () {
      this.loading = true
      if ((this.player1Select !== '') & (this.player2Select !== '')) {
        this.getJsonInfo(this.player1Select, this.player2Select, this.single)
      } else {
        // TODO: alert
      }
    },
    // eslint-disable-next-line
    winRate: function (index) {
      var total = this.player1.wins + this.player2.wins
      if (total === 0) {
        return 0
      } else if (index === 1) {
        return Math.round((100 * this.player1.wins) / total)
      } else return Math.round((100 * this.player2.wins) / total)
    },
    // eslint-disable-next-line
    recordRowStyle({ row, rowIndex }) {
      // console.log(row['stateOfCurrWeek'])
      if (row['resultFlag'] === 0) {
        return { background: 'rgba(248,248,255,0.6)  !important' }
      } else if (row['resultFlag'] === 1) {
        return { background: 'rgba(64,158,255,0.6)  !important' }
      } else if (row['resultFlag'] === 2) {
        return { background: 'rgba(245,108,108,0.6)  !important' }
      } else {
        return {}
      }
    },
    // signleChange: function (value) { this.single = value },
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
    }
  }
}
</script>

<style>
.selectContainer {
  display: flex;
  flex-direction: row;
  justify-content: center;
  padding: 1% 0;
}

.selectContainer button {
  margin: 0 5%;
}

.selectContainer .playerSelect {
  max-width: 50%;
  min-width: 30%;
}

.recordContainer {
  height: auto;
  width: 100%;
  display: flex;
  flex-direction: column;
  justify-content: center;
  align-items: center;
}

.playerInfoContainer {
  height: 25vh;
  width: 80%;
  display: flex;
  flex-direction: row;
  justify-content: center;
  align-items: center;
}

.ringContainer {
  height: 100%;
}

.playerProgress {
  margin-top: 5%;
}

.recordContainer .el-table table td {
  border: none;
}
</style>
