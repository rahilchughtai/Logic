

import java.awt.event.*;

public class RunListener implements ActionListener {

	RangierFrame ani;

	public RunListener(RangierFrame ani)
	{
		this.ani = ani;
	}

	public void actionPerformed(ActionEvent e) {
		if(!ani.isMRun()) {
			ani.setMRun(true);
			ani.getMRunBut().setText("Pause");
		} else {
			ani.setMRun(false);
			ani.getMRunBut().setText("Run");

		}

		//ani.setMDelay(Integer.parseInt(ani.getMDelayField().getText().trim()));
	}

}