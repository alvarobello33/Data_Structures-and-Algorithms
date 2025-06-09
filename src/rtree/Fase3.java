package rtree;

import java.util.ArrayList;

public class Fase3 {
    public static Figure[] getLongestFigures(ArrayList<Figure> figures) {
        float maxDist = Integer.MIN_VALUE;
        Figure[] longestFigures = new Figure[2];

        for (int i = 0; i < figures.size(); i++) {
            for (int j = 0; j < figures.size(); j++) {
                if (i != j) {
                    float dist = getDist(figures.get(i), figures.get(j));
                    if (dist > maxDist) {
                        maxDist = dist;
                        longestFigures[0] = figures.get(i);
                        longestFigures[1] = figures.get(j);
                    }
                }
            }
        }

        return longestFigures;
    }

    /**
     * Calcula la distancia entre els punts centrals de dues figures.
     * @param f1
     * @param f2
     * @return 0 si una figura es troba dins d'una altre, sino la distancia entre les figures
     */
    public static float getDist(Figure f1, Figure f2)  {
        //si una figura es troba dins de laltre
        if (f1.containsFull(f2) || f2.containsFull(f1)) {
            return 0;
        }
        //sino calculem la distancia entre els seus varicentres
        return (float) Math.sqrt(Math.pow((f1.getPuntMax().getY() - f2.getPuntMin().getY()), 2) + Math.pow((f1.getPuntMax().getX() - f2.getPuntMax().getX()), 2));
    }





}
