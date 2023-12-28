package Example.Homework_27_12;
//        create type role as enum('user','company');
//        drop table users;
//        create table users(id serial primary key,name varchar,password varchar,tel_number varchar,
//        email varchar,role role);
//        insert into users(name, password, tel_number,email,role)values
//        ('Alan','1234','+998900341998','mirgnv8@gmail.com','user');
//        insert into users(name, password, tel_number, email, role) VALUES
//        ('Harry','4321','+998953214567','harry@email.com','company');
//        create table company_description(id serial primary key ,company_id int references users(id),
//        information text,site varchar,location varchar,number_of_workers int,owner varchar);
//        create table candidate_resume(id serial primary key ,candidate_id int references users(id),
//        information text,linkedin_lik varchar,address varchar,age int);
//        create table candidate_skills(id serial primary key ,candidate_resume_id int references
//        candidate_resume(id),name varchar,level int);
//        create table candidate_job_history(id serial primary key ,candidate_id int references users(id),
//        job_title varchar,company varchar,from_date date,to_date date,information_about_job text);
//        create table candidate_education(id serial primary key ,school_name varchar,from_date date,to_date date,
//        about_course text);
//        create table vacancy(id serial primary key,company_id int references users(id),name varchar,
//        needed_tech varchar[],location varchar,work_time varchar,salary int,is_active boolean default true);
//        create table vacancy_candidate(id serial primary key ,vacancy_id int references vacancy(id),
//        candidate_id int references users(id),date_time date default current_timestamp,status varchar);

import Example.homework_25_12.HeadHunter;

import java.sql.SQLException;
import java.util.Scanner;

public class Servise {
    public static void main(String[] args) throws SQLException, InterruptedException {
        Scanner sc = new Scanner(System.in);
        HeadHunter h = new HeadHunter();
        int selection = -1;
        while (selection!=0){
            System.out.println("1.company\t\t\t2.candidate\t\t\t0.exit\n selection: ");
            selection = sc.nextInt();
            if (selection==1){
                System.out.print("enter the user name: ");
                String user_name = sc.next();
                System.out.print("enter the password: ");
                String password = sc.next();
                if (h.log_in_users(user_name,password)){
                    int sel1 = -1;
                    while (sel1!=0) {
                        System.out.print("1.insert informations company\t\t\t2.show companies\t\t\t0.exit\nselection: ");
                        sel1 = sc.nextInt();
                        if (sel1==1){
                            h.create_information_for_company(sc);
                        }
                        if (sel1==2){
                            h.show_companys();
                        }
                    }
                }
                else System.out.println("wrong user name or password!");
            } else if (selection==2) {
                System.out.print("enter the user name: ");
                String user_name = sc.next();
                System.out.print("enter the password: ");
                String password = sc.next();
                if (h.log_in_users(user_name,password)){
                    int sel1 = -1;
                    while (sel1!=0){
                        System.out.print("1.insert informations candidate\t\t\t2.show candidates\t\t\t0.exit\nselection: ");
                        sel1 = sc.nextInt();
                        if (sel1==1){
                            h.create_information_for_candidate(sc);
                        }
                        if (sel1==2){
                            h.show_candidates();
                        }
                    }
                }
                else System.out.println("wrong user name or password!");
            }
        }
    }
}

