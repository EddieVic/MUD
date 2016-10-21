package mud.view;

//Import other parts of mud package

//Import Java GUI stuff
import javax.swing.JPanel;
import java.awt.Color;

//Import observable stuff
import java.util.Observer;
import java.util.Observable;

/*
* Class         ColumnPanel
* Package       mud.view
* Contributors  --------
* Public Methods:
*     ---
*/
public class ColumnPanel extends JPanel implements Observer {
	public ColumnPanel() {
		setBackground(Color.BLACK);
	}
	
	public void update(Observable o, Object arg) {}
}