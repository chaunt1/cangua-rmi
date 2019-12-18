package chow.rmi.client;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.ComponentOrientation;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;
import java.rmi.RemoteException;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JSplitPane;
import javax.swing.JTextField;

import chow.rmi.client.controller.Receiver;

public class WelcomeWindow extends JFrame {
    private static final long serialVersionUID = 1L;
    private Receiver controller;
    private JButton blogin;
    private JTextField txuser;
    private JTextField serverIP;
    private JPanel loginPanel, imagePanel;
	private JLabel username, serverIPLabel;
    private int x = 0, y = 0;

    public WelcomeWindow() {
        super("Kết Nối Tới Server");
        this.setComponentOrientation(ComponentOrientation.LEFT_TO_RIGHT);
        this.setBackground(Color.BLACK);
        this.setLocationRelativeTo(null);
        this.setLayout(new BorderLayout());

        this.txuser = new JTextField(10);
        this.serverIP = new JTextField(10);
        this.username = new JLabel("Tên Người Chơi: ");
        this.username.setForeground(Color.WHITE);
        this.serverIPLabel = new JLabel("Server IP: ");
        this.serverIPLabel.setForeground(Color.WHITE);

        this.blogin = new JButton();
        this.blogin.setIcon(new ImageIcon(
				new ImageIcon(getClass().getResource("img/login.png")).getImage()
						.getScaledInstance(150, 40, java.awt.Image.SCALE_SMOOTH)));
        this.blogin.setBorderPainted(false);

        this.loginPanel = new JPanel();
        this.loginPanel.setBackground(Color.BLACK);
        this.loginPanel.add(username);
        this.loginPanel.add(txuser);
        this.loginPanel.add(serverIPLabel);
        this.loginPanel.add(serverIP);
        this.loginPanel.add(blogin);

        JSplitPane splitPane = new JSplitPane(JSplitPane.VERTICAL_SPLIT, this.loginPanel, imagePanel);
		splitPane.setResizeWeight(0);
		splitPane.setEnabled(false);
		splitPane.setDividerSize(0);
		this.add(splitPane, BorderLayout.CENTER);

		this.setVisible(true);
		this.setSize(300, 200);

		actionlogin();
    }

    public void actionlogin(){
		blogin.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent ae) {
				String puname = txuser.getText();
				String puip = serverIP.getText();
				getController().setMyUsername(puname);
				int connectionAttempt = getController().connect(puip);
				if(connectionAttempt > 0) {
					try {
						Client.showMainWindow();
					} catch (RemoteException e) {
						e.printStackTrace();
					}
					dispose();
				} else if (connectionAttempt == 0){
					JOptionPane.showMessageDialog(null,"Tên Người Chơi Đã Có");
					txuser.requestFocus();
				} else {
					JOptionPane.showMessageDialog(null,"Không Thể Kết Nối Tới Server");
				}
			}
		});
	}

	public Receiver getController() {
		return controller;
	}

	public void setController(Receiver controller) {
		this.controller = controller;
	}
}