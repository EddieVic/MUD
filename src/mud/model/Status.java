package mud.model;

//Import observable stuff
import java.util.Observable;

//Import file I/O stuff
import java.io.File;
import java.io.Serializable;
import java.io.FileInputStream;
import java.io.ObjectInputStream;
import java.io.FileOutputStream;
import java.io.ObjectOutputStream;
import java.io.IOException;
import java.io.FileNotFoundException;

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
    private static Status status = null;   //Singleton class variable

    //Instance variables
    private Dimension frameSize;   //The size of the frame of the MUD
    private int[] columns;         //The sizes of columns of panels
    private int[][] rows;          //The sizes of rows of panels in each column
    private boolean isMaximized;   //A boolean on whether the frame is maximized

    private PanelType types[][];   //The types of each panel
    private int terminals;         //The number of terminal panels open
    /************************** End class variables **************************/


    /*********************** Start static class methods **********************/
    /*
    * getInstance()
    *   Purpose  - Creates/loads from a file an instance of Status and returns
    *              it. This helps make sure that only one instance of Status is
    *              created. If one has already been made, it returns that
    *              instance.
    *   Class    - Status
    *   Returns  - A Status object
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
                //If the file exists, print an error
                File f = new File("mud.cfg");
                if (f.exists()) {
                    System.err.println("\'mud.cfg\' could not be"
                                       + " opened, loading defaults.");
                }
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
    /*
    * Status()
    *   Purpose  - It's a fucking constructor. What do you think the purpose is?
    *   Class    - Status
    *   Returns  - Reference to Status object
    */
    private Status() {
        //Initialize columns array
        columns = new int[1];
        columns[0] = 100;

        //Initialize rows array
        rows = new int[1][];
        rows[0] = new int[1];
        rows[0][0] = 100;
        
        //Initialize default frame size to be null
        //Should be set manually upon first run
        frameSize = null;
        isMaximized = true;

        //Initialize types
        types = new PanelType[1][];
        types[0] = new PanelType[1];
        types[0][0] = PanelType.TERMINAL;
        terminals = 1;
    }

    /*
    * setFrameSize(Dimension)
    *   Purpose  - Set the size of the frame that is desired
    *   Class    - Status
    *   Returns  - None
    *   @d       - The new size of the MUD
    */
    public void setFrameSize(Dimension d) {
        frameSize = d;
    }

    /*
    * getFrameSize()
    *   Purpose  - Get the size of the frame of the MUD
    *   Class    - Status
    *   Returns  - A Dimension
    */
    public Dimension getFrameSize() {
        return frameSize;
    }

    /*
    * getType(int,int)
    *   Purpose  - Tells the caller what type of panel is at the given location
    *   Class    - Status
    *   Returns  - A PanelType
    *   @col     - An int giving the column being requested
    *   @row     - An int giving the row being requested
    */
    public PanelType getType(int col, int row) {
        //If either index is out of bounds, return null
        if (!(col < columns.length && row < rows[col].length)) {
            return null;
        }

        //Otherwise give the panel type
        return types[col][row];
    }

    /*
    * addPanel(int,int,PanelType)
    *   Purpose  - Adds a panel at the given index. If row is -1, a new column
    *              is created, otherwise a row is added to that column at that
    *              index
    *   Class    - Status
    *   Returns  - A boolean of whether or not the panel was successfully added
    *   @col     - An int giving the column being added
    *   @row     - An int giving the row being added
    *   @pt      - The type of panel being added
    */
    public boolean addPanel(int col, int row, PanelType pt) {
        //Avoid negative index
        if (col < 0 || row < -1) {
            return false;
        }

        //If an entire column is being added
        if (row == -1) {
            //If the column cannot be added, return false
            if (col > columns.length) {
                return false;
            }

            //Adjust sizes
            int size = 0;
            for (int i = 0; i < columns.length; i++) {
                int adjust;
                if (columns[i] < (200 / columns.length) / columns.length) {
                    adjust = columns[i] / 2;
                }
                else {
                    adjust = (100 / columns.length) / columns.length;
                }
                size += adjust;
                columns[i] -= adjust;
            }

            //Add a new column
            int[] tmpC = new int[columns.length + 1];
            int[][] tmpR = new int[columns.length + 1][];
            PanelType[][] tmpT = new PanelType[columns.length + 1][];
            for (int i = 0, j = 0; i < tmpC.length; i++) {
                //If the column needs to be copied
                if (i != col) {
                    //Copy it
                    tmpC[i] = columns[j];
                    tmpR[i] = rows[j];
                    tmpT[i] = types[i];
                    j++;
                }
                //Otherwise add the column
                else {
                    tmpC[i] = size;
                    tmpR[i] = new int[1];
                    tmpR[i][0] = 1;
                    tmpT[i] = new PanelType[1];
                    tmpT[i][0] = pt;
                }
            }
            columns = tmpC;
            rows = tmpR;
            types = tmpT;
        }
        //Otherwise add a new row
        else {
            //If row cannot be added return false
            if (!(col < columns.length && row <= rows[col].length)) {
                return false;
            }

            //Adjust sizes
            int size = 0;
            int rLen = rows[col].length;
            for (int i = 0; i < rows[col].length; i++) {
                int adjust;
                if (rows[col][i] < (200 / rLen) / rLen) {
                    adjust = rows[col][i] / 2;
                }
                else {
                    adjust = (100 / rLen) / rLen;
                }
                size += adjust;
                rows[col][i] -= adjust;
            }

            //Add a new row
            int tmpR[] = new int[rows[col].length + 1];
            PanelType tmpT[] = new PanelType[tmpR.length];
            for (int i = 0, j = 0; i < tmpR.length; i++) {
                if (i != row) {
                    tmpR[i] = rows[col][j];
                    tmpT[i] = types[col][j];
                    j++;
                }
                else {
                    tmpR[i] = size;
                    tmpT[i] = pt;
                }
            }
            rows[col] = tmpR;
            types[col] = tmpT;
        }

        //If it got to this point the panel was successfully added, return true
        return true;
    }

    /*
    * removePanel(int,int)
    *   Purpose  - Removes the panel at the given index.
    *   Class    - Status
    *   Returns  - A boolean of whether the panel was successfully removed
    *   @col     - An int giving the column being removed
    *   @row     - An int giving the row being removed
    */
    public boolean removePanel(int col, int row) {
        return true;
    }

    /*
    * movePanel(int,int,int,int)
    *   Purpose  - Moves the panel at the first index to the second index
    *   Class    - Status
    *   Returns  - A boolean of whether the panel was successfully moved
    *   @sc      - An int giving the source column
    *   @sr      - An int giving the source row
    *   @dc      - An int giving the destination column
    *   @dr      - An int giving the destination row
    */
    public boolean movePanel(int sc, int sr, int dc, int dr) {
        return true;
    }

    /*
    * swapPanel(int,int,int,int)
    *   Purpose  - Swaps the panel at the first index with the panel at the
    *              second index
    *   Class    - Status
    *   Returns  - A boolean of whether the panels were successfully swapped
    *   @col1    - An int giving the column of the first panel
    *   @row1    - An int giving the row of the first panel
    *   @col2    - An int giving the column of the second panel
    *   @row2    - An int giving the row of the second panel
    */
    public boolean swapPanel(int col1, int row1, int col2, int row2) {
        return true;
    }

    /*
    * resizeColumn(int,int)
    *   Purpose  - Resizes the columns at the given index
    *   Class    - Status
    *   Returns  - A boolean of whether the columns were successfully resized
    *   @col     - An int giving the column to the left of the border being
    *              resized
    *   @amt     - An int giving the amount to change by
    */
    public boolean resizeColumn(int col, int amt) {
        return true;
    }

    /*
    * resizeRow(int,int)
    *   Purpose  - Resizes the rows at the given index
    *   Class    - Status
    *   Returns  - A boolean of whether the rows were successfully resized
    *   @row     - An int giving the row above the border being resized
    *   @amt     - An int giving the amount to change by
    */
    public boolean resizeRow(int row, int amt) {
        return true;
    }
    /*********************** End class instance methods **********************/
}