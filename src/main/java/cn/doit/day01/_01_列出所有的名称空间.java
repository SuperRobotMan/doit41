package cn.doit.day01;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.hbase.HBaseConfiguration;
import org.apache.hadoop.hbase.NamespaceDescriptor;
import org.apache.hadoop.hbase.client.Admin;
import org.apache.hadoop.hbase.client.Connection;
import org.apache.hadoop.hbase.client.ConnectionFactory;

import java.io.IOException;
import java.util.Map;
import java.util.Set;

public class _01_列出所有的名称空间 {
    public static void main(String[] args) throws IOException {
        /*Configuration conf = HBaseConfiguration.create();
        conf.set("hbase.zookeeper.quorum","linux01:2181");
        Connection conn = ConnectionFactory.createConnection(conf);*/
        Connection conn = HbaseUtil.getConn();
        //获取admin对象
        Admin admin = conn.getAdmin();
        //获取所有的名称空间
        NamespaceDescriptor[] namespaceDescriptors = admin.listNamespaceDescriptors();
        //拿到每一个名称空间描述器
        for (NamespaceDescriptor namespaceDescriptor : namespaceDescriptors) {
            //名称空间的名称
            String name = namespaceDescriptor.getName();
            System.out.println("名称空间是:"+name);
            //关于名称空间的属性参数
            Map<String, String> configuration = namespaceDescriptor.getConfiguration();
            Set<String> set = configuration.keySet();
            for (String key : set) {
                String value = configuration.get(key);
                System.out.println(key+"=>"+value);
            }
        }
        admin.close();
        conn.close();
    }
}
