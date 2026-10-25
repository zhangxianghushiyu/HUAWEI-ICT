#!/usr/bin/env bash
# 打包最新前端代码为 debug APK 并安装到真机
# 步骤：HBuilderX 生成资源 → 同步插件/资源 → gradle 编译 → adb 安装
set -e
DUNE="C:/Users/PC刘安波/Desktop/HBuilderX.5.07.2026041006/HBuilderX/cli.exe"
PROJECT="D:/data/liuyueliu/hoshino/hoshino"

echo "=== [1/4] HBuilderX 生成打包资源 ==="
"$DUNE" publish app-android --type appResource --project "$PROJECT"
echo "=== [2/4] 同步插件与资源 ==="
bash "$PROJECT/scripts/sync-plugin-to-offline.sh"
echo "=== [3/4] 编译 debug APK ==="
bash "$PROJECT/scripts/build-offline-apk.sh" debug
echo "=== [4/4] 安装到真机 ==="
bash "$PROJECT/scripts/install-and-run.sh" debug
echo "=== 打包完成 ==="