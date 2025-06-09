package rtree;

import org.w3c.dom.css.Rect;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;



import static rtree.Fase3.getDist;

public class RTree {
    public static final int MAX_CHILDS = 3; // Máximo de 3 rectángulos por nodo
    private Rectangle rectangleArrel;

    public RTree() {
        rectangleArrel = new Rectangle(null);
    }

    public void agregarPuntEnNodo(Bardissa bardissa) {
        agregarPuntEnNodoRecursivo(bardissa, rectangleArrel);
    }

    private void agregarPuntEnNodoRecursivo(Bardissa bardissa, Rectangle rectangleActual) {

        // Si el nodo es una hoja, simplemente agregamos el punto a la lista de puntos del nodo
        if (rectangleActual.getFigures().isEmpty() || rectangleActual.getFigures().get(0) instanceof Bardissa) {
            rectangleActual.addFigure(bardissa, this);
        } else {
            // Sino, encontramos el hijo que mejor se ajuste al punto que queremos añadir
            float shorterDistance = Float.MAX_VALUE;
            Rectangle bestRectangle = null;
            for (Figure f : rectangleActual.getFigures()) {
                if (getDist(bardissa, f) < shorterDistance) {
                    shorterDistance = getDist(bardissa, f);
                    bestRectangle = (Rectangle) f;
                }
            }

            // Anadimos el punto de forma recursiva en el mejor hijo
            agregarPuntEnNodoRecursivo(bardissa, bestRectangle);
        }

    }

    /**
     * Elimina bardissa del RTree a partir de les seves coordenades
     *
     * @param x coordenada x
     * @param y coordenada y
     * @return boolean indicant si sha eliminat la bardissa del RTree
     */
    public boolean eliminarBardissa(float x, float y) {

        Bardissa b = trobarBardissa(x, y);

        //en cas que no s'hagi trobat una bardissa amb les coordenades proporcionades
        if (b == null) {
            return false;
        }

        Rectangle rectangleAnterior = b.getRectangleAnterior();

        //eliminem la figura del seu rectangle
        rectangleAnterior.getFigures().remove(b);

        //actualitzar els valors maxims i minims del rectangle
        rectangleAnterior.getPuntMax().setX(Float.MIN_VALUE);
        rectangleAnterior.getPuntMax().setY(Float.MIN_VALUE);
        rectangleAnterior.getPuntMin().setX(Float.MAX_VALUE);
        rectangleAnterior.getPuntMin().setY(Float.MAX_VALUE);

        for (Figure f : rectangleAnterior.getFigures()) {
            rectangleAnterior.updateMaxMinPoints(f);
        }

        return true;
    }

    /**
     * Troba una bardissa a partir de unes coordenades
     *
     * @param x
     * @param y
     * @return Objecte bardissa si sha trobat, null si no sha trobat
     */
    public Bardissa trobarBardissa(float x, float y) {
        Bardissa tarjetCopy = new Bardissa(null, "indefinit", 0, new Punt(x, y), "indefinit");
        return trobarBardissaRecursiu(x, y, tarjetCopy, rectangleArrel);
    }

    public Bardissa trobarBardissaRecursiu(float x, float y, Bardissa tarjetCopy, Rectangle rectangleActual) {

        if (rectangleActual.getFigures().isEmpty()) {
            return null;
        } else if (rectangleActual.getFigures().get(0) instanceof Bardissa) {
            //Comprobem si alguna de les bardisses del rectangle es la que busquem
            for (Figure f : rectangleActual.getFigures()) {
                if (f.getPuntMax().getX() == x && f.getPuntMax().getY() == y) {
                    return (Bardissa) f;
                }
            }
            return null;
        } else {

            //comprobem si algun dels rectangles fills conte la bardissa
            for (Figure f : rectangleActual.getFigures()) {
                if (f.containsFull(tarjetCopy)) {
                    //si la conte cridem una busqueda recursiva amb aquell rectangle
                    Bardissa bardissa = trobarBardissaRecursiu(x, y, tarjetCopy, (Rectangle) f);
                    if (bardissa != null) {
                        //si la hem trobat la retornem
                        return bardissa;
                    }
                }
            }
        }

        //en cas de que no s'hagi trobat la bardissa
        return null;
    }

    /**
     * Cerca totes les bardisses que es trobin dins del rectangle format per puntMax i puntMin.
     *
     * @param puntMax
     * @param puntMin
     * @return ArrayList de les bardisses trobades.
     */
    public ArrayList<Bardissa> cercaPerArea(Punt puntMax, Punt puntMin) {
        Rectangle rectangleArea = new Rectangle(null, puntMax, puntMin);
        ArrayList<Bardissa> bardisses = new ArrayList<>();
        cercaPerAreaRecursiu(rectangleArea, bardisses, rectangleArrel);
        return bardisses;
    }

    public void cercaPerAreaRecursiu(Rectangle rectangleArea, ArrayList<Bardissa> bardisses, Rectangle rectangleActual) {

        if (rectangleActual.getFigures().isEmpty()) {
            //no fer res
        } else if (rectangleActual.getFigures().get(0) instanceof Bardissa) {
            //Comprobem si alguna de les bardisses del rectangle es la que busquem
            for (Figure f : rectangleActual.getFigures()) {
                if (rectangleArea.containsFull(f)) {
                    //en cas de ser-ho la guardem
                    bardisses.add((Bardissa) f);
                }
            }
        } else {

            //comprobem si algun dels rectangles fills conte alguna part dins del area de cerca
            for (Figure f : rectangleActual.getFigures()) {
                if (rectangleArea.contains(f)) {
                    //si la conte cridem una busqueda recursiva amb aquell rectangle
                    cercaPerAreaRecursiu(rectangleArea, bardisses, (Rectangle) f);

                }
            }
        }

    }

    public Rectangle getRectangleArrel() {
        return rectangleArrel;
    }

    public void setRectangleArrel(Rectangle rectangleArrel) {
        this.rectangleArrel = rectangleArrel;
    }

    /**
     * Metode que mostra l'arbre R per consola
     */
    public void printTree() {
        printTreeRecursive(rectangleArrel, "", true);
    }

    /**
     * Metode privat recursiu que crida recursivament per imprimir l'arbre per consola
     *
     * @param rectangle
     * @param prefix
     * @param isLast
     */
    private void printTreeRecursive(Rectangle rectangle, String prefix, boolean isLast) {
        // mostra la informacio del rectangle
        mostraRectangle(prefix, isLast, rectangle);

        if (!rectangle.getFigures().isEmpty()) {
            int lastChildIndex = rectangle.getFigures().size() - 1;  // ens guardem l'index de l'ultima figura
            for (int i = 0; i <= lastChildIndex; i++) {
                Figure figure = rectangle.getFigures().get(i);
                String newPrefix;
                // depenen de si era l'ultim fill o no, muntem part del string que es mostrara mes endevant
                if (isLast) {
                    newPrefix = prefix + "    ";
                } else {
                    newPrefix = prefix + "│   ";
                }
                boolean isLastChild = i == lastChildIndex;  // boolean per saber si estem a la posicio de l'ultim fill o no per passar-ho com a parametre a la funcio recursiva
                if (figure instanceof Rectangle) {
                    // si es un rectangle, cridem recursivament a la funcio per seguir mostrant informacio del rectangle
                    printTreeRecursive((Rectangle) figure, newPrefix, isLastChild);
                } else {
                    // sino es un rectangle, sera una bardissa, per tant la mostrem per consola i deixem de cridar la funcio recursiva, ja que al ser fulla no te mes fills
                    mostraBardissa(newPrefix, isLastChild, (Bardissa) figure);
                }
            }
        }
    }

    /**
     * Metode que mostra per pantalla la informacio d'un rectangle
     *
     * @param prefix
     * @param isLast
     * @param rectangle
     */
    private void mostraRectangle(String prefix, boolean isLast, Rectangle rectangle) {
        System.out.print(prefix);
        if (rectangle.getRectangleAnterior() == null) {
            System.out.print("└── "); // mostra indicador de l'ultim rectangle del nivell
        } else {
            if (isLast) {
                System.out.print("└── "); // mostra indicador de l'ultima bardissa del nivell
            } else {
                System.out.print("├── "); // mostra indicador de d'una bardissa del nivell
            }
        }
        String figura;
        if (rectangle.getFigures().get(0) instanceof Rectangle) {
            figura = "rectangles";
        } else {
            figura = "bardisses";
        }
        System.out.println(rectangle.getFigures().size() + " " + figura + ". Rectangle actual: [(" + rectangle.getPuntMin().getX() + ", " + rectangle.getPuntMin().getY() + "), (" + rectangle.getPuntMax().getX() + ", " + rectangle.getPuntMax().getY() + ")]");
    }

    /**
     * Metode que mostra per pantalla una bardissa
     *
     * @param prefix
     * @param isLast
     * @param bardissa
     */
    private void mostraBardissa(String prefix, boolean isLast, Bardissa bardissa) {
        System.out.print(prefix);
        if (isLast) {
            System.out.print("└── ");
        } else {
            System.out.print("├── ");
        }
        System.out.println("Punt (" + bardissa.getPuntMin().getX() + ", " + bardissa.getPuntMin().getY() + ")");
    }


    //OPTIMIZACIÓ ESTÈTICA

    private float getDistanciaPunts(Punt p1, Punt p2) {
        float dx = p1.getX() - p2.getX();
        float dy = p1.getY() - p2.getY();
        return (float) Math.sqrt(dx * dx + dy * dy);
    }

    public ArrayList<Bardissa> OptimitzacioEstetica(Punt punt, int k) {
        ArrayList<Bardissa> closestBardisses = new ArrayList<>();

        // Obtenim totes les bardisses del rectangle arrel
        ArrayList<Bardissa> allBardisses = new ArrayList<>();
        GetBardissesFromAllRectangles(rectangleArrel, allBardisses);

        // Ordenem les bardisses per distancia al punt donat
        Collections.sort(allBardisses, new Comparator<Bardissa>() {
            @Override
            public int compare(Bardissa b1, Bardissa b2) {
                float dist1 = getDistanciaPunts(b1.getPuntMax(), punt);
                float dist2 = getDistanciaPunts(b2.getPuntMax(), punt);
                return Float.compare(dist1, dist2);
            }
        });

        // Afegim les K bardisses més properes
        int count = 0;
        for (Bardissa bardissa : allBardisses) {
            closestBardisses.add(bardissa);
            count++;
            if (count >= k) {
                break;
            }
        }

        return closestBardisses;
    }

    private void GetBardissesFromAllRectangles(Rectangle rectangle, ArrayList<Bardissa> bardisses) {
        if (rectangle == null) {
            return;
        }

        for (int i = 0; i < rectangle.getFigures().size(); i++) {
            if(rectangle.getFigures().get(i) instanceof Bardissa){
                bardisses.add((Bardissa)rectangle.getFigures().get(i));
            }

        }

        // Recorrer els rectangles fills
        for (int i = 0; i < rectangle.getFigures().size(); i++) {
            if(rectangle.getFigures().get(i) instanceof Rectangle){
                GetBardissesFromAllRectangles((Rectangle)rectangle.getFigures().get(i), bardisses);
            }

        }


    }

    public static String MitjanaTipus(ArrayList<Bardissa> bardisses) {
        int circleCount = 0;
        int squareCount = 0;

        // Calcula el número de bardisses de tipo "CIRCLE" y "SQUARE"
        for (Bardissa bardissa : bardisses) {
            if (bardissa.getTipus().equals("CIRCLE")) {
                circleCount++;
            } else if (bardissa.getTipus().equals("SQUARE")) {
                squareCount++;
            }
        }

        // Determina el tipus majoritari de les bardisses
        if (circleCount > squareCount) {
            return "CIRCLE";
        } else if (squareCount > circleCount) {
            return "SQUARE";
        } else {
            return "NONE";
        }
    }

    public static String MitjanaColor(ArrayList<Bardissa> bardisses) {
        int redSum = 0;
        int greenSum = 0;
        int blueSum = 0;

        // Verificar si la llista bardisses està buida abans de calcular
        if (bardisses.isEmpty()) {
            return "#000000"; // Retornar un color predeterminat en cas de llista buida
        }

        // Suma els valors rgb de les bardisses (colors red, green, blue)
        for (Bardissa bardissa : bardisses) {
            String color = bardissa.getColor();
            int red = Integer.parseInt(color.substring(1, 3), 16);
            int green = Integer.parseInt(color.substring(3, 5), 16);
            int blue = Integer.parseInt(color.substring(5, 7), 16);

            redSum += red;
            greenSum += green;
            blueSum += blue;
        }


        // Calcula el valor mig dels colors RGB (red, green, blue)
        int averageRed = redSum / bardisses.size();
        int averageGreen = greenSum / bardisses.size();
        int averageBlue = blueSum / bardisses.size();

        // Color en format hexadecimal
        return String.format("#%02X%02X%02X", averageRed, averageGreen, averageBlue);
    }
}






