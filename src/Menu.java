import graphs.Graph;
import graphs.LlocInteres;
import rtree.Punt;

import java.util.ArrayList;
import java.util.Scanner;

public class Menu {
    private static Scanner scanner;

    public Menu() {
        scanner = new Scanner(System.in);
    }

    /**
     * Funcio que mostra el menu inicial
     */
    public void showMenu() {
        System.out.println();
        System.out.println("1. Sobre orenetes i cocos (Grafs)");
        System.out.println("2. Caça de bruixes (Arbres binaris de cerca)");
        System.out.println("3. Tanques de bardissa (Arbres R)");
        System.out.println("4. D’heretges i blasfems (Taules)");
        System.out.println();
        System.out.println("5. Exit");
    }

    /**
     * Funcio que demana un enter a l'usuari
     * @param message
     * @return int
     */
    public int askForInt(String message) {
        System.out.print(message);
        String aux = scanner.nextLine();
        return Integer.parseInt(aux);
    }

    /**
     * Funcio que demana un valor decimal i el retorna
     * @param message
     * @return float
     */
    public Float askForFloat(String message) {
        System.out.print(message);
        String aux = scanner.nextLine();
        return Float.parseFloat(aux);
    }

    /**
     * Funcio que demana a l'usuari que introdueixi una cadena de caracters
     * @param message
     * @return String
     */
    public String askForString(String message) {
        System.out.print(message);
        return scanner.nextLine();
    }

    /**
     * Funcio que rep un string per parametre i el mostra per consola
     * @param message
     */
    public void showMessage(String message) {
        System.out.println(message);
    }

    /**
     * Funcio que mostra els llocs d'interes adjacents a un lloc en concret
     * @param graph
     * @param id del lloc d'interes
     */
    public void mostraAdjacents(Graph graph, int id) {
        // Mostra adjacents
        ArrayList<LlocInteres> adjacents = graph.adjacents(graph.getLlocSegonsId(id));
        System.out.println("Adjacents del id " + id);
        for (int i = 0; i < adjacents.size(); i++) {
            System.out.println("    - " + adjacents.get(i).getId());
        }
    }

    /**
     * Funcio que demana si la oreneta porta un coco o no pel trajecte i retorna true o false
     * @param s
     * @return boolea, si porta coco o si no porta
     */
    public boolean askOrenetaCoco(String s) {
        while (true) {
            System.out.print(s);
            String answer = scanner.nextLine().toUpperCase();
            if (answer.equals("SI")) {
                return true;
            } else if (answer.equals("NO")) {
                return false;
            } else {
                System.out.println("Error: Introdueix “SI” o “NO”.");
            }
        }
    }

    /**
     * Funcio que demana a l'usuari que introdueixi dues coordenades (Punt)
     * @param s
     * @return Punt amb les coordenades x i y
     */
    public Punt askForPunt(String s) {
        System.out.println(s);
        String[] pos = scanner.nextLine().split(",");
        return new Punt(Float.parseFloat(pos[0]), Float.parseFloat(pos[0]));
    }
}
