package projekt;


import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.GridLayout;
import java.awt.HeadlessException;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.text.DecimalFormat;
import java.util.Locale;

import javax.imageio.ImageIO;
import javax.swing.BorderFactory;
import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JColorChooser;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JSlider;
import javax.swing.JTextField;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.filechooser.FileSystemView;

import java.util.ResourceBundle;

public class PhotoelectricEffect extends JFrame implements ActionListener {
	
	private static final long serialVersionUID = 1L;

	// Deklaracja paneli, zmiennych, sta³ych nie wszystkie narazie u¿ywane

	JMenuBar menuBar;
	JMenu menu, language, windowPane;
	JMenuItem menuSave, menuNewFile, polish, english, rus, german, creators, effect, instruction; 
	JPanel pUpper, pDown, pRight, pLeft, drawPanel;
	JSlider slider;
	JTextField text1, frequency;
	JRadioButton radio1, radio2;
	JButton selectMaterial, changeColor, start, magnes, ces, silver, gold;
	public ButtonGroup group;
	static final int SLIDER_MIN = 0;
	static final int SLIDER_MAX = 1000;
	static final int SLIDER_INIT = 0;
	static int frequencyValue = SLIDER_INIT;
	String choosenPanel = "choice";
	String choosenMaterial = "choosenMaterial"; 
	//Deklaracje do rysowania 
	static final int promien = 100;
	
	BufferedImage image1 = null;

	String ang="en";
	String country="US";
	Locale l1 = new Locale(ang,country);
	ResourceBundle r1 = ResourceBundle.getBundle("projekt/language_en_ENG",l1);
	
	//do obliczania energii fotonu
	static final double exitWorkCes = 3.42;  //[J], 2.14 eV
	static final double exitWorkSilver = 6.81; //[J], 4.26 eV
	static final double exitWorkGold = 8.16; //[J], 5.1eV
	static final double exitWorkMagnes = 5.85; //[J],3.66 eV
	
	
	static final double planck = 0.00000000000000662; 	//tutaj 6,62 jest przemno¿ona przez 10^-15, aby otrzymana liczba by³a
	 															// równa 6,62*10^-19 (bo cala stala plancka jest *10^-34)
															//, poniewaz przy liczeniu predkosci elektronu
															// musimy odjac od energi fotonu prace wyjscia, ktora wyrazona jest w elektronowoltach zamienionych na d¿ule, 
															// wiec ich wartoœc to x*10^-19. 
	static final double electronMass = 0.00000000000910;//*10^-31, tutaj to samo, skalujemy przez 10^-12 aby ta wartosc byla *10^-19
	
	double photonEnergy, x, y, electronSpeed; //energia fotonu wyrazona w J

	//do obrazkow 
	BufferedImage image;



	private JLabel chooseFrequency; 

	public PhotoelectricEffect() throws HeadlessException{
   
		
		super();
		
	this.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
	this.setSize(900,600);
    this.setLayout(new BorderLayout());
    this.setResizable(false);

    //Menu
    menuBar = new JMenuBar();		//zaczynamy tworzenie menu, dodajemy kolejne przyciski, nie wszystkie na ten moment maj¹ zdefiniowane listenery
    menu = new JMenu("Menu");
	    
    menuSave=new JMenuItem(r1.getString("menuZapisz")); 
    menuSave.setActionCommand("zapisz");
    menuSave.addActionListener(this);
		
		
    menuNewFile =new JMenuItem(r1.getString("menuNowyplik"));
    menuNewFile.setActionCommand("nowyplik");
    menuNewFile.addActionListener(this);
    
    menu.add(menuSave);
    menu.add(menuNewFile);
		
    language=new JMenu(r1.getString("language"));
    
    polish=new JMenuItem("PL");
    polish.setActionCommand("polish");
    polish.addActionListener(this);
		
    english=new JMenuItem("ENG");
    english.setActionCommand("english");
    english.addActionListener(this);
		
    rus=new JMenuItem("RUS");
    rus.setActionCommand("rus");
    rus.addActionListener(this);
		
    german=new JMenuItem("GE");
    german.setActionCommand("german");
    german.addActionListener(this);
    
    windowPane = new JMenu("Info");
    
    instruction = new JMenuItem("Instrukcja");
    instruction.addActionListener(new ActionListener() 		 
    {
    	@Override
    	public void actionPerformed(ActionEvent es) {
    		
    		 JOptionPane.showMessageDialog(null,"COœ tu bêdzie" , "Instrukcja", JOptionPane.INFORMATION_MESSAGE); 
      }	
    }
	
  );
  
    creators = new JMenuItem(r1.getString("creators"));
    creators.setActionCommand("creators");
    creators.addActionListener(this);
		
    effect = new JMenuItem(r1.getString("effect"));
    effect.setActionCommand("effect");
    effect.addActionListener(this); 	 
    
    windowPane.add(creators);
    windowPane.add(effect);
    windowPane.add(instruction); 
    
    language.add(english);
    language.add(polish);
    language.add(rus);
    language.add(german); 
		
    menuBar.add(menu);
    menuBar.add(language);
    menuBar.add(windowPane);
    this.setJMenuBar(menuBar);
    
		
    pLeft = new JPanel();
    
    //przyciski odpowiedzialne za dodanie p³ytki, z ktorej wybijane beda elektrony
    //na ten moment na zasadzie wczytania pocz¹tkowego obrazka
    //Nazwy obrazków s¹ narazie niesototne, zmienimy je potem dodaj¹c elementy animacji
    
    JLabel selectMaterial = new JLabel(r1.getString("selectMaterial")); 
    magnes = new JButton(r1.getString("magnes"));
    magnes.setActionCommand("magnes");
    magnes.addActionListener(new ActionListener() 		 
    {
    	@Override
    	public void actionPerformed(ActionEvent es) {
    		
      	URL resource = getClass().getResource("ebonit2.PNG");
      	try 
      	{
      		image = ImageIO.read(resource);
      		Graphics g2d = drawPanel.getGraphics();
      		g2d.drawImage(image, -250, 80, drawPanel);
      	      		
      	} 
      	catch (IOException en) 
      	{
      		System.err.println("Blad odczytu obrazka");
      		en.printStackTrace();
      	}
      	 
      	Dimension dimension = new Dimension(image.getWidth(), image.getHeight());
      	setPreferredSize(dimension);
      	 
      	choosenMaterial = "magnes";  
      	
      }	
    }
	
  );
         
		
    ces = new JButton(r1.getString("ces"));
    ces.setActionCommand("ces");
    ces.addActionListener(new ActionListener() 		 
    {
    	@Override
    	public void actionPerformed(ActionEvent es) 
    	{
    			
    		URL resource = getClass().getResource("porcelana.PNG");
    		try 
    		{
    			image = ImageIO.read(resource);
    			Graphics g2d = drawPanel.getGraphics();
    			g2d.drawImage(image, -250, 80, drawPanel);
    			//setSize(image.getPreferredSize());
    		} 
    		catch (IOException en) 
    		{
    			System.err.println("Blad odczytu obrazka");
    			en.printStackTrace();
    		}
	            	 
    		Dimension dimension = new Dimension(image.getWidth(), image.getHeight());
    		setPreferredSize(dimension);	
	            	 
    		choosenMaterial = "ces";               
    	}	
      });
		
    gold = new JButton(r1.getString("gold"));
    gold.setActionCommand("gold");
    gold.addActionListener(new ActionListener() 		 
    {
    	@Override
    	public void actionPerformed(ActionEvent es) 
    	{
	            	
    		URL resource = getClass().getResource("kalafonia.PNG");
    		try 
    		{
    			image = ImageIO.read(resource);
    			Graphics g2d = drawPanel.getGraphics();
    			g2d.drawImage(image, -250, 80, drawPanel);
    		} 
    		catch (IOException en) 
    		{
    			System.err.println("Blad odczytu obrazka");
    			en.printStackTrace();
    		}
	            	 
    		Dimension dimension = new Dimension(image.getWidth(), image.getHeight());
    		setPreferredSize(dimension);
	            	 
    		choosenMaterial = "gold";               
    	}	
			 
      });
		
    silver = new JButton(r1.getString("silver"));
    silver.setActionCommand("silver");
    silver.addActionListener(new ActionListener() 		 
    {
    	@Override
    	public void actionPerformed(ActionEvent es) 
    	{
    		
    		URL resource = getClass().getResource("szklo.PNG");
    		try 
    		{
    			image = ImageIO.read(resource);
    			Graphics g2d = drawPanel.getGraphics();
    			g2d.drawImage(image, -250, 80, drawPanel);
    		} 
    		catch (IOException en) 
    		{
    			System.err.println("Blad odczytu obrazka");
    			en.printStackTrace();
    		}
	            	 
    		Dimension dimension = new Dimension(image.getWidth(), image.getHeight());
    		setPreferredSize(dimension);
    		
    		choosenMaterial = "silver";               
    	}	
      });

	changeColor = new JButton(r1.getString("Tlo"));
	changeColor.setActionCommand("changeColor");
	changeColor.addActionListener(new ActionListener() 		 
	{
		@Override
		public void actionPerformed(ActionEvent e) 
		{
			Color newColor = JColorChooser.showDialog
					(null, "Wybierz kolor", Color.white);
                  	drawPanel.setBackground(newColor);
		}
	});
		

		
	chooseFrequency = new JLabel("Wybierz czêstotliwoœæ [Hz]:"); 
	
	slider = new JSlider(JSlider.HORIZONTAL, SLIDER_MIN, SLIDER_MAX, SLIDER_INIT);
	slider.setPreferredSize(new Dimension(300,100));
	slider.setMajorTickSpacing(100); 
	slider.setMinorTickSpacing(50); 
	slider.setPaintTicks(true);
	slider.setPaintLabels(true); 
	slider.addChangeListener(new SliderChangeListener());
	
		
	frequency = new JTextField(); 

	start = new JButton("START/STOP");
	start.setActionCommand("start");
	start.setBorder(BorderFactory.createLineBorder(Color.BLACK));
	start.addActionListener(this);
	
	text1 = new JTextField("0"); //tutaj bêdzie wyswietlac sie energia fotonu 
	
	JLabel label = new JLabel("Energia fotonu:"); 
			
	pLeft.setLayout(new GridLayout(12,1)); //ustawiamy przyciski w panelu w odpowiedniej kolejnosci
	pLeft.add(chooseFrequency ,GridLayout(1,1));
	pLeft.add(slider, GridLayout(2,1));
	pLeft.add(frequency, GridLayout(3,1));		
	pLeft.add(changeColor, GridLayout(4,1));
	pLeft.add(selectMaterial, GridLayout(5,1));
	pLeft.add(magnes, GridLayout(6,1));
	pLeft.add(silver, GridLayout(7,1));
	pLeft.add(ces, GridLayout(8,1));
	pLeft.add(gold, GridLayout(9,1));
	pLeft.add(start, GridLayout(10,1));		
	pLeft.add(label, GridLayout(11,1));
	pLeft.add(text1, GridLayout(12,1));
	Color k = new Color(153, 153, 255);
	start.setBackground(k);
	
	this.add(pLeft, BorderLayout.LINE_START); //dodajemy panel do okna
		
	//Panel do symulacji 
		
	drawPanel = new JPanel();
		
	drawPanel.setBackground(Color.white);
	this.add(drawPanel, BorderLayout.CENTER);
		    
}
	
	private Object GridLayout(int i, int j) {
		// TODO Auto-generated method stub
		return null;
	}

	public void paintComponent(Graphics g)
	{
		Graphics2D g2d = (Graphics2D) g;
		g2d.drawImage(image, 0, 0, this);
	}
	
	//Action Performer
	public void actionPerformed(ActionEvent e) {
		if (e.getActionCommand() == "start")
		{
			countElectronSpeed(); 
		System.out.println("Photon energy:" + photonEnergy); 
			
		}
		if (e.getActionCommand() == "zapisz") //zapisywanie obrazu z panelu 
		{
			
			
			 BufferedImage image = new BufferedImage(drawPanel.getWidth(), drawPanel.getHeight(),BufferedImage.TYPE_INT_ARGB);
			 Graphics2D g2d = image.createGraphics();
			 drawPanel.paintAll(g2d);
			 JFileChooser chooser = new JFileChooser(FileSystemView.getFileSystemView().getHomeDirectory());
			 FileNameExtensionFilter filter = new FileNameExtensionFilter("PNG images", "png");
			 chooser.setFileFilter(filter);
			 int returnVal = chooser.showSaveDialog(null);
			 if(returnVal == JFileChooser.APPROVE_OPTION) {
				 File outputFile = new File(chooser.getSelectedFile().getAbsolutePath() + ".png");
				 try {
					 ImageIO.write(image, "png", outputFile);
				 } 
				 catch (IOException exception) {
					 System.out.println(exception.getMessage());
				 }
	         }
		}
		if (e.getActionCommand() == "nowyplik") //nowy, pusty plik
		{
			 JFileChooser chooser = new JFileChooser(FileSystemView.getFileSystemView().getHomeDirectory());
			 FileNameExtensionFilter filter = new FileNameExtensionFilter("PNG images", "png");
			 chooser.setFileFilter(filter);
			 int returnVal = chooser.showOpenDialog(null);
			 if(returnVal == JFileChooser.APPROVE_OPTION) {
				 BufferedImage image = null;
				 File inputFile = new File(chooser.getSelectedFile().getAbsolutePath());
				 try {
					 image = ImageIO.read(inputFile);
					 Graphics g = drawPanel.getGraphics();
					 g.drawImage(image, 0, 0, null);
				 }
				 catch(IOException ex) {
					 System.out.println(ex.getMessage());
	             }

			 }	
		}
		
		if (e.getActionCommand() == "creators") //tutaj okno, które wyœwietla informacje o autorach
		{										//otwiera siê dziêki przyciskowi w menu jako oddzielne okno
			//call the object of NewWindow and set visible true
			Window2 frame = new Window2();
			frame.setVisible(true);
			//set default close operation
			setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		}
		if (e.getActionCommand() == "effect") //okno z informacjami teoretycznymi, równie¿ otwierane poprzez menu oraz jako oddzielne okno
		{
			//call the object of NewWindow and set visible true
			Window3 frame = new Window3();
			frame.setVisible(true);
			//set default close operation
			setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		}
		
		if (e.getActionCommand() == "polish") { //ca³y poni¿szy blok kodu dotyczny zmiany jêzyka
		
	    String pol="pl";
		String kraj="PL";
		Locale l2 = new Locale(pol,kraj);
		ResourceBundle r2 = ResourceBundle.getBundle("projekt/language_pl_PL",l2);
		language.setText(r2.getString("language"));
		frequency.setText(r2.getString("frequency"));
		menuSave.setText(r2.getString("menuZapisz"));
		menuNewFile.setText(r2.getString("menuNowyplik"));
		creators.setText(r2.getString("creators"));
		magnes.setText(r2.getString("magnes"));
		ces.setText(r2.getString("ces"));
		gold.setText(r2.getString("gold"));
		silver.setText(r2.getString("silver"));
		changeColor.setText(r2.getString("Tlo"));
		effect.setText(r2.getString("effect"));
		//selectMaterial.setText(r2.getString("selectMaterial"));
		}
		
		if (e.getActionCommand() == "english"){
			   
		language.setText(r1.getString("language"));
		frequency.setText(r1.getString("frequency"));
		menuSave.setText(r1.getString("menuZapisz"));
		menuNewFile.setText(r1.getString("menuNowyplik"));
		creators.setText(r1.getString("creators"));
		magnes.setText(r1.getString("magnes"));
		ces.setText(r1.getString("ces"));
		gold.setText(r1.getString("gold"));
		silver.setText(r1.getString("silver"));
		changeColor.setText(r1.getString("Tlo"));
		effect.setText(r1.getString("effect"));
		//selectMaterial.setText(r1.getString("selectMaterial"));
		}
		
		if (e.getActionCommand() == "rus"){
		
		String ru="ru";
		String kraj="RUS";
		Locale l3 = new Locale(ru,kraj);
		ResourceBundle r3 = ResourceBundle.getBundle("projekt/language_ru_RUS",l3);
		language.setText(r3.getString("language"));
		frequency.setText(r3.getString("frequency"));
		menuSave.setText(r3.getString("menuZapisz"));
		menuNewFile.setText(r3.getString("menuNowyplik"));
		creators.setText(r3.getString("creators"));
		magnes.setText(r3.getString("magnes"));
		ces.setText(r3.getString("ces"));
		gold.setText(r3.getString("gold"));
		silver.setText(r3.getString("silver"));
		changeColor.setText(r3.getString("Tlo"));
		//selectMaterial.setText(r3.getString("selectMaterial"));
		}
		
		if (e.getActionCommand() == "german") {
		
	    String ge="ge";
		String kraj="GE";
		Locale l4 = new Locale(ge,kraj);
		ResourceBundle r4 = ResourceBundle.getBundle("projekt/language_ge_GE",l4);
		language.setText(r4.getString("language"));
		//frequency.setText(r4.getString("frequency"));
		menuSave.setText(r4.getString("menuZapisz"));
		menuNewFile.setText(r4.getString("menuNowyplik"));
		creators.setText(r4.getString("creators"));
		magnes.setText(r4.getString("magnes"));
		ces.setText(r4.getString("ces"));
		gold.setText(r4.getString("gold"));
		silver.setText(r4.getString("silver"));
		changeColor.setText(r4.getString("Tlo"));
		effect.setText(r4.getString("effect"));
		//selectMaterial.setText(r4.getString("selectMaterial"));
		}
	}
	
	public class SliderChangeListener implements ChangeListener { //klasa implemetnuj¹ca dzia³anie slidera//
		
		 @Override
		 public void stateChanged(ChangeEvent arg0){
			 	
			 String value = String.format("%d Hz", slider.getValue());//nadanie sliderowi wartosci poprzez przesuniecie suwaka
			 frequency.setText(value); //wyswietlenie wartosci w okienku tekstowym
			 frequencyValue = slider.getValue(); // pobranie wybranej wartosci
			 countEnergy(); //liczenie energii na podstawie wybranej wartosci czestotliwosci ze slidera
		  }
	   
	}
	
	void countEnergy() {
		
		photonEnergy = planck*frequencyValue; 
		DecimalFormat df=new DecimalFormat("######.##"); 
		y= photonEnergy*1000000000*1000;//wprowadzam now¹ zmienn¹, któr¹ przeskalujê na potrzeby wyœwietlenia energii
		//nie chce zeby zmieniala ona rz¹d w liczeniu ni¿ej prêdkoœci, wiec nie uzywam oryginalnej zmiennej photonEnergy
		//przemnozono przez taka wartosc, aby wyswietlila sie sama liczba, bo gdy wynik jest
		//za daleko po przecinku, to liczba sie nie chce wyswietlic w polu tekstowym
		//dodatkowo sam wynik jest rzedu 10^-19, wiec aby zgadzal sie rzad bierzemy rz¹d wyniku i mnozymy jeszcze przez
		//rz¹d, ktory sztucznie dodalismy na potrzeby mozliwosci wyswietlenia (10^-12) (pomniejszamy o tyle, bo tyle ddoalismy sztucznie)
		text1.setText(df.format(y) + "x10^-31 J");		
	}
	
	void countElectronSpeed() { //liczenie prêdkoœci wybitego elektronu w zale¿noœci od pracy wyjscia wybranego materia³u
		
		 if (choosenMaterial == "ces") {		 
			 
			 x = (2*(photonEnergy - exitWorkCes))/electronMass;//tutaj wszystkie liczby sa rzêdu 10^-19,wiec wynik bedzie po prostu x
			 electronSpeed = Math.sqrt(x); 
			 JOptionPane.showMessageDialog(null,x + " " + "m/s", "Prêdkoœæ wybitego elektronu" , JOptionPane.INFORMATION_MESSAGE); 
			 		 		
		 }
			 
		 else if (choosenMaterial == "gold"){
			 			
			 x = (2*(photonEnergy - exitWorkGold))/electronMass; 
			 electronSpeed = Math.sqrt(x); 
			 JOptionPane.showMessageDialog(null,x + " " + "m/s", "Prêdkoœæ wybitego elektronu" , JOptionPane.INFORMATION_MESSAGE); 
		 }
		 
		 else if (choosenMaterial == "silver"){
			 
			 x = (2*(photonEnergy - exitWorkSilver))/electronMass; 
			 electronSpeed = Math.sqrt(x); 
			 JOptionPane.showMessageDialog(null,x + " " + "m/s", "Prêdkoœæ wybitego elektronu" , JOptionPane.INFORMATION_MESSAGE); 
			 
		 } 
		 
		 else if (choosenMaterial == "magnes"){
			 
			 x = (2*(photonEnergy - exitWorkMagnes))/electronMass; 
			 electronSpeed = Math.sqrt(x); 
			 JOptionPane.showMessageDialog(null,x + " " + "m/s", "Prêdkoœæ wybitego elektronu" , JOptionPane.INFORMATION_MESSAGE); 
			 
		 }
	}
	
}
