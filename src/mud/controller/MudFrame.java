package mud.controller;

import mud.model.Status;

//Import Java GUI stuff
import javax.swing.JFrame;
import javax.swing.JPanel;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.GraphicsEnvironment;
import java.awt.Cursor;

//Import observable stuff
import java.util.Observer;
import java.util.Observable;

//Import file IO handling
import java.io.FileInputStream;
import java.io.ObjectInputStream;
import java.io.FileOutputStream;
import java.io.FileNotFoundException;
import java.io.ObjectOutputStream;
import java.io.IOException;

//Import event stuff
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;

/*
* Class         MudFrame
* Package       mud.controller
* Contributors  Eddie Vic
* Public Methods:
*     MudFrame()
*     update(Observable o, Object arg)
*     getChanged()
*/
public class MudFrame extends JFrame implements Observer {
    private Status status;

    public MudFrame() {
        //Load status
        status = Status.getInstance();
        
        //Set constant frame options
        setTitle("MUD");
        setLocationRelativeTo(null);
        setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
        setSize(800, 600);
        //If the maximized flag is set, maximize the window
        if ((0x01) == 0x01) {
            setExtendedState(getExtendedState() | MAXIMIZED_BOTH);
        }
        //Open icon and set as program icon
        //setIconImage(Image i)

        //Adds the necessary listener
        addWindowListener(new MudFrameWindow());

        //Show the frame
        setVisible(true);
    }

    public void update(Observable o, Object arg) {}

    public static void main(String[] args) {
    	new MudFrame();
    }

    /*
    * Class         MudFrameWindow
    * Contained by  MudFrame
    * Public Methods:
    *     windowActivated(WindowEvent e)
    *     windowClosed(WindowEvent e)
    *     windowClosing(WindowEvent e)
    *     windowDeactivated(WindowEvent e)
    *     windowDeiconified(WindowEvent e)
    *     windowIconified(WindowEvent e)
    *     windowOpened(WindowEvent e)
    * Notes:
    *   Exists as a listener for MudFrame to find when the frame is 
    */
    private class MudFrameWindow implements WindowListener {
        //Saves window settings when it is being closed
        public void windowClosing(WindowEvent e) {
        	Status.save();
            System.exit(0);
        }

        //The rest are just because WindowListener has to be implemented
        public void windowActivated(WindowEvent e) {}
        public void windowClosed(WindowEvent e) {}
        public void windowDeactivated(WindowEvent e) {}
        public void windowDeiconified(WindowEvent e) {}
        public void windowIconified(WindowEvent e) {}
        public void windowOpened(WindowEvent e) {}
    }
}
