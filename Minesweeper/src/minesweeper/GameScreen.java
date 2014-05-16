package minesweeper;

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
import javax.swing.JOptionPane;
import javax.swing.JPanel;

import java.io.*;
import javax.imageio.ImageIO;

public class GameScreen extends JFrame{

		private static final long serialVersionUID = 542909349322114411L;
		
		// A mezõk értékait tartalmazó tömb
		int [][] fields_array;
		
		// Különbözõ méretekhez tartozó értékek
		Point ScreenSize = new Point();
		Point CellNum = new Point();
				
		final int cell_size_1 = 30;
		final int cell_size_2 = 20;
		final int cell_size_3 = 15;

		int cell_size;

		GUI gui;	
		
		// Példányosítandó osztályok
		FieldsPanelClass fields_panel;
		BackgroundPanelClass background_panel;
		Control control;
		TimeCounter time;
	
		// Konstruktor
		public GameScreen(GUI g, int [][] fields)
		{
			super("Minesweeper");
			gui = g;
					
			fields_array = fields;
			
			// Cellamátrix méretei
			CellNum.y = fields_array[0].length;
			CellNum.x = fields_array.length;
			
			// A szükséges mezõméret megállapítása
			if(CellNum.y < 15 && CellNum.x < 15)
			{
				cell_size = cell_size_1;
			}
			else if (CellNum.y < 25 && CellNum.x < 25)
			{
				cell_size = cell_size_2;
			}
			else
			{
				cell_size = cell_size_3;
			}
			
			// A mezõket tartalmazó panel példányosítása
			fields_panel = new FieldsPanelClass();
			
			// A számláló panel példányosítása
			try {
				time = new TimeCounter(0);
			} catch (IOException e) {
				JOptionPane.showMessageDialog(null, "ERROR during create new TimeCounter");
				e.printStackTrace();
			}
			try {
				background_panel = new BackgroundPanelClass("resources\\background.png");
			} catch (IOException e) {
				JOptionPane.showMessageDialog(null, "ERROR during create new BackgroundPanelClass");
				e.printStackTrace();
			}	
			
			// A képernyõ méretének beállítása
			ScreenSize.x = (CellNum.x)*cell_size + 120;
			ScreenSize.y = (CellNum.y)*cell_size + 240;

			// Mezõ panel beállítása
			fields_panel.setLayout(null);
			fields_panel.setBounds(40, 
					140, 
					(CellNum.x)*cell_size, 
					(CellNum.y)*cell_size 
					);
			fields_panel.setBorder(BorderFactory.createLineBorder(Color.black));
			
			// A panel feltöltése a mezõkkel
			for(int i = 0; i < CellNum.x; i++)
				for(int j = 0; j < CellNum.y; j++ )
				{
					fields_panel.setField(new Point(i,j),fields_array[i][j]); 
				}
			
			time.setBounds(220, 
					50, 
					time.segsize.x*3, 
					time.segsize.y 
					);
			
			time.setBorder(BorderFactory.createLineBorder(Color.DARK_GRAY ));
			add(background_panel);
			background_panel.add(time);
			
			setSize(ScreenSize.x,ScreenSize.y);
			background_panel.add(fields_panel);
			setVisible(true);
			repaint();
			setResizable(false);
			setDefaultCloseOperation(EXIT_ON_CLOSE);
		}

		// Kattintás eseményrõl értesítjük a GUI-t
		void clickHappened(Point p, int mouse_event_num) throws IOException
		{
			gui.clickHappenedOnGameScreen(p, mouse_event_num);
		}

		// A mezõtáblánk újrarajzolása
		public void modifyFieldTable(int [][] fields) throws IOException
		{
			for(int i = 0; i < CellNum.x; i++)
				for(int j = 0; j < CellNum.y; j++ )
				{
					if(fields_panel.getButtonState(new Point(i,j)) != fields[i][j])
					{
						fields_panel.modifyField(new Point(i,j),fields[i][j]);
					}
				}
			repaint();
		}
		
		// Új idõérték érkezett
		public void setTime(int sec) throws IOException
		{
			time.SetTime(sec);
			repaint();
		}
		
		// A JButton osztály kiterjesztése		class Button extends JButton{
			
			private static final long serialVersionUID = 1L;
			// Szükséges tárolnunk a mezõ jelenlegi állapotát, azaz hogy milyen számot/aknát takar
			int state;
			
			public void setState(int st)
			{
				state = st;
			}
			
			public int getState()
			{
				return state;
			}
			
			// A mezõ állapotától függõen különbüzõ képeket kell betöltenünk. Ezen képek elérési útját
			// adjuk vissza ebben a metódusban
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
		
		// A mezõket tartalmazó panel
		class FieldsPanelClass extends JPanel{

			// A mezõket, jelen esetben gombokat tartalmazõ mátrixunk
			Button [][] buttons;
			
			private static final long serialVersionUID = 1L;

			public FieldsPanelClass()
			{
				buttons = new Button[CellNum.x][CellNum.y];		
			}
			
			// Egy mezõ módosítása, a megfelelõ kép beállítása
			public void modifyField(Point pos, int value)
			{
				buttons[pos.x][pos.y].setState(value);
				
				buttons[pos.x][pos.y].setIcon(new ImageIcon(((new ImageIcon(
						buttons[pos.x][pos.y].pathOfState(value)).getImage()
						.getScaledInstance(cell_size, cell_size,
								java.awt.Image.SCALE_SMOOTH)
								)))
						);
			}
			
			// Egy mezõ állapotának visszaadása
			public int getButtonState(Point pos)
			{
				return buttons[pos.x][pos.y].getState();
			}
					
			// Még nem lézetnek a mezõink, itt péládnyosítjuk õket
			public void setField(Point pos, int value)
			{
				
				buttons[pos.x][pos.y] = new Button();
				buttons[pos.x][pos.y].setState(value);
				buttons[pos.x][pos.y].setIcon(new ImageIcon(buttons[pos.x][pos.y].pathOfState(value)));
	
				buttons[pos.x][pos.y].setLocation(pos.x*cell_size, pos.y*cell_size);
				buttons[pos.x][pos.y].setBorder(BorderFactory.createLineBorder(Color.orange));

				buttons[pos.x][pos.y].setSize(cell_size, cell_size);
				
				// Minden mezõt feliratkoztatunk az egér klikkelésre
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
										clickHappened(pos, event);
									} catch (IOException e1) {
										JOptionPane.showMessageDialog(null, "ERROR during clickHappened call");
										e1.printStackTrace();
									}						
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
										clickHappened(pos, event);
									} catch (IOException e1) {
										JOptionPane.showMessageDialog(null, "ERROR during clickHappened call");
										e1.printStackTrace();
									}	
									
								}
								});
			
				add(buttons[pos.x][pos.y]);
				buttons[pos.x][pos.y].setVisible(true);
				
			}
		}
		
		// Az idõszámlálót magábanfoglaló panel
		class TimeCounter extends JPanel{
			
			private static final long serialVersionUID = 1L;

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
		
		// a Háttérkép panelja
		class BackgroundPanelClass extends JPanel{
			
			private static final long serialVersionUID = 1L;
			private Image img;


			  public BackgroundPanelClass(String path) throws IOException {
				img = ImageIO.read(new File(path));
			    setSize(ScreenSize.x,ScreenSize.y);
			    setLayout(null);
			  }
			  
			  @Override
			  public void paintComponent(Graphics g) {
			    g.drawImage(img, 0, 0, null);
			  }
		}
}
