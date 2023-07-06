package cn.doit.doit.day01;

import redis.clients.jedis.Jedis;

import java.util.Random;
import java.util.Scanner;

//需求：使用redis模拟手机验证码发送，验证码有效期60s，验证验证码输入不能超过3次，超过3次今天就没机会了
public class _02_手机验证码 {
    public static void main(String[] args) {

        //获取redis的对象
        Jedis jedis = JedisUtil.getJedis();

        //请输入你的手机号==》判断一下这个手机号是否正确
        Scanner sc = new Scanner(System.in);
        System.out.println("请输入你的手机号：");
        String phone = sc.next();
        //判断这个手机号是否正确
        //验证手机号
        /**
         * 判断字符串是否符合手机号码格式
         * 移动号段:   134 135 136 137 138 139 147 148 150 151 152 157 158 159  165 172 178 182 183 184 187 188 198
         * 联通号段:   130 131 132 145 146 155 156 166 170 171 175 176 185 186
         * 电信号段:   133 149 153 170 173 174 177 180 181 189  191  199
         * 虚拟运营商: 170
         */
// "[1]"代表下一位为数字可以是几，"[0-9]"代表可以为0-9中的一个，"[5,7,9]"表示可以是5,7,9中的任意一位,[^4]表示除4以外的任何一个,\\d{8}"代表后面是可以是0～9的数字，有8位。
        String regex = "^((13[0-9])|(14[5,6,7,9])|(15[^4])|(16[5,6])|(17[0-9])|(18[0-9])|(19[1,8,9]))\\d{8}$";
        boolean flag = phone.matches(regex);
        if (!flag) {
            System.out.println("啥玩意，手机号都输不对，你没资格获取验证码！！！！");
        } else {
            //发送验证码 ==》模拟一下验证码
            String code = getCode();
            System.out.println("验证码已经发到手机上，为了安全起见，不要给宇翔知道，验证码是：" + code);
            //将验证码存到redis里面，并且设置这个key过期时间为60s ==》 key 是啥，value是啥
            jedis.setex(phone, 10, code);
            //输入验证码
            System.out.println("请输入你的验证码：");
            String yourCode = sc.next();
            //拿着输入的验证码和redis里面的验证码进行比较
            //获取出来redis中的验证码
//            String checkCode = jedis.get(phone);
            int count = 2;
            //翔山
            while (count >= 0) {
                //只有在每次判断的时候才能get
                if (yourCode.equals(jedis.get(phone))) {
                    System.out.println("验证成功！！！！");
                    //跳出循环
                    break;
                } else {
                    if (count == 0){
                        System.out.println("验证码不正确，还有次" + count + "机会,game over!!!");
                        count--;
                    }else{
                        System.out.println("验证码不正确，还有次" + count + "机会,重新输入");
                        yourCode = sc.next();
                        count--;
                    }
                }
            }
        }
    }

    public static String getCode() {
        Random random = new Random();
        StringBuilder sb = new StringBuilder();
        //随机数
        for (int i = 0; i < 6; i++) {
            int num = random.nextInt(10);
            sb.append(num);
        }
        return sb.toString();
    }


}
