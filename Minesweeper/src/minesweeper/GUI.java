package minesweeper;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Point;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.BorderFactory;
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

	int cell_size = 30;

	int cell_num_x = 20;
	int cell_num_y = 30;

	Fields_panel_c fields_panel;
	Control control;

	BufferedImage [] field_images = new BufferedImage[13];
	one_Field ff;
	
	public GUI(Control c) throws IOException
	{
		super("Minesweeper");
		control = c;
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

	
		add(fields_panel);
		setLayout(null);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		//setResizable(false);
		set_new_Size(10,20);
		setVisible(true);
		repaint();
	}

	void click_happend(Point p) throws IOException
	{

		//determine cell
		Point cell_num = new Point();
		cell_num.x = (int)(p.x/cell_size);
		cell_num.y = (int)(p.y/cell_size);
		String path = "im2.jpg";
		set_field(cell_num,path);
		control.click_happend(cell_num);
		repaint();
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
		
		for(int i = 0; i < cell_num_x; i++)
			for(int j = 0; j < cell_num_y; j++ )
			{
				String path = "resources\\im.jpg";
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

		one_Field [][] fields;
		
		private static final long serialVersionUID = 1L;

		public Fields_panel_c()
		{
			fields = new one_Field[cell_num_x][cell_num_y];
		}
		
		public void set_field(Point pos, BufferedImage im)
		{
			fields[pos.x][pos.y] = new one_Field(im, pos);	
		}
		
		
		@Override
	    public void paintComponent(Graphics g){
			super.paintComponent(g);
			g.setColor(Color.black);
			for(int x = 0; x < cell_num_x; x++ )
				for(int y = 0; y < cell_num_y; y++ )
					g.drawImage(fields[x][y].own_image, fields[x][y].position.x*cell_size, fields[x][y].position.y*cell_size, this);
		}
			
	}

	class one_Field{
		
		BufferedImage own_image;
		Point position = new Point();
		
		public one_Field(BufferedImage im, Point pos)
		{
			position.x = pos.x;
			position.y = pos.y;
			own_image = im;
		}
		
		public void set_image(BufferedImage im)
		{
			own_image = im;
		}
		
		public void set_position(Point p)
		{
			position.x = p.x;
			position.y = p.y;
		}
	}


}