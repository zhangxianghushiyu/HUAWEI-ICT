#!/usr/bin/env bash
# 命令行编译 HBuilder-Integrate-AS 离线打包 APK
# 用法：bash scripts/build-offline-apk.sh [debug|release]

set -e

SCRIPT_DIR="$(cd "$(dirname "$0")" && pwd)"
PROJECT_DIR="$(dirname "$SCRIPT_DIR")"
SDK_DIR="D:/data/liuyueliu/Android-SDK@5.24.82669_20260813"
GRADLE_DIR="$SDK_DIR/HBuilder-Integrate-AS"
APPID="__UNI__8969615"
WWW_SRC="$PROJECT_DIR/unpackage/resources/$APPID/www"
WWW_DST="$GRADLE_DIR/simpleDemo/src/main/assets/apps/$APPID/www"

BUILD_TYPE="${1:-debug}"
if [ "$BUILD_TYPE" != "debug" ] && [ "$BUILD_TYPE" != "release" ]; then
    echo "用法：bash scripts/build-offline-apk.sh [debug|release]"
    exit 1
fi

# 检查本地打包资源是否存在
if [ ! -d "$WWW_SRC" ]; then
    echo "错误：未找到本地打包资源目录：$WWW_SRC"
    echo "请先在 HBuilderX 中执行：发行 -> 原生 App-本地打包 -> 生成本地打包 App 资源"
    exit 1
fi

# 优先使用 JDK 17（AGP 8.7 需要 Java 11+）
if [ -d "/d/develop/jdk17" ]; then
    export JAVA_HOME=/d/develop/jdk17
    export PATH="$JAVA_HOME/bin:$PATH"
elif [ -d "/d/java/jdk17" ]; then
    export JAVA_HOME=/d/java/jdk17
    export PATH="$JAVA_HOME/bin:$PATH"
elif [ -d "/d/java/jdk-17" ]; then
    export JAVA_HOME=/d/java/jdk-17
    export PATH="$JAVA_HOME/bin:$PATH"
fi

echo "=== 编译离线打包 APK [$BUILD_TYPE] ==="
echo "JAVA_HOME: $JAVA_HOME"
echo "Gradle: $GRADLE_DIR"
echo ""

# 同步前端资源到 Android 工程
echo "=== 同步前端资源 ==="
echo "源：$WWW_SRC"
echo "目标：$WWW_DST"
rm -rf "$WWW_DST"
mkdir -p "$WWW_DST"
cp -r "$WWW_SRC"/* "$WWW_DST"/
echo "资源同步完成：$(find "$WWW_DST" -type f | wc -l) 个文件"
echo ""

# 同步 App 图标（mipmap）到 Android 工程，确保安装后图标与仓库 static/icons 一致
# 若后续更换图标，请只修改 static/icons/*，此步骤会自动同步；勿手改离线工程 res/。
echo "=== 同步 App 图标 ==="
RES_DIR="$GRADLE_DIR/simpleDemo/src/main/res"
ICON_SRC_DIR="$PROJECT_DIR/static/icons"
declare -A ICON_MAP=(
  ["$ICON_SRC_DIR/72x72.png"]="mipmap-hdpi/icon.png"
  ["$ICON_SRC_DIR/96x96.png"]="mipmap-xhdpi/icon.png"
  ["$ICON_SRC_DIR/144x144.png"]="mipmap-xxhdpi/icon.png"
  ["$ICON_SRC_DIR/192x192.png"]="mipmap-xxxhdpi/icon.png"
)
for src in "${!ICON_MAP[@]}"; do
  dst="$RES_DIR/${ICON_MAP[$src]}"
  if [ -f "$src" ]; then
    if [ -d "$RES_DIR" ]; then
      cp -f "$src" "$dst"
      echo "图标同步：$(basename "$src") -> ${ICON_MAP[$src]}"
    else
      echo "警告：未找到 Android res 目录 $RES_DIR，跳过图标同步"
    fi
  else
    echo "警告：图标源不存在 $src，请检查 static/icons"
  fi
done
echo "图标同步完成"
echo ""

# 同步广告/网络 aar：libs 只保留白名单，避免下次从 SDK/libs 重拷时冲突 aar 复发
# 白名单是唯一事实源；新增/删减广告网络时只改这里，勿手改 libs/
echo "=== 同步广告 aar（白名单） ==="
SDK_LIBS="$SDK_DIR/SDK/libs"
LIBS_DIR="$GRADLE_DIR/simpleDemo/libs"

AAR_KEEP=(
  # 工程基础 aar（HBuilder-Integrate-AS 模板自带）
  "android-gif-drawable-1.2.29.aar"
  "breakpad-build-release.aar"
  "lib.5plus.base-release.aar"
  "media-release.aar"
  "oaid_sdk_1.0.25.aar"
  "uniapp-v8-release.aar"
  "weex_videoplayer-release.aar"
  # uni-ad 核心
  "ads-release.aar"
  "weex_ad-release.aar"
  # 穿山甲 GroMore(gm)
  "ads-gromore-release.aar"
  "open_ad_sdk.aar"
  # 优量汇(gdt)
  "ads-gdt-release.aar"
  "GDTSDK.unionNormal.aar"
  # 快手内容(ks-content)
  "ads-ks-content-release.aar"
  "kssdk-allad-content.aar"
  # Sigmob
  "ads-sigmob-release.aar"
  # 掌游 Octopus(zy)
  "uniad-zy-release.aar"
  "octopus_ad_sdk_2.6.5.18.aar"
  # 泛连 Funlink(fl)
  "Funlink_29003_20260507_release.aar"
  "funlink_29104_20260428_release.aar"
  "Funlink_adapter_fl_29003_20260507_release.aar"
  "Funlink_adapter_uniad_2.9.0_77278214.aar"
  # 华夏乐游(yt)
  "uniad-custom-yt-4.7.5.aar"
  "adalliance_adn_sdk.4.7.8.1.aar"
  # 佳投 Advista(jt)
  "advista-1.9.2-release.aar"
  "advista-uniad-adapter-1.0.4-release.aar"
  # 旺脉(wa)
  "ads-wm-release.aar"
  "wm_ad_sdk_7.9.17.19_android.aar"
)

declare -A AAR_KEEP_MAP
for name in "${AAR_KEEP[@]}"; do
  AAR_KEEP_MAP["$name"]=1
done

# 1) 删除 libs 里存在于 SDK/libs 但不在白名单的 aar（清理重拷进来的冲突 aar）
for aar in "$LIBS_DIR"/*.aar; do
  [ -e "$aar" ] || continue
  name="$(basename "$aar")"
  if [ -f "$SDK_LIBS/$name" ] && [ -z "${AAR_KEEP_MAP[$name]:-}" ]; then
    rm -f "$aar"
    echo "移除多余 aar：$name"
  fi
done

# 2) 从 SDK/libs 拷贝白名单 aar（缺失或版本更新时覆盖）
for name in "${AAR_KEEP[@]}"; do
  if [ -f "$SDK_LIBS/$name" ]; then
    cp -f "$SDK_LIBS/$name" "$LIBS_DIR/$name"
    echo "拷贝 aar：$name"
  else
    echo "警告：SDK/libs 里找不到 $name"
  fi
done
echo "广告 aar 同步完成"
echo ""

cd "$GRADLE_DIR"
./gradlew :simpleDemo:clean :simpleDemo:assemble${BUILD_TYPE^} --no-daemon -x lint

APK_PATH="$GRADLE_DIR/simpleDemo/build/outputs/apk/$BUILD_TYPE/simpleDemo-$BUILD_TYPE.apk"
echo "=== 编译完成 ==="
echo "APK: $APK_PATH"
ls -lh "$APK_PATH"

# 复制到项目根目录方便安装
cp "$APK_PATH" "$PROJECT_DIR/base.apk"
echo "已复制到：$PROJECT_DIR/base.apk"
