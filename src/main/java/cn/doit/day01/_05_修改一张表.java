package cn.doit.day01;

import org.apache.hadoop.hbase.TableName;
import org.apache.hadoop.hbase.client.Admin;
import org.apache.hadoop.hbase.client.ColumnFamilyDescriptorBuilder;
import org.apache.hadoop.hbase.client.Connection;
import org.apache.hadoop.hbase.client.Table;

import java.io.IOException;
import java.nio.charset.StandardCharsets;

public class _05_修改一张表 {
    public static void main(String[] args) throws IOException {
        Connection conn = HbaseUtil.getConn();
        //admin对象是对表，名称空间级别的操作
        Admin admin = conn.getAdmin();
        //table对象是对表中的数据进行操作的
//        Table table = conn.getTable(TableName.valueOf("doit:abc"));

        ColumnFamilyDescriptorBuilder columnFamilyDescriptorBuilder = ColumnFamilyDescriptorBuilder.newBuilder("f1".getBytes(StandardCharsets.UTF_8));
        columnFamilyDescriptorBuilder.setTimeToLive(20);
        //删除一个列族
        admin.deleteColumnFamily(TableName.valueOf("doit:abc"),"f2".getBytes(StandardCharsets.UTF_8));
        admin.modifyColumnFamily(TableName.valueOf("doit:abc"),columnFamilyDescriptorBuilder.build());
        conn.close();

        //往里面添加数据  put 一条一条添加的
    }
}
