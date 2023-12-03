import com.jogamp.opengl.GL2;
import com.jogamp.opengl.GLAutoDrawable;
import com.jogamp.opengl.GLEventListener;
import com.jogamp.opengl.glu.GLU;
import com.jogamp.opengl.util.awt.TextRenderer;
import com.jogamp.opengl.util.gl2.GLUT;
import java.awt.Color;
import java.awt.Font;
import java.util.Random;

public class Bolinha {
    private Random random = new Random();
    private Cena cena;
    private float posX = 0.0f;
    private float posY = 0.0f;
    private float velX = 1.7f; // Velocidade na direção X
    private float velY = 1.7f; // Velocidade na direção Y
    public void desenharBolinha(GL2 gl, float radius, int stacks) {
        this.cena = new Cena();
        GLUT glut = new GLUT();

        gl.glTranslatef(posX, posX, 0);

        gl.glColor3f(0,1,1);

        gl.glPushMatrix();
        //gl.glRotated(ang, 0, 1, 1);
            glut.glutSolidSphere(2, 50, 50);
        gl.glPopMatrix();
    }

    public void atualizar() {
        // Atualiza a posição com base na velocidade
        posX += velX;
        posY += velY;

        // Verifica limites da tela e inverte a direção se atingir o limite
        if (posX + 2 > 104 || posX - 2 < -104) {
            velX = -velX;
        }
        if (posY + 2 > 104) {
            velY = -velY;
            velY = (random.nextFloat() - 1) * 1.7f; // Valor aleatório entre -1 e 1 para a direção X
            if(velY < 0.7f && velY > 0) {
                velY = 1;
            } else if (velY > -0.7f && velY < 0) {
                velY = -1;
            }
        }
        if (posY - 2 < -104) {
            velY = -velY;
        }
        //System.out.println("Velocidade X: " + velX + ", VelocidadeY" + velY);
    }

}
