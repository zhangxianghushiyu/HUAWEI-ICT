#!/usr/bin/env bash
# 同步前端资源到离线打包工程（已移除 Taku 原生插件，改用 uni-ad 模块）
# 用法：在 Git Bash 中执行 bash scripts/sync-plugin-to-offline.sh

set -e

SCRIPT_DIR="$(cd "$(dirname "$0")" && pwd)"
PROJECT_DIR="$(dirname "$SCRIPT_DIR")"
SDK_DIR="D:/data/liuyueliu/Android-SDK@5.24.82669_20260813"
OFFLINE_DIR="$SDK_DIR/HBuilder-Integrate-AS/simpleDemo"
APP_ID="__UNI__8969615"

if [ ! -d "$OFFLINE_DIR" ]; then
    echo "错误：离线工程不存在 $OFFLINE_DIR"
    exit 1
fi

echo "=== 同步前端资源到离线打包工程 ==="
echo "项目目录: $PROJECT_DIR"
echo "离线工程: $OFFLINE_DIR"

# 清理旧 Taku 原生插件残留（如离线工程中仍有上次同步的 Taku 源码/aar/注册表）
echo "[1/3] 清理旧 Taku 插件残留..."
rm -rf "$OFFLINE_DIR/src/main/java/com/xingye/hoshino/taku"
rm -f "$OFFLINE_DIR/src/main/assets/dcloud_uniplugins.json"
# 清理离线工程 libs 中已知的 Taku 相关 aar（保留其他第三方 aar）
for aar in \
    "$OFFLINE_DIR/libs/anythink_"*.aar \
    "$OFFLINE_DIR/libs/ads-sdk-pro-"*.aar \
    "$OFFLINE_DIR/libs/Baidu_MobAds_"*.aar \
    "$OFFLINE_DIR/libs/GDTSDK"*.aar \
    "$OFFLINE_DIR/libs/anythink_common_util_"*.aar \
    "$OFFLINE_DIR/libs/anythink_adx_"*.aar \
    "$OFFLINE_DIR/libs/anythink_network_"*.aar
 do
    if [ -f "$aar" ]; then
        echo "删除旧 Taku aar: $aar"
        rm -f "$aar"
    fi
done
echo "清理完成"

# 同步应用资源（如果已导出）
echo "[2/4] 同步应用资源..."
WWW_SRC="$PROJECT_DIR/unpackage/resources/$APP_ID/www"
WWW_DST="$OFFLINE_DIR/src/main/assets/apps/$APP_ID/www"
if [ -d "$WWW_SRC" ]; then
    rm -rf "$WWW_DST"
    mkdir -p "$WWW_DST"
    cp -r "$WWW_SRC/"* "$WWW_DST/"
    echo "已同步 $WWW_SRC -> $WWW_DST"
else
    echo "警告：未找到导出的应用资源 $WWW_SRC，请先使用 HBuilderX 生成本地打包 App 资源"
fi

# 同步 HBuilderX 生成的插件注册表（dcloud_uniplugins.json）
echo "[3/4] 同步插件注册表..."
PLUGIN_JSON_SRC="$PROJECT_DIR/unpackage/resources/$APP_ID/dcloud_uniplugins.json"
PLUGIN_JSON_DST="$OFFLINE_DIR/src/main/assets/dcloud_uniplugins.json"
if [ -f "$PLUGIN_JSON_SRC" ]; then
    cp -f "$PLUGIN_JSON_SRC" "$PLUGIN_JSON_DST"
    echo "已同步 $PLUGIN_JSON_SRC -> $PLUGIN_JSON_DST"
else
    echo "警告：未找到插件注册表 $PLUGIN_JSON_SRC，跳过"
fi

# 同步 App 图标（mipmap），保证打包生成的 APK 图标与仓库 static/icons 一致
ICON_SRC="$PROJECT_DIR/static/icons"
RES_DIR="$OFFLINE_DIR/src/main/res"
echo "[4/4] 同步 App 图标..."
for entry in "72x72.png:mipmap-hdpi" "96x96.png:mipmap-xhdpi" "144x144.png:mipmap-xxhdpi" "192x192.png:mipmap-xxxhdpi"; do
    file="${entry%%:*}"; dir="${entry##*:}"
    if [ -f "$ICON_SRC/$file" ] && [ -d "$RES_DIR/$dir" ]; then
        cp -f "$ICON_SRC/$file" "$RES_DIR/$dir/icon.png"
        echo "图标同步：$file -> $dir/icon.png"
    else
        echo "警告：图标源或目标目录不存在 ($file / $dir)，跳过"
    fi
done
echo "图标同步完成"

echo "=== 同步完成 ==="
