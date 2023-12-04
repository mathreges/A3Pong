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
    private float posY = 0.0f;;

    private float minimumVel;

    private float randomNumber;
    private float baseVel;
    private float velX;
    private float velY;


    public void init(float velocidadeBase) {
        this.cena = new Cena();

        baseVel = velocidadeBase;
        minimumVel = baseVel * 0.6f;
        velX = velY = baseVel;
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

        if (posX + 4 > this.cena.getMaxScreen() || posX - 4 < this.cena.getMinScreen()) {
            velX = -velX;
        }

        if (posX + 2 > retangulo.getxEsquerda() && posX - 2 < retangulo.getxDireita() &&
                posY + 2 > retangulo.getyBaixo() && posY - 2 < retangulo.getyCima()) {

            if(posY + -retangulo.getyCima() >= -4 && posY + -retangulo.getyCima() < 0 &&
              (posX + -retangulo.getxDireita() >= -4 && posX + -retangulo.getxDireita() < 0) ||
              (posX + -retangulo.getxEsquerda() >= -4 && posX + -retangulo.getxEsquerda() < 0)) {
                velX = -velX;
                return;
            }


            velY = -velY;
        }

        if(posY + 4 > this.cena.getMaxScreen()) {
            velY = getRandomVel(-velY);
        }
        if (posY - 2 < this.cena.getMinScreen()) {
            velY = 0;
            velX = 0;
            // Aqui vai vir a função de perdeu e/ou zerou pontos/perdeu vida
        }

    }


    private float getRandomVel(float vel) {
        randomNumber = random.nextFloat(baseVel);
        if(vel >= 0) {
            vel = randomNumber;
        } else if (vel < 0) {
            vel = -randomNumber;
        }
        if(vel < minimumVel && vel > 0) {
            vel = (baseVel * 0.75f);
        } else if (vel > -minimumVel && vel < 0) {
            vel = -(baseVel * 0.75f);
        }
        return vel;
    }

}
