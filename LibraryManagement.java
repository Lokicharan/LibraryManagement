import java.util.*;

class Book {
    private int id;
    private String title;
    private String author;
    private boolean isIssued;

    public Book(int id, String title, String author) {
        this.id = id;
        this.title = title;
        this.author = author;
        this.isIssued = false;
    }

    public int getId() { return id; }
    public String getTitle() { return title; }
    public String getAuthor() { return author; }
    public boolean isIssued() { return isIssued; }
    public void setIssued(boolean issued) { this.isIssued = issued; }

    @Override
    public String toString() {
        return "Book ID: " + id + ", Title: " + title + ", Author: " + author +
                ", Status: " + (isIssued ? "Issued" : "Available");
    }
}

// User class
class User {
    private int userId;
    private String name;
    private List<Book> issuedBooks;

    public User(int userId, String name) {
        this.userId = userId;
        this.name = name;
        this.issuedBooks = new ArrayList<>();
    }

    public int getUserId() { return userId; }
    public String getName() { return name; }
    public List<Book> getIssuedBooks() { return issuedBooks; }

    public void issueBook(Book book) {
        issuedBooks.add(book);
    }

    public void returnBook(Book book) {
        issuedBooks.remove(book);
    }

    public String toString() {
        return "User ID: " + userId + ", Name: " + name + ", Books Issued: " + issuedBooks.size();
    }
}

class Library {
    private List<Book> books;
    private List<User> users;

    public Library() {
        books = new ArrayList<>();
        users = new ArrayList<>();
    }

    public void addBook(Book book) {
        books.add(book);
        System.out.println("Book added successfully!");
    }

    public void addUser(User user) {
        users.add(user);
        System.out.println("User added successfully!");
    }

    public void showAllBooks() {
        if (books.isEmpty()) {
            System.out.println("No books available.");
            return;
        }
        System.out.println("\n--- Book List ---");
        for (Book b : books) {
            System.out.println(b);
        }
    }

    public void showAllUsers() {
        if (users.isEmpty()) {
            System.out.println("No users found.");
            return;
        }
        System.out.println("\n--- User List ---");
        for (User u : users) {
            System.out.println(u);
        }
    }

    public void issueBook(int userId, int bookId) {
        User user = findUserById(userId);
        Book book = findBookById(bookId);

        if (user == null) {
            System.out.println("User not found!");
            return;
        }
        if (book == null) {
            System.out.println("Book not found!");
            return;
        }
        if (book.isIssued()) {
            System.out.println("Sorry, this book is already issued.");
            return;
        }

        book.setIssued(true);
        user.issueBook(book);
        System.out.println("Book '" + book.getTitle() + "' issued to " + user.getName());
    }

    public void returnBook(int userId, int bookId) {
        User user = findUserById(userId);
        Book book = findBookById(bookId);

        if (user == null) {
            System.out.println("User not found!");
            return;
        }
        if (book == null) {
            System.out.println("Book not found!");
            return;
        }
        if (!book.isIssued() || !user.getIssuedBooks().contains(book)) {
            System.out.println("This user didn’t issue this book.");
            return;
        }

        book.setIssued(false);
        user.returnBook(book);
        System.out.println("Book '" + book.getTitle() + "' returned by " + user.getName());
    }

    private User findUserById(int id) {
        for (User u : users) {
            if (u.getUserId() == id)
                return u;
        }
        return null;
    }

    private Book findBookById(int id) {
        for (Book b : books) {
            if (b.getId() == id)
                return b;
        }
        return null;
    }
}

public class LibraryManagement {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        Library library = new Library();
        int choice;

        do {
            System.out.println("\n=== LIBRARY SYSTEM MENU ===");
            System.out.println("1. Add Book");
            System.out.println("2. Add User");
            System.out.println("3. View All Books");
            System.out.println("4. View All Users");
            System.out.println("5. Issue Book");
            System.out.println("6. Return Book");
            System.out.println("7. Exit");
            System.out.print("Enter your choice: ");
            choice = sc.nextInt();

            switch (choice) {
                case 1 -> {
                    System.out.print("Enter Book ID: ");
                    int id = sc.nextInt();
                    sc.nextLine();
                    System.out.print("Enter Book Title: ");
                    String title = sc.nextLine();
                    System.out.print("Enter Author: ");
                    String author = sc.nextLine();
                    library.addBook(new Book(id, title, author));
                }
                case 2 -> {
                    System.out.print("Enter User ID: ");
                    int userId = sc.nextInt();
                    sc.nextLine();
                    System.out.print("Enter User Name: ");
                    String name = sc.nextLine();
                    library.addUser(new User(userId, name));
                }
                case 3 -> library.showAllBooks();
                case 4 -> library.showAllUsers();
                case 5 -> {
                    System.out.print("Enter User ID: ");
                    int uId = sc.nextInt();
                    System.out.print("Enter Book ID: ");
                    int bId = sc.nextInt();
                    library.issueBook(uId, bId);
                }
                case 6 -> {
                    System.out.print("Enter User ID: ");
                    int uId = sc.nextInt();
                    System.out.print("Enter Book ID: ");
                    int bId = sc.nextInt();
                    library.returnBook(uId, bId);
                }
                case 7 -> System.out.println("Exiting... Thank you!");
                default -> System.out.println("Invalid choice! Try again.");
            }
        } while (choice != 7);
    }
}
