package Example.Homework_20_12_2023;

public class Users {
    Integer id;
    String name;
    String password;
    Integer role_id;
    String role_name;

    public Users(Integer id, String name, String password, Integer role_id, String role_name) {
        this.id = id;
        this.name = name;
        this.password = password;
        this.role_id = role_id;
        this.role_name = role_name;
    }

    @Override
    public String toString() {
        return "Users{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", password='" + password + '\'' +
                ", role_id=" + role_id +
                ", role_name='" + role_name + '\'' +
                '}';
    }
}
