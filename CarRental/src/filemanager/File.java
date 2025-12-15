package filemanager;

public class File {
   private String path;
   File(String path) {
       this.path = path;
   }
   public String getPath() {return this.path;}
   public void setPath(String path) {this.path = path;}
}

