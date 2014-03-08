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

public class Game_screen extends JFrame{
		/**
		 * 
		 */
		private static final long serialVersionUID = 542909349322114411L;
		
		int [][] fields_array;
		
		int screen_size_x;
		int screen_size_y;

		int fields_panel_size_offset_x = 0;
		int fields_panel_size_offest_y = 0;

		int cell_size = 40;

		GUI gui;	
		
		int cell_num_x;
		int cell_num_y;

		Fields_panel_c fields_panel;
		Control control;

		BufferedImage [] field_images = new BufferedImage[13];
		
		public void close()
		{
			dispose();
		}
		
		public Game_screen(GUI g) throws IOException
		{
			super("Minesweeper");
			gui = g;
			fields_panel = new Fields_panel_c();

			setDefaultCloseOperation(EXIT_ON_CLOSE);
		}


			

		
		void click_happend(Point p, int mouse_event_num) throws IOException
		{
			control.click_happend(p, mouse_event_num);

			;
		}


		public void new_field_table(int [][] fields) throws IOException
		{
			fields_array = fields;
			cell_num_y = fields_array[0].length;
			cell_num_x = fields_array.length;
				

			screen_size_x = (cell_num_x)*cell_size +30;//+ fields_panel_size_offset_x*2;
			screen_size_y = (cell_num_y)*cell_size + 100;//+ fields_panel_size_offest_y*2;
			remove(fields_panel);
			
			fields_panel = new Fields_panel_c();
			fields_panel.setLayout(null);
			
			fields_panel.setBounds(fields_panel_size_offset_x, 
					fields_panel_size_offest_y, 
					(cell_num_x)*cell_size , 
					(cell_num_y)*cell_size 
					);
			fields_panel.setBorder(BorderFactory.createLineBorder(Color.black));
			setVisible(true);
			for(int i = 0; i < cell_num_x; i++)
				for(int j = 0; j < cell_num_y; j++ )
				{
					//String path = "resources\\im.png";
					set_field(new Point(i,j),fields_array[i][j]); 
				}
			
			setSize(screen_size_x,screen_size_y);
			add(fields_panel);

			repaint();
			System.out.println("ez");
		}

		void set_field(Point pos, int value) throws IOException
		{
			fields_panel.set_field(new Point(pos.x,pos.y),value);	
		}
		

		
		class Fields_panel_c extends JPanel{

			//one_Field [][] fields;
			JButton [][] buttons;
			
			private static final long serialVersionUID = 1L;

			public Fields_panel_c()
			{
				buttons = new JButton[cell_num_x][cell_num_y];		
			}
			
			public void set_field(Point pos, int value)
			{
				
				buttons[pos.x][pos.y] = new JButton();
				buttons[pos.x][pos.y].setText(""+value);
				//buttons[pos.x][pos.y].setIcon(new ImageIcon("resources\\im2.jpg"));
				
				buttons[pos.x][pos.y].setLocation(pos.x*cell_size, pos.y*cell_size);
				buttons[pos.x][pos.y].setBorder(BorderFactory.createLineBorder(Color.orange));
				//buttons[pos.x][pos.y].setBackground(Color.orange);
				buttons[pos.x][pos.y].setSize(cell_size, cell_size);
				
				final JButton btn = buttons[pos.x][pos.y];
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
										click_happend(p, event);
									} catch (IOException e1) {
										// TODO Auto-generated catch block
										e1.printStackTrace();
									}						
									//System.out.println("The left mouse button was pressed." + pos.x + " " + pos.y);
								}
								@Override			
								public void mouseReleased(MouseEvent e) {
									Point p = btn.getLocation();
									Point pos = new Point(p.x/cell_size, p.y/cell_size);
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
										click_happend(p, event);
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

}
