package helpers;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.Reader;

public class ReaderPlus {

    private BufferedReader in;
    private String line;

    public ReaderPlus(Reader reader, int size) {
        in = new BufferedReader(reader, size);
        line = "";
    }

    public String line() {
        return line;
    }

    public boolean moreLines() {
        try {
            line = in.readLine();
        } catch (IOException ex) {
            line = null;
        }
        return line != null;
    }
    
    public String nextLine(){
        try {
            line = in.readLine();
        } catch (IOException ex) {
            line = null;
        }
        return line;
    }

    public boolean close() {
        boolean ok = true;
        try {
            in.close();
        } catch (IOException ex) {
            ok = false;
        }
        return ok;
    }
}
