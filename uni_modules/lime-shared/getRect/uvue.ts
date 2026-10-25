// @ts-nocheck
/**
 * 获取节点信息
 * @param selector 选择器字符串
 * @param context ComponentPublicInstance 对象，传 null 时自动使用页面级 context
 * @returns 包含节点信息的 Promise 对象
 */
export function getRect(selector : string, context: ComponentPublicInstance|null=null):Promise<NodeInfo> {	
	return new Promise((resolve)=>{
		if(context != null) {
			uni.createSelectorQuery().in(context!).select(selector).boundingClientRect(res =>{
				resolve(res as NodeInfo)
			}).exec();
		} else {
			uni.createSelectorQuery().select(selector).boundingClientRect(res =>{
				resolve(res as NodeInfo)
			}).exec();
		}
		
	})
}

export function getAllRect(selector : string, context: ComponentPublicInstance|null=null):Promise<NodeInfo[]> {
	return new Promise((resolve)=>{
		if(context != null){
			uni.createSelectorQuery().in(context!).selectAll(selector).boundingClientRect(res =>{
				resolve(res as NodeInfo[])
			}).exec();
		} else {
			uni.createSelectorQuery().selectAll(selector).boundingClientRect(res =>{
				resolve(res as NodeInfo[])
			}).exec();
		}
	})
}