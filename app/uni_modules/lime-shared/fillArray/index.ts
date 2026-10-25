// @ts-nocheck
export function fillArray<T>(length: number, value: T): T[] {
 
  // #ifdef APP-ANDROID
  const result:T[] = []
  for (let i = 0; i < length; i++) {
  	result.push(value)
  }
  // #endif

  // #ifndef APP-ANDROID
  const result = new Array(length).fill(value)
  // #endif

  

  return result
}