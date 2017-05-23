## JMX
```
-Dcom.sun.management.jmxremote -Djava.rmi.server.hostname=ip -Dcom.sun.management.jmxremote.port=port -Dcom.sun.management.jmxremote.authenticate=false -Dcom.sun.management.jmxremote.ssl=false
```

## VM
```
-server -Xms2g -Xmx2g -XX:NewSize=512m -XX:MaxNewSize=1g -XX:+AggressiveOpts -XX:+UseBiasedLocking -XX:+DisableExplicitGC -XX:MaxTenuringThreshold=31 -XX:+UseConcMarkSweepGC -XX:+UseParNewGC -XX:+CMSParallelRemarkEnabled -XX:+UseCMSCompactAtFullCollection -XX:+UseFastAccessorMethods -XX:+UseCMSInitiatingOccupancyOnly -Djava.awt.headless=true
```
## VM case2（6G）
```
-Xms4g
-Xmx4g
-XX:NewRatio=1
-XX:OldSize=64m
-XX:MetaspaceSize=256m
-XX:MaxMetaspaceSize=512m
-XX:MaxDirectMemorySize=4g
-XX:ReservedCodeCacheSize=240M
-XX:AutoBoxCacheMax=20000
-XX:+UseConcMarkSweepGC
-XX:CMSInitiatingOccupancyFraction=75
-XX:+UseCMSInitiatingOccupancyOnly
-XX:MaxTenuringThreshold=6
-XX:+ExplicitGCInvokesConcurrent
-XX:+ParallelRefProcEnabled
-Xloggc:/www/logs/gc-www.log
-XX:ErrorFile=/www/logs/hs_err_%p.log
-Xnoclassgc
-XX:-OmitStackTraceInFastThrow
-XX:+HeapDumpOnOutOfMemoryError
-XX:HeapDumpPath=/www/logs/
-XX:+PrintGCDetails
-XX:+PrintGCDateStamps
-XX:+PrintHeapAtGC
-XX:+PrintTenuringDistribution
-XX:+PrintGCApplicationStoppedTime
-XX:+PrintPromotionFailure
-XX:+UseGCLogFileRotation
-XX:NumberOfGCLogFiles=10
-XX:GCLogFileSize=100M
-Dcom.sun.management.jmxremote
-Djava.rmi.server.hostname=192.168.1.1
-Dcom.sun.management.jmxremote.port=1099
-Dcom.sun.management.jmxremote.authenticate=false
-Dcom.sun.management.jmxremote.ssl=false
```
## VM case（2G）
```
-Xms1g
-Xmx2g
-XX:NewRatio=1
#-XX:OldSize=64m
-XX:MetaspaceSize=256m
-XX:MaxMetaspaceSize=512m
-XX:MaxDirectMemorySize=2g
-XX:ReservedCodeCacheSize=240M
-XX:AutoBoxCacheMax=20000
-XX:+UseConcMarkSweepGC
-XX:CMSInitiatingOccupancyFraction=75
-XX:+UseCMSInitiatingOccupancyOnly
-XX:MaxTenuringThreshold=6
-XX:+ExplicitGCInvokesConcurrent
-XX:+ParallelRefProcEnabled
-Xloggc:/www/logs/gc-www.log
-XX:ErrorFile=/www/logs/hs_err_%p.log
-Xnoclassgc
-XX:-OmitStackTraceInFastThrow
-XX:+HeapDumpOnOutOfMemoryError
-XX:HeapDumpPath=/www/logs/
-XX:+PrintGCDetails
-XX:+PrintGCDateStamps
-XX:+PrintHeapAtGC
-XX:+PrintTenuringDistribution
-XX:+PrintGCApplicationStoppedTime
-XX:+PrintPromotionFailure
-XX:+UseGCLogFileRotation
-XX:NumberOfGCLogFiles=10
-XX:GCLogFileSize=500M
#-Dcom.sun.management.jmxremote
#-Djava.rmi.server.hostname=192.168.1.1
#-Dcom.sun.management.jmxremote.port=1099
#-Dcom.sun.management.jmxremote.authenticate=false
#-Dcom.sun.management.jmxremote.ssl=false
```

## VM case3
```
-server #64位机器下默认
-Xms6000M #最小堆大小，相当于NewSize
-Xmx6000M #最大堆大小，相当于MaxNewSize
-Xmn500M #新生代大小
-XX:NewRatio=1 #新生代占堆大小的1/3，1表示对半
-XX:OldSize=64m #64位默认约5m
-Xss256K #栈大小，默认每条线程为1M（以前是256K），有JSON解析之类的递归调用时不能设太小
-XX:PermSize=500M (JDK7)
-XX:MaxPermSize=500M (JDK7)
-XX:MetaspaceSize=128m  （JDK8）
-XX:MaxMetaspaceSize=512m（JDK8）
-XX:SurvivorRatio=65536
-XX:MaxTenuringThreshold=0 #晋升到老年代需要的存活次数,设置为0时，survivor区失去作用，一次minor gc，eden中存活的对象就会进入老年代，默认是15，使用CMS时默认是4
-Xnoclassgc #不做类的gc
#-XX:+PrintCompilation #输出jit编译情况，慎用
-XX:+TieredCompilation #启用多层编译，jd8默认开启
-XX:CICompilerCount=4 #编译器数目增加
-XX:-UseBiasedLocking #取消偏向锁
-XX:AutoBoxCacheMax=20000 #自动装箱的缓存数量，如int默认缓存为-128~127
-Djava.security.egd=file:/dev/./urandom #替代默认的/dev/random阻塞生成因子
-XX:+AlwaysPreTouch #启动时访问并置零内存页面，大堆时效果比较好
-XX:-UseCounterDecay #禁止JIT调用计数器衰减。默认情况下，每次GC时会对调用计数器进行砍半的操作，导致有些方法一直是个温热，可能永远都达不到C2编译的1万次的阀值。
-XX:ParallelRefProcEnabled=true # 默认为false，并行的处理Reference对象，如WeakReference
-XX:+DisableExplicitGC #此参数会影响使用堆外内存，会造成oom，如果使用NIO,请慎重开启
#-XX:+UseParNewGC #此参数其实在设置了cms后默认会启用，可以不用设置
-XX:+UseConcMarkSweepGC #使用cms垃圾回收器
#-XX:+UseCMSCompactAtFullCollection #是否在fullgc是做一次压缩以整理碎片，默认启用
-XX:CMSFullGCsBeforeCompaction=0 #full gc触发压缩的次数
#-XX:+CMSClassUnloadingEnabled #如果类加载不频繁，也没有大量使用String.intern方法，不建议打开此参数，况且jdk7后string pool已经移动到了堆中。开启此项的话，即使设置了Xnoclassgc也会进行class的gc, 但是某种情况下会造成bug：https://www.google.com/url?sa=t&rct=j&q=&esrc=s&source=web&cd=5&cad=rja&uact=8&ved=0ahUKEwjR16Wf6MHQAhWLrVQKHfLdCe4QFgg8MAQ&url=https%3A%2F%2Fblogs.oracle.com%2Fpoonam%2Fentry%2Fjvm_hang_with_cms_collector&usg=AFQjCNFNtkw6jHM-uyz-Wjri3LtAVXWJ8g&sig2=BFxSfHc-AIek18fEhY07mg。
#-XX:+CMSParallelRemarkEnabled #并行标记, 默认开启, 可以不用设置
#-XX:+CMSScavengeBeforeRemark #强制remark之前开始一次minor gc，减少remark的暂停时间，但是在remark之后也将立即开始又一次minor gc
-XX:CMSInitiatingOccupancyFraction=90 #触发full gc的内存使用百分比
-XX:+PrintClassHistogram #打印类统计信息
-XX:+PrintHeapAtGC #打印gc前后的heap信息
-XX:+PrintGCDetails #以下都是为了记录gc日志
-XX:+PrintGCDateStamps
-XX:+PrintGCApplicationStoppedTime #打印清晰的GC停顿时间外，还可以打印其他的停顿时间，比如取消偏向锁，class 被agent redefine，code deoptimization等等
-XX:+PrintTenuringDistribution #打印晋升到老年代的年龄自动调整的情况(并行垃圾回收器启用UseAdaptiveSizePolicy参数的情况下以及其他垃圾回收期也会动态调整，从最开始的MaxTenuringThreshold变成占用当前堆50%的age)
#-XX:+UseAdaptiveSizePolicy # 此参数在并行回收器时是默认开启的会根据应用运行状况做自我调整，如MaxTenuringThreshold、survivor区大小等，其他情况下最好不要开启
#-XX:StringTableSize #字符串常量池表大小(hashtable的buckets的数目)，java 6u30之前无法修改固定为1009，后面的版本默认为60013，可以通过此参数设置
-XX:GCTimeLimit=98 #gc占用时间超过多少抛出OutOfMemoryError
-XX:GCHeapFreeLimit=2 #gc回收后小于百分之多少抛出OutOfMemoryError
-Xloggc:/home/logs/gc.log
```
