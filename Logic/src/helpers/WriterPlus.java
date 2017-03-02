package helpers;

import java.io.BufferedWriter;
import java.io.IOException;
import java.io.Writer;

public class WriterPlus {

    private BufferedWriter out;

    public WriterPlus(Writer writer, int size) {
        out = new BufferedWriter(writer, size);
    }

    public boolean writeLine(String line) {
        boolean ok = true;
        try {
            out.write(line);
            out.newLine();
        } catch (IOException ex) {
            ok = false;
        }
        return ok;
    }
    
    public boolean flush() {
        boolean ok = true;
        try {
            out.flush();
        } catch (IOException ex) {
            ok = false;
        }
        return ok;
    }

    public boolean close() {
        boolean ok = true;
        try {
            out.close();
        } catch (IOException ex) {
            ok = false;
        }
        return ok;
    }
}
