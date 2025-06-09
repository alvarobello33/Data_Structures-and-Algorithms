package taules;
import java.util.ArrayList;
import java.util.Hashtable;
import java.util.LinkedList;
import java.util.Objects;

public class HashTable {

    public String KING = "KING";
    public String QUEEN = "QUEEN";
    public String CLERGYMAN = "CLERGYMAN";
    private int size;
    private ArrayList<ArrayList<Acusat>> hashTable;

    public HashTable(int size){

        hashTable = new ArrayList<>();
        this.size = (int) (size/0.7);

        for (int i = 0; i < this.size; i++) {
            hashTable.add(new ArrayList<>());
        }
    }

    private int hashFunction(String key) {
        int hash = 0;

        for (char c : key.toCharArray()) {
            hash += Math.pow(2, (int) c);
        }

        return hash % size;
    }

    public void insert(String key, Acusat value) {
        int index = hashFunction(key);
        hashTable.get(index).add(value);
    }

    public Acusat get(String key) {
        int index = hashFunction(key);
        for (Acusat a:hashTable.get(index)) {
            if (a.getNom().equals(key)) {
                return a;
            }
        }
        return null;
    }

    public void delete(String key) {
        int index = hashFunction(key);
        for (Acusat a:hashTable.get(index)) {
            if (a.getNom().equals(key)) {
                hashTable.get(index).remove(a);
            }
        }
    }

    public void marcarHeretge(String key, boolean heretge) {
        Acusat a = get(key);
        if (!a.getProfessio().equals(KING) && !a.getProfessio().equals(QUEEN) && !a.getProfessio().equals(CLERGYMAN)) {
                a.setHeretge(heretge);
        }
    }

    public int size() {
        return hashTable.size();
    }

    public void judiciFinal(int minConills, int maxConills) {
        for (ArrayList<Acusat> acusats : hashTable) {
            for (Acusat acusat : acusats) {
                if (minConills <= acusat.getConills() && acusat.getConills() <= maxConills) {
                    System.out.println("Acusat: " + acusat.getNom());
                    System.out.println("\t* Nombre de conills vistos: " + acusat.getConills());
                    System.out.println("\t* Professio: " + acusat.getProfessio());
                    System.out.println("\t* Heretge? " + acusat.isHeretge());
                }
            }
        }
    }

    public void showHistogram() {
        for (ArrayList<Acusat> acusats : hashTable) {
            for (Acusat acusat : acusats) {
                System.out.println("Acusat: " + acusat.getNom());
                System.out.println("\t* Nombre de conills vistos: " + acusat.getConills());
                System.out.println("\t* Professio: " + acusat.getProfessio());
                System.out.println("\t* Heretge? " + acusat.isHeretge());
            }
        }
    }

}
