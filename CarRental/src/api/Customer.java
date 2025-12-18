package api;

import filemanager.Paths;
import filemanager.Reader;
import filemanager.Writer;

import java.util.ArrayList;
import java.util.List;

public class Customer extends Person{


    //Attributes - Ιδιότητες
    private String AFM;
    private String phoneNumber;
    private static final String[] header = new String[]{"ΑΦΜ,ονοματεπωνυμο,τηλεφωνο,email"}; // Η επικεφαλιδα του customers.csv
    private static final String Path = Paths.getCustomersPath(); // Το path (μονοπατι) για την προσβαση στο αρχειο customers.csv
    private static final String rentsPath = Paths.getRentsPath();
    //Constructor - Κατασκευαστής
    public Customer(String AFM, String fullname, String phoneNumber, String email) {
        super(fullname,email); // Τα υιοθετεί απο την κλάση Person
        this.AFM = AFM;
        this.phoneNumber = phoneNumber;
    }

    // Μέθοδοι Getters
    public String getAFM() {return AFM;}
    public String getPhoneNumber() {return phoneNumber;}
    /*
    Για το fullname και το email
    Δεν χρειαζόνται γιατί υιοθετούνται
    Από την κλάση Person
     */


    public static boolean addCustomer(Customer customer){
        /*
        Μεθοδος η οποια προσθετει εναν πελατη στο αρχειο customers.csv
         */
        Reader reader = new Reader(Path);
        String[][] Customers = reader.read(); // Διαβαζουμε ολους τους πελατες ωστε να ελεγξουμε εαν υπαρχει ηδη καταχωρημενος ο ιδιος πελατης
        boolean customerAdded = false;
        for(int i = 0; i < Customers.length; i++){
            if(Customers[i][0].equals(customer.getAFM())){ // Εδω γινεται ο ελεγχος
                System.out.println("Customer already exists");
                return false;
            }
        }
        Writer writer = new Writer(Path);
        String[] CSVLine = new String[]{ // Με ποια σειρα γινεται η ενταξη στο αρχειο
                customer.getAFM(), // ΑΦΜ
                customer.getFullname(), // Ονοματεπωνυμο
                customer.getPhoneNumber(), // Τηλεφωνο
                customer.getEmail() // Εμαιλ
        };
        writer.write(CSVLine);
        customerAdded = true; // Επιστρεφουμε true ωστε να ενημερωσουμε οτι εγινε επιτυχης η καταχωρηση
        return customerAdded;
    }

    public static boolean editCustomer(String AFM, String WhatToEdit, String updatedItem){
        /*
        Μεθοδος που θα επεξεργαζεται πελατες, Δινουμε το αφμ διοτι ειναι μοναδικο για καθε πελατη
        Επιλεγουμε τι θελουμε να αλλαξουμε, Και γραφουμε αυτο που θα αλλαξει
         */
        Reader reader = new Reader(Path);
        String[][] Customers = reader.read(); // Διαβαζουμε τους πελατες
        boolean customerFound = false;
        for(int i = 1; i < Customers.length; i++){ // Ξεκιναμε να διαβαζουμε απτο 1 για να μην συμπερηλαμβανουμε την επικεφαλιδα
            if(Customers[i][0].equals(AFM)){ // Ελεγχουμε αμα βρεθηκε ατομο με το αφμ

                switch(WhatToEdit.toLowerCase()){ // Και χρησιμοποιουμε μια switch για να προσπελασουμε διαφορα στοιχεια
                    case "afm", "αφμ":
                        for(int j = 0; j < Customers.length; j++){
                            if(Customers[j][0].equals(updatedItem)){ // Γινεται ελεγχος σε περιπτωση που υπαρχει καποιος με το ιδιο αφμ
                                System.out.println("Someone has the same afm");
                                return false;
                            }
                        }
                        Customers[i][0] = updatedItem; // αλλαζουμε το αφμ
                        break;
                    case "fullname", "ονοματεπώνυμο":
                        Customers[i][1] = updatedItem; // αλλαζουμε το ονοματεπωνυμο
                        break;
                    case "phonenumber", "τηλέφωνο":
                        Customers[i][2] = updatedItem; // αλλαζουμε το τηλεφωνο
                        break;
                    case "email", "εμαιλ":
                        for(int j = 0; j < Customers.length; j++){
                            if(Customers[j][3].equals(updatedItem)){ // Γινεται ελεγχος σε περιπτωση που υπαρχει καποιος με το ιδιο email
                                System.out.println("Someone has the same email");
                                return false;
                            }
                        }
                        Customers[i][3] = updatedItem; // αλλαζουμε το εμαιλ
                        break;
                    default:
                        return false;
                }
                customerFound = true;
                Writer.clearCSV(Path);
                break;

            }

        }
        if(!customerFound){
            return false;
        }
        Writer writer = new Writer(Path);
        //writer.write(header);
        for(int i = 0; i < Customers.length; i++){
            writer.write(Customers[i]);
        }
        return customerFound;
    }

    public static String[][] searchCustomer(String[][] customers, String[] criteria) {

        // criteria = { afm, name, surname, phone, email }

        List<String[]> result = new ArrayList<>();

        for (String[] customer : customers) { // Για καθε πελατη στους πελατες

            boolean match = true;

            String afm      = customer[0].toLowerCase();
            String fullname = customer[1].toLowerCase();
            String phone    = customer[2].toLowerCase();
            String email    = customer[3].toLowerCase();

            String c_afm      = criteria[0].toLowerCase();
            String c_name     = criteria[1].toLowerCase();
            String c_surname  = criteria[2].toLowerCase();
            String c_phone    = criteria[3].toLowerCase();
            String c_email    = criteria[4].toLowerCase();

            // AFM
            if (!c_afm.isEmpty() && !afm.contains(c_afm))
                match = false;

            // NAME
            if (!c_name.isEmpty() && !fullname.contains(c_name))
                match = false;

            // SURNAME
            if (!c_surname.isEmpty() && !fullname.contains(c_surname))
                match = false;

            // PHONE
            if (!c_phone.isEmpty() && !phone.contains(c_phone))
                match = false;

            // EMAIL
            if (!c_email.isEmpty() && !email.contains(c_email))
                match = false;

            if (match) {
                result.add(customer);
            }
        }

        return result.toArray(new String[0][]);
    }



    public static String[][] ViewHistory(Customer customer){
        List<String[]> result = new ArrayList<>();
        Reader reader = new Reader(rentsPath);
        String[][] rents = reader.read();
        for(int i = 1; i < rents.length; i++){
            if(rents[i][2].equals(customer.getAFM())){
                result.add(rents[i]);
            }
        }
        return result.toArray(new String[result.size()][]);
    }

    public static String[][] allCustomers(){
        Reader reader = new Reader(Path);
        String[][] customers = reader.read();
        String[][] removeHeader = new String[customers.length-1][];
        for(int i = 1; i < customers.length; i++){
            removeHeader[i-1] = customers[i].clone();
        }
        return removeHeader;
    }


}
