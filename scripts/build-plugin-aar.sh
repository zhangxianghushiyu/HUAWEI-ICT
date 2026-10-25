#!/usr/bin/env bash
# 构建 TakuAdsNativePlugin.aar（仅插件类），发布到插件 android/ 根目录，
# 供 HBuilderX 云打包（自定义基座）自动引用。
# 用法：bash scripts/build-plugin-aar.sh
set -e

SCRIPT_DIR="$(cd "$(dirname "$0")" && pwd)"
PROJECT_DIR="$(dirname "$SCRIPT_DIR")"
SDK_DIR="D:/data/liuyueliu/Android-SDK@5.24.82669_20260813"
GRADLE_DIR="$SDK_DIR/HBuilder-Integrate-AS"
MODULE_DIR="$GRADLE_DIR/takuads"
PLUGIN_ANDROID="$PROJECT_DIR/nativeplugins/TakuAdsNativePlugin/android"
PLUGIN_SRC="$PLUGIN_ANDROID/src/main/java/com/xingye/hoshino/taku"
MODULE_SRC="$MODULE_DIR/src/main/java/com/xingye/hoshino/taku"
OFFLINE_LIBS="$GRADLE_DIR/simpleDemo/libs"
OUT_AAR="$MODULE_DIR/build/outputs/aar/takuads-release.aar"

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

if [ ! -d "$GRADLE_DIR" ]; then
    echo "错误：离线工程不存在 $GRADLE_DIR"
    exit 1
fi

echo "=== 构建 TakuAdsNativePlugin.aar ==="
echo "JAVA_HOME: $JAVA_HOME"

# 1) 同步第三方 AAR 到离线工程 libs（takuads 模块的编译期依赖）
echo "[1/4] 同步第三方 AAR..."
mkdir -p "$OFFLINE_LIBS"
cp -v "$PLUGIN_ANDROID"/anythink_*.aar "$PLUGIN_ANDROID"/ads-sdk-pro-*.aar \
      "$PLUGIN_ANDROID"/Baidu_*.aar "$PLUGIN_ANDROID"/GDTSDK*.aar "$OFFLINE_LIBS/"

# 2) 确保 takuads 模块存在（模块文件在离线工程内，不入仓库）
echo "[2/4] 准备 takuads 模块..."
mkdir -p "$MODULE_SRC"
cat > "$MODULE_DIR/build.gradle" <<'EOF'
apply plugin: 'com.android.library'

android {
    compileSdkVersion 35
    buildToolsVersion '36.0.0'
    namespace 'com.xingye.hoshino.taku'
    defaultConfig {
        minSdkVersion 21
        versionCode 1
        versionName "1.0.0"
        compileOptions {
            sourceCompatibility JavaVersion.VERSION_1_8
            targetCompatibility JavaVersion.VERSION_1_8
        }
    }
}

dependencies {
    compileOnly fileTree(dir: '../simpleDemo/libs', include: [
        'anythink_*.aar',
        'ads-sdk-pro-*.aar',
        'Baidu_*.aar',
        'GDTSDK*.aar'
    ])
    compileOnly files('../simpleDemo/libs/uniapp-v8-release.aar')
    compileOnly files('../simpleDemo/libs/lib.5plus.base-release.aar')
    implementation 'com.alibaba:fastjson:1.2.83'
    implementation 'com.github.bumptech.glide:glide:4.9.0'
}
EOF
cat > "$MODULE_DIR/src/main/AndroidManifest.xml" <<'EOF'
<manifest xmlns:android="http://schemas.android.com/apk/res/android" />
EOF
if ! grep -q "':takuads'" "$GRADLE_DIR/settings.gradle"; then
    echo "include ':takuads'" >> "$GRADLE_DIR/settings.gradle"
fi

# 3) 拷贝插件源码到 takuads 模块
echo "[3/4] 拷贝插件源码..."
rm -rf "$MODULE_SRC"
mkdir -p "$MODULE_SRC"
cp -v "$PLUGIN_SRC"/*.java "$MODULE_SRC/"

# 4) 编译插件 AAR 并发布到插件目录
echo "[4/4] 编译插件 AAR..."
cd "$GRADLE_DIR"
./gradlew :takuads:assembleRelease --no-daemon

cp -v "$OUT_AAR" "$PLUGIN_ANDROID/TakuAdsNativePlugin.aar"
echo "=== 完成：$PLUGIN_ANDROID/TakuAdsNativePlugin.aar ==="
