
import java.io.*;
import java.awt.*;

import javax.swing.*;
import javax.imageio.*;

public class Zug {

	private RailVehicle m1;
	private RailVehicle m2;
	private RailVehicle m3;
	private RailVehicle m4;


	public Zug() {
		m1 = new RailVehicle("lock.png",40,344,80,80);
		m2 = new RailVehicle("w_2.png",435,5,80,80);
		m3 = new RailVehicle("w_3.png",515,5,80,80);
		m4 = new RailVehicle("w_4.png",595,5,80,80);

		m1.setKuppel(true);
		m2.setKuppel(false);
		m3.setKuppel(false);
		m4.setKuppel(false);
	}

	public int getPosX(int v) {
		if(v==1) return m1.getPosX();
		if(v==2) return m2.getPosX();
		if(v==3) return m3.getPosX();
		if(v==4) return m4.getPosX();

		return 0;
	}

	public int getPosY(int v) {
		if(v==1) return m1.getPosY();
		if(v==2) return m2.getPosY();
		if(v==3) return m3.getPosY();
		if(v==4) return m4.getPosY();

		return 0;
	}

	public void moveVehicle(int x, int y, int v) {
		if(v==1) m1.moveVehicle(x, y);
		if(v==2 && m2.isKuppel()) m2.moveVehicle(x, y);
		if(v==3 && m3.isKuppel()) m3.moveVehicle(x, y);
		if(v==4 && m4.isKuppel()) m4.moveVehicle(x, y);
	}

	public void changeKuppel(int v) {
		if(v==1) ;
		if(v==2) m2.changeKuppel();
		if(v==3) m3.changeKuppel();
		if(v==4) m4.changeKuppel();
	}

	public boolean isKuppel(int v) {
		if(v==1) return true;
		if(v==2) return m2.isKuppel();
		if(v==3) return m3.isKuppel();
		if(v==4) return m4.isKuppel();

		return false;
	}


	public Image getVehicle(int v) {
		if(v==1) return m1.getVehicle();
		if(v==2) return m2.getVehicle();
		if(v==3) return m3.getVehicle();
		if(v==4) return m4.getVehicle();
		return null;
	}

	public void setVehicle(int v, String path) {
		if(v==1) {
			RailVehicle old = m1;
			m1 = new RailVehicle(path,old.getPosX(),old.getPosY(),80,80);
		}
		if(v==2) {
			RailVehicle old = m2;
			m2 = new RailVehicle(path,old.getPosX(),old.getPosY(),80,80);
		}
		if(v==3) {
			RailVehicle old = m3;
			m3 = new RailVehicle(path,old.getPosX(),old.getPosY(),80,80);
		}
		if(v==4) {
			RailVehicle old = m4;
			m4 = new RailVehicle(path,old.getPosX(),old.getPosY(),80,80);
		}
	}

	public void setPosY(int v,int y) {
		if(v==1) m1.setPosY(y);
		if(v==2) m2.setPosY(y);
		if(v==3) m3.setPosY(y);
		if(v==4) m4.setPosY(y);
	}

	public void setPosX(int v, int x) {
		if(v==1) m1.setPosX(x);
		if(v==2) m2.setPosX(x);
		if(v==3) m3.setPosX(x);
		if(v==4) m4.setPosX(x);

	}


}