package fr.rigaud;

import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class Complexe {
	private static int numeroActuel = 0;
	private int nbTotalPlacesFit;
	private int nbTotalPlacesMuscu;
	private int nbPlacesOccupeesFit;
	private int nbPlacesOccupeesMuscu;
	private String nomComplexe;
	List<Arrivee> lesArrivees = new ArrayList<Arrivee>();
	List<Equipement> equipements = new ArrayList<Equipement>();

	
	
	/////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	/// CONSTRUTEURS ///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	
	

	public Complexe(final int nbTotalPlacesMuscu, final int nbTotalPlacesFit, final String nomComplexe) {
		this.nbTotalPlacesFit = nbTotalPlacesFit;
		this.nbTotalPlacesMuscu = nbTotalPlacesMuscu;
		this.nomComplexe = nomComplexe;
		this.nbPlacesOccupeesFit = 0;
		this.nbPlacesOccupeesMuscu = 0;
		for(int i = 0; i < nbTotalPlacesMuscu; i++) {
			equipements.add(new Equipement(true));
		}
		for(int i = 0; i < nbTotalPlacesFit; i++) {
			equipements.add(new Equipement(false));
		}
		
		
		
		
	}


	
	//////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	/// METHODES PUBLIQUES //////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

	public boolean entreeUsager(final Arrivee uneArrivee) {
		boolean ok;
		char choix;

		ok = false;
		choix = uneArrivee.getChoixSport();
		if (choix == 'F') {
			if (this.etatFit() != 1.0) {
				Complexe.setNumeroActuel();
				uneArrivee.setNumeroArrivee(Complexe.getNumeroActuel());
				lesArrivees.add(uneArrivee);
				if(rechercheEquipement(choix == 'M',uneArrivee)) {
					this.nouvelUsagerFitness();
					ok = true;
				}

			}
		} else {
			if (this.etatMuscu() != 1.0) {
				Complexe.setNumeroActuel();
				uneArrivee.setNumeroArrivee(Complexe.getNumeroActuel());
				lesArrivees.add(uneArrivee);
				if(rechercheEquipement(choix == 'M',uneArrivee)) {
					this.nouvelUsagerMusculation();
					ok = true;
				}
				
				
			}
		}
		return ok;
	}

	public Arrivee sortieUsager(final int entree) {
		Arrivee leDepart = recherche(entree);
		sortieEquipement(leDepart);
		if (leDepart.getChoixSport() == 'F') {
			this.oterUsagerFitness();
		} else {
			this.oterUsagerMusculation();
		}
		return leDepart;
	}



	

	public double etatFit() {
		return (this.getNbPlacesOccupeesFit()) * 1.0 / this.nbTotalPlacesFit;
	}

	public String lesInfos() {
		final String MSGNOM = "Etat du complexe : ";
		final String MSGDATE = "date : ";
		final String MSGHEURE = "heure : ";
		final String MSGDISPMUSCU = "Places disponibles M : ";
		final String MSGDISPFIT = "Places disponibles F : ";
		final String MSGOCCMUSCU = "Places occupées M : ";
		final String MSGOCCFIT = "Places occupées F : ";
		final String MSGTXMUSCU = "Taux occ. M : ";
		final String MSGTXFIT = "Taux occ. F : ";
		final String MSGCOULMUSCU = "Couleur M : ";
		final String MSGCOULFIT = "Couleur F : ";
		final String MSGBAS = "M : en musculation	F : en fitness";

		String leDoc;

		DecimalFormat df2 = new DecimalFormat("##0.00%");
		leDoc = MSGNOM + this.nomComplexe + "\t";

		Date laDate = Calendar.getInstance().getTime();
		SimpleDateFormat leJour = new SimpleDateFormat("dd/MM/yyyy");
		leDoc += MSGDATE + leJour.format(laDate) + "\t";
		SimpleDateFormat lHeure = new SimpleDateFormat("HH:mm");
		leDoc += MSGHEURE + lHeure.format(laDate) + "\n";

		leDoc += MSGDISPMUSCU + this.getNbPlacesRestantesMuscu() + "\t";
		leDoc += MSGOCCMUSCU + this.nbPlacesOccupeesMuscu + "\t";
		leDoc += MSGTXMUSCU + df2.format(this.etatMuscu()) + "\t";
		leDoc += MSGCOULMUSCU + this.couleurMuscu() + "\n";

		leDoc += MSGDISPFIT + this.getNbPlacesRestantesFit() + "\t";
		leDoc += MSGOCCFIT + this.nbPlacesOccupeesFit + "\t";
		leDoc += MSGTXFIT + df2.format(this.etatFit()) + "\t";
		leDoc += MSGCOULFIT + this.couleurFit() + "\n\n";

		leDoc += MSGBAS + "\n\n";
		return leDoc;
	}

	
	public void sortieEquipement(Arrivee ar) {
		int cpt = 0;
		boolean find = false;
		while(!find && cpt != equipements.size()) {
			Equipement tmp = equipements.get(cpt);
			if(tmp.getArrivee() != null && tmp.getArrivee().equals(ar) && tmp.isOccupe()) {
				tmp.setArrivee(null);
				tmp.setOccupe(false);
			}
			else {
				cpt++;
			}
		}
	}
	
	/**
	 * Retourne True si un équipement a été trouvé (passe Equipement.IsOccupe à True)
	 * @param musc
	 * @return
	 */
	public boolean rechercheEquipement(boolean musc,Arrivee arr) {
		int cpt = 0;
		boolean find = false;
		while(!find && cpt != equipements.size()) {
			Equipement tmp = equipements.get(cpt);
			if(tmp.isMuscu() == musc && !tmp.isOccupe() && !tmp.isDefectueux()) {
				tmp.setOccupe(true);
				tmp.setArrivee(arr);
				find = true;
			}
			else {
				cpt++;
			}
		}
		return find;
	}
	

	public void nouvelUsagerFitness() {
		nbPlacesOccupeesFit++;
	}

	public void oterUsagerFitness() {
		nbPlacesOccupeesFit--;
	}

	public int getNbPlacesRestantesMuscu() {
		return this.nbTotalPlacesMuscu - (this.nbPlacesOccupeesMuscu);
	}

	public int getNbPlacesOccupeesMuscu() {
		return this.nbPlacesOccupeesMuscu;
	}

	public void nouvelUsagerMusculation() {
		nbPlacesOccupeesMuscu++;
	}

	public void oterUsagerMusculation() {
		nbPlacesOccupeesMuscu--;
	}

	public double etatMuscu() {
		return (this.getNbPlacesOccupeesMuscu()) * 1.0
				/ this.nbTotalPlacesMuscu;
	}

	
	public Equipement getEquipement(int indice) {
		return equipements.get(indice);
	}
	
		
	//////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	/// GETTER & SETTERS ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	
	public int getNbPlacesRestantesFit() {
		return this.nbTotalPlacesFit - (this.nbPlacesOccupeesFit);
	}

	public int getNbPlacesOccupeesFit() {
		return this.nbPlacesOccupeesFit;
	}
	
	public static int getNumeroActuel() {
		return numeroActuel;
	}

	public static void setNumeroActuel() {
		Complexe.numeroActuel = Complexe.getNumeroActuel() + 1;
	}

	public String getNomComplexe() {
		return nomComplexe;
	}
	
	public List<Equipement> getEquipements() {
		return equipements;
	}



	
		
	//////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	/// METHODES PRIVEES ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
		
	private Arrivee recherche(int num) {
		int i = 0;
		Arrivee courant = lesArrivees.get(i);
		while (courant.getNumeroArrivee() != num)
			courant = lesArrivees.get(++i);
		return courant;
	}
	
	
	private String couleurMuscu() {
		ChoixCouleur choixCouleur = new ChoixCouleur(this.etatMuscu());
		return choixCouleur.getCouleur().toString();
	}
	
	
	private String couleurFit() {
		ChoixCouleur choixCouleur = new ChoixCouleur(this.etatFit());
		return choixCouleur.getCouleur().toString();
	}	
}
