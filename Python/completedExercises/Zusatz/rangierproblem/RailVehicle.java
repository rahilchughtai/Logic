

import java.io.*;
import java.awt.*;
import javax.swing.*;
import javax.imageio.*;

public class RailVehicle extends JPanel {
	private int mPosX;
	private int mPosY;
	private int mAngel;
	private boolean pan;
	private Image Vehicle;

	public RailVehicle(String imgPath, int posX, int posY, int sizeX, int sizeY) {
		mPosX = posX;
		mPosY = posY;
		mAngel = 0;
		try {
			Vehicle = ImageIO.read(new File(imgPath));
			Vehicle = Vehicle.getScaledInstance(sizeX, sizeY, 10);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void moveVehicle(int x,int y) {
		mPosX += x;
		mPosY += y;
	}
	public Image getVehicle() {
		return Vehicle;
	}
	public void setVehicle(Image newImg) {
		Vehicle = newImg;
	}


	public int getPosX() {
		return mPosX;
	}
	public void setPosX(int posX) {
		mPosX = posX;
	}
	public int getPosY() {
		return mPosY;
	}
	public void setPosY(int posY) {
		mPosY = posY;
	}
	public int getAngel() {
		return mAngel;
	}
	public void setAngel(int angel) {
		mAngel = angel;
	}

	public boolean isKuppel() {
		return pan;
	}

	public void setKuppel(boolean panel) {
		this.pan = panel;
	}

	public void changeKuppel() {
		if(pan) pan = false;
		else pan = true;
	}

}