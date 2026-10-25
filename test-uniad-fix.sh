#!/bin/bash
set -e

PROJECT_DIR="D:/data/liuyueliu/hoshino/hoshino"
ADB="D:/develop/jdk17/platform-tools/adb"
CLI="C:/Users/PC刘安波/Desktop/HBuilderX.5.07.2026041006/HBuilderX/cli.exe"

cd "$PROJECT_DIR"

echo "=== 1. 卸载旧 App ==="
$ADB uninstall com.xingye.hoshino || true

echo "=== 2. 绕过 vivo 安装确认 ==="
$ADB shell settings put global adb_install_need_confirm 0

echo "=== 3. 生成本地打包资源（约 30-60 秒）==="
"$CLI" publish app-android --type appResource --project "$PROJECT_DIR"

echo "=== 4. 同步前端资源到离线工程（已移除 Taku 插件） ==="
bash scripts/sync-plugin-to-offline.sh

echo "=== 5. 编译 debug APK ==="
bash scripts/build-offline-apk.sh debug

echo "=== 6. 安装 APK ==="
$ADB install -r base.apk

echo "=== 7. 拉起 App ==="
$ADB shell monkey -p com.xingye.hoshino -c android.intent.category.LAUNCHER 1 || true
echo "安装完成，App 已拉起。"
