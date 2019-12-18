package chow.rmi.client;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.*;

import java.util.List;

import java.rmi.RemoteException;

import chow.rmi.client.controller.Receiver;
import chow.rmi.client.PlayersPanel;

public class ClientWindow extends Game implements Observer {
	private static final long serialVersionUID = 1L;
    private static ClientWindow instance;
    private Receiver controller;
	private PlayersPanel playersPanel;
	private final int ONE_BONUS = -1;
    private final int NO_BONUS = 0;

	private int turn = 1, turnBonus = 0;
    private GameMap map;
    private boolean endGameFlag;
    private Die die;

	static final int W_FRAME = 910;
    static final int H_FRAME = 710;

    static final int x0_position = 287;
    static final int y0_position = 9;

    static final Coordinate baseStabeCoor[] = {null};
    static final Coordinate baseDestinationCoor[] = {
        null,
        new Coordinate(x0_position + DISTANCE, y0_position + DISTANCE),
        new Coordinate(x0_position - 5 * DISTANCE, y0_position + 7 * DISTANCE),
        new Coordinate(x0_position + DISTANCE, y0_position + 13 * DISTANCE),
        new Coordinate(x0_position + 7 *DISTANCE, y0_position + 7 *DISTANCE)
    };

    private JPanel mapPanel;
    private JPanel controlPanel;
    private JFrame mainFrame;
    private JButton xuatQuanButton, dropButton;

    private Icon iconDie[], iconHorse[];
    private Image imMap, imControl;
    private JLabel labelDie, turnLabel;

	void prepareDie() {
        final int numberSide = 7;
        iconDie = new ImageIcon[numberSide];
        for (int i = 0; i < numberSide; i++) {
            iconDie[i] = new ImageIcon(getClass().getResource("img/D" + i + ".JPG"));
        }
    }

    void prepareHorse() {
        iconHorse = new ImageIcon[4 + 1];
        for (int i = 1; i <= 4; i++) {
            iconHorse[i] = new ImageIcon(getClass().getResource("img/H" + i + ".GIF"));
        }
    }

    void prepareMap() {
        imMap = new ImageIcon(getClass().getResource("img/board.png")).getImage();
	}

	void playGame() {
		while (!endGameFlag) {
			int color = turn;
			turnBonus = NO_BONUS;
			this.drawTurnLabel(color);

			try {
				diePhaseSema.acquire();
			} catch (InterruptedException exc) {
				System.out.println(exc);
			}
			horsePhaseFlag = true;
			int steps = die.getSteps();
			if(steps == 6){
				turnBonus = ONE_BONUS;
				this.drawXuatQuanButton(map, color);
			}

			map.addPlayerListener(color, steps);
			try {
				horsePhaseSema.acquire();
			} catch (InterruptedException exc) {
				System.out.println(exc);
			}

			map.removePlayerListener(color);
			this.removeXuatQuanButton();
			horsePhaseFlag = false;
			this.drawMap(map);

			diePhaseFlag = true;
			turn = (turn + turnBonus) % map.getNumberPlayer() + 1;

			if(map.isWin()){
				endGameFlag = true;
			}
		}
	}

    public ClientWindow(Receiver controller) throws RemoteException {
		mainFrame = new JFrame();
		ClientWindow.setInstance(this);
		this.setController(controller);
		mainFrame.setTitle("Cờ Cá Ngựa");
		mainFrame.setSize(W_FRAME, H_FRAME);
		mainFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		mainFrame.setLocationRelativeTo(null);
		mainFrame.setLayout(new BorderLayout());
		map = new GameMap();
		die = new Die();
		prepareMap();
        prepareHorse();
        prepareDie();
        drawMap(map);
        drawControl(die);
		this.playersPanel = new PlayersPanel();
		endGameFlag = false;

		mainFrame.getContentPane().add(playersPanel, BorderLayout.SOUTH);
		mainFrame.setResizable(false);
		this.playGame();
		mainFrame.setVisible(true);
		mainFrame.addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent windowEvent) {
                System.exit(0);
            }
		});
    }

	private final int point[] = {0, 6, 12, 14, 20, 26, 28, 34, 40, 42, 48, 54, 56};
    private final int sign[] = {1, 1, -1, 1, 1, 1, 1, -1, 1, -1, -1, -1, -1};

    public Coordinate getCoordinate(HorseSea horse) {
        Coordinate coor = new Coordinate(x0_position, y0_position);
        int position = horse.getPosition();

        if (position == HorseSea.FINISH_POSITION) {
            int color = horse.getColor();
            coor.x = baseDestinationCoor[color].x;
            coor.y = baseDestinationCoor[color].y;

            if (color == BLUE) {
                coor.y += DISTANCE * horse.getRank();
            } else if (color == YELLOW) {
				coor.x += DISTANCE * horse.getRank();
			} else if (color == GREEN) {
				coor.y -= DISTANCE * horse.getRank();
			} else if (color == RED) {
				coor.x -= DISTANCE * horse.getRank();
            }
            return coor;
        }

        for (int i = 1; i < point.length; i++) {
			boolean oddFlag = (i % 2 != 0);

			if (position < point[i]) {
				if (oddFlag) {
					coor.y += sign[i] * DISTANCE * (position - point[i - 1]);
				} else {
					coor.x += sign[i] * DISTANCE * (position - point[i - 1]);
				}
				return coor;
			}

			if (oddFlag) {
				coor.y += sign[i] * DISTANCE * (point[i] - point[i - 1]);
			} else {
				coor.x += sign[i] * DISTANCE * (point[i] - point[i - 1]);
			}
		}
		return coor;
	}

    public void drawHorse(HorseSea horse) {
		Coordinate coor = getCoordinate(horse);// Vị trí cá ngựa trên màn hình.

		horse.setIcon(iconHorse);
		horse.getLabel().setBounds(coor.x, coor.y, 30, 30);
		mapPanel.add(horse.getLabel());
		mainFrame.setVisible(true);
	}

	public void drawDie() {
		labelDie = new JLabel(iconDie[0]);
		controlPanel.add(labelDie);
		mainFrame.setVisible(true);
	}

	public void drawThrowButton(Die die) {
		class AnimationDie implements Runnable {
			Thread thread = null;

			AnimationDie() {
				thread = new Thread(this);
				thread.start();
			}

			public void run() {
				/* Tạo hiệu ứng tung xúc xắc */
				for (int i = 1; i < 15; i++) {
					die.thrown();
					labelDie.setIcon(iconDie[die.getSteps()]);
					sleep(100);
				}

				mainFrame.setVisible(true);
				diePhaseSema.release();
			}
		}

		JButton throwButton = new JButton("Tung XX");

		throwButton.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				if (diePhaseFlag) {
					diePhaseFlag = false;
					new AnimationDie();
				}
			}
		});

		controlPanel.add(throwButton);
		mainFrame.setVisible(true);
	}

	public void drawTime() {
		class DigitalWatch implements Runnable {
			Thread thread = null;
			Date startTime = null;
			String timeString = "";
			JButton button;

			DigitalWatch() {
				thread = new Thread(this);
				button = new JButton();
				controlPanel.add(button);
				thread.start();
				mainFrame.setVisible(true);
			}

			public void reset() {
				Calendar cal = Calendar.getInstance();
				startTime = cal.getTime();
			}

			public void run() {
				reset();

				while (true) {
					Calendar cal = Calendar.getInstance();
					Date currentTime = cal.getTime();
					long time = (currentTime.getTime() - startTime.getTime()) / 1000;
					long second = time % 60, hour = (time / 3600), minute = (time / 60) % 60;
					String s = Long.toString(second), h = Long.toString(hour), m = Long.toString(minute);

					if(second < 10){
						s = "0" + s;
					}
					if(hour < 10){
						h = "0" + h;
					}
					if(minute < 10){
						m = "0" + m;
					}

					timeString = h + ":" + m + ":" + s;

					printTime();
					sleep(1000);
				}
			}

			public void printTime() {
				button.setText(timeString);
			}
		}

		new DigitalWatch();
	}

	public void drawStable(Stable stable) {

	}

	public void drawDropButton() {
		dropButton = new JButton("Bỏ lượt");

		dropButton.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				if (horsePhaseFlag) {
					horsePhaseFlag = false;
					horsePhaseSema.release();
				}
			}
		});

		controlPanel.add(dropButton);
		mainFrame.setVisible(true);
	}

	public void drawTurnLabel(int color) {
		turnLabel.setIcon(iconHorse[color]);
		mainFrame.setVisible(true);
	}

	public void drawXuatQuanButton(GameMap map, int color) {
		xuatQuanButton = new JButton("Xuất quân");
		xuatQuanButton.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				if (horsePhaseFlag) {
					if (map.xuatQuan(color)) {
						horsePhaseFlag = false;
						horsePhaseSema.release();
					}
				}
			}
		});
		controlPanel.add(xuatQuanButton);
		mainFrame.setVisible(true);
	}

	public void removeXuatQuanButton() {
		if (xuatQuanButton != null) {
			controlPanel.remove(xuatQuanButton);
			xuatQuanButton = null;
		}
	}

	public void drawTurnLabel() {
		turnLabel = new JLabel("");
		turnLabel.setOpaque(true);
		controlPanel.add(turnLabel);
	}

	public void drawControl(Die die) {
		controlPanel = new JPanel() {
			@Override
			public void paintComponent(Graphics g) {
				super.paintComponent(g);
				g.drawImage(imControl, 0, 100, W_FRAME - (W_FRAME - 215), H_FRAME - 15 - 100 + 35 - 8, this);
			}
		};
		mainFrame.add(controlPanel);

		drawTurnLabel();
		drawDie();
		drawThrowButton(die);
		drawDropButton();
		drawTime();

		mainFrame.setVisible(true);
	}

	public void drawMap(GameMap map) {
		mapPanel = new JPanel() {
			@Override
			public void paintComponent(Graphics g) {
				super.paintComponent(g);
				g.drawImage(imMap, 0, 0, W_FRAME - 215, H_FRAME - 15, this); //background
			}
		};
		mapPanel.setPreferredSize(new Dimension(W_FRAME - 215, H_FRAME - 15));
		mapPanel.setLayout(null);

		int num = map.getNumberPlayer();
		for (int i = 1; i <= num; i++) {
			for (int j = 0; j < 4; j++) {
				if (map.getPlayer()[i].horse[j] != null) {
					drawHorse(map.getPlayer()[i].horse[j]);
				}
			}
		}

		mainFrame.add(mapPanel, BorderLayout.WEST);
		mainFrame.setVisible(true);
	}

    @Override
	public void update(Observable arg0, Object message) {
		if (message instanceof List) {
			this.getPlayersPanel().update(arg0, message);
        }
        else if (message instanceof Player || message instanceof Boolean) {
			this.update(arg0, message);
		}
    }

    public Receiver getController() {
		return this.controller;
	}

	public void setController(Receiver controller) {
		this.controller = controller;
	}

	public PlayersPanel getPlayersPanel() {
		return playersPanel;
	}

	public void setPlayersPanel(PlayersPanel playersPanel) {
		this.playersPanel = playersPanel;
	}

    public static ClientWindow getInstance() {
		return instance;
    }

    public static void setInstance(ClientWindow instance) {
		ClientWindow.instance = instance;
	}
}
