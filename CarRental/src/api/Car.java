package api;

import filemanager.Reader;
import filemanager.Writer;

import java.util.ArrayList;
import java.util.List;

public class Car {
    private int id; // id
    private String plate; // pinakida
    private String brand; // marka
    private String type; // typos
    private String model; // montelo
    private String year; // xronologia
    private String color; // xrwma
    private boolean available; // diathesimo - mh diathesimo

    private static final String carsPath = "src/vehicles_with_plates.csv";
    private static final String rentsPath = "src/rents.csv";
    private static final String[] header = new String[]{"id,πινακίδα,μάρκα,τύπος,μοντέλο,έτος,χρώμα,κατάσταση"};


    // Constructor
    public Car(int id, String plate, String brand, String type, String model, String year, String color, boolean available) {
        this.id = id;
        this.plate = plate;
        this.brand = brand;
        this.type = type;
        this.model = model;
        this.year = year;
        this.color = color;
        this.available = available;

    }

    //Getters
    public int getId() {return id;}
    public String getPlate() {return plate;}
    public String getBrand() {return brand;}
    public String getType() {return type;}
    public String getModel() {return model;}
    public String getYear() {return year;}
    public String getColor() {return color;}
    public boolean isAvailable() {return available;}


    // Μέθοδος toString Για να εμφανίζονται ομοιόμορφα
    public String toString(){
        return id + " " +
                brand + " " +
                "("+plate+")" + " " +
                model + " " +
                year + " " +
                 "( " + type + "," + color + " )" + " " +
                available;
    }

    public static int getNextID(){
        /*
        Σε αυτη τη μεθοδο θα δημιουργειται αυτοματα το id για το επομενο αυτοκινητο
         */
        Reader reader = new Reader(carsPath);
        String[][] Cars = reader.read();
        int max_ID = 0;
        //Ξεκιναμε απο το 1 να διαβαζουμε για να μην παρουμε την επικεφαλιδα
        for(int i = 1; i < Cars.length; i++){
            int id = Integer.parseInt(Cars[i][0]);
            if(id > max_ID){
                max_ID = id; // Βρισκουμε το μεγαλυτερο id που υπαρχει μες το αρχειο
            }
        }
        return max_ID+1; // Επιστρεφουμε το αμεσως επομενο μεγαλυτερο id
    }


    public static boolean addCar(Car car){ // Προσθεση αυτοκινητου στο αρχειο
        Reader reader = new Reader(carsPath);
        String[][] Cars = reader.read();
        for(int i = 1; i < Cars.length; i++){ // Διαβαζουμε απτο 1 για να μην συμπεριλαμβανουμε την επικεφαλιδα
            if(car.getPlate().equals(Cars[i][1])){ // Ελεγχουμε αν υπαρχει αυτοκινητο με την ιδια πινακιδα
                System.out.println("Car already exists"); // Αχριαστη γραμμη, μονο για debugging
                return false; // Αν βρεθηκε αμαξι επιστρεφουμε false
            }
        }
        Writer writer = new Writer(carsPath);
        String[] CSVLine = new String[]{
                Integer.toString(car.getId()),
                car.getPlate(),
                car.getBrand(),
                car.getType(),
                car.getModel(),
                car.getYear(),
                car.getColor(),
                "Διαθέσιμο"
        };
        writer.write(CSVLine);
        return true; // Γραφουμε το καινουργιο αυτοκινητο στο αρχειο και επιστρεφουμε true
    }






    public static String[][] searchCars(String[][] cars, String[] criteria) {

        // criteria = { plate, brand, type, model, year, color, availability }

        List<String[]> result = new ArrayList<>(); // Λιστα που θα αποθηκευει τα αυτοκινητα που ταιριαζουν με τα κριτηρια

        String availability = "";
        // Μετατροπη του availability σε Διαθέσιμο, Μη διαθέσιμο
        if (criteria[6] != null && !criteria[6].trim().isEmpty()) {
            availability = criteria[6].trim().toLowerCase();

            if (availability.equalsIgnoreCase("ναι") || availability.equalsIgnoreCase("true")) {
                availability = "Διαθέσιμο";
            } else if (availability.equalsIgnoreCase("οχι") || availability.equalsIgnoreCase("όχι") || availability.equalsIgnoreCase("false")) {
                availability = "Μη διαθέσιμο";
            }
        }

        // Για καθε αυτοκινητο
        for (String[] car : cars) {
            // Αφηνουμε την επικεφαλίδα
            if(car[0].equalsIgnoreCase("id")){
                continue;
            }

            boolean match = true; // Αρχικοποιουμε οτι ταιριαζει το αυτοκινητο με τα κριτηρια

            //Τα κανουμε ολα μικρα για να μπορουμε να τα συγκρινουμε πιο ευκολα
            String plate = car[1].toLowerCase();
            String brand = car[2].toLowerCase();
            String type  = car[3].toLowerCase();
            String model = car[4].toLowerCase();
            String year  = car[5].toLowerCase();
            String color = car[6].toLowerCase();
            String carAvailability = car[7].toLowerCase();

            String c_plate = criteria[0].toLowerCase();
            String c_brand = criteria[1].toLowerCase();
            String c_type  = criteria[2].toLowerCase();
            String c_model = criteria[3].toLowerCase();
            String c_year  = criteria[4].toLowerCase();
            String c_color = criteria[5].toLowerCase();

            if (!c_plate.isEmpty() && !plate.contains(c_plate)) // Χρησιμοποιουμε contains ωστε να μην χρειαστει να ταυτιζεται η λεξη 100%
                match = false;

            if (!c_brand.isEmpty() && !brand.contains(c_brand))
                match = false;

            if (!c_type.isEmpty() && !type.contains(c_type))
                match = false;

            if (!c_model.isEmpty() && !model.contains(c_model))
                match = false;

            if (!c_year.isEmpty() && !year.contains(c_year))
                match = false;

            if (!c_color.isEmpty() && !color.contains(c_color))
                match = false;

            if (!availability.isEmpty() && !carAvailability.equalsIgnoreCase(availability)) // Εδω χρησιμοποιουμε equals
                match = false;

            if (match) {
                result.add(car);
            }
        }

        return result.toArray(new String[0][]);
    }

    public static boolean carReturn(Car car){
        /*
        Σε αυτη τη μεθοδο γινεται η επιστροφη των αυτοκινητων
         */
        boolean carReturned = false; // Για να τεσταρουμε αν εγινε με επιτυχια
        Reader reader = new Reader(carsPath); // Διαβαζουμε το αρχειο με τα αυτοκινητα
        String[][] Cars = reader.read();
        for(int i = 1; i < Cars.length; i++){ // Σε μια for loop βλεπουμε εαν η πινακιδα του αυτοκινητου που δοθηκε αντιστοιχει με καποια μεσα στο αρχειο
            if(car.getPlate().trim().equalsIgnoreCase(Cars[i][1].trim()) && Cars[i][7].trim().equalsIgnoreCase("Μη διαθέσιμο")){ // Βλεπουμε εαν ισχυει το οτι δεν ειναι διαθεσιμο


                carReturned = true; // γινεται true και το αυτοκινητο επιστρεφεται
                Cars[i][7] = "Διαθέσιμο"; // αλλαζουμε το μη διαθεσιμο σε διαθεσιμο
                break;

            }
        }
        if(carReturned){ // Αν βρεθηκε και επιστραφηκε με επιτυχια το αυτοκινητο γραφουμε στο αρχειο ξανα τα αυτοκινητα με ανανεωμενο το συγκεκριμενο
            Writer writer = new Writer(carsPath);
            Writer.clearCSV(carsPath);
            writer.write(header);
            for(int i = 1; i < Cars.length; i++){
                writer.write(Cars[i]);
            }
        }
        return carReturned;
    }

    public static String[][] ViewHistory(Car car){
        List<String[]> result = new ArrayList<>(); // Η λιστα για αποθηκευση
        Reader reader = new Reader(rentsPath);
        String[][] rents = reader.read();
        result.add(rents[0]); // Προσθετουμε επικεφαλιδα
        for(int i = 1; i < rents.length; i++){
            if(car.getPlate().equalsIgnoreCase(rents[i][1])){ // Αν η πινακιδα ταιριαζει με την πινακιδα που νοικιαστηκε προσθετουμε στη λιστα
                result.add(rents[i]);
            }
        }
        return result.toArray(new String[result.size()][]); // Επιστρεφουμε String[][] με το αποτελεσμα

    }

    public static Car getCarByPlate(String plate){
        // Μεθοδος που επιστρεφει Car δινοντας πινακιδα
       Reader reader = new Reader(carsPath);
       String[][] Cars = reader.read();
       for(int i = 1; i < Cars.length; i++){
           if(plate.equalsIgnoreCase(Cars[i][1])){ // Βρισκει το αυτοκινητο στο αρχειο που εχει την ιδια πινακιδα
               return new Car(
                       Integer.parseInt(Cars[i][0].trim()),
                       Cars[i][1].trim(),
                       Cars[i][2].trim(),
                       Cars[i][3].trim(),
                       Cars[i][4].trim(),
                       Cars[i][5].trim(),
                       Cars[i][6].trim(),
                       Cars[i][7].equalsIgnoreCase("Διαθέσιμο")
                       ); // Επιστρεφει ενα new Car με τα στοιχεια που εχει μεσα στο αρχειο
           }
       }
       return null; // Αν δεν βρεθηκε το αμαξι επιστρεφει null;
    }

    public static String[][] allCars(){
        // Επιστρεφει ολα τα αυτοκινητα σε String[][] χωρις την επικεφαλιδα
        Reader reader = new Reader(carsPath);
        String[][] cars = reader.read();
        String[][] removeHeader = new String[cars.length-1][];
        for(int i = 1; i < cars.length; i++){
            removeHeader[i-1] = cars[i].clone();
        }
        return removeHeader;
    }

}
