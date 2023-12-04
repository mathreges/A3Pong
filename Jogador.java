package cena;

import com.jogamp.opengl.GL2;
import com.jogamp.opengl.util.gl2.GLUT;

public class Jogador {
    public int vida;
    public void desenharVida(GL2 gl) {
        GLUT glut = new GLUT();

        gl.glColor3f(0, 0, 0);
        gl.glRotated(0, 0, 1, 1);
            glut.glutSolidTeapot(2);
        gl.glEnd();
    }
    public int atualizarVida () {
        //System.out.println(vida);
        return vida;
    }

}