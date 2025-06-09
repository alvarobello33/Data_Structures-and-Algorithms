package taules;
import java.util.Hashtable;
public class Acusat {

    private String nom;
    private int conills;
    private String professio;
    private boolean heretge;

    public Acusat (String nom, int conills, String professio){
        this.nom = nom;
        this.conills = conills;
        this.professio = professio;
        heretge = false;
    }

    public String getNom() {
        return nom;
    }

    public int getConills() {
        return conills;
    }

    public String getProfessio() {
        return professio;
    }

    public boolean isHeretge() {
        return heretge;
    }

    public void setHeretge(boolean heretge) {
        this.heretge = heretge;
    }
}
