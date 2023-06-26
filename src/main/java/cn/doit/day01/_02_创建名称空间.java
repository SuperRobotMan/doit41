package cn.doit.day01;

import org.apache.hadoop.hbase.NamespaceDescriptor;
import org.apache.hadoop.hbase.client.Admin;
import org.apache.hadoop.hbase.client.Connection;

import java.io.IOException;

public class _02_创建名称空间 {
    public static void main(String[] args) throws IOException {
        Connection conn = HbaseUtil.getConn();
        Admin admin = conn.getAdmin();

        //需要名称空间描述器，首先先构建一个构造器
        NamespaceDescriptor.Builder java = NamespaceDescriptor.create("java");
        //设置属性参数
        java.addConfiguration("author","robot_jiang");
        java.addConfiguration("time","2023-06-26");
        java.addConfiguration("describe","this namespace from idea");
        //用构造器再去构建描述器
        NamespaceDescriptor build = java.build();
        //创建名称空间需要一个名称空间描述器
        admin.createNamespace(build);

        admin.close();
        conn.close();


    }
}
