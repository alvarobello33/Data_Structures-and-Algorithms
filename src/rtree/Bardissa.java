package rtree;

public class Bardissa extends Figure {
    private String tipus;
    private float mida;
    private Punt punt;
    private String color;


    public Bardissa(Rectangle rectangleAnterior, String tipus, float mida, Punt punt, String color) {
        super(rectangleAnterior);
        this.tipus = tipus;
        this.mida = mida;
        this.punt = punt;
        this.color = color;
    }

    public String getTipus() {
        return tipus;
    }

    public void setTipus(String tipus) {
        this.tipus = tipus;
    }

    public float getMida() {
        return mida;
    }

    public void setMida(float mida) {
        this.mida = mida;
    }

    @Override
    public Punt getPuntMax() {
        return punt;
    }

    @Override
    public Punt getPuntMin() {
        return punt;
    }

    public void setPunt(Punt punt) {
        this.punt = punt;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

}
