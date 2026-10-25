// @ts-nocheck
export function getDeviceInfo():GetDeviceInfoResult {
	// #ifndef UNI-APP-X && APP
	if (uni.getDeviceInfo || uni.canIUse('getDeviceInfo')) {
		return uni.getDeviceInfo();
	} else {
		return uni.getSystemInfoSync();
	}
	// #endif
	// #ifdef UNI-APP-X && APP
	return uni.getDeviceInfo()
	// #endif
}