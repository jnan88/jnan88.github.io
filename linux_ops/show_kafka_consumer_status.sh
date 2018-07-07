#!/bin/bash
# --------------------------------
#描述：查看Kafka的各个队列消费状态
#适用版本：kafka-2.10.x-0.10.x
#作者：jnan88@qq.com
#版本：0.0.1
#最后更新时间：2018-07-07
# --------------------------------
zk_server="localhost:2181"
kafka_server="localhost:9092"
#
topics=$(./bin/kafka-consumer-groups.sh --list --zookeeper ${zk_server})
for topic in ${topics}
do
echo "----------- group = ${topic} ------------------"
./bin/kafka-consumer-groups.sh --zookeeper ${zk_server} --describe --group ${topic} | awk '{printf("%-20s %-30s %-10s %-10s %-10s %-10s \n",$1,$2,$3,$4,$5,$6)}'
done
#
topics=$(./bin/kafka-consumer-groups.sh --list --bootstrap-server ${kafka_server})
for topic in ${topics}
do
echo "----------- group = ${topic} ------------------"
./bin/kafka-consumer-groups.sh --bootstrap-server ${kafka_server} --describe --group ${topic} | awk '{printf("%-20s %-30s %-10s %-10s %-10s %-10s \n",$1,$2,$3,$4,$5,$6)}'
done
