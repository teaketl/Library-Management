import java.io.Serializable;
import java.time.LocalDate;
import java.util.*; //for date and time

class Book implements Serializable {
    private static final long serialVersionUID = 1L;

    private String title;
    private String isbn; // book identifier
    private Genres genre; //enum list for easier catagorizing
    private boolean childPermission; //true = child allowed false = child not allowed to check out book
    private boolean checkedOut;
    private boolean onHold;
    private LocalDate dueDate;
    private User borrower;

    public Book(String title, String isbn, Genres genre, boolean childPermission) {
        this.title = title;
        this.isbn = isbn;
        this.genre = genre;
        this.childPermission = childPermission;
        this.checkedOut = false;
        this.onHold = false;
        this.dueDate = null;
        this.borrower = null;
    }

    //setters
    public void setTitle(String title) {
        this.title = title;
    }


    public void setIsbn(String isbn) {
        this.isbn = isbn;
    }

    public void setGenre(Genres genre){
        this.genre = genre;
    }


    public void setChildPermission(boolean childPermission) {
        this.childPermission = childPermission;
    }


    public void setCheckedOut(boolean checkedOut) {
        this.checkedOut = checkedOut;
    }

    public void setOnHold(boolean onHold) {
        this.onHold = onHold;
    }

    public void setDueDate(LocalDate dueDate) {
        this.dueDate = dueDate;
    }

    public void setBorrower(User borrower) {
        this.borrower = borrower;
    }

    //getters
    public String getTitle() {
        return title;
    }

    public String getISBN(){
        return isbn;
    }

    public Genres getGenre(){
        return genre;
    }

    public boolean getChildPermission() {
        return childPermission;
    }
    
    public LocalDate getDueDate() {
        return dueDate;
    }

    public boolean isCheckedOut() {
        return checkedOut;
    }

    public boolean isOnHold() {
        return onHold;
    }

    public User getBorrower() {
        return borrower;
    }

    //other methods
    public void placeOnHold(String isbn) { 
        Book book = Authenticate.findBookISBN(isbn); //remember to check permission in menu first then hold //check book isbn is riht
        if (book != null && !book.isCheckedOut() && !book.isOnHold()) { //if book not checked out, not on hold, not null
            book.setOnHold(true); //maybe come back and have fixed time for holdin book --> 2 days
            System.out.println("Book " + book.getTitle() + " has been placed on hold."); //confirmation message
        } else {
            System.out.println("Book " + title + " couldn't be placed on hold."); //user error message

        }
    }

    //remove book from hold
    public void removeHold() {
        this.onHold = false; //this book now not on hold (in plain terms)
        System.out.println("Hold removed from: " + title + "."); //user message
    }

    public static void extendHold(String isbn) { //extend hold on checked out book NOT on holds
        Book book = Authenticate.findBookISBN(isbn);
        if (book != null) {
            if (book.isCheckedOut()) { //must be on checked out to extend hold
                LocalDate originalDueDate = book.getDueDate(); //get original due date and add 7 days 
                LocalDate newDueDate = originalDueDate.plusDays(7); //plus 7 days
                book.setDueDate(newDueDate); //set as new due date
                System.out.println("Hold on " + book.getTitle() + " has been extended for 7 days.\n Please return the book on time, or late fees will be charged for each day the book is overdue. (0.50$)"); //user confirmation message
            } else {
                System.out.println("You have not checked out this book."); //user erre message
            }
        } else {
            System.out.println("Couldn't extend hold.");//user error message
        }
    }

    public void checkOut(String isbn, User user) {
        if (user instanceof Child && !checkedOut && !isOnHold() && childPermission && !((Child) user).hasFees()) { //not on hold, not checkout, yes child perm then check out
            if (Authenticate.findBookISBN(isbn) != null) { // if there is a book..
                Child childUser = (Child) user;
                LocalDate currentDate = LocalDate.now(); //get current time
                LocalDate dueDate = currentDate.plusDays(14); //plus 14 days to due date
                checkedOut = true; //offically checked out
                setDueDate(dueDate); //set due date 14 days later
                childUser.getBorrowedBooks().add(this);
                System.out.println("Book " + title + " was checked out by child user. Please return the book within 14 days, or a fee of 0.50$ will be charged per day late.\nOverdue books and late fees will result in an inability to check out future books."); //confirmation message //reminder to turn in
            } else {
                System.out.println("Book not available for checkout."); //in case of error message
            }
        } else if (user instanceof Adult && !checkedOut && !isOnHold() && !((Adult) user).hasFees()) { //if adult no need to check permission //check to make sure no fees are present
            if (Authenticate.findBookISBN(isbn) != null) { // see if the book exists
                Adult adultUser = (Adult) user; //cast adult user to call getBorrowedBooks()
                LocalDate currentDate = LocalDate.now();
                LocalDate dueDate = currentDate.plusDays(14);
                checkedOut = true; //offically checked out
                setDueDate(dueDate); //set due date 14 days later
                adultUser.getBorrowedBooks().add(this); //add book to borrowed books
                System.out.println("Book " + title + " was checked out by adult user. Please return the book within 14 days, or a fee of 0.50$ will be charged per day late.\nOverdue books and late fees will result in an inability to check out future books."); //confirmation message //child vs adult for clarity
            } else {
                System.out.println("Book not available for checkout."); //user message
            }
        } else {
            System.out.println("This book is either already checked out or not suitable for the user."); //user message if checked out or not child friendly
        }
    }
    public void returnBook(String isbn) {
        if (isbn.equals(this.isbn)) {
            if (checkedOut) {
                checkedOut = false; //set everything back to before checkedout status
                dueDate = null;
                onHold = false;
                Library.getInstance().addBook(this); // add the book back to the library (only one library)
                System.out.println("Book " + title + " returned successfully."); //user message
            } else {
                System.out.println("Book is not currently checked out."); //user message
            }
        } else {
            System.out.println("ISBN does not match the book being returned."); //user message
        }
    }

    public void addToWishlist(User user) {
        if (user instanceof Adult) {
            Adult adultUser = (Adult) user;
            List<Book> wishlist = adultUser.getWishlist(); //get listlist and add book --> same for child instance of User
            wishlist.add(this);
            System.out.println("Book added to your wishlist."); //confirmation
        } else if (user instanceof Child) {
            Child childUser = (Child) user;
            List<Book> wishlist = childUser.getWishlist(); 
            wishlist.add(this);
            System.out.println("Book added to your wishlist."); //confirmation
        } else {
            System.out.println("Couldn't add book to wishlist."); //if error occurs or already checkout, etc
        }
    }
}
