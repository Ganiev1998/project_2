package Example.Homework_20_12_2023;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Service {
    private final static String url = "jdbc:postgresql://localhost:5432/mydata";
    private final static String user = "postgres";
    private final static String password = "gmb1998";
    private final static Connection connection;

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
            if (password.equals(resultSet.getString("password"))) return true;
        }
        return false;
    }
    public void admins_permissions() throws SQLException {
        Statement statement = connection.createStatement();
        ResultSet res = statement.executeQuery("select p.id,p.permission_name from permissions p left join " +
                "role_permission rp on p.id = rp.permission_id where rp.role_id=1;\n");
        int x =0;
        while (res.next()){
            if (x%2==0) System.out.println(res.getInt("id")+"."+res.getString("permission_name")+"\t\t\t");
            else if (x%2==1) System.out.println(res.getInt("id")+"."+res.getString("permission_name"));
        }
    }
    public void teachers_permissions() throws SQLException{
        Statement statement = connection.createStatement();
        ResultSet res = statement.executeQuery("select p.id,p.permission_name from permissions p left join " +
                "role_permission rp on p.id = rp.permission_id where rp.role_id=2;\n");
        int x = 0;
        while (res.next()){
            if (x%2==0) System.out.println(res.getInt("id")+"."+res.getString("permission_name")+"\t\t\t");
            else if (x%2==1) System.out.println(res.getInt("id")+"."+res.getString("permission_name"));
        }
    }
    public void students_permissions() throws SQLException{
        Statement statement = connection.createStatement();
        ResultSet res = statement.executeQuery("select p.id,p.permission_name from permissions p left join " +
                "role_permission rp on p.id = rp.permission_id where rp.role_id=3;\n");
        int x = 0;
        while (res.next()){
            if (x%2==0) System.out.println(res.getInt("id")+"."+res.getString("permission_name")+"\t\t\t");
            else if (x%2==1) System.out.println(res.getInt("id")+"."+res.getString("permission_name"));
        }
    }
    public void create_user(Scanner sc) throws SQLException {
        PreparedStatement preparedStatement = connection.prepareStatement(
                "insert into users(name,password,role_id) values (?,?,?)");
        System.out.print("enter the user name: ");
        preparedStatement.setString(1,sc.next());
        System.out.print("enter the password: ");
        preparedStatement.setString(2,sc.next());
        System.out.print("enter the role id(1.admin/2.teacher/3.student): ");
        preparedStatement.setInt(3,sc.nextInt());
        preparedStatement.executeUpdate();
        System.out.println("user is successfully created");
    }
    public void show_users() throws SQLException {
        Statement statement = connection.createStatement();
        ResultSet r = statement.executeQuery("select u.id,u.name,u.password,u.role_id,r.name from users u" +
                    " left join roles r on u.role_id = r.id ");
        System.out.println("         >>>>>>>>>>>>>>>>>>> users table <<<<<<<<<<<<<<<<<<<");
        while (r.next()){
            Users user = new Users(r.getInt("id"),r.getString("name"),
                    r.getString("password"),r.getInt("role_id"),
                    r.getString("name"));
            System.out.println(user);
        }
    }
    public void update_users(Scanner sc) throws SQLException {
        System.out.print("enter the id users who do you want to update: ");
        int id = sc.nextInt();
        System.out.print("1.update the name\t\t\t2.update the password\t\t\t3.update the role\nselect the attribute: ");
        int selection = sc.nextInt();
        if (selection==1) {
            PreparedStatement preparedStatement = connection.prepareStatement(
                    "update users set name = ? where id ='"+id+"'");
            System.out.print("enter the new user name: ");
            preparedStatement.setString(1,sc.next());
            preparedStatement.executeUpdate();
            System.out.println("user successfully updated");
        } else if (selection==2) {
            PreparedStatement preparedStatement = connection.prepareStatement(
                    "update users set password = ? where id='"+id+"'");
            System.out.print("enter the new password: ");
            preparedStatement.setString(1,sc.next());
            preparedStatement.executeUpdate();
            System.out.println("user is successfully updated");
        } else if (selection==3) {
            PreparedStatement preparedStatement = connection.prepareStatement(
                    "update users set role_id = ? where id='"+id+"'");
            System.out.print("select the new role(1.admin/2.teacher/3.student): ");
            preparedStatement.setInt(1,sc.nextInt());
            preparedStatement.executeUpdate();
            System.out.println("user is successfully updated");
        }
    }
    public void delete_user(Scanner sc) throws SQLException {
        PreparedStatement preparedStatement =connection.prepareStatement(
                "delete from users where id = ?");
        System.out.println("enter the id user who do you want to delete: ");
        preparedStatement.setInt(1,sc.nextInt());
        preparedStatement.executeUpdate();
        System.out.println("user is successfully deleted");
    }
}
