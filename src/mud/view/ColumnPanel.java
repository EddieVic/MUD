package mud.view;

//Import other parts of mud package

//Import Java GUI stuff
import javax.swing.JPanel;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Cursor;

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
	public ColumnPanel(Dimension size) {
		setSize(size);
		setBackground(Color.BLACK);
		setCursor(new Cursor(Cursor.TEXT_CURSOR));
	}

	public void update(Observable o, Object arg) {}
}