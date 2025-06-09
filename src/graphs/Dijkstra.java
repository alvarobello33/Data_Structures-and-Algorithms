package graphs;

import graphs.Graph;
import graphs.LlocInteres;
import graphs.Trajecte;

import java.util.ArrayList;

public class Dijkstra {

    private static ArrayList<Trajecte> millorTrajecte;
    private static float millorDistancia;
    private static float millorTemps;
    private static String tipusOreneta;

    public static ArrayList<Trajecte> dijkstraMillorTrajecte(Graph graph, int id_origen, int id_desti, boolean coco) {
        //si te coco no pot fer mes de 50km
        //africanes nomes poden recorre clima continental i tropical
        //europea -> continental i polar

        //Dir quin es mes eficient dels dos (europeas o africanes)
        millorDistancia = Float.MAX_VALUE;
        millorTemps = Float.MAX_VALUE;
        dijkstraEuropeas(graph, id_origen, id_desti, coco);
        dijkstraAfricanes(graph, id_origen, id_desti, coco);
        return millorTrajecte;

    }

    private static ArrayList<Trajecte> dijkstraEuropeas(Graph graph, int id_origen, int id_desti, boolean coco) {
        //si te coco no pot fer mes de 50km
        //africanes nomes poden recorre clima continental i tropical
        //europea -> continental i polar

        //distancies entre node origen i cada node
        float[] distancies = new float[graph.length()];

        //temps que triga la oreneta entre node origen i cada node
        float[] times = new float[graph.length()];

        //llista de rutes desde inici fins a cada node
        ArrayList<ArrayList<Trajecte>> camins = new ArrayList<>();
        for (int i=0; i<graph.length(); i++) {
            camins.add(new ArrayList<Trajecte>());
        }

        //posem totes les distancies i temps a infinit
        for (int i=0; i<graph.length(); i++) {
            distancies[i] = Float.MAX_VALUE;
            times[i] = Float.MAX_VALUE;
        }

        //posem distancia (i temps) entre node origen i node origen a 0
        int index_origen = graph.getQueue().indexOf(graph.searchForID(id_origen));
        distancies[index_origen] = 0;
        times[index_origen] = 0;
        //guardem index del node desti
        int index_desti = graph.getQueue().indexOf(graph.searchForID(id_desti));

        //Asignem lloc origen
        LlocInteres actual = graph.getNode(index_origen);
        int index_actual = graph.getQueue().indexOf(actual);

        //comprobem que el node final encara no sha visitat (i que queden nodes per visitar)
        while (distancies[index_desti] == Float.MAX_VALUE && !graph.totsNodesNoTropicalsVisitats()) {
            //Recorrem tots els nodes adjacents al actual
            ArrayList<LlocInteres> adjacents = graph.adjacents(actual);
            for (LlocInteres adj: adjacents) {
                //Com es europea comprovem que no pasi per llocs dinteres tropicals
                if (!adj.getClima().equals(LlocInteres.TROPICAL)) {
                    //comprovem que no hagi estat visitat aquest node adjacent
                    if (!adj.isVisitat()) {
                        //guardem index del node adjacent
                        int index_adjacent = graph.getQueue().indexOf(adj);
                        //guardem trajecte entre node actual i node adjacent
                        Trajecte t = graph.getTrajecteEntreNodes(actual, adj);
                        //calculem distancia desde origen fins aquest node adjacent
                        float newDist = distancies[graph.getQueue().indexOf(actual)] + t.getDistancia();
                        float newTime = times[graph.getQueue().indexOf(actual)] + t.getTempsE();


                        //En cas que tingui coco nomes considerem trajectes amb distancia menor a 50km
                        if (!coco || coco && newDist <= 50000) {

                            //en cas que el nou temps desde origen fins adjacent sigui millor que el guardat, actualitzem
                            if (newTime < times[index_adjacent]) {
                                //actualitzem distancia i temps
                                distancies[index_adjacent] = newDist;
                                times[index_adjacent] = newTime;

                                //ACTUALITZEM RUTA
                                //recorrem tots els trajectes de la ruta del node actual
                                for (int i = 0; i < camins.get(index_actual).size(); i++) {
                                    //afegir cada trajecte de la ruta del node actual a la ruta del node adjacent
                                    camins.get(index_adjacent).add(camins.get(index_actual).get(i));
                                }

                                camins.get(index_adjacent).add(t);
                            }
                        }
                    }
                }
            }

            //marquem node actual com visitat
            actual.setVisitat(true);

            //actualitzem node actual a algun dels adjacents
            //recorrem tots els nodes del graf
            for (LlocInteres node:graph.getQueue()) {
                //comprovem que no hagi sigut visitat
                if (!node.isVisitat()) {
                    //comprovem que la seva distancia amb el node origen no sigui infinit
                    //(per saber si aquest node ha sigut un adjacent)
                    if (distancies[graph.getQueue().indexOf(node)] != Float.MAX_VALUE) {
                        //actualitzar actual
                        actual = node;
                        index_actual = graph.getQueue().indexOf(actual);
                        break;
                    }
                }
            }

        }

        //En cas que sigui millor cami que el guardat actualitzem
        if (times[index_desti] < millorTemps) {
            millorTemps = times[index_desti];
            millorDistancia = distancies[index_desti];
            millorTrajecte = camins.get(index_desti);
            tipusOreneta = "europea";
        }
        //retornar trajecte de node_desti
        return camins.get(index_desti);

    }

    private static ArrayList<Trajecte> dijkstraAfricanes(Graph graph, int id_origen, int id_desti, boolean coco) {
        //si te coco no pot fer mes de 50km
        //africanes nomes poden recorre clima continental i tropical
        //europea -> continental i polar

        //distancies entre node origen i cada node
        float[] distancies = new float[graph.length()];

        //temps que triga la oreneta entre node origen i cada node
        float[] times = new float[graph.length()];

        //llista de rutes desde inici fins a cada node
        ArrayList<ArrayList<Trajecte>> camins = new ArrayList<>();
        for (int i=0; i<graph.length(); i++) {
            camins.add(new ArrayList<Trajecte>());
        }

        //posem totes les distancies i temps a infinit
        for (int i=0; i<graph.length(); i++) {
            distancies[i] = Float.MAX_VALUE;
            times[i] = Float.MAX_VALUE;
        }

        //posem distancia (i temps) entre node origen i node origen a 0
        int index_origen = graph.getQueue().indexOf(graph.searchForID(id_origen));
        distancies[index_origen] = 0;
        times[index_origen] = 0;
        //guardem index del node desti
        int index_desti = graph.getQueue().indexOf(graph.searchForID(id_desti));

        //Asignem lloc origen
        LlocInteres actual = graph.getNode(index_origen);
        int index_actual = graph.getQueue().indexOf(actual);

        //comprobem que el node final encara no sha visitat (i que queden nodes per visitar)
        while (distancies[index_desti] == Float.MAX_VALUE && !graph.totsNodesNoPolarsVisitats()) {
            //Recorrem tots els nodes adjacents al actual
            ArrayList<LlocInteres> adjacents = graph.adjacents(actual);
            for (LlocInteres adj: adjacents) {
                //Com es africana comprovem que no pasi per llocs dinteres polars
                if (!adj.getClima().equals(LlocInteres.POLAR)) {
                    //comprovem que no hagi estat visitat aquest node adjacent
                    if (!adj.isVisitat()) {
                        //guardem index del node adjacent
                        int index_adjacent = graph.getQueue().indexOf(adj);
                        //guardem trajecte entre node actual i node adjacent
                        Trajecte t = graph.getTrajecteEntreNodes(actual, adj);
                        //calculem distancia desde origen fins aquest node adjacent
                        float newDist = distancies[graph.getQueue().indexOf(actual)] + t.getDistancia();
                        float newTime = times[graph.getQueue().indexOf(actual)] + t.getTempsA();


                        //En cas que tingui coco nomes considerem trajectes amb distancia menor a 50km
                        if (!coco || coco && newDist <= 50000) {

                            //en cas que el nou temps desde origen fins adjacent sigui millor que el guardat, actualitzem
                            if (newTime < times[index_adjacent]) {
                                //actualitzem distancia i temps
                                distancies[index_adjacent] = newDist;
                                times[index_adjacent] = newTime;

                                //ACTUALITZEM RUTA
                                //recorrem tots els trajectes de la ruta del node actual
                                for (int i = 0; i < camins.get(index_actual).size(); i++) {
                                    //afegir cada trajecte de la ruta del node actual a la ruta del node adjacent
                                    camins.get(index_adjacent).add(camins.get(index_actual).get(i));
                                }

                                camins.get(index_adjacent).add(t);
                            }
                        }
                    }
                }
            }

            //marquem node actual com visitat
            actual.setVisitat(true);

            //actualitzem node actual a algun dels adjacents
            //recorrem tots els nodes del graf
            for (LlocInteres node:graph.getQueue()) {
                //comprovem que no hagi sigut visitat
                if (!node.isVisitat()) {
                    //comprovem que la seva distancia amb el node origen no sigui infinit
                    //(per saber si aquest node ha sigut un adjacent)
                    if (distancies[graph.getQueue().indexOf(node)] != Float.MAX_VALUE) {
                        //actualitzar actual
                        actual = node;
                        index_actual = graph.getQueue().indexOf(actual);
                        break;
                    }
                }
            }

        }

        //En cas que sigui millor cami que el guardat actualitzem
        if (times[index_desti] < millorTemps) {
            millorTemps = times[index_desti];
            millorDistancia = distancies[index_desti];
            millorTrajecte = camins.get(index_desti);
            tipusOreneta = "africana";
        }
        //retornar trajecte de node_desti
        return camins.get(index_desti);

    }

    public static float getMillorDistancia() {
        return millorDistancia;
    }

    public static float getMillorTemps() {
        return millorTemps;
    }

    public static String getTipusOreneta() {
        return tipusOreneta;
    }
}
