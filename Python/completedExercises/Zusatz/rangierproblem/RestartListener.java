

import java.awt.event.*;

public class RestartListener implements ActionListener {

	RangierFrame ani;

	public RestartListener(RangierFrame ani)
	{
		this.ani = ani;
	}

	public void actionPerformed(ActionEvent e) {
		ani.reset();
	}

}