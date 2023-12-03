import com.jogamp.opengl.GL2;
import com.jogamp.opengl.GLAutoDrawable;
import com.jogamp.opengl.GLEventListener;
import com.jogamp.opengl.glu.GLU;

public class Retangulo {
    public void desenharQuadrado(GL2 gl, int xDireita, int xEsquerda, int yCima, int yBaixo) {

        gl.glColor3f(0,0,1);

        gl.glBegin(GL2.GL_QUADS);
            gl.glVertex2f(xDireita, yCima);
            gl.glVertex2f(xDireita, yBaixo);
            gl.glVertex2f(xEsquerda, yBaixo);
            gl.glVertex2f(xEsquerda, yCima);
        gl.glEnd();
    }
}