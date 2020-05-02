package data;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

public class File {
    static ObjectOutputStream out;
    static ObjectInputStream in;
    
    private static String route(String file) {
        return "src/data/" + file;
    }
    
    public static void write() throws FileNotFoundException, IOException {
        FileOutputStream f = new FileOutputStream(route("data.obj"));
        out = new ObjectOutputStream(f);
        Data info = Data.getInstance();
        
        out.writeObject(info);
        out.close();
        f.close();
    }
    
    public static Data read() throws FileNotFoundException, IOException, ClassNotFoundException {
        FileInputStream f = new FileInputStream(route("data.obj"));
        in = new ObjectInputStream(f);
        Data temp = (Data) in.readObject();
        
        in.close();
        return temp;
    }
}
