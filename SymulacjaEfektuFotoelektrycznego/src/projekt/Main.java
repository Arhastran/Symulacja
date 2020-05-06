package projekt;

import java.awt.EventQueue;

public class Main {
	
	public static void main(String[] args) {
		
		EventQueue.invokeLater(new Runnable() {	
		public void run() {
			try {			
				PhotoelectricEffect ramka = new  PhotoelectricEffect();
				ramka.setTitle("Photoelectric Effect");
				ramka.setVisible(true);				
			} 
			catch (Exception e) {			
				e.printStackTrace();
			}
		}
	  });
	}
}
