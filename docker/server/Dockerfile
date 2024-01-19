#FROM eclipse-temurin:8-jre
FROM williamyeh/java8
LABEL authors="安惠科技"

## 创建目录，并使用它作为工作目录
RUN mkdir -p /d3code-server
WORKDIR /d3code-server
RUN mkdir -p /d3code-server/config
## 将后端项目的 Jar 文件，复制到镜像中
COPY d3code-admin.jar app.jar
COPY lib lib
COPY config config
## 设置 TZ 时区
ENV TZ=Asia/Shanghai
## 设置 JAVA_OPTS 环境变量，可通过 docker run -e "JAVA_OPTS=" 进行覆盖
ENV JAVA_OPTS="-Xms512m -Xmx1024m -XX:MetaspaceSize=128m -XX:MaxMetaspaceSize=512m -XX:+HeapDumpOnOutOfMemoryError -XX:+PrintGCDateStamps  -XX:+PrintGCDetails -XX:NewRatio=1 -XX:SurvivorRatio=30 -XX:+UseParallelGC -XX:+UseParallelOldGC -Djava.security.egd=file:/dev/./urandom"
ENV APPNAME="D3Code"

## 应用参数
ENV ARGS="--spring.profiles.active=prod"

## 暴露后端项目的 48080 端口
EXPOSE 8080

## 启动后端项目
CMD java -Dloader.path=lib -Dname=${APPNAME} ${JAVA_OPTS} -jar app.jar $ARGS
