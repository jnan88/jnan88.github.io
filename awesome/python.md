## python函数执行用时分析
```
def test_py():
    xxxxx
import sys,line_profiler
profiler = line_profiler.LineProfiler(test_py)
profiler.enable()
test_py()
profiler.disable()
profiler.print_stats(sys.stdout)
```
## 时间操作
```
import time

format_cn="%Y-%m-%d %H:%M:%S"
str_time=time.strftime(format_cn, time.localtime())
print("本地时间为 :",str_time)
long_time=time.mktime(time.strptime(str_time,format_cn))
print("本地时间为 :",long_time)
localtime = time.localtime(long_time)
print ("本地时间为 :", localtime)
localtime = time.asctime(localtime)
print ("本地时间为 :", localtime)
```
