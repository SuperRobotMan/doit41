package cn.doit.day01;
import org.apache.commons.lang3.RandomStringUtils;
import org.apache.commons.lang3.RandomUtils;

import java.io.*;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.*;


public class BufferWriterDemo {
    public static void main(String[] args) {
        try {
            // 创建输出文件流对象
            FileOutputStream fos = new FileOutputStream("D://output.txt");
            // 创建缓冲流对象
            BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(fos));
            ArrayList<String> list = new ArrayList<>();
            list.add("父母");
            list.add("子女");
            list.add("爷爷奶奶");
            list.add("夫妻");
            list.add("朋友");
            list.add("大众");

            ArrayList<String> list1 = new ArrayList<>();
            list1.add("平安e生保-百万医疗2023");
            list1.add("平安e生保-长期医疗险");
            list1.add("平安少儿百万全保险");
            list1.add("平安在线问诊医疗险");
            list1.add("平安互联网少儿牙医保");
            list1.add("平安e生保-家庭医疗险");
            list1.add("平安颐享-高端医疗险");
            list1.add("品安抗癌特药险");
            list1.add("平安e生保-慢病医疗2023");
            list1.add("平安e生保-综合意外险");
            list1.add("平安e生保-中老年综合意外险");
            list1.add("平安综合意外险");


            ArrayList<String> list2 = new ArrayList<>();
            list2.add("微信");
            list2.add("支付宝");
            list2.add("现金");
            list2.add("银行卡");
            // 写入10万条数据
            /**
             * -- cdm.dwd_policy_main_d           -- 保单主表
             * policy_no                          -- 保单号
             * app_applicant_user_id              -- 投保人id
             * applicant_name                     -- 投保人姓名
             * applicant_relation                 -- 与被保人关系
             * product_code                       -- 产品代码
             * product_name                       -- 产品名称
             * agent_code                         -- 主招揽业务员代码
             * application_date                   -- 投保日期
             * underwrite_date                    -- 承保日期
             * effective_date                     -- 生效日期
             * maturity_date                      -- 满期日期
             * payment_to_date                    -- 缴至日期
             * modal_standard_premium             -- 期缴标准保费
             * modal_add_premium                  -- 期缴其他加费
             * modal_total_premium                -- 期缴保费合计
             * app_pay_type_code                  -- 支付类型
             * renewal_flag                       -- 续保标识(0:新保,1:续保)
             */
            for (int i = 1; i <= 10000000; i++) {
                int app_applicant_user_id = RandomUtils.nextInt(10000, 1000000);
                String applicant_name = RandomStringUtils.randomAlphabetic(5);
                String applicant_relation = list.get(RandomUtils.nextInt(0, list.size()));
                String product_code = RandomStringUtils.randomAlphabetic(8);
                String product_name = list1.get(RandomUtils.nextInt(0, list1.size()));
                String agent_code = RandomStringUtils.randomAlphabetic(5);
                String application_date = getRandomTime();

                String app_pay_type_code = list2.get(RandomUtils.nextInt(0, list2.size()));

                int modal_standard_premium = RandomUtils.nextInt(1000, 10000);
                int modal_add_premium = RandomUtils.nextInt(1000, 2000);


                String res = i+","
                        +app_applicant_user_id +","
                        +applicant_name +","
                        +applicant_relation +","
                        +product_code +","
                        +product_name +","
                        +agent_code +","
                        +application_date +","
                        +addData(application_date, 1) +","
                        +addData(application_date, 2) +","
                        +addData(application_date, 365) +","
                        +addData(application_date, 365) +","
                        +modal_standard_premium +","
                        +modal_add_premium +","
                        +modal_standard_premium+modal_add_premium +","
                        +app_pay_type_code +","
                        +RandomUtils.nextInt(0,2);
                //用户日志行为数据
                bw.write(res);
                bw.newLine(); // 换行
            }
            // 关闭缓冲流和输出文件流
            bw.close();
            fos.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static String getRandomTime() {
        int  year,  month,  day;
        Random  rand  =  new Random();

        //  generate  random  year  between  1900  and  2020
        year  =  rand.nextInt(23)  +  2000;

        //  generate  random  month  between  1  and  12
        month  =  rand.nextInt(12)  +  1;

        //  generate  random  day  between  1  and  maximum  number  of  days  in  that  month  and  year
        Calendar calendar  =  new GregorianCalendar(year,  month  -  1,  1);
        int  maxDay  =  calendar.getActualMaximum(Calendar.DAY_OF_MONTH);
        day  =  rand.nextInt(maxDay)  +  1;

        //  format  date  to  display  as  string
        SimpleDateFormat  dateformat  =  new  SimpleDateFormat("yyyy-MM-dd");
        Date  date  =  new  GregorianCalendar(year,  month  -  1,  day).getTime();
        String  randomDate  =  dateformat.format(date);

        return  randomDate;
    }

    public static String addData(String currentDate ,int num){
        LocalDate  date  =  LocalDate.parse(currentDate,  DateTimeFormatter.ISO_DATE);
        LocalDate newDate  =  date.plusDays(num);
        String  newDateString  =  newDate.format(DateTimeFormatter.ISO_DATE);
        return newDateString;
    }
}

