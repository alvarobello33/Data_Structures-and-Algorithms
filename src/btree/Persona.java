package btree;

public class Persona {
    private int id;
    private String nom;
    private float pes;
    private String nomRegne;

    private Persona personaL;
    private Persona personaR;
    private Persona personaAnterior;
    private int profunditat;

    public Persona(int id, String nom, float pes, String nomRegne) {
        this.id = id;
        this.nom = nom;
        this.pes = pes;
        this.nomRegne = nomRegne;
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

    public float getPes() {
        return pes;
    }

    public void setPes(float pes) {
        this.pes = pes;
    }

    public String getNomRegne() {
        return nomRegne;
    }

    public void setNomRegne(String nomRegne) {
        this.nomRegne = nomRegne;
    }

    public Persona getPersonaL() {
        return personaL;
    }

    public void setPersonaL(Persona personaL) {
        this.personaL = personaL;
    }

    public Persona getPersonaR() {
        return personaR;
    }

    public void setPersonaR(Persona personaR) {
        this.personaR = personaR;
    }

    public int getProfunditat() {
        return profunditat;
    }

    public void setProfunditat(int profunditat) {
        this.profunditat = profunditat;
    }

    public Persona getPersonaAnterior() {
        return personaAnterior;
    }

    public void setPersonaAnterior(Persona personaAnterior) {
        this.personaAnterior = personaAnterior;
    }


    public boolean esFulla() {
        return getPersonaR() == null && getPersonaL() == null;
    }

    public Persona majorFill() {
        Persona actual = this;
        while (actual.getPersonaR() != null) {
            actual = actual.getPersonaR();
        }

        return actual;
    }

    public Persona menorFill() {
        Persona actual = this;
        while (actual.getPersonaL() != null) {
            actual = actual.getPersonaL();
        }

        return actual;
    }


}
