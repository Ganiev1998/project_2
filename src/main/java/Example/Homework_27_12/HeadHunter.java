package Example.Homework_27_12;

import java.sql.*;
import java.util.List;
import java.util.Scanner;

public class HeadHunter {
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
            if (password.equals(resultSet.getString("password"))) return true;
        }
        return false;
    }
    public void create_information_for_candidate(Scanner sc) throws SQLException, InterruptedException {
        PreparedStatement preparedStatement = connection.prepareStatement(
                "insert into candidate_resume(candidate_id, information, linkedin_lik, address, age) VALUES(" +
                        "?,?,?,?,?) ");
        preparedStatement.setInt(1,id);
        System.out.print("information: ");
        preparedStatement.setString(2, sc.next());
        System.out.print("linkedin link: ");
        preparedStatement.setString(3,sc.next());
        System.out.print("address:");
        preparedStatement.setString(4,sc.next());
        System.out.print("age: ");
        preparedStatement.setInt(5,sc.nextInt());
        preparedStatement.executeUpdate();
        Thread.sleep(100);

        Statement statement = connection.createStatement();
        ResultSet res = statement.executeQuery(
                "select c.id from users u join candidate_resume c on c.candidate_id = u.id where u.id ='"+id+"'");
        int resume_id = 0;
        if (res.next()) {
            resume_id = res.getInt("id");
        }
        int s = -1;
        while (s!=0) {
            PreparedStatement preparedStatement1 = connection.prepareStatement("" +
                    "insert into candidate_skills(candidate_resume_id, name, level) VALUES (?,?,?)");
            preparedStatement1.setInt(1, resume_id);
            System.out.print("skill name: ");
            preparedStatement1.setString(2, sc.next());
            System.out.print("level: ");
            preparedStatement1.setInt(3, sc.nextInt());
            preparedStatement1.executeUpdate();
            System.out.print("yana skil kiritishni istaysizmi?(1.ha 0.yo`q): ");
            s = sc.nextInt();
        }
        int s1 = -1;
        while (s1!=0) {
            PreparedStatement preparedStatement2 = connection.prepareStatement(
                    "insert into candidate_job_history(candidate_id, job_title, company, from_date, to_date," +
                            " information_about_job)values (?,?,?,?,?,?)");
            preparedStatement2.setInt(1, id);
            System.out.print("job title: ");
            preparedStatement2.setString(2, sc.next());
            System.out.print("company: ");
            preparedStatement2.setString(3, sc.next());
            System.out.print("from date: ");
            preparedStatement2.setDate(4, Date.valueOf(sc.next()));
            System.out.print("to date: ");
            preparedStatement2.setDate(5, Date.valueOf(sc.next()));
            System.out.print("information about job: ");
            preparedStatement2.setString(6, sc.next());
            preparedStatement2.executeUpdate();
            System.out.print("yana qaysidur joyda ishlaganmisiz?(1.ha 0.yoq): ");
            s1 = sc.nextInt();
        }
        int s2 = -1;
        while (s2!=0) {
            PreparedStatement preparedStatement3 = connection.prepareStatement(
                    "insert into candidate_education(school_name, from_date, to_date, about_course) " +
                            "values(?,?,?,?) ");
            System.out.print("school name: ");
            preparedStatement3.setString(1, sc.next());
            System.out.print("from date: ");
            preparedStatement3.setDate(2, Date.valueOf(sc.next()));
            System.out.print("to date: ");
            preparedStatement3.setDate(3, Date.valueOf(sc.next()));
            System.out.print("about course: ");
            preparedStatement3.setString(4, sc.next());
            preparedStatement3.executeUpdate();
            System.out.print("yana qaysidur joyda o`qiganmisiz?(1.ha 0.yo`q): ");
            s2 = sc.nextInt();
        }
    }
    public void create_information_for_company(Scanner sc) throws SQLException {
        PreparedStatement preparedStatement = connection.prepareStatement(
                "insert into company_description(company_id, information, site, location, number_of_workers, owner)" +
                        "values (?,?,?,?,?,?)");
        preparedStatement.setInt(1,id);
        System.out.print("information: ");
        preparedStatement.setString(2,sc.next());
        System.out.print("site: ");
        preparedStatement.setString(3,sc.next());
        System.out.print("location: ");
        preparedStatement.setString(4,sc.next());
        System.out.print("number of workers: ");
        preparedStatement.setInt(5,sc.nextInt());
        System.out.print("owner: ");
        preparedStatement.setString(6,sc.next());
        preparedStatement.executeUpdate();
    }
    public void create_vacancy(Scanner sc) throws SQLException {
        PreparedStatement preparedStatement = connection.prepareStatement(
                "insert into vacancy(company_id, name, needed_tech, lacation, work_time, salary)" +
                        "values (?,?,?,?,?,?)");
        preparedStatement.setInt(1,id);
        System.out.print("vacancy name: ");preparedStatement.setString(2,sc.next());
        System.out.print("needed technologies: ");
        String s = sc.nextLine();
        List<String> list = List.of(s.split(","));
        preparedStatement.setArray(3, (Array) list);
        System.out.print("location: ");preparedStatement.setString(4,sc.next());
        System.out.print("work_time: ");preparedStatement.setString(5,sc.next());
        System.out.print("slalry: ");preparedStatement.setInt(6,sc.nextInt());
        preparedStatement.executeUpdate();
    }
    public void show_vacancy() throws SQLException {
        Statement statement = connection.createStatement();
        ResultSet res = statement.executeQuery("SELECT v.id,u.name,v.name,v,needed_tech,\n" +
                "v.location,v.work_time,v.salary,v.is_active from vacancy v left join " +
                "users u on u.id = v.company_id");
        while (res.next()){
            System.out.println("id: "+res.getInt("id")+" / company: " +res.getString("u.name")
                    +" / vacancy: "+res.getString("v.name")+" needed tech: "+res.getString("needed_tech")+
                    " / location: "+res.getString("location")+" / work_time: "+res.getTimestamp("work_time")+
                    " / salary: "+res.getInt("salary")+" / is active: "+res.getString("is_active"));
        }
    }
    public void vacancy_condidate(Scanner sc,int vac_id,String status) throws SQLException {
        PreparedStatement preparedStatement = connection.prepareStatement(
                "insert into vacancy_candidate(vacancy_id, candidate_id,status)" +
                        "values (?,?,?)");
        preparedStatement.setInt(1,vac_id);preparedStatement.setInt(2,id);
        preparedStatement.setString(3,status);
        preparedStatement.executeUpdate();
    }
    public void show_candidates() throws SQLException {
        Statement statement = connection.createStatement();
        ResultSet resultSet = statement.executeQuery(
                "select u.name,u.tel_number,u.email,c.information,c.linkedin_lik,\n" +
                        "                c.address,c.age,cs.name as skill_name,j.job_title,j.company from users u left join\n" +
                        "                candidate_resume c on c.candidate_id = u.id join candidate_skills cs on cs.candidate_resume_id = c.id\n" +
                        "                join candidate_job_history j on j.candidate_id = u.id;"
        );
        while (resultSet.next()){
            System.out.println(resultSet.getString("name")+" / "+resultSet.getString("tel_number")+" / "+
                    resultSet.getString("email")+"\n"+resultSet.getString("information")+" / "+
                    resultSet.getString("linkedin_lik")+" / "+resultSet.getString("address")+" / "+
                    resultSet.getString("age")+" / "+resultSet.getString("skill_name")+" / "+
                    resultSet.getString("job_title")+" / "+resultSet.getString("company"));
        }
    }
    public void show_companys() throws SQLException {
        Statement statement = connection.createStatement();
        ResultSet resultSet = statement.executeQuery(
                "select u.name,u.tel_number,u.email,c.information,c.site,c.location,c.number_of_workers,c.owner from users u left join\n" +
                        "        company_description c on u.id = c.company_id where u.role = 'company';\n");
        while (resultSet.next()){
            System.out.println(resultSet.getString("name")+" / "+resultSet.getString("tel_number")+" / "+
                    resultSet.getString("email")+" / "+resultSet.getString("information")+" / "+
                    resultSet.getString("site")+" / "+resultSet.getString("location")+" / "+
                    resultSet.getString("number_of_workers")+" / "+resultSet.getString("owner"));
        }
    }
}
