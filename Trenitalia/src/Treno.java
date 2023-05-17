import java.awt.*;

public class Treno extends Thread{

    private Linea_Bassano_Venezia linea;

    private int iPos;

    private TrenitaliaNode position;

    private String name;

    private String[] path;

    private Canvas canvas;

    private Color color;

    private Color oldColor = Color.BLACK;

    private boolean returns;

    private int oldIndex;


    public Treno(String name, Linea_Bassano_Venezia linea, String[] path, Canvas c, Color color, boolean returns){
        this.name = name;
        this.linea = linea;
        this.path = path;
        this.iPos = -1;
        this.canvas = c;
        this.color = color;
        this.returns = returns;
    }

    @Override
    public void run(){
        System.out.println("Partenza treno " + this.name);
        while( this.iPos < this.path.length-1) {
            try {
                goNextPosition();
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
        if (this.returns){

            while( this.iPos > 0) {
                try {
                    goPrevPosition();
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            }
        }
        this.position.getSemaphore().release();
        System.out.println("Arrivo treno " + this.name);
    }

    private void goPrevPosition() throws InterruptedException {
        for (int i = 0; i < this.position.getNear().size(); i++){
            if (this.position.getNear().get(i).getName() == this.path[(iPos-1)]){
                this.position.getNear().get(i).getSemaphore().acquire();
                int in = this.position.getNear().get(i).getIndex();
                this.canvas.colors[oldIndex] = this.oldColor;
                this.oldColor = this.canvas.colors[in];
                this.canvas.colors[in] = this.color;
                this.oldIndex = in;
                this.position.getSemaphore().release();
                this.iPos--;
                this.position = this.position.getNear().get(i);
                break;
            }
        }
        this.canvas.repaint();
        try{
            Thread.sleep(2000);
        }catch (Exception e){}
        System.out.println("Il treno " + this.name + " è arrivato a/al " + this.position.getName());
    }

    private void goNextPosition() throws InterruptedException {
        if (this.iPos == -1){
            for (int i = 0; i< this.linea.getLinea().size(); i++){
                if (this.linea.getElement(i).getName() == this.path[0]){
                    this.linea.getElement(i).getSemaphore().acquire();
                    int in = this.linea.getElement(i).getIndex();
                    this.canvas.colors[in] = this.color;
                    this.oldIndex = in;
                    this.iPos = 0;
                    this.position = this.linea.getElement(i);
                    break;
                }
            }
        }else{
            for (int j = 0; j < this.position.getNear().size(); j++){
                if (this.position.getNear().get(j).getName() == this.path[(iPos+1)]){
                    this.position.getNear().get(j).getSemaphore().acquire();
                    int in = this.position.getNear().get(j).getIndex();
                    this.canvas.colors[oldIndex] = this.oldColor;
                    this.oldColor = this.canvas.colors[in];
                    this.canvas.colors[in] = this.color;
                    this.oldIndex = in;
                    this.position.getSemaphore().release();
                    this.iPos++;
                    this.position = this.position.getNear().get(j);
                    break;
                }
            }
        }
        this.canvas.repaint();
        try{
            Thread.sleep(2000);
        }catch (Exception e){}

        System.out.println("Il treno " + this.name + " è arrivato a/al " + this.position.getName());
    }
}
