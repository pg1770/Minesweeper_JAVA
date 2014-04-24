package minesweeper;

import java.awt.Button;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Point;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTextField;

import java.awt.image.BufferedImage;
import java.io.*;
import java.security.acl.Owner;

import javax.imageio.ImageIO;

import javax.swing.JFrame;

public class GameScreen extends JFrame{

		private static final long serialVersionUID = 542909349322114411L;
		
		int [][] fields_array;
			
		
		int screen_size_x;
		int screen_size_y;
		
		int cell_size_1 = 30;
		int cell_size_2 = 20;
		int cell_size_3 = 15;

		int cell_size;

		GUI gui;	
		
		int cell_num_x;
		int cell_num_y;

		FieldsPanelClass fields_panel;
		BackgroundPanelClass background_panel;
		Control control;
		TimeCounter time;

		BufferedImage [] field_images = new BufferedImage[13];
		
		
		public void close()
		{
			dispose();
		}
		
		public GameScreen(GUI g, int [][] fields) throws IOException, InterruptedException
		{
			super("Minesweeper");
			gui = g;
			
			
			fields_array = fields;
			
			cell_num_y = fields_array[0].length;
			cell_num_x = fields_array.length;
			
			if(cell_num_y < 15 && cell_num_x < 15)
			{
				cell_size = cell_size_1;
			}
			else if (cell_num_y < 25 && cell_num_x < 25)
			{
				cell_size = cell_size_2;
			}
			else
			{
				cell_size = cell_size_3;
			}
			
			fields_panel = new FieldsPanelClass();
			time = new TimeCounter();
			background_panel = new BackgroundPanelClass("resources\\background.png");	
			

			screen_size_x = (cell_num_x)*cell_size + 120;//+ fields_panel_size_offset_x*2;
			screen_size_y = (cell_num_y)*cell_size + 240;//+ fields_panel_size_offest_y*2;

			fields_panel.setLayout(null);

			fields_panel.setBounds(40, 
					140, 
					(cell_num_x)*cell_size, 
					(cell_num_y)*cell_size 
					);
			fields_panel.setBorder(BorderFactory.createLineBorder(Color.black));
			setVisible(true);
			for(int i = 0; i < cell_num_x; i++)
				for(int j = 0; j < cell_num_y; j++ )
				{
					fields_panel.setField(new Point(i,j),fields_array[i][j]); 
				}
			
			time = new TimeCounter(0);

			time.setBounds(220, 
					50, 
					time.segsize.x*3, 
					time.segsize.y 
					);
			
			time.setBorder(BorderFactory.createLineBorder(Color.DARK_GRAY ));
			add(background_panel);
			background_panel.add(time);
			
			setSize(screen_size_x,screen_size_y);
			background_panel.add(fields_panel);
			

			repaint();
			System.out.println("ez");
			
			setDefaultCloseOperation(EXIT_ON_CLOSE);
		}

			
		void clickHappend(Point p, int mouse_event_num) throws IOException
		{
			gui.clickHappendOnGameScreen(p, mouse_event_num);
		}

		public void modifyFieldTable(int [][] fields) throws IOException
		{
			for(int i = 0; i < cell_num_x; i++)
				for(int j = 0; j < cell_num_y; j++ )
				{
					if(fields_panel.getButtonState(new Point(i,j)) != fields[i][j])
					{
						fields_panel.modifyField(new Point(i,j),fields[i][j]);
					}
				}
			repaint();
		}
		
		public void setTime(int sec) throws IOException
		{
			time.SetTime(sec);
			repaint();
		}
		
		
		class Button extends JButton{
			
			int state;
			
			public void setState(int st)
			{
				state = st;
			}
			
			public int getState()
			{
				return state;
			}
			
			public String pathOfState(int state)
			{
				String path;
				switch(state)
				{
				case 0:
					path = "resources\\im_empty.png";
					break;
				case 1:
				case 2:
				case 3:
				case 4:
				case 5:
				case 6:
				case 7:
				case 8:
					path = "resources\\im_"+state+".png";
					break;

				case Defines.field_unknow:
					path = "resources\\im_unknow.png";
					break;
				case Defines.field_qst_marked:
					path = "resources\\im_question.png";
					break;
				case Defines.field_exploited:
					path =  "resources\\im_exploited.png";
					break;

				case Defines.field_flagged:
				case Defines.field_marked:
					path =  "resources\\im_flagged.png";
					break;
				
				case 16:
				case 17:
				case 18:
				case 19:
				case 20:
				case 21:
				case 22:
				case 23:
				case 24:
				case 25:
				case 26:
				case 27:
				case 28:
				case 29:
				case 30:
				case 31:
			
					path =  "resources\\im_pushed.png";
					break;
					
				default:
					path =  "resources\\im_default.png";
					break;
		
				}
				return path;
			}
		}
		
		class FieldsPanelClass extends JPanel{

			Button [][] buttons;
			
			private static final long serialVersionUID = 1L;

			public FieldsPanelClass()
			{
				buttons = new Button[cell_num_x][cell_num_y];		
			}
			
			public void modifyField(Point pos, int value)
			{
				buttons[pos.x][pos.y].setState(value);
				
				/*
				JButton button = new JButton(new ImageIcon(((new ImageIcon(
			            "path-to-image").getImage()
			            .getScaledInstance(64, 64,
			            		java.awt.Image.SCALE_SMOOTH)
			            		)))
					);
				*/
				buttons[pos.x][pos.y].setIcon(new ImageIcon(((new ImageIcon(
						buttons[pos.x][pos.y].pathOfState(value)).getImage()
						.getScaledInstance(cell_size, cell_size,
								java.awt.Image.SCALE_SMOOTH)
								)))
						);
				//buttons[pos.x][pos.y].setIcon(new ImageIcon(buttons[pos.x][pos.y].pathOfState(value)));
			}
			
			public int getButtonState(Point pos)
			{
				return buttons[pos.x][pos.y].getState();
			}
					
			public void setField(Point pos, int value)
			{
				
				buttons[pos.x][pos.y] = new Button();
				buttons[pos.x][pos.y].setState(value);
				buttons[pos.x][pos.y].setIcon(new ImageIcon(buttons[pos.x][pos.y].pathOfState(value)));
	
				buttons[pos.x][pos.y].setLocation(pos.x*cell_size, pos.y*cell_size);
				buttons[pos.x][pos.y].setBorder(BorderFactory.createLineBorder(Color.orange));

				buttons[pos.x][pos.y].setSize(cell_size, cell_size);
				
				final Button btn = buttons[pos.x][pos.y];
				btn.addMouseListener(new MouseAdapter() {
								
								@Override
								public void mousePressed(MouseEvent e) {
									Point p = btn.getLocation();
									Point pos = new Point(p.x/cell_size, p.y/cell_size);
									int event = 0;
									switch(e.getButton()){
									case 1:
										event = Defines.mouse_left_Pressed;
										break;
									case 2:
										event = Defines.mouse_middle_Pressed;
										break;
									case 3:
										event = Defines.mouse_right_Pressed;
										break;
									}
									
									try {
										clickHappend(pos, event);
									} catch (IOException e1) {
										// TODO Auto-generated catch block
										e1.printStackTrace();
									}						
									//System.out.println("The left mouse button was pressed." + pos.x + " " + pos.y);
								}
								@Override			
								public void mouseReleased(MouseEvent e) {
									Point p = btn.getLocation();
									Point pos = new Point((p.x)/cell_size, (p.y)/cell_size);
									int event = 0;
									switch(e.getButton()){
									case 1:
										event = Defines.mouse_left_Released;
										break;
									case 2:
										event = Defines.mouse_middle_Released;
										break;
									case 3:
										event = Defines.mouse_right_Released;
										break;
									}
									
									try {
										clickHappend(pos, event);
									} catch (IOException e1) {
										// TODO Auto-generated catch block
										e1.printStackTrace();
									}	
									//System.out.println("The left mouse button was released." + pos.x + " " + pos.y + " button " + e.getButton());
									
								}
								});
			
				add(buttons[pos.x][pos.y]);
				buttons[pos.x][pos.y].setVisible(true);
				
			}
		}
		

		class TimeCounter extends JPanel{
			
			public Point position;
			
			Image segment1, segment2, segment3;

			Point segsize = new Point(38,54);
			
			public TimeCounter() throws IOException {
				SetTime(0);
			}
			
			public TimeCounter(int sec) throws IOException {
			    
				SetTime(sec);
			}
			
			public void SetTime(int second) throws IOException
			{
				int seg1, seg2, seg3;
				if(second > 999)
					second = 999;
				seg1 = second/100;
				seg2 = (second%100)/10;
				seg3 = (second%10);
				
				setSize(segsize.x*3,segsize.y);
			    setLayout(null);
				
				segment1 = ImageIO.read(new File("resources\\segment_"+seg1+".png"));
				segment2 = ImageIO.read(new File("resources\\segment_"+seg2+".png"));
				segment3 = ImageIO.read(new File("resources\\segment_"+seg3+".png"));	
			}
			
			@Override
			public void paintComponent(Graphics g) {
				g.drawImage(segment1, segsize.x*0, 0, null);
				g.drawImage(segment2, segsize.x*1, 0, null);
				g.drawImage(segment3, segsize.x*2, 0, null);
			}
			
		}
		
		class BackgroundPanelClass extends JPanel{
			
			  private Image img;


			  public BackgroundPanelClass(String path) throws IOException {
				img = ImageIO.read(new File(path));
			    setSize(screen_size_x,screen_size_y);
			    setLayout(null);
			  }
			  
			  @Override
			  public void paintComponent(Graphics g) {
			    g.drawImage(img, 0, 0, null);
			  }
		}
}
