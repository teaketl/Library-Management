import java.io.Serializable;
import java.util.Scanner;

class Menu implements Serializable {
    private static final long serialVersionUID = 1L;

    private Library library;

    public Menu(Library library) {
        this.library = library;
    }

    //display main menu
    public void mainMenu(Admin admin){ 
        Scanner scanner = new Scanner(System.in);

        System.out.println("Welcome to the Library."); ///welcome message
        while (true) {
            System.out.println("---Main Menu---"); //different options
            System.out.println("1. Child Login");
            System.out.println("2. Adult Login");
            System.out.println("3. Admin Login");
            System.out.println("4. Exit");
            System.out.print("Enter your choice: "); //get input
            int choice = scanner.nextInt();
            scanner.nextLine();

            switch (choice) {
                case 1: //kid login
                    System.out.println("Enter username: ");
                    String childUsername = scanner.nextLine();
                    System.out.println("Enter password: ");
                    String childPassword = scanner.nextLine();
                    if(Authenticate.childLogin(childUsername, childPassword)){
                        Child childUser = Authenticate.findChildUsername(childUsername);
                        if (childUser != null) {
                            userMenu(childUser);
                        } else {
                            System.out.println("Child login failed. Please try again."); //catch if login fails -> no error message //keep messages for each user similar
                        }
                    }
                    break;
                case 2: //adult login
                    System.out.println("Enter username: ");
                    String adultUsername = scanner.nextLine();
                    System.out.println("Enter password: ");
                    String adultPassword = scanner.nextLine();
                    if(Authenticate.adultLogin(adultUsername, adultPassword)){ //validate login make sure not null
                        Adult adultUser = Authenticate.findAdultUsername(adultUsername);
                        if (adultUser != null) {
                            userMenu(adultUser); //logged in
                        } else {
                            System.out.println("Adult login failed. Please try again."); //catch if login fails -> no error message
                        }
                    }
                    break;
                case 3: //admin login
                    System.out.println("Enter username: ");
                    String adminUsername = scanner.nextLine();
                    System.out.println("Enter password: ");
                    String adminPassword = scanner.nextLine();
                    if(Authenticate.adminLogin(adminUsername, adminPassword, admin)){ //check (admin built in to program)
                        adminMenu(); //logged in
                    } else {
                        System.out.println("Admin login failed. Please try again."); //catch if login fails -> no error message
                    }  
                    break;
                case 4:
                    System.out.println("Exiting...");
                    FileHandler.saveLibraryData(library, "library.ser"); //save library (serializing everything)
                    FileHandler.saveBookData(Library.getBooks(), "books.ser"); //save books
                    FileHandler.saveUserData(Admin.getAdults(), "adults.ser"); //save adults
                    FileHandler.saveUserData(Admin.getChildren(), "children.ser"); //save children
                    scanner.close(); //close scanner
                    System.exit(0); //leave program only in main menu
                default:
                    System.out.println("Please enter a number between 1 and 4."); //make sure user enters something that is an option
            }
        }
    }

    public boolean userMenu(User currentUser){ //menu for all
        Scanner input = new Scanner(System.in);
        int choice = 0;
        while (choice != 11) {
            System.out.println("---User Menu---"); //fancy flair
            System.out.println("1. View books by genre");
            System.out.println("2. Check out a new book");
            System.out.println("3. Return a book");
            System.out.println("4. Add a book to wishlist");
            System.out.println("5. Put a book on hold");
            System.out.println("6. View loaned books");
            System.out.println("7. View library fees");
            System.out.println("8. Extend hold on a book");
            System.out.println("9. Pay library fees");
            System.out.println("10. Renew library card"); //give new random number that doesnt match any others
            System.out.println("11. Exit");
            choice = input.nextInt();
            input.nextLine();
            switch (choice) {
                case 1: //view books by genre
                    System.out.println("Enter the genre of the book you want to view:");
                    String genre = input.nextLine();
                    library.displayBooksByGenre(genre);
                    break;
                case 2:
                    System.out.println("Enter the ISBN of the book you want to checkout: ");
                    String isbn = input.nextLine();
                    Book bookToCheckout = Authenticate.findBookISBN(isbn);
                    if (bookToCheckout != null) {
                        bookToCheckout.checkOut(isbn, currentUser);
                    } else {
                        System.out.println("Book not found. Please enter a valid ISBN.");
                    }
                    break;
                case 3: //return a book
                    System.out.println("Enter the ISBN of the book you want to return: ");
                    String isbnReturn = input.nextLine();
                    Book bookToReturn = Authenticate.findBookISBN(isbnReturn);
                    if (bookToReturn != null) {
                        bookToReturn.returnBook(isbnReturn);
                    } else {
                        System.out.println("Book not found. Please enter a valid ISBN.");
                    }
                    break;
                case 4: //add book to wishlist
                    System.out.println("Enter the ISBN of the book you want to add to your wishlist: ");
                    String wishlistISBN = input.nextLine();
                    Book wishlistBook = Authenticate.findBookISBN(wishlistISBN);
                    if (wishlistBook != null) {
                        wishlistBook.addToWishlist(currentUser);
                    } else {
                        System.out.println("Book not found. Please enter a valid ISBN.");
                    }
                    break;
                case 5: //put book on hold 
                    System.out.println("Enter the ISBN of the book you want to put on hold: ");
                    String holdISBN = input.nextLine();
                    Book bookToHold = Authenticate.findBookISBN(holdISBN);
                    if (bookToHold != null) {
                        bookToHold.placeOnHold(holdISBN);
                        System.out.println("Book " + bookToHold.getTitle() + " has been put on hold.");
                    } else {
                        System.out.println("Book with ISBN " + holdISBN + " not found.");
                    }
                    break;
                case 6: //view loaned books
                    if (currentUser instanceof Child) {
                        Child childUser = (Child) currentUser;
                        System.out.println("Loading child loaned books...");
                        childUser.viewLoanedBooks();
                    } else if (currentUser instanceof Adult) {
                        Adult adultUser = (Adult) currentUser;
                        System.out.println("Loading adult loaned books...");
                        adultUser.viewLoanedBooks();
                    } else {
                        System.out.println("Error happened.."); //if error w user type //cant be admin
                    }
                    break;
                case 7: //view fees
                    if (currentUser instanceof Child) {
                        Child childUser = (Child) currentUser;
                        System.out.println("Loading child fees...");
                        childUser.viewFees();
                    } else if (currentUser instanceof Adult) {
                        Adult adultUser = (Adult) currentUser;
                        System.out.println("Loading adult fees...");
                        adultUser.viewFees();
                    } else {
                        System.out.println("Error happened.."); //if error w user type //cant be admin
                    }
                    break;
                case 8: //extend hold on book (checkedout book)
                    System.out.println("Enter the ISBN of the book you want to extend hold on: ");
                    String extendHoldISBN = input.nextLine();
                    Book.extendHold(extendHoldISBN);
                    break;
                case 9: //pay fees
                    System.out.println("Enter the amount you're paying: ");
                    double amountToPay = input.nextDouble();
                    Admin.payFees(currentUser, amountToPay);
                    break;
                case 10: //renew card
                    System.out.println("Choosing the perfect library card...");
                    LibraryCard.renewLibraryCard(currentUser);
                    break;        
                case 11: //exit to main menu
                    System.out.println("Exiting to main menu...");
                    return true;
                default:
                    System.out.println("Please enter a number between 1 and 11."); //make sure user enters something that is an option
            }
        }
        input.close();
        return false; //not returning to main menu --> not serializing
    }

    public boolean adminMenu() {
        Scanner input = new Scanner(System.in);
        int choice = 0;
        while (choice != 10) {
            System.out.println("---Admin Menu---"); //fancy flair
            System.out.println("1. Enter a new book");
            System.out.println("2. Delete a book");
            System.out.println("3. Edit Child's Fee");
            System.out.println("4. Edit Adult's Fee");
            System.out.println("5. Change user's permission");
            System.out.println("6. Create Child User");
            System.out.println("7. Create Adult User");
            System.out.println("8. Delete Child User");
            System.out.println("9. Delete Adult User");
            System.out.println("10. Exit");
            choice = input.nextInt();
            input.nextLine();
            switch (choice) {
                case 1:
                    Admin.createNewBook(library);
                    break;
                case 2:
                    System.out.println("Enter the ISBN of the book to delete: ");
                    String isbnToDelete = input.nextLine();
                    Admin.deleteBook(isbnToDelete);
                    break;
                case 3:
                    System.out.println("Enter the username of the child user: ");
                    String childUsername = input.nextLine();
                    System.out.println("Enter the new fee for the child user: ");
                    double newChildFee = input.nextDouble();
                    input.nextLine(); // bck 2 next ln
                    Admin.editChildFee(childUsername, newChildFee);
                    break;
                case 4:
                    System.out.println("Enter the username of the adult user: ");
                    String adultUsername = input.nextLine();
                    System.out.println("Enter the new fee for the adult user: ");
                    double newAdultFee = input.nextDouble();
                    input.nextLine(); // bck 2 next ln
                    Admin.editAdultFee(adultUsername, newAdultFee);
                    break;
                case 5:
                    System.out.println("Enter the username of the user whose permission you want to change: ");
                    String username = input.nextLine();
                    Admin.changeUserPermission(username);
                    break;
                case 6:
                    Admin.createChild();
                    break;
                case 7:
                    Admin.createAdult();
                    break;
                case 8:
                    System.out.println("Enter the username of the child you want to delete: ");
                    String deleteChildUsername = input.nextLine();
                    Admin.deleteChild(deleteChildUsername);
                    break;
                case 9:
                    System.out.println("Enter the username of the adult you want to delete: ");
                    String deleteAdultUsername = input.nextLine();
                    Admin.deleteAdult(deleteAdultUsername);
                    break;
                case 10:
                    System.out.println("Exiting to main menu...");
                    return true; //back to main menu
                default:
                    System.out.println("Please enter a number between 1 and 10."); //make sure user enters something that is an option
            }
        }
    input.close();
    return false; //not returning to main menu --> not serializing
    }
}
