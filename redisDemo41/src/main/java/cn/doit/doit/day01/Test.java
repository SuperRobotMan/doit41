package cn.doit.doit.day01;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.TreeSet;

public class Test {
    public static void main(String[] args) {
        ArrayList<String> list = new ArrayList<>();
        list.add("zss");
        list.add("lss");
        list.add("ww");
        list.add("zl");
        list.add("haixiang");
        list.add("yuxiang");
        System.out.println(list);//[zss, lss, ww, zl, haixiang, yuxiang]  ==>有序  按照的插入的顺序


        HashSet<String> set = new HashSet<>();
        set.add("zss");
        set.add("lss");
        set.add("ww");
        set.add("zl");
        set.add("haixiang");
        set.add("yuxiang");
        System.out.println(set);//[ww, zl, haixiang, yuxiang, zss, lss]  ==》 按照插入的顺序 叫无序

        TreeSet<String> set1 = new TreeSet<>();
        set1.add("zss");
        set1.add("lss");
        set1.add("ww");
        set1.add("zl");
        set1.add("haixiang");
        set1.add("yuxiang");
        System.out.println(set1);//[haixiang, lss, ww, yuxiang, zl, zss]  排序==》字典顺序
    }

}
