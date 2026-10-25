// @ts-nocheck
/**
 * Canvas上下文创建结果类型
 * 包含创建Canvas上下文时返回的各个组件
 */
export type LimeCanvasInstance = {
	canvasContext : CanvasContext,
	node : UniCanvasElement,
	renderContext : CanvasRenderingContext2D,
	isCanvas2d : boolean,
	width : number,
	height : number,
	toDataURL : (type?: string, quality?: number) => Promise<string>,
	flush : (reserve?: boolean) => Promise<void>,
	getImageData : (x: number, y: number, width: number, height: number) => Promise<ImageData>
}



/**
 * 异步创建Canvas上下文
 * 这个函数封装了uni-app不同平台下的Canvas创建逻辑：
 * 1. 在uni-app X中使用uni.createCanvasContextAsync API
 * 2. 在非uni-app X中使用uni.createSelectorQuery查询Canvas元素
 * 
 * @param canvasId - Canvas元素的id选择器
 * @param component - Vue组件实例
 * @returns Promise<LimeCanvasInstance> 包含Canvas上下文、元素和渲染上下文
 * 
 */
export function createCanvasContextAsync(
	canvasId : string,
	component : ComponentPublicInstance
) : Promise<LimeCanvasInstance> {
	return new Promise((resolve, reject) => {
		// 条件编译：uni-app X平台
		// uni-app X使用新的Canvas API
		// #ifdef UNI-APP-X
		nextTick(() => {
			uni.createCanvasContextAsync({
				id: canvasId,
				component,
				success(canvasContext : CanvasContext) {
					// 从CanvasContext中获取2D渲染上下文
					const ctx = canvasContext.getContext('2d')!;
					// 获取Canvas DOM元素
					const node = ctx.canvas;
					// 确保获取有效的尺寸
					const width = node.offsetWidth;
					const height = node.offsetHeight;
					resolve({
						canvasContext: canvasContext,
						node,
						renderContext: ctx,
						isCanvas2d: true,
						width,
						height,
						toDataURL: (type?: string, quality?: number) : Promise<string> => {
							return new Promise((resolve) => {
								const dataURL = canvasContext.toDataURL(type ?? 'image/png', quality ?? 1.0)
								resolve(dataURL)
							})
						},
						flush: (_reserve?: boolean):Promise<void> => {
							return Promise.resolve()
						},
						getImageData: (x: number, y: number, w: number, h: number) : Promise<ImageData> => {
							return new Promise((resolve, reject) => {
								try {
									const imageData = ctx.getImageData(x, y, w, h)
									resolve(imageData)
								} catch (error) {
									reject(error)
								}
							})
						}
					} as LimeCanvasInstance)
				},
				fail(error) {
					// uni-app X中创建失败时reject错误
					reject(error)
				}
			})
		})

		// #endif

		// 条件编译：非uni-app X平台
		// 包括H5、小程序等平台，使用传统的Canvas查询方式
		// #ifndef UNI-APP-X
		// 创建选择器查询
		uni.createSelectorQuery()
			// 限定在指定组件内查询
			.in(component)
			// 选择指定的Canvas元素
			.select(`#${canvasId}`)
			// 指定需要查询的字段
			.fields({
				node: true,      // 需要获取Canvas节点
				size: true,      // 需要获取尺寸信息
				rect: true       // 需要获取位置信息
			}, (queryResult) => {
				// 成功查询到Canvas节点
				if (queryResult.node && queryResult.node.getContext) {
					// 获取Canvas的2D渲染上下文
					const renderContext = queryResult.node.getContext('2d')!;

					let isCanvas2d = true;

					// #ifdef WEB
					isCanvas2d = ![...queryResult.node.classList].includes('uni-canvas-canvas')
					// #endif
					
					const canvasContext = {
						createImage: () => {
							if(queryResult.node.createImage) {
								return queryResult.node.createImage()
							}
							return new LimeCanvasImage()
						},
						requestAnimationFrame: (callback: (time: number) => void) : number => {
							// #ifdef WEB
							return requestAnimationFrame(callback)
							// #endif
							// #ifndef WEB
							if (queryResult.node.requestAnimationFrame) {
								return queryResult.node.requestAnimationFrame(callback)
							}
							// return setTimeout(callback, Math.floor(1000 / 60)) as unknown as number
							return setTimeout(() => callback(Date.now()), Math.floor(1000 / 60)) as unknown as number
							// #endif
						},
						cancelAnimationFrame: (id: number) : void => {
							// #ifdef WEB
							cancelAnimationFrame(id)
							// #endif
							// #ifndef WEB
							if (queryResult.node.cancelAnimationFrame) {
								queryResult.node.cancelAnimationFrame(id)
							} else {
								clearTimeout(id)
							}
							// #endif
						}
					}
					
					resolve({
						renderContext,
						node: queryResult.node,
						canvasContext,
						isCanvas2d,
						width: queryResult.width,
						height: queryResult.height,
						toDataURL: (type?: string, quality?: number) : Promise<string> => {
							return new Promise((resolve) => {
								if (queryResult.node != null && typeof queryResult.node.toDataURL == 'function') {
									const dataURL = queryResult.node.toDataURL(type ?? 'image/png', quality ?? 1.0)
									resolve(dataURL)
								} else {
									uni.canvasToTempFilePath({
										canvas: queryResult.node,
										success: (res) => {
											resolve(res.tempFilePath)
										},
										fail: () => {
											resolve('')
										}
									})
								}
							})
						},
						flush: (_reserve?: boolean) => {
							return Promise.resolve()
						},
						getImageData: (x: number, y: number, w: number, h: number) : Promise<ImageData> => {
							return new Promise((resolve, reject) => {
								try {
									const result = renderContext.getImageData(x, y, w, h)
									if (result && typeof result.then === 'function') {
										result.then((imageData : ImageData) => {
											resolve(imageData)
										}).catch(reject)
									} else {
										resolve(result as ImageData)
									}
								} catch(e) {
									reject(e)
								}
							})
						}
					} as LimeCanvasInstance)
				} else {
					// 未能获取到Canvas节点，回退到旧的API
					// 使用uni.createCanvasContext创建绘图上下文
					const renderContext = uni.createCanvasContext(canvasId, component)
					if (!renderContext._drawImage) {
						renderContext._drawImage = renderContext.drawImage
						renderContext.drawImage = function (...args) {
							const {path} = args.shift();
							renderContext._drawImage(path, ...args)
						}
					}

					// 创建模拟的CanvasContext对象以保持接口一致
					const canvasContext = {
						/**
						 * 模拟CanvasContext的getContext方法
						 * 在旧版本API中返回绘图上下文
						 */
						getContext(type : string) {
							return renderContext
						},
						createImage(){
							return new LimeCanvasImage()
						},
						requestAnimationFrame: (callback: (time: number) => void) : number => {
							return setTimeout(() => callback(Date.now()), Math.floor(1000 / 60)) as unknown as number
						},
						cancelAnimationFrame: (id: number) : void => {
							clearTimeout(id)
						}
					}
					const node = {
						...canvasContext,
					}
					// 在回退方案中，无法获取到Canvas DOM元素
					resolve({
						renderContext,
						node,
						canvasContext,
						isCanvas2d: false,
						width: queryResult.width || 300,
						height: queryResult.height || 150,
						toDataURL: (type?: string, quality?: number) : Promise<string> => {
							return new Promise((resolve) => {
								renderContext.draw(true, () => {
									const fileType = type == 'image/jpeg' ? 'jpg' : 'png'
									uni.canvasToTempFilePath({
										canvasId: canvasId,
										fileType: fileType,
										quality: quality ?? 1.0,
										success: (res) => {
											resolve(res.tempFilePath)
										},
										fail: (err) => {
											console.log('[createCanvasContextAsync]:生成图片失败', err)
											resolve('')
										}
									}, component)
								})
							})
						},
						flush: (reserve?: boolean) => {
							return new Promise((resolve)=> {
								renderContext.draw(reserve ?? true, resolve)
							})
						},
						getImageData: (x: number, y: number, w: number, h: number) : Promise<ImageData> => {
							return new Promise((resolve, reject) => {
								renderContext.draw(true, () => {
									uni.canvasGetImageData({
										canvasId: canvasId,
										x: Math.round(x),
										y: Math.round(y),
										width: Math.round(w),
										height: Math.round(h),
										success(res : any) {
											resolve(res as ImageData)
										},
										fail(err : any) {
											reject(err)
										}
									}, component)
								})
							})
						}
					} as LimeCanvasInstance)
				}
			}).exec()  // 执行查询
		// #endif
	})
}


// #ifndef UNI-APP-X
// #ifndef WEB
class LimeCanvasImage {
	currentSrc: string | null = null
	naturalHeight: number = 0
	naturalWidth: number = 0
	width: number = 0
	height: number = 0
	tagName: string = 'IMG'
	path: string = ''
	crossOrigin: string = ''
	referrerPolicy: string = ''
	onload: () => void = () => {}
	onerror: () => void = () => {}
	complete: boolean = false
	constructor() {}
	set src(src: string) {
		if(!src) {
			return this.onerror()
		}
		src = src.replace(/^@\//,'/')
		this.currentSrc = src
		// this.path = src
		// uniapp 好像不需要下载
		// this.onload()
		uni.getImageInfo({
			src,
			success: (res: any) => {
				this.path = res.path
				this.naturalWidth = this.width = res.width
				this.naturalHeight = this.height = res.height
				this.onload()
			},
			fail: () => { this.onerror() }
		})
	}
	get src() {
		return this.currentSrc
	}
}
// #endif
// #ifdef WEB
const LimeCanvasImage = Image
// #endif
// #endif
