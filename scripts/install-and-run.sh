#!/usr/bin/env bash
# 安装并启动离线打包 APK，同时抓取 uni-ad 与广告相关日志
# 用法：bash scripts/install-and-run.sh [debug|release]

set -e

SCRIPT_DIR="$(cd "$(dirname "$0")" && pwd)"
PROJECT_DIR="$(dirname "$SCRIPT_DIR")"
SDK_DIR="D:/data/liuyueliu/Android-SDK@5.24.82669_20260813"
GRADLE_DIR="$SDK_DIR/HBuilder-Integrate-AS"
BUILD_TYPE="${1:-debug}"
APK_PATH="$GRADLE_DIR/simpleDemo/build/outputs/apk/$BUILD_TYPE/simpleDemo-$BUILD_TYPE.apk"
ADB="D:/develop/jdk17/platform-tools/adb"

if [ ! -f "$APK_PATH" ]; then
    echo "错误：APK 不存在 $APK_PATH，请先执行 build-offline-apk.sh"
    exit 1
fi

echo "=== 安装并运行 [$BUILD_TYPE] ==="
$ADB install -r "$APK_PATH"
$ADB logcat -c
$ADB shell am force-stop com.xingye.hoshino
sleep 1
$ADB shell am start -n com.xingye.hoshino/io.dcloud.PandoraEntry

echo "=== 等待 15 秒后抓取日志 ==="
sleep 15
$ADB logcat -d 2>&1 | grep -E 'uni-ad|AdUnit|onAdLoaded|onAdLoad|onError|onAdShow|onAdClick|onAdClose|onAdDismiss|onVideo|onReward|App Launch|App Show|JSException|jsLog|createRewardedVideoAd|createInterstitialAd|createBannerAd|ad-splash' | tail -80
