#!/bin/sh
basePath=$(cd `dirname $0`; pwd)

echo "开始启动solo>>>>>>>>>>>>>>>>"

nohup java  -Xmx128m -Xms128m -XX:+PrintGC -Xloggc:$basePath/logs/gc.log -XX:ErrorFile=$basePath/../logs/java_error_%p.log -jar "$basePath/solo.jar"   > "$basePath/logs/solo.log" 2>&1 &


echo "日志目录： $basePath/logs/solo.log"

tail -f "$basePath/logs/solo.log"