import java.sql.Array;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Сохранение массивов в таблицу и чтение их обратно.
 * Каждый массив - одна запись: (tip, massiv INTEGER[]).
 */
public class MassivBD {

    // Сохранить массив и вывести сохранённую запись, прочитав её из БД
    static void sohranitIVyvesti(Baza b, String tip, int[] m) throws SQLException {
        Integer[] obj = new Integer[m.length];
        for (int i = 0; i < m.length; i++) obj[i] = m[i];

        PreparedStatement ps = b.conn.prepareStatement("INSERT INTO " + b.tablename
                + " (tip, massiv) VALUES (?, ?) RETURNING id");
        ps.setString(1, tip);
        ps.setArray(2, b.conn.createArrayOf("integer", obj));
        ResultSet rs = ps.executeQuery();
        rs.next();
        int id = rs.getInt(1);
        rs.close();
        ps.close();
        System.out.println("Массив '" + tip + "' сохранён в таблицу '" + b.tablename + "' (id = " + id + ")");

        PreparedStatement sel = b.conn.prepareStatement("SELECT * FROM " + b.tablename + " WHERE id = ?");
        sel.setInt(1, id);
        ResultSet r = sel.executeQuery();
        if (r.next()) {
            System.out.println(r.getString("tip") + " (данные из БД): " + ArrayPI.vStroku(izBD(r)));
        }
        r.close();
        sel.close();
    }

    // Последний сохранённый исходный массив или null
    static int[] posledniyIshodnyi(Baza b) throws SQLException {
        ResultSet rs = b.stat.executeQuery("SELECT massiv FROM " + b.tablename
                + " WHERE tip = 'Исходный' ORDER BY id DESC LIMIT 1");
        int[] m = rs.next() ? izBD(rs) : null;
        rs.close();
        return m;
    }

    static int[] izBD(ResultSet r) throws SQLException {
        Array arr = r.getArray("massiv");
        if (arr == null) return new int[0];
        Integer[] obj = (Integer[]) arr.getArray();
        int[] m = new int[obj.length];
        for (int i = 0; i < obj.length; i++) m[i] = obj[i];
        return m;
    }
}
