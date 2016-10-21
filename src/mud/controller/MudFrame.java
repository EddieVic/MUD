package mud.controller;

//Import other parts of mud package
import mud.view.ColumnPanel;

//Import Java GUI stuff
import javax.swing.JFrame;
import javax.swing.JPanel;
import java.awt.Color;

//Import observable stuff
import java.util.Observer;
import java.util.Observable;

//Import file IO handling
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

/*
* Class         MudFrame
* Package       mud.controller
* Contributors  Eddie Vic
* Public Methods:
*     MudFrame()
*/
public class MudFrame extends JFrame implements Observer {
	public MudFrame() {
		//Load frame options file
		FileInputStream in = null;
		int width = 0;
		int height = 0;
		int options = 0;
		try {
			in = new FileInputStream("cfg/Frame.cfg");
			width = (in.read() << 8) | (in.read());
			height = (in.read() << 8) | (in.read());
			options = in.read();
			in.close();
		}
		catch (FileNotFoundException e) {
			//File doesn't exist, create it with default options
			try {
				FileOutputStream f = new FileOutputStream("cfg/Frame.cfg");
				//Width
				f.write(800 >> 8);
				f.write(800 & 0xFF);
				//Height
				f.write(600 >> 8);
				f.write(600 & 0xFF);
				// 0x01 = Maximized
				f.write(0x01);
				//Close the file
				f.close();
				
				//Set to default options
				width = 800;
				height = 600;
				options = 0x01;
			}
			catch (FileNotFoundException ex) {
				System.err.println("File \'cfg/Frame.cfg\' could not be opened");
				System.exit(1);
			}
			catch (IOException ex) {
				System.err.println("Error writing to file \'cfg/Frame.cfg\'");
				System.exit(1);
			}
		}
		catch (IOException e) {
			System.err.println("Error reading file \'cfg/Frame.cfg\'");
			System.exit(1);
		}

		//Set frame options from file
		setTitle("MUD");
		setLocationRelativeTo(null);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setSize(width, height);
		//If the maximized flag is set, maximize the window
		if ((options & 0x01) == 0x01) {
			setExtendedState(getExtendedState() | MAXIMIZED_BOTH);
		}

		//////////////////////
		ColumnPanel cp = new ColumnPanel();
		add(cp);

		setVisible(true);
	}

	public void update(Observable o, Object arg) {}

	public static void main(String[] args) {
		MudFrame mf = new MudFrame();
	}
}
