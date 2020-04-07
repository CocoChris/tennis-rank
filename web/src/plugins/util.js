export default {
  install: function (Vue) {
    // Vue.prototype.$_isMobile = true
    Vue.prototype.$_isMobile = window.matchMedia('(orientation: portrait)').matches
    // Vue.prototype._isMobile = navigator.userAgent.match(
    //   /(phone|pad|pod|iPhone|iPod|ios|iPad|Android|Mobile|BlackBerry|IEMobile|MQQBrowser|JUC|Fennec|wOSBrowser|BrowserNG|WebOS|Symbian|Windows Phone)/i)
  }
}
