import java.util.ArrayList;

public class Linea_Bassano_Venezia {
    private ArrayList<TrenitaliaNode> linea = new ArrayList<>();

    public Linea_Bassano_Venezia(){

        //inizializzazione degli oggetti Stazione
        Stazione venezia = new Stazione("Venezia", 9);
        Stazione bassanos = new Stazione("Bassano", 5);
        Stazione salzano = new Stazione("Salzano", 2);
        Stazione resana = new Stazione("Resana",2);
        Stazione piombin = new Stazione("Piombino", 2);
        Stazione treba = new Stazione("Trebaseleghe", 2);

        //inizializzazione degli oggetti Binario
        Binario ven_sc1 = new Binario("b1");
        Binario sal_sc1 = new Binario("b2");
        Binario sal_sc2 = new Binario("b3");
        Binario res_sc2 = new Binario("b4");
        Binario res_sc3 = new Binario("b5");
        Binario bas_sc3 = new Binario("b6");
        Binario tre_sc2 = new Binario("b7");
        Binario pio_sc2 = new Binario("b8");

        //inizializzazione degli oggetti Scambio
        Scambio sc1 = new Scambio("sc1");
        Scambio sc2 = new Scambio("sc2");
        Scambio sc3 = new Scambio("sc3");

        //creazione degli oggetti nodo del grafo
        TrenitaliaNode venice = new TrenitaliaNode(venezia, 0);
        TrenitaliaNode bassano = new TrenitaliaNode(bassanos, 12);
        TrenitaliaNode salz = new TrenitaliaNode(salzano,4);
        TrenitaliaNode resa = new TrenitaliaNode(resana,8);
        TrenitaliaNode trebas = new TrenitaliaNode(treba, 14);
        TrenitaliaNode piombino = new TrenitaliaNode(piombin, 16);

        TrenitaliaNode bi1 = new TrenitaliaNode(ven_sc1,1);
        TrenitaliaNode bi2 = new TrenitaliaNode(sal_sc1,3);
        TrenitaliaNode bi3 = new TrenitaliaNode(sal_sc2,5);
        TrenitaliaNode bi4 = new TrenitaliaNode(res_sc2,7);
        TrenitaliaNode bi5 = new TrenitaliaNode(res_sc3,9);
        TrenitaliaNode bi6 = new TrenitaliaNode(bas_sc3,11);
        TrenitaliaNode bi7 = new TrenitaliaNode(tre_sc2, 13);
        TrenitaliaNode bi8 = new TrenitaliaNode(pio_sc2, 15);

        TrenitaliaNode sca1 = new TrenitaliaNode(sc1,2);
        TrenitaliaNode sca2 = new TrenitaliaNode(sc2,6);
        TrenitaliaNode sca3 = new TrenitaliaNode(sc3,10);

        //aggiunta dei nodi nella lista dei nodi del grafo
        this.linea.add(venice);
        this.linea.add(bi1);
        this.linea.add(sca1);
        this.linea.add(bi2);
        this.linea.add(salz);
        this.linea.add(bi3);
        this.linea.add(sca2);
        this.linea.add(bi4);
        this.linea.add(resa);
        this.linea.add(bi5);
        this.linea.add(sca3);
        this.linea.add(bi6);
        this.linea.add(bassano);

        //ciclo per aggiungere tutti i collegamenti tra i nodi
        int i;
        for (i = 0; i < this.linea.size() -1; i++){
            this.linea.get(i).addNear(this.linea.get((i+1)));
        }

        i++;

        this.linea.add(bi7);
        this.linea.add(trebas);
        this.linea.add(bi8);
        this.linea.add(piombino);

        this.linea.get(i).addNear(this.linea.get((i+1)));
        this.linea.get(6).addNear(this.linea.get((i)));
        i+=2;
        this.linea.get(i).addNear(this.linea.get((i+1)));
        this.linea.get(6).addNear(this.linea.get((i)));

        System.out.println(this.linea.get(this.linea.size()-1).getName());
    }

    public TrenitaliaNode getElement(int i){
        return this.linea.get(i);
    }

    public ArrayList<TrenitaliaNode> getLinea() {
        return this.linea;
    }

    public TrenitaliaNode getFirst(){
        return this.linea.get(0);
    }

    public void printAll(){
        for (int i = 0; i < this.linea.size(); i++){
            System.out.println("Node name: " + this.linea.get(i).getName());
            System.out.println("its nodes:");
            printNodes(i);
        }
    }

    public void printNodes(int n){
        for (int i = 0; i<this.linea.get(n).getNear().size(); i++){
            System.out.println(this.linea.get(n).getNear().get(i).getName());
        }
    }
}
