package mud.view;

//Import other parts of mud package

//Import observable stuff
import java.util.Observer;
import java.util.Observable;

/*
* Class         MudPanel
* Package       mud.views
* Contributors  --------
* Public Methods:
*     ---
*/
public abstract class MudPanel implements Observer {
	public void update(Observable o, Object arg) {}
}