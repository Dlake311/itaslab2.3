import java.sql.SQLException;

/** Пункт меню 3: ввод одномерного массива, сохранение в БД и вывод в консоль. */
public class VvodMassiva extends PunktMenu {
    private final Sort sort;

    public VvodMassiva(Baza b, Sort sort) {
        super(b);
        this.sort = sort;
    }

    @Override
    public String nazvanie() {
        return "Ввести одномерный массив и сохранить в PostgreSQL с последующим выводом в консоль";
    }

    @Override
    public void vypolnit() throws SQLException {
        if (!b.estTabl()) return;
        sort.vvod(b.scan);
        MassivBD.sohranitIVyvesti(b, "Исходный", sort.getMassiv());
    }
}
