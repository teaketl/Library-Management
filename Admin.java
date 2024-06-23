import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

class Admin extends User {
    private static final long serialVersionUID = 1L;
    private static List<Child> children = new ArrayList<>(); //list of all kids
    private static List<Adult> adults = new ArrayList<>(); //list of adults

    //constructor
    public Admin(String name, String username, String password, int age) {
        super(name, username, password, age);
    }

    //settrs
    public static void setChildren(List<Child> children) {
        Admin.children = children;
    }

    public static void setAdults(List<Adult> adults) {
        Admin.adults = adults;
    }

    //gettrs
    public static List<Child> getChildren() {
        return children;
    }

    public static List<Adult> getAdults() {
        return adults;
    }

    //other methods
    public static void createNewBook(Library library) { //duplicate books allowed
        Scanner input = new Scanner(System.in); //take input
        System.out.println("Enter book title: ");
        String title = input.nextLine();
        System.out.println("Enter book isbn: ");
        String isbn = input.nextLine();
        System.out.println("Enter book genre: ");
        Genres genre = Genres.valueOf(input.nextLine().toUpperCase());
        System.out.println("Can kids check out this book? (true/false): ");
        boolean childrenPermission = input.nextBoolean();
        Book newBook = new Book(title, isbn, genre, childrenPermission);
        library.addBook(newBook);
        System.out.println("New book " + title + " added.");
    }

    public static void createChild() {
        Scanner input = new Scanner(System.in); //take input
        System.out.println("Enter name: ");
        String childName = input.nextLine();
        System.out.println("Enter username: ");
        String childUsername = input.nextLine();
        System.out.println("Enter password: ");
        String childPassword = input.nextLine();
        System.out.println("Enter age: "); 
        int childAge = Integer.parseInt(input.nextLine());
        Child newChild = new Child(childName,childUsername,childPassword, childAge);
        LibraryCard libraryCard = new LibraryCard();
        LibraryCard.createCardNumber(); //new card number for child
        newChild.setLibraryCard(libraryCard); //set new library card
        children.add(newChild); //add to children list in admin
        System.out.println("New child user " + childName + " created."); //confirmation message
    }

    public static void createAdult() {
        Scanner input = new Scanner(System.in); //take input
        System.out.println("Enter name: ");
        String adultName = input.nextLine();
        System.out.println("Enter username: ");
        String adultUsername = input.nextLine();
        System.out.println("Enter password: ");
        String adultPassword = input.nextLine();
        System.out.println("Enter age: ");
        int adultAge = Integer.parseInt(input.nextLine());
        Adult newAdult = new Adult(adultName, adultUsername, adultPassword, adultAge); //create instance
        LibraryCard libraryCard = new LibraryCard(); //create library card instance
        LibraryCard.createCardNumber(); //new card number for adult
        newAdult.setLibraryCard(libraryCard); //set new library card
        adults.add(newAdult); //add to admin adults list
        System.out.println("New adult user " + adultName + " created."); ///confirmation message
    }

    public static void deleteBook(String isbn) {
        Book bookToDelete = Authenticate.findBookISBN(isbn); //check if book is in library
        if (bookToDelete != null) {
            Library.getInstance().removeBook(bookToDelete); //remove book from instance of library //no confirmation message theres one in removeBook();
        } else {
            System.out.println("Couldn't delete book."); //user error message
        }
    }

    public static void deleteChild(String username) {
        Child childToDelete = Authenticate.findChildUsername(username); //check if child is in program
        if(childToDelete != null) { //if they are then remove
            children.remove(childToDelete);
        System.out.println(childToDelete.getName() + " has been deleted."); //confirmation message
        } else {
            System.out.println("Error occurred trying to delete user."); //user error message
        }
    }

    public static void deleteAdult(String username) {
        Adult adultToDelete = Authenticate.findAdultUsername(username); //check if adult is in program
        if(adultToDelete != null) { //if they are then remove
            adults.remove(adultToDelete); //remove from list
        System.out.println(adultToDelete.getName() + " has been deleted."); //confirmation message
        } else {
            System.out.println("Error occurred trying to delete user."); //user error message
        }
    }

    private static int calculateOverdueDays(Book book) {
        if (book.getDueDate() != null) { // if there is a due date
            LocalDate currentDate = LocalDate.now(); //get current time
            LocalDate dueDate = book.getDueDate(); //check due date
            int overdueDays = currentDate.compareTo(dueDate);
            if (overdueDays > 0) {  //if now it is past due date
                return overdueDays;
            }
        }
        return 0;
    }

    public static void updateFees(List<Book> borrowedBooks) { //input respective borrowedBooks listsfrom child/adult
        for (Book book : borrowedBooks) {
            User borrower = book.getBorrower(); //return user borrowing book --> increase their fees
            if (borrower instanceof Child) {
                Child child = (Child) borrower;
                int overdueDays = calculateOverdueDays(book); //get overdue days 
                if (overdueDays > 0 && overdueDays <= 30) { //greater than 0 = overdue //stop at 30 days
                    double overdueFee = overdueDays * 0.50; // 50 cents each day overdue
                    child.setFees(child.getFees() + overdueFee); //add fees to existign fees
                    System.out.println("You are overdue on " + book.getTitle() + " by " + overdueDays); 
                    System.out.println("Your fees have been updated. " + child.getFees());
                } else {
                    System.out.println("You have no overdue books!");
                }
            } else if (borrower instanceof Adult) {
                Adult adult = (Adult) borrower;
                int overdueDays = calculateOverdueDays(book);
                if (overdueDays > 0 && overdueDays <= 30) {
                    double overdueFee = overdueDays * 0.50; // .50 each day overdue //stop at 30 days
                    adult.setFees(adult.getFees() + overdueFee);
                    System.out.println("You are overdue on " + book.getTitle() + " by " + overdueDays);
                    System.out.println("Your fees have been updated. " + adult.getFees());
                } else {
                    System.out.println("You have no overdue books!"); //yippee
                }
            }
        }
    }

    public static void payFees(User user, double amount) {
        if (user instanceof Child) {
            Child child = (Child) user;
            if (child.getFees() >= amount) { //ensures theyre paying an amount they owe (ie cant be 0)
                child.setFees(child.getFees() - amount); //subtract fees
                System.out.println("Successfully paid $" + amount + " of library fees for " + child.getName() + ".");
                System.out.println("Your fees are now: " + child.getFees());
            } else {
                System.out.println("Couldn't pay library fees for " + child.getName() + ".");
            }
        } else if (user instanceof Adult) {
            Adult adult = (Adult) user;
            if (adult.getFees() >= amount) {
                adult.setFees(adult.getFees() - amount);
                System.out.println("Successfully paid $" + amount + " of library fees for " + adult.getName() + ".");
                System.out.println("Your fees are now: " + adult.getFees());
            } else {
                System.out.println("Couldn't pay library fees for " + adult.getName() + ".");
            }
        } else {
            System.out.println("Error trying to pay fees. Please try again.");
        }
    }

    public static void editChildFee(String username, double newFee) {
        Child child = Authenticate.findChildUsername(username); //check child exists
        if (child != null) { //if they do
            child.setFees(newFee); //set fee entered in
            System.out.println("Fee for child user " + username + " updated to $" + newFee);
        } else {
            System.out.println("Couldn't edit fees.");
        }
    }

    public static void editAdultFee(String username, double newFee) {
        Adult adult = Authenticate.findAdultUsername(username);
        if (adult != null) {
            adult.setFees(newFee);
            System.out.println("Fee for adult user " + username + " updated to $" + newFee);
        } else {
            System.out.println("Couldn't edit fees.");
        }
    }

    public static void changeUserPermission(String username) {
        Child child = Authenticate.findChildUsername(username); ///authenticate // checks if adult or child whatever != null is the changing user
        Adult adult = Authenticate.findAdultUsername(username);
        if (child != null) {
            children.remove(child); //remove from list and add to new list
            Adult newAdult = new Adult(child.getName(), child.getUsername(), child.getPassword(), child.getAge()); //create new adult
            adults.add(newAdult); 
            System.out.println("User " + username + " permission changed from child to adult.");
        } else if (adult != null) {
            adults.remove(adult); //remove from list and add to new list
            Child newChild = new Child(adult.getName(), adult.getUsername(), adult.getPassword(), adult.getAge()); //create new child
            children.add(newChild); //add to new list
            System.out.println("User " + username + " permission changed from adult to child.");
        } else {
            System.out.println("Couldn't change permission.");
        }
    }
}
