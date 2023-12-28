package Example;

import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicReference;

import static java.lang.Integer.valueOf;

public class exam_1 {
    public class shape {
        public int calculate(int a, int b) {
            return a + b;
        }
    }

    public class rectangle extends shape {
        @Override
        public int calculate(int a, int b) {
            return a * b;
        }
    }

    public class Triangle extends shape {
        @Override
        public int calculate(int a, int b) {
            return Math.abs(a - b);
        }
    }

    public class Circle extends shape {
        @Override
        public int calculate(int a, int b) {
            return a / b;
        }
    }
    public class task_4{
        public static void main(String[] args) {
            Thread t = new Thread(()->{
                System.out.println("hello world");
            });
            t.start();
        }
    }
    public class task_5{
        public static void main(String[] args) {
            AtomicInteger a = new AtomicInteger(10);
            ExecutorService e = Executors.newFixedThreadPool(5);
            e.submit(()->{
                for (int i = 0; i < 10; i++) {
                    a.incrementAndGet();
                }
            });
            System.out.println(a.get());
        }
    }

    public static void main(String[] args) {
        List<Integer> list = List.of(1, 3, 6, 8, 10, 18, 36);
        double i = list.stream().mapToInt(Integer::intValue).average().getAsDouble();
        System.out.println(i);

    }
}