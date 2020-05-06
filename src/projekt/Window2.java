package projekt;



import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.FlowLayout;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.net.URL;
import java.util.Locale;
import java.util.ResourceBundle;

import javax.imageio.ImageIO;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
 

public class Window2 extends JFrame implements ActionListener 
{

private static final long serialVersionUID = 1L;
//private JPanel contentPane;

String ang="en";
String country="US";
Locale l1 = new Locale(ang,country);
ResourceBundle r1 = ResourceBundle.getBundle("projekt/language_en_ENG",l1);

JPanel pUpper, pDown, pLeft, pRight, pRight2, drawPanel;
JLabel label;
JButton PL, ENG, RUS, GE;

BufferedImage image = null;
	public static void main(String[] args) {
		
		EventQueue.invokeLater(new Runnable(){
			public void run(){
				try {
					Window2 frame = new Window2();
					frame.setVisible(true);					
				}
				catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}
 
	
	public Window2() {
		super(); 
		
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		this.setSize(900,600);
	    this.setLayout(new BorderLayout());
	    
	    pUpper = new JPanel();
	    
	    ENG = new JButton ("ENG");
	    ENG.setActionCommand("english");
	    //ENG.setBackground(Color.red);
		ENG.addActionListener(this);
		
		PL = new JButton ("PL");
	    PL.setActionCommand("polish");
	    //PL.setBackground(Color.blue);
		PL.addActionListener(this);
		
		RUS = new JButton ("RUS");
	    RUS.setActionCommand("rus");
	    //RUS.setBackground(Color.green);
		RUS.addActionListener(this);
		
		GE = new JButton ("GE");
	    GE.setActionCommand("german");
	    //GE.setBackground(Color.yellow);
		GE.addActionListener(this);
		
		pUpper.add(ENG);
		pUpper.add(PL);
		pUpper.add(RUS);
		pUpper.add(GE);
		pUpper.setLayout(new FlowLayout(1,5,5));
		
		this.add(pUpper, BorderLayout.PAGE_START);
	    
	    drawPanel= new JPanel();
	    drawPanel.setBackground(Color.white);
	    this.add(drawPanel, BorderLayout.CENTER);
	    
		URL resource = getClass().getResource("alleluja.PNG");
		try 
		{
			image = ImageIO.read(resource);
			Graphics g2d = drawPanel.getGraphics();
			g2d.drawImage(image, 0,0, drawPanel);
		} 
		catch (IOException en) 
		{
			System.err.println("Blad odczytu obrazka");
			en.printStackTrace();
		}
            	 
		Dimension dimension = new Dimension(image.getWidth(), image.getHeight());
		setPreferredSize(dimension);	
		 
	}
	public void actionPerformed(ActionEvent e){
		if (e.getActionCommand() == "english"){
			
		}
		if (e.getActionCommand() == "polish"){
			
		}
		if (e.getActionCommand() == "rus"){
			
		}
		if (e.getActionCommand() == "german"){
			
		}
	
	}
	

	public void paintComponent(Graphics g)
	{
		Graphics2D g2d = (Graphics2D) g;
		g2d.drawImage(image, 0, 0, this);
	}
	
}