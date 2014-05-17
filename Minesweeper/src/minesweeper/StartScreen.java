package minesweeper;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Point;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.IOException;
import java.util.Collection;

import javax.imageio.ImageIO;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;

public class StartScreen extends JFrame{

	private static final long serialVersionUID = 1L;
	GUI gui;
	BackgroundPanelClass background_panel;
	
	JButton server_btn;
	JButton client_btn;
	JScrollPane scrollPan;
	Button [] buttons;	
	Collection<Player> clientsList;
	
	JTextField name_field;
	
	final Point backgroundSize = new Point(800,600);
	
	JLabel labels[];
	JLabel myname = new JLabel();
	
	JLabel setYourName;
	
	JLabel logLabels[];
	final int logLabelsNum = 5;
	
	final Point screen_size_start = new Point(500,400);
	final Point screen_size_cliens = new Point(500,600);
	
	final Point buttons_base_position = new Point(250,150);
	final Point buttons_size = new Point(100,50);
	
	final Point labels_base_position = new Point(50,150);
	final Point labels_size = new Point(100,20);
	
	final Point name_field_base_position = new Point(250,50);
	final Point name_field_size = new Point(150,40);
	
	
	//
	// Kliend esetén érvényes metódusok
	//
	
	// Csatlakozott játékosok listázása
	public void listNames(PlayersList list)
	{
		int array_size = list.getCollection().size() + 1;
		if(clientsList != null)
		{
			for(int i = 0; i < labels.length; i++)
			{
				background_panel.remove(labels[i]);
			}
		}
		clientsList = list.getCollection();
		
		labels= new JLabel[array_size];
		
		labels[0] = new JLabel();
		labels[0].setLayout(null);
		labels[0].setText("Connected players: ");
		labels[0].setLocation(labels_base_position.x, labels_base_position.y + labels_size.y);
		labels[0].setForeground(Color.GREEN);
		labels[0].setBackground(Color.CYAN);
		labels[0].setSize(labels_size.x + 100, labels_size.y);
		background_panel.add(labels[0]);
		
		int i = 1;
		for(Player actualPlayer : clientsList)
		{
			String temp_name = actualPlayer.getPlayerName();
			String temp_table_size = " ";;
			switch(actualPlayer.getTableSize())
			{
			case 0:
				temp_table_size = " ";
				break;
			case 1:
				temp_table_size = " : small";
				break;
			case 2:
				temp_table_size = " : medium";
				break;
			case 3:
				temp_table_size = " : large";
				break;
			}
			
			labels[i] = new JLabel();
			labels[i].setLayout(null);
			labels[i].setText(temp_name + temp_table_size);
			labels[i].setLocation(labels_base_position.x, labels_base_position.y + labels_size.y*(i+1));
			labels[i].setForeground(Color.GREEN);
			labels[i].setBackground(Color.CYAN);
			labels[i].setSize(labels_size.x, labels_size.y);
			background_panel.add(labels[i]);
			i++;
		}
		
		repaint();
		
	}
	
	// Táblaméret választó gombok hozzáadása
	void setButton(int which_size)
	{
		buttons[which_size]= new Button();
		Button temp = buttons[which_size];
		
		temp.whichSize = which_size;
		temp.choosed = false;
		
		temp.setLocation(buttons_base_position.x, buttons_base_position.y + buttons_size.y*which_size);
		temp.setBorder(BorderFactory.createLineBorder(Color.gray ));

		temp.setSize(buttons_size.x, buttons_size.y);
		
		temp.setBackground(Color.white);
		
		switch(which_size)
		{
		case 0:
			temp.setText("Small");
			break;
		case 1:
			temp.setText("Medium");
			break;
		case 2:
			temp.setText("Large");
			break;
		}
			
		final Button btn = temp;
		btn.addMouseListener(new MouseAdapter() {
						
						@Override
						public void mousePressed (MouseEvent e) {
							btn.choosed = !btn.choosed;
							try {
								clickHappend(btn.whichSize);
							} catch (IOException e1) {
								e1.printStackTrace();
							}								
							}
						});
	
		add(temp);
		temp.setVisible(false);
	}
	
	// Táblaméret kiválasztása megtörtént
	void clickHappend (int which_size) throws IOException
	{
		for(int i=0; i<3; i++)
		{
			if(buttons[which_size].choosed)
			{
				if(i!=which_size)
				{
					buttons[i].setBackground(Color.white);
					buttons[i].choosed = false;
				}
				else
				{
					buttons[i].setBackground(Color.lightGray);
					gui.chooseTableSize(which_size +1);
				}
			}
			else
			{
				buttons[i].setBackground(Color.white);
				gui.chooseTableSize(0);
			}
		}
		
		
	}
	
	//
	// Ha szerver képenyõ esetén a logkiírásért felelõs metódus
	//
	
	void printLog(String log)
	{
		
		for(int i = logLabelsNum -2 ; i >= 0 ; i--)
		{
			logLabels[i+1].setText(logLabels[i].getText()); 
		}
		logLabels[0].setText(log);
		repaint();
	}
	
	// Konstruktor
	StartScreen(GUI g) throws IOException
	{
		super("Start minesweeper");
		gui = g;
			
		setLayout(null);
		
		buttons = new Button[3];
		//gombok tulajdonságainak beállítása, hozzáadás
		
		for(int i = 0; i < 3; i++)
		{
			setButton(i);
		}
		
		// Képernyõ különbözõ elemeinek példányosítása
		logLabels = new JLabel[logLabelsNum];
		scrollPan = new JScrollPane();
		server_btn = new JButton();
		client_btn = new JButton();		
		name_field = new JTextField();
		setYourName = new JLabel();
		
		setYourName.setLayout(null);
		server_btn.setLayout(null);
		client_btn.setLayout(null);
		myname.setLayout(null);
		name_field.setLayout(null);
		
		// Logokat tartalmazó labelek létrahozása
		for(int i = 0; i < logLabelsNum; i++)
		{
			logLabels[i] = new JLabel();
			logLabels[i].setLayout(null);
			logLabels[i].setText(" ");
			logLabels[i].setVisible(true);
			logLabels[i].setLocation(50, buttons_base_position.y + buttons_size.y + 20*(logLabelsNum - i - 1));
			logLabels[i].setForeground(Color.GREEN);
			logLabels[i].setBackground(Color.CYAN);
			logLabels[i].setSize(labels_size.x*10, labels_size.y);
			add(logLabels[i]);
		}
		
		
		server_btn.setText("Server");
		client_btn.setText("Client");
		setYourName.setText("Please set your name!");
		
		background_panel = new BackgroundPanelClass("resources\\background.png");
		background_panel.setBackground(Color.BLACK);
		
		// A név megadásáért felelõs textfield feliratkoztatása a text változásának eseményére
		name_field.addActionListener(new java.awt.event.ActionListener() {
			@Override
		    public void actionPerformed(java.awt.event.ActionEvent e) {
				String s = name_field.getText();
				if (s.equals(""))
					return;
				setYourName.setVisible(false);
				myname.setText("Welcome "+ s + " !");
				name_field.setVisible(false);
				for(int i=0; i < buttons.length;i++)
					buttons[i].setVisible(true);
				setSize(screen_size_cliens.x, screen_size_cliens.y);
				repaint();
		        gui.setPlayerName(s);
		    }
		});
		
		myname.setForeground(Color.GREEN);
		Font font1 = new Font("SansSerif", Font.BOLD, 20);
		myname.setFont(font1);
		setYourName.setForeground(Color.GREEN);
		setYourName.setBackground(Color.CYAN);
		
		setLayout(null);
		
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setSize(screen_size_start.x,screen_size_start.y);
		
		server_btn.setSize(buttons_size.x,buttons_size.y);
		client_btn.setSize(buttons_size.x,buttons_size.y);
		myname.setSize(labels_size.x + 150, labels_size.y);
		setYourName.setSize(labels_size.x + 50, labels_size.y);
		name_field.setSize(name_field_size.x,name_field_size.y);
		
		client_btn.setLocation(buttons_base_position.x,	buttons_base_position.y - buttons_size.y);
		server_btn.setLocation(buttons_base_position.x + buttons_size.x, buttons_base_position.y - buttons_size.y);
		name_field.setLocation(name_field_base_position.x, name_field_base_position.y);
		setYourName.setLocation(name_field_base_position.x, name_field_base_position.y  - labels_size.y);
		myname.setLocation(name_field_base_position.x, name_field_base_position.y);
		
		
		name_field.setVisible(false);
		myname.setVisible(false);
		setYourName.setVisible(false);
		
		// Gombok feliratkoztatása a gombonyás eseményre
		final JButton btn_sv = server_btn;
		btn_sv.addMouseListener(new MouseAdapter() {
						@Override
						public void mouseClicked(MouseEvent e) {
								gui.server_click();
						}
		});
		
		final JButton btn_cl = client_btn;
		btn_cl.addMouseListener(new MouseAdapter() {
						@Override
						public void mouseClicked(MouseEvent e) {
							gui.client_click();
							setYourName.setVisible(true);
							server_btn.setVisible(false);
							client_btn.setVisible(false);
							for(int i = 0; i < logLabelsNum; i++)
							{
								logLabels[i].setVisible(false);
							}
							name_field.setVisible(true);
							myname.setVisible(true);
							
						}
		});
		
		background_panel.add(server_btn);
		background_panel.add(client_btn);	
		background_panel.add(name_field);
		background_panel.add(myname);
		background_panel.add(setYourName);
		add(background_panel);
		setVisible(true);
		repaint();
		
		setResizable(false);
	}
	
	 class Button extends JButton{
		 
		private static final long serialVersionUID = 1L;
		public int whichSize;
		public boolean choosed;
		 
	 }
	
	
	 class BackgroundPanelClass extends JPanel{
		
		private static final long serialVersionUID = 1L;
		private Image img;


		  public BackgroundPanelClass(String path) throws IOException {
			  
			img = ImageIO.read(getClass().getResourceAsStream("/resources/background.png"));

		    setSize(backgroundSize.x,backgroundSize.y);
		    setLayout(null);
		  }

		  public void paintComponent(Graphics g) {
			g.setColor(Color.black);
		    g.drawImage(img, 0, 0, null);
		  }
	}

}
