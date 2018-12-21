package fr.rigaud;

import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import connexion.ConnexionDerby;

public class Complexe {
	private static int numeroActuel = 0;
	private int nbTotalPlacesFit;
	private int nbTotalPlacesMuscu;
	private String nomComplexe;
	List<Arrivee> lesArrivees = new ArrayList<Arrivee>();
	List<Equipement> equipements = new ArrayList<Equipement>();
	private ConnexionDerby laBase;
	
	
	/////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	/// CONSTRUTEURS ///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
		
	public Complexe(final String nomComplexe) {
	    laBase = ConnexionDerby.getInstance();
	    
		this.nbTotalPlacesFit = laBase.getNbEquipementsTotal("Fitness");;
		this.nbTotalPlacesMuscu = laBase.getNbEquipementsTotal("Musculation");
		this.nomComplexe = nomComplexe;
		
		for(int i = 0; i < nbTotalPlacesMuscu; i++) {
			if (i < laBase.getNbEquipementsOccupees("Musculation")) {
			    equipements.add(new Equipement(true, true));
			} else {
		         equipements.add(new Equipement(true));
			}
		}
		for(int i = 0; i < nbTotalPlacesFit; i++) {
            if (i < laBase.getNbEquipementsOccupees("Fitness")) {
                equipements.add(new Equipement(false, true));
            } else {
                 equipements.add(new Equipement(false));
            }
        }
	}

	
	//////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	/// METHODES PUBLIQUES //////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

	//Change
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
				    laBase.miseAJourUsager("Fitness", true);
					ok = true;
				}

			}
		} else {
			if (this.etatMuscu() != 1.0) {
				Complexe.setNumeroActuel();
				uneArrivee.setNumeroArrivee(Complexe.getNumeroActuel());
				lesArrivees.add(uneArrivee);
				if(rechercheEquipement(choix == 'M',uneArrivee)) {
				    laBase.miseAJourUsager("Musculation", true);
					ok = true;
				}
				
				
			}
		}
		
		return ok;
	}

	public Arrivee sortieUsager(final int entree) {
		Arrivee leDepart = recherche(entree);
		sortieEquipement(leDepart);
		
		return leDepart;
	}
	
	/**
	 * Gestion de la sortie depuis un code barre
	 * @param code = données du code barre décodé
	 * @return ret l'arrivee
	 * @throws InvalidBarrCodeException
	 */
	public Arrivee sortieBarCode(String code) throws InvalidBarrCodeException {
		Arrivee ret = null;
		if(code.length() == 12) {
			//Récupération des données du code barre
			int nBillet = Integer.parseInt(code.substring(0, 2));
			int day = Integer.parseInt(code.substring(2, 4));
			int month = Integer.parseInt(code.substring(4, 6))-1;
			int year = 2000 + Integer.parseInt(code.substring(6, 8));
			int hour = Integer.parseInt(code.substring(8, 10));
			int min = Integer.parseInt(code.substring(10, 12));
			ret = recherche(nBillet);
			
			//Vérification du code barre
			if(ret.gethAr().get(Calendar.DAY_OF_MONTH) == day
				&& ret.gethAr().get(Calendar.MONTH) == month 
				&& ret.gethAr().get(Calendar.YEAR) == year
				&& ret.gethAr().get(Calendar.HOUR) == hour 
				&& ret.gethAr().get(Calendar.MINUTE) == min)
			{
				
				sortieEquipement(ret);
				
			}
			else {
				throw new InvalidBarrCodeException(code);
			}
		}
		else {
			throw new InvalidBarrCodeException(code + " la longueur doit être de 12 charactères");
		}
		
		return ret;
	}

	
	
	public double etatFit() {
		return (this.getNbPlacesIndisponibles(false)) * 1.0d / this.nbTotalPlacesFit;
	}

	public String lesInfos() {
		final String MSGNOM = "Etat du complexe : ";
		final String MSGDATE = "date : ";
		final String MSGHEURE = "heure : ";
		final String MSGDISPMUSCU = "Places disponibles M : ";
		final String MSGDISPFIT = "Places disponibles F : ";
		final String MSGOCCMUSCU = "Places occup�es M : ";
		final String MSGOCCFIT = "Places occup�es F : ";
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
		leDoc += MSGOCCMUSCU + getNbPlacesIndisponibles(true) + "\t";
		leDoc += MSGTXMUSCU + df2.format(this.etatMuscu()) + "\t";
		leDoc += MSGCOULMUSCU + this.couleurMuscu() + "\n";

		leDoc += MSGDISPFIT + this.getNbPlacesRestantesFit() + "\t";
		leDoc += MSGOCCFIT + getNbPlacesIndisponibles(false) + "\t";
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
				if (tmp.getMuscu()) { //WALA YA PËUT ETRE MOYEN DE RECUP LA SALLE AVEC EQUIPPEMENT
				    laBase.miseAJourUsager("Musculation", false);
				} else {
				    laBase.miseAJourUsager("Fitness", false);
				}
			}
			else {
				cpt++;
			}
		}
	}
	
	
	/**
	 * Retourne True si un équipement a été trouvé (passe Equipement.IsOccupe  True)
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
	

	public int getNbPlacesRestantesMuscu() {
		return this.nbTotalPlacesMuscu - (getNbPlacesIndisponibles(true));
	}


	public double etatMuscu() {
		return (this.getNbPlacesIndisponibles(true)) * 1.0
				/ this.nbTotalPlacesMuscu;
	}

	
	public Equipement getEquipement(int indice) {
		return equipements.get(indice);
	}
	
		
	//////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	/// GETTER & SETTERS ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	
	public int getNbPlacesRestantesFit() {
		return this.nbTotalPlacesFit - (getNbPlacesIndisponibles(false));
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

	public int getNbPlacesIndisponibles(boolean isMuscu) {
		int ret = 0;
		for(int i = 0; i < equipements.size(); i++) {
			Equipement tmp =equipements.get(i);
			if((tmp.isMuscu() == isMuscu) && tmp.isOccupe() || tmp.isDefectueux()) {
				ret++;
			}
		}
		
		return ret;
	}
	
	public int getNbPlacesOccupes(boolean isMuscu) {
		int ret = 0;
		for(int i = 0; i < equipements.size(); i++) {
			Equipement tmp =equipements.get(i);
			if(tmp.isMuscu() == isMuscu && tmp.isOccupe()) {
				ret++;
			}
		}
		
		return ret;
	}
	
		
	//////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	/// METHODES PRIVEES ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
		
	public Arrivee recherche(int num) {
		int i = 0;
		Arrivee courant = lesArrivees.get(i);
		while (courant.getNumeroArrivee() != num && i<lesArrivees.size())
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


	public List<Arrivee> getLesArrivees() {
		
		return lesArrivees;
	}	
}
