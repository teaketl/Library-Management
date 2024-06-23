import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

class Library implements Serializable {
    private static final long serialVersionUID = 1L;
    private static Library instance;
    private static List<Book> books = new ArrayList<>(); //all books held in library static so admin can change and can be used in other functions

    Library() {
    }
    
    //setters
    public static void setBooks(List<Book> books) {
        Library.books = books;
    }

    public static void setInstance(Library instance) {
        Library.instance = instance;
    }

    //getters
    public static List<Book> getBooks() {
        return books;
    }

    //other methods

    public static Library getInstance() { //ensures only one library for whole program
        if (instance == null) {
            instance = new Library(); //instance of library if theres not one
            setInstance(instance); //set instance
        }
        return instance; 
    }

    public void addBook(Book book) {
        books.add(book); //add books to overall library books
    }

    public void removeBook(Book book) {
        if (books.contains(book)) { //if book in List<Book> books remove it //doesnt delete book just makes it so no one can check out //used for delete book in Admin
            books.remove(book);
            System.out.println(book.getTitle() + " has been removed from the library."); //user confirmation message
        } else {
            System.out.println("Couldn't be removed from library"); //user error message
        }
    }

    public void displayBooks() { //for debugging use //not used in program bc it could potentially be extremely long
        System.out.println("Books in catalogue:");
        for (Book book : books) { //loop through all books 
            System.out.println("Title: " + book.getTitle() + "Checked Out: " + book.isCheckedOut());
        }
    }

    public void displayBooksByGenre(String genreName) { //instead of just printing all books
        System.out.println("Books in " + genreName + ": ");
        boolean found = false;
        for (Book book : books) { //look throuh all books
            if (book.getGenre().toString().equalsIgnoreCase(genreName)) { //make string & print out info //learned toString from Geeks for Geeks //https://www.geeksforgeeks.org/java-program-to-convert-enum-to-string/
                System.out.println("Title: " + book.getTitle() + " ISBN: " + book.getISBN());
                found = true;
            }
        }
        if (!found) {
            System.out.println("No books found in " + genreName + " genre.");
        }
    }
}
