package kunkun;

import org.apache.kafka.common.serialization.StringSerializer;

import java.io.FileInputStream;
import java.io.IOException;

public class test {
    public static void main(String[] args) throws IOException {
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


        System.out.println(StringSerializer.class.getName());

    }
}
