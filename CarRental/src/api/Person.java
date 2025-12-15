package api;

public class Person {


    /*
     * Ο Employee και ο Customer
     * Μοιράζονται τις ίδιες ιδιότητες
     * Το ονοματεπώνυμο και το email.
     * Οπότε δημιουργούμε μια κλάση Person
     * Η οποία θα είναι η Parent και οι άλλες θα υιοθετούν τα χαρακτηριστικά αυτής.
     */

    private String fullname;
    private String email;

    // Constructor - Κατασκευαστής
    Person(String fullname, String email){
        this.fullname = fullname;
        this.email = email;
    }

    //Οι Getters μέθοδοι
    public String getFullname() {return fullname;}
    public String getEmail() {return email;}
    public String getName(){
        String[] parts =  getFullname().split(" ");
        return parts[0];
    }
    public String getSurname(){
        String[] parts =  getFullname().split(" ");
        return parts[1];
    }
}
