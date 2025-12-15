package filemanager;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class Reader extends File {



    public Reader(String path){
        super(path);
    }
    public String[][] read(){

        /*
        Γινεται το πρωτο περασμα για να δουμε ποσες γραμμες περιεχει το αρχειο
         */
        int lineCount = 0;
        try (BufferedReader br = new BufferedReader(new FileReader(getPath()))) {
            while(br.readLine() != null){
                lineCount++;
            }
        } catch(IOException e){
            e.printStackTrace();
        }

        String[][] data =  new String[lineCount][]; // Δημιουργουμε ενα δισδιαστατο πινακα με γραμμες οσες περιεχει και το αρχειο

        /*
        Εδω γινεται το δεθτερο περασμα στο οποιο προσθετουμε σε καθε γραμμη του πινακα τα δεδομενα του αρχειου
         */
        try(BufferedReader br = new BufferedReader(new FileReader(getPath()))){
            String line;
            int index = 0;
            while((line = br.readLine()) != null){
                data[index] = line.split(",");
                index++;
            }
        } catch(IOException e){
            e.printStackTrace();
        }
        return data;
    }
}
