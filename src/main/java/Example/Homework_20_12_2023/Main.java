package Example.Homework_20_12_2023;

import java.sql.SQLException;
import java.sql.SQLOutput;
import java.util.Scanner;

public class Main {
    //        !!!DASTUR ISHLASHI UCHUN KERAKLI TABLELAR CREATE BILAN INSETLARI!!!
//    create table roles(id serial primary key,name varchar(20));
//    create table users(id serial primary key,name varchar(20),password varchar(20),role_id int references roles(id)
//    on delete cascade );
//    create table permissions(id serial primary key,permission_name varchar(20));
//    create table role_permission(id serial primary key,role_id int references roles(id) on delete cascade ,permission_id
//    int references permissions(id) on delete cascade );
//    insert into roles(name) values ('admin'),('teacher'),('student');
//    insert into permissions(permission_name) values ('create user'),('show users'),('update user'),('delete user'),
//            ('create permission'),('show permissions'),('update permission'),
//            ('delete permission'),('role+permission'),('create role'),
//            ('create test'),('update test'),('show tests'),('delete test'),
//            ('test solution');
//    insert into users(name, password, role_id) VALUES ('Alan','1234',1);
//    insert into role_permission(role_id, permission_id) VALUES (1,1),(1,2),(1,3),(1,4),(1,5),(1,6),(1,7),(1,8),
//            (1,9),(1,10),(2,11),(2,12),(2,13),(2,14),(3,13),(3,15);

    public static void main(String[] args) throws SQLException, InterruptedException {
        Scanner sc = new Scanner(System.in);
        Service s = new Service();
        int selection = -1;
        while (selection != 0) {
            System.out.print("1.log in for admin\t\t2.log in for teacher\t\t3.log in for student\t\t0.exit\nplease select: ");
            selection = sc.nextInt();
            if (selection == 1) {
                System.out.print("enter the user name: ");
                String name = sc.next();
                System.out.print("enter the password: ");
                String password = sc.next();
                if (s.log_in_users(name, password)) {
                    int sel = -1;
                    while (sel != 0) {
                        System.out.println(">>>>>>>>>>>>>>>>>>>> admins permissions <<<<<<<<<<<<<<<<<<<<<<");
                        s.admins_permissions();
                        System.out.print("0. exit\nsiz faqat userlar ustida CRUD bajara olasiz\nplease selact permission: ");
                        sel = sc.nextInt();
                        if (sel == 1) s.create_user(sc);
                        else if (sel == 2) s.show_users();
                        else if (sel == 3) s.update_users(sc);
                        else if (sel == 4) s.delete_user(sc);
                    }
                }
                else System.err.println("wrong password or user name please try again!");
                Thread.sleep(100);
            } else if (selection == 2) {
                System.out.print("enter the user name: ");
                String name = sc.next();
                System.out.print("enter the password: ");
                String password = sc.next();
                if (s.log_in_users(name, password)) {
                    System.out.println(">>>>>>>>>>>>>>>>>>>>> teachers permissions <<<<<<<<<<<<<<<<<<<<");
                    s.teachers_permissions();
                    System.err.println("teacher qismi hali oxirirgacha ishlab chiqilmagan !!!");
                    Thread.sleep(100);
                }
            } else if (selection == 3) {
                System.out.print("enter the user name: ");
                String name = sc.next();
                System.out.print("enter the password: ");
                String password = sc.next();
                if (s.log_in_users(name, password)) {
                    System.out.println(">>>>>>>>>>>>>>>>>>>>>> students permissions <<<<<<<<<<<<<<<<<<<<<");
                    s.students_permissions();
                    System.err.println("student qismi hali oxirirgacha ishlab chiqilmagan !!!");
                    Thread.sleep(100);
                }
            }
        }
    }
}
