package cena;

import com.jogamp.opengl.GL2;
import com.jogamp.opengl.util.gl2.GLUT;
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

        baseVel = velocidadeBase;
        minimumVel = baseVel * 0.2f;
        velX = velY = baseVel;
    }

    public void desenharBolinha(GL2 gl, float radius, int stacks) {
        GLUT glut = new GLUT();

        gl.glTranslatef(posX, posY, 0);

        gl.glColor3f(0,1,1);

        gl.glPushMatrix();
        glut.glutSolidSphere(2, 50, 50);
        gl.glPopMatrix();
    }

    public String atualizar(Retangulo retangulo) {
        cena = new Cena();
        posX += velX;
        posY += velY;

        if (posX + 4 > cena.getMaxScreen() || posX - 4 < cena.getMinScreen()) {
            velX = -velX;
        }

        if (posX + 2 > retangulo.getxEsquerda() && posX - 2 < retangulo.getxDireita() &&
                posY + 2 > retangulo.getyBaixo() && posY - 2 < retangulo.getyCima()) {

            if(posY + -retangulo.getyCima() >= -4 && posY + -retangulo.getyCima() < 0 &&
                    (posX + -retangulo.getxDireita() >= -4 && posX + -retangulo.getxDireita() < 0) ||
                    (posX + -retangulo.getxEsquerda() >= -4 && posX + -retangulo.getxEsquerda() < 0)) {
                velX = -velX;
                return "ponto";
            }
            velY = -velY;
            return "ponto";
        }

        if(posY + 4 > cena.getMaxScreen()) {
            velY = getRandomVel(-velY);
        }

        if (posY - 2 < cena.getMinScreen()) {
            velY = 0;
            velX = 0;
            // Aqui vai vir a função de perdeu e/ou zerou pontos/perdeu vida
            return "vida";
        }
        return "iniciar";
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