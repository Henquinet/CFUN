package fr.rigaud;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;


public class Arrivee {

    private static int numeroSortie = 0;
    private int numeroArrivee;
    private char choixSport;
    private long horaireArrivee;
    private Calendar hDep; //heure Départ
    private Complexe complexe;

    

    
    /////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    /// CONSTRUTEURS ///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    ///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    
    /*
     * Constructeur de la classe Arrivee
     * 
     * @param complexe le complexe
     * @param choixSport le choix du sport (muscu ou fit)
     */
    public Arrivee(final Complexe complexe, final char choixSport) {
        this.horaireArrivee = Calendar.getInstance().getTimeInMillis();
        this.choixSport = choixSport;
        this.complexe = complexe;
        this.hDep = null;
    }

    
    //////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    /// METHODES PUBLIQUES //////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    /*
     * methode qui construit le billet d'entrée de la salle de fitness ou de muscu
     * 
     * @return leBillet le billet d'entrée de la salle
     */
    public String afficheBillet() {
        final String MSGNOM = "Complexe ";
        final String MSGNUM = "Billet d'entrée né : ";
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

    /*
     * methode qui construit le ticket de sortie de la salle
     * 
     * @return leTicket le ticket de sortie de la salle
     */
    public String afficheTicket() {
        final String MSGNOM = "Complexe ";
        final String MSGNUM = "Ticket de sortie né : ";
        final String MSGDATE = "Date : ";
        final String MSGHEURE = "Heure : ";
        final String MSGCOUT = "Montant : ";

        String leTicket;

        leTicket = MSGNOM + this.getComplexe().getNomComplexe() + "\t";
        leTicket += MSGNUM + ++Arrivee.numeroSortie + "\n";

        this.hDep = Calendar.getInstance();
        
        Date laDate = hDep.getTime();
        SimpleDateFormat leJour = new SimpleDateFormat("dd/MM/yyyy");
        leTicket += MSGDATE + leJour.format(laDate) + "\n";
        SimpleDateFormat lHeure = new SimpleDateFormat("HH:mm");
        leTicket += MSGHEURE + lHeure.format(laDate) + "\n";
        leTicket += MSGCOUT + this.getMontant() + " €\n";

        return leTicket;
    }

    

    
    //////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    /// GETTER & SETTERS ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    
    /*
     * methode qui calcule et retourne le prix de la séance en fonction de la durée entre l'entrée et la sortie de la salle
     * 
     * @param duree la durée d'utilisation de la salle (entre l'entée et la sortie)
     * @return cout le cout de la séance en fonction de la durée
     */
    public double calculPrix(long duree) {
        double cout = 0;
        
        if (duree > 15) {
            if (duree <= 30 && duree > 15) {
                cout = 0.5;
            } else {
                if (duree < 60 && duree >30) {
                    cout = 1;
                } else {
                    // cout fixe d'une heure
                    cout = 1;
                    duree -= 60;
                    // + tous les 1/4 h commencées
                    long nbquarts, reste;
                    nbquarts = duree / 15;
                    reste = duree % 15;
                    if (reste != 0)
                        nbquarts++;
                    cout += nbquarts * 0.5;
                }
            }
        }
    
        return cout;
    }
    
    /*
     * méthode qui retourne le montant à payer pour la séance à la salle de muscu ou de fitness (en fonction de l'utilisation)
     * en utlisant la méthode calculPrix()
     * 
     * @return cout le cout de la séance en fonction de la durée
     */
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
}
