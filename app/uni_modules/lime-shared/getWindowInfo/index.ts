// @ts-nocheck
export function getWindowInfo():GetWindowInfoResult {
	// #ifndef UNI-APP-X && APP
	if (uni.getWindowInfo || uni.canIUse('getWindowInfo')) {
		return uni.getWindowInfo();
	} else {
		return uni.getSystemInfoSync();
	}
	// #endif
	// #ifdef UNI-APP-X && APP
	return uni.getWindowInfo()
	// #endif
}