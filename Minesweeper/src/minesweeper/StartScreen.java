package minesweeper;

import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;
import java.io.IOException;
import java.util.Vector;

import javax.imageio.ImageIO;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTextField;


import minesweeper.GameScreen.Button;

public class StartScreen extends JFrame{

	GUI gui;
	BackgroundPanelClass background_panel;
	/*
	JButton server_btn;
	JButton client_btn;
	*/
	Button [][] buttons;
	
	JTextField name_field;
	
	final int screen_size_x = 250;
	final int screen_size_y = 250;
	
	int sr_btn_size_x = 80;
	int sr_btn_size_y = 40;
	int cl_btn_size_x = 80;
	int cl_btn_size_y = 40;
	int nmf_size_x = 150;
	int nmf_size_y = 40;
	
	
	void close()
	{
		dispose();
	}
	
	public void listNames(clientsList list)
	{
		
	}
	
	StartScreen(GUI g) throws IOException
	{
		super("Start minesweeper");
		gui = g;
			
		setLayout(null);
		/*
		server_btn = new JButton();
		client_btn = new JButton();
		*/
		name_field = new JTextField();
		
		/*
		server_btn.setLayout(null);
		client_btn.setLayout(null);
		*/
		name_field.setLayout(null);
		
		/*
		server_btn.setText("Server");
		client_btn.setText("Client");
		*/
		
		background_panel = new BackgroundPanelClass("resources\\background.png");
		
		name_field.addActionListener(new java.awt.event.ActionListener() {
			@Override
		    public void actionPerformed(java.awt.event.ActionEvent e) {
				String s = name_field.getText();
				gui.setPlayerName(s);
		               
		    }
		});
		
		
		setLayout(null);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setSize(screen_size_x,screen_size_y);
		
		/*
		server_btn.setSize(sr_btn_size_x,sr_btn_size_y);
		client_btn.setSize(cl_btn_size_x,cl_btn_size_y);
		*/
		name_field.setSize(nmf_size_x,nmf_size_y);
		
		/*
		client_btn.setLocation(screen_size_x/3 - cl_btn_size_x/2,
				screen_size_y/3*2 - cl_btn_size_y/2);
		//server_btn.setLocation(100,100);
		server_btn.setLocation(screen_size_x/3*2 - sr_btn_size_x/2,
				screen_size_y/3*2 - sr_btn_size_y/2);
		*/
		name_field.setLocation(screen_size_x/2 - nmf_size_x/2,
				screen_size_y/3 - nmf_size_y/2);
		/*
		final JButton btn_sv = server_btn;
		btn_sv.addMouseListener(new MouseAdapter() {
						@Override
						public void mouseClicked(MouseEvent e) {
							System.out.println("server_btn clicked");
							gui.Start_server_screen();
							
						}
		});
		
		final JButton btn_cl = client_btn;
		btn_cl.addMouseListener(new MouseAdapter() {
						@Override
						public void mouseClicked(MouseEvent e) {
							System.out.println("client_btn clicked");
						}
		});
		
		add(server_btn);
		add(client_btn);
		*/
		add(name_field);
		add(background_panel);
		setVisible(true);
		repaint();
		
		
	}
	
	 class BackgroundPanelClass extends JPanel{
		
		  private Image img;


		  public BackgroundPanelClass(String path) throws IOException {
			img = ImageIO.read(new File(path));
		    setSize(screen_size_x,screen_size_y);
		    setLayout(null);
		  }

		  public void paintComponent(Graphics g) {
		    g.drawImage(img, 0, 0, null);
		  }
	}

}
