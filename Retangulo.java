package cena;

import com.jogamp.opengl.GL2;
import com.jogamp.opengl.GLAutoDrawable;
import com.jogamp.opengl.GLEventListener;
import com.jogamp.opengl.glu.GLU;

public class Retangulo {
    public int xDireita;
    public int xEsquerda;
    public int yCima;
    public int yBaixo;

    public void desenharRetangulo(GL2 gl, int xDir, int xEsq, int yCim, int yBaix) {

        xDireita = xDir;
        xEsquerda = xEsq;
        yCima = yCim;
        yBaixo = yBaix;

        gl.glClearColor(0.32549019607f, 0.54509803921f, 0.92549019607f, 1.0f);

        gl.glBegin(GL2.GL_QUADS);
        gl.glVertex2f(xDireita, yCima);
        gl.glVertex2f(xDireita, yBaixo);
        gl.glVertex2f(xEsquerda, yBaixo);
        gl.glVertex2f(xEsquerda, yCima);
        gl.glEnd();
    }

    public void reset(){
        xDireita = 8;
        xEsquerda = -8;

        yCima = -96;
        yBaixo = -100;
    }

    public int getxDireita() {
        return xDireita;
    }

    public int getxEsquerda() {
        return xEsquerda;
    }

    public int getyBaixo() {
        return yBaixo;
    }

    public int getyCima() {
        return yCima;
    }
}