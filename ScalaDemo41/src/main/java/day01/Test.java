package day01;

public class Test {
    public static void main(String[] args) {

        Func3 func3 = new Func3();
        String apply = func3.apply(1, 2, 3);

        MyFunction3<Integer, Integer, Integer, String> myFunc3 = new MyFunction3<Integer, Integer, Integer, String>() {
            @Override
            public String apply(Integer a, Integer b, Integer c) {
                return a+b+c+"";
            }
        };
        String apply1 = myFunc3.apply(1, 2, 3);


    }
}

class Func3 implements MyFunction3<Integer,Integer,Integer,String>{

    @Override
    public String apply(Integer a, Integer b, Integer c) {
        return a+b+c+"";
    }
}



