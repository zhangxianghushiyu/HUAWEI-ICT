import Vue from 'vue';
import Vuex from 'vuex';

Vue.use(Vuex);

const store = new Vuex.Store({
	state: {
		isShow: true
	},
	mutations: {
		setShow(state, payload) {
			state.isShow = payload;
		}
	},
	actions: {
		asyncSetShow({
			commit
		}, payload) {
			// 模拟异步操作，例如网络请求、定时器等
			commit('setShow', payload);
		}
	}
});

export default store;