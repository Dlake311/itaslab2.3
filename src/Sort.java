/**
 * Класс-наследник Sort: наследует массив из ArrayPI и сортирует его
 * методом пузырьковой сортировки по возрастанию и по убыванию.
 */
public final class Sort extends ArrayPI {

    // Пузырьковая сортировка по возрастанию (исходный массив не меняется)
    public int[] poVozrastaniyu() {
        return puzyrek(true);
    }

    // Пузырьковая сортировка по убыванию (исходный массив не меняется)
    public int[] poUbyvaniyu() {
        return puzyrek(false);
    }

    private int[] puzyrek(boolean vozr) {
        int[] a = massiv.clone();
        for (int i = 0; i < a.length - 1; i++) {
            boolean obmen = false;
            // после каждого прохода самый большой (или маленький) элемент "всплывает" в конец
            for (int j = 0; j < a.length - 1 - i; j++) {
                boolean nadoMenyat = vozr ? a[j] > a[j + 1] : a[j] < a[j + 1];
                if (nadoMenyat) {
                    int t = a[j];
                    a[j] = a[j + 1];
                    a[j + 1] = t;
                    obmen = true;
                }
            }
            if (!obmen) break; // обменов не было - массив уже отсортирован
        }
        return a;
    }
}
