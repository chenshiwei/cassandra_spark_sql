# Spark-SQL 读 cassandra 聚合数据 保存 csv/json 格式小工具

## 开发说明

### 构建步骤
```bash
git clone https://git.uyunsoft.cn/chensw/cassandra_spark_sql.git
cd cassandra_spark_sql
mvn package
ls build/target/cassandra_sql.tar.gz
```

### 调试步骤
以下说明如何在IDE中运行cassandra_spark_sql服务：
1. 增加本地启动配置文件：cassandra_spark_sql/build/conf/sql.perperties
    
    ```
    # Cassandra Config
    spark.cassandra.connection.host=10.1.62.234
    spark.cassandra.auth.username=root
    spark.cassandra.auth.password=Root_123
    cassandra.keyspace=system

    ```
2. 运行Java类：uyun.ocean.ai.app.AIApp
3. 运行成功后，就可以通过提示运行

### 重要的配置
1. 调试模式下日志文件<br>
    cassandra_spark_sql/build/conf/logback.xml
2. 调试模式下配置文件<br>
    cassandra_spark_sql/build/conf/sql.perperties<br>

### 配置库目录布局
```
cassandra_spark_sql
│  README.md            本文件
├─build   
│  ├─bin                相关二进制文件与shell文件
│  ├─conf               目录：相关配置文件            
│  ├─assembly.xml       maven打包文件
│  └─conf               相关配置文件
├─common                模块：公共部分
├─core                  模块：核心部分
```

## 部署方法
cassandra_sql.tar.gz 拷入 linux 系统中
```bash
tar zxvf cassandra_sql.tar.gz
# 修改cassandra连接配置
vi cassandra_sql/conf/sql.properties 
# run
cassandra_sql/bin/sql.sh 
# 根据提示操作即可
```