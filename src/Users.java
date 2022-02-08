import java.sql.*;

public class Users
{
    private String firstname;
    private String lastname;
    private String userName;
    private String password;
    private String phone;
    private String address;

    public static void registerSQL(String firstname,String lastname,String userName,String userPassword,String phone,String address){
        String jdbcUrl = "jdbc:postgresql://localhost:5432/Library";
        String user = "postgres";
        String password = "root";
        try {
            Class.forName("org.postgresql.Driver");
            Connection connection = DriverManager.getConnection(jdbcUrl, user, password);
            String sqlBook = "INSERT INTO users (firstname, lastname, username, password, address, phonenumber)\n" +
                    "    VALUES (?, ?, ?, ?, ?, ?);";

            PreparedStatement preparedStatement = connection.prepareStatement(sqlBook);

            preparedStatement.setString(1, firstname);
            preparedStatement.setString(2, lastname);
            preparedStatement.setString(3, userName);
            preparedStatement.setString(4, userPassword);
            preparedStatement.setString(5, phone);
            preparedStatement.setString(6, address);


            preparedStatement.executeUpdate();

            System.out.println("User added successfully");
            connection.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    public static int loginControl(String userName, String userpassword) {
        String jdbcUrl = "jdbc:postgresql://localhost:5432/Library";
        String user = "postgres";
        String password = "root";
        try {
            Class.forName("org.postgresql.Driver");
            Connection connection = DriverManager.getConnection(jdbcUrl, user, password);
            PreparedStatement statement = connection.prepareStatement("Select * from users where username=? and password=?");
            statement.setString(1, userName);
            statement.setString(2, userpassword);
            ResultSet resultSet = statement.executeQuery();
            if(resultSet.next()) {
                return 0;
            } else {
                return 1;
            }
        }catch (Exception e) {
            e.printStackTrace();
            return 1;
        }
    }

    public static int usernameControl(String userName) {
        try {
            Class.forName("org.postgresql.Driver");
            Connection conn = DriverManager.getConnection("jdbc:postgresql://localhost:5432/Library", "postgres", "root");
            Statement stmt = conn.createStatement();
            String sql = "SELECT * FROM users WHERE username LIKE '" + userName + "'";
            ResultSet rs = stmt.executeQuery(sql);
            if(rs.next()) {
                return 0;
            }
            else {
                return 1;
            }
        } catch(Exception e) {
            System.out.println(e);
            return 1;
        }
    }
    public Users(String firstname, String lastname, String userName, String password, String phone, String address) {
        this.firstname = firstname;
        this.lastname = lastname;
        this.userName = userName;
        this.password = password;
        this.phone = phone;
        this.address = address;
    }

    public String getFirstname() {
        return firstname;
    }

    public void setFirstname(String firstname) {
        this.firstname = firstname;
    }

    public String getLastname() {
        return lastname;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }
}