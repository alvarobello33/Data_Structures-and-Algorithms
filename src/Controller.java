import graphs.*;
import rtree.Bardissa;
import rtree.Punt;
import rtree.RTree;
import btree.BTree;
import btree.Persona;
import taules.Acusat;
import taules.HashTable;

import java.util.ArrayList;

public class Controller {

    // TODO: Aqui podeu modificar el nom dels fitxers que voleu llegir
    private static String FILENAME_GRAPHS = "graphsXS.paed";
    private static String FILENAME_BTREES = "treeXS.paed";
    private static String FILENAME_BARDISSES = "rtreeXXS.paed";
    private static String FILENAME_ACUSATS = "tablesXXS.paed";

    public static void start() throws CloneNotSupportedException {

        int option = 0;
        Menu menu = new Menu();
        menu.showMessage("'`^\\ The Hashy Grail /^´'");

        while (option != 5) {
            menu.showMenu();
            menu.showMessage("");
            option = menu.askForInt("Esculli una opció: ");
            String funcionalitat;

            switch (option) {
                case 1:
                    LlocInteres[] llocsInteres = FileReader.readLlocsInteres(FILENAME_GRAPHS);
                    Trajecte[] trajectes;
                    if (llocsInteres != null) {
                        trajectes = FileReader.readTrajectes(FILENAME_GRAPHS);
                        if (trajectes != null) {
                            ArrayList<LlocInteres> queue = Fase1.initGraph(llocsInteres, trajectes);
                            Graph graph = new Graph(queue);

                            do {

                                menu.showMessage("");
                                menu.showMessage("A. Exploració del regne");
                                menu.showMessage("B. Detecció de trajectes habituals");
                                menu.showMessage("C. Missatgeria premium");
                                menu.showMessage("");
                                menu.showMessage("D. Tornar enrere");
                                menu.showMessage("");
                                funcionalitat = menu.askForString("Quina funcionalitat vol executar? ");
                                funcionalitat = funcionalitat.toUpperCase();

                                switch (funcionalitat) {
                                    case "A":
                                        graph.cleanVisitats();
                                        // Exploracio del regne
                                        int id = menu.askForInt("Introdueix el id del lloc que vol explorar: ");
                                        LlocInteres llocInteres = graph.getLlocSegonsId(id);
                                        if (llocInteres != null) {
                                            String regne = graph.getLlocSegonsId(id).getRegne();

                                            menu.showMessage("");
                                            menu.showMessage(id + " - " + llocInteres.getNom() + ", " + regne + " (Clima " + llocInteres.getClima() + ")");
                                            menu.showMessage("");
                                            menu.showMessage("Els llocs del " + regne + " als que es pot arribar són: ");
                                            menu.showMessage("");
                                            Fase1.DFS(graph, llocInteres);
                                        } else {
                                            menu.showMessage("Error: no s'ha trobat el lloc");
                                        }

                                        break;
                                    case "B":
                                        // Detecció de trajectes habituals
                                        graph.cleanVisitats();
                                        Graph mst = Fase1.Prim(graph);

                                        menu.showMessage("");
                                        menu.showMessage("Els trajectes més habituals són els següents: ");
                                        menu.showMessage("");

                                        for (LlocInteres lloc:mst.getQueue()) {
                                            for (Trajecte t:lloc.getTrajectes()) {
                                                if (!t.isMostrat()) {
                                                    menu.showMessage("\t" +t.getLlocInteresA().getId()+ " -> " +t.getLlocInteresB().getId());
                                                    t.setMostrat(true);
                                                }
                                            }
                                        }

                                        break;

                                    case "C":
                                        // Missatgeria premium
                                        graph.cleanVisitats();

                                        int idOrigen = menu.askForInt("Quin es el lloc (id) d'origen? ");
                                        int idDesti = menu.askForInt("Quin es el lloc (id) desti? ");
                                        boolean carregaCoco = menu.askOrenetaCoco("L'oreneta carrega un coco?");

                                        //Fase1.dijkstra(graph, idOrigen, idDesti, carregaCoco);
                                        ArrayList<Trajecte> ruta = Dijkstra.dijkstraMillorTrajecte(graph, idOrigen, idDesti, carregaCoco);

                                        if (ruta != null) {
                                            menu.showMessage("");
                                            menu.showMessage("L’opció més eficient és enviar una oreneta " +Dijkstra.getTipusOreneta()+ ".");
                                            menu.showMessage("");
                                            menu.showMessage("\tTemps: " +Dijkstra.getMillorTemps()+ " minuts");
                                            menu.showMessage("\tDistància: " +Dijkstra.getMillorDistancia()+ " quilòmetres");
                                            menu.showMessage("\tCamí: ");

                                            int idActual = idOrigen;
                                            for (Trajecte t:ruta) {
                                                if (t.getIdLlocA() == idActual) {
                                                    menu.showMessage("\t\t" +t.getLlocInteresA().getNom()+ " -> " +t.getLlocInteresB().getNom());
                                                    idActual = t.getIdLlocB();
                                                } else {
                                                    menu.showMessage("\t\t" +t.getLlocInteresB().getNom()+ " -> " +t.getLlocInteresA().getNom());
                                                    idActual = t.getIdLlocA();
                                                }
                                            }
                                        } else {
                                            menu.showMessage("No existeix una ruta directe que pugui realitzar una oreneta europea o africana.");
                                        }

                                        break;

                                }
                            } while (!funcionalitat.equals("D"));
                        }
                    }

                    break;
                case 2:
                    Persona[] persones = FileReader.readPersones(FILENAME_BTREES);
                    BTree bTree = new BTree();
                    ArrayList<Persona> bruixes = new ArrayList<>();

                    for (Persona persona : persones) {
                        bTree.addPersona(persona);
                    }

                    do {
                        menu.showMessage("");
                        menu.showMessage("A. Afegir habitant");
                        menu.showMessage("B. Eliminar habitant");
                        menu.showMessage("C. Representacio visual");
                        menu.showMessage("D. Identificacio de bruixes");
                        menu.showMessage("E. Batuda");
                        menu.showMessage("");
                        menu.showMessage("F. Tornar enrere");
                        menu.showMessage("");

                        funcionalitat = menu.askForString("Quina funcionalitat vol executar? ");
                        funcionalitat = funcionalitat.toUpperCase();
                        menu.showMessage("");

                        switch (funcionalitat) {
                            case "A":
                                // afegir habitant
                                int id = menu.askForInt("Identificador de l'habitant: ");
                                String nom = menu.askForString("Nom del habitant: ");
                                float pes = menu.askForFloat("Pes del habitant: ");
                                String regne = menu.askForString("Regne de l'habitant: ");

                                bTree.addPersona(new Persona(id, nom, pes, regne));

                                menu.showMessage("");
                                menu.showMessage(nom + " ens acompanyarà a partir d’ara.");
                                menu.showMessage("");
                                break;
                            case "B":
                                // eliminar habitant
                                int idHabitant = menu.askForInt("Identificador de l'habitant: ");

                                Persona habitant= bTree.cerca_persona_id(idHabitant);

                                if(habitant==null){
                                    System.out.println("ERROR");
                                }else{
                                    bTree.deletePersona(habitant);
                                    menu.showMessage(habitant.getNom() + " ha estat transformat en un grill.");
                                }
                                break;

                            case "C":
                                // representacio visual
                                bTree.showTree();
                                break;
                            case "D":
                                // identificacio de bruixes
                                String nomObjecte = menu.askForString("Nom de l'objecte: ");
                                float pesObjecte = menu.askForFloat("Pes de l'objecte: ");
                                String tipusObjecte = menu.askForString("Tipus de l'objecte: ");
                                menu.showMessage("");
                                Persona bruixa;
                                switch (tipusObjecte){
                                    case "DUCK":
                                        bruixa= bTree.duck(pesObjecte);
                                        if(bruixa==null){
                                            System.out.println("ERROR");
                                        }else{
                                            menu.showMessage("S'ha descobert 1 bruixa!");
                                            menu.showMessage("\t* "+bruixa.getNom()+" ("+bruixa.getId()+", "+bruixa.getNomRegne()+"): "+ bruixa.getPes()+"kg");
                                            bruixes.add(bruixa);
                                        }
                                        break;
                                    case "WOOD":
                                        bruixa= bTree.wood(pesObjecte);
                                        menu.showMessage("S'ha descobert 1 bruixa!");
                                        menu.showMessage("\t* "+bruixa.getNom()+" ("+bruixa.getId()+", "+bruixa.getNomRegne()+"): "+ bruixa.getPes()+"kg");
                                        bruixes.add(bruixa);
                                        break;
                                    case "STONE":
                                        bruixa=bTree.stone(pesObjecte);
                                        menu.showMessage("S'ha descobert 1 bruixa!");
                                        menu.showMessage("\t* "+bruixa.getNom()+" ("+bruixa.getId()+", "+bruixa.getNomRegne()+"): "+ bruixa.getPes()+"kg");
                                        bruixes.add(bruixa);
                                        break;
                                    default:
                                        System.out.println("Aquest tipus d'objecte no existeix");
                                        break;
                                }
                                break;
                            case "E":
                                // batuda

                                int i;
                                float pesmin = menu.askForFloat("Pes mínim:");
                                float pesmax = menu.askForFloat("Pes màxim:");
                                ArrayList<Persona> capturades= bTree.batuda(pesmin,pesmax,bruixes);
                                menu.showMessage("");
                                menu.showMessage("S'han capturat "+capturades.size()+" bruixes!");
                                for (i=0; i<capturades.size();i++){
                                    menu.showMessage("* "+capturades.get(i).getNom()+" ("+capturades.get(i).getId()+", "+capturades.get(i).getNomRegne()+"): "+capturades.get(i).getPes()+"kg");
                                }

                                break;
                        }
                    } while (!funcionalitat.equals("F"));

                    break;
                case 3:
                    Bardissa[] bardisses = FileReader.readBardisses(FILENAME_BARDISSES);
                    RTree rTree = new RTree();

                    for (Bardissa bardissa : bardisses) {
                        rTree.agregarPuntEnNodo(bardissa);
                    }

                    do {
                        menu.showMessage("");
                        menu.showMessage("A. Afegir bardissa");
                        menu.showMessage("B. Eliminar bardissa");
                        menu.showMessage("C. Visualitzacio");
                        menu.showMessage("D. Cerca per area");
                        menu.showMessage("E. Optimitzacio estetica");
                        menu.showMessage("");
                        menu.showMessage("F. Tornar enrere");
                        menu.showMessage("");
                        funcionalitat = menu.askForString("Quina funcionalitat vol executar? ");
                        funcionalitat = funcionalitat.toUpperCase();

                        menu.showMessage("");

                        float latitud;
                        float longitud;
                        switch (funcionalitat) {
                            case "A":
                                // Afegir bardissa
                                String tipus = menu.askForString("Tipus de la bardissa: ");
                                float mida = menu.askForFloat("Mida de la bardissa: ");
                                latitud = menu.askForFloat("Latidud de la bardissa: ");
                                longitud = menu.askForFloat("Longitud de la bardissa: ");
                                String color = menu.askForString("Color de la bardissa: ");

                                rTree.agregarPuntEnNodo(new Bardissa(null, tipus, mida, new Punt(latitud, longitud), color));
                                menu.showMessage("Una nova bardissa aparegué a la Bretanya");

                                break;
                            case "B":
                                // Eliminar bardissa
                                latitud = menu.askForFloat("Latidud de la bardissa: ");
                                longitud = menu.askForFloat("Longitud de la bardissa: ");

                                rTree.eliminarBardissa(latitud, longitud);
                                menu.showMessage("La bardissa s’ha eliminat, per ser integrada a una tanca.");

                                break;
                            case "C":
                                // Visualitzacio
                                rTree.printTree();
                                break;
                            case "D":
                                // Cerca per area
                                Punt puntMax = menu.askForPunt("Entra el primer punt de l’àrea (lat,long): ");
                                Punt puntMin = menu.askForPunt("Entra el segon punt de l’àrea (lat,long): ");

                                ArrayList<Bardissa> bardissesList = rTree.cercaPerArea(puntMax, puntMin);

                                menu.showMessage("S'han trobat " + bardissesList.size() + " bardisses en aquesta area: ");
                                menu.showMessage("");
                                for (Bardissa b : bardissesList) {
                                    String type;
                                    if (b.getTipus().equals("CIRCLE")) {
                                        type = "r";
                                    } else {
                                        type = "s";
                                    }
                                    menu.showMessage("\t* " + b.getPuntMin().getX() + ", " + b.getPuntMin().getY() + ": " + b.getTipus() + "(" + type + "=" + b.getMida() + "m) " + b.getColor());
                                }

                                break;
                            case "E":
                                // Optimitzacio estetica
                                Punt punt = menu.askForPunt("Entra el punt a consultar (lat,long): ");
                                int numBardisses = menu.askForInt("Entra la quantitat de bardisses a considerar (K): ");

                                ArrayList<Bardissa> closestBardisses = rTree.OptimitzacioEstetica(punt, numBardisses);
                                String tipusMigBardisses = RTree.MitjanaTipus(closestBardisses);
                                System.out.println("Tipus majoritari: "+tipusMigBardisses);
                                String colorMigBardisses = RTree.MitjanaColor(closestBardisses);
                                System.out.println("Color mitjà: "+colorMigBardisses);

                                break;
                        }
                    } while (!funcionalitat.equals("F"));


                    break;
                case 4:
                    Acusat[] acusats = FileReader.readAcusats(FILENAME_ACUSATS);
                    HashTable hashTable = new HashTable(acusats.length);

                    for (Acusat acusat : acusats) {
                        hashTable.insert(acusat.getNom(), acusat);
                    }

                    do {

                        menu.showMessage("");
                        menu.showMessage("A. Afegir acusat");
                        menu.showMessage("B. Eliminar acusat");
                        menu.showMessage("C. Edicte de gracia");
                        menu.showMessage("D. Judici final (un acusat)");
                        menu.showMessage("E. Judici final (rang)");
                        menu.showMessage("F. Histograma per professions");
                        menu.showMessage("");
                        menu.showMessage("G. Tornar enrere");
                        menu.showMessage("");

                        funcionalitat = menu.askForString("Quina funcionalitat vol executar? ");
                        funcionalitat = funcionalitat.toUpperCase();
                        menu.showMessage("");

                        String nomAcusat;
                        switch (funcionalitat) {
                            case "A":
                                // afegir acusat
                                nomAcusat = menu.askForString("Nom de l'acusat: ");
                                int numConills = menu.askForInt("Nombre de conills vistos: ");
                                String professio = menu.askForString("Professio: ");

                                hashTable.insert(nomAcusat, new Acusat(nomAcusat, numConills, professio));

                                menu.showMessage("S’ha enregistrat un nou possible heretge.");

                                break;
                            case "B":
                                // eliminar acusat
                                nomAcusat = menu.askForString("Nom de l'acusat: ");

                                hashTable.delete(nomAcusat);

                                menu.showMessage("L’execució pública de " + nomAcusat + " ha estat un èxit.");

                                break;
                            case "C":
                                // edicte de gracia
                                nomAcusat = menu.askForString("Nom de l'acusat: ");
                                String heretgeYN = menu.askForString("Marcar com a heretge (Y/N)?");
                                boolean heretge = heretgeYN.equals("Y");

                                hashTable.marcarHeretge(nomAcusat, heretge);

                                menu.showMessage("La Inquisició Espanyola ha conclòs que " + nomAcusat + " és un heretge.");

                                break;
                            case "D":
                                // judici final (un acusat)
                                nomAcusat = menu.askForString("Nom de l'acusat: ");
                                Acusat acusatJudici = hashTable.get(nomAcusat);

                                menu.showMessage("Registre per “" + acusatJudici.getNom() + "”:");
                                menu.showMessage("\t* Nombre de conills vistos: " + acusatJudici.getConills());
                                menu.showMessage("\t* Professio: " + acusatJudici.getProfessio());
                                menu.showMessage("\t* Heretge? " + acusatJudici.isHeretge());

                                break;
                            case "E":
                                // judici final (rang)
                                int minConills = menu.askForInt("Nombre mínim de conills: ");
                                int maxConills = menu.askForInt("Nombre màxim de conills: ");

                                hashTable.judiciFinal(minConills, maxConills);

                                break;
                            case "F":
                                // histograma per professions
                                menu.showMessage("Generant histograma...");
                                menu.showMessage("");

                                hashTable.showHistogram();

                                break;
                        }
                    } while (!funcionalitat.equals("G"));

                    break;
            }
        }
        menu.showMessage("Aturant The Hashy Grail...");
    }
}
