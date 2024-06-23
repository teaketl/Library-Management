import java.io.*;
import java.util.List;

class Authenticate implements Serializable { //serializable bc it uses <User>
    private static final long serialVersionUID = 1L;
    private List<User> users; //list of users

    //setter
    public void setUsers(List<User> users) {
        this.users = users;
    }

    //getter
    public List<User> getUsers() {
        return users;
    }

    //methods for authenticating children and adults + bookISBN + allIDs(library cards) recycled from School Management
    public static Child findChildUsername(String username) {
        System.out.println("Searching for child's username..."); //loading message
        for(Child child : Admin.getChildren()) { //checks all children
            if(child.getUsername().equals(username)){ 
                System.out.println("Username found..."); //confirmation //allows to debug
                return child;
            }
        }
        return null; //can be easily used later .. something !=null 
    }
    public static Adult findAdultUsername(String adultUsername) {
        System.out.println("Searching for adult's username..."); //loading message
        for(Adult adult : Admin.getAdults()) {
            if(adult.getUsername().equals(adultUsername)){ 
                System.out.println("Adult username found..."); //confirmation //allows to debug
                return adult;
            }
        }
        return null; //can be easily used later .. something !=null 
    }
    public static Book findBookISBN(String isbn) {
        System.out.println("Searching for book's isbn..."); //loading message
        for(Book book : Library.getBooks()) {
            if(book.getISBN().equals(isbn)){ 
                System.out.println("Book isbn found..."); //confirmation //allows to debug
                return book;
            }
        }
        return null; //can be easily used later .. something !=null 
    }

    //authenticate logins childten, admins, adults //all boolean for easy reading 
    public static boolean childLogin(String username, String password){
        List<Child> kids = Admin.getChildren();
        for(Child child : kids){
            if(child.getUsername().equals(username) && child.getPassword().equals(password)){
                System.out.println("Child: " + child.getUsername() + " successfully logged in.");
                return true;
            }
        }
        System.out.println("Incorrect username or password.");
        return false;
    }

    public static boolean adultLogin(String username, String password){
        List<Adult> adults = Admin.getAdults(); // loop through admin adult list
        for (Adult adult : adults) { //loop through adults to find teacher
            if(adult.getUsername().equals(username) && adult.getPassword().equals(password)){
                return true;
            }
        }
        System.out.println("Incorrect username or password."); //user message
        return false;
    }

    public static boolean adminLogin(String username, String password, Admin admin){     //create admin here bc no admin to loop through theres only one
        if (admin.getUsername().equals(username) && admin.getPassword().equals(password)){ //putting in user and pass ("admin" and "admin") were allowing user to login w/o right --> maybe something to do with string so that fixed
            System.out.println("Admin: " + admin.getUsername() + " successfully logged in.");
            return true;
        } else {
            System.out.println("Incorrect username or password.");//user message
            return false;
        }
    }
}
