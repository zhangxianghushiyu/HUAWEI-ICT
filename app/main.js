// main.js
import Vue from 'vue'
import App from './App'
import utils from 'common/utils.js'
import apiService from '@/common/api/apiService.js'
import store from './store/switch.js'
import GlobalToast from '@/components/global-toast.vue'

// ============================================
// Vue 配置
// ============================================
Vue.prototype.$store = store
Vue.config.productionTip = false
Vue.component('global-toast', GlobalToast)

// ###########################
// 这里已经把报错的 document.body 全部删掉了！
// ###########################

// 全局捕获浏览器自动播放策略导致的 play() 拒绝异常
if (typeof window !== 'undefined') {
	window.addEventListener('unhandledrejection', function(event) {
		if (event.reason && event.reason.name === 'NotAllowedError' &&
			event.reason.message && event.reason.message.indexOf('play()') !== -1) {
			event.preventDefault();
		}
	});
}

Vue.prototype.$utils = utils
Vue.prototype.$api = apiService

App.mpType = 'app'

const app = new Vue({
    store,
    ...App
})
app.$mount()

// ============================================
// 启动后延迟检查（调试用）
// ============================================
// #ifdef APP-PLUS
// setTimeout(() => {
//     console.log('🔍 [main.js] 启动后检查:')
//     checkTakuStatus()
// }, 3000)
// #endif