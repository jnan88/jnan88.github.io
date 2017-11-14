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
