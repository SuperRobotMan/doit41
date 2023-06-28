package cn.doit.day02;

import cn.doit.day01.HbaseUtil;
import org.apache.hadoop.hbase.Cell;
import org.apache.hadoop.hbase.CellUtil;
import org.apache.hadoop.hbase.CompareOperator;
import org.apache.hadoop.hbase.TableName;
import org.apache.hadoop.hbase.client.*;
import org.apache.hadoop.hbase.filter.*;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.Iterator;

public class _03_scan数据 {
    public static void main(String[] args) throws IOException {
        Connection conn = HbaseUtil.getConn();
        Table table = conn.getTable(TableName.valueOf("doit:userinfo"));
        Scan scan = new Scan();
        //过滤器
        /**
         * rowFilter
         * 列族
         * 列
         * 行的前缀
         * value的filter
         */
        /**
         * 传两个参数
         * 1.CompareOperator  是一个枚举类型   大于  大于等于 小于  小于等于 不等于 等于...
         * 2.ByteArrayComparable 这是一个抽象类，需要传具体的实现 BinaryComparator
         */
        //扫描 f1这个列族 并且只要行键为2的数据
        FamilyFilter familyFilter = new FamilyFilter(CompareOperator.EQUAL, new BinaryComparator("f1".getBytes(StandardCharsets.UTF_8)));
        RowFilter rowFilter = new RowFilter(CompareOperator.EQUAL, new BinaryComparator("2".getBytes(StandardCharsets.UTF_8)));

//        new ValueFilter()
//        new QualifierFilter()
//                new PrefixFilter()


        FilterList filterList = new FilterList();
        filterList.addFilter(familyFilter);
        filterList.addFilter(rowFilter);


//        scan.setFilter(familyFilter);
//        scan.setFilter(rowFilter);
        scan.setFilter(filterList);
        //前闭后开
//        scan.withStartRow("2".getBytes(StandardCharsets.UTF_8));
//        scan.withStopRow("4".getBytes(StandardCharsets.UTF_8));

        ResultScanner scanner = table.getScanner(scan);
        Iterator<Result> iterator = scanner.iterator();
        while (iterator.hasNext()){
            Result next = iterator.next();
            while (next.advance()){
                Cell current = next.current();//类似于next
                //获取cell中的具体元素值
                String family = new String(CellUtil.cloneFamily(current));
                String row = new String(CellUtil.cloneRow(current));
                String qualifier = new String(CellUtil.cloneQualifier(current));
                String value = new String(CellUtil.cloneValue(current));
                System.out.println(row+","+family+","+qualifier+","+value);
            }
        }

    }
}
