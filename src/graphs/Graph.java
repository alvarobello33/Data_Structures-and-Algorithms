package graphs;

import java.util.ArrayList;

public class Graph {
    private ArrayList<LlocInteres> queue;

    public Graph(ArrayList<LlocInteres> queue) {
        this.queue = queue;
    }

    public ArrayList<LlocInteres> getQueue() {
        return queue;
    }

    public void cleanVisitats() {
        for (LlocInteres lloc:queue) {
            lloc.setVisitat(false);
        }
    }

    public void afegirNode(LlocInteres node) throws CloneNotSupportedException {

        LlocInteres newNode = node.clone();
        newNode.setTrajectes(new ArrayList<>());
        newNode.setVisitat(true);
        queue.add(newNode);//clonar nodo
    }

    public LlocInteres getNode(int index) {
        return queue.get(index);
    }

    public boolean contains(LlocInteres node) {
        return queue.contains(node);
    }
    public boolean containsByID(LlocInteres node) {
        //comprobamos si hay algun nodo con el mismo id que el que nos han introducido
        for (LlocInteres lloc:queue) {
            if (lloc.getId() == node.getId()) {
                return true;
            }
        }
        //si no lo encuentra -> false
        return false;
    }
    public ArrayList<LlocInteres> adjacents (int num) {
        //no-implementat
        return null;
    }

    public int length () {
        return queue.size();
    }

    public LlocInteres searchForID(int id) {
        //devuelve el nodo con id introducido
        for (LlocInteres node:queue) {
            if (node.getId() == id) {
                return node;
            }
        }
        //si no hay ningun nodo con dicho id en el graph devuelve null
        return null;
    }

    //Devuelve el trayecto mas corto conectado a algun nodo del mst
    public Trajecte trajecteMesCurt (Graph mst) {
        Trajecte mesCurt = null;
        //recorrer todos nodos coincidentes en mst
        for (LlocInteres node:queue) {
            if (mst.containsByID(node)) {
                //recorrer todos los trayectos del nodo
                for (Trajecte t:node.getTrajectes()) {
                    //obtenemos el nodo destino del trayecto (el que su id no sea igual al nodo)
                    LlocInteres destino;
                    if (t.getIdLlocA() == node.getId()) {
                        destino = t.getLlocInteresB();  //nuevo nodo a conectar
                    } else {
                        destino = t.getLlocInteresA();
                    }
                    //comprobamos si el trayecto nos lleva a un nodo no visitado
                    if ( !destino.isVisitat() ) {
                        //guardarnos el trayecto que menor distancia tenga
                        if (mesCurt == null || t.getDistancia() < mesCurt.getDistancia()) {
                            mesCurt = t;
                        }
                    }
                }
            }
        }

        if (mesCurt != null) {
            //marcamos como visitado el nuevo nodo que hemos conectado al mst
            if ( mst.containsByID(mesCurt.getLlocInteresA()) ) {
                mesCurt.getLlocInteresB().setVisitat(true);
            } else {
                mesCurt.getLlocInteresA().setVisitat(true);
            }
        }
        return mesCurt;
    }

    public void afegirTrajecte (Trajecte t, Graph graph) throws CloneNotSupportedException {

        //añadir nodo que no contenga el graph
        LlocInteres newNode;
        if ( containsByID(t.getLlocInteresA()) ) {
            newNode = t.getLlocInteresB();
        } else {
            newNode = t.getLlocInteresA();
        }
        afegirNode(newNode);

        //añadir el trayecto(clonado con los nuevos nodos[tambien clonados]) a los dos nodos (clonados) del mst
        LlocInteres llocA = searchForID(t.getLlocInteresA().getId());
        LlocInteres llocB = searchForID(t.getLlocInteresB().getId());

        Trajecte newT = t.clone();
        newT.setLlocInteresA(llocA);
        newT.setLlocInteresB(llocB);

        llocA.addTrajecte(t);
        llocB.addTrajecte(t);
    }

    public ArrayList<LlocInteres> adjacents(LlocInteres node) {
        ArrayList<LlocInteres> adjacents = new ArrayList<>();

        for (int i = 0; i < node.getTrajectes().size(); i++) {
            if (node.getTrajectes().get(i).getIdLlocA() == node.getId() || node.getTrajectes().get(i).getIdLlocB() == node.getId()) {
                for (LlocInteres llocInteres : queue) {
                    if (llocInteres != node) {
                        if (llocInteres.getId() == node.getTrajectes().get(i).getIdLlocA()) {
                            adjacents.add(node.getTrajectes().get(i).getLlocInteresA());
                            //System.out.println("ok");
                            //break;
                        } else if (llocInteres.getId() == node.getTrajectes().get(i).getIdLlocB()) {
                            adjacents.add(node.getTrajectes().get(i).getLlocInteresB());
                            //System.out.println("ok2");
                            //break;
                        }
                    }
                }
            }
        }

       //System.out.println();
        //System.out.println("Adj size is " + adjacents.size());

        return adjacents;
    }

    public LlocInteres getLlocSegonsId(int id) {
        for (LlocInteres llocInteres : queue) {
            if (llocInteres.getId() == id) {
                return llocInteres;
            }
        }
        return null;
    }

    //Funcio que retorna el trajecte entre dos LLocs dinteres(nodes) buscant per ID el trajecte coincident
    public Trajecte getTrajecteEntreNodes(LlocInteres llocA, LlocInteres llocB) {
        //busquem trajecte dels trajectes de llocA que el conecti amb llocB
        for (Trajecte trajecte: llocA.getTrajectes()) {
            if (llocB.getId() == trajecte.getIdLlocB() || llocB.getId() == trajecte.getIdLlocA()) {
                return trajecte;
            }
        }

        //en cas de que no hagi trobat cami entre els dos llocs dinteres
        return null;
    }

    //Funcio que ens indica si tots els nodes del grafic han sigut visitats
    public boolean totsNodesNoTropicalsVisitats() {
        //recorrem tots els nodes i en cas que hagi un no visitat tornem false (sense considerar els tropicals)
        for (LlocInteres node:queue) {
            if (!node.isVisitat() && !node.getClima().equals(LlocInteres.TROPICAL)) {
                return false;
            }
        }

        return true;
    }

    public boolean totsNodesNoPolarsVisitats() {
        //recorrem tots els nodes i en cas que hagi un no visitat tornem false (sense considerar els polars)
        for (LlocInteres node:queue) {
            if (!node.isVisitat() && !node.getClima().equals(LlocInteres.POLAR)) {
                return false;
            }
        }

        return true;
    }

}
