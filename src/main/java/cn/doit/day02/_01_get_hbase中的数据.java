package cn.doit.day02;

import cn.doit.day01.HbaseUtil;
import org.apache.hadoop.hbase.Cell;
import org.apache.hadoop.hbase.CellUtil;
import org.apache.hadoop.hbase.TableName;
import org.apache.hadoop.hbase.client.Connection;
import org.apache.hadoop.hbase.client.Get;
import org.apache.hadoop.hbase.client.Result;
import org.apache.hadoop.hbase.client.Table;

import java.io.IOException;
import java.nio.charset.StandardCharsets;

public class _01_get_hbase中的数据 {
    public static void main(String[] args) throws IOException {
        Connection conn = HbaseUtil.getConn();
        Table table = conn.getTable(TableName.valueOf("doit:userinfo"));
        Get get = new Get("1".getBytes(StandardCharsets.UTF_8));
//        get.addColumn("f1".getBytes(StandardCharsets.UTF_8),"name".getBytes(StandardCharsets.UTF_8));
        get.addFamily("f1".getBytes(StandardCharsets.UTF_8));
        Result result = table.get(get);

        //里面是一个迭代器
//        boolean advance = ;//类似于hasnext

        while (result.advance()){
            Cell current = result.current();//类似于next
            //获取cell中的具体元素值
            String family = new String(CellUtil.cloneFamily(current));
            String row = new String(CellUtil.cloneRow(current));
            String qualifier = new String(CellUtil.cloneQualifier(current));
            String value = new String(CellUtil.cloneValue(current));
            System.out.println(row+","+family+","+qualifier+","+value);

        }



    }
}
