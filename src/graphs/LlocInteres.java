package graphs;

import java.util.ArrayList;

public class LlocInteres implements Cloneable{

    public static final String POLAR = "POLAR";
    public static final String CONTINENTAL = "CONTINENTAL";
    public static final String TROPICAL = "TROPICAL";
    private int id;
    private String nom;
    private String regne;
    private String clima;
    private ArrayList<Trajecte> trajectes;
    private boolean visitat;

    public LlocInteres(int id, String nom, String regne, String clima) {
        this.id = id;
        this.nom = nom;
        this.regne = regne;
        this.clima = clima;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public String getRegne() {
        return regne;
    }

    public void setRegne(String regne) {
        this.regne = regne;
    }

    public String getClima() {
        return clima;
    }

    public void setClima(String clima) {
        this.clima = clima;
    }

    public ArrayList<Trajecte> getTrajectes() {
        return trajectes;
    }

    public void setTrajectes(ArrayList<Trajecte> trajectes) {
        this.trajectes = trajectes;
    }

    public boolean isVisitat() {
        return visitat;
    }

    public void setVisitat(boolean visitat) {
        this.visitat = visitat;
    }

    public void cleanTrajectes() {
        trajectes.clear();
    }
    @Override
    public LlocInteres clone() throws CloneNotSupportedException {
        return (LlocInteres) super.clone();
    }

    public void addTrajecte(Trajecte trajecte) {
        trajectes.add(trajecte);
    }
}
