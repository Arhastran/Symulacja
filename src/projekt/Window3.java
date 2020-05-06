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
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.nio.charset.Charset;
import java.util.Locale;
import java.util.ResourceBundle;
import java.util.Scanner;

import javax.imageio.ImageIO;
import javax.swing.JButton;
import javax.swing.JEditorPane;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
 // Tu bedzie siê wyœwietla³ napis z pliku png

public class Window3 extends JFrame implements ActionListener 
{

private static final long serialVersionUID = 1L;
private BufferedImage image;
//private JPanel contentPane;

String ang="en";
String country="US";
Locale l1 = new Locale(ang,country);
ResourceBundle r1 = ResourceBundle.getBundle("projekt/language_en_ENG",l1);

JPanel pUpper, pDown, pLeft, pRight, pRight2, drawPanel;
JLabel label;
JButton PL, ENG, RUS, GE;
JEditorPane editorpane;

JTextField text2; 

	public static void main(String[] args){
		
		EventQueue.invokeLater(new Runnable(){
			public void run(){
				try {
					Window3 frame = new Window3();
					frame.setVisible(true);					
				}
				catch (Exception es){
					es.printStackTrace();
				}
			}
		});
	}
 
	
	public Window3() {
		super(); 
		
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		this.setSize(900,600);
	    this.setLayout(new BorderLayout());
	    
	    pUpper = new JPanel();
	    
	    ENG = new JButton ("ENG");
	    ENG.setActionCommand("eng");
	    ENG.setBackground(Color.red);
		ENG.addActionListener(this);
		
		PL = new JButton ("PL");
	    PL.setActionCommand("pl");
	    PL.setBackground(Color.blue);
		PL.addActionListener(this);
		
		RUS = new JButton ("RUS");
	    RUS.setActionCommand("rus");
	    RUS.setBackground(Color.green);
		RUS.addActionListener(this);
		
		GE = new JButton ("GE");
	    GE.setActionCommand("ge");
	    GE.setBackground(Color.yellow);
		GE.addActionListener(this);
		
		pUpper.add(ENG);
		pUpper.add(PL);
		pUpper.add(RUS);
		pUpper.add(GE);
		pUpper.setLayout(new FlowLayout(1,5,5));
		
		this.add(pUpper, BorderLayout.PAGE_START);
	    	   
	    editorpane = new JEditorPane();						
		editorpane.setEditable(false);	
	    this.add(editorpane, BorderLayout.CENTER);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if (e.getActionCommand() == "eng"){
			File plik = new File("Effect_ENG");
			try {
				InputStreamReader isr = new InputStreamReader(
				        new FileInputStream(plik),
				        Charset.forName("UTF-8").newDecoder() 
				            );
				 Scanner sc = new Scanner(isr);
				 sc.useDelimiter("//Z");
				 String plikwczytany = sc.hasNext() ? sc.next() : "";
				 editorpane.setText(plikwczytany); 
                 isr.close();
			} catch (FileNotFoundException e1) {
				e1.printStackTrace();
			} catch (IOException e1) {
				e1.printStackTrace();
			}
		}
		else if (e.getActionCommand() == "pl") {
			File plik1 = new File("Effect_PL");
			try {
				InputStreamReader isr = new InputStreamReader(
				        new FileInputStream(plik1),
				        Charset.forName("UTF-8").newDecoder() 
				            );
				 Scanner sc = new Scanner(isr);
				 sc.useDelimiter("//Z");
				 String plikwczytany1 = sc.hasNext() ? sc.next() : "";
				 editorpane.setText(plikwczytany1); 
                 isr.close();
			} catch (FileNotFoundException e1) {
				e1.printStackTrace();
			} catch (IOException e1) {
				e1.printStackTrace();
			}
		}
		else if (e.getActionCommand() == "rus")	{
			File plik2 = new File("Effect_RUS");
			try {
				InputStreamReader isr = new InputStreamReader(
				        new FileInputStream(plik2),
				        Charset.forName("UTF-8").newDecoder() 
				            );
				 Scanner sc = new Scanner(isr);
				 sc.useDelimiter("//Z");
				 String plikwczytany2 = sc.hasNext() ? sc.next() : "";
				 editorpane.setText(plikwczytany2); 
                 isr.close();
			} catch (FileNotFoundException e1) {
				e1.printStackTrace();
			} catch (IOException e1) {
				e1.printStackTrace();
			}
		}
		else if (e.getActionCommand() == "ge"){
			File plik3 = new File("Effect_GE");
			try {
				InputStreamReader isr = new InputStreamReader(
				        new FileInputStream(plik3),
				        Charset.forName("UTF-8").newDecoder() 
				            );
				 Scanner sc = new Scanner(isr);
				 sc.useDelimiter("//Z");
				 String plikwczytany3 = sc.hasNext() ? sc.next() : "";
				 editorpane.setText(plikwczytany3); 
                 isr.close();
			} catch (FileNotFoundException e1) {
				e1.printStackTrace();
			} catch (IOException e1) {
				e1.printStackTrace();
			}
		}
		
	}
	}