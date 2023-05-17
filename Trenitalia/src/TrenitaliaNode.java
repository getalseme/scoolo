import java.util.ArrayList;
import java.util.concurrent.Semaphore;

public class TrenitaliaNode {
    private Object element;
    private ArrayList<TrenitaliaNode> near = new ArrayList<>();
    private int index;

    private Semaphore semaphore;
    public TrenitaliaNode(Stazione st, int index){
        this.element = st;
        this.semaphore = new Semaphore(st.getnBinari());
        this.index = index;
    }

    public TrenitaliaNode(Binario bi, int index){
        this.element = bi;
        this.semaphore = new Semaphore(1);
        this.index = index;
    }

    public TrenitaliaNode(Scambio sc, int index){
        this.element = sc;
        this.semaphore = new Semaphore(2);
        this.index = index;
    }

    public ArrayList<TrenitaliaNode> getNear() {
        return near;
    }

    public void addNear(TrenitaliaNode tn){
        this.near.add(tn);
        tn.near.add(this);
    }

    public Semaphore getSemaphore() {
        return this.semaphore;
    }

    public Object getName() {
        return element.toString();
    }

    public int getIndex(){
        return this.index;
    }


}
