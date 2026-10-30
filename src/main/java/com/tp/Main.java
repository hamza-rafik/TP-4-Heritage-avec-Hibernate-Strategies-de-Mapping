package com.tp;

import com.tp.dao.DepartementDao;
import com.tp.model.joined.Compte;
import com.tp.model.joined.CompteCourant;
import com.tp.model.joined.CompteEpargne;
import com.tp.model.mapped.Departement;
import com.tp.model.singletable.Etudiant;
import com.tp.model.singletable.Personne;
import com.tp.model.singletable.Professeur;
import com.tp.model.tableperclass.Moto;
import com.tp.model.tableperclass.Vehicule;
import com.tp.model.tableperclass.Voiture;
import com.tp.service.CompteService;
import com.tp.service.PersonneService;
import com.tp.service.VehiculeService;
import com.tp.util.HibernateUtil;

import java.time.LocalDate;
import java.util.List;

/**
 * Application principale du :
 * TP 4 : Héritage avec Hibernate - Stratégies de Mapping.
 * 
 * Démontre de façon exhaustive et comparative l'ensemble des stratégies d'héritage supportées par JPA & Hibernate :
 * 1. SINGLE_TABLE (Une seule table avec colonne discriminante)
 * 2. JOINED (Une table par sous-classe avec jointures et clés étrangères)
 * 3. TABLE_PER_CLASS (Une table par classe concrète avec requêtes UNION ALL)
 * 4. @MappedSuperclass (Mutualisation des attributs et de l'audit)
 */
public class Main {

    public static void main(String[] args) {
        afficherBanniere();

        PersonneService personneService = new PersonneService();
        CompteService compteService = new CompteService();
        VehiculeService vehiculeService = new VehiculeService();
        DepartementDao departementDao = new DepartementDao();

        // ==========================================================================================
        // PARTIE 1 : STRATÉGIE 1 - SINGLE_TABLE (Une seule table par hiérarchie)
        // ==========================================================================================
        System.out.println("\n==========================================================================================");
        System.out.println(">>> PARTIE 1 : STRATÉGIE SINGLE_TABLE (Une seule table avec colonne discriminante)");
        System.out.println("    Table physique : 'personnes_single_table' | Colonne discriminante : 'type_personne'");
        System.out.println("==========================================================================================");

        Etudiant etd1 = new Etudiant("Rafik", "Hamza", "hamza.rafik@ensa.ac.ma", "+212 600-112233",
                "CNE-2026-001", "Génie Informatique & IA", "4ème Année");
        Etudiant etd2 = new Etudiant("Bennani", "Sara", "sara.bennani@ensa.ac.ma", "+212 622-334455",
                "CNE-2026-002", "Big Data & Cloud Computing", "Master 2");

        Professeur prof1 = new Professeur("Ait Said", "Oumaima", "oumaima.aitsaid@toubkalit.com", "+212 655-667788",
                "Génie Logiciel & Architectures JPA/Hibernate", "Département Informatique", 28500.0);
        Professeur prof2 = new Professeur("El Amrani", "Karim", "karim.amrani@univ.ac.ma", "+212 677-889900",
                "Intelligence Artificielle & Deep Learning", "Département Math-Info", 31000.0);

        personneService.enregistrer(etd1);
        personneService.enregistrer(etd2);
        personneService.enregistrer(prof1);
        personneService.enregistrer(prof2);

        System.out.println("\n--- Requête Polymorphe (from Personne) : Récupération de tous les enregistrements ---");
        List<Personne> toutesPersonnes = personneService.listerTous();
        System.out.println("+----+----------------------+-----------------------------+-----------------+---------------+-----------------------------------------+");
        System.out.println("| ID | Type (Discriminant)  | Nom & Prénom                | Email           | CNE / Spécial | Détail Spécifique                       |");
        System.out.println("+----+----------------------+-----------------------------+-----------------+---------------+-----------------------------------------+");
        for (Personne p : toutesPersonnes) {
            String type = (p instanceof Etudiant) ? "ETUDIANT" : (p instanceof Professeur) ? "PROFESSEUR" : "PERSONNE";
            String cneOrSpec = (p instanceof Etudiant) ? ((Etudiant) p).getCne() : ((Professeur) p).getSpecialite();
            if (cneOrSpec.length() > 20) cneOrSpec = cneOrSpec.substring(0, 17) + "...";
            String detail = (p instanceof Etudiant) ? ("Filière: " + ((Etudiant) p).getFiliere()) : ("Dép: " + ((Professeur) p).getDepartement() + " | " + ((Professeur) p).getSalaire() + " DH");
            if (detail.length() > 39) detail = detail.substring(0, 36) + "...";
            System.out.printf("| %-2d | %-20s | %-27s | %-15s | %-13s | %-39s |%n",
                    p.getId(), type, p.getPrenom() + " " + p.getNom(), p.getEmail(), cneOrSpec, detail);
        }
        System.out.println("+----+----------------------+-----------------------------+-----------------+---------------+-----------------------------------------+");
        System.out.printf("Total polymorphe : %d personne(s) stockée(s) dans l'unique table 'personnes_single_table'.%n", toutesPersonnes.size());

        System.out.println("\n--- Requêtes Ciblées par Sous-Classe (filtrage automatique sur discriminateur) ---");
        List<Etudiant> etudiants = personneService.listerEtudiants();
        System.out.printf("  > Nombre d'étudiants trouvés : %d%n", etudiants.size());
        for (Etudiant e : etudiants) {
            System.out.printf("    - %s (%s) | CNE: %s | Niveau: %s%n", e.getPrenom() + " " + e.getNom(), e.getFiliere(), e.getCne(), e.getNiveau());
        }

        List<Professeur> profs = personneService.listerProfesseurs();
        System.out.printf("  > Nombre de professeurs trouvés : %d%n", profs.size());
        for (Professeur pr : profs) {
            System.out.printf("    - Pr. %s (%s) | Salaire: %.2f DH%n", pr.getPrenom() + " " + pr.getNom(), pr.getSpecialite(), pr.getSalaire());
        }


        // ==========================================================================================
        // PARTIE 2 : STRATÉGIE 2 - JOINED (Une table par sous-classe avec jointure)
        // ==========================================================================================
        System.out.println("\n==========================================================================================");
        System.out.println(">>> PARTIE 2 : STRATÉGIE JOINED (Une table par sous-classe avec clé étrangère)");
        System.out.println("    Tables physiques : 'comptes_joined' (Mère), 'comptes_courants' (Fille 1), 'comptes_epargnes' (Fille 2)");
        System.out.println("==========================================================================================");

        CompteCourant cc1 = new CompteCourant("CC-5001-MAD", 45000.00, LocalDate.of(2024, 1, 15), 10000.0, 35.0);
        CompteCourant cc2 = new CompteCourant("CC-5002-EUR", 12850.50, LocalDate.of(2024, 3, 20), 5000.0, 45.0);

        CompteEpargne ce1 = new CompteEpargne("CE-8001-MAD", 185000.00, LocalDate.of(2023, 6, 10), 0.035, 300000.0);
        CompteEpargne ce2 = new CompteEpargne("CE-8002-MAD", 92000.00, LocalDate.of(2023, 11, 5), 0.040, 200000.0);

        compteService.ouvrirCompte(cc1);
        compteService.ouvrirCompte(cc2);
        compteService.ouvrirCompte(ce1);
        compteService.ouvrirCompte(ce2);

        System.out.println("\n--- Requête Polymorphe (from Compte) : Exécution d'un LEFT OUTER JOIN automatique ---");
        List<Compte> tousComptes = compteService.listerTousComptes();
        System.out.println("+----+---------------+------------------+------------------+-------------------------------------------------+");
        System.out.println("| ID | Type Compte   | Numéro Compte    | Solde Actuel     | Caractéristiques de la Sous-Classe              |");
        System.out.println("+----+---------------+------------------+------------------+-------------------------------------------------+");
        for (Compte c : tousComptes) {
            String type = (c instanceof CompteCourant) ? "COURANT" : "ÉPARGNE";
            String spec = (c instanceof CompteCourant) ?
                    String.format("Découvert Autorisé: %.2f DH | Frais: %.2f DH", ((CompteCourant) c).getDecouvertAutorise(), ((CompteCourant) c).getFraisTenueCompte()) :
                    String.format("Taux Intérêt: %.2f%% | Plafond: %.2f DH", (((CompteEpargne) c).getTauxInteret() * 100), ((CompteEpargne) c).getPlafondDepot());
            System.out.printf("| %-2d | %-13s | %-16s | %13.2f DH | %-47s |%n",
                    c.getId(), type, c.getCode(), c.getSolde(), spec);
        }
        System.out.println("+----+---------------+------------------+------------------+-------------------------------------------------+");
        System.out.printf("Total des encours bancaires gérés : %.2f DH sur %d comptes (répartis sur 3 tables jointes).%n",
                compteService.totalEncoursBancaire(), tousComptes.size());


        // ==========================================================================================
        // PARTIE 3 : STRATÉGIE 3 - TABLE_PER_CLASS (Une table par classe concrète)
        // ==========================================================================================
        System.out.println("\n==========================================================================================");
        System.out.println(">>> PARTIE 3 : STRATÉGIE TABLE_PER_CLASS (Une table par classe concrète)");
        System.out.println("    Tables physiques : 'voitures_table_per_class' et 'motos_table_per_class'");
        System.out.println("    Résolution des requêtes polymorphes par Hibernate : Requête SQL 'UNION ALL'");
        System.out.println("==========================================================================================");

        Voiture v1 = new Voiture("12345-A-26", "Mercedes-Benz", "Classe C 220d", 420000.0, 5, 8, true);
        Voiture v2 = new Voiture("67890-B-1", "Tesla", "Model 3 Performance", 530000.0, 5, 10, true);

        Moto m1 = new Moto("99881-D-50", "Yamaha", "T-MAX 560 Tech MAX", 145000.0, 560, "Automatique CVT");
        Moto m2 = new Moto("77443-E-12", "BMW", "R 1250 GS Adventure", 215000.0, 1254, "Manuelle 6 rapports");

        vehiculeService.ajouterVehicule(v1);
        vehiculeService.ajouterVehicule(v2);
        vehiculeService.ajouterVehicule(m1);
        vehiculeService.ajouterVehicule(m2);

        System.out.println("\n--- Requête Polymorphe (from Vehicule) : Exécution d'un UNION ALL SQL par Hibernate ---");
        List<Vehicule> tousVehicules = vehiculeService.listerToutLeParc();
        System.out.println("+----+------------+------------------+---------------------------+----------------+------------------------------------------+");
        System.out.println("| ID | Catégorie  | Immatriculation  | Marque & Modèle           | Prix (DH)      | Paramètres Techniques                    |");
        System.out.println("+----+------------+------------------+---------------------------+----------------+------------------------------------------+");
        for (Vehicule v : tousVehicules) {
            String cat = (v instanceof Voiture) ? "VOITURE" : "MOTO";
            String spec = (v instanceof Voiture) ?
                    String.format("Portes: %d | Puissance: %d CV | Clim: %s", ((Voiture) v).getNombrePortes(), ((Voiture) v).getPuissanceFiscale(), ((Voiture) v).isClimatisee() ? "OUI" : "NON") :
                    String.format("Cylindrée: %d cc | Boîte: %s", ((Moto) v).getCylindree(), ((Moto) v).getTypeTransmission());
            System.out.printf("| %-2d | %-10s | %-16s | %-25s | %11.2f DH | %-40s |%n",
                    v.getId(), cat, v.getImmatriculation(), v.getMarque() + " " + v.getModele(), v.getPrix(), spec);
        }
        System.out.println("+----+------------+------------------+---------------------------+----------------+------------------------------------------+");
        System.out.printf("Valeur vénale totale du parc : %.2f DH pour %d véhicules répertoriés.%n",
                vehiculeService.calculerValeurTotale(), tousVehicules.size());


        // ==========================================================================================
        // PARTIE 4 : CAS PARTICULIER - @MappedSuperclass (Héritage sans entité)
        // ==========================================================================================
        System.out.println("\n==========================================================================================");
        System.out.println(">>> PARTIE 4 : CAS PARTICULIER @MappedSuperclass (Factorisation d'Attributs & Audit)");
        System.out.println("    Super-classe : 'EntiteAudit' (non-entité, sans table) -> Héritée par 'Departement'");
        System.out.println("==========================================================================================");

        Departement d1 = new Departement("DEP-INFO", "Génie Informatique & Intelligence Artificielle", "Bâtiment A - 3ème Étage", "Hamza Rafik");
        Departement d2 = new Departement("DEP-DATA", "Sciences des Données, Cloud & Cybersécurité", "Bâtiment B - Pôle Recherche", "Oumaima Ait Said");

        departementDao.create(d1);
        departementDao.create(d2);

        List<Departement> departements = departementDao.findAll();
        System.out.println("+----+------------+-------------------------------------------------+--------------------------+-----------------------+");
        System.out.println("| ID | Code       | Nom du Département                              | Localisation             | Audit / Créé par      |");
        System.out.println("+----+------------+-------------------------------------------------+--------------------------+-----------------------+");
        for (Departement d : departements) {
            System.out.printf("| %-2d | %-10s | %-47s | %-24s | %-21s |%n",
                    d.getId(), d.getCode(), d.getNom(), d.getLocalisation(), d.getCreePar());
        }
        System.out.println("+----+------------+-------------------------------------------------+--------------------------+-----------------------+");


        // ==========================================================================================
        // PARTIE 5 : MATRICE COMPARATIVE DES STRATÉGIES DE MAPPING D'HÉRITAGE
        // ==========================================================================================
        System.out.println("\n==========================================================================================");
        System.out.println(">>> PARTIE 5 : MATRICE COMPARATIVE DES STRATÉGIES DE MAPPING D'HÉRITAGE HIBERNATE");
        System.out.println("==========================================================================================");
        System.out.println("+-------------------+-------------------+-------------------+-------------------+-----------------------+");
        System.out.println("| Critère d'Analyse | SINGLE_TABLE      | JOINED            | TABLE_PER_CLASS   | @MappedSuperclass     |");
        System.out.println("+-------------------+-------------------+-------------------+-------------------+-----------------------+");
        System.out.println("| Nombre de Tables  | 1 unique table    | 1 mère + N filles | N tables concrètes| N tables filles       |");
        System.out.println("| Colonnes NULL     | OUI (Obligatoire) | NON (Normalisé)   | NON (Normalisé)   | NON                   |");
        System.out.println("| Contrainte NOT NULL| Impossible filles | Possible partout  | Possible partout  | Possible partout      |");
        System.out.println("| Requêtes Sous-Cl. | Rapide (WHERE)    | Jointure (JOIN)   | Très rapide (sans)| Directe               |");
        System.out.println("| Requête Polymorphe| Maximale (SELECT) | Moyenne (L-JOIN)  | Faible (UNION ALL)| Non supportée         |");
        System.out.println("| Clé Primaire      | IDENTITY / AUTO   | IDENTITY / AUTO   | SEQUENCE / TABLE  | Selon les sous-classes|");
        System.out.println("| Cas d'Usage Idéal | Hiérarchie simple | Modèle normalisé  | Sous-classes isol.| Audit & factorisation |");
        System.out.println("+-------------------+-------------------+-------------------+-------------------+-----------------------+");

        System.out.println("\n==========================================================================================");
        System.out.println(" [SUCCÈS] Exécution du TP 4 terminée avec succès sans aucune erreur !");
        System.out.println("==========================================================================================\n");

        HibernateUtil.shutdown();
    }

    private static void afficherBanniere() {
        System.out.println("************************************************************************************************");
        System.out.println("*                                                                                              *");
        System.out.println("*   TP 4 : Héritage avec Hibernate - Stratégies de Mapping                                     *");
        System.out.println("*   Auteur      : Hamza Rafik                                                                  *");
        System.out.println("*   Framework   : Hibernate ORM 6.4 | Jakarta Persistence API (JPA) | Base H2 In-Memory        *");
        System.out.println("*   Stratégies  : 1. SINGLE_TABLE  | 2. JOINED  | 3. TABLE_PER_CLASS  | 4. @MappedSuperclass   *");
        System.out.println("*                                                                                              *");
        System.out.println("************************************************************************************************");
    }
}
