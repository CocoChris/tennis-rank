<template>
  <div :style="headerStyleObject">
    <img
      id="CurlingLogo"
      :src="logoUrl"
      :style="logoStyle"
    >
    <span :style="mainTitleStyle">World Curling Association</span>
  </div>
</template>

<script>
export default {
  name: 'Header',
  // eslint-disable-next-line
  data() {
    return {
      headerStyleObject: {
        color: 'white',
        width: '100%',
        'font-size': 'xx-large',
        height: '100%'
      },
      logoUrl: require('./../assets/curling.svg'),
      logoStyle: {
        display: 'inline-box',
        height: '100%',
        width: 'auto',
        'max-width': '100%'
      },
      mainTitleStyle: {
        height: '100%',
        width: 'auto',
        dispaly: 'inline-box',
        'vertical-align': 'top',
        'font-size': this.getFontSize()
      }
    }
  },
  methods: {
    // eslint-disable-next-line
    getFontSize() {
      if (this.$_isMobile) {
        return 'x-large'
      }
      return 'xx-large'
    }
  }
}
</script>
