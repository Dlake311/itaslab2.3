import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.nio.charset.StandardCharsets;
import java.sql.ResultSet;
import java.sql.SQLException;

/** Пункт меню 5: выгрузка всех массивов из БД в Excel (CSV) и вывод их в консоль. */
public class EksportExcel extends PunktMenu {

    public EksportExcel(Baza b) {
        super(b);
    }

    @Override
    public String nazvanie() {
        return "Сохранить результаты из PostgreSQL в Excel и вывести их в консоль";
    }

    @Override
    public void vypolnit() throws SQLException {
        if (!b.estTabl()) return;
        File file = new File(b.papkaDlyaFaila(), b.tablename + ".csv");
        ResultSet rs = b.stat.executeQuery("SELECT * FROM " + b.tablename + " ORDER BY id");
        int count = 0;

        System.out.println("\n=== Содержимое таблицы '" + b.tablename + "' ===");
        // CSV с BOM и разделителем ';' - Excel открывает его с правильной кодировкой.
        // Каждый массив - одна строка Excel, каждый элемент - в своей ячейке.
        try (Writer w = new OutputStreamWriter(new FileOutputStream(file), StandardCharsets.UTF_8)) {
            w.write('﻿');
            w.write("id;Массив");
            for (int i = 1; i <= ArrayPI.N; i++) w.write(";" + i);
            w.write("\n");
            while (rs.next()) {
                count++;
                String id = rs.getString("id");
                String tip = rs.getString("tip");
                int[] m = MassivBD.izBD(rs);
                System.out.printf("%-4s %-15s %s%n", id, tip, ArrayPI.vStroku(m));
                w.write(id + ";" + b.csv(tip));
                for (int v : m) w.write(";" + v);
                w.write("\n");
            }
        } catch (IOException e) {
            System.out.println("Ошибка при создании файла: " + e.getMessage());
            return;
        } finally {
            rs.close();
        }

        if (count == 0) System.out.println("Таблица пуста");
        System.out.println("\nДанные сохранены в Excel-файл: " + file.getAbsolutePath() + " (записей: " + count + ")");
        b.otkrytFail(file);
    }
}
