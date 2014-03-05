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
import java.awt.image.BufferedImage;
import java.io.*;
import java.security.acl.Owner;

import javax.imageio.ImageIO;
import minesweeper.Control;

public class GUI extends JFrame {


	/**
	 * 
	 */
	private static final long serialVersionUID = 542909349322114411L;
	
	int screen_size_x;
	int screen_size_y;

	int fields_panel_size_offset_x = 0;
	int fields_panel_size_offest_y = 0;

	int cell_size = 40;

	int cell_num_x = 20;
	int cell_num_y = 30;

	Fields_panel_c fields_panel;
	Control control;

	BufferedImage [] field_images = new BufferedImage[13];
	//one_Field ff;
	
	public GUI(Control c) throws IOException
	{
		super("Minesweeper");
		control = c;
		fields_panel = new Fields_panel_c();
		/*add(fields_panel);
		
		fields_panel.addMouseListener(new MouseAdapter() {

            @Override
            public void mousePressed(MouseEvent e) {
            	try {
					click_happend(new Point(e.getX(), e.getY()));
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
            }
        });

		setLayout(null);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		*/
		set_new_Size(2,2);
		//repaint();
	}

	void click_happend(Point p) throws IOException
	{
		
		//determine cell
		Point cell_num = new Point();
		cell_num.x = (int)(p.x/cell_size);
		cell_num.y = (int)(p.y/cell_size);
		String path = "resources\\im2.png";
		
		//control.click_happend(cell_num);
		repaint();
		set_field(cell_num,path);
	}

	public void set_new_Size(int x, int y) throws IOException
	{
		cell_num_x = x;
		cell_num_y = y;

		screen_size_x = (cell_num_x)*cell_size + fields_panel_size_offset_x*2 +400;
		screen_size_y = (cell_num_y)*cell_size + fields_panel_size_offest_y*2 + 30 +400;
		remove(fields_panel);
		fields_panel = new Fields_panel_c();
		
		fields_panel.addMouseListener(new MouseAdapter() {

            @Override
            public void mousePressed(MouseEvent e) {
            	try {
					click_happend(new Point(e.getX(), e.getY()));
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
            }
        });
		
		fields_panel.setBounds(fields_panel_size_offset_x, 
				fields_panel_size_offest_y, 
				(cell_num_x)*cell_size +400, 
				(cell_num_y)*cell_size +400
				);
		fields_panel.setBorder(BorderFactory.createLineBorder(Color.black));
		setVisible(true);
		for(int i = 0; i < cell_num_x; i++)
			for(int j = 0; j < cell_num_y; j++ )
			{
				String path = "resources\\im.png";
				set_field(new Point(i,j),path);
			}
		
		setSize(screen_size_x,screen_size_y);
		add(fields_panel);

		repaint();
		System.out.println("ez");
	}

	public void set_field(Point pos, String image_path) throws IOException
	{
		fields_panel.set_field(new Point(pos.x,pos.y),ImageIO.read(new File(image_path)));	
	}
	
	class Fields_panel_c extends JPanel{

		//one_Field [][] fields;
		JButton [][] buttons;
		
		private static final long serialVersionUID = 1L;

		public Fields_panel_c()
		{
			buttons = new JButton[cell_num_x][cell_num_y];		
		}
		
		public void set_field(Point pos, BufferedImage im)
		{
			String s = pos.x + " " + pos.y;
			
			buttons[pos.x][pos.y] = new JButton(s);
			
			buttons[pos.x][pos.y].setIcon(new ImageIcon("resources\\im2.jpg"));
			
			buttons[pos.x][pos.y].setLocation(pos.x*cell_size, pos.y*cell_size);
			buttons[pos.x][pos.y].setBorder(BorderFactory.createLineBorder(Color.orange));
			//buttons[pos.x][pos.y].setBackground(Color.orange);
			buttons[pos.x][pos.y].setSize(cell_size, cell_size);
			
			final JButton btn = buttons[pos.x][pos.y];
			btn.addMouseListener(new MouseAdapter() {
							@Override
							public void mouseClicked(MouseEvent e) {
								Point p = btn.getLocation();
								Point pos = new Point(p.x/cell_size, p.y/cell_size);
								System.out.println("The frame was clicked." + pos.x + " " + pos.y);
							}
												
							});
		
			add(buttons[pos.x][pos.y]);
			buttons[pos.x][pos.y].setVisible(true);
			
		}
	}

}