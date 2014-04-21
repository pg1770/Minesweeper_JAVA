package minesweeper;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Point;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Vector;
import java.util.Collection;

import javax.imageio.ImageIO;
import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

import minesweeper.clientsList;
import minesweeper.clients;
import minesweeper.GameScreen.Button;
import java.util.Collections;

import javax.swing.JButton;

public class StartScreen extends JFrame{

	GUI gui;
	BackgroundPanelClass background_panel;
	
	JButton server_btn;
	JButton client_btn;
	
	Button [] buttons;	
	Collection<Player> clientsList;
	
	JTextField name_field;
	
	final int screen_size_x = 800;
	final int screen_size_y = 600;
	
	JLabel labels[];
	JLabel myname = new JLabel();
	
	final Point screen_size_start = new Point(500,300);
	final Point screen_size_cliens = new Point(500,600);
	
	final Point buttons_base_position = new Point(250,150);
	final Point buttons_size = new Point(100,50);
	
	final Point labels_base_position = new Point(50,150);
	final Point labels_size = new Point(100,20);
	
	final Point name_field_base_position = new Point(250,50);
	final Point name_field_size = new Point(150,40);
	
	
	void close()
	{
		dispose();
	}
	
	public void listNames(PlayersList list)
	{
		int array_size = list.getCollection().size();
		if(clientsList != null)
		{
			for(int i = 0; i < clientsList.size() ;i++)
			{
				background_panel.remove(labels[i]);
			}
		}
		clientsList = list.getCollection();
		
		//names = new String[array_size];
		//TODO szebben, listtel, ne tömbbel
		labels= new JLabel[array_size];
		
		//for(int i = 0; i < array_size;i++)
		//{
		int i = 0;
		for(Player actualPlayer : clientsList)
		{
			String temp_name = actualPlayer.getPlayerName();
			labels[i] = new JLabel();
			labels[i].setLayout(null);
			labels[i].setText(temp_name);
			labels[i].setLocation(labels_base_position.x, labels_base_position.y + labels_size.y*i);
			labels[i].setForeground(Color.GREEN);
			labels[i].setBackground(Color.CYAN);
			labels[i].setSize(labels_size.x, labels_size.y);
			background_panel.add(labels[i]);
			//labels[i].setVisible(true);
			i++;
		}
		
		repaint();
		
	}
	
	public void setButton(int which_size)
	{
		buttons[which_size]= new Button();
		Button temp = buttons[which_size];
		
		temp.whichSize = which_size;
		temp.choosed = false;
		
		///buttons[pos.x][pos.y].setIcon(new ImageIcon(buttons[pos.x][pos.y].pathOfState(value)));

		temp.setLocation(buttons_base_position.x, buttons_base_position.y + buttons_size.y*which_size);
		temp.setBorder(BorderFactory.createLineBorder(Color.gray ));

		temp.setSize(buttons_size.x, buttons_size.y);
		
		temp.setBackground(Color.white);
		//temp.clientNumber = id;
		
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
								// TODO Auto-generated catch block
								e1.printStackTrace();
							}								}
						});
	
		add(temp);
		temp.setVisible(false);
	}
	
	
	void clickHappend (int which_size) throws IOException
	{
		int selected_size;
		for(int i=0; i<3; i++)
		{
			if(buttons[which_size].choosed)
			{
				selected_size = which_size;
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
	

	
	StartScreen(GUI g) throws IOException
	{
		super("Start minesweeper");
		gui = g;
			
		setLayout(null);
		
		buttons = new Button[3];
		//gombok tulajdonságainak beállítása, hozzáadás
		
		for(int i=0; i<3; i++)
		{
				setButton(i);
		}
		
		server_btn = new JButton();
		client_btn = new JButton();
		
		name_field = new JTextField();
		
		
		server_btn.setLayout(null);
		client_btn.setLayout(null);
		myname.setLayout(null);
		name_field.setLayout(null);
		
		
		server_btn.setText("Server");
		client_btn.setText("Client");
		myname.setText("Your name:");
		
		
		background_panel = new BackgroundPanelClass("resources\\background.png");
		background_panel.setBackground(Color.BLACK);
		
		name_field.addActionListener(new java.awt.event.ActionListener() {
			@Override
		    public void actionPerformed(java.awt.event.ActionEvent e) {
				String s = name_field.getText();
				myname.setText(s);
				name_field.setVisible(false);
				gui.setPlayerName(s);
				for(int i=0; i < buttons.length;i++)
					buttons[i].setVisible(true);
				setSize(screen_size_cliens.x, screen_size_cliens.y);
				repaint();
		        gui.setPlayerName(s);
		    }
		});
		
		myname.setForeground(Color.GREEN);
		
		setLayout(null);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setSize(screen_size_start.x,screen_size_start.y);
		
		server_btn.setSize(buttons_size.x,buttons_size.y);
		client_btn.setSize(buttons_size.x,buttons_size.y);
		myname.setSize(labels_size.x, labels_size.y + 50);
		name_field.setSize(name_field_size.x,name_field_size.y);
		
		client_btn.setLocation(buttons_base_position.x,
				buttons_base_position.y - buttons_size.y);
		server_btn.setLocation(buttons_base_position.x + buttons_size.x,
				buttons_base_position.y - buttons_size.y);
		name_field.setLocation(name_field_base_position.x,
				name_field_base_position.y);
		myname.setLocation(name_field_base_position.x, name_field_base_position.y - 50);
		
		name_field.setVisible(false);
		myname.setVisible(false);
		
		final JButton btn_sv = server_btn;
		btn_sv.addMouseListener(new MouseAdapter() {
						@Override
						public void mouseClicked(MouseEvent e) {
							System.out.println("server_btn clicked");

								gui.server_click();

							
						}
		});
		
		final JButton btn_cl = client_btn;
		btn_cl.addMouseListener(new MouseAdapter() {
						@Override
						public void mouseClicked(MouseEvent e) {
							System.out.println("client_btn clicked");

								gui.client_click();

							server_btn.setVisible(false);
							client_btn.setVisible(false);
							name_field.setVisible(true);
							myname.setVisible(true);
							
						}
		});
		
		background_panel.add(server_btn);
		background_panel.add(client_btn);	
		background_panel.add(name_field);
		background_panel.add(myname);
		add(background_panel);
		setVisible(true);
		repaint();
		
		
	}
	
	 class Button extends JButton{
		 
		 public int whichSize;
		 public boolean choosed;
		 
	 }
	
	
	 class BackgroundPanelClass extends JPanel{
		
		  private Image img;


		  public BackgroundPanelClass(String path) throws IOException {
			img = ImageIO.read(new File(path));
		    setSize(screen_size_x,screen_size_y);
		    setLayout(null);
		  }

		  public void paintComponent(Graphics g) {
			g.setColor(Color.black);
		    g.drawImage(img, 0, 0, null);
		  }
	}

}
