package fr.rigaud;

public class Equipement {

    private boolean muscu = false;
    private boolean defectueux = false;
    private boolean occupe = false;
    private Arrivee arrivee;
    
    public Equipement(boolean muscu, boolean occupe, boolean defectueux) {
        super();
        this.muscu = muscu;
        this.defectueux = defectueux;
        this.occupe = occupe;
    }
    public Equipement(boolean muscu) {
        super();
        this.muscu = muscu;
    
    }
    public Equipement(boolean muscu, boolean occupe) {
        super();
        this.muscu = muscu;
        this.occupe = occupe;
    }
    public Equipement(Arrivee ar,boolean muscu, boolean occupe, boolean defectueux) {
        super();
        this.muscu = muscu;
        this.defectueux = defectueux;
        this.occupe = occupe;
        this.arrivee = ar;
    }
    public Equipement(Arrivee ar,boolean muscu) {
        super();
        this.muscu = muscu;
        this.arrivee = ar;
    }
    public Equipement(Arrivee ar,boolean muscu, boolean occupe) {
        super();
        this.muscu = muscu;
        this.occupe = occupe;
        this.arrivee = ar;
    }
    public Equipement() {}
    
    
    /////////////////////////////////////////////////
    /// GETTER & SETTER /////////////////////////////
    /////////////////////////////////////////////////
    
    public boolean isMuscu() {
        return muscu;
    }

    public void setMuscu(boolean muscu) {
        this.muscu = muscu;
    }

    public boolean isDefectueux() {
        return defectueux;
    }

    public void setDefectueux(boolean defectueux) {
        this.defectueux = defectueux;
    }

    public boolean isOccupe() {
        return occupe;
    }

    public void setOccupe(boolean occupe) {
        this.occupe = occupe;
    }

    public Arrivee getArrivee() {
        return arrivee;
    }
    public void setArrivee(Arrivee arrivee) {
        this.arrivee = arrivee;
    }
    public boolean getMuscu() {
        return muscu;
    }
    
}