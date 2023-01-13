import java.sql.*;
import java.util.*;

public class
Main {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        System.out.println("Veuillez saisir le nom du projet:");
        String nom_projet = sc.nextLine();
        System.out.println("Veuillez saisir le numéro de projet:");
        int id_projet = sc.nextInt();

        List<String> liste_apprenants = new ArrayList<String>();
        List<String> liste_groupe = new ArrayList<>();
        int i = 0;
        int id_groupe = 1;

        String url = "jdbc:mysql://localhost:3306/binomotron";
        String user = "root";
        String pwd = "root";
        String driver = "com.mysql.jdbc.Driver";

        Connection con = null;
        Statement st = null;
        ResultSet rs1 = null;


        try {
            con = DriverManager.getConnection(url, user, pwd);
            st = con.createStatement();
            rs1 = st.executeQuery("SELECT * FROM apprenant;");

            while (rs1.next()) {
                liste_apprenants.add(rs1.getString("id_apprenant") + " " + rs1.getString("prenom_apprenant") + " " + (rs1.getString("nom_apprenant")));
            }   // recupérer l'id, le prénom et le nom des apprenants


            System.out.println("Liste des apprenants avant mélange: " + liste_apprenants + "\n");  // afficher la liste des apprenants avant mélange

            Collections.shuffle(liste_apprenants);  // mélanger la liste ds apprenants

            System.out.println("Liste des apprenants après mélange: " + liste_apprenants + "\n"); // afficher la liste des apprenants après mélange

            if (liste_apprenants.size() % 2 == 0) {   //cas de liste des apprenants paire
                for (i = 0; i < liste_apprenants.size(); i += 2) {
                    System.out.println("binôme " + id_groupe + " : " + liste_apprenants.get(i) + " et " + liste_apprenants.get(i + 1) + "\n"); // chercher les noms des apprenants i et i+1
                    liste_groupe.add("binôme " + id_groupe + " : " + liste_apprenants.get(i) + "et" + liste_apprenants.get(i + 1)); //ajout des binomes à la liste des groupes
                    id_groupe++;  // incrémenter la valeur du numéro groupe créé
                }
            } else {   //cas de liste des apprenants impaire
                for (i = 0; i < liste_apprenants.size() - 1; i += 2) {
                    System.out.println("binôme " + id_groupe + " : " + liste_apprenants.get(i) + " et " + liste_apprenants.get(i + 1) + "\n"); // chercher les noms des apprenants i et i+1
                    liste_groupe.add("binôme " + id_groupe + " : " + liste_apprenants.get(i) + " et " + liste_apprenants.get(i + 1)); //ajout des binomes à la liste des groupes
                    id_groupe++; // incrémenter la valeur du numéro groupe créé
                }
                System.out.println("et un éleve en solo, le groupe " + id_groupe + " : " + liste_apprenants.get(i) + "\n"); // chercher le nom de l'apprenant restant i
                liste_groupe.add("solo " + id_groupe + " : " + liste_apprenants.get(i));  //ajout du solo restant à la liste des groupes
            }

            System.out.println("Nous obtenons la liste de groupes suivante:" + liste_groupe + "\n");  // afficher la liste des groupes

            //String requete = "INSERT INTO apprenants_groupe(id_apprenant, id_groupe, id_projet) VALUES(id_apprenant,numbinome,id_projet)";   // tentative d'insertion des données dans la base de données
            //st.executeUpdate(requete);

        } catch (Exception e) {
            System.err.println("Exception: " + e.getMessage());
        } finally {
            try {
                if (st != null) st.close();
                if (con != null) con.close();
            } catch (Exception e) {
            }
        }
    }
}
