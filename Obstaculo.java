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

        gl.glPolygonMode(GL2.GL_FRONT_AND_BACK, GL2.GL_FILL);

        gl.glColor3f(1, 1, 1);

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