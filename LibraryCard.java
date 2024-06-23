import java.io.Serializable;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Random;
import java.util.Set;

public class LibraryCard implements Serializable {
    private static final long serialVersionUID = 1L;

    private static final int MIN_CARD_NUMBER = 10000000; //must be big numbers since there will be lots of users //may make bigger
    private static final int MAX_CARD_NUMBER = 99999999; //want all to be 8 nums long

    private static Set<Integer> usedCardNumbers = new HashSet<>(); //keep a collection of used nums

    private int cardNumber;
    private LocalDate expirationDate;

    public LibraryCard() {
        this.cardNumber = createCardNumber();
        this.expirationDate = LocalDate.now().plusYears(1); //when library card expires (every year)
    }

    //setters
    public static void setUsedCardNumbers(Set<Integer> usedCardNumbers) {
        LibraryCard.usedCardNumbers = usedCardNumbers;
    }

    public void setCardNumber(int cardNumber) {
        this.cardNumber = cardNumber;
    }

    public void setExpirationDate(LocalDate expirationDate) {
        this.expirationDate = expirationDate;
    }

    //getters
    public int getCardNumber() {
        return cardNumber;
    }

    public LocalDate getExpirationDate() {
        return expirationDate;
    }

    //other methods

    public void renew() {
        expirationDate = expirationDate.plusYears(1); //add 1yr to expiryDate (specific for my museums memberships)
    }

    public static int createCardNumber() {
        int newCardNum; //initialize here --> get num in loop so it keeps getting new nums each loop
        do {
            newCardNum = generateRandomCardNumber();
        } while (!usedCardNumbers.add(newCardNum)); // keeps creatin nums til a new card number is created
        return newCardNum;
    }

    public static int generateRandomCardNumber() {
        Random random = new Random(); //new rand num
        return random.nextInt(MAX_CARD_NUMBER - MIN_CARD_NUMBER + 1) + MIN_CARD_NUMBER;  //[100000, 999999] range
    }

    public static void renewLibraryCard(User user) {
        if (user instanceof Child) { //check instances of user adult/child
            Child child = (Child) user;
            if (child.getLibraryCard() != null) { //cant renew if it doesnt exist
                child.getLibraryCard().renew(); //renew if it does exist
                System.out.println("Library card renewed for 1 year"); //confirmation
            } else {
                System.out.println("No library card found for child user: " + child.getUsername()); //error or null card
            }
        } else if (user instanceof Adult) {
            Adult adult = (Adult) user;
            if (adult.getLibraryCard() != null) { //cant renew if it doesnt exist
                adult.getLibraryCard().renew(); //renew if it does exist
                System.out.println("Library card renewed for 1 year"); //confirmation
            } else {
                System.out.println("No library card found for adult user: " + adult.getUsername()); //error or null card
            }
        }
    }
}
