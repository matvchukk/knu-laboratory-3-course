package main.utils;

import main.utils.CustomSkipList;

public class Main {
    public static void main(String[] args) {
        CustomSkipList skipList = new CustomSkipList(16);

        skipList.add(5);
        skipList.add(1);
        skipList.add(3);
        skipList.add(4);
        skipList.add(6);
        skipList.add(13);
        skipList.add(511);
        skipList.add(111);
        skipList.add(332);
        skipList.add(522);
        skipList.add(112);
        skipList.add(3323);

        System.out.println(skipList.contains(511));

        System.out.println(skipList.toString());

        skipList.remove(5);

        System.out.println(skipList.toString());

        skipList.remove(333111);

        System.out.println(skipList.toString());

        System.out.println(skipList.contains(511));
        skipList.remove(511);
        System.out.println(skipList.contains(511));

        System.out.println(skipList.toString());

    }
}