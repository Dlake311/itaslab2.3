import java.sql.SQLException;

/** Пункт меню 4: сортировка массива (по возрастанию и убыванию), сохранение в БД и вывод. */
public class SortirovkaMassiva extends PunktMenu {
    private final Sort sort;

    public SortirovkaMassiva(Baza b, Sort sort) {
        super(b);
        this.sort = sort;
    }

    @Override
    public String nazvanie() {
        return "Отсортировать массив и сохранить в PostgreSQL с последующим выводом в консоль";
    }

    @Override
    public void vypolnit() throws SQLException {
        if (!b.estTabl()) return;

        // Если в этом запуске массив не вводили - берём последний сохранённый в таблице
        if (!sort.isZapolnen()) {
            int[] m = MassivBD.posledniyIshodnyi(b);
            if (m == null) {
                System.out.println("Сначала введите массив (пункт 3)");
                return;
            }
            sort.zadat(m);
            System.out.println("Исходный массив загружен из таблицы '" + b.tablename + "'");
        }

        System.out.println("Исходный массив: " + ArrayPI.vStroku(sort.getMassiv()));
        MassivBD.sohranitIVyvesti(b, "По возрастанию", sort.poVozrastaniyu());
        MassivBD.sohranitIVyvesti(b, "По убыванию", sort.poUbyvaniyu());
    }
}
