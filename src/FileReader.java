import graphs.Graph;
import graphs.LlocInteres;
import graphs.Trajecte;
import rtree.Bardissa;
import rtree.Punt;
import btree.Persona;
import taules.Acusat;

import java.io.File;
import java.io.FileNotFoundException;
import java.nio.file.Path;
import java.util.*;

public class FileReader {

    private static final String GRAPHS_PATH = "Datasets/Graphs/";
    private static final String BTREE_PATH = "Datasets/Trees/";
    private static final String RTREE_PATH = "Datasets/R-Trees/";

    private static final String SEPARATOR = ";";
    public static LlocInteres[] llocs;
    public static Trajecte[] trajectes;
    public static Graph[] graph;

    private static Persona[] persones;
    private static Bardissa[] bardisses;

    public static Acusat[] acusats;

    /**
     * Funcio que llegeix els llocs d'interes del fitxer i els retorna en un array estatic
     * @param fileName
     * @return
     */
    public static LlocInteres[] readLlocsInteres(String fileName) {
        try {
            Scanner myReader = new Scanner(new File(Path.of(GRAPHS_PATH + fileName).toUri()));
            int numLlocs = Integer.parseInt(myReader.nextLine());
            llocs = new LlocInteres[numLlocs];
            graph = new Graph[numLlocs];
            for (int i = 0; i < numLlocs; i++) {
                String line = myReader.nextLine();
                llocs[i] = llocInteresFromTxt(line);
            }
            myReader.close();
            return llocs;
        } catch (FileNotFoundException e) {
            System.out.println("Error: File “" + fileName + "” not found in path “" + GRAPHS_PATH + "”");
            return null;
        }
    }

    /**
     * Funcio que transforma un string en l'objecte LlocInteres i el retorna
     * @param txt
     * @return
     */
    private static LlocInteres llocInteresFromTxt(String txt) {
        String[] parts = txt.split(SEPARATOR);

        return new LlocInteres(
                Integer.parseInt(parts[0]),
                parts[1],
                parts[2],
                parts[3]

        );
    }

    /**
     * Funcio que llegeix els trajectes del fitxer i els retorna en un array estatic
     * @param fileName
     * @return
     */
    public static Trajecte[] readTrajectes(String fileName) {
        try {
            Scanner myReader = new Scanner(new File(Path.of("Datasets/Graphs/" + fileName).toUri()));
            int numLlocsInteres = Integer.parseInt(myReader.nextLine());
            for (int i = 0; i < numLlocsInteres; i++) {
                myReader.nextLine();
            }
            int numTrajectes = Integer.parseInt(myReader.nextLine());
            trajectes = new Trajecte[numTrajectes];
            for (int i = 0; i < numTrajectes; i++) {
                String line = myReader.nextLine();
                trajectes[i] = trajectesFromTxt(line);
            }
            myReader.close();
            return trajectes;
        } catch (FileNotFoundException e) {
            System.out.println("Error: File “" + fileName + "” not found in path “" + GRAPHS_PATH + "”");
            return null;
        }
    }

    /**
     * Funcio que transforma un string en l'objecte Trajecte i el retorna
     * @param txt
     * @return
     */
    private static Trajecte trajectesFromTxt(String txt) {
        String[] parts = txt.split(SEPARATOR);

        return new Trajecte(
                Integer.parseInt(parts[0]),
                Integer.parseInt(parts[1]),
                Float.parseFloat(parts[2]),
                Float.parseFloat(parts[3]),
                Float.parseFloat(parts[4])
        );
    }


    /**
     * Funcio que llegeix les persones del fitxer i les retorna en un array estatic
     * @param fileName
     * @return
     */
    public static Persona[] readPersones(String fileName) {
        try {
            Scanner myReader = new Scanner(new File(Path.of("Datasets/Trees/" + fileName).toUri()));
            int numPersones = Integer.parseInt(myReader.nextLine());
            persones = new Persona[numPersones];
            for (int i = 0; i < numPersones; i++) {
                String line = myReader.nextLine();
                persones[i] = personesFromTxt(line);
            }
            myReader.close();
            return persones;
        } catch (FileNotFoundException e) {
            System.out.println("Error: File (" + fileName + ") not found.");
            return null;
        }
    }

    /**
     * Funcio que transforma un string en l'objecte Persona i el retorna
     * @param line
     * @return
     */
    private static Persona personesFromTxt(String line) {
        String[] parts = line.split(SEPARATOR);

        return new Persona(
                Integer.parseInt(parts[0]),
                parts[1],
                Float.parseFloat(parts[2]),
                parts[3]
        );

    }

    /**
     * Funcio que llegeix les bardisses del fitxer i les retorna en un array estatic
     * @param fileName
     * @return
     */
    public static Bardissa[] readBardisses(String fileName) {
        try {
            Scanner myReader = new Scanner(new File(Path.of("Datasets/R-Trees/" + fileName).toUri()));
            int numBardisses = Integer.parseInt(myReader.nextLine());
            bardisses = new Bardissa[numBardisses];
            for (int i = 0; i < numBardisses; i++) {
                String line = myReader.nextLine();
                bardisses[i] = bardissesFromTxt(line);
            }
            myReader.close();
            return bardisses;
        } catch (FileNotFoundException e) {
            System.out.println("Error: File (" + fileName + ") not found.");
            return null;
        }
    }

    /**
     * Funcio que transforma un string en l'objecte Bardissa i el retorna
     * @param line
     * @return
     */
    private static Bardissa bardissesFromTxt(String line) {
        String[] parts = line.split(SEPARATOR);

        return new Bardissa(
                null,
                parts[0],
                Float.parseFloat(parts[1]),
                new Punt(Float.parseFloat(parts[3]), Float.parseFloat(parts[2])),
                parts[4]
        );

    }

    /**
     * Funcio que llegeix els acusats del fitxer i els retorna en un array estatic
     * @param fileName
     * @return
     */
    public static Acusat[] readAcusats(String fileName){
        try {
            Scanner myReader = new Scanner(new File(Path.of("Datasets/Tables/" + fileName).toUri()));
            int numAcusats = Integer.parseInt(myReader.nextLine());
            acusats = new Acusat[numAcusats];
            for (int i = 0; i < numAcusats; i++) {
                String line = myReader.nextLine();
                acusats[i] = acusatsFromTxt(line);
            }
            myReader.close();
            return acusats;
        } catch (FileNotFoundException e) {
            System.out.println("Error: File (" + fileName + ") not found.");
            return null;
        }

    }

    /**
     * Funcio que transforma un string en l'objecte Acusat i el retorna
     * @param line
     * @return
     */
    private static Acusat acusatsFromTxt(String line) {
        String[] parts = line.split(SEPARATOR);

        return new Acusat(
                parts[0],
                Integer.parseInt(parts[1]),
                parts[2]
        );
    }
}
