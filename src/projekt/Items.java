package projekt;
// to mia³o dzia³aæ a nie dzia³a :(
import java.awt.Color;

import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JSlider;
import javax.swing.JTextField;

public interface Items {
	public static final JMenuBar menubar = new JMenuBar();
	public static final JMenu language = new JMenu();
	public static final JMenu windowpane = new JMenu();
	public static final JMenu menu = new JMenu();
	
	public static final JMenuItem menuZapisz = new JMenuItem();
	public static final JMenuItem menuNowyplik = new JMenuItem();
	public static final JMenuItem polish= new JMenuItem();
	public static final JMenuItem english = new JMenuItem();
	public static final JMenuItem creators = new JMenuItem();
	
	public static final JPanel PUpper = new JPanel();
	public static final JPanel PDown = new JPanel();
	public static final JPanel PRight = new JPanel();
	public static final JPanel PLeft = new JPanel();
	public static final JPanel DrawPanel = new JPanel();
	
	public static final JSlider slider = new JSlider();
	public static final JTextField text1 = new JTextField();
	
	public static final JRadioButton radio1 = new JRadioButton();
	public static final JRadioButton radio2 = new JRadioButton();
	
	public static final JButton selectmaterial = new JButton();
	public static final JButton frequency = new JButton();
	public static final JButton changeColor = new JButton();
	public static final JButton start = new JButton();
	public static final JButton Ebonit = new JButton();
	public static final JButton Porcelana = new JButton();
	public static final JButton Szk³o = new JButton();
	public static final JButton Kalafonia = new JButton();
	
	public static final ButtonGroup group = new ButtonGroup();
	static final int SLIDER_MIN = 3;
	static final int SLIDER_MAX = 30;
	static final int SLIDER_INIT = 3;
	
	static int numberOfTextFieldsRows = SLIDER_INIT;
	String choosenPanel = "choice";
	//Deklaracje do rysowania 
	static final int promien = 100;
	int dlugosclini = 1;
	Color kolorlinii = Color.black;

}
