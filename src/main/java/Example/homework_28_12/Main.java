package Example.homework_28_12;

import java.sql.SQLException;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) throws SQLException {
        Dorixana d = new Dorixana();
        Scanner sc = new Scanner(System.in);
        System.out.println("tizimga kirish uchun login parolingizni kiriting!");
        int selection = -1;
        while (selection != 0) {
            System.out.print("user name: ");
            String user_name = sc.next();
            System.out.print("Parol: ");
            String password = sc.next();
            if (d.log_in_users(user_name, password)) {
                while (selection != 0) {
                    System.out.print("1.dori sotish\t\t\t2.dori qo`shish\t\t\t3.dorilar ro`yxatini ko`rish\n4.sotuv tarixi" +
                            "\t\t\t0.chiqish: ");
                    selection = sc.nextInt();
                    if (selection == 1) {
                        d.sotish(sc);
                    } else if (selection == 2) {
                        d.dori_qoshish(sc);
                    } else if (selection == 3) {
                        d.show_dori();
                    } else if (selection == 4) {
                        d.show_sotuv_doc();
                    } else if (selection == 0) {
                        return;
                    }
                }
            } else System.out.println("parol yoki login notogri kiritilgan!");
        }
    }
}
