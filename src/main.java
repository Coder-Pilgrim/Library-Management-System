import java.sql.*;
import java.util.Scanner;

public class main {

    public static void main(String[] args) throws SQLException {

        boolean flag = true;
        while (flag) {
            System.out.println("------------Welcome to Library Management System------------");
            System.out.println("Please select an option:");
            System.out.println("1. Login");
            System.out.println("2. Register");
            System.out.println("3. Exit");
            Scanner scanner = new Scanner(System.in);
            int option = scanner.nextInt();
            scanner.nextLine();
            if (option == 1) {
                System.out.println("Please enter your username:");
                String username = scanner.nextLine();

                System.out.println("Please enter your password:");
                String password = scanner.nextLine();

                Users.loginControl(username, password);

                if (Users.loginControl(username, password) == 0) {
                    System.out.println("Login Successful");
                    displayOptions();
                }
                else {
                    System.out.println("Login Failed");
                }
            }
            else if (option == 2) {
                System.out.println("Please enter your first name:");
                String firstName = scanner.nextLine();

                System.out.println("Please enter your last name:");
                String lastName = scanner.nextLine();

                System.out.println("Please enter a username:");
                String username = scanner.nextLine();

                while (Users.usernameControl(username) == 0) {
                    System.out.println("Username already exists. Please enter a new username:");
                    username = scanner.nextLine();
                }

                System.out.println("Please enter a password:");
                String password = scanner.nextLine();

                System.out.println("Please enter your address:");
                String address = scanner.nextLine();

                System.out.println("Please enter your phone number:");
                String phoneNumber = scanner.nextLine();

                Users.registerSQL(firstName, lastName, username, password, address, phoneNumber);
            }
            else if (option == 3) {
                flag = false;
            }
        }

    }

    public static void displayOptions(){
        boolean choose = true;
        while (choose) {
            System.out.println("Enter your choice");
            System.out.println("1. Add Book");
            System.out.println("2. Issue Book");
            System.out.println("3. Return Book");
            System.out.println("4. Search Book");
            System.out.println("5. Search User");
            System.out.println("6. List Available Books");
            System.out.println("7. List Issued Books");
            System.out.println("8. List Users");
            System.out.println("9. Exit");
            Scanner sc = new Scanner(System.in);
            int choice = sc.nextInt();
            sc.nextLine();
            switch(choice) {
                case 1: //Add book
                    System.out.println("Enter Book Name");
                    String bookName = sc.nextLine();
                    System.out.println("Enter Book Author");
                    String bookAuthor = sc.nextLine();
                    System.out.println("Enter Book Publisher");
                    String bookPublisher = sc.nextLine();
                    System.out.println("Enter Book Category");
                    String bookCategory = sc.nextLine();
                    Book book = new Book(bookName, bookAuthor, bookPublisher, bookCategory);
                    book.insertBookPostgreSQL(book.getBookName(), book.getBookAuthor(), book.getBookCategory(), book.getBookPublisher());
                    break;
                case 2: //Issue book
                    System.out.println("Enter Book Name");
                    String bookname = sc.nextLine();
                    while (IssueBook.issuedBookControl(bookname) == 0) {
                        System.out.println("Book is not available");
                        System.out.println("Enter Book Name");
                        bookname = sc.nextLine();
                    }
                    System.out.println("Enter Author Name");
                    String authorname = sc.nextLine();
                    System.out.println("Enter Username");
                    String username = sc.nextLine();
                    System.out.println("Enter Issue Date");
                    String issueDate = sc.nextLine();

                    IssueBook issueBook = new IssueBook(bookname, authorname, username, issueDate);
                    issueBook.insertIssuedBook(issueBook.getBookName(), issueBook.getAuthorName(),
                            issueBook.getUserName(), issueBook.getIssueDate());
                    IssueBook.deleteFromBooks(bookname);
                    break;
                case 3: //Return book
                    System.out.println("Enter Book Name");
                    String returnbookname = sc.nextLine();
                    System.out.println("Enter Book Author");
                    String returnbookauthor = sc.nextLine();
                    System.out.println("Enter Book Publisher");
                    String returnbookpublisher = sc.nextLine();
                    System.out.println("Enter Book Category");
                    String returnbookcategory = sc.nextLine();
                    ReturnBook.returnBook(returnbookname, returnbookauthor, returnbookpublisher, returnbookcategory);
                    ReturnBook.deleteFromIssuedBooks(returnbookname);
                    break;
                case 4: //Search book
                    System.out.println("Enter Book ID");
                    int bookID3 = sc.nextInt();
                    searchBook(bookID3);
                    break;
                case 5: //Search member
                    System.out.println("Enter Member ID");
                    int memberID3 = sc.nextInt();
                    searchUser(memberID3);
                    break;
                case 6: //List books
                    listBooks();
                    break;
                case 7: //List books
                    listIssuedBooks();
                    break;
                case 8: //List members
                    listUsers();
                    break;
                case 9: //Exit
                    System.out.println("Thank you for using Library Management System");
                    choose = false;
                    break;
                default:
                    System.out.println("----------Invalid Choice----------");
            }
        }
    }

    public static void listIssuedBooks(){
        String jdbcUrl = "jdbc:postgresql://localhost:5432/Library";
        String user = "postgres";
        String password = "root";
        try {
            Class.forName("org.postgresql.Driver");
            Connection connection = DriverManager.getConnection(jdbcUrl, user, password);
            System.out.println("Connected to the PostgreSQL server successfully.");
            Statement statement = connection.createStatement();
            ResultSet results = statement.executeQuery( "SELECT * FROM issuedbooks;" );
            while ( results.next() ) {
                String book_name = results.getString("bookname");
                String book_author  = results.getString("authorname");
                String book_publisher = results.getString("username");
                String book_category = results.getString("issuedate");
                System.out.println( "BOOK NAME = " + book_name );
                System.out.println( "BOOK AUTHOR = " + book_author );
                System.out.println( "USERNAME = " + book_publisher );
                System.out.println( "ISSUE DATE= " + book_category );
                System.out.println();
            }
            connection.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    public static void searchUser(int memberID3) {
        try {
            Class.forName("org.postgresql.Driver");
            Connection conn = DriverManager.getConnection("jdbc:postgresql://localhost:5432/Library", "postgres", "root");
            Statement stmt = conn.createStatement();
            String sql = "SELECT * FROM users WHERE id = " + memberID3;
            ResultSet rs = stmt.executeQuery(sql);
            while(rs.next()) {
                System.out.println("USER ID: " + rs.getInt("id"));
                System.out.println("USER FIRSTNAME: " + rs.getString("firstname"));
                System.out.println("USER LASTNAME: " + rs.getString("lastname"));
                System.out.println("USERNAME: " + rs.getString("Username"));
                System.out.println("USER ADDRESS: " + rs.getString("address"));
                System.out.println("USER PHONE NUMBER: " + rs.getString("phonenumber"));
                System.out.println();
            }
            rs.close();
            stmt.close();
            conn.close();
        } catch(Exception e) {
            System.out.println(e);
        }
    }

    public static void listBooks(){
        String jdbcUrl = "jdbc:postgresql://localhost:5432/Library";
        String user = "postgres";
        String password = "root";
        try {
            Class.forName("org.postgresql.Driver");
            Connection connection = DriverManager.getConnection(jdbcUrl, user, password);
            System.out.println("Connected to the PostgreSQL server successfully.");
            Statement statement = connection.createStatement();
            ResultSet results = statement.executeQuery( "SELECT * FROM books;" );
            while ( results.next() ) {
                int id = results.getInt("id");
                String book_name = results.getString("book_name");
                String book_author  = results.getString("book_author");
                String book_publisher = results.getString("book_publisher");
                String book_category = results.getString("book_category");
                System.out.println( "ID = " + id );
                System.out.println( "BOOK NAME = " + book_name );
                System.out.println( "BOOK AUTHOR = " + book_author );
                System.out.println( "BOOK PUBLISHER = " + book_publisher );
                System.out.println( "BOOK CATEGORY = " + book_category );
                System.out.println();
            }
            connection.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void listUsers(){
        String jdbcUrl = "jdbc:postgresql://localhost:5432/Library";
        String user = "postgres";
        String password = "root";
        try {
            Class.forName("org.postgresql.Driver");
            Connection connection = DriverManager.getConnection(jdbcUrl, user, password);
            System.out.println("Connected to the PostgreSQL server successfully.");
            Statement statement = connection.createStatement();
            ResultSet results = statement.executeQuery( "SELECT * FROM users;" );
            while ( results.next() ) {
                int id = results.getInt("id");
                String firstname = results.getString("firstname");
                String lastname  = results.getString("lastname");
                String username = results.getString("username");
                String address = results.getString("address");
                String phoneNumber = results.getString("phonenumber");
                System.out.println( "ID = " + id );
                System.out.println( "USER FIRSTNAME = " + firstname );
                System.out.println( "USER LASTNAME = " + lastname );
                System.out.println( "USERNAME = " + username );
                System.out.println( "USER ADDRESS = " + address );
                System.out.println( "USER PHONE NUMBER = " + phoneNumber );
                System.out.println();
            }
            connection.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void searchBook(int bookID){
        String jdbcUrl = "jdbc:postgresql://localhost:5432/Library";
        String user = "postgres";
        String password = "root";
        try {
            Class.forName("org.postgresql.Driver");
            Connection connection = DriverManager.getConnection(jdbcUrl, user, password);
            System.out.println("Connected to the PostgreSQL server successfully.");
            Statement statement = connection.createStatement();
            ResultSet results = statement.executeQuery( "SELECT * FROM books WHERE id = " + bookID + ";" );
            while ( results.next() ) {
                int id = results.getInt("id");
                String book_name = results.getString("book_name");
                String book_author  = results.getString("book_author");
                String book_publisher = results.getString("book_publisher");
                String book_category = results.getString("book_category");
                System.out.println( "ID = " + id );
                System.out.println( "BOOK NAME = " + book_name );
                System.out.println( "BOOK AUTHOR = " + book_author );
                System.out.println( "BOOK PUBLISHER = " + book_publisher );
                System.out.println( "BOOK CATEGORY = " + book_category );
                System.out.println();
            }
            connection.close();
        } catch (Exception e) {
            e.printStackTrace();
        }



    }
}
