package filemanager;

import java.io.FileWriter;
import java.io.IOException;

public class Writer extends File{

    public Writer(String path) {
        super(path);
    }

    public void write(String[] line){
        Reader r = new Reader(getPath());
        String[][] readfile = r.read();
        /*
        Σε αυτο το κομματι ελεγχουμε αν αυτο που παμε να γραψουμε
        Στο αρχειο ειναι ηδη καταχωρημενο
        Εφοσον δεν γινεται να εχουμε αυτοκινητα με την ιδια πινακιδα
        Και χρηστες με το ιδιο username.
         */
        // Για ασφαλεια, σε περιπτωση που παμε να γραψουμε κατι που δεν εχει περιεχομενο
        if (line == null || line.length == 0) {
            System.out.println("Cannot write empty CSV line.");
            return;
        }


        try(FileWriter fw = new FileWriter(getPath(),true)){

            // Για καθε στοιχειο που υπαρχει γραφουμε το στοιχειο.
            for(int i=0;i<line.length;i++){
                fw.write(line[i]);
                if(i<line.length-1){ // προσθετουμε ενα κομμα, εκτος απο το τελευταιο στοιχειο της λιστας.
                    fw.write(",");
                }
            }
            //Επομενη σειρα
            fw.write("\n");
        } catch(IOException e){
            e.printStackTrace();
        }
    }

    public static void clearCSV(String path){
        try (FileWriter fw = new FileWriter(path, false)) {
            // Απλα βαζοντας την παραμετρο append με false το αρχειο csv καθαριζεται εντελως
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
