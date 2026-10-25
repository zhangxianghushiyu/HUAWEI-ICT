FROM openjdk:8-jdk-alpine

# 设置工作目录
WORKDIR /app

# 复制 JAR 包到容器中
COPY target/polymer-platform.jar app.jar

# 暴露端口
EXPOSE 9521

# 设置 JVM 参数
ENV JAVA_OPTS="-Xms512m -Xmx1024m -Djava.security.egd=file:/dev/./urandom"

# 启动应用
ENTRYPOINT ["sh", "-c", "java $JAVA_OPTS -jar app.jar"]