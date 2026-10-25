# HBuilderX 自定义基座制作 SOP

> 适用场景：需要在 HBuilderX 中通过自定义调试基座运行 `hoshino` 项目，并加载 `TakuAdsNativePlugin` 原生插件。

## 一、前置条件

1. 本机已安装 **HBuilderX**（建议 4.x 及以上版本）。
2. HBuilderX 已登录 DCloud 账号，且该账号拥有应用 `__UNI__8969615` 的权限。
3. 项目 `hoshino` 已导入 HBuilderX。
4. 电脑已连接 Android 真机或模拟器，并开启 USB 调试。
5. 原生插件包 `hoshino/nativeplugins/TakuAdsNativePlugin/` 已存在且文件完整。

## 二、检查项目配置

### 2.1 确认 manifest.json 已声明原生插件

打开 `hoshino/manifest.json`，确认 `app-plus → nativePlugins` 中包含：

```json
"app-plus": {
    "nativePlugins": {
        "TakuAdsNativePlugin": {}
    }
}
```

> 若不存在，可在 HBuilderX 中点击 `manifest.json` → **源码视图** → 在 `app-plus` 节点下添加上述内容。

### 2.2 确认原生插件文件完整

检查以下文件是否存在：

```
hoshino/nativeplugins/TakuAdsNativePlugin/
├── package.json
├── readme.md
└── android/
    ├── dcloud_uniplugins.json
    ├── libs/
    │   ├── anythink_core_6.6.22.aar
    │   ├── anythink_adx_sdk_kuying_6.5.72_necessary.aar
    │   ├── anythink_common_util_1.1.3.aar
    │   ├── anythink_network_adx_kuying_sdk_necessary_6.5.72.1.0.aar
    │   ├── anythink_network_baidu_9.450.1.1.aar
    │   ├── Baidu_MobAds_SDK-release_v9.450.aar
    │   ├── anythink_network_csj_7.6.1.2.1.0.aar
    │   └── ads-sdk-pro-7.6.1.2.aar
    └── src/main/java/com/xingye/hoshino/taku/
        └── TakuAdBridge.java
```

## 三、制作自定义基座

### 3.1 打开菜单

在 HBuilderX 顶部菜单依次点击：

```
运行 → 运行到手机或模拟器 → 制作自定义基座
```

### 3.2 选择平台

在弹出的"制作自定义基座"窗口中：

1. **平台**：选择 **Android**。
2. **包名**：填写 `com.xingye.hoshino`。
3. **证书**：选择或填写 `D:/APP/hyt/test.keystore`：
   - 别名：`testalias`
   - 证书库密码：`Ll159357`
   - 私钥密码：`Ll159357`
4. **Android 版本号**：保持默认或按需填写（建议 `1.0.3 / 103` 与 manifest 一致）。

### 3.3 勾选原生插件

在插件列表中找到并勾选：

```
TakuAdsNativePlugin
```

> 如果没有看到该插件，请检查 `manifest.json` 中是否正确声明了 `nativePlugins`，以及插件目录结构是否正确。

### 3.4 开始打包

点击 **打包** 按钮，等待云打包完成。过程通常需要 2-10 分钟，取决于网络和队列。

打包成功后，HBuilderX 会提示：

```
自定义基座制作成功
```

并显示基座保存路径（通常在 `unpackage/debug/` 或 HBuilderX 安装目录的 `plugins/` 下）。

## 四、使用自定义基座运行

### 4.1 切换基座

在 HBuilderX 顶部菜单依次点击：

```
运行 → 运行到手机或模拟器 → 基座运行
```

### 4.2 选择刚制作的基座

在基座列表中选择刚才制作的自定义基座（名称通常包含 `TakuAdsNativePlugin` 或自定义基座 ID）。

### 4.3 启动运行

选择目标设备（已连接的 Android 真机/模拟器），点击 **运行**。

首次使用自定义基座时，HBuilderX 会先安装基座 APK，然后注入当前项目的 JS 资源并启动。

## 五、验证广告

启动后，观察手机：

1. 若出现 Taku 开屏广告并自动关闭，说明插件加载成功。
2. 在 HBuilderX 控制台或 `adb logcat` 中应看到：

```text
TakuAdBridge: Initializing Taku SDK
TakuAdBridge: Loading splash ad
TakuAdBridge: onAdLoaded
TakuAdBridge: onAdShow
TakuAdBridge: onAdDismiss
```

## 六、常见问题

### 6.1 自定义基座列表为空

- 确认已成功制作自定义基座。
- 检查 HBuilderX 登录账号是否有该应用的权限。
- 重启 HBuilderX 后再试。

### 6.2 打包提示"未配置appkey或配置错误"

- 检查 DCloud 开发者中心该应用的 Android 离线 Key 是否与证书指纹匹配。
- 自定义基座使用的是 HBuilderX 云打包，依赖 DCloud 后台配置；离线打包则依赖 `AndroidManifest.xml` 中的 `dcloud_appkey`。

### 6.3 运行时提示"TakuAdBridge 插件未找到"

- 确认制作基座时勾选了 `TakuAdsNativePlugin`。
- 确认 `nativeplugins/TakuAdsNativePlugin/android/dcloud_uniplugins.json` 格式正确：

```json
{
    "nativePlugins": [
        {
            "plugins": [
                {
                    "type": "module",
                    "name": "TakuAdBridge",
                    "class": "com.xingye.hoshino.taku.TakuAdBridge"
                }
            ]
        }
    ]
}
```

### 6.4 广告加载失败

- 检查真机是否联网。
- 检查 Taku 后台广告位 `b6a2769587c201` 是否启用、渠道是否配置。
- 开屏广告加载超时已设为 10 秒，若仍超时请检查网络或 Taku 后台配置。

## 七、与离线打包的关系

| 方式 | 用途 | 是否消耗 DCloud 打包次数 | 是否含原生插件 |
|------|------|------------------------|---------------|
| 自定义基座 | 日常 JS 调试 | 是（云打包） | 是 |
| 离线打包（debug/release） | 最终发版/本地完整验证 | 否 | 是，需手动同步 |

建议：
- **开发阶段**：使用自定义基座，修改 JS 后即时生效。
- **发版阶段**：使用 `scripts/build-offline-apk.sh release` 编译 release APK。

## 八、相关文件

- 插件目录：`hoshino/nativeplugins/TakuAdsNativePlugin/`
- 集成文档：`hoshino/docs/TAKU_INTEGRATION.md`
- 离线打包脚本：`hoshino/scripts/build-offline-apk.sh`
