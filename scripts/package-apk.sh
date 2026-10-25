#!/usr/bin/env bash
# 一键打包项目为 base.apk（生成资源 → 编译），不装机
# 用法：bash scripts/package-apk.sh [debug|release]
# 产物：项目根目录 base.apk
# 依赖：HBuilderX（未运行时会自动拉起并等待初始化）

set -e

PROJECT_DIR="D:/data/liuyueliu/hoshino/hoshino"
CLI="C:/Users/PC刘安波/Desktop/HBuilderX.5.07.2026041006/HBuilderX/cli.exe"
BUILD_TYPE="${1:-debug}"

cd "$PROJECT_DIR"

# HBuilderX 未运行时先拉起（publish 依赖已打开的 HBuilderX）
if ! tasklist 2>/dev/null | grep -qi "HBuilderX.exe"; then
    echo "=== 启动 HBuilderX ==="
    "$CLI" open
    echo "等待 HBuilderX 初始化..."
    sleep 15
fi

echo "=== [1/2] 生成本地打包资源（约 30-60 秒）==="
"$CLI" publish app --type appResource --project "$PROJECT_DIR"

echo "=== [2/2] 编译 $BUILD_TYPE APK ==="
bash scripts/build-offline-apk.sh "$BUILD_TYPE"

echo ""
echo "打包完成：$PROJECT_DIR/base.apk"
