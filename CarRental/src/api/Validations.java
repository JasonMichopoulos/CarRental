package api;

public class Validations {

    private Validations(){}

    public static boolean isValidEmail(String email){
        if (email==null){
            return false;
        }
        return email.trim().matches("^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+$");
    }

    public static boolean isValidPhone(String phone){
        if(phone==null){
            return false;
        }
        return phone.trim().matches("^[0-9]{10}$");
    }

    public static boolean confirmPassword(String password, String confirmedPass){
        //Ο χρηστης δινει 2 κωδικους για επαληθευση
        //Αυτη η μεθοδος ελεγχει αμα συμπιπτουν οι κωδικοι
        return password.equals(confirmedPass);
    }

}
