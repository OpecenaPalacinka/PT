import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.Label;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.filechooser.FileNameExtensionFilter;

/**
 * Spoustejici trida programu, graficke rozhrani
 * @author Muhr - Pelikán
 *
 */
public class MainMenu {

	/**
	 * Nacteny soubor
	 */
	public static File file;
	/**
	 * Zda se jedna o soubor data_generated
	 */
	public static boolean jeUkazka = false;
	
	/**
	 * Hlavni trida programu a GUI
	 * @param args parametry prikazove radky
	 */
	public static void main(String[] args) {
		
		//Vytvoreni okna
		JFrame win = new JFrame();
		win.setTitle("Muhr || Pelikán: Simulace");
		win.setPreferredSize(new Dimension(1000, 500));

		makeGui(win);
		

		win.pack();
		
		win.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		win.setLocationRelativeTo(null);
		win.setVisible(true);	
		
	}
	/**
	 * Slozeni vsech komponentu okna
	 * @param win
	 */
	private static void makeGui(JFrame win) {
		
		win.setLayout(new BorderLayout());
		
		
		
		win.add(makeLeft(), BorderLayout.WEST);
		win.add(makeRight(), BorderLayout.EAST);
		
	}
	
	/**
	 * Vytvori pravy panel okna s Generatory
	 * @return pravou cast okna
	 */
	private static JPanel makeRight() {
		
		JPanel rightP = new JPanel();
		rightP.setLayout(new GridLayout(0,3,5,5));
		
		JLabel nadpis = new JLabel("Generator");
		nadpis.setFont(new Font("Arial", 1 ,40));
		
		JLabel dL = new JLabel("Pocet tovaren:");
		dL.setFont(new Font("Arial", 1 ,17));
		JTextField dTF = new JTextField();
		dTF.setFont(new Font("Arial", 1 ,17));
		JLabel sL = new JLabel("Pocet supermarketu:");
		sL.setFont(new Font("Arial", 1 ,17));
		JTextField sTF = new JTextField();
		sTF.setFont(new Font("Arial", 1 ,17));
		JLabel zL = new JLabel("Pocet typu zbozi:");
		zL.setFont(new Font("Arial", 1 ,17));
		JTextField zTF = new JTextField();
		zTF.setFont(new Font("Arial", 1 ,17));
		JLabel tL = new JLabel("Pocet dni:");
		tL.setFont(new Font("Arial", 1 ,17));
		JTextField tTF = new JTextField();
		tTF.setFont(new Font("Arial", 1 ,17));
		JLabel minL = new JLabel("Minimalni hranice:");
		minL.setFont(new Font("Arial", 1 ,17));
		JTextField minTF = new JTextField();
		minTF.setFont(new Font("Arial", 1 ,17));
		JLabel maxL = new JLabel("Maximalni hranice:");
		maxL.setFont(new Font("Arial", 1 ,17));
		JTextField maxTF = new JTextField();
		maxTF.setFont(new Font("Arial", 1 ,17));
		
		JButton gaussB = new JButton("Generuj gaussove");
		
		JLabel vysledek = new JLabel();

		gaussB.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent ae) {
				generuj(vysledek,dTF,sTF,zTF,tTF,minTF,maxTF,3);
			}
        });
		
		JButton rovnoB = new JButton("Generuj rovnomerne");
		
		rovnoB.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent ae) {
				generuj(vysledek,dTF,sTF,zTF,tTF,minTF,maxTF,2);
			}
        });
		
		JButton obojiB = new JButton("Generuj obema zpùsoby");
		
		obojiB.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent ae) {
				generuj(vysledek,dTF,sTF,zTF,tTF,minTF,maxTF,1);
			}
        });
		
		rightP.add(new Label());
		rightP.add(nadpis);
		rightP.add(new Label());
		rightP.add(new Label());
		rightP.add(dL);
		rightP.add(dTF);
		rightP.add(new Label());
		rightP.add(sL);
		rightP.add(sTF);
		rightP.add(new Label());
		rightP.add(zL);
		rightP.add(zTF);
		rightP.add(new Label());
		rightP.add(tL);
		rightP.add(tTF);
		rightP.add(new Label());
		rightP.add(minL);
		rightP.add(minTF);
		rightP.add(new Label());
		rightP.add(maxL);
		rightP.add(maxTF);
		rightP.add(new Label());		
		rightP.add(gaussB);
		rightP.add(rovnoB);
		rightP.add(new Label());		
		rightP.add(obojiB);
		rightP.add(vysledek);
		
		return rightP;
	}
	
	/**
	 * Spusti generaci vybraneho generatoru po kliknuti tlacitka
	 * @param vysledek Label, ktery vypise chybu nebo uspech
	 * @param dTF Textfield s tovarnamy
	 * @param sTF Textfield se supermarkety
	 * @param zTF Textfield s typy zbozi
	 * @param tTF Textfield s poctem dnu
	 * @param minTF Textfield s minimalni hranici
	 * @param maxTF Textfield s maximalni hranici
	 * @param genTyp typ generatoru: 1 = Oboji, 2 = Rovno, 3 = Gauss
	 */
	public static void generuj(JLabel vysledek, JTextField dTF, JTextField sTF, JTextField zTF, 
			JTextField tTF, JTextField minTF, JTextField maxTF, int genTyp) {
		try {
			int d = Integer.parseInt(dTF.getText());
			int s = Integer.parseInt(sTF.getText());
			int z = Integer.parseInt(zTF.getText());
			int t = Integer.parseInt(tTF.getText());
			int min = Integer.parseInt(minTF.getText());
			int max = Integer.parseInt(maxTF.getText());
			int chyba = 0;
			
			if (genTyp==1) {DataGenerator.generuj(d,s,z,t,min,max);}
			else if (genTyp==2) {DataGeneratorRovno.generuj(d,s,z,t,min,max);}
			else if (genTyp==3) {DataGeneratorGauss.generuj(d,s,z,t,min,max);}
			
			if (chyba == -1) {
				vysledek.setText("Min je vetsi jak maximum");
			}
			else if (chyba == -2) {
				vysledek.setText("Negativni nebo nulove hodnoty");						
			}
			else {
				vysledek.setText("Soubor byl uspesne generovan");
			}
		} catch (Exception e) {
			vysledek.setText("Chyba v zadani udaju");
		}
	}

	/**
	 * Vytvori levy panel okna s ovladani simulace
	 * @return levou cast okna
	 */
	private static JPanel makeLeft() {
		
		JPanel leftP = new JPanel();
		leftP.setLayout(new GridLayout(7,2,5,5));
		
		JLabel nadpis = new JLabel("Simulace");
		nadpis.setFont(new Font("Arial", 1 ,40));
		JButton nactiSouborB = new JButton("Nacti simulaci");
		nactiSouborB.setFont(new Font("Arial", 1 ,15));
		
		JLabel upozorneni = new JLabel();
		
		JLabel nactenySoubor = new JLabel("empty");
		nactenySoubor.setFont(new Font("Arial", 1 ,18));
		
		nactiSouborB.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent ae) {
				JFileChooser fileChooser = new JFileChooser();
				fileChooser.setCurrentDirectory(new File(System.getProperty("user.dir")));
				FileNameExtensionFilter filter = new FileNameExtensionFilter("TEXT FILES", "txt", "text");
				fileChooser.setFileFilter(filter);
				int returnValue = fileChooser.showOpenDialog(null);
				if (returnValue == JFileChooser.APPROVE_OPTION) {
					File selectedFile = fileChooser.getSelectedFile();
					file = selectedFile;
					nactenySoubor.setText(selectedFile.getName());
					System.out.println("Vybran soubor: " + selectedFile.getName());
					if (selectedFile.getName().equals("data_generated.txt")) {
						upozorneni.setText("Tento soubor je ukazkovy, nelze spustit");
						jeUkazka = true;
					}
					else {
						upozorneni.setText("");
						jeUkazka = false;
					}
				}
			}
        });
				
		JLabel souborL = new JLabel("Nacteny soubor:");
		souborL.setFont(new Font("Arial", 1 ,20));
		
		JLabel pocetDni = new JLabel("Pocet dni:");
		pocetDni.setFont(new Font("Arial", 1 ,20));
		JTextField dnyTF = new JTextField(Integer.toString(0), 3);
		dnyTF.setFont(new Font("Arial", 1 ,20));
		
		JLabel info = new JLabel("0 = provede se cela simulace");
		
		JButton play = new JButton("Spust Simulaci");
		
		JLabel vysledek = new JLabel();

		play.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent ae) {
				if (!jeUkazka) {
					try {
						Simulace.spustSimulaci(Integer.parseInt(dnyTF.getText()),file);
						if (!Simulace.maReseni) {
							vysledek.setText("Simulace nema reseni bez nakupu z Ciny");
						}
						else {
							vysledek.setText("Finalni cena: " + Double.toString(Simulace.cena));
						}
					} catch (Exception e) {
						vysledek.setText("Chyba v zadani udaju");
						System.out.println("Chyba:" + e);
					}	
				}
				else {
					vysledek.setText("Soubor je ukazkovy, nelze spustit");
				}
			}
        });
		
		JLabel info2 = new JLabel("Prubeh simulace se ulozi do simulace.txt,");
		JLabel info3 = new JLabel(" Statistiky se ulozi do statistiky.txt");
		
		leftP.add(nadpis);
		leftP.add(new Label());
		leftP.add(nactiSouborB);
		leftP.add(upozorneni);
		leftP.add(souborL);
		leftP.add(nactenySoubor);
		leftP.add(pocetDni);
		leftP.add(dnyTF);
		leftP.add(info);
		leftP.add(new Label());
		leftP.add(play);
		leftP.add(vysledek);
		leftP.add(info2);
		leftP.add(info3);
		
		return leftP;
	}
	
}
