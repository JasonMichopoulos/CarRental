package api;


import filemanager.Reader;
import filemanager.Writer;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Employee extends Person {


    //Attributes - Ιδιότητες
    private String username;
    private String password;
    private static final String usersPath = "src/users.csv";
    private static final String rentsPath = "src/rents.csv";
    //Constructor - Κατασκευαστής
    public Employee(String fullname, String username, String password, String email){
        super(fullname, email); // Τα υιοθετεί απο την κλάση Person
        this.username = username;
        this.password = password;
    }

    // Μέθοδοι Getters
    public String getUsername() {return username;}
    public String getPassword() {return password;}
    /*
    Για το fullname και το email
    Δεν χρειαζόνται γιατί υιοθετούνται
    Από την κλάση Person
     */

    public static boolean addEmployee(Employee employee){
        /*
        Αυτη η μεθοδος προσθετει στο αρχειο users.csv
        Τον υπαλληλο user (employee) και επιστρεφει
        true αν εγινε με επιτυχια η false
        σε περιπτωση που υπηρχε ηδη καταχωρημενος με ιδιο username
         */
        boolean Added = true;
        Reader reader = new Reader(usersPath);
        String[][] users =  reader.read();
        for(int i = 1; i < users.length; i++){
            if(users[i][2].equals(employee.getUsername())){ // Εδω γινεται ο ελεγχος
                System.out.println("Employee already exists");
                Added = false;
            }
        }

        if(!employee.getEmail().contains("@")){
            Added = false;
            System.out.println("Email must contain @");
        }


        if(Added){
            Writer writer = new Writer(usersPath);
            String[] CSVLine = new String[]{
                    employee.getName(),
                    employee.getSurname(),
                    employee.getUsername(),
                    employee.getPassword(),
                    employee.getEmail()
            };
            writer.write(CSVLine);
        }


        return Added;
    }

    public static boolean deleteEmployee(Employee employee){
        Reader reader = new Reader(usersPath);
        String[][] users = reader.read();
        boolean userFound = false;
        List<String[]> userList = new ArrayList<>(Arrays.asList(users));

        for(int i = 1; i < users.length; i++){
            if(userList.get(i)[2].equals(employee.getUsername())){
                userList.remove(i);
                userFound = true;
                break;
            }
        }
        if(userFound){
            Writer.clearCSV(usersPath);
            Writer writer = new Writer(usersPath);
            for(String[] user : userList){
                writer.write(user);
            }
        }
        return userFound;
    }

    public static int totalRents(Employee employee){
        /*
        Μεθοδος που επιστρεφει τις συνολικες ενοικιασεις που εχει κανει ενας υπαλληλος
         */
        int total = 0; // Αρχικοποιουμε μια μεταβλητη total
        Reader reader = new Reader(rentsPath);
        String[][] rents = reader.read();
        // Αφου διαβασουμε ολες τις ενοικιασεις χρησιμοποιουμαι for loop που ξεκιναει με i=1 για να αποφυγουμε την επικεφαλιδα
        for(int i = 1; i < rents.length; i++){
            if(rents[i][5].equals(employee.getUsername())){
                total++;
                // Αν το username του υπαλληλου που εκανε την ενοικιαση ειναι ιδιο με το username  του υπαλληλου που θελουμε προσθετουμε +1 στο total
            }
        }
        return total;

    }

    public static Employee getEmployeeByUsername(String username){
        Reader reader = new Reader(usersPath);
        String[][] users = reader.read();
        Employee employee = null;
        for(int i = 1; i < users.length; i++){
            if(users[i][2].equals(username)){
                employee = new Employee(users[i][0] + " " + users[i][1],users[i][2],users[i][4],users[i][3]);
                break;
            }
        }
        return employee;
    }

    public static String Login(String username, String password){
        /*
        Με αυτη τη μεθοδο θα γινεται η συνδεση στην εφαρμογη
        Απο τους υπαλληλους
        Θα επιστρεφεται το username αν εγινε με επιτυχια
        Η Not found αν δεν εγινε με επιτυχια
         */
        boolean Logged = false;
        Reader reader = new Reader(usersPath); // Διαβαζουμε τους χρηστες
        String[][] users = reader.read();
        for(int i = 1; i < users.length; i++){
            if(users[i][2].equals(username) && users[i][4].equals(password)){ // Αν το username και το password ταυτιζονται με καποιον στο αρχειο τοτε το logged γινεται true
                Logged = true;
            }
        }
        if(!Logged){
            return "Not found";
        }
        return username;
    }

    public static String[][] allEmployees(){
        List<String[]> employees = new ArrayList<>();
        Reader reader = new Reader(usersPath);
        String[][] users = reader.read();
        for(int i = 1; i < users.length; i++){
            employees.add(users[i]);
        }
        return employees.toArray(new String[0][]);
    }



}
