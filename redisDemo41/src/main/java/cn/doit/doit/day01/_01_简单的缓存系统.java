package cn.doit.doit.day01;

import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;

//造一个简单的缓存
public class _01_简单的缓存系统 {
    public static void main(String[] args) throws SQLException, InterruptedException {

        HashMap<String, HashMap<Integer, String>> map1 = new HashMap<>();
        HashMap<Integer, String> map2 = new HashMap<>();
        map2.put(1,"zss");
        map2.put(2,"lss");
        map2.put(3,"ww");
        map2.put(4,"zl");
        map1.put("friends",map2);


        HashMap<Integer, String> map = new HashMap<>();
        //jdbc连接mysql
        Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/test", "root", "123456");
        //得到一个预编译的sql
        PreparedStatement pps = conn.prepareStatement("select name from test where id = ? ");

        //想让他查询的时候，又去数据库里面查，又去缓存里面查
        ArrayList<Integer> list = new ArrayList<>();
        list.add(1);
        list.add(2);
        list.add(3);
        list.add(2);
        list.add(1);
        list.add(4);
        for (Integer i : list) {
            //在查询之前，先去缓存中搂一眼
            String res = map.get(i);
            if (res!=null){
                System.out.println("从缓存中获取到了数据");
                System.out.println(res);
                Thread.sleep(500);
            }else {
                //如果缓存中没有，再去查询mysql
                //设置？的值
                pps.setInt(1,i);
                //获取查询结果
                ResultSet resultSet = pps.executeQuery();
                while (resultSet.next()){
                    String name = resultSet.getString("name");
                    //将查询的结果添加到缓存系统中
                    System.out.println("数据库中有，准备将数据存到缓存中");//埋点日志
                    map.put(i,name);
                    System.out.println("数据库中有，已经将数据存到缓存中了");//埋点日志
                    System.out.println("返回数据库中的数据");
                    System.out.println(name);
                    Thread.sleep(500);
                }
            }

        }


    }
}
