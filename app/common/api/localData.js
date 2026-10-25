const SERIES = [
	{ dramaId: 1,  title: '梨园春色',     classifyId: 11, totalGather: 12, titleImg: '/static/sourceMaterial/1.png' },
	{ dramaId: 2,  title: '戏韵悠悠',     classifyId: 11, totalGather: 8,  titleImg: '/static/sourceMaterial/2.png' },
	{ dramaId: 3,  title: '百年老歌精选', classifyId: 12, totalGather: 20, titleImg: '/static/sourceMaterial/3.png' },
	{ dramaId: 4,  title: '那个年代的歌', classifyId: 12, totalGather: 15, titleImg: '/static/sourceMaterial/4.png' },
	{ dramaId: 5,  title: '曲艺大舞台',   classifyId: 13, totalGather: 10, titleImg: '/static/sourceMaterial/5.png' },
	{ dramaId: 6,  title: '传统节日故事', classifyId: 21, totalGather: 6,  titleImg: '/static/sourceMaterial/6.png' },
	{ dramaId: 7,  title: '民间故事精华', classifyId: 22, totalGather: 18, titleImg: '/static/sourceMaterial/7.png' },
	{ dramaId: 8,  title: '非遗技艺传承', classifyId: 31, totalGather: 9,  titleImg: '/static/sourceMaterial/8.png' },
	{ dramaId: 9,  title: '手工艺人',     classifyId: 32, totalGather: 14, titleImg: '/static/sourceMaterial/9.png' },
	{ dramaId: 10, title: '传统医药',     classifyId: 33, totalGather: 7,  titleImg: '/static/sourceMaterial/10.png' },
]

function makePageRes(list) {
	return {
		code: 200, msg: 'success', success: true,
		data: {
			pageNum: 1, pageSize: 15, total: list.length, pages: 1,
			list,
			isFirstPage: true, isLastPage: true,
			hasPreviousPage: false, hasNextPage: false
		}
	}
}

export default {

	/** @description 短剧分类 */
	getCategory() {
		return new Promise((resolve) => {
			setTimeout(() => {
				resolve({
					code: 200, msg: 'success', success: true,
					data: [
						{ classifyId: 1, name: '环境' },
						{ classifyId: 2, name: '公司' },
						{ classifyId: 3, name: '介绍' }
					]
				})
			}, 500)
		})
	},

	/** @description 首页系列列表 */
	homePage(param) {
		return new Promise((resolve) => {
			const categoryId = param && param.categoryId
			const list = (categoryId ? SERIES.filter(s => s.classifyId === categoryId) : SERIES)
				.map(s => ({
					...s,
					isVip: 0, isRecommend: 1, details: '这是系列简介',
					playCount: 88800, likeCount: 360,
					isEnd: 1, watchGather: 1,
					createTime: '2024-12-25 22:18:03',
					price: 0, isPrice: 0, isCollect: 0
				}))
			setTimeout(() => resolve(makePageRes(list)), 500)
		})
	},

	/** @description 搜索短剧 */
	dramaQuery(param) {
		return new Promise((resolve) => {
			const key = (param && param.title) || ''
			const list = SERIES.filter(s => s.title.includes(key)).map(s => ({
				...s,
				isVip: 0, isRecommend: 1, details: '',
				playCount: 88800, likeCount: 360,
				isEnd: 1, watchGather: 1,
				createTime: '2024-12-25 22:18:03',
				price: 0, isPrice: 0, isCollect: 0
			}))
			setTimeout(() => resolve(makePageRes(list)), 500)
		})
	},

	/** @description 获取视频列表 */
	getVideoList(param) {
		return new Promise((resolve) => {
			const data = [1, 2, 3, 4].map(i => ({
				id: i,
				name: `测试视频${i}`,
				content: '测试内容',
				videoUrl: 'https://www.w3schools.com/html/mov_bbb.mp4',
				videoCover: `/static/notice/${i}.jpg`,
				playCount: 0, likeCount: 10001, collectCount: 2,
				createTime: '2024-12-16 11:54:36',
				isCollect: 0, isLike: 0
			}))
			setTimeout(() => resolve({ code: 200, msg: 'success', success: true, data }), 500)
		})
	},

	/** @description 获取一级分类 */
	getShotType(param) {
		return new Promise((resolve) => {
			setTimeout(() => {
				resolve({
					code: 200, msg: 'success', success: true,
					data: {
						list: [
							{ id: 1, name: '文化艺术' },
							{ id: 2, name: '民俗风情' },
							{ id: 3, name: '非遗传承' }
						],
						isLastPage: true
					}
				})
			}, 300)
		})
	},

	/** @description 获取短剧集数列表 */
	getDetailsList(param) {
		return new Promise((resolve) => {
			const dramaId = (param && param.dramaId) || 1
			const series = SERIES.find(s => s.dramaId === dramaId) || SERIES[0]
			const count = series.totalGather || 6
			const list = []
			for (let i = 1; i <= count; i++) {
				// 前3集免费，第4集起锁定（无videoUrl）
				const isFree = i <= 3
				list.push({
					dramaDetailsId: dramaId * 100 + i,
					dramaId: dramaId,
					title: series.title + ' 第' + i + '集',
					gather: i,
					videoUrl: isFree ? 'https://www.w3schools.com/html/mov_bbb.mp4' : '',
					titleImg: series.titleImg || '/static/sourceMaterial/1.png',
					playCount: 10000 + i * 100,
					likeCount: 200 + i * 10,
					isLike: 0,
					isCollect: 0,
					details: series.title + ' 第' + i + '集简介'
				})
			}
			setTimeout(() => resolve({ code: 200, msg: 'success', success: true, data: list }), 500)
		})
	},

	/** @description 获取二级分类 */
	getShotNextType(param) {
		const map = {
			1: [{ id: 11, name: '戏剧' }, { id: 12, name: '老歌' }, { id: 13, name: '曲艺' }],
			2: [{ id: 21, name: '传统节日' }, { id: 22, name: '民间故事' }],
			3: [{ id: 31, name: '非遗技艺' }, { id: 32, name: '手工艺' }, { id: 33, name: '传统医药' }]
		}
		const parentId = param && param.parentId
		return new Promise((resolve) => {
			setTimeout(() => {
				resolve({
					code: 200, msg: 'success', success: true,
					data: { list: map[parentId] || map[1], isLastPage: true }
				})
			}, 300)
		})
	}
}
