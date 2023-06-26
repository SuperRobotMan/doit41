package cn.doit.day01;

import org.apache.hadoop.hbase.TableName;
import org.apache.hadoop.hbase.client.*;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;

/**
 * 单个的一个字段一个字段进行put
 * 缓冲put
 */

public class _06_缓冲的方式put数据 {
    public static void main(String[] args) throws IOException {
        Connection conn = HbaseUtil.getConn();
        //获取一个表数据缓冲的对象
        BufferedMutator bufferedMutator = conn.getBufferedMutator(TableName.valueOf("doit:abc1"));
        Put put = new Put("rowkey004".getBytes(StandardCharsets.UTF_8));
        put.addColumn("f1".getBytes(StandardCharsets.UTF_8),"name".getBytes(StandardCharsets.UTF_8),"zss".getBytes(StandardCharsets.UTF_8));
        put.addColumn("f1".getBytes(StandardCharsets.UTF_8),"age".getBytes(StandardCharsets.UTF_8),"18".getBytes(StandardCharsets.UTF_8));
        put.addColumn("f1".getBytes(StandardCharsets.UTF_8),"gender".getBytes(StandardCharsets.UTF_8),"male".getBytes(StandardCharsets.UTF_8));
        put.addColumn("f1".getBytes(StandardCharsets.UTF_8),"address".getBytes(StandardCharsets.UTF_8),"shanghai".getBytes(StandardCharsets.UTF_8));
        Put put1 = new Put("rowkey005".getBytes(StandardCharsets.UTF_8));
        put1.addColumn("f2".getBytes(StandardCharsets.UTF_8),"name".getBytes(StandardCharsets.UTF_8),"zss".getBytes(StandardCharsets.UTF_8));
        put1.addColumn("f2".getBytes(StandardCharsets.UTF_8),"age".getBytes(StandardCharsets.UTF_8),"18".getBytes(StandardCharsets.UTF_8));
        put1.addColumn("f2".getBytes(StandardCharsets.UTF_8),"gender".getBytes(StandardCharsets.UTF_8),"male".getBytes(StandardCharsets.UTF_8));
        put1.addColumn("f2".getBytes(StandardCharsets.UTF_8),"address".getBytes(StandardCharsets.UTF_8),"shanghai".getBytes(StandardCharsets.UTF_8));
        Put put2 = new Put("rowkey006".getBytes(StandardCharsets.UTF_8));
        put2.addColumn("f1".getBytes(StandardCharsets.UTF_8),"name".getBytes(StandardCharsets.UTF_8),"zss".getBytes(StandardCharsets.UTF_8));
        put2.addColumn("f1".getBytes(StandardCharsets.UTF_8),"age".getBytes(StandardCharsets.UTF_8),"18".getBytes(StandardCharsets.UTF_8));
        put2.addColumn("f1".getBytes(StandardCharsets.UTF_8),"gender".getBytes(StandardCharsets.UTF_8),"male".getBytes(StandardCharsets.UTF_8));
        put2.addColumn("f1".getBytes(StandardCharsets.UTF_8),"address".getBytes(StandardCharsets.UTF_8),"shanghai".getBytes(StandardCharsets.UTF_8));
        ArrayList<Put> puts = new ArrayList<>();
        puts.add(put);
        puts.add(put1);
        puts.add(put2);

        bufferedMutator.mutate(puts);
        //手动刷新
        bufferedMutator.flush();







    }
}
