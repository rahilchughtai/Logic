

import java.awt.event.*;

public class DelayListener implements ActionListener {

	RangierFrame ani;

	public DelayListener(RangierFrame ani)
	{
		this.ani = ani;
	}

	public void actionPerformed(ActionEvent e) {
		ani.setMDelay(Integer.parseInt(ani.getMDelayField().getText().trim()));
	}

}