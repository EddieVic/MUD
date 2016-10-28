package mud.model;

//Import other parts of mud package
import mud.view.*;

//Import observable stuff
import java.util.Observer;
import java.util.Observable;

//Import file I/O stuff
import java.io.FileInputStream;
import java.io.ObjectInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.Serializable;

//Import threading
import java.lang.Thread;
import java.lang.InterruptedException;

//Import few GUI stuff needed
import java.awt.Dimension;

/*
* Class         Status
* Package       mud.model
* Contributors  --------
* Public Methods:
*     ---
*/
public class Status extends Observable implements Serializable {
    //Constants
    public static final String CFG_FILE = "mud.cfg";

    //Instance variables
    private Dimension frameSize;

    //Constructor
    public Status() {}

    //Reloads the frame upon being opened
    public void reload() {
    }

    public void setFrameSize(Dimension d) {
    	frameSize = d;
    } 

    public Dimension getFrameSize() {
    	return frameSize;
    }

//Oh shit, don't do this
    public static void main(String[] args) {
        Status status = null;
        try {
            FileInputStream fis = new FileInputStream(CFG_FILE);
            ObjectInputStream ois = new ObjectInputStream(fis);
            status = (Status) ois.readObject();
            ois.close();
            fis.close();
        }
        catch (FileNotFoundException fnf) {
            status = new Status();
        }
        catch (IOException ioe) {
            System.err.println("Error reading file \'mud.cfg\'");
            System.exit(1);
        }
        catch (ClassNotFoundException cnf) {
            System.err.println("Error reading file \'mud.cfg\'");
            System.exit(1);
        }
        status.reload();
    }
}