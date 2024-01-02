package Example.homework_28_12;

import java.sql.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Scanner;

import static java.lang.Integer.valueOf;

public class Dorixana{
    private final static String url = "jdbc:postgresql://localhost:5432/mydata";
    private final static String user = "postgres";
    private final static String password = "gmb1998";
    private final static Connection connection;
    public int id;

    static {
        try {
            connection = DriverManager.getConnection(url,user,password);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
    public boolean log_in_users(String user_name,String password) throws SQLException {
        Statement statement = connection.createStatement();
        ResultSet resultSet = statement.executeQuery("select * from users where name ='"+user_name+"'");
        while (resultSet.next()){
            id = resultSet.getInt("id");
            if (password.equals(resultSet.getString("password"))) {
                System.out.println("siz dorixona tizimiga jirdingiz!!!");
                return true;
            }
        }
        return false;
    }
    public void dori_qoshish(Scanner sc) throws SQLException {
        PreparedStatement preparedStatement = connection.prepareStatement(
                "insert into dori(name, price, dori_turi, olchov, ishlab_chiqaruvchi, qoldiq) VALUES(?,?,?,?,?,?)");
        System.out.print("nomi: ");preparedStatement.setString(1,sc.next());
        System.out.print("narxi: ");preparedStatement.setInt(2,sc.nextInt());
        System.out.print("dori_turi (1.tabletka,2.suyuqlik,3.kapsula,4.surtma-krem,5.poroshok): ");
        preparedStatement.setInt(3,sc.nextInt());
        System.out.print("olchovi (1.dona,2.pochka,3.mgram,4.mlitr,5.litr,6.kg): ");
        preparedStatement.setInt(4,sc.nextInt());
        System.out.print("ishlab chiqaruvchi(1.Berlin-Chemie,2.GMP,3.Fulmed-Farm,4.Farmak): ");
        preparedStatement.setInt(5,sc.nextInt());
        System.out.print("soni: ");preparedStatement.setInt(6,sc.nextInt());
        preparedStatement.executeUpdate();
        System.out.println("dori qo`shildi");
    }
    public void show_dori() throws SQLException {
        Statement statement = connection.createStatement();
        ResultSet res = statement.executeQuery("select d.id,d.name,d.price,t.name as turi,o.name as olchovi,i.name ishlab_ch,d.qoldiq from dori d join dori_turi t on d.dori_turi = t.id\n" +
                "        join olchov_birlik o on d.olchov = o.id join ishlab_chiqaruvchi i on i.id = d.ishlab_chiqaruvchi");
        while (res.next()){
            System.out.println(res.getInt("id")+" | "+res.getString("name")+" | "+
                    res.getInt("price")+" | "+res.getString("turi")+" | "+
                    res.getString("olchovi")+" | "+
                    res.getString("ishlab_ch")+" | "+res.getInt("qoldiq"));
        }
    }
    public void sotish(Scanner sc) throws SQLException {
        show_dori();
        PreparedStatement preparedStatement = connection.prepareStatement(
                "insert into sotuv_doc(dorilar_id,tolov_turi,hodim_id) VALUES (?,?,?)");
        System.out.print("kerakli dorilar idlarini kiriting: ");
        String[] arr = sc.next().split(",");
        ArrayList<Integer> list = new ArrayList<>();
        for (int i = 0; i < arr.length; i++) {
            list.add(Integer.valueOf(arr[i]));
        }
        Array array = connection.createArrayOf("int", list.toArray());
        preparedStatement.setArray(1, array);
        System.out.print("tolov turini tanlang 1.naqd\t\t2.karta: ");
        preparedStatement.setInt(2,sc.nextInt());
        preparedStatement.setInt(3,id);
        preparedStatement.executeUpdate();
        Statement statement = connection.createStatement();
        ResultSet res = statement.executeQuery("\n" +
                "select s.id,s.dorilar_id,s.dorilar_nomi,s.jami_tolov,t.name as tolov_t,h.name as hodim from sotuv_doc s left join " +
                "tolov_turi t on s.tolov_turi = t.id left join hodimlar h on h.id = s.hodim_id order by id desc limit 1;");
        System.out.println(">>>>>>>>>>>>>>>>>>>>>>>>>> harid cheki <<<<<<<<<<<<<<<<<<<<<<<<<<");
        while (res.next()){
            System.out.println(res.getInt("id")+" | "+res.getArray("dorilar_id")+" | "+
                    res.getArray("dorilar_nomi")+" | "+res.getInt("jami_tolov")+" | "+
                    res.getString("tolov_t")+" | "+res.getString("hodim"));
        }
    }
    public void show_sotuv_doc() throws SQLException {
        Statement statement = connection.createStatement();
        ResultSet res = statement.executeQuery("\n" +
                "select s.id,s.dorilar_id,s.dorilar_nomi,s.jami_tolov,t.name as tolov_t,h.name as hodim\n" +
                "from sotuv_doc s left join tolov_turi t on s.tolov_turi = t.id left join hodimlar h on h.id = s.hodim_id");
        while (res.next()){
            System.out.println(res.getInt("id")+" | "+res.getArray("dorilar_id")+" | "+
                    res.getArray("dorilar_nomi")+" | "+res.getDouble("jami_tolov")+" | "+
                    res.getString("tolov_t")+" | "+res.getString("hodim"));
        }
    }
}
