## 日志采集
1. Flume：http://flume.apache.org/

## Elasticsearch ——为云构建的分布式RESTful搜索引擎。

ElasticSearch是基于Lucene的搜索服务器。它提供了分布式多用户能力的全文搜索引擎，基于RESTful web接口。Elasticsearch是用Java开发的，并作为Apache许可条款下的开放源码发布，是比较流行的企业级搜索引擎。

ElasticSearch不仅是一个全文本搜索引擎，还是一个分布式实时文档存储，其中每个field均是被索引的数据且可被搜索;也是一个带实时分析功能的分布式搜索引擎，并且能够扩展至数以百计的服务器存储及处理PB级的数据。ElasticSearch在底层利用Lucene完成其索引功能，因此其许多基本概念源于Lucene。

## Cassandra——开源分布式数据库管理系统。

最初是由Facebook开发的，旨在处理许多商品服务器上的大量数据，提供高可用性，没有单点故障。
Apache Cassandra是一套开源分布式NoSQL数据库系统。集Google BigTable的数据模型与Amazon Dynamo的完全分布式架构于一身。于2008开源，此后，由于Cassandra良好的可扩展性，被Digg、Twitter等Web 2.0网站所采纳，成为了一种流行的分布式结构化数据存储方案。
因Cassandra是用Java编写的，所以理论上在具有JDK6及以上版本的机器中都可以运行，官方测试的JDK还有OpenJDK 及Sun的JDK。 Cassandra的操作命令，类似于我们平时操作的关系数据库，对于熟悉MySQL的朋友来说，操作会很容易上手。

## Hazelcast ——基于Java的开源内存数据网格。

Hazelcast 是一种内存数据网格 in-memory data grid，提供Java程序员关键任务交易和万亿级内存应用。虽然Hazelcast没有所谓的“Master”，但是仍然有一个Leader节点(the oldest member)，这个概念与ZooKeeper中的Leader类似，但是实现原理却完全不同。同时，Hazelcast中的数据是分布式的，每一个member持有部分数据和相应的backup数据，这点也与ZooKeeper不同。
Hazelcast的应用便捷性深受开发者喜欢，但如果要投入使用，还需要慎重考虑。

## Hadoop ——用Java编写的开源软件框架。

用于分布式存储，并对非常大的数据用户可以在不了解分布式底层细节的情况下，开发分布式程序。充分利用集群进行高速运算和存储。Hadoop实现了一个分布式文件系统(Hadoop Distributed File System)，简称HDFS。Hadoop的框架最核心的设计就是：HDFS和MapReduce。HDFS为海量的数据提供了存储，MapReduce则为海量的数据提供了计算。

## Flink

## Solr ——开源企业搜索平台，用Java编写，来自Apache Lucene项目。

Solr是一个独立的企业级搜索应用服务器，它对外提供类似于Web-service的API接口。用户可以通过http请求，向搜索引擎服务器提交一定格式的XML文件，生成索引;也可以通过Http Get操作提出查找请求，并得到XML格式的返回结果。

与ElasticSearch一样，同样是基于Lucene，但它对其进行了扩展，提供了比Lucene更为丰富的查询语言，同时实现了可配置、可扩展并对查询性能进行了优化。

## Spark ——Apache Software Foundation中最活跃的项目，是一个开源集群计算框架。

Spark 是一种与 Hadoop 相似的开源集群计算环境，但是两者之间还存在一些不同之处，这些不同之处使 Spark 在某些工作负载方面表现得更加优越，换句话说，Spark 启用了内存分布数据集，除了能够提供交互式查询外，它还可以优化迭代工作负载。

Spark 是在 Scala 语言中实现的，它将 Scala 用作其应用程序框架。与 Hadoop 不同，Spark 和 Scala 能够紧密集成，其中的 Scala 可以像操作本地集合对象一样轻松地
