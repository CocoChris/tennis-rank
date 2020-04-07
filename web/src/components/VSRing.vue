<template>
  <v-chart style="height: 100%"
           :options="vsRing" />
</template>

<script>
import ECharts from 'vue-echarts'

import 'echarts/lib/chart/pie'

var i = 0
var colors = ['#F56C6C', '#409EFF']

export default {
  name: 'VSRing',
  components: {
    'v-chart': ECharts
  },
  props: ['recordPlayer1', 'recordPlayer2'],
  // eslint-disable-next-line
  data() {
    return {
      vsRing: {
        series: [
          {
            name: 'vsRing',
            type: 'pie',
            radius: ['40%', '60%'],
            itemStyle: {
              normal: {
                // eslint-disable-next-line
                color: function () {
                  return colors[i++ % 2]
                },
                label: {
                  show: false
                },
                labelLine: {
                  show: false
                }
              }
            },
            avoidLabelOverlap: false,
            label: {
              normal: {
                show: false,
                position: 'center'
              },
              emphasis: {
                show: true,
                textStyle: {
                  fontSize: '20',
                  fontWeight: 'bold'
                }
              }
            },
            labelLine: {
              normal: {
                show: false
              }
            },
            data: [
              { value: this.recordPlayer2.wins, name: this.recordPlayer2.name },
              { value: this.recordPlayer1.wins, name: this.recordPlayer1.name }
            ]
          }
        ]
      }
    }
  },
  watch: {
    // eslint-disable-next-line
    recordPlayer1() {
      this.$set(this.vsRing.series.data, 0, this.recordPlayer1)
    },
    // eslint-disable-next-line
    recordPlayer2() {
      this.$set(this.vsRing.series.data, 1, this.recordPlayer2)
    }
  }
}
</script>

<style>
.echarts {
  width: 100% !important;
  max-height: 100% !important;
}
</style>
