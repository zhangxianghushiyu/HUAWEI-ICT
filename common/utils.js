import {
	upLoadUrl
} from '@/common/api/config.js'
export default {

	/** @description 根据属性获取下标 a数组 b属性值 c属性名字 */
	SXindex(a, b, c) {
		return a.indexOf(a.find(item => item[c] == b))
	},
	/** @description 复制文本 */
	copyText(text) {
		uni.setClipboardData({
			data: text,
			success: function(res) {
				uni.showToast({
					icon: 'none',
					title: '复制成功',
				});
			},
			fail: function(err) {
				console.log(err)
			},
		});
	},
	/** @description 深拷贝 */
	deepCopy(items) {
		return JSON.parse(JSON.stringify(items))
	},
	/** @description 验证手机号 */
	verifyPhone(phoneNumber) {
		const phoneRegex = /^1[3-9]\d{9}$/;
		return phoneRegex.test(phoneNumber);
	},
	/** @description 打电话
		@param 手机号 Number
	 */
	hitPhone(Number) {
		if (!this.verifyPhone(Number)) {
			this.showTip("手机号格式不正确，请联系管理员", "提示")
			return;
		}

		uni.makePhoneCall({
			phoneNumber: Number
		});
	},
	/** @description 预览图片 */
	previewImage(current, urls) {
		uni.previewImage({
			current,
			urls,
		})
	},

	/** @description 获取随机数 */
	getRandomInt() {
		return Math.floor(Math.random() * (1000 - -1000 + 1)) + -1000;
	},
	/** @description 获取当前路径 */
	currentPath() {
		// 获取当前页面路径
		const currentPage = getCurrentPages()[getCurrentPages().length - 1].route;
		console.log(currentPage);
		return currentPage
	},
	/** @description 计算文件大小 */
	convertFileSize(sizeInBytes) {
		if (sizeInBytes >= 1024 * 1024) {
			const sizeInMB = sizeInBytes / (1024 * 1024);
			return `${sizeInMB.toFixed(2)} MB`;
		} else {
			const sizeInKB = sizeInBytes / 1024;
			return `${sizeInKB.toFixed(2)} KB`;
		}
	},
	/** @description 获取url参数 
		@returns params 参数对象
	*/
	getURLParams(url) {
		if (!url) return {}
		const queryString = url.split('?')[1];
		const queryParams = {};
		queryString.split('&').forEach(param => {
			const [key, value] = param.split('=');
			queryParams[key] = value;
		});
		return queryParams
	},
	/** @description 提示 */
	showTip(content, title = "提示") {
		uni.showModal({
			title: title,
			content: content,
			showCancel: false,
			confirmColor: '#F14835'
		});
	},
	/** @description 异步提示 */
	showTipAsync(content, title) {
		return new Promise((resolve, reject) => {
			uni.showModal({
				title: title,
				content: content,
				showCancel: false,
				confirmColor: '#F14835',
				success() {
					resolve();
				},
				fail() {
					reject()
				}
			});
		})
	},
	/** @description 异步确认 */
	showModalAsync(content, title) {
		return new Promise((resolve, reject) => {
			uni.showModal({
				title: title,
				content: content,
				confirmColor: '#F14835',
				success(e) {
					if (e.confirm) {
						resolve();
					}
					if (e.cancel) {
						reject();
					}
				}
			});
		})
	},
	/** @description 获取经纬度 */
	getLocation() {
		return new Promise((resolve, reject) => {
			uni.getLocation({
				type: "gcj02",
				isHighAccuracy: true,
				success(res) {
					resolve(res);
				},
				fail(err) {
					reject(err);
				},
			});
		})
	},
	/** @description 保存图片至相册 */
	savePicture(path) {
		uni.saveImageToPhotosAlbum({
			filePath: path,
			success() {
				uni.showToast({
					icon: 'none',
					title: '保存成功',
				});
			},
			complete(res) {
				console.log(res);
			}
		});
	},
	/** @description 时间戳转时间 格式 2024-09-22 16:31:33 */
	timestampToTime(timestamp) {
		const date = new Date(timestamp * 1000);
		const a = date.getFullYear()
		const b = String(date.getMonth() + 1).padStart(2, '0')
		const c = String(date.getDate()).padStart(2, '0')
		const d = String(date.getHours()).padStart(2, '0')
		const e = String(date.getMinutes()).padStart(2, '0')
		const f = String(date.getSeconds()).padStart(2, '0')

		return `${a}-${b}-${c} ${d}:${e}:${f}`;
	},
	/** @description 距离格式化 */
	distanceFormatting(distance) {

		if (distance >= 1000) {
			return `${(distance / 1000).toFixed(0)} km`
		} else {
			return `${distance.toFixed(0)} m`
		}
	},
	/** @description 页面跳转 */
	toPage(url) {
		// const token = uni.getStorageSync('Token')

		if (url.includes('/pages/index/index') ||
			url.includes('/pages/mine/index') ||
			url.includes('/pages/look-at/index')
		) {
			uni.reLaunch({
				url: url
			})
			return
		}

		// if (!token &&
		// 	!url.includes('/pages/mine/policy') &&
		// 	!url.includes('/pages/mine/code') &&
		// 	!url.includes('/pages/mine/register-account') &&
		// 	!url.includes('/pages/index/query')
		// ) {
		// 	uni.reLaunch({
		// 		url: '/pages/mine/login'
		// 	})

		// 	return
		// }

		uni.navigateTo({
			url: url
		})
	},
	/**
	 * @description 上传文件
	 * @param {String} file 文件路径
	 */
	async upLoadFile(file) {
		return new Promise((resolve, reject) => {
			uni.showLoading({
				title: '上传中'
			})

			uni.uploadFile({
				url: upLoadUrl,
				filePath: file,
				name: "file",
				success(res) {
					const result = JSON.parse(res.data)
					resolve(result);
				},
				fail(err) {
					console.log(err);
					reject(err.msg);
				},
				complete() {
					uni.hideLoading()
				}
			})
		})
	},
	/**
	 * @description 微信支付
	 */
	async wxPay(orderNo) {
		return new Promise((resolve, reject) => {
			uni.showLoading({
				title: '拉取支付'
			})

			// uni.requestPayment({
			// 	provider: 'wxpay',
			// 	timeStamp: config.data.timeStamp,
			// 	nonceStr: config.data.nonceStr,
			// 	package: config.data.package,
			// 	signType: config.data.signType,
			// 	paySign: config.data.paySign,
			// 	success: (paymentRes) => {
			// 		console.log(1);
			// 		resolve('支付成功')
			// 	},
			// 	fail: (paymentErr) => {
			// 		console.log(2);
			// 		resolve('支付取消')
			// 	},
			// });

			resolve('成功');
		})
	},
	/**
	 * @description 支付宝支付
	 */
	async alipayPay(orderInfo) {
		return new Promise((resolve, reject) => {
			uni.showLoading({
				title: '拉取支付'
			})

			uni.requestPayment({
				//服务提供商 通过uni.getProvider获取
				provider: 'alipay',
				//后台返回的订单数据
				orderInfo: orderInfo,
				//调用成功的回调
				success(res) {
					console.log(res);
					resolve('支付成功')
				},
				//调用失败的回调
				fail(err) {
					console.log(err);
					reject('支付取消')
				}
			})
		})
	},
}