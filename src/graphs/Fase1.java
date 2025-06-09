package graphs;

import java.util.ArrayList;

public class Fase1 {

    /**
     * Metode per realitzar la "Depth First Search" que crida la funcio recursiva
     * @param graph
     * @param node
     */
    public static void DFS(Graph graph, LlocInteres node) {
        DFSRecursiu(graph, node, node.getId(), node.getRegne());
    }

    /**
     * Metode DFS recursiu que es crida recursivament fins visitar tots els llocs d'interes
     * @param graph
     * @param node
     * @param id
     * @param regne
     */
    private static void DFSRecursiu(Graph graph, LlocInteres node, int id, String regne) {
        // afegim el node actual com a visitat
        node.setVisitat(true);

        // si el node actual te el nom del mateix regen inicial
        if (node.getRegne().equals(regne)) {
            // si el id del node actual es el mateix que el id inicial el obviarem, ja que no es pot viatgar al mateix node desde ell mateix
            if (node.getId() != id) {
                System.out.println(node.getId() + " - " + node.getNom() + ", " + regne + " (Clima " + node.getClima() + ")");
            }
        }

        // per tots els llocs d'interes adjacents del actual, cridem recursivament al metode DFSRecursiu per repetir el proces
        for (LlocInteres adj : graph.adjacents(node)) {
            if (!adj.isVisitat()) {
                DFSRecursiu(graph, adj, id, regne);
            }
        }
    }


    /**
     * Metode que donat un id, retorna el lloc d'interes associat
     * @param llocs
     * @param id
     * @return
     */
    public static LlocInteres getLLocFromId (LlocInteres[] llocs, int id){
        for (LlocInteres lloc : llocs) {
            if (lloc.getId() == id) {
                return lloc;
            }
        }
        return null;
    }

    /**
     * Metode que inicialitza el graph amb tota la informacio dels fitxers (llocs d'interes i trajectes)
     * @param llocsInteres
     * @param trajectes
     * @return
     */
    public static ArrayList<LlocInteres> initGraph (LlocInteres[]llocsInteres, Trajecte[]trajectes){
        ArrayList<LlocInteres> queue = new ArrayList<>();
        for (LlocInteres llocInteres : llocsInteres) {
            // per cada lloc d'interes comprovem tots els trajectes que te associats
            ArrayList<Trajecte> listTrajectes = new ArrayList<>();
            for (Trajecte trajecte : trajectes) {
                // per a cada trajecte comprovem si el id del lloc A o lloc B es igual al lloc d'interes actual
                if (llocInteres.getId() == trajecte.getIdLlocA() || llocInteres.getId() == trajecte.getIdLlocB()) {
                    // Afegim una referencia del Entitats.LlocInteres segons el id a la classe trajecte
                    trajecte.setLlocInteresA(getLLocFromId(llocsInteres, trajecte.getIdLlocA()));
                    trajecte.setLlocInteresB(getLLocFromId(llocsInteres, trajecte.getIdLlocB()));
                    listTrajectes.add(trajecte);
                }
            }
            llocInteres.setTrajectes(listTrajectes);
            queue.add(llocInteres);
        }

        return queue;
    }

    //Prim
    public static Graph Prim (Graph graph) throws CloneNotSupportedException {
        boolean finish = false;
        Graph mst = new Graph(new ArrayList<>());
        LlocInteres origen = graph.getNode(0);
        mst.afegirNode(origen);
        origen.setVisitat(true);

        while (!finish) {
            Trajecte a = graph.trajecteMesCurt(mst);
            if (a == null) {
                //en cas que shagin visitat tots els nodes
                finish = true;
            } else {
                mst.afegirTrajecte(a, graph);
            }
        }

        return mst;
    }

}

