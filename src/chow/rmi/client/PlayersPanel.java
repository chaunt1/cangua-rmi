package chow.rmi.client;

import java.awt.BorderLayout;
import java.awt.HeadlessException;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.List;
import java.util.Observable;
import java.util.Observer;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.ListSelectionModel;
import javax.swing.event.ListSelectionEvent;

public class PlayersPanel extends JPanel implements Observer {
    private static final long serialVersionUID = 1L;
    private JList list;
    private DefaultListModel listModel;
    private static final String fireString = "Chơi cùng";
    private JButton playWithButton;

    public PlayersPanel() {
        super(new BorderLayout());

        listModel = new DefaultListModel();
        listModel.addElement("");

        list = new JList(listModel);
        list.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		list.setSelectedIndex(0);
		list.setVisibleRowCount(5);
		JScrollPane listScrollPane = new JScrollPane(list);

		playWithButton = new JButton(fireString);
		playWithButton.setActionCommand(fireString);
        playWithButton.addActionListener(new PlayWithListener());

        JPanel buttonPane = new JPanel();
		buttonPane.setLayout(new BoxLayout(buttonPane,
				BoxLayout.LINE_AXIS));
		buttonPane.add(playWithButton);
		buttonPane.add(Box.createHorizontalStrut(5));
		buttonPane.setBorder(BorderFactory.createEmptyBorder(5,31,5,31));

		add(listScrollPane, BorderLayout.CENTER);
		add(buttonPane, BorderLayout.PAGE_END);
    }

    class PlayWithListener implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			String username = (String) list.getModel().getElementAt(list.getSelectedIndex());
			try {
				if (!ClientWindow.getInstance().getController().isUserReady(username)) {
					JOptionPane.showMessageDialog(null, username + " is playing.");
				} else {
					ClientWindow.getInstance().getController().startGameWithOpponent(username);
				}
			} catch (HeadlessException e1) {
				e1.printStackTrace();
			} catch (RemoteException e1) {
				e1.printStackTrace();
			}
		}
    }

    public void valueChanged(ListSelectionEvent e) {
		if (e.getValueIsAdjusting() == false) {
			if (list.getSelectedIndex() == -1) {
				//No selection, disable fire button.
				playWithButton.setEnabled(false);
			} else {
				//Selection, enable the fire button.
				playWithButton.setEnabled(true);
			}
		}
    }

    @Override
	public void update(Observable o, Object arg) {
		@SuppressWarnings("unchecked")
		List<String> list = ((ArrayList<String>)arg);
		this.listModel.clear();
		String currentUsername = ClientWindow.getInstance().getController().getMyUsername();
		for (String username : list) {
			if (!username.equals(currentUsername)) {
				this.listModel.addElement(username);
			}
		}
	}
}
