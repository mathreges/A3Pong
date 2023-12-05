package cena;

import com.jogamp.opengl.GL2;
import com.jogamp.opengl.GLAutoDrawable;

public class Obstaculo {
    public int xDireita;
    public int xEsquerda;
    public int yCima;
    public int yBaixo;

    public void desenharObstaculo(GLAutoDrawable drawable) {

        GL2 gl = drawable.getGL().getGL2();

        reset();

        gl.glClearColor(0.32549019607f, 0.54509803921f, 0.92549019607f, 1.0f);
        gl.glClear(GL2.GL_COLOR_BUFFER_BIT | GL2.GL_DEPTH_BUFFER_BIT);
        gl.glLoadIdentity();

        gl.glPolygonMode(GL2.GL_FRONT_AND_BACK, GL2.GL_FILL);

        gl.glBegin(GL2.GL_QUADS);
            gl.glVertex2f(xEsquerda, yBaixo);
            gl.glVertex2f(xDireita, yBaixo);
            gl.glVertex2f(xDireita, yCima);
            gl.glVertex2f(xEsquerda, yCima);
        gl.glEnd();

    }

    public void reset(){
        xDireita = 15;
        xEsquerda = -15;

        yCima = 15;
        yBaixo = -15;
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