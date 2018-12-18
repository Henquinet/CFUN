package connexion;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

public class ConnexionDerby {
    private static ConnexionDerby singleton;
    
    private Connection connexion;
    
    private ConnexionDerby() {
        final String DRIVER = "org.apache.derby.jdbc.EmbeddedDriver";
        String path = System.getProperty("user.dir");
        final String URL = "jdbc:derby:" + path + "\\Ressoucres\\Derby\\Base\\DB_CFUN;create?localEncoding=ISO8859_1";
        
        final String USER = "root";
        final String PASSWORD = "";
        
        try {
            Class.forName(DRIVER).newInstance();
            this.connexion = DriverManager.getConnection(URL, USER, PASSWORD);
        } catch(ClassNotFoundException e) {
            System.err.println("Classe non trouvée : " + DRIVER);
        } catch(Exception e) {
            System.err.println("Erreur : "+ e);
        } 
    }
    
    public static synchronized ConnexionDerby getInstance() {
        if (singleton == null) {
            singleton = new ConnexionDerby();
        }
        return singleton; 
    }
    
    public Connection getConnexion() {
        return connexion;
    }
    
    public int getNbEquipementsTotal(String salle) {
        int nbEquipSalle = 0;
        
        PreparedStatement pst = null;
        ResultSet rs = null;
        String sql = "";
        
        try {
               sql = "SELECT place_salle FROM CFUN.Salle WHERE nom_Salle = ?";
               
               pst = connexion.prepareStatement(sql);
               pst.setString(1, salle);
               rs = pst.executeQuery();
               rs.next();
               
               nbEquipSalle = rs.getInt(1);;
               
        } catch(SQLException e) {
            System.err.println("SQL erreur : " + sql + " " + e.getMessage());
        } catch(Exception e) {
            System.err.println("Erreur : "+ e);
        }
        return nbEquipSalle;
    }
    
    public int getNbEquipementsOccupees(String salle) {
        int nbFitLibre = 0;
        
        PreparedStatement pst = null;
        ResultSet rs = null;
        String sql = "";
        
        try {
               sql = "SELECT count (*) FROM CFUN.Equipement E"
                       + " INNER JOIN CFUN.salle S ON E.id_salle = S.id_salle"
                       + " WHERE etat = 1 AND libre = 0 AND S.nom_Salle = ?";
               
               pst = connexion.prepareStatement(sql);
               pst.setString(1, salle);
               rs = pst.executeQuery();
               rs.next();
               
               nbFitLibre = rs.getInt(1);;
               
        } catch(SQLException e) {
            System.err.println("SQL erreur : " + sql + " " + e.getMessage());
        } catch(Exception e) {
            System.err.println("Erreur : "+ e);
        }
        return nbFitLibre;
    }
    
    public int getNbEquipementsDefectueux(String salle) {
        int nbFitLibre = 0;
        
        PreparedStatement pst = null;
        ResultSet rs = null;
        String sql = "";
        
        try {
               sql = "SELECT count (*) FROM CFUN.Equipement E"
                       + " INNER JOIN CFUN.salle S ON E.id_salle = S.id_salle"
                       + " WHERE E.etat = 0 AND S.nom_Salle = ?";
               
               pst = connexion.prepareStatement(sql);
               pst.setString(1, salle);
               rs = pst.executeQuery();
               rs.next();
               
               nbFitLibre = rs.getInt(1);;
               
        } catch(SQLException e) {
            System.err.println("SQL erreur : " + sql + " " + e.getMessage());
        } catch(Exception e) {
            System.err.println("Erreur : "+ e);
        }
        return nbFitLibre;
    }
    
    public void miseAJourUsager(String salle, boolean entree) {
        //adapte le boolean en int pour recuperer un equipement libre ou non
        int libre;
        if (entree) {
            libre = 1;
        } else {
            libre = 0;
        }
        
        // adapte le boolean en int pour mettre a jour l'equipement en occupée ou libre
        int setLibre;
        if (entree) {
            setLibre = 0;
        } else {
            setLibre = 1;
        }
        
        PreparedStatement pst = null;
        ResultSet rs = null;
        String sql = "";
        
        try {
            sql = "SELECT id_equip FROM CFUN.Equipement E"
                    + " INNER JOIN CFUN.salle S ON E.id_salle = S.id_salle"
                    + " WHERE etat = 1 AND libre = ? AND S.nom_Salle = ?";
            
            pst = connexion.prepareStatement(sql);
            pst.setInt(1, libre);
            pst.setString(2, salle);
            rs = pst.executeQuery();
            rs.next();
            
            int id = rs.getInt(1);

            
            sql = "UPDATE CFUN.Equipement"
               + " SET Libre = ?"
               + " WHERE id_equip = ?";
           
           pst = connexion.prepareStatement(sql);
           pst.setInt(1, setLibre);
           pst.setInt(2, id);
           pst.executeUpdate();
               
        } catch(SQLException e) {
            System.err.println("SQL erreur : " + sql + " " + e.getMessage());
        } catch(Exception e) {
            System.err.println("Erreur : "+ e);
        }
    }
    
    public ArrayList<String> getGestionnaire () {
        ArrayList<String> leGestionnaire = new ArrayList<String>();
        
        Statement st = null;
        ResultSet rs = null;
        String sql = "";
        
        try {
               sql = "SELECT identifiant, mdp FROM CFUN.gestionnaire";
               
               st = connexion.createStatement();
               rs = st.executeQuery(sql);
               rs.next();
               
               leGestionnaire.add(rs.getString(1));
               leGestionnaire.add(rs.getString(2));
               
        } catch(SQLException e) {
            System.err.println("SQL erreur : " + sql + " " + e.getMessage());
        } catch(Exception e) {
            System.err.println("Erreur : "+ e);
        }
        return leGestionnaire;
    }
    
}