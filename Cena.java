package cena;

import com.jogamp.opengl.GL2;
import com.jogamp.opengl.GLAutoDrawable;
import com.jogamp.opengl.GLEventListener;
import com.jogamp.opengl.glu.GLU;
import com.jogamp.opengl.util.awt.TextRenderer;
import com.jogamp.opengl.util.gl2.GLUT;
import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.*;

public class Cena implements GLEventListener{
    private float xMin, xMax, yMin, yMax, zMin, zMax;
    private TextRenderer textRenderer;
    public int op;
    private Bolinha bolinha;
    private Timer timer;
    public int pontos;

    public Retangulo retangulo;

    private int minScreen = -104;

    private int maxScreen = 104;

    //Dados do Quadrado
    public int xDireita, xEsquerda, yCima, yBaixo;

    //dados da esfera
    public float radio;
    public int stacks, xBola, yBola;

    //Preenchimento
    public int mode;

    @Override
    public void init(GLAutoDrawable drawable) {
        GL2 gl = drawable.getGL().getGL2();
        xMin = yMin = zMin = this.minScreen;
        xMax = yMax = zMax = this.maxScreen;

        retangulo = new Retangulo();

        reset();
        retangulo.reset();

        textRenderer = new TextRenderer(new Font("Comic Sans MS Negrito", Font.BOLD, 15));
        gl.glEnable(GL2.GL_DEPTH_TEST);

        bolinha = new Bolinha();

        bolinha.init(1.7f);

        int delay = 16;
        ActionListener taskPerformer = e -> {
            bolinha.atualizar(retangulo);
            drawable.display();
        };
        timer = new Timer(delay, taskPerformer);
        timer.start();
    }

    public int getMinScreen() {
        return this.minScreen;
    }

    public int getMaxScreen() {
        return this.maxScreen;
    }

    public void reset(){

        pontos = 0;

        //Dados Quadrado
        xDireita = 8;
        xEsquerda = -8;

        yCima = -96;
        yBaixo = -100;

        //Dados Bola
        radio = 20;
        stacks = 10;
        xBola = 0;
        yBola = 0;

        mode = GL2.GL_FILL;
    }

    @Override
    public void display(GLAutoDrawable drawable) {
        GL2 gl = drawable.getGL().getGL2();
        GLUT glut = new GLUT();

        gl.glClearColor(1, 1, 1, 1);
        gl.glClear(GL2.GL_COLOR_BUFFER_BIT | GL2.GL_DEPTH_BUFFER_BIT);
        gl.glLoadIdentity();

        gl.glPolygonMode(GL2.GL_FRONT_AND_BACK, mode);

        String m = mode == GL2.GL_LINE ? "LINE" : "FILL";

        desenhaTexto(gl, 0, (int) (Renderer.screenHeight*0.95), Color.BLACK, "Pontos: " + pontos);

        //Retangulo
        retangulo.desenharRetangulo(gl, xDireita, xEsquerda, yCima, yBaixo);

        //Bola
        bolinha.desenharBolinha(gl, radio, stacks);
        gl.glFlush();
    }

    public void desenhaTexto(GL2 gl, int xPosicao, int yPosicao, Color cor, String frase){
        gl.glPolygonMode(GL2.GL_FRONT_AND_BACK, GL2.GL_FILL);
        textRenderer.beginRendering(Renderer.screenWidth, Renderer.screenHeight);
        textRenderer.setColor(cor);
        textRenderer.draw(frase, xPosicao, yPosicao);
        textRenderer.endRendering();
        gl.glPolygonMode(GL2.GL_FRONT_AND_BACK, mode);
    }

    @Override
    public void reshape(GLAutoDrawable drawable, int x, int y, int width, int height) {
        GL2 gl = drawable.getGL().getGL2();

        gl.glMatrixMode(GL2.GL_PROJECTION);
        gl.glLoadIdentity();

        gl.glOrtho(xMin, xMax, yMin, yMax, zMin, zMax);

        gl.glMatrixMode(GL2.GL_MODELVIEW);
        gl.glLoadIdentity();
        System.out.println("Reshape: " + width + ", " + height);
    }

    @Override
    public void dispose(GLAutoDrawable drawable) {}
}