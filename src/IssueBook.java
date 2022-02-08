import java.sql.*;

public class IssueBook {
    private String bookName;
    private String authorName;
    private String userName;
    private String issueDate;

    public IssueBook(String bookName, String authorName,String userName, String issueDate) {
        this.bookName = bookName;
        this.authorName = authorName;
        this.userName = userName;
        this.issueDate = issueDate;
    }

    public void insertIssuedBook(String bookName,String authorName,String userName,String issueDate) {
        String jdbcUrl = "jdbc:postgresql://localhost:5432/Library";
        String user = "postgres";
        String password = "root";
        try{
            Class.forName("org.postgresql.Driver");
            Connection connection = DriverManager.getConnection(jdbcUrl, user, password);
            String sql = "INSERT INTO issuedbooks (bookname, authorname, username,issuedate) values (?,?,?,?)";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1,bookName);
            preparedStatement.setString(2,authorName);
            preparedStatement.setString(3,userName);
            preparedStatement.setString(4,issueDate);
            preparedStatement.executeUpdate();
            System.out.println("Book Issued Successfully");
        }catch (Exception e) {
            e.printStackTrace();
        }
    }
    public static void deleteFromBooks(String bookName) {
        String jdbcUrl = "jdbc:postgresql://localhost:5432/Library";
        String user = "postgres";
        String password = "root";
        try{
            Class.forName("org.postgresql.Driver");
            Connection connection = DriverManager.getConnection(jdbcUrl, user, password);
            PreparedStatement statement =
                    connection.prepareStatement("DELETE FROM books where book_name=?");
            statement.setString(1,bookName);
            statement.executeUpdate();
        }catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static int issuedBookControl(String bookName)
    {
        String jdbcUrl = "jdbc:postgresql://localhost:5432/Library";
        String user = "postgres";
        String password = "root";
        try{
            Class.forName("org.postgresql.Driver");
            Connection connection = DriverManager.getConnection(jdbcUrl, user, password);
            PreparedStatement statement =
                    connection.prepareStatement("Select * from issuedbooks where bookname=?");
            statement.setString(1,bookName);
            ResultSet resultSet = statement.executeQuery();
            if(resultSet.next()) {
                return 0;
            }
            else {
                return 1;
            }
        }catch (Exception e) {
            e.printStackTrace();
            return 1;
        }
    }

    public String getBookName() {
        return bookName;
    }

    public void setBookName(String bookName) {
        this.bookName = bookName;
    }

    public String getAuthorName() {
        return authorName;
    }

    public void setAuthorName(String authorName) {
        this.authorName = authorName;
    }
    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getIssueDate() {
        return issueDate;
    }

    public void setIssueDate(String issueDate) {
        this.issueDate = issueDate;
    }
}
