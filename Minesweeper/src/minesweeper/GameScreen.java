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

		int fields_panel_size_offset_x = 0;
		int fields_panel_size_offest_y = 0;

		int cell_size = 30;

		GUI gui;	
		
		int cell_num_x;
		int cell_num_y;

		FieldsPanelClass fields_panel;
		BackgroundPanelClass background_panel;
		Control control;

		BufferedImage [] field_images = new BufferedImage[13];
		
		
		public void close()
		{
			dispose();
		}
		
		public GameScreen(GUI g) throws IOException
		{
			super("Minesweeper");
			gui = g;
			fields_panel = new FieldsPanelClass();

			setDefaultCloseOperation(EXIT_ON_CLOSE);
		}

		
		String pathOfState(int state)
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

			case 10:
				path = "resources\\im_unknow.png";
				break;
			case 13:
				path = "resources\\im_question.png";
				break;
			case 14:
				path =  "resources\\im_exploited.png";
				break;

			case 12:
			case 15:
				path =  "resources\\im_flagged.png";
				break;
				
			default:
				path =  "resources\\im_default.png";
				break;
	
			}
			return path;
		}
			

		
		void clickHappend(Point p, int mouse_event_num) throws IOException
		{
			gui.clickHappend(p, mouse_event_num);
		}


		public void newFieldTable(int [][] fields) throws IOException
		{
			fields_array = fields;
	
			cell_num_y = fields_array[0].length;
			cell_num_x = fields_array.length;
			

			screen_size_x = (cell_num_x)*cell_size + 320;//+ fields_panel_size_offset_x*2;
			screen_size_y = (cell_num_y)*cell_size + 240;//+ fields_panel_size_offest_y*2;
			remove(fields_panel);
			
			fields_panel = new FieldsPanelClass();
			fields_panel.setLayout(null);
			
			background_panel = new BackgroundPanelClass();
			//background_panel.setLayout(null);
			
			
			
			
			
			fields_panel.setBounds(fields_panel_size_offset_x + 220, 
					fields_panel_size_offest_y + 140, 
					(cell_num_x)*cell_size , 
					(cell_num_y)*cell_size 
					);
			fields_panel.setBorder(BorderFactory.createLineBorder(Color.black));
			setVisible(true);
			for(int i = 0; i < cell_num_x; i++)
				for(int j = 0; j < cell_num_y; j++ )
				{
					setField(new Point(i,j),fields_array[i][j]); 
				}
			
			setSize(screen_size_x,screen_size_y);
			add(fields_panel);
			add(background_panel);

			repaint();
			System.out.println("ez");
		}
		
		public void modifyFieldTable(int [][] fields) throws IOException
		{
			for(int i = 0; i < cell_num_x; i++)
				for(int j = 0; j < cell_num_y; j++ )
				{
					if(fields_panel.buttons[i][j].getState() != fields[i][j])
					{
						fields_panel.buttons[i][j].setState(fields[i][j]);
						fields_panel.buttons[i][j].setIcon(new ImageIcon(pathOfState(fields[i][j])));
						;
					}
				}
			repaint();
		}

		void setField(Point pos, int value) throws IOException
		{
			fields_panel.setField(new Point(pos.x,pos.y),value);	
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
			
		}
		
		class FieldsPanelClass extends JPanel{

			Button [][] buttons;
			
			private static final long serialVersionUID = 1L;

			public FieldsPanelClass()
			{
				buttons = new Button[cell_num_x][cell_num_y];		
			}
			
			public void setField(Point pos, int value)
			{
				
				buttons[pos.x][pos.y] = new Button();
				buttons[pos.x][pos.y].setState(value);
				buttons[pos.x][pos.y].setIcon(new ImageIcon(pathOfState(value)));
	
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

		class BackgroundPanelClass extends JPanel{
			
			  private Image img;


			  public BackgroundPanelClass() throws IOException {
				img = ImageIO.read(new File("resources\\background.png"));
			    setSize(screen_size_x,screen_size_y);
			    setLayout(null);
			  }

			  public void paintComponent(Graphics g) {
			    g.drawImage(img, 0, 0, null);
			  }
		}
}
