import java.util.ArrayList;
import java.util.List;

class Adult extends User{
    private static final long serialVersionUID = 1L;
    private double fees;
    private List<Book> borrowedBooks = new ArrayList<>();
    private List<Book> wishlist = new ArrayList<>();
    private LibraryCard libraryCard; //priv causes error..


    public Adult(String name, String username, String password, int age) {
        super(name, username, password, age);
        this.fees = 0.0; //starting value of fees = 0
    }

    //setters
    public void setFees(double fees) {
        this.fees = fees;
    }

    public void setBorrowedBooks(List<Book> borrowedBooks) {
        this.borrowedBooks = borrowedBooks;
    }

    public void setLibraryCard(LibraryCard libraryCard) {
        this.libraryCard = libraryCard;
    }

    public void setWishlist(List<Book> wishlist){
        this.wishlist = wishlist;
    }

    //getters
    public double getFees() {
        return fees;
    }
    
    public List<Book> getBorrowedBooks() {
        return borrowedBooks;
    }

    public LibraryCard getLibraryCard() {
        return libraryCard;
    }

    public List<Book> getWishlist(){
        return wishlist;
    }

    public boolean hasFees(){ //does user have fees (copied over from Child.java)
        if(getFees() > 0){ //if getFees more than 0 cant checkout/put books on hold
            return true; //has fees
        }
        return false; //has no fees
    }

    public void viewFees() {
        System.out.println("Your current fees: $" + getFees());
        System.out.println("Please ensure you're returning your library books on time!"); //ffriendly remindeer
    }

    public void viewWishlist(){
        if (wishlist != null && !wishlist.isEmpty()) {
            for(Book book : wishlist){ //loop through wishlist books
                System.out.println("Book " + book.getTitle() + " ISBN: " + book.getISBN());
            }
        } else {
            System.out.println("Wishlist is empty."); //user message
        }
    }

    public void viewLoanedBooks(){
        if(borrowedBooks != null && !borrowedBooks.isEmpty()){ //check if there stuff in list
            for(Book book : borrowedBooks){
                System.out.println("Book " + book.getTitle() + " ISBN: " + book.getISBN() + " Due Date: " + book.getDueDate());
            }
        } else {
            System.out.println("You have yet to check out any books."); //user message
        }
    }
}
