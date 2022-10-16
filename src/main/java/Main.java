import java.util.Random;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;

public class Main {
    static BlockingQueue<String> queueA = new ArrayBlockingQueue<>(100);
    static BlockingQueue<String> queueB = new ArrayBlockingQueue<>(100);
    static BlockingQueue<String> queueC = new ArrayBlockingQueue<>(100);
    final static int numberOFText = 10000;

    public static void main(String[] args) {
        new Thread(() -> {
            System.out.println("Поток генератор запущен");
            for (int i = 0; i < numberOFText; i++) {
                String text = generateText("abc", 100000);
                try {
                    queueA.put(text);
                    queueB.put(text);
                    queueC.put(text);
                } catch (InterruptedException e) {
                    return;
                }
            }
        }).start();

        new Thread(() -> {
            System.out.println("Поток счетчик буквы 'a' запущен");
            long counterMax = 0;
            String maxA = "";
            for (int i = 0; i < numberOFText; i++) {
                try {
                    String textA = queueA.take();
                    long counter = countOfLetter(textA,'a');
                    if (counter > counterMax){
                        maxA = textA;
                    }
                } catch (InterruptedException e) {
                    return;
                }
            }
            System.out.println("Строка с самым большим количеством буквы 'a'-> " + maxA);
        }).start();

        new Thread(() -> {
            System.out.println("Поток счетчик буквы 'b' запущен");
            long counterMax = 0;
            String maxB = "";
            for (int i = 0; i < numberOFText; i++) {
                try {
                    String textB = queueB.take();
                    long counter = countOfLetter(textB,'b');
                    if (counter > counterMax){
                        maxB = textB;
                    }
                } catch (InterruptedException e) {
                    return;
                }
            }
            System.out.println("Строка с самым большим количеством буквы 'b'-> " + maxB);
        }).start();

        new Thread(() -> {
            System.out.println("Поток счетчик буквы 'c' запущен");
            long counterMax = 0;
            String maxC = "";
            for (int i = 0; i < numberOFText; i++) {
                try {
                    String textC = queueC.take();
                    long counter = countOfLetter(textC,'c');
                    if (counter > counterMax){
                        maxC = textC;
                    }
                } catch (InterruptedException e) {
                    return;
                }
            }
            System.out.println("Строка с самым большим количеством буквы 'c' -> " + maxC);
        }).start();
    }

    public static String generateText(String letters, int length) {
        Random random = new Random();
        StringBuilder text = new StringBuilder();
        for (int i = 0; i < length; i++) {
            text.append(letters.charAt(random.nextInt(letters.length())));
        }
        return text.toString();
    }

    public static long countOfLetter(String text, char letter) {
        return text.chars()
                .filter(c -> c == letter)
                .count();
    }


}
