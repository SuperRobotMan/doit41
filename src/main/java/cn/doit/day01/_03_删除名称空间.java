package cn.doit.day01;

import org.apache.hadoop.hbase.TableName;
import org.apache.hadoop.hbase.client.Admin;
import org.apache.hadoop.hbase.client.Connection;
import org.apache.hadoop.hbase.util.Bytes;

import java.io.IOException;

public class _03_删除名称空间 {

    public static void main(String[] args) throws IOException {
        Connection conn = HbaseUtil.getConn();
        Admin admin = conn.getAdmin();
        //在删除表之前还需要禁用表
        TableName tableName1 = TableName.valueOf("doit41:stu");
        TableName tableName2 = TableName.valueOf("doit41:student");
        TableName tableName3 = TableName.valueOf("doit41:test");
        admin.disableTable(tableName1);
        admin.disableTable(tableName2);
        admin.disableTable(tableName3);
        //对于表的删除
        admin.deleteTable(tableName1);
        admin.deleteTable(tableName2);
        admin.deleteTable(tableName3);
        admin.deleteNamespace("doit41");

/*        TableName[] doit41 = admin.listTableNamesByNamespace("doit41");
        for (TableName tableName : doit41) {
            byte[] name = tableName.getName();
            String table = new String(name);
            System.out.println(table);
        }*/

    }
}
