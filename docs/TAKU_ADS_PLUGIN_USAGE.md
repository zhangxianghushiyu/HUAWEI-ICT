# TakuAdsNativePlugin 使用说明

## 一、插件简介

`TakuAdsNativePlugin` 是 `hoshino` 项目集成的 Taku（TopOn）广告 SDK 原生插件，支持在 uni-app Android 端调用开屏、插屏、激励视频、Banner、原生广告等多种广告形式。

插件内部封装了 Taku Android SDK（TopOn），通过 `TakuAdBridge` 模块暴露 JS API，同时提供 `<taku-banner>` 和 `<taku-native-ad>` 两个原生组件用于页面内嵌入广告。

## 二、支持平台与广告类型

| 平台 | 支持状态 |
|------|---------|
| Android App | ✅ 支持 |
| iOS | ❌ 不支持 |
| H5/小程序 | ❌ 不支持 |

| 广告类型 | API / 组件 | 说明 |
|---------|-----------|------|
| 开屏广告 | `TakuAds.loadSplashAd` / `showSplashAd` | 全屏开屏 |
| 插屏广告 | `TakuAds.loadInterstitialAd` / `showInterstitialAd` | 全屏/半屏插屏 |
| 激励视频 | `TakuAds.loadRewardedVideoAd` / `showRewardedVideoAd` | 看完发奖励 |
| Banner 广告 | `TakuAds.showBannerAd` / `hideBannerAd` 或 `<taku-banner>` | 横幅 |
| 原生广告 | `TakuAds.showNativeAd` / `hideNativeAd` 或 `<taku-native-ad>` | 信息流自渲染 |

## 三、前置条件

1. 仅在 **App 环境** 可用，H5/小程序调用无效。
2. 必须在用户**同意隐私政策之后**再调用 `TakuAds.init()`，否则插件会拒绝初始化。
3. 需要在 Taku 后台创建对应的广告位，获取 `placementId`。

## 四、集成方式

### 4.1 本地插件（开发/离线打包）

插件目录：`nativeplugins/TakuAdsNativePlugin/`

`manifest.json` 中声明：

```json
"app-plus": {
    "nativePlugins": {
        "TakuAdsNativePlugin": {}
    }
}
```

### 4.2 云端插件（云打包）

将 `nativeplugins/TakuAdsNativePlugin/` 打包成 zip 上传到 DCloud 插件市场，然后在 `manifest.json` 中引用：

```json
"app-plus": {
    "nativePlugins": {
        "TakuAdsNativePlugin": {
            "version": "1.0.0"
        }
    }
}
```

> 注意：Taku/TopOn 是商业 SDK，公开上传插件市场可能涉及授权审核，建议使用私有插件或企业插件。

## 五、初始化

在 `App.vue` 的 `onLaunch` 或用户同意隐私政策后调用：

```js
import TakuAds from '@/common/taku-sdk.js'

TakuAds.init({
    appId: '你的_Taku_AppId',
    appKey: '你的_Taku_AppKey'
}, (res) => {
    if (res && res.code === 0) {
        console.log('Taku SDK 初始化成功')
    } else {
        console.error('Taku SDK 初始化失败', res)
    }
})
```

## 六、API 说明

插件通过 `common/taku-sdk.js` 包装后导出为 `TakuAds` 对象使用。

### 6.1 激励视频广告

```js
// 加载广告
TakuAds.loadRewardedVideoAd({
    placementId: 'b6a27695xxxxxx'
}, {
    onAdLoaded: (placementId) => {},
    onAdFailed: ({ placementId, error }) => {},
    onAdShow: (placementId) => {},
    onAdClicked: (placementId) => {},
    onAdClosed: (placementId) => {},
    onReward: ({ placementId, rewardAmount }) => {
        // 发放奖励
    }
})

// 展示广告
TakuAds.showRewardedVideoAd({ placementId: 'b6a27695xxxxxx' })
```

### 6.2 插屏广告

```js
TakuAds.loadInterstitialAd({
    placementId: 'b6a27695xxxxxx',
    scenarioId: '可选_场景ID',
    extraData: '可选_透传数据'
}, {
    onAdLoaded: (placementId) => {},
    onAdFailed: ({ placementId, error }) => {},
    onAdShow: (placementId) => {},
    onAdClicked: (placementId) => {},
    onAdClosed: (placementId) => {}
})

TakuAds.showInterstitialAd({ placementId: 'b6a27695xxxxxx' })
```

### 6.3 Banner 广告（JS API）

```js
TakuAds.showBannerAd({
    placementId: 'b6a27695xxxxxx',
    positionKey: 'home_banner',   // 用于标识位置，隐藏时用到
    x: 0,
    y: 600,
    width: 750,
    height: 180
}, {
    onAdLoaded: (placementId) => {},
    onAdFailed: ({ placementId, error }) => {},
    onAdShow: (placementId) => {},
    onAdClicked: (placementId) => {},
    onAdClosed: (placementId) => {}
})

// 隐藏
TakuAds.hideBannerAd('home_banner')
```

### 6.4 原生广告（JS API）

```js
TakuAds.showNativeAd({
    placementId: 'b6a27695xxxxxx',
    positionKey: 'feed_1',
    x: 0,
    y: 300,
    width: 750,
    height: 400
}, {
    onAdLoaded: (placementId) => {},
    onAdFailed: ({ placementId, error }) => {},
    onAdShow: (placementId) => {},
    onAdClicked: (placementId) => {}
})

TakuAds.hideNativeAd('feed_1')
```

### 6.5 开屏广告

```js
TakuAds.loadSplashAd('b6a27695xxxxxx', {
    onAdLoaded: (placementId) => {
        TakuAds.showSplashAd(placementId)
    },
    onAdFailed: ({ placementId, error }) => {},
    onAdShow: (placementId) => {},
    onAdClick: (placementId) => {},
    onAdDismiss: (placementId) => {}
})
```

## 七、原生组件使用

### 7.1 Banner 组件 `<taku-banner>`

```vue
<template>
    <view>
        <taku-banner
            placement-id="b6a27695xxxxxx"
            @onAdLoaded="onBannerLoaded"
            @onAdFailed="onBannerFailed"
            @onAdShow="onBannerShow"
            @onAdClicked="onBannerClicked"
            @onAdClosed="onBannerClosed"
        />
    </view>
</template>

<script>
export default {
    methods: {
        onBannerLoaded(e) { console.log('Banner 加载成功', e) },
        onBannerFailed(e) { console.error('Banner 加载失败', e) },
        onBannerShow(e) { console.log('Banner 展示', e) },
        onBannerClicked(e) { console.log('Banner 点击', e) },
        onBannerClosed(e) { console.log('Banner 关闭', e) }
    }
}
</script>
```

### 7.2 原生广告组件 `<taku-native-ad>`

```vue
<template>
    <view>
        <taku-native-ad
            placement-id="b6a27695xxxxxx"
            @onAdLoaded="onNativeLoaded"
            @onAdFailed="onNativeFailed"
            @onAdShow="onNativeShow"
            @onAdClicked="onNativeClicked"
        />
    </view>
</template>
```

## 八、事件统一说明

无论是 JS API 还是原生组件，事件名称统一如下：

| 事件名 | 说明 |
|--------|------|
| `onAdLoaded` | 广告加载成功 |
| `onAdFailed` | 广告加载失败，含 `error` 字段 |
| `onAdShow` | 广告展示 |
| `onAdClicked` | 广告被点击 |
| `onAdClosed` | 广告关闭 |
| `onReward` | 激励视频奖励发放（仅激励视频） |
| `onAdPlayStart` | 激励视频开始播放 |
| `onAdPlayEnd` | 激励视频播放结束 |

## 九、隐私合规

1. **必须在用户同意隐私政策后再调用 `TakuAds.init()`。**
2. 插件内部已做拦截：若用户未同意隐私政策，`TakuAds.init()` 会返回失败，所有广告请求都不会发起。
3. Banner/原生组件内部也会判断 SDK 是否已初始化，未初始化时不请求广告。

建议调用流程：

```
显示隐私协议弹窗 → 用户点击同意 → 调用 TakuAds.init() → 之后展示广告
```

## 十、常见问题

### Q1：调用 init 失败，提示 "not app-plus"

说明当前运行环境不是 App（可能是 H5/小程序/浏览器）。Taku 插件仅在 App 端可用。

### Q2：广告加载失败，报错 "no fill" 或 "code 2008"

- 检查 `placementId` 是否在 Taku 后台正确配置
- 检查广告位是否已启用、渠道是否已配置
- 新广告位通常需要等待一段时间才有填充

### Q3：为什么云打包后找不到 `TakuAdBridge`

- 确认 `manifest.json` 中已声明 `TakuAdsNativePlugin`
- 云打包需要选择**传统打包**，并勾选本地/云端插件
- 自定义调试基座支持本地插件，正式云打包需要走云端插件

### Q4：和 uni-ad 有什么关系？

`TakuAdsNativePlugin` 是独立的第三方原生插件，不依赖 DCloud 的 uni-ad。但如果基座内嵌了 uni-ad 的 `ba_pull` 逻辑，启动时仍可能报 `-9001`/`-9002`。需要在 `manifest.json` 中关闭 DCloud 快捷广告，并重新打自定义基座。

## 十一、相关文件

- 插件目录：`nativeplugins/TakuAdsNativePlugin/`
- JS 封装：`common/taku-sdk.js`
- 集成文档：`docs/TAKU_INTEGRATION.md`
- 自定义基座 SOP：`docs/HBUILDERX_CUSTOM_BASE_SOP.md`
