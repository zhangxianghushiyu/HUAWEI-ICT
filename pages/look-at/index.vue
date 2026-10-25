<template>
	<view class="redirect-page">
		<uv-loading-icon color="#fff" size="36" :vertical="true" text="加载中"></uv-loading-icon>
	</view>
</template>

<script>
	export default {
		onLoad(e) {
			console.log('[look-at] 重定向到 player/index, 参数:', JSON.stringify(e))
			
			// 构建跳转 URL
			let url = '/pages/player/index?type=video'
			
			// 如果有 id 参数，带上
			if (e && e.id) {
				url += `&id=${e.id}`
			}
			
			// 使用 reLaunch 避免返回循环
			uni.reLaunch({ 
				url,
				fail: (err) => {
					console.error('[look-at] 重定向失败:', err)
					// 降级方案：直接跳转
					uni.navigateTo({ url })
				}
			})
		}
	}
</script>

<style scoped>
	.redirect-page {
		position: fixed;
		top: 0;
		left: 0;
		width: 100%;
		height: 100%;
		background-color: #000000;
		display: flex;
		align-items: center;
		justify-content: center;
	}
</style>