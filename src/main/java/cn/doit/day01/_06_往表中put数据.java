package cn.doit.day01;

import org.apache.hadoop.hbase.TableName;
import org.apache.hadoop.hbase.client.Connection;
import org.apache.hadoop.hbase.client.Put;
import org.apache.hadoop.hbase.client.Table;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;

/**
 * 单个的一个字段一个字段进行put
 * 缓冲put
 */

public class _06_往表中put数据 {
    public static void main(String[] args) throws IOException {
        Connection conn = HbaseUtil.getConn();
        Table table = conn.getTable(TableName.valueOf("doit:abc1"));

        //创建一个put对象
        Put put = new Put("rowkey001".getBytes(StandardCharsets.UTF_8));
        //指定哪一个行键下面的那一个列族下面的那一个列
        /**
         * 第一个参数：列族
         * 第二个参数：列名
         * 第三个参数：列对应的值
         */
        put.addColumn("f1".getBytes(StandardCharsets.UTF_8),"name".getBytes(StandardCharsets.UTF_8),"zss".getBytes(StandardCharsets.UTF_8));
        put.addColumn("f1".getBytes(StandardCharsets.UTF_8),"age".getBytes(StandardCharsets.UTF_8),"18".getBytes(StandardCharsets.UTF_8));
        put.addColumn("f1".getBytes(StandardCharsets.UTF_8),"gender".getBytes(StandardCharsets.UTF_8),"male".getBytes(StandardCharsets.UTF_8));
        put.addColumn("f1".getBytes(StandardCharsets.UTF_8),"address".getBytes(StandardCharsets.UTF_8),"shanghai".getBytes(StandardCharsets.UTF_8));
        Put put1 = new Put("rowkey002".getBytes(StandardCharsets.UTF_8));
        put1.addColumn("f2".getBytes(StandardCharsets.UTF_8),"name".getBytes(StandardCharsets.UTF_8),"zss".getBytes(StandardCharsets.UTF_8));
        put1.addColumn("f2".getBytes(StandardCharsets.UTF_8),"age".getBytes(StandardCharsets.UTF_8),"18".getBytes(StandardCharsets.UTF_8));
        put1.addColumn("f2".getBytes(StandardCharsets.UTF_8),"gender".getBytes(StandardCharsets.UTF_8),"male".getBytes(StandardCharsets.UTF_8));
        put1.addColumn("f2".getBytes(StandardCharsets.UTF_8),"address".getBytes(StandardCharsets.UTF_8),"shanghai".getBytes(StandardCharsets.UTF_8));
        Put put2 = new Put("rowkey003".getBytes(StandardCharsets.UTF_8));
        put2.addColumn("f1".getBytes(StandardCharsets.UTF_8),"name".getBytes(StandardCharsets.UTF_8),"zss".getBytes(StandardCharsets.UTF_8));
        put2.addColumn("f1".getBytes(StandardCharsets.UTF_8),"age".getBytes(StandardCharsets.UTF_8),"18".getBytes(StandardCharsets.UTF_8));
        put2.addColumn("f1".getBytes(StandardCharsets.UTF_8),"gender".getBytes(StandardCharsets.UTF_8),"male".getBytes(StandardCharsets.UTF_8));
        put2.addColumn("f1".getBytes(StandardCharsets.UTF_8),"address".getBytes(StandardCharsets.UTF_8),"shanghai".getBytes(StandardCharsets.UTF_8));
        ArrayList<Put> puts = new ArrayList<>();
        puts.add(put);
        puts.add(put1);
        puts.add(put2);

        table.put(puts);

        //put数据需要put对象
//        table.put(put);
//        table.put(put1);
//        table.put(put2);


    }
}
