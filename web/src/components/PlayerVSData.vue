<template>
  <div class="playerVSDataContainer">
    <div v-if="!TopNMode"
         class="playerTitle"
         :style="titleOrder()">
      <div :class="playerIndex"
           class="playerName"
           :span="12">{{player.name}}</div>
      <div :class="playerIndex"
           class="playerRank"
           :span="12">No.{{player.rank}}</div>
    </div>
    <div v-else
         class="playerTitle"
         :style="titleOrder()">
      <div :class="playerIndex"
           class="playerName"
           :span="12">{{player.name}}</div>
    </div>

    <div class="playerRecord">
      <div :class="playerIndex"
           class="playerWins"
           :span="12">{{player.wins}}</div>
      <div :class="playerIndex"
           class="playerPercent"
           v-if="!$_isMobile"
           :span="12">{{player.percentage}}%</div>
    </div>

  </div>
</template>

<script>
export default {
  name: 'PlayerVSData',
  components: {},
  props: ['player', 'playerIndex', 'TopNMode'],
  data: function () {
    return {}
  },
  mounted: function () { },
  methods: {
    titleOrder: function () {
      if (this.playerIndex === 'player1' || this.$_isMobile) {
        return { order: '0' }
      } else return { order: '1' }
    }
  }
}
</script>

<style>
@media screen and (orientation: landscape) {
  .playerVSDataContainer {
    display: flex;
    flex-direction: row;
    justify-content: space-around;
    align-items: center;
    width: 100%;
  }
  .playerName,
  .playerRank {
    line-height: 60px;
    font-size: xx-large;
    text-align: center;
    font-weight: 300;
  }

  .playerWins {
    line-height: 60px;
    font-size: x-large;
    text-align: center;
    font-weight: 500;
  }

  .playerPercent {
    line-height: 60px;
    font-size: x-large;
    text-align: center;
    font-weight: 300;
    border-style: solid;
    border-width: 0.25rem 0 0 0;
  }
}

@media screen and (orientation: portrait) {
  .playerVSDataContainer {
    display: flex;
    flex-direction: column;
    justify-content: space-around;
    align-items: center;
    width: 100%;
  }

  .playerName,
  .playerRank {
    line-height: 20px;
    font-size: medium;
    text-align: center;
    font-weight: 300;
  }

  .playerWins {
    margin-top: 1vh;
    line-height: 30px;
    font-size: x-large;
    text-align: center;
    font-weight: 500;
  }

  .playerPercent {
    line-height: 25px;
    font-size: large;
    text-align: center;
    font-weight: 300;
    border-style: solid;
    border-width: 0.25rem 0 0 0;
  }
}

.player1 {
  color: #409eff;
  border-color: #409eff;
}

.player2 {
  color: #f56c6c;
  border-color: #f56c6c;
}
</style>
