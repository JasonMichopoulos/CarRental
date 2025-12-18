package api;

import filemanager.Reader;
import filemanager.UpdateCar;
import filemanager.Writer;

import java.time.LocalDate;


import java.time.format.DateTimeFormatter;
import java.util.UUID;

public class Rental {


    //Attributes - Ιδιότητες
    private final String rentalID; // id Μοναδικό με χρήση UUID
    private final Car car; // κλάση Car
    private final Customer customer; // κλάση Customer
    private final LocalDate startDate; // Η ημερομηνία έναρξης ενοικίασης
    private final LocalDate endDate; // Η ημερομηνία λήξης ενοικίασης
    private final Employee employee; // κλάση Employee
    private boolean returned;

    public final static String Path = "CarRental/src/rents.csv";
    public final static String[] Header = new String[]{"id,car,customer,startdate,enddate,employee,returned"};

    // Ολες οι ιδιοτητες ειναι τυπου final γιατι δεν θα χρειαστουν επεξεργασια μετα την δηλωση τους
    //Constructor - Κατασκευαστής
    public Rental(Car car, Customer customer, LocalDate endDate, Employee employee) {
        this.rentalID = UUID.randomUUID().toString();
        this.car = car;
        this.customer = customer;
        this.startDate = LocalDate.now();
        this.endDate = endDate;
        this.employee = employee;
        this.returned = false;

    }

    //Μέθοδοι Getters
    public String getRentalID() {return rentalID;}
    public Car getCar() {return car;}
    public Customer getCustomer() {return customer;}
    public LocalDate getStartDate() {return startDate;}
    public LocalDate getEndDate() {return endDate;}
    public Employee getEmployee() {return employee;}

    public void Rent(){
        //Βλεπουμε αν το αυτοκινητο που παμε να νοικιασουμε ειναι διαθεσιμο
        if(!car.isAvailable()){
            System.out.println("Car is not available");
            return; // τερματιζουμε σε περιπτωση που ειναι
        }
        Writer writeRent = new Writer(Path); // Σε ενα καινουργιο csv αρχειο καταχωρουμε την ενοικοιαση
        writeRent.write(new String[]{
           getRentalID(),
                getCar().getPlate(), // Χρησιμοποιουμε την πινακιδα γιατι ειναι μοναδικη για καθε αυτοκινητο
                getCustomer().getAFM(), // Το ιδιο και με το αφμ
           getStartDate().toString(),
           getEndDate().toString(),
           getEmployee().getUsername(), // Και με το username
           Boolean.toString(this.returned)
        });
        UpdateCar.carAvailable(car.getId(),false);

    }



    public static boolean Returned(Car car){
        boolean carFound = false;
        String[][] allRents = new Reader(Path).read();
        for(int i = 0; i < allRents.length; i++){
            if(allRents[i][1].equalsIgnoreCase(car.getPlate()) && allRents[i][6].equalsIgnoreCase("false")){

                allRents[i][6] = "true";
                carFound = true;
                break;

            }
        }
        if(carFound){
            Writer write = new Writer(Path);
            Writer.clearCSV(Path);
            for (String[] rents : allRents) {
                write.write(rents);
            }

            UpdateCar.carAvailable(car.getId(),true);
        }


        return carFound;
    }

    public static int totalRents(){
        return new Reader(Path).read().length - 1; // Επιστρεφει ολες τις ενοικιασεις που εχουν γινει - 1 για την επικεφαλιδα
    }

}
