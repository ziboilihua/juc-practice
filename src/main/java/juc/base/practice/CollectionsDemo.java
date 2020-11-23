package juc.base.practice;

import java.util.ArrayList;
import java.util.List;

public class CollectionsDemo<T> {
    private List<T> list;

    public CollectionsDemo() {
        list = new ArrayList<>();
    }

    public void add(T t) {
        list.add(t);
    }

    public Integer size() {
        return list.size();
    }

    public static void main(String[] args) {
        CollectionsDemo<Integer> c = new CollectionsDemo<>();
        Object o = new Object();
        new Thread(() -> {
            while (c.size() != 5) {
            }
            System.out.println("等于5了");
        }).start();
        new Thread(() -> {
            for (int i = 0; i < 10; i++) {
                c.add(i);
                System.out.println(c.size());
            }
        }).start();
    }
}
