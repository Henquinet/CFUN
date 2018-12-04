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
               sql = "SELECT count (*) FROM Equipement E "
                       + "INNER JOIN salle S ON E.salle = S.id_salle"
                       + "WHERE S.nomSalle = ?";
               
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
    
    public int getNbEquipementsLibre(String salle) {
        int nbFitLibre = 0;
        
        PreparedStatement pst = null;
        ResultSet rs = null;
        String sql = "";
        
        try {
               sql = "SELECT count (*) FROM Equipement E "
                       + "INNER JOIN salle S ON E.salle = S.id_salle"
                       + "WHERE etat = true AND libre = true AND S.nomSalle = ?";
               
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
    
}
