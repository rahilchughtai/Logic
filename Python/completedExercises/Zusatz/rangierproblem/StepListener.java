

import java.awt.event.*;

public class StepListener implements ActionListener {

	RangierFrame ani;

	public StepListener(RangierFrame ani)
	{
		this.ani = ani;
	}

	public void actionPerformed(ActionEvent e) {
		ani.setMNextStep(true);
	}

}