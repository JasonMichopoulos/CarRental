package filemanager;

public class Paths {
    private final static String carPath = "CarRental/src/vehicles_with_plates.csv";
    private final static String customersPath = "CarRental/src/customers.csv";
    private final static String usersPath = "CarRental/src/users.csv";
    private final static String rentsPath = "CarRental/src/rents.csv";

    private Paths(){};

    public static String getCarPath(){
        return carPath;
    }
    public static String getCustomersPath(){
        return customersPath;
    }
    public static String getUsersPath(){
        return usersPath;
    }
    public static String getRentsPath(){
        return rentsPath;
    }
}
