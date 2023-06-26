package cn.doit.day01;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.hbase.HBaseConfiguration;
import org.apache.hadoop.hbase.client.Admin;
import org.apache.hadoop.hbase.client.Connection;
import org.apache.hadoop.hbase.client.ConnectionFactory;

import java.io.IOException;

public class ClientDemo {
    public static void main(String[] args) throws IOException {
        /*Configuration conf = HBaseConfiguration.create();
        //给hbase的conf设置一些参数   key value
        //zookeeper的连接地址 linux01:2181  ==> hbase-site.xml
        conf.set("hbase.zookeeper.quorum","linux01:2181,linux02:2181,linux03:2181");
        //首先要获取连接
        //创建连接需要传入一个conf
        Connection conn = ConnectionFactory.createConnection(conf);*/
        Connection conn = HbaseUtil.getConn();

        //得到一个操作对象
        Admin admin = conn.getAdmin();

        //用操作对象针对各种api 增删改查

        //关闭资源
        admin.close();
        conn.close();




    }
}
