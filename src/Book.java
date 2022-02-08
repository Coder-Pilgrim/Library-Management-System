import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;

public class Book {
    private String bookName;
    private String bookAuthor;
    private String bookPublisher;
    private String bookCategory;

    public Book(String bookName, String bookAuthor, String bookPublisher, String bookCategory) {
        this.bookName = bookName;
        this.bookAuthor = bookAuthor;
        this.bookPublisher = bookPublisher;
        this.bookCategory = bookCategory;
    }

    public String getBookName() {
        return bookName;
    }

    public String getBookAuthor() {
        return bookAuthor;
    }

    public String getBookPublisher() {
        return bookPublisher;
    }

    public String getBookCategory() {
        return bookCategory;
    }

    public void setBookName(String bookName) {
        this.bookName = bookName;
    }

    public void setBookAuthor(String bookAuthor) {
        this.bookAuthor = bookAuthor;
    }

    public void setBookPublisher(String bookPublisher) {
        this.bookPublisher = bookPublisher;
    }

    public void setBookCategory(String bookCategory) {
        this.bookCategory = bookCategory;
    }


    public static void insertBookPostgreSQL(String name, String author, String category, String publisher) {
        String jdbcUrl = "jdbc:postgresql://localhost:5432/Library";
        String user = "postgres";
        String password = "root";
        try {
            Class.forName("org.postgresql.Driver");
            Connection connection = DriverManager.getConnection(jdbcUrl, user, password);
            String sqlBook = "INSERT INTO books (book_name, book_author, book_publisher, book_category)\n" +
                    "    VALUES (?, ?, ?, ?);";

            PreparedStatement preparedStatement = connection.prepareStatement(sqlBook);

            preparedStatement.setString(1, name);
            preparedStatement.setString(2, author);
            preparedStatement.setString(3, category);
            preparedStatement.setString(4, publisher);

            preparedStatement.executeUpdate();

            System.out.println("Book added successfully");
            connection.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    public String toString() {
        return "Book Name: " + bookName + "\nBook Author: " + bookAuthor + "\nBook Publisher: " + bookPublisher + "\nBook Category: " + bookCategory;
    }
}
