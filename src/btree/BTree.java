package btree;

import java.util.ArrayList;

public class BTree {
    private Persona personaArrel;

    public BTree() {
        this.personaArrel = null;
    }

    private void addPersonaRecursiva(Persona personaActual, Persona persona, int p) {
        if (personaArrel == null) {
            personaArrel = persona;
            personaArrel.setProfunditat(0);
        } else {
            if (persona.getPes() < personaActual.getPes()) {
                if (personaActual.getPersonaL() == null) {
                    personaActual.setPersonaL(persona);
                    personaActual.getPersonaL().setPersonaAnterior(personaActual);
                    personaActual.getPersonaL().setProfunditat(p);
                } else {
                    addPersonaRecursiva(personaActual.getPersonaL(), persona, p + 1);
                }
            } else {
                if (personaActual.getPersonaR() == null) {
                    personaActual.setPersonaR(persona);
                    personaActual.getPersonaR().setPersonaAnterior(personaActual);
                    personaActual.getPersonaR().setProfunditat(p);
                } else {
                    addPersonaRecursiva(personaActual.getPersonaR(), persona, p + 1);
                }
            }
        }
    }

    public void addPersona(Persona persona) {
        addPersonaRecursiva(personaArrel, persona, 1);

        Persona p = persona;
        while (p != null) {
            rebalance(p);
            p = p.getPersonaAnterior();
        }
    }

    public void deletePersona(Persona p) {

        //Si el nodo no tiene hijos
        if (p.esFulla()) {
            if (p.getPes() < p.getPersonaAnterior().getPes()) {
                p.getPersonaAnterior().setPersonaL(null);
            } else {
                p.getPersonaAnterior().setPersonaR(null);
            }
        } else {

            //Si el nodo tiene unicamente 1 hijo por izquierda
            if (p.getPersonaL().esFulla()) {
                Persona sustitut = p.getPersonaL();
                sustitut.setPersonaAnterior(p.getPersonaAnterior());

                if (p.getPes() < p.getPersonaAnterior().getPes()) {
                    //izquierda del anterior
                    p.getPersonaAnterior().setPersonaL(sustitut);
                    sustitut.setPersonaR(p.getPersonaR());
                } else {
                    //derecha del anterior
                    p.getPersonaAnterior().setPersonaR(sustitut);
                    sustitut.setPersonaR(p.getPersonaR());
                }

                //REVISAR!!! Autobalanceo
                Persona pActual = sustitut;
                while (pActual != null) {
                    rebalance(pActual);
                    pActual = pActual.getPersonaAnterior();
                }

            } else if (p.getPersonaR().esFulla()){
                //Si el nodo tiene unicamente 1 hijo por derecha

                Persona sustitut = p.getPersonaR();
                sustitut.setPersonaAnterior(p.getPersonaAnterior());

                if (p.getPes() < p.getPersonaAnterior().getPes()) {
                    //si esta a la izquierda del anterior
                    p.getPersonaAnterior().setPersonaL(sustitut);
                    sustitut.setPersonaL(p.getPersonaL());
                } else {
                    //si esta a la derecha del anterior
                    p.getPersonaAnterior().setPersonaR(sustitut);
                    sustitut.setPersonaL(p.getPersonaL());
                }

                Persona pActual = sustitut;
                while (pActual != null) {
                    rebalance(pActual);
                    pActual = pActual.getPersonaAnterior();
                }

            } else {
                //Si ninguno de los dos hijos del nodo son hojas

                //Encontramos el mas grande de los de su lado izquierdo
                Persona sustitut = p.getPersonaL().majorFill();
                sustitut.getPersonaAnterior().setPersonaR(sustitut.getPersonaL());

                //Node inicial pel bucle de balancejar
                Persona start = sustitut.getPersonaAnterior();

                sustitut.setPersonaAnterior(p.getPersonaAnterior());
                sustitut.setPersonaL(p.getPersonaL());
                sustitut.setPersonaR(p.getPersonaR());

                if (p.getPes() < p.getPersonaAnterior().getPes()) {
                    //si esta a la izquierda del anterior
                    p.getPersonaAnterior().setPersonaL(sustitut);
                } else {
                    //si esta a la derecha del anterior
                    p.getPersonaAnterior().setPersonaR(sustitut);
                }

                Persona pActual = start;
                while (pActual != null) {
                    rebalance(pActual);
                    pActual = pActual.getPersonaAnterior();
                }
            }
        }


    }

    private void rebalance(Persona persona) {
        int factor = getBalanceFactor(persona);

        if (factor < -1) {
            if (getBalanceFactor(persona.getPersonaL()) <= 0) {
                rotRight(persona);
            } else {
                rotLeft(persona.getPersonaL());
                rotRight(persona);
            }

        } else if (factor > 1) {
            if (getBalanceFactor(persona.getPersonaR()) >= 0) {
                rotLeft(persona);
            } else {
                rotRight(persona.getPersonaR());
                rotLeft(persona);
            }
        }

    }

    private int getBalanceFactor(Persona persona) {
        return calculateHeight(persona.getPersonaR()) - calculateHeight(persona.getPersonaL());
    }

    private static int calculateHeight(Persona persona) {

        // si la persona es null o es una fulla, retornem 0
        if (persona == null || persona.esFulla()) {
            return 0;
        } else {
            // sino, retornem el maxim de la crida recursiva de l'alcada del node dret o esquerra
            return Math.max(calculateHeight(persona.getPersonaL()), calculateHeight(persona.getPersonaR())) + 1;
        }
    }

    /**
     * Metode per mostrar la informacio del arbre
     */
    public void showTree() {
        showTreeRecursive(personaArrel, "", new ArrayList<>(), 0);
    }

    /**
     * Metode privat recursiu per mostrar la informacio del arbre
     * @param persona
     * @param limit
     * @param listLimits
     * @param child
     */
    private void showTreeRecursive(Persona persona, String limit, ArrayList<String> listLimits, int child) {
        // si persona es null, vol dir que la persona anterior (el pare) no te fill, per tant deixarem de cridar el metode recursiu
        if (persona != null) {
            // muntem un llistat de strings afegint el limit (" " o "|")
            listLimits.add(limit);

            // si child == 1 vol dir que es un fill dret, si child == -1, es un fill esquerra
            // definim els limits segons el tipus de fill
            String limitPersonaRight = "";
            String limitPersonaLeft = "";
            switch (child) {
                case -1 -> {
                    limitPersonaRight = "|";
                    limitPersonaLeft = " ";
                }
                case 1 -> {
                    limitPersonaRight = " ";
                    limitPersonaLeft = "|";
                }
            }

            // cridem recursivament el metode amb el fill dret
            showTreeRecursive(persona.getPersonaR(), limitPersonaRight, listLimits, 1);

            // mostrem la persona amb els amb el llistat de caracters que hem anat muntant recursivament
            mostra(persona, listLimits);

            // cridem recursivament el metode amb el fill esquerra
            showTreeRecursive(persona.getPersonaL(), limitPersonaLeft, listLimits, -1);

            // desmarquem eliminant el ultim caracter afegit
            listLimits.remove(listLimits.size() - 1);
        }
    }

    /**
     * Metode que mostra per consola la informacio de la persona
     * @param p
     * @param listLimits limits que hem anat afegint (tabulacions amb espais o "|")
     */
    private void mostra(Persona p, ArrayList<String> listLimits) {
        // mostrem tots els limits a la mateixa linia
        for (String limit : listLimits) {
            System.out.print(limit + "     ");
        }
        // si p == personaArrel
        if (p == personaArrel) {
            System.out.print("* ");
        } else {
            System.out.print("|--- ");
        }
        System.out.println(p.getNom() + " (" + p.getId() + ", " + p.getNomRegne() + "): " + p.getPes()+"kg");
    }

    private void rotLeft(Persona p) {
        Persona personaRight = p.getPersonaR();

        if (p.getPersonaAnterior() != null) {
            //Si 'p' te persona anterior (no es el node arrel)
            if (p.getPersonaAnterior().getPes() < p.getPes()) {
                //la persona esta a la derecha de la anterior
                p.getPersonaAnterior().setPersonaR(personaRight);
            } else {
                //la persona esta a la izquierda de la anterior
                p.getPersonaAnterior().setPersonaL(personaRight);
            }

            personaRight.setPersonaAnterior(p.getPersonaAnterior());
        } else {
            //es nodo raiz
            personaArrel = personaRight;
            personaArrel.setPersonaAnterior(null);
        }

        //asignamos derecha izquierda a la derecha de p
        p.setPersonaR(personaRight.getPersonaL());
        if (p.getPersonaR() != null) {
            p.getPersonaR().setPersonaAnterior(p);
        }

        //asignamos p a la izquierda de persona Right
        personaRight.setPersonaL(p);
        p.setPersonaAnterior(personaRight);
    }

    private void rotRight(Persona p) {
        //guardamos la derecha de p (la cual sera el nodo raiz despues de la rotacion)
        Persona personaLeft = p.getPersonaL();

        if (p.getPersonaAnterior() != null) {
            //Si 'p' tiene persona anterior (no es el nodo raiz)
            if (p.getPersonaAnterior().getPes() < p.getPes()) {
                //la persona esta a la derecha de la anterior
                p.getPersonaAnterior().setPersonaR(personaLeft);
            } else {
                //la persona esta a la izquierda de la anterior
                p.getPersonaAnterior().setPersonaL(personaLeft);
            }

            personaLeft.setPersonaAnterior(p.getPersonaAnterior());
        } else {
            //es nodo raiz
            personaArrel = personaLeft;
            personaArrel.setPersonaAnterior(null);
        }

        //asignamos izquierda derecha a la izquierda de p
        p.setPersonaL(personaLeft.getPersonaR());
        if (p.getPersonaL() != null) {
            p.getPersonaL().setPersonaAnterior(p);
        }

        //asignamos p a la izquierda de persona Right
        personaLeft.setPersonaR(p);
        p.setPersonaAnterior(personaLeft);
    }
    public Persona duck(float peso){

         return peso_igual(personaArrel,peso);
    }
    public  Persona peso_igual (Persona p, float peso) {
        int trobat = 0;
        Persona encontrado = null;
        if (p == null) {
            return null;
        }
        if (p.getPes() == peso) {
            encontrado=p;
            trobat=1;
            //return p;
        }
        if(trobat==0){
            // cridem recursivament el metode amb el fill dret
            encontrado=peso_igual(p.getPersonaR(), peso);

            // cridem recursivament el metode amb el fill esquerre
            encontrado=peso_igual(p.getPersonaL(), peso);
        }

        return encontrado;

    }
    public Persona stone(float peso){

        return peso_grande(personaArrel,peso,1000, personaArrel);
    }

    public  Persona peso_grande (Persona p, float peso,float diferencia_min, Persona encontrado) {
        int trobat = 0;
        //Persona encontrado = null;
        if (p == null) {
            return encontrado;
        }
        if (p.getPes() > peso && (p.getPes() - peso)< diferencia_min) {
            diferencia_min=p.getPes() - peso;
            encontrado=p;
            //System.out.println("---"+p.getNom()+" "+p.getPes());
            //trobat=0;
            //return encontrado;
        }
        //if(trobat==0){
            // cridem recursivament el metode amb el fill dret
        encontrado = peso_grande(p.getPersonaR(), peso, diferencia_min, encontrado);

            // cridem recursivament el metode amb el fill esquerre
        encontrado = peso_grande(p.getPersonaL(), peso, diferencia_min, encontrado);
        //}



        return encontrado;

    }

    public Persona wood(float peso){

        return peso_pequeño(personaArrel,peso,1000, personaArrel);
    }
    public  Persona peso_pequeño (Persona p, float peso,float diferencia_min, Persona encontrado) {
        int trobat = 0;
        //Persona encontrado = null;
        if (p == null) {
            return encontrado;
        }
        if (p.getPes() < peso && (peso - p.getPes() )< diferencia_min) {
            diferencia_min=peso - p.getPes() ;
            encontrado=p;
            //System.out.println("---"+p.getNom()+" "+p.getPes());
            //trobat=0;
            //return encontrado;
        }
        //if(trobat==0){
        // cridem recursivament el metode amb el fill dret
        encontrado = peso_pequeño(p.getPersonaR(), peso, diferencia_min, encontrado);

        // cridem recursivament el metode amb el fill esquerre
        encontrado = peso_pequeño(p.getPersonaL(), peso, diferencia_min, encontrado);
        //}



        return encontrado;

    }

    public Persona cerca_persona_id(int id){

        return cerca_persona(personaArrel,id, personaArrel);
    }
    public  Persona cerca_persona (Persona p, int id, Persona encontrado) {
        int trobat = 0;
        //Persona encontrado = null;
        if (p == null) {
            return encontrado;
        }
        if (p.getId() == id) {
            encontrado=p;
            trobat=1;
            //return p;
        }
        if(trobat==0){
            // cridem recursivament el metode amb el fill dret
            encontrado=cerca_persona(p.getPersonaR(), id, encontrado);

            // cridem recursivament el metode amb el fill esquerre
            encontrado=cerca_persona(p.getPersonaL(), id, encontrado);
        }

        return encontrado;

    }

    public static ArrayList<Persona> batuda(float pesmin, float pesmax, ArrayList<Persona> bruixes){
        int i;
        ArrayList<Persona> capturades = new ArrayList<>();
        for(i=0; i<bruixes.size();i++){
            if(bruixes.get(i).getPes()>pesmin && bruixes.get(i).getPes()<pesmax){
                capturades.add(bruixes.get(i));
            }
        }
        return capturades;

    }





}


