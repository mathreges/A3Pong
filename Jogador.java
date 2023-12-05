package cena;

import com.jogamp.opengl.GL2;
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

        gl.glColor3f(0, 0, 0);
        gl.glRotated(0, 0, 1, 1);
            glut.glutSolidTeapot(2);
        gl.glEnd();
    }
    public int atualizarVida () {
        vida--;
        return vida;
    }

    public int atualizarPontos () {
        pontos += 40;
        if (pontos >= 200 && fase == 1) {
            cenaAtual.trocarDeFase();
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