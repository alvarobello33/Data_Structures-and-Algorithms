package graphs;

public class Trajecte implements Cloneable{
    private int idLlocA;
    private int idLlocB;
    private float tempsE;
    private float tempsA;
    private float distancia;

    private LlocInteres llocInteresA;
    private LlocInteres llocInteresB;

    private boolean mostrat;    //nomes utilitzat en apartat de mostrar trajectes Prim

    public Trajecte(int idLlocA, int idLlocB, float tempsE, float tempsA, float distancia) {
        this.idLlocA = idLlocA;
        this.idLlocB = idLlocB;
        this.tempsE = tempsE;
        this.tempsA = tempsA;
        this.distancia = distancia; //metres
        mostrat = false;
    }

    public int getIdLlocA() {
        return idLlocA;
    }

    public void setIdLlocA(int idLlocA) {
        this.idLlocA = idLlocA;
    }

    public int getIdLlocB() {
        return idLlocB;
    }

    public void setIdLlocB(int idLlocB) {
        this.idLlocB = idLlocB;
    }

    public float getTempsE() {
        return tempsE;
    }

    public void setTempsE(float tempsE) {
        this.tempsE = tempsE;
    }

    public float getTempsA() {
        return tempsA;
    }

    public void setTempsA(float tempsA) {
        this.tempsA = tempsA;
    }

    public float getDistancia() {
        return distancia;
    }

    public void setDistancia(float distancia) {
        this.distancia = distancia;
    }

    public LlocInteres getLlocInteresA() {
        return llocInteresA;
    }

    public void setLlocInteresA(LlocInteres llocInteresA) {
        this.llocInteresA = llocInteresA;
    }

    public LlocInteres getLlocInteresB() {
        return llocInteresB;
    }

    public void setLlocInteresB(LlocInteres llocInteresB) {
        this.llocInteresB = llocInteresB;
    }

    public boolean isMostrat() {
        return mostrat;
    }

    public void setMostrat(boolean mostrat) {
        this.mostrat = mostrat;
    }

    @Override
    public Trajecte clone() throws CloneNotSupportedException {
        return (Trajecte) super.clone();
    }
}
