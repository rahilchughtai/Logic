

import java.io.*;
import java.awt.*;
import javax.swing.*;
import javax.imageio.*;

public class GleisePanel extends JPanel {
	private Zug zug;
	private Image pic;
	private boolean lvl = false;
	private RailVehicle lvlPic;

	public GleisePanel() {
		zug = new Zug();
	}

	public void moveVehicle(int x,int y,int v) {
		zug.moveVehicle(x, y, v);
	}

	public int getPosX(int v) {
		return zug.getPosX(v);
	}

	public void setPosX(int v, int x) {
		zug.setPosX(v,x);
	}

	public void kuppeln(int v) {
		zug.changeKuppel(v);
	}

	public boolean getKuppel(int v) {
		return zug.isKuppel(v);
	}

	public int getPosY(int v) {
		return zug.getPosY(v);
	}

	public void setPosY(int v, int y) {
		zug.setPosY(v,y);
	}

	public void setPosXY(int v, int x,int y) {
		zug.setPosX(v,x);
		zug.setPosY(v,y);
	}

	public void setVehicle(int v, String path) {
		zug.setVehicle(v, path);
	}

	public void lvlAni() {
		lvlPic = new RailVehicle("lvl.png",zug.getPosX(1)-10,zug.getPosY(1)-20,100,35);
		lvl = true;
	}

	public void lvlKill() {
		lvlPic = null;
		lvl = false;
	}

	public void moveLvl(int y) {
		lvlPic.moveVehicle(0, -y);
	}

	public void paintComponent(Graphics g) {
		Graphics2D g2 = (Graphics2D) g;
		setBackground(Color.WHITE);
		super.paintComponent(g);

		try {
			Image gleise = ImageIO.read(new File("rangier.gif"));
			g2.drawImage(gleise, 0, 0, null);
		} catch (IOException e) {
			e.printStackTrace();
		}

		for(int i=1;i<=4;++i) {
			pic = zug.getVehicle(i);
			g2.drawImage(pic, zug.getPosX(i), zug.getPosY(i), null);
		}

		if(lvl) {
			pic = lvlPic.getVehicle();
			g2.drawImage(pic, lvlPic.getPosX(), lvlPic.getPosY(), null);
		}
	}
}
