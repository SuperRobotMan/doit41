package day01;

public class Test {
    public static void main(String[] args) {
       /* int a = 10;
        a = 29;
        System.out.println(a);

        int b ;//局部变量  不赋予初始值，不能够使用
        b = 10;
        System.out.println(b);

        //常量   用final修饰的变量就是常量   一旦创建，不能够修改他的值
        final int c = 10;
//        c = 20;

        Integer num = 10;
        int num1 = 10;*/
        //参与计算得时候，可以自动类型提升
/*        int a = 10;
        double b = 10.0;
        char c = 'a';
        double res = a + b + c;
        System.out.println(res);//117.0*/
        int [] arr = {1,2,3,4,5};
        for (int i : arr) {
            System.out.println(i);
        }

        for (int i = 0; i < 5; i+=2) {
            System.out.println(i);
        }

        //代码块
        {


        }

    }


}
