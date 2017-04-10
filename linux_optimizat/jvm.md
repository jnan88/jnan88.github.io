## JMX
```
-Dcom.sun.management.jmxremote -Djava.rmi.server.hostname=ip -Dcom.sun.management.jmxremote.port=port -Dcom.sun.management.jmxremote.authenticate=false -Dcom.sun.management.jmxremote.ssl=false
```

## VM
```
-server -Xms2g -Xmx2g -XX:NewSize=512m -XX:MaxNewSize=1g -XX:+AggressiveOpts -XX:+UseBiasedLocking -XX:+DisableExplicitGC -XX:MaxTenuringThreshold=31 -XX:+UseConcMarkSweepGC -XX:+UseParNewGC -XX:+CMSParallelRemarkEnabled -XX:+UseCMSCompactAtFullCollection -XX:+UseFastAccessorMethods -XX:+UseCMSInitiatingOccupancyOnly -Djava.awt.headless=true
```
