package library;
import java.sql.*;
import java.util.Scanner;



public class lib {

    String DB_URL = "jdbc:mysql://localhost:3306/library";
    String USER = "root";
    String PASSWORD = "Habipsna@1";

    Connection connection;
    Scanner scanner;

    public lib() throws SQLException {
        connection = DriverManager.getConnection(DB_URL, USER, PASSWORD);
        System.out.println("Connected to the database.");
        scanner = new Scanner(System.in);
    }

    public void displayMenu() {
        System.out.println("\nLibrary Management System Menu:");
        System.out.println("1. Add New Book");
        System.out.println("2. Search Book");
        System.out.println("3. Show All Books");
        System.out.println("4. Register Student");
        System.out.println("5. Show All Registered Students");
        System.out.println("6. Give Rating");
        System.out.println("7. Exit Application");

        System.out.print("Enter your choice: ");
    }

    public void addBook() throws SQLException {
        System.out.print("Enter book name: ");
        String bookName = scanner.nextLine();
        System.out.println("Enter publisher name: ");
        String authorName = scanner.nextLine();
        System.out.print("Enter price of book: ");
        int quantity = scanner.nextInt();
        scanner.nextLine(); 
        System.out.print("Enter ISBN: ");
        String isbn = scanner.nextLine();

        String query = "INSERT INTO books_details1 (book_isbn, book_name, book_publisher, book_price) VALUES (?, ?, ?, ?)";
        PreparedStatement statement = connection.prepareStatement(query);
        statement.setString(1, isbn);
        statement.setString(2, bookName);
        statement.setString(3, authorName);
        statement.setInt(4, quantity);
        statement.executeUpdate();
        System.out.println("Book added successfully.");
    }

    public void searchBook() throws SQLException {
        System.out.print("Enter publisher name: ");
        String publisherName = scanner.nextLine();

        String query = "SELECT * FROM books_details1 WHERE book_publisher = ?";
        PreparedStatement statement = connection.prepareStatement(query);
        statement.setString(1, publisherName);
        ResultSet resultSet = statement.executeQuery();

        System.out.println("Books by " + publisherName + ":");
        System.out.println("ID\tISBN\t\tName\t\tPublisher\t\tPrice");
        while (resultSet.next()) {
            int bid = resultSet.getInt("bid");
            String bookIsbn = resultSet.getString("book_isbn");
            String bookName = resultSet.getString("book_name");
            String bookPublisher = resultSet.getString("book_publisher");
            int bookPrice = resultSet.getInt("book_price");

            System.out.printf("%d\t%s\t\t%s\t\t%s\t\t%d\n", bid, bookIsbn, bookName, bookPublisher, bookPrice);
        }
    }

    public void showAllBooks() throws SQLException {
        String query = "SELECT * FROM books_details1";
        PreparedStatement statement = connection.prepareStatement(query);
        ResultSet resultSet = statement.executeQuery();

        System.out.println("All Available Books:");
        System.out.println("ID\tISBN\t\tName\t\tPublisher\t\tPrice");
        while (resultSet.next()) {
            int bid = resultSet.getInt("bid");
            String bookIsbn = resultSet.getString("book_isbn");
            String bookName = resultSet.getString("book_name");
            String bookPublisher = resultSet.getString("book_publisher");
            int bookPrice = resultSet.getInt("book_price");

            System.out.printf("%d\t%s\t\t%s\t\t%s\t\t%d\n", bid, bookIsbn, bookName, bookPublisher, bookPrice);
        }
    }

    public void registerStudent() throws SQLException {
        System.out.print("Enter register number: ");
        int registerNumber = scanner.nextInt();
        scanner.nextLine(); 
        System.out.print("Enter username: ");
        String username = scanner.nextLine();

        String query = "INSERT INTO user_details1 (REGNO, USERNAME, PASSWORD, USER_TYPE) VALUES (?, ?, ?, ?)";
        PreparedStatement statement = connection.prepareStatement(query);
        statement.setInt(1, registerNumber);
        statement.setString(2, username);
        statement.setString(3, "password_placeholder"); 
        statement.setInt(4, 1); 
        statement.executeUpdate();
        System.out.println("Student registered successfully.");
    }

    public void displayRegisteredStudents() throws SQLException {
        String query = "SELECT * FROM user_details1";
        PreparedStatement statement = connection.prepareStatement(query);
        ResultSet resultSet = statement.executeQuery();

        System.out.println("Registered Students:");
        System.out.println("Register Number\tName");
        while (resultSet.next()) {
            int registerNumber = resultSet.getInt("REGNO");
            String username = resultSet.getString("USERNAME");

            System.out.println(registerNumber + "\t\t" + username);
        }
    }

    public void rateBook() throws SQLException {
        System.out.print("Enter your user ID: ");
        int userId = scanner.nextInt();
        System.out.print("Enter the book ID you want to rate: ");
        int bookId = scanner.nextInt();
        System.out.print("Enter your rating (1 to 5 stars): ");
        int rating = scanner.nextInt();

        if (rating < 1 || rating > 5) {
            System.out.println("Invalid rating value. Please enter a rating between 1 and 5.");
            return;
        }

        String query = "INSERT INTO book_rating1 (user_id, book_id, rating) VALUES (?, ?, ?)";
        PreparedStatement statement = connection.prepareStatement(query);
        statement.setInt(1, userId);
        statement.setInt(2, bookId);
        statement.setInt(3, rating);
        statement.executeUpdate();

        System.out.println("Rating submitted successfully.");
    }

    public void closeConnection() {
        try {
            if (connection != null && !connection.isClosed()) {
                connection.close();
                System.out.println("Connection closed.");
            }
            scanner.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) throws SQLException {
        lib librarySystem = new lib();
        Scanner scanner = new Scanner(System.in);

        int choice = -1;

        while (choice != 0) {
            librarySystem.displayMenu();
            choice = scanner.nextInt();

            switch (choice) {
                case 1:
                    librarySystem.addBook();
                    break;
                case 2:
                    librarySystem.searchBook();
                    break;
                case 3:
                    librarySystem.showAllBooks();
                    break;
                case 4:
                    librarySystem.registerStudent();
                    break;
                case 5:
                    librarySystem.displayRegisteredStudents();
                    break;
                case 6:
                    librarySystem.rateBook();
                    break;
                case 7:
                    System.out.println("Exiting Application...");
                    break;
                default:
                    System.out.println("Invalid choice. Please try again.");
                    break;
            }
        }

        librarySystem.closeConnection();
        scanner.close();
    }
}
