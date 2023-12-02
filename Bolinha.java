package cena;

import com.jogamp.opengl.GL2;
import com.jogamp.opengl.util.gl2.GLUT;
import java.util.Random;

public class Bolinha {
    private float posX = 0.0f;
    private float posY = 0.0f;
    private float velX = 1.7f; // Velocidade na direção X
    private float velY = 1.7f; // Velocidade na direção Y

    private Random random = new Random();

    public void desenharBolinha(GL2 gl, float radius, int stacks) {
        GLUT glut = new GLUT();

        gl.glColor3f(0, 1, 1);

        gl.glPushMatrix();
        gl.glTranslatef(posX, posY, 0); // Translada para a posição atual
        glut.glutSolidSphere(radius, 50, 50);
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
        System.out.println("Velocidade X: " + velX + ", VelocidadeY" + velY);
    }
}
