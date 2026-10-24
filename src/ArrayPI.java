import java.util.Arrays;
import java.util.Scanner;

/**
 * Класс ArrayPI: одномерный массив из 35 целых чисел, вводится с клавиатуры.
 */
public class ArrayPI {
    public static final int N = 35;

    protected int[] massiv = new int[N];
    protected boolean zapolnen = false; // введён ли массив

    // Ввод массива с клавиатуры. Числа можно вводить по одному или
    // несколько в строке через пробел - пока не наберётся 35.
    public void vvod(Scanner scan) {
        System.out.println("Введите " + N + " целых чисел (можно по одному или несколько через пробел):");
        int kol = 0;
        while (kol < N) {
            System.out.print("Элементы " + (kol + 1) + ".." + N + ": ");
            String stroka = scan.nextLine().trim();
            if (stroka.isEmpty()) continue;
            for (String ch : stroka.split("[\\s,;]+")) {
                if (kol >= N) {
                    System.out.println("Лишние числа (больше " + N + ") отброшены");
                    break;
                }
                try {
                    massiv[kol] = Integer.parseInt(ch);
                    kol++;
                } catch (NumberFormatException e) {
                    System.out.println("Ошибка: '" + ch + "' - не целое число, пропущено");
                }
            }
        }
        zapolnen = true;
    }

    public void zadat(int[] m) {
        massiv = m.clone();
        zapolnen = true;
    }

    public boolean isZapolnen() {
        return zapolnen;
    }

    public int[] getMassiv() {
        return massiv.clone();
    }

    public static String vStroku(int[] m) {
        return Arrays.toString(m);
    }
}
