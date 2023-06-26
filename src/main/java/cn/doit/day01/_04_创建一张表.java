package cn.doit.day01;

import org.apache.hadoop.hbase.TableName;
import org.apache.hadoop.hbase.client.*;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;

public class _04_创建一张表 {
    public static void main(String[] args) throws IOException {
        Connection conn = HbaseUtil.getConn();
        Admin admin = conn.getAdmin();

        //创建一个表的描述器构建器
        TableDescriptorBuilder tableDescriptorBuilder = TableDescriptorBuilder.newBuilder(TableName.valueOf("doit:abc1"));
//        ArrayList<ColumnFamilyDescriptor> columnFamilyDescriptors = new ArrayList<>();
        //创建列族描述器构建器
        ColumnFamilyDescriptorBuilder columnFamilyDescriptorBuilder = ColumnFamilyDescriptorBuilder.newBuilder("f1".getBytes(StandardCharsets.UTF_8));
        columnFamilyDescriptorBuilder.setTimeToLive(10);
        ColumnFamilyDescriptorBuilder columnFamilyDescriptorBuilder1 = ColumnFamilyDescriptorBuilder.newBuilder("f2".getBytes(StandardCharsets.UTF_8));

//        columnFamilyDescriptors.add(columnFamilyDescriptorBuilder.build());
//        columnFamilyDescriptors.add(columnFamilyDescriptorBuilder1.build());

        //需要一个列族描述器
//        tableDescriptorBuilder.setColumnFamilies(columnFamilyDescriptors);
        tableDescriptorBuilder.setColumnFamily(columnFamilyDescriptorBuilder.build());
        tableDescriptorBuilder.setColumnFamily(columnFamilyDescriptorBuilder1.build());
        //创建一张表，需要一个表的描述器
        admin.createTable(tableDescriptorBuilder.build());

    }
}
