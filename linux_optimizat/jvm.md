## JMX
```
-Dcom.sun.management.jmxremote -Djava.rmi.server.hostname=ip -Dcom.sun.management.jmxremote.port=port -Dcom.sun.management.jmxremote.authenticate=false -Dcom.sun.management.jmxremote.ssl=false
```

## VM
```
-server -Xms2g -Xmx2g -XX:NewSize=512m -XX:MaxNewSize=1g -XX:+AggressiveOpts -XX:+UseBiasedLocking -XX:+DisableExplicitGC -XX:MaxTenuringThreshold=31 -XX:+UseConcMarkSweepGC -XX:+UseParNewGC -XX:+CMSParallelRemarkEnabled -XX:+UseCMSCompactAtFullCollection -XX:+UseFastAccessorMethods -XX:+UseCMSInitiatingOccupancyOnly -Djava.awt.headless=true
```
## VM2
```
-Xms1g
-Xmx4g
-XX:NewSize=256m
-XX:MaxNewSize=512m
-XX:MetaspaceSize=256m
-XX:MaxMetaspaceSize=512m
-XX:AutoBoxCacheMax=20000
-Xloggc:/www/logs/gc-www.log
-XX:+HeapDumpOnOutOfMemoryError
-XX:+PrintGCDetails
-XX:+PrintGCDateStamps
-XX:+PrintHeapAtGC
-XX:+PrintTenuringDistribution
-XX:+PrintGCApplicationStoppedTime
-XX:+PrintPromotionFailure
-XX:+UseGCLogFileRotation
-XX:NumberOfGCLogFiles=10
-XX:GCLogFileSize=10M
```

