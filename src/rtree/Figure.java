package rtree;

public class Figure {

    private Rectangle rectangleAnterior;

    public Figure(Rectangle rectangleAnterior) {
        this.rectangleAnterior = rectangleAnterior;
    }

    public Rectangle getRectangleAnterior() {
        return rectangleAnterior;
    }

    public void setRectangleAnterior(Rectangle rectangleAnterior) {
        this.rectangleAnterior = rectangleAnterior;
    }

    public Punt getPuntMax(){return null;}
    public Punt getPuntMin(){return null;}

    public boolean containsFull(Figure f) {return false;}
    public boolean contains(Figure f) {return false;}

    public boolean equals(Figure figure) {

        // si el punt max es igual de figure i la instancia actual
        if (figure.getPuntMax().getY() == this.getPuntMax().getY() && figure.getPuntMax().getX() == this.getPuntMax().getX()) {
            // si el punt max es igual de figure i la instancia actual
            if (figure.getPuntMin().getY() == this.getPuntMin().getY() && figure.getPuntMin().getX() == this.getPuntMin().getX()) {
                return true;
            }
        }
        return false;
    }

}
