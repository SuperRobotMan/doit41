package cn.doit.day02;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.hbase.HBaseConfiguration;
import org.apache.hadoop.hbase.TableName;
import org.apache.hadoop.hbase.client.*;
import org.apache.hadoop.hbase.tool.LoadIncrementalHFiles;

import java.io.IOException;

public class LoadHFileDemo {
    public static void main(String[] args) throws IOException {

        System.setProperty("HADOOP_USER_NAME","root");

        Configuration conf = HBaseConfiguration.create();
        conf.set("hbase.zookeeper.quorum","linux01:2181");
        Connection conn = ConnectionFactory.createConnection(conf);
        Admin admin = conn.getAdmin();
        TableName tableName = TableName.valueOf("doit:userinfo");
        Table table = conn.getTable(tableName);
        RegionLocator regionLocator = conn.getRegionLocator(tableName);

        LoadIncrementalHFiles loadIncrementalHFiles = new LoadIncrementalHFiles(conf);
        /**
         * 1.hfile现在的存储路径
         * 2.admin对象
         * 3.表对象
         * 4.region的加载器
         */
        loadIncrementalHFiles.doBulkLoad(new Path("hdfs://linux01:8020/output/"),admin,table,regionLocator);
    }
}
