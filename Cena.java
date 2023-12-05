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
import java.text.DecimalFormat;

public class Cena implements GLEventListener{
    private float xMin, xMax, yMin, yMax, zMin, zMax;
    private TextRenderer textRenderer;
    public int op;
    private Bolinha bolinha;
    private Timer timer;
    public int pontos;
    public int vidas;
    public Jogador jogador;
    public Retangulo retangulo;
    public StatusJogo statusjogo;
    public Renderer renderer;
    public Obstaculo obstaculo;
    private int minScreen = -104;
    private int maxScreen = 104;
    public String texto = "", valor;

    //Dados do Quadrado
    public int xDireita, xEsquerda, yCima, yBaixo;

    //dados da esfera
    public float radio;
    public int stacks, xBola, yBola;

    //Dados Vida
    public int xVida;
    private int yVida;
    private int i;

    //Dados do jogo
    public int status;

    //Preenchimento
    public int mode;

    @Override
    public void init(GLAutoDrawable drawable) {
        GL2 gl = drawable.getGL().getGL2();
        xMin = yMin = zMin = this.minScreen;
        xMax = yMax = zMax = this.maxScreen;

        retangulo = new Retangulo();
        jogador = new Jogador();
        statusjogo = new StatusJogo();
        renderer = new Renderer();
        obstaculo = new Obstaculo();

        jogador.init(this);

        reset();
        retangulo.reset();

        textRenderer = new TextRenderer(new Font("Arial", Font.BOLD, 15));
        gl.glEnable(GL2.GL_DEPTH_TEST);

        bolinha = new Bolinha();

        bolinha.init(1.5f, jogador, statusjogo, this);

        int delay = 16;
        ActionListener taskPerformer = e -> {
            if (status != 1) {
                texto = "";
                drawable.display();
                updateStatus();
                bolinha.atualizar(retangulo, obstaculo, drawable);
            }
        };
        timer = new Timer(delay, taskPerformer);
        timer.start();
    }

    public void renovarBolinha(float velocidadeBase) {
        bolinha = new Bolinha();
        bolinha.init(velocidadeBase, jogador, statusjogo, this);
    }

    public void trocarDeFase(GLAutoDrawable drawable) {
        jogador.pontos = 0;
        renovarBolinha(2.7f);
        jogador.setVida(5);
        jogador.setFase(2);
    }

    public void updateStatus() {

        statusjogo.status = status;

        if (jogador.getVidas() <= 0) {

            reset();
            retangulo.reset();

            renovarBolinha(0);

            status = 2;
            texto = "VOCÊ É RUIM !!! KKKKKK";

            if (!timer.isRunning()) {
                timer.restart();
            }
        }

        if (statusjogo.status() == "pausado") {
            status = 1;
        } else if (statusjogo.status() == "parado") {
            reset();
            retangulo.reset();

            bolinha = new Bolinha();
            bolinha.init(1.7f, jogador, statusjogo, this);

            status = 1;
            jogador.setVida(5);
            jogador.setFase(1);
            jogador.pontos = 0;

            renovarBolinha(1.5f);

            if (!timer.isRunning()) {
                timer.restart();
            }
        }
    }

    public int getMinScreen() {
        return this.minScreen;
    }
    public int getMaxScreen() {
        return this.maxScreen;
    }

    @Override
    public void display(GLAutoDrawable drawable) {
        GL2 gl = drawable.getGL().getGL2();

        gl.glClearColor(0.32549019607f, 0.54509803921f, 0.92549019607f, 1.0f);
        gl.glClear(GL2.GL_COLOR_BUFFER_BIT | GL2.GL_DEPTH_BUFFER_BIT);
        gl.glLoadIdentity();

        gl.glPolygonMode(GL2.GL_FRONT_AND_BACK, mode);

        desenhafundo(gl);

        if (jogador.fase == 2) {
            gl.glPushMatrix();
            obstaculo.desenharObstaculo(drawable);
            gl.glPopMatrix();
        }


        valor = statusjogo.status();

        desenhaTexto(gl, 15, (int) (Renderer.screenHeight*0.95), Color.BLACK, "Pontos: " + jogador.pontos);
        desenhaTexto(gl, 15, (int) (Renderer.screenHeight*0.91), Color.BLACK, "Vidas: ");
        desenhaTexto(gl, 15, (int) (Renderer.screenHeight*0.87), Color.BLACK, "Fase: " + jogador.fase);
        desenhaTexto(gl, 15, (int) (Renderer.screenHeight*0.83), Color.BLACK, "Status: " + valor);

        desenhaTexto(gl, (int) (renderer.screenWidth*0.5), (int) (renderer.screenHeight*0.8), Color.BLACK, texto);

        //Retangulo
        retangulo.desenharRetangulo(gl, xDireita, xEsquerda, yCima, yBaixo);

        //Vida
        for(i = 0; i < jogador.getVidas(); i++) {
            gl.glPushMatrix();
            gl.glTranslatef(xVida + 0.2f + (i * 4f), yVida, 0);
            jogador.desenharVida(gl);
            gl.glPopMatrix();
        }

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

    public void reset(){

        pontos = 0;
        vidas = 5;

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

        //Dados Vida
        xVida = -95;
        yVida = 86;

        mode = GL2.GL_FILL;
    }

    public void desenhafundo(GL2 gl){
        gl.glPushMatrix();
        gl.glTranslatef(0.0f, 0.0f, -1.0f);
        gl.glColor3f(0.0f, 0.0f, 0.0f);
        gl.glLineWidth(10.0f);
        gl.glBegin(GL2.GL_LINE_LOOP);
        gl.glVertex2f(-103.0f, -103.0f);
        gl.glVertex2f(103.0f, -103.0f);
        gl.glVertex2f(103.0f, 103.0f);
        gl.glVertex2f(-103.0f, 103.0f);
        gl.glEnd();
        gl.glPopMatrix();

        gl.glPushMatrix();
        gl.glTranslatef(0.0f, 0.0f, -2.0f);
        gl.glLineWidth(5.0f);
        gl.glColor4f(1.0f, 1.0f, 1.0f, 0.5f);

        gl.glBegin(GL2.GL_LINES);
        gl.glVertex2f(0.0f, -102.5f);
        gl.glVertex2f(0.0f, 102.5f);
        gl.glEnd();
        gl.glPopMatrix();

        //---------------------------------------

        gl.glPushMatrix();
        gl.glTranslatef(0.0f, 0.0f, -1.0f);
        gl.glLineWidth(3.0f);

        gl.glColor3f(0.0f, 0.0f, 0.0f);
        gl.glBegin(GL2.GL_LINES);
        for (float i = -102.5f; i <= 102.5f; i += 2.0f) {
            gl.glVertex2f(i, -4.0f);
            gl.glVertex2f(i, 4.0f);
        }
        gl.glEnd();

        for (float i = -4.0f; i <= 4.0f; i += 2.0f) {
            gl.glBegin(GL2.GL_LINES);
            gl.glVertex2f(-102.5f, i);
            gl.glVertex2f(102.5f, i);
            gl.glEnd();
        }

        gl.glColor3f(1.0f, 1.0f, 1.0f);
        gl.glBegin(GL2.GL_LINES);
        gl.glVertex2f(-102.5f, 4.0f);
        gl.glVertex2f(102.5f, 4.0f);
        gl.glEnd();

        gl.glPopMatrix();
    }

    @Override
    public void reshape(GLAutoDrawable drawable, int x, int y, int width, int height) {
        GL2 gl = drawable.getGL().getGL2();

        gl.glMatrixMode(GL2.GL_PROJECTION);
        gl.glLoadIdentity();

        gl.glOrtho(xMin, xMax, yMin, yMax, zMin, zMax);

        gl.glMatrixMode(GL2.GL_MODELVIEW);
        gl.glLoadIdentity();
    }

    @Override
    public void dispose(GLAutoDrawable drawable) {}
}