package mud.model;

//Import observable stuff
import java.util.Observer;
import java.util.Observable;

//Import file I/O stuff
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.Serializable;

//Import few GUI stuff needed
import java.awt.Dimension;

/*
* Class         Status
* Package       mud.model
* Contributors  Eddie Vic
* Public Methods:
*     Status.getInstance()
*     Status.save()
*     ---
*/
public class Status extends Observable implements Serializable {
    /************************* Start class variables *************************/
    //Static variables
    private static Status status = null;

    //Instance variables
    private Dimension frameSize;
    private Dimension[][] sizes;
    private int cols;
    private int[] rows;
    /************************** End class variables **************************/


    /*********************** Start static class methods **********************/
    /*
    * getInstance()
    *   Purpose  - Creates/loads from a file an instance of Status and returns
    *              it. This helps make sure that only one instance of Status is
    *              created. If one has already been made, it returns that
    *              instance.
    *   Class    - Status
    *   Returns  - Status
    */
    public static Status getInstance() {
        //If status is already set, don't do anything
        if (status == null) {
            //Attempt to open and read the file
            try {
                FileInputStream fis = new FileInputStream("mud.cfg");
                ObjectInputStream ois = new ObjectInputStream(fis);
                status = (Status) ois.readObject();
                ois.close();
                fis.close();
            }
            //If the file could not be opened, load default settings
            catch (FileNotFoundException fnf) {
                System.err.println("\'mud.cfg\' does not exist or could not be"
                                   + " opened, loading defaults.");
                status = new Status();
            }
            //If there was an error reading from the file, print error and load
            //defaults
            catch (IOException io) {
                System.err.println("Error reading \'mud.cfg\', exiting...");
            }
            //In the event of class not found, report corrupted file and load
            //defaults
            catch(ClassNotFoundException cnf) {
                System.err.println("Error, malformed \'mud.cfg\'");
            }
        }

        //Return instance of status
        return status;
    }

    /*
    * save()
    *   Purpose  - Saves the current instance of Status object to file 'mud.cfg'
    *              Does nothing if status is null
    *   Class    - Status
    *   Returns  - None
    */
    public static void save() {
        //If instance has not yet been created, end method
        if (status == null) {
            return;
        }

        //Open file for writing and output instance
        try {
            FileOutputStream fos = new FileOutputStream("mud.cfg");
            ObjectOutputStream oos = new ObjectOutputStream(fos);
            oos.writeObject(status);
            oos.close();
            fos.close();
        }
        catch (FileNotFoundException fnf) {
            System.err.println("Could not open file \'mud.cfg\'");
        }
        catch (IOException io) {
            System.err.println("Error writing to file \'mud.cfg\'");
        }
    }
    /************************ End static class methods ***********************/


    /********************** Start class instance methods *********************/
    /*Everything above this should be done except maybe more imports*/
    //Constructor, creates default settings
    private Status() {
        //Dynamically get frame size
        frameSize = null;
    }

    public void setFrameSize(Dimension d) {
        frameSize = d;
    } 

    public Dimension getFrameSize() {
        return frameSize;
    }
    /*********************** End class instance methods **********************/
}