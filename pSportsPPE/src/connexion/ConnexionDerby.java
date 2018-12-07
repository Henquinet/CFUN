package connexion;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class ConnexionDerby {
    private static ConnexionDerby singleton;
    
    private Connection connexion;
    
    private ConnexionDerby() {
        final String DRIVER = "com.mysql.jdbc.Driver";
        final String URL = "jdbc:derby://localhost/DB_CFun?localEncoding=ISO8859_1";
        
        final String USER = "sysdba";
        final String PASSWORD = "masterkey";
        
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
        int nbFitTotal = 0;
        
        PreparedStatement pst = null;
        ResultSet rs = null;
        String sql = "";
        
        try {
               sql = "SELECT place FROM Salle WHERE nomSalle = ?";
               
               pst = connexion.prepareStatement(sql);
               pst.setString(1, salle);
               rs = pst.executeQuery();
               rs.next();
               
               nbFitTotal = rs.getInt(1);;
               
        } catch(SQLException e) {
            System.err.println("SQL erreur : " + sql + " " + e.getMessage());
        } catch(Exception e) {
            System.err.println("Erreur : "+ e);
        }
        return nbFitTotal;
    }
    
    public int getNbEquipementsOccupees(String salle) {
        int nbFitLibre = 0;
        
        PreparedStatement pst = null;
        ResultSet rs = null;
        String sql = "";
        
        try {
               sql = "SELECT count (*) FROM Equipement E "
                       + "INNER JOIN salle S ON E.salle = S.id_salle"
                       + "WHERE etat = true AND libre = false AND S.nomSalle = ?";
               
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
               sql = "SELECT count (*) FROM Equipement E "
                       + "INNER JOIN salle S ON E.salle = S.id_salle"
                       + "WHERE etat = false AND S.nomSalle = ?";
               
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
            sql = "SELECT id FROM Equipement"
                    + "INNER JOIN salle S ON E.salle = S.id_salle"
                    + "WHERE etat = true AND libre = ? AND S.nomSalle = ?";
            
            pst = connexion.prepareStatement(sql);
            pst.setInt(1, libre);
            pst.setString(1, salle);
            rs = pst.executeQuery();
            rs.next();
            
            int id = rs.getInt(1);

            
            sql = "UPDATE Equipement"
               + "SET Libre = ?"
               + "WHERE id = ?";
           
           pst = connexion.prepareStatement(sql);
           pst.setInt(1, setLibre);
           pst.setInt(2, id);
           rs = pst.executeQuery();
           rs.next();
               
        } catch(SQLException e) {
            System.err.println("SQL erreur : " + sql + " " + e.getMessage());
        } catch(Exception e) {
            System.err.println("Erreur : "+ e);
        }
    }
    
}
