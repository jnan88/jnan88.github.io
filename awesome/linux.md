## 文件同步
> rsync -zvrtopgl --progress from to

## shell 随机字符串
> openssl rand -base64 [size]  # openssl rand -base64 32

## awk分组记数
> awk '{a[$0]+=1}END{for(i in a) {printf "%s %d\n",substr(i,0,16),a[i]}}'
