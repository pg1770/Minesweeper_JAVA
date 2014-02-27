package minesweeper;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import javax.swing.BorderFactory;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;

import minesweeper.Control;

public class GUI extends JFrame {


	int field_size = 10;
	
	int screen_size_x;
	int screen_size_y;
	
	int fields_panel_size_offset_x = 10;
	int fields_panel_size_offest_y = 10;
	
	int cell_size = 20;
	
	int cell_num_x = 20;
	int cell_num_y = 30;
	
	Field_Lines fields_panel;
	Control control;
	int [] fields;
	
	public GUI(Control c)
	{
		super("Minesweeper");
		control = c;
		fields_panel = new Field_Lines();
		fields_panel.addMouseListener(new MouseAdapter() {

            @Override
            public void mousePressed(MouseEvent e) {
                //System.out.println("X:" + e.getX() + " Y:" + e.getY());
                //ctrl.sendClick(new Point(e.getX(), e.getY()));
            	click_happend(new Point(e.getX(), e.getY()));
            }
        });
		
		
		add(fields_panel);
		setLayout(null);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setResizable(false);
		
		set_new_Size(1,2);
		setVisible(true);
	}
	
	void click_happend(Point p)
	{
	
		//determine cell
		Point cell_num = new Point();
		cell_num.x = (int)(p.x/cell_size);
		cell_num.y = (int)(p.y/cell_size);
			
		control.click_happend(cell_num);
	}
	
	public void set_new_Size(int x, int y)
	{
		cell_num_x = x;
		cell_num_y = y;
		
		screen_size_x = (cell_num_x)*cell_size + fields_panel_size_offset_x*2;
		screen_size_y = (cell_num_y)*cell_size + fields_panel_size_offest_y*2 + 30;
		
		fields_panel.setBounds(fields_panel_size_offset_x, 
				fields_panel_size_offest_y, 
				(cell_num_x)*cell_size, 
				(cell_num_y)*cell_size
				);
		fields_panel.setBorder(BorderFactory.createLineBorder(Color.black));
		setSize(screen_size_x,screen_size_y);
		repaint();
		System.out.println("ez");
	}
	
	class Field_Lines extends JPanel{
		
		private static final long serialVersionUID = 1L;
		
		@Override
	    public void paintComponent(Graphics g){
			super.paintComponent(g);
			g.setColor(Color.black);
			for(int x = 0; x < cell_num_x; x++ )
			{
				for(int y = 0; y < cell_num_y; y++ )
				{
					g.drawLine(cell_size*x, cell_size*y , cell_size*(x), cell_size*(y+1));
					g.drawLine(cell_size*x, cell_size*y , cell_size*(x+1), cell_size*(y));
					g.drawLine(cell_size*(x+1), cell_size*(y), cell_size*(x+1), cell_size*(y+1));
					g.drawLine(cell_size*(x), cell_size*(y+1), cell_size*(x+1), cell_size*(y+1));
				}
			}
			}
	}
	

	
	
}

