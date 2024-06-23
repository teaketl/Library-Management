public class App {
    private static Menu menu; //instance of menu
    private static Library library; //instance of library
    public static void main(String[] args) throws Exception {
        FileHandler.loadUserData("adults.ser"); //load user info
        FileHandler.loadUserData("children.ser");
        FileHandler.loadBookData("books.ser"); //load book info
        library = FileHandler.loadLibrary("library.ser"); //load library info
        
        menu = new Menu(library); //instance of menu to call menus
        Admin admin = new Admin("admin","admin", "admin", 19); //create default admin

        menu.mainMenu(admin); //open main menu/start program

    }
}
