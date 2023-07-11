package kunkun;

import com.alibaba.fastjson.JSON;
import org.apache.commons.lang3.RandomStringUtils;
import org.apache.commons.lang3.RandomUtils;
import org.apache.kafka.common.serialization.StringSerializer;

import java.io.FileInputStream;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;

public class test {
    public static void main(String[] args) throws IOException, SQLException {
        //D:\doit_hp\doit37_scala\cai
       /* String p1 = "C:\\Users\\34650\\Desktop\\坤坤\\cai\\ASCII-cai (";
        String p3 = ").txt";

        for (int i = 1; i < 590; i++) {
            try {
                Thread.sleep(78);
                FileInputStream fis = new FileInputStream(p1 + i + p3);

                int data;

                while ((data = fis.read()) != -1) {
                    System.out.print((char) data);
                }
                fis.close();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

        }*/
        //获取jdbc的连接
/*
        Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/test", "root", "123456");
        PreparedStatement pps = conn.prepareStatement("insert into test values(?,?,?,?)");


//        System.out.println(StringSerializer.class.getName());
        UserInfo userInfo = new UserInfo(1, "zss", 18, "male");
        String json = JSON.toJSONString(userInfo);
        System.out.println(json);
        UserInfo userInfo1 = JSON.parseObject(json, UserInfo.class);
        System.out.println(userInfo1.getAge());
        String name = userInfo1.getName();
        int id = userInfo1.getId();
        System.out.println(userInfo1.getGender());

        pps.setInt(1,id);
        pps.setString(2,name);
        pps.setInt(3,userInfo1.getAge());
        pps.setString(4,userInfo1.getGender());
        pps.execute();
*/

        ArrayList<String> list = new ArrayList<>();
        list.add("male");
        list.add("female");

        for (int i = 0; i < 100; i++) {
            System.out.println(list.get(RandomUtils.nextInt(0, list.size())));
        }


//        for (int i = 0; i < 100; i++) {
//            System.out.println(RandomStringUtils.randomAlphabetic(10));
//
//        }

    }
}
