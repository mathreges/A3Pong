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
    public int vidas;
    public Jogador jogador;
    public Retangulo retangulo;
    public StatusJogo statusjogo;
    public Renderer renderer;
    private int minScreen = -104;
    private int maxScreen = 104;

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

        reset();
        retangulo.reset();

        textRenderer = new TextRenderer(new Font("Comic Sans MS Negrito", Font.BOLD, 15));
        gl.glEnable(GL2.GL_DEPTH_TEST);

        bolinha = new Bolinha();

        bolinha.init(1.7f);

        int delay = 16;
        ActionListener taskPerformer = e -> {

            if (vidas == 0){
                statusjogo.status = 4;
                System.out.println(statusjogo.status);
            }

            statusjogo.status = status;

            if (statusjogo.status() == "iniciar") {
                if(bolinha.atualizar(retangulo) == "vida"){
                    status = 3;
                } else if (bolinha.atualizar(retangulo) == "ponto") {
                    pontos += 5;
                    status = 0;
                }
                drawable.display();
            } else if (statusjogo.status() == "pausar") {
                //pausa o jogo
            } else if (statusjogo.status() == "parar") {
                reset();
                retangulo.reset();

                bolinha = new Bolinha();
                bolinha.init(1.7f);

                status = 0;

                if (!timer.isRunning()) {
                    timer.restart();
                }
            } else if (statusjogo.status() == "vida"){

                retangulo.reset();

                bolinha = new Bolinha();
                bolinha.init(1.7f);

                vidas -= 1;
                jogador.vida = vidas;
                status = 0;
            } else if (statusjogo.status() == "perdeu") {

                reset();
                retangulo.reset();

                bolinha = new Bolinha();
                bolinha.init(1.7f);

                desenhaTexto(gl, 20, 580, Color.BLACK ,"VOCÊ PERDEU!");

                status = 2;

                if (!timer.isRunning()) {
                    timer.restart();
                }
            }
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

    @Override
    public void display(GLAutoDrawable drawable) {
        GL2 gl = drawable.getGL().getGL2();

        gl.glClearColor(1, 1, 1, 1);
        gl.glClear(GL2.GL_COLOR_BUFFER_BIT | GL2.GL_DEPTH_BUFFER_BIT);
        gl.glLoadIdentity();

        gl.glPolygonMode(GL2.GL_FRONT_AND_BACK, mode);

        desenhaTexto(gl, 0, (int) (Renderer.screenHeight*0.95), Color.BLACK, "Pontos: " + pontos);
        desenhaTexto(gl, 0, (int) (Renderer.screenHeight*0.91), Color.BLACK, "Vidas: ");

        //Retangulo
        retangulo.desenharRetangulo(gl, xDireita, xEsquerda, yCima, yBaixo);

        jogador.vida = vidas;

        //Vida
        for(i = 0; i < jogador.atualizarVida(); i++) {
            gl.glPushMatrix();
            gl.glTranslatef(xVida + (i * 6.5f), yVida, 0);
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