package cena;

import com.jogamp.opengl.GL2;
import com.jogamp.opengl.util.gl2.GLUT;

import java.text.DecimalFormat;
import java.util.Random;

public class Bolinha {
    public Random random = new Random();
    private Cena cena;
    private float posX = 0.0f;
    private float posY = 0.0f;;
    private float minimumVel;
    private float randomNumber;
    public DecimalFormat formatar = new DecimalFormat("0.00");
    private Cena cenaAtual;
    private Jogador jogadorAtual;
    private StatusJogo statusJogo;
    private float baseVel;
    private float velX;
    private float velY;

    public void init(float velocidadeBase, Jogador jogador, StatusJogo statusjogo, Cena cena) {
        this.statusJogo = statusJogo;
        this.jogadorAtual = jogador;
        this.cenaAtual = cena;
        baseVel = velocidadeBase;
        minimumVel = baseVel * 0.5f;
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

    public void atualizar(Retangulo retangulo) {
        if (baseVel == 0) {
            return;
        }
        posX += velX;
        posY += velY;

        if (posX + 4 > cenaAtual.getMaxScreen() || posX - 4 < cenaAtual.getMinScreen()) {
            velX = -velX;
        }

        if (posX + 2 > retangulo.getxEsquerda() && posX - 2 < retangulo.getxDireita() &&
                posY + 2 > retangulo.getyBaixo() && posY - 2 < retangulo.getyCima()) {

            if(posY + -retangulo.getyCima() >= -4 && posY + -retangulo.getyCima() < 0 &&
                    (posX + -retangulo.getxDireita() >= -4 && posX + -retangulo.getxDireita() < 0) ||
                    (posX + -retangulo.getxEsquerda() >= -4 && posX + -retangulo.getxEsquerda() < 0)) {
                velX = -velX;
            } else {
                velY = -velY;
                jogadorAtual.atualizarPontos();
            }
        }

        if(posY + 4 > cenaAtual.getMaxScreen()) {
            velY = getRandomVel(-velY);
        }

        if (posY - 2 < cenaAtual.getMinScreen()) {
            velY = 0;
            velX = 0;
            jogadorAtual.atualizarVida();
            if(jogadorAtual.fase == 2) {
                cenaAtual.renovarBolinha(4f);
                return;
            }
            cenaAtual.renovarBolinha(1.7f);
        }
    }
    private float getRandomVel(float vel) {
        randomNumber = random.nextFloat(baseVel);
        if(vel >= 0) {
            vel = randomNumber;
        } else if (vel < 0) {
            vel = -randomNumber;
        }
        if(vel <= minimumVel && vel > 0) {
            vel = (baseVel * 0.75f);
        } else if (vel > -minimumVel && vel < 0) {
            vel = -(baseVel * 0.75f);
        }
        return vel;
    }
}