package cena;

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
    public Random random = new Random();
    private Cena cena;
    private float posX = 0.0f;
    private float posY = 0.0f;

    private float positiveVelX = 1.7f;

    private float randomNumber;
    private float quadranteSuperior;

    private float positiveVelY = 1.7f;
    private float velX = 1.7f; // Velocidade na direção X
    private float velY = 1.7f; // Velocidade na direção Y


    public void init() {
        this.cena = new Cena();
        this.quadranteSuperior = (float) (this.cena.getMaxScreen() - (this.cena.getMaxScreen() * 0.3));


    }

    public void desenharBolinha(GL2 gl, float radius, int stacks) {
        GLUT glut = new GLUT();

        gl.glTranslatef(posX, posY, 0);

        gl.glColor3f(0,1,1);

        gl.glPushMatrix();
        //gl.glRotated(ang, 0, 1, 1);
            glut.glutSolidSphere(2, 50, 50);
        gl.glPopMatrix();
    }

    public void atualizar(Retangulo retangulo) {

        posX += velX;
        posY += velY;

        if (posX + 2 > this.cena.getMaxScreen() || posX - 2 < this.cena.getMinScreen()) {
            velX = -velX;
        }

        // Verifica colisão com o retângulo
        if (posX + 2 > retangulo.getxEsquerda() && posX - 2 < retangulo.getxDireita() &&
                posY + 2 > retangulo.getyBaixo() && posY - 2 < retangulo.getyCima()) {
            // Colisão com o retângulo, inverte a direção na vertical (Y)
            velY = -velY;
        }

        if(posY + 2 > this.cena.getMaxScreen()) {
            velY = getRandomVel(-velY);
        }
        if (posY - 2 < this.cena.getMinScreen()) {
            velY = -velY;
        }

    }


    private float getRandomVel(float vel) {
        randomNumber = random.nextFloat(this.positiveVelX);
        if(vel >= 0) {
            vel = randomNumber;
        } else if (vel < 0) {
            vel = -randomNumber;
        }
        if(vel < 0.7f && vel > 0) {
            vel = 1;
        } else if (vel > -0.7f && vel < 0) {
            vel = -1;
        }

        return vel;
    }

}
