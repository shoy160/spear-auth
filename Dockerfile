FROM hub.app-chengdu1.yunzhicloud.com/library/java:8-alpine
ADD *-web/target/*.jar ./${APP_JAR_PATH}