public class Stazione {
    private String name;

    private int nBinari;

    public Stazione(String name, int binari){
        this.name=name;
        this.nBinari= binari;
    }

    public int getnBinari() {
        return nBinari;
    }

    @Override
    public String toString(){
        return name;
    }
}
