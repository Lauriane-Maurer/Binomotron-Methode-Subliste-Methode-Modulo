import java.sql.*;
import java.util.*;

public class
Main {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);     // Faire saisir le nom du projet, la date de création des groupes, le numéro de projet et le nombre d'apprenants par groupe
        System.out.println("Veuillez saisir le nom du projet:");
        String nom_projet = sc.nextLine();
        System.out.println("Veuillez saisir la date de création des groupes:");
        String date_creation = sc.nextLine();
        System.out.println("Veuillez saisir le numéro de projet (id):");
        int id_projet = sc.nextInt();
        System.out.println("Veuillez saisir le nombre d'apprenants par groupe:");
        int taille_groupe = sc.nextInt();


        List<String> liste_apprenants = new ArrayList<String>();     // déclarer la variable liste_apprenants
        List<String> liste_groupe = new ArrayList<>();               // déclarer la varibale liste_groupe
        int i = 0;                                                   // déclarer la variable i
        int id_groupe = 1;                                           // déclarer la variable

        String url = "jdbc:mysql://localhost:3306/binomotron";      // copier coller comme Stéphane
        String user = "root";
        String pwd = "root";
        String driver = "com.mysql.cj.jdbc.Driver";

        Connection con = null;
        Statement st = null;
        ResultSet rs1 = null;


        try {
            con = DriverManager.getConnection(url, user, pwd);
            st = con.createStatement();
            rs1 = st.executeQuery("SELECT * FROM apprenant;");  // Chercher dans la base de données apprenant les données des apprenants

            while (rs1.next()) {
                liste_apprenants.add(rs1.getString("id_apprenant") + " " + rs1.getString("prenom_apprenant") + " " + (rs1.getString("nom_apprenant")));
            }   // recupérer l'id, le prénom et le nom des apprenants

            System.out.println("Liste des apprenants avant mélange: " + liste_apprenants + "\n");

            Collections.shuffle(liste_apprenants);  // mélanger la liste ds apprenants

            System.out.println("Liste des apprenants après mélange: " + liste_apprenants + "\n");

            for (i = 0; i < liste_apprenants.size(); i += taille_groupe) {             // créer des sous-liste à partir de la liste_apprenants, avec le nombre d'apprenants souhaité par groupe
                liste_groupe.add(liste_apprenants.subList(i, Math.min(i + taille_groupe,
                        liste_apprenants.size())).toString());
                System.out.println("Groupe " + id_groupe + " : " + (liste_apprenants.subList(i, Math.min(i + taille_groupe,
                        liste_apprenants.size())).toString()) + "\n");
                id_groupe++;
            }
            System.out.println("Nous obtenons la liste de groupes suivante:" + liste_groupe + "\n");    //Afficher la liste des groupes


                String requete = "INSERT INTO apprenant_groupe_projet(id_apprenant, id_groupe, id_projet, date_creation) VALUES(id_apprenant, id_groupe, id_projet, date_creation)";
               st.executeUpdate(requete);


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