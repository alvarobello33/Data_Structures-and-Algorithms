package rtree;

import java.util.ArrayList;

public class Rectangle extends Figure {
    private Punt puntMax;
    private Punt puntMin;

    private ArrayList<Figure> figures;

    public Rectangle(Rectangle rectangleAnterior) {
        super(rectangleAnterior);
        figures = new ArrayList<>();

        puntMax = new Punt(Float.MIN_VALUE, Float.MIN_VALUE);
        puntMin = new Punt(Float.MAX_VALUE, Float.MAX_VALUE);
    }

    public Rectangle(Rectangle rectangleAnterior, Punt puntMax, Punt puntMin) {
        super(rectangleAnterior);
        figures = new ArrayList<>();

        this.puntMax = puntMax;
        this.puntMin = puntMin;
    }

    @Override
    public Punt getPuntMax() {
        return puntMax;
    }

    @Override
    public Punt getPuntMin() {
        return puntMin;
    }

    public void setPuntMax(Punt puntMax) {
        this.puntMax = puntMax;
    }

    public void setPuntMin(Punt puntMin) {
        this.puntMin = puntMin;
    }


    public void addFigure(Figure figure, RTree rTree) {

        updateMaxMinPoints(figure);

        figures.add(figure);
        figure.setRectangleAnterior(this);


        //actualitzar parametres dels rectangles anteriors
        Rectangle rectangle = this;
        while (rectangle.getRectangleAnterior() != null) {
            rectangle.getRectangleAnterior().updateMaxMinPoints(rectangle);
            rectangle = rectangle.getRectangleAnterior();
        }


        // si hi ha mes de 3 bardisses, haurem de crear un nou rectangle
        if (figures.size() > RTree.MAX_CHILDS) {

            // guardem les dos figures (bardissa o rectangle) amb mes distancia entre elles
            Figure[] longestFigures = Fase3.getLongestFigures(figures);


            // guardem les figures del rectangle que hem de dividir en una variable auxiliar
            ArrayList<Figure> figuresClone = (ArrayList<Figure>) figures.clone();

            // esborrem les figures del rectangle actual
            clear();


            // afegim en el rectangle actual i en el nou rectangle les figures amb mes distancia trobades anteriorment
            // al fer updateMaxMinPoints avans, l'arraylist de figures estara buit i s'agafaran els marges de la figura introduida
            Rectangle newRectangle = new Rectangle(this);

            updateMaxMinPoints(longestFigures[0]);
            figures.add(longestFigures[0]);
            newRectangle.updateMaxMinPoints(longestFigures[1]);
            newRectangle.addFigure(longestFigures[1], rTree);


            // anem afegint les figures restants al rectangle mes adequat
            for (Figure f: figuresClone) {
                if (!f.equals(longestFigures[0]) && !f.equals(longestFigures[1])) {
                    //comprobem quin rectangle te mes a prop
                    if (Fase3.getDist(f, this) < Fase3.getDist(f, newRectangle)) {
                        addFigure(f, rTree);
                        //updateMaxMinPoints(f);
                    } else {
                        newRectangle.addFigure(f, rTree);
                        //newRectangle.updateMaxMinPoints(f);
                    }

                }
            }

            //ENLLAZAR NOU RECTANGLE AMB RTREE

            //en cas de que el rectangle actual sigui el rectangle arrel
            if (this.getRectangleAnterior() == null) {
                // creem nou rectangle com arrel i afegim el rectangle actual
                rTree.setRectangleArrel(new Rectangle(null));
                //rTree.getRectangleArrel().updateMaxMinPoints(this);
                rTree.getRectangleArrel().addFigure(this, rTree);
                //setRectangleAnterior(rTree.getRectangleArrel());
            }

            // al rectangle anterior del actual li afegim un nou rectangle
            this.getRectangleAnterior().addFigure(newRectangle, rTree);
        }
    }

    /**
     * Actualitza els punts maxims i minims del rectangle en
     * cas que la figura proporcionada surti d'aquests marges.
     * @param figure figura proporcionada
     */
    public void updateMaxMinPoints(Figure figure) {
        if (figures.isEmpty()) {
            //si el rectangle no te fills
            puntMax.setX(figure.getPuntMax().getX());
            puntMin.setX(figure.getPuntMin().getX());
            puntMax.setY(figure.getPuntMax().getY());
            puntMin.setY(figure.getPuntMin().getY());

        } else {
            if (puntMax.getY() < figure.getPuntMax().getY()) {
                puntMax.setY(figure.getPuntMax().getY());
            } else if (puntMin.getY() > figure.getPuntMin().getY()) {
                puntMin.setY(figure.getPuntMin().getY());
            }

            if (puntMax.getX() < figure.getPuntMax().getX()) {
                puntMax.setX(figure.getPuntMax().getX());
            } else if (puntMin.getX() > figure.getPuntMin().getX()) {
                puntMin.setX(figure.getPuntMin().getX());
            }
        }
    }


    /**
     * Ens indica si la figura proporcionada esta completament continguda dins del rectangle.
     * @param f figura proporcionada
     * @return si tota la figura es troba dins del rectangle
     */
    @Override
    public boolean containsFull(Figure f) {
        //comprobamos que el punto maximo de la figura se encuentre dentro del rectangulo
        if (f.getPuntMax().getX() >= getPuntMin().getX() && f.getPuntMax().getX() <= getPuntMax().getX()) {
            if (f.getPuntMax().getY() >= getPuntMin().getY() && f.getPuntMax().getY() <= getPuntMax().getY()) {
                //comprobamos que el punto minimo de la figura se encuentre dentro del rectangulo
                if (f.getPuntMin().getX() >= getPuntMin().getX() && f.getPuntMin().getX() <= getPuntMax().getX()) {
                    if (f.getPuntMin().getY() >= getPuntMin().getY() && f.getPuntMin().getY() <= getPuntMax().getY()) {
                        return true;
                    }
                }
            }
        }

        return false;
    }

    /**
     * Ens indica si la figura proporcionada esta parcialment continguda dins del rectangle.
     * @param f figura proporcionada
     * @return si alguna part de la figura es troba dins del rectangle
     */
    @Override
    public boolean contains(Figure f) {
        boolean dentroEnX = f.getPuntMax().getX() <= getPuntMax().getX() && f.getPuntMax().getX() >= getPuntMin().getX()
                || f.getPuntMin().getX() <= getPuntMax().getX() && f.getPuntMin().getX() >= getPuntMin().getX()
                || f.getPuntMin().getX() <= getPuntMin().getX() && f.getPuntMax().getX() >=  getPuntMax().getX();

        // Verificar si f está dentro de r1 en el eje y
        boolean dentroEnY = f.getPuntMax().getY() <=  getPuntMax().getY() && f.getPuntMax().getY() >=  getPuntMin().getY()
                || f.getPuntMin().getY() <=  getPuntMax().getY() && f.getPuntMin().getY() >=  getPuntMin().getY()
                || f.getPuntMin().getY() <=  getPuntMin().getY() && f.getPuntMax().getY() >=  getPuntMax().getY();

        // Si r2 está dentro de r1 en ambos ejes, entonces r1 contiene parte de r2
        return dentroEnX && dentroEnY;
    }

    public void clear() {
        figures.clear();
        getPuntMax().setX(Float.MIN_VALUE);
        getPuntMax().setY(Float.MIN_VALUE);
        getPuntMin().setX(Float.MAX_VALUE);
        getPuntMin().setY(Float.MAX_VALUE);
    }

    public ArrayList<Figure> getFigures() {
        return figures;
    }

    public void setFigures(ArrayList<Figure> figures) {
        this.figures = figures;
    }
}
