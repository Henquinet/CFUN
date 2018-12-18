package fr.rigaud;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import com.onbarcode.barcode.EAN13;

public class Arrivee {
	
	private static int numeroSortie = 0;
	private int numeroArrivee;
	private char choixSport;
	private long horaireArrivee;
	private Calendar hDep;  //heure Départ
	private Calendar hAr;	//Change
	private Complexe complexe;
	
	/////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	/// CONSTRUTEURS ///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

	public Arrivee() {}
	
	public Arrivee(final Complexe complexe, final char choixSport) {
		this.horaireArrivee = Calendar.getInstance().getTimeInMillis();
		this.choixSport = choixSport;
		this.complexe = complexe;
		this.hDep = null;
		this.hAr = Calendar.getInstance();
	}

	
	//////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	/// METHODES PUBLIQUES //////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
		
	/**
	 * Retourne le billet d'entrée
	 * @return
	 */
	public String afficheBillet() {
		final String MSGNOM = "Complexe ";
		final String MSGNUM = "Billet d'entr�e n� : ";
		final String MSGDATE = "Date : ";
		final String MSGHEURE = "Heure : ";

		String leBillet;
		leBillet = MSGNOM + this.getComplexe().getNomComplexe() + "\t";
		leBillet += MSGNUM + this.numeroArrivee + "\n";
		
		Calendar leCal = Calendar.getInstance();
		leCal.setTimeInMillis(this.horaireArrivee);
		Date laDate = leCal.getTime();
		SimpleDateFormat leJour = new SimpleDateFormat("dd/MM/yyyy");
		leBillet += MSGDATE + leJour.format(laDate) + "\n";
		SimpleDateFormat lHeure = new SimpleDateFormat("HH:mm");
		leBillet += MSGHEURE + lHeure.format(laDate) + "\n";
		return leBillet;
	}

	/**
	 * Génère le code-barre (fichier image)
	 */
	public String genBarcode() {
		EAN13 barcode = new EAN13();
		String data = "";

		data += format(numeroArrivee);
		
		data += format(hAr.get(Calendar.DAY_OF_MONTH));
		data += format(hAr.get(Calendar.MONTH) + 1);
		data += format(hAr.get(Calendar.YEAR));
		
		data += format(hAr.get(Calendar.HOUR));
		data += format(hAr.get(Calendar.MINUTE));
		
		barcode.setData(data); 
		barcode.setAddCheckSum(true);	//Calcul du CheckSum
		barcode.setShowCheckSumChar(true);	//Affichage du CheckSum sur le code barre
		
		String path = System.getProperty("user.dir");
		
		try {			
			barcode.drawBarcode(path + "/Ressoucres/barcodes/barcode-" + data + ".gif"); 
			 
		} catch (Exception e) {
			e.printStackTrace();
		} 
		return path + "/Ressoucres/barcodes/barcode-" + data + ".gif";
	}
	
	/**
	 * Rajoute un 0 avant le nombre si < 10
	 * Enlève les 2 premiers chiffre si > 1000
	 * @param nb
	 * @return
	 */
	private String format(int nb) {
		String ret ="";
		if(nb < 10) {	// Exemple 7 devient 07
			ret = "0";
		}
		else if(nb >= 100) {	//Exemple 2018 devient 18
			nb = nb % 100;
		}
			
		ret += nb;
		return ret;
	}
		
	/**
	 * Retourn le ticket de sortie
	 * @return
	 */
	public String afficheTicket() {
		final String MSGNOM = "Complexe ";
		final String MSGNUM = "Ticket de sortie n� : ";
		final String MSGDATE = "Date : ";
		final String MSGHEURE = "Heure : ";
		final String MSGCOUT = "Montant : ";

		String leTicket;

		leTicket = MSGNOM + this.getComplexe().getNomComplexe() + "\t";
		leTicket += MSGNUM + ++Arrivee.numeroSortie + "\n";

		this.hDep = Calendar.getInstance();
		
		//on simule ici une sortie 32 mn plus tard
        //hDep.add(Calendar.MINUTE, +32);
        hDep.add(Calendar.MINUTE, +8);
		
		Date laDate = hDep.getTime();
		SimpleDateFormat leJour = new SimpleDateFormat("dd/MM/yyyy");
		leTicket += MSGDATE + leJour.format(laDate) + "\n";
		SimpleDateFormat lHeure = new SimpleDateFormat("HH:mm");
		leTicket += MSGHEURE + lHeure.format(laDate) + "\n";
		leTicket += MSGCOUT + this.getMontant() + " €\n";

		return leTicket;
	}

	/**
	 * Retourne le prix en fonction de la durée
	 * @param duree
	 * @return
	 */
	public double calculPrix(long duree) {
		double cout = 0;
		if (duree <= 30 && duree > 15) {
			cout = 0.5;
		} else {
			if (duree < 60 && duree >30) {
				cout = 1;
			} else {
				// cout fixe d'une heure
				cout = 1;
				duree -= 60;
				// + tous les 1/4 h commencés
				long nbquarts, reste;
				nbquarts = duree / 15;
				reste = duree % 15;
				if (reste != 0)
					nbquarts++;
				cout += nbquarts * 0.5;
			}
		}
	
		return cout;
	}
	
	
	//////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	/// GETTER & SETTERS ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	
	public double getMontant() {
		double cout = 0;

		if (hDep != null) {
			// on passe des ms en mn
			long dep = hDep.getTimeInMillis() / (1000 * 60);
			long arr = this.horaireArrivee / (1000 * 60);
			long duree =  dep - arr;
			cout = calculPrix(duree);
		}
		return cout;
	}
	
	public Complexe getComplexe() {
		return this.complexe;
	}

	public void setNumeroArrivee(int numero) {
		numeroArrivee = numero;
	}
	
	public int getNumeroArrivee() {
		return numeroArrivee;
	}
	
	public char getChoixSport() {
		return choixSport;
	}

	public Calendar gethAr() {
		return hAr;
	}
}
