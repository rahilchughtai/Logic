

import java.awt.*;
import javax.swing.*;

public class RangierFrame extends JFrame {


	public static final int WIDTH=1100;
	public static final int HEIGHT=600;

	private boolean mNextStep;
	private boolean mRun;
	private int mDelay;
	private int mStep;

	private JTextField mDelayField;
	private JButton mRunBut;
	private JButton mStepBut;
	private JButton mRestart;
	private GleisePanel gleis;
	ComparableList<ListTripel> mPath;

	public RangierFrame(ComparableList<ListTripel> path) {
		//
		// Rahmenwerte (Frame)
		setTitle("Rangier Problem");
		setSize(WIDTH,HEIGHT);
		//
		// Member Variablen initialisieren
		mNextStep = false;
		mDelay = 40;
		mRun = false;
		mPath = path;
		mStep = 0;
		//
		// Bedienelemente
		JLabel lDelay = new JLabel("Delay:");
		mStepBut = new JButton("Step");
		mDelayField = new JTextField(5);
		mDelayField.setMaximumSize(mDelayField.getPreferredSize());
		mRunBut = new JButton("Run");
		mRestart = new JButton("Restart");

		DelayListener l1 = new DelayListener(this);
		mDelayField.addActionListener(l1);

		RunListener l2 = new RunListener(this);
		mRunBut.addActionListener(l2);

		StepListener l3 = new StepListener(this);
		mStepBut.addActionListener(l3);

		RestartListener l4 = new RestartListener(this);
		mRestart.addActionListener(l4);

		Box elementPanel = Box.createHorizontalBox();
		elementPanel.add(Box.createHorizontalGlue());
		elementPanel.add(mStepBut);
		elementPanel.add(Box.createHorizontalGlue());
		elementPanel.add(lDelay);
		elementPanel.add(mDelayField);
		elementPanel.add(Box.createHorizontalGlue());
		elementPanel.add(mRunBut);
		elementPanel.add(Box.createHorizontalGlue());
		elementPanel.add(mRestart);
		elementPanel.add(Box.createHorizontalGlue());
		//
		// Hintergrund + Gleise + RailVehicles
		gleis = new GleisePanel();
		//
		// Layout und anordnung
		JPanel mainPanel = new JPanel();
		mainPanel.setLayout(new BorderLayout());
		mainPanel.add(elementPanel, BorderLayout.NORTH);
		mainPanel.add(gleis, BorderLayout.CENTER);
		//mainPanel.add(lock, BorderLayout.CENTER);
		//
		add(mainPanel);
		//
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		//
		// Das Frame anzeigen
		setVisible(true);
		//
		// Fahrzeuge bewegen

		while(true) {
			for(mStep = 0; mStep < path.size()-1; mStep++)
			{
				while(!mNextStep && !mRun) {
					try {
						Thread.sleep(mDelay);
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}

				Move move = Animation.move(path.get(mStep), path.get(mStep+1));
				System.out.println("Move to:"+move.fromTo +" West/East:"+move.westEast+" Wagons:"+move.waggons);

				kuppelList(move.waggons);

				int ziel = getZiel(move.fromTo);
				//System.out.println("ziel; "+ziel);
				int pp = this.getPP(path.get(mStep), ziel, move.westEast);


				if(mStep == 1) {
					pp = this.getPP(path.get(mStep), ziel, 1);
					this.untenNachObenLinks(pp);
					//System.out.println("PP; "+pp+" Ziel: "+ziel);
				} else {
					//System.out.println("PP; "+pp+" Ziel: "+ziel);
					if(move.fromTo == 2) this.linksNachRechts(pp);
					if(move.fromTo == 3) this.rechtsNachLinks(pp);
					if(move.fromTo == 4 && move.westEast == 1) this.obenNachUntenLinks(pp);
					if(move.fromTo == 4 && move.westEast == 0) this.obenNachUntenRechts(pp);
					if(move.fromTo == 5 && move.westEast == 1) this.untenNachObenLinks(pp);
					if(move.fromTo == 5 && move.westEast == 0) this.untenNachObenRechts(pp);
				}


				this.refitAll(path.get(mStep+1));
				this.entKuppelAll();

				mNextStep = false;

			}
			for(int a=0;a<5;a++) {
				gleis.setVehicle(1, "lock_2.png");
				malen(100);
				gleis.setVehicle(1, "lock.png");
				malen(100);
			}

			gleis.lvlAni();
			for(int b=0;b<25;++b) {
				gleis.moveLvl(4);
				malen();
			}
			gleis.lvlKill();
			malen();

			while(mStep == path.size()-1) {
				try {
					Thread.sleep(mDelay);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}


		}

	}

	private int getZiel(int to) {
		if(to==2) return 2;
		if(to==3) return 1;
		if(to==4) return 2;
		if(to==5) return 0;

		return -1;
	}

	private void refitAll(ListTripel a) {
		for(int i=0;i<=2;++i) {
			if(i==0) refit(a.getFirst(),i);
			if(i==1) refit(a.getSecond(),i);
			if(i==2) refit(a.getThird(),i);
		}

	}

	private void refit(ComparableList<Integer> belegung, int gNr) {
		if((gNr == 0 && belegung.contains(0)) || (gNr == 2 && belegung.contains(0))) {
			if(belegung.size()==1) {
				int v = belegung.get(0)+1;
				while(gleis.getPosX(v) != 515) {
					for(int i=0;i<=4;++i) {
						if(gleis.getPosX(v) < 515) {
							gleis.moveVehicle(1, 0, v);
						}
						if(gleis.getPosX(v) > 515) {
							gleis.moveVehicle(-1, 0, v);
						}
					}
					malen();
				}
			}

			if(belegung.size()==2) {
				int v1 = belegung.get(0)+1;
				int v2 = belegung.get(1)+1;

				if(!gleis.getKuppel(belegung.get(0)+1)) kuppeln(belegung.get(0)+1);
				if(!gleis.getKuppel(belegung.get(1)+1)) kuppeln(belegung.get(1)+1);

				while(gleis.getPosX(v1) != 475 || gleis.getPosX(v2) != 555) {
					for(int i=0;i<=4;++i) {
						if(gleis.getPosX(v1) < 475) {
							gleis.moveVehicle(1, 0, v1);
						}
						if(gleis.getPosX(v1) > 475) {
							gleis.moveVehicle(-1, 0, v1);
						}
						if(gleis.getPosX(v2) < 555) {
							gleis.moveVehicle(1, 0, v2);
						}
						if(gleis.getPosX(v2) > 555) {
							gleis.moveVehicle(-1, 0, v2);
						}
					}
					malen();
				}
			}
			if(belegung.size()==3) {
				int v1 = belegung.get(0)+1;
				int v2 = belegung.get(1)+1;
				int v3 = belegung.get(2)+1;

				if(!gleis.getKuppel(belegung.get(0)+1)) kuppeln(belegung.get(0)+1);
				if(!gleis.getKuppel(belegung.get(1)+1)) kuppeln(belegung.get(1)+1);
				if(!gleis.getKuppel(belegung.get(2)+1)) kuppeln(belegung.get(2)+1);




				while(gleis.getPosX(v1) != 435 || gleis.getPosX(v2) != 515 || gleis.getPosX(v3) != 595) {
					for(int i=0;i<=4;++i) {
						if(gleis.getPosX(v1) < 435) {
							gleis.moveVehicle(1, 0, v1);
						}
						if(gleis.getPosX(v1) > 435) {
							gleis.moveVehicle(-1, 0, v1);
						}
						if(gleis.getPosX(v2) < 515) {
							gleis.moveVehicle(1, 0, v2);
						}
						if(gleis.getPosX(v2) > 515) {
							gleis.moveVehicle(-1, 0, v2);
						}
						if(gleis.getPosX(v3) < 595) {
							gleis.moveVehicle(1, 0, v3);
						}
						if(gleis.getPosX(v3) > 595) {
							gleis.moveVehicle(-1, 0, v3);
						}
					}
					malen();
				}
			}
			if(belegung.size()==4) {
				int v1 = belegung.get(0)+1;
				int v2 = belegung.get(1)+1;
				int v3 = belegung.get(2)+1;
				int v4 = belegung.get(3)+1;

				for(int x=1;x<=4;x++) {
					if(!gleis.getKuppel(x)) gleis.kuppeln(x);
				}


				while(gleis.getPosX(v1) != 395 || gleis.getPosX(v2) != 475 || gleis.getPosX(v3) != 555 || gleis.getPosX(v4) != 635) {
					for(int i=0;i<=4;++i) {
						if(gleis.getPosX(v1) < 395) {
							gleis.moveVehicle(1, 0, v1);
						}
						if(gleis.getPosX(v1) > 395) {
							gleis.moveVehicle(-1, 0, v1);
						}
						if(gleis.getPosX(v2) < 475) {
							gleis.moveVehicle(1, 0, v2);
						}
						if(gleis.getPosX(v2) > 475) {
							gleis.moveVehicle(-1, 0, v2);
						}
						if(gleis.getPosX(v3) < 555) {
							gleis.moveVehicle(1, 0, v3);
						}
						if(gleis.getPosX(v3) > 555) {
							gleis.moveVehicle(-1, 0, v3);
						}
						if(gleis.getPosX(v4) < 635) {
							gleis.moveVehicle(1, 0, v4);
						}
						if(gleis.getPosX(v4) > 635) {
							gleis.moveVehicle(-1, 0, v4);
						}
					}
					malen();
				}
			}
		}

		if(gNr == 1 && belegung.contains(0)) {
			for(int i=0;i<belegung.size();++i) {
				int v = belegung.get(i)+1;
				while(gleis.getPosX(v) != 40+(i*80)) {
					for(int j=0;j<=4;++j) {
						if(gleis.getPosX(v) > 40+(i*80)) {
							gleis.moveVehicle(-1, 0, v);
						}
						if(gleis.getPosX(v) < 40+(i*80)) {
							gleis.moveVehicle(1, 0, v);
						}
					}
					malen();
				}
			}
		}
	}

	//
	// Ziel: 0 oben, 1 abstellgleis, 2 unten
	// Richtung: 1 links, 0 rechts
	private int getPP(ListTripel a, int ziel, int richtung) {
		ComparableList<Integer> x = null;

			if(ziel==0) {
				x=a.getFirst();
			}
			if(ziel==1) {
				x=a.getSecond();
			}
			if(ziel==2) {
				x=a.getThird();
			}
			if(richtung==0) {
				if(x.size()>0) return gleis.getPosX(x.getLast()+1)+80;
				else {
					if(ziel == 1) {
						return 40;
					}
					return 515;
				}

			}
			if(richtung == 1) {
				if(x.size()>0) return gleis.getPosX(x.getFirst()+1)-80;
				else {
					if(ziel == 1) {
						return 40;
					}
					return 515;
				}
			}

		return -1;
	}


	private void obenNachUntenLinks(int pp) {
		int done = 0;
		int phase[] = {1,1,1,1,1};
		int x[] = {0,0,0,0,0};
		int z[] = {0,0,0,0,0};
		while(done<10) {
			// Alle Fahrzeuge die angekuppelt sind bewegen
			// (kuppel überprüfung liegt in der zug klasse)
			for(int i=1;i<=4;++i) {
				if(gleis.getKuppel(i)) {
				// Fahrzeug weit genug links um bogen zu beginnen?
				if(gleis.getPosX(i) < 360 || phase[i] > 1) {
					phase[i]=2;
					//1. Hälfte des Bogens bewältigt?
					if(gleis.getPosY(i) > 173) {
						// 2. Hälfte des Bogens bewältigt?
						if(gleis.getPosY(i) > 340) {
							// Parkposition erreicht?
							if(gleis.getPosX(i) > pp-(80*done)) {
								done++;
							} else {
								int k = 0;
								for(k=0; k<=12;++k) {
									if(gleis.getPosX(i) <= pp-(80*done)) {
										gleis.moveVehicle(1, 0, i);
									} else {
										done++;
									}
								}
							}
						} else {
							double y = Math.sqrt(16*16-z[i]*z[i]);
							gleis.moveVehicle(z[i],(int)y,i);
							z[i]++;
						}
					} else {
						double y = Math.sqrt(16*16-x[i]*x[i]);
						gleis.moveVehicle(-(int)y,x[i],i);
						x[i]++;
					}
				} else {
					int j = 0;
					for(j=0; j<=12;++j) {
						if(gleis.getPosX(i) >= 360) {
							gleis.moveVehicle(-1, 0, i);
						}
					}
				}
			}
			}
			malen();
		}
	}

	private void obenNachUntenRechts(int pp) {
		int done = 0;
		int phase[] = {1,1,1,1,1};
		int x[] = {0,0,0,0,0};
		int z[] = {0,0,0,0,0};
		while(done<10) {
			// Alle Fahrzeuge die angekuppelt sind bewegen
			// (kuppel überprüfung liegt in der zug klasse)
			for(int i=1;i<=4;++i) {
				if(gleis.getKuppel(i)) {
					// Fahrzeug weit genug links um bogen zu beginnen?
					if(gleis.getPosX(i) > 750 || phase[i] > 1) {
						phase[i]=2;
						//1. Hälfte des Bogens bewältigt?
						if(gleis.getPosY(i) > 173) {
							// 2. Hälfte des Bogens bewältigt?
							if(gleis.getPosY(i) > 340) {
								// Parkposition erreicht?
								if(gleis.getPosX(i) < pp+80*done) {
									done++;
								} else {
									int k = 0;
									for(k=0; k<=12;++k) {
										if(gleis.getPosX(i) >= pp+80*done) {
											gleis.moveVehicle(-1, 0, i);
										} else {
											done++;
										}
									}
								}
							} else {
								double y = Math.sqrt(16*16-z[i]*z[i]);
								gleis.moveVehicle(-z[i],(int)y,i);
								z[i]++;
							}
						} else {
							double y = Math.sqrt(16*16-x[i]*x[i]);
							gleis.moveVehicle((int)y,x[i],i);
							x[i]++;
						}
					} else {
						int j = 0;
						for(j=0; j<=12;++j) {
							if(gleis.getPosX(i) <= 750) {
								gleis.moveVehicle(1, 0, i);
							}
						}
					}
				}
			}
			malen();
		}

	}

	private void untenNachObenLinks(int pp) {
		int done = 0;
		int phase[] = {1,1,1,1,1};
		int x[] = {0,0,0,0,0};
		int z[] = {0,0,0,0,0};
		while(done<10) {
			// Alle Fahrzeuge die angekuppelt sind bewegen
			// (kuppel überprüfung liegt in der zug klasse)
			for(int i=1;i<=4;++i) {
				if(gleis.getKuppel(i)) {
				// Fahrzeug weit genug links um bogen zu beginnen?
				if(gleis.getPosX(i) < 360 || phase[i] > 1) {
					phase[i]=2;
					//1. Hälfte des Bogens bewältigt?
					if(gleis.getPosY(i) < 193) {
						// 2. Hälfte des Bogens bewältigt?
						if(gleis.getPosY(i) < 10) {
							// Parkposition erreicht?
							if(gleis.getPosX(i) > pp-80*done) {
								done++;
							} else {
								int k = 0;
								for(k=0; k<=13;++k) {
									if(gleis.getPosX(i) <= pp-80*done) {
										gleis.moveVehicle(1, 0, i);
									}
								}
							}
						} else {
							double y = Math.sqrt(16*16-z[i]*z[i]);
							gleis.moveVehicle(z[i],-(int)y,i);
							z[i]++;
						}
					} else {
						double y = Math.sqrt(16*16-x[i]*x[i]);
						gleis.moveVehicle(-(int)y,-x[i],i);
						x[i]++;
					}
				} else {
					int j = 0;
					for(j=0; j<=12;++j) {
						if(gleis.getPosX(i) >= 360) {
							gleis.moveVehicle(-1, 0, i);
						}
					}
				}
			}
			}
			malen();
		}
	}

	private void untenNachObenRechts(int pp) {
		int done = 0;
		int phase[] = {1,1,1,1,1};
		int x[] = {0,0,0,0,0};
		int z[] = {0,0,0,0,0};
		while(done<10) {
			// Alle Fahrzeuge die angekuppelt sind bewegen
			// (kuppel überprüfung liegt in der zug klasse)
			for(int i=1;i<=4;++i) {
				if(gleis.getKuppel(i)) {
				// Fahrzeug weit genug rechts um bogen zu beginnen?
				if(gleis.getPosX(i) > 750 || phase[i] > 1) {
					phase[i]=2;
					//1. Hälfte des Bogens bewältigt?
					if(gleis.getPosY(i) < 193) {
						// 2. Hälfte des Bogens bewältigt?
						if(gleis.getPosY(i) < 10) {
							// Parkposition erreicht?
							if(gleis.getPosX(i) < pp+80*done) {
								done++;
							} else {
								int k = 0;
								for(k=0; k<=12;++k) {
									if(gleis.getPosX(i) >= pp+80*done) {
										gleis.moveVehicle(-1, 0, i);
									} else {
										done++;
									}
								}
							}
						} else {
							double y = Math.sqrt(16*16-z[i]*z[i]);
							gleis.moveVehicle(-z[i],-(int)y,i);
							z[i]++;
						}
					} else {
						double y = Math.sqrt(16*16-x[i]*x[i]);
						gleis.moveVehicle((int)y,-x[i],i);
						x[i]++;
					}
				} else {
					int j = 0;
					for(j=0; j<=12;++j) {
						if(gleis.getPosX(i) <= 750) {
							gleis.moveVehicle(1, 0, i);
						}
					}
				}
			}
			}
			malen();
		}
	}

	private void linksNachRechts(int pp) {
		int done = 0;
		while(done<30) {
			for(int i=1;i<=4;++i) {
				for(int j=0;j<=12;++j) {
					if(gleis.getPosX(i) < pp-(80*done)) {
						gleis.moveVehicle(1, 0, i);
					}
				}
				if(gleis.getPosX(i) >= pp-(80*done) && gleis.getKuppel(i)) {
					done++;
				}
			}
			malen();
		}
	}

	private void rechtsNachLinks(int pp) {
		int done = 0;
		while(done<30) {
			for(int i=1;i<=4;++i) {
				for(int j=0;j<=12;++j) {
					if(gleis.getPosX(i) > pp+(80*done)) {
						gleis.moveVehicle(-1, 0, i);
					}
				}
				if(gleis.getPosX(i) <= pp+(80*done) && gleis.getKuppel(i)) {
					done++;
				}
			}
			malen();
		}
	}

	private void kuppelList(ComparableList<Integer> wag) {
		for(Integer i: wag) {
			kuppeln(i+1);
		}
	}

	private void kuppeln(int vNr) {
		//System.out.println("Kuppel Wagen "+vNr+" an");
		gleis.kuppeln(vNr);
		malen();
	}

	private void entKuppelAll() {
		for(int i=1;i<=4;++i) {
			if(gleis.getKuppel(i)) kuppeln(i);
		}

	}

	public void reset() {
		mNextStep = false;
		mDelay = 40;
		mRun = false;
		mStep = 0;
		//
		// Fahrzeuge zurück setzen
		gleis.setPosXY(1, 40, 344);
		gleis.setPosXY(2, 435, 5);
		gleis.setPosXY(3, 515, 5);
		gleis.setPosXY(4, 595, 5);
		this.entKuppelAll();
		this.entKuppelAll();
		this.mRunBut.setText("Run");
		gleis.lvlKill();
		malen();
	}

	private void malen() {
		repaint();
		try {
			Thread.sleep(mDelay);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
	private void malen(int x) {
		repaint();
		try {
			Thread.sleep(x);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

	public boolean isMNextStep() {
		return mNextStep;
	}

	public void setMNextStep(boolean nextStep) {
		mNextStep = nextStep;
	}

	public boolean isMRun() {
		return mRun;
	}

	public void setMRun(boolean run) {
		mRun = run;
	}

	public JTextField getMDelayField() {
		return mDelayField;
	}

	public void setMDelayField(JTextField delayField) {
		mDelayField = delayField;
	}

	public int getMDelay() {
		return mDelay;
	}

	public void setMDelay(int delay) {
		mDelay = delay;
	}

	public JButton getMRunBut() {
		return mRunBut;
	}

	public void setMRunBut(JButton runBut) {
		mRunBut = runBut;
	}

	public ComparableList<ListTripel> getMPath() {
		return mPath;
	}

}

