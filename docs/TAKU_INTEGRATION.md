# Taku 广告集成文档

## 一、集成方式概览

本项目同时支持两种 Android 集成/测试方式：

1. **离线打包（命令行）**：使用 HBuilder-Integrate-AS 工程本地编译 APK，适合调试和最终发版。
2. **自定义基座（HBuilderX）**：将原生插件打包进 HBuilderX 自定义调试基座，适合日常 JS 层开发调试。

核心源码统一放在 `nativeplugins/TakuAdsNativePlugin/` 中，离线工程通过同步脚本保持一致。

## 二、已支持广告类型

| 广告类型 | 入口页面 | Taku placementId | 说明 |
|----------|----------|------------------|------|
| 开屏 | `App.vue` | `b6a2769587c201` | 启动首屏全屏广告 |
| 插屏 | `pages/player/index.vue` | `b6a27695ae4bad` | 娱乐 Tab 右侧悬浮红包点击（看 5 个短视频后触发） |
| 插屏 | `pages/index/video-see.vue` | `b6a27695d01da8` | 短剧每看完 3 集自动弹 |
| 激励视频 | `pages/index/video-see.vue` | `b6a276959b26c2` | 短剧「看广告解锁下一集」 |
| 激励视频 | `pages/game/weChat.vue` | `b6a27695d9c3d4` | 红包群领红包奖励 |
| 原生 | `pages/index/index.vue` | `b6a276959b29c7` | 首页短剧列表第 4、10 位 |
| Banner | `pages/mine/index.vue` | `b6a27695b77b77` | 个人中心底部 |

渠道配置：Taku Core + 百度联盟 + 穿山甲 CSJ + 优量汇 GDT。已在真机验证开屏广告可正常展示。

## 三、目录结构

```
hoshino/
├── App.vue                              # JS 层调用 Taku 开屏广告
├── common/taku-sdk.js                   # TakuAdBridge 包装器（window.TakuAds）
├── manifest.json                        # 声明原生插件 TakuAdsNativePlugin
├── nativeplugins/
│   └── TakuAdsNativePlugin/             # 原生插件包
│       ├── package.json
│       ├── readme.md
│       └── android/
│           ├── dcloud_uniplugins.json
│           ├── libs/                    # Taku Core + 渠道 SDK/Adapter aar
│           └── src/main/java/com/xingye/hoshino/taku/
│               ├── TakuAdBridge.java              # 桥接模块
│               ├── TakuNativeAdComponent.java     # 原生广告原生组件
│               └── TakuBannerComponent.java       # Banner 原生组件
├── pages/
│   ├── index/index.vue                  # 首页原生广告
│   ├── index/video-see.vue              # 短剧插屏+激励视频
│   ├── player/index.vue                 # 娱乐 Tab 右侧悬浮红包 -> 插屏广告
│   ├── player/drama.vue                 # 短剧激励视频解锁
│   ├── articel/index.vue                # 欢乐吧顶部原生横幅广告
│   ├── novel/index.vue                  # 小说原生广告（已按新方案移除）
│   ├── mine/index.vue                   # 个人中心 Banner
│   └── game/weChat.vue                  # 红包群激励视频
├── scripts/
│   ├── sync-plugin-to-offline.sh        # 同步插件到离线工程
│   ├── build-offline-apk.sh             # 命令行编译离线 APK
│   └── install-and-run.sh               # 安装运行并抓取日志
└── docs/
    ├── TAKU_INTEGRATION.md              # 本文档
    └── HBUILDERX_CUSTOM_BASE_SOP.md     # 自定义基座制作 SOP
```

## 四、JS 调用

### 4.1 初始化

建议在 `App.vue` 的 `onLaunch` 中统一初始化：

```js
// #ifdef APP-PLUS
const TakuAdBridge = uni.requireNativePlugin('TakuAdBridge')
TakuAdBridge.init({
    appId: 'a6a2768f6227eb',
    appKey: 'a6af687f456b60b76907f9a9b8cbbc73f'
}, (res) => {
    console.log('Taku init result', res)
})
// #endif
```

`init` 采用异步线程执行（`uiThread = false`），避免 GDT 等渠道初始化阻塞主线程导致 ANR。

### 4.2 开屏广告

```js
// #ifdef APP-PLUS
const TakuAdBridge = uni.requireNativePlugin('TakuAdBridge')
TakuAdBridge.init({ appId: 'a6a2768f6227eb', appKey: 'a6af687f456b60b76907f9a9b8cbbc73f' }, (res) => {
    if (res && res.code === 0) {
        TakuAdBridge.loadSplashAd({ placementId: 'b6a2769587c201' }, (r) => {
            if (r && r.event === 'onAdLoaded') {
                TakuAdBridge.showSplashAd({ placementId: 'b6a2769587c201' })
            }
        })
    }
})
// #endif
```

### 4.3 插屏广告

```js
// #ifdef APP-PLUS
const TakuAd = window.TakuAds || uni.requireNativePlugin('TakuAdBridge')
TakuAd.loadInterstitialAd('b6a27695d01da8', (res) => {
    if (res.event === 'onAdLoaded') {
        TakuAd.showInterstitialAd('b6a27695d01da8')
    }
})
// #endif
```

### 4.4 激励视频

```js
// #ifdef APP-PLUS
const TakuAd = window.TakuAds || uni.requireNativePlugin('TakuAdBridge')
TakuAd.loadRewardedVideoAd('b6a276959b26c2', (res) => {
    if (res.event === 'onAdLoaded') {
        TakuAd.showRewardedVideoAd('b6a276959b26c2')
    } else if (res.event === 'onReward') {
        console.log('发放奖励', res.extra)
    }
})
// #endif
```

## 五、原生组件

### 5.1 原生广告 `<taku-native-ad>`

```html
<!-- #ifdef APP-PLUS -->
<taku-native-ad placement-id="b6a276959b29c7"
  style="width:100%;height:300rpx;border-radius:32rpx;"
  @onAdLoaded="onNativeAdLoaded"
  @onAdFailed="onNativeAdFailed"
  @onAdClicked="onNativeAdClick">
</taku-native-ad>
<!-- #endif -->
```

事件：

- `onAdLoaded`：广告素材加载成功
- `onAdFailed`：加载失败，`detail.message` 包含错误信息
- `onAdClicked`：用户点击广告
- `onAdShow`：广告曝光

### 5.2 Banner `<taku-banner>`

```html
<!-- #ifdef APP-PLUS -->
<taku-banner placement-id="b6a27695b77b77"
  style="width:100%;height:160rpx;border-radius:32rpx;"
  @onAdLoaded="onBannerAdLoaded"
  @onAdFailed="onBannerAdFailed"
  @onAdClicked="onBannerAdClick">
</taku-banner>
<!-- #endif -->
```

事件与原生广告一致。

## 六、离线打包（命令行）

### 6.1 环境要求

- JDK 17（已配置在 `D:/java/jdk17`）
- Android SDK（已配置在 `D:/APP/android-sdk`）
- Git Bash（用于执行 `.sh` 脚本）

### 6.2 同步插件并编译

```bash
# 1. 先用 HBuilderX 导出本地打包 App 资源到 unpackage/resources/__UNI__8969615
# 2. 同步插件 + 资源到离线工程
bash scripts/sync-plugin-to-offline.sh

# 3. 编译 debug / release APK
bash scripts/build-offline-apk.sh debug
bash scripts/build-offline-apk.sh release

# 4. 安装运行并查看 Taku 日志
bash scripts/install-and-run.sh debug
```

### 6.3 关键配置

- **包名**：`com.xingye.hoshino`
- **签名**：`D:/APP/hyt/test.keystore`（别名 `testalias`，密码 `Ll159357`）
- **DCloud AppKey**：`755d312670ac60089fa9807eff6483d0`（已写入离线工程 AndroidManifest.xml）
- **AppID**：`__UNI__8969615`

> 如果更换证书，必须去 [DCloud 开发者中心](https://dev.dcloud.net.cn) 更新 Android 证书 SHA1/SHA256 并重新获取离线 Key。

## 七、自定义基座（HBuilderX）

### 7.1 前提

- 本机已安装 HBuilderX。
- 登录 HBuilderX 账号，且账号已绑定 DCloud 应用 `__UNI__8969615`。

### 7.2 制作自定义基座

1. 打开 `hoshino` 项目。
2. 确保 `manifest.json → app-plus → nativePlugins` 中已声明 `TakuAdsNativePlugin`。
3. 点击菜单 **运行 → 运行到手机或模拟器 → 制作自定义基座**。
4. 在弹窗中勾选 **TakuAdsNativePlugin**，选择 Android 平台，点击打包。
5. 打包完成后，使用 **运行 → 运行到手机或模拟器 → 基座运行** 选择刚制作的自定义基座运行。

### 7.3 注意事项

- 自定义基座属于云打包，会消耗 DCloud 打包次数；离线打包不消耗。
- 自定义基座主要用于 JS 层调试；最终发布 APK 建议走离线打包或云打包正式版。

## 八、真机验证清单

> 受 ADB 自动点击限制（部分系统拒绝 `INJECT_EVENTS` 权限），需要手动在真机上逐项验证，并通过 `logcat | grep Taku` 观察日志。

- [ ] 启动 App：开屏广告正常展示并关闭
- [ ] 娱乐 Tab（player/index）：右侧红包看满 5 个短视频后可点击，点击弹出插屏广告，关闭后发放金币，单日 10 次上限生效
- [ ] 欢乐吧（articel/index）：顶部原活动图位置展示原生横幅广告，无广告时隐藏
- [ ] 首页（index/index）：短剧列表第 4、10 位出现原生广告卡片
- [ ] 个人中心（mine/index）：底部出现 Banner
- [ ] 短剧页（player/drama）：第 5 集出现「看广告免费解锁下一集」按钮，完播后解锁并发放奖励
- [ ] 红包群（game/weChat）：点击红包播放激励视频并发放奖励

常见日志 TAG：`TakuAdBridge`、`TakuNativeAdComponent`、`TakuBannerComponent`。

## 九、常见问题

### 9.1 真机提示“未配置appkey或配置错误”

检查三点是否一致：
1. `AndroidManifest.xml` 中 `dcloud_appkey` 的值；
2. DCloud 后台该应用的 Android 离线 Key；
3. DCloud 后台登记的包名、SHA1、SHA256 与当前签名证书是否匹配。

### 9.2 `uni.requireNativePlugin('TakuAdBridge')` 返回 null

检查 `nativeplugins/TakuAdsNativePlugin/android/dcloud_uniplugins.json` 格式：

```json
{
    "nativePlugins": [
        {
            "plugins": [
                {
                    "type": "module",
                    "name": "TakuAdBridge",
                    "class": "com.xingye.hoshino.taku.TakuAdBridge"
                },
                {
                    "type": "component",
                    "name": "taku-native-ad",
                    "class": "com.xingye.hoshino.taku.TakuNativeAdComponent"
                },
                {
                    "type": "component",
                    "name": "taku-banner",
                    "class": "com.xingye.hoshino.taku.TakuBannerComponent"
                }
            ]
        }
    ]
}
```

必须有 `nativePlugins` 数组包裹，离线工程和自定义基座都如此。

### 9.3 广告加载失败 / onAdLoadTimeout

- 检查测试设备是否联网；
- 检查 Taku 后台对应广告位是否已启用、渠道配置是否正确；
- 检查百度联盟 / CSJ / GDT 的 App ID、广告位 ID 是否填写正确；
- Release 包首次加载可能较慢，已将开屏广告加载超时调整为 **10 秒**（默认 5 秒），如需修改请编辑 `TakuAdBridge.java` 中的 `timeoutMs`。

### 9.4 Release 打包失败：lintVitalAnalyzeRelease 下载依赖超时

在离线工程 `simpleDemo/build.gradle` 中关闭 release lint：

```gradle
lintOptions {
    checkReleaseBuilds false
    abortOnError false
}
```

### 9.5 接入优量汇 GDT 后主线程卡死 / ANR

GDT SDK 初始化耗时较长，必须把 `TakuAdBridge.init` 的 `@UniJSMethod` 标记为 `uiThread = false`。当前插件已按此实现，请勿改回主线程。

### 9.6 原生广告 / Banner 不展示

- 确认页面已使用 `<!-- #ifdef APP-PLUS -->` 条件编译；
- 确认 `dcloud_uniplugins.json` 已注册 `taku-native-ad` / `taku-banner`；
- 确认 Taku 后台该广告位已配置并启用；
- 查看日志中 `onAdFailed` 错误码，常见为 10003（无填充）、网络超时等。

## 十、已验证环境

- HBuilderX Android SDK：`5.07.82603_20260414`
- Taku SDK：`Taku_Sdk_release_China_v6.6.22_20260615112732`
- Gradle：`8.11.1`
- Android Gradle Plugin：`8.7.3`
- compileSdk：`35`
- minSdk：`24`
- targetSdk：`33`
