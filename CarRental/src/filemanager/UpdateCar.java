package filemanager;


public class UpdateCar {
    private static final String Path = Paths.getCarPath();
    private static final String[] header = new String[]{"id,πινακίδα,μάρκα,τύπος,μοντέλο,έτος,χρώμα,κατάσταση"}; // Η επικεφαλιδα του CSV αρχειου που θα χρειαστει



    // Για να ενημερωνει την διαθεσιμοτητα του αυτοκινητου
    public static void carAvailable(int id, boolean updatedAvailability){
        Reader readCars = new Reader(Path); //Ανοιγουμε το αρχειο για να το διαβασουμε

        String[][] all_cars = readCars.read();  //Καταχωρουμε τα αυτοκινητα

        for (int i = 1; i < all_cars.length; i++) { // Διαβαζουμε απο το 1 και μετα για να μην συμπερηλαμβανουμε την επικεφαλιδα id,πινακιδα...
            //Αναζητουμε το συγκεκριμενο αυτοκινητο που θελουμε να ανανεωσουμε
            if(Integer.parseInt(all_cars[i][0]) == id){
                if(updatedAvailability){ // Αν δωσουμε true γινεται διαθεσιμο
                    all_cars[i][7] = "Διαθέσιμο";
                }
                else{ // αλλιως ειναι μη διαθεσιμο
                    all_cars[i][7] = "Μη διαθέσιμο";
                }

                Writer.clearCSV(Path);
                break; //Σταματαμε την λουπα για να μην κανει περιττες επαναληψεις
            }

        }

        Writer writeUpdateCar = new Writer(Path);
        //writeUpdateCar.write(header);// Γραφουμε την επικεφαλιδα
        for(int i = 0; i < all_cars.length; i++){
            writeUpdateCar.write(all_cars[i]);// Και ολα τα αυτοκινητα με την αλλαγη
        }
    }

    public static boolean editCars(int id, String WhatToEdit, String updatedItem){
        Reader readCars = new Reader(Path);
        String[][] all_cars = readCars.read();
        boolean carFound = false;
        for (int i = 1; i < all_cars.length; i++) {
            if(Integer.parseInt(all_cars[i][0]) == id){
                switch(WhatToEdit.toLowerCase()){
                    case "brand":
                        all_cars[i][2] = updatedItem;
                        break;
                    case "type":
                        all_cars[i][3] = updatedItem;
                        break;
                    case "model":
                        all_cars[i][4] = updatedItem;
                        break;
                    case "year":
                        all_cars[i][5] = updatedItem;
                        break;
                    case "color":
                        all_cars[i][6] = updatedItem;
                        break;
                    default:
                        return false;
                }
                carFound = true;
                Writer.clearCSV(Path);
                break;
            }

        }
        if(!carFound){
            return false;
        }

        Writer writeUpdateCar = new Writer(Path);

        for(int i = 0; i < all_cars.length; i++){
            writeUpdateCar.write(all_cars[i]);
        }

        return carFound;
    }


}
