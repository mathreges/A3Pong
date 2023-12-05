package cena;

import com.jogamp.opengl.GL2;
import com.jogamp.opengl.GLAutoDrawable;
import com.jogamp.opengl.util.gl2.GLUT;

public class Jogador {
    public int vida = 5;

    public int fase = 1;
    public Cena cenaAtual;
    public int pontos;

    public void init (Cena cena) {
        this.cenaAtual = cena;
    }
    public void desenharVida(GL2 gl) {
        GLUT glut = new GLUT();

        gl.glColor3f(1, 1, 0);
        gl.glRotated(0, 0, 1, 1);
        glut.glutSolidTorus(0.2, 1.2f, 50, 50);
        gl.glEnd();
    }
    public int atualizarVida () {
        vida--;
        return vida;
    }

    public int atualizarPontos (GLAutoDrawable drawable) {
        pontos += 40;
        if (pontos >= 200 && fase == 1) {
            cenaAtual.trocarDeFase(drawable);
        }
        return pontos;
    }
    public int getPontos() {
        return pontos;
    }

    public int getVidas() {
        return vida;
    }

    public void setVida(int vida) {
        this.vida = vida;
    }

    public void setFase(int fase) {
        this.fase = fase;
    }
}