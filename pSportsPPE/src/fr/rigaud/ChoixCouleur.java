package fr.rigaud;


/*
* Couleurs en fonction des �tats : 
*  ~ vert < 70%
*  ~ 70% <= orange < 100%
*  ~ rouge = 100%  
*/
public class ChoixCouleur {
	private Couleur couleur;

	public ChoixCouleur(final double etat) {
		if(etat < 0.7) {
			couleur = Couleur.vert;
		}
		else {
			if(etat<1) {
				couleur = Couleur.orange;
			}
			else {
				couleur = Couleur.rouge;
			}
		}
	}
	
	public Couleur getCouleur() {
		return couleur;
	}
}