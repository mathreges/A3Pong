import com.jogamp.newt.event.KeyEvent;
import com.jogamp.newt.event.KeyListener;
import com.jogamp.opengl.GL2;

public class KeyBoard implements KeyListener{
    private Cena cena;

    public KeyBoard(Cena cena){
        this.cena = cena;
    }

    @Override
    public void keyPressed(KeyEvent e) {
        //System.out.println("Key pressed: " + e.getKeyCode());
        if(e.getKeyCode() == KeyEvent.VK_ESCAPE)
            System.exit(0);

        int keyCode = e.getKeyCode();

        switch (keyCode) {
            case KeyEvent.VK_LEFT:
                if (cena.xDireita > -88){
                    cena.xDireita -= 8;
                    cena.xEsquerda -= 8;
                }
                break;

            case KeyEvent.VK_RIGHT:
                if (cena.xEsquerda < 88){
                    cena.xDireita += 8;
                    cena.xEsquerda += 8;
                }
                break;
        }
        }

    @Override
    public void keyReleased(KeyEvent e) { }

}