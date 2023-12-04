import com.jogamp.newt.event.WindowEvent;
import com.jogamp.newt.opengl.GLWindow;
import com.jogamp.opengl.GL2;
import com.jogamp.opengl.GLAutoDrawable;
import com.jogamp.opengl.GLCapabilities;
import com.jogamp.opengl.GLProfile;
import com.jogamp.opengl.util.FPSAnimator;
import com.jogamp.opengl.util.awt.TextRenderer;
import javax.swing.*;
import java.awt.*;
import java.awt.Color;
import com.jogamp.newt.event.WindowAdapter;
import com.jogamp.opengl.util.gl2.GLUT;
import com.jogamp.opengl.*;
import com.jogamp.newt.event.MouseListener;
import com.jogamp.newt.event.MouseEvent;

public class Renderer implements GLEventListener {
    public static Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
    public static int screenWidth = screenSize.width;
    public static int screenHeight = screenSize.height;
    private TextRenderer textRenderer;
    private static GLWindow window = null;
    private final float startButtonX = -0.2f;
    private final float startButtonY = 0.2f;
    private final float startButtonWidth = 0.4f;
    private final float startButtonHeight = 0.1f;


    @Override
    public void init(GLAutoDrawable drawable) {
        GL2 gl = drawable.getGL().getGL2();
        gl.glClearColor(0.0f, 0.0f, 0.0f, 1.0f);
    }

    @Override
    public void display(GLAutoDrawable drawable) {
        GL2 gl = drawable.getGL().getGL2();
        gl.glClear(GL2.GL_COLOR_BUFFER_BIT);
        GLUT glut = new GLUT();

        gl.glLoadIdentity();
        gl.glColor3f(1.0f, 1.0f, 1.0f);

        gl.glLoadIdentity();
        gl.glColor3f(1.0f, 1.0f, 1.0f);
        gl.glRasterPos2f(startButtonX, startButtonY);
        glut.glutBitmapString(GLUT.BITMAP_HELVETICA_18, "Início");

        gl.glRasterPos2f(-0.2f, 0.0f);
        glut.glutBitmapString(GLUT.BITMAP_HELVETICA_18, "Sair");
    }

    public static void main(String[] args) {
        GLProfile.initSingleton();
        GLProfile profile = GLProfile.get(GLProfile.GL2);
        GLCapabilities caps = new GLCapabilities(profile);
        window = GLWindow.create(caps);

        window.setTitle("Menu Pong");

        Renderer menuExample = new Renderer();
        window.addGLEventListener(menuExample);

        window.setSize(screenWidth, screenHeight);
        window.setResizable(false);
        window.setVisible(true);

        final Renderer Renderer = new Renderer();
        window.addGLEventListener(Renderer);

        window.addMouseListener(new MouseListener() {
        @Override
        public void mouseClicked(MouseEvent e) {
            float x = e.getX();
            float y = e.getY();

            float normalizedX = (2.0f * x) / window.getWidth() - 1.0f;
            float normalizedY = 1.0f - (2.0f * y) / window.getHeight();

            if (normalizedX >= Renderer.startButtonX && normalizedX <= Renderer.startButtonX + Renderer.startButtonWidth &&
                    normalizedY >= Renderer.startButtonY && normalizedY <= Renderer.startButtonY + Renderer.startButtonHeight) {
                Cena cena = new Cena();

                window.setTitle("Jogo Pong");

                window.addGLEventListener(cena);
                window.addKeyListener(new KeyBoard(cena));
            }
        }

        public void mouseWheelMoved(MouseEvent e) {
        }
        @Override
        public void mouseMoved(MouseEvent e) {
        }
        public void mouseDragged(MouseEvent e) {
        }
        @Override
        public void mouseEntered(MouseEvent e) {}
        @Override
        public void mouseExited(MouseEvent e) {}
        @Override
        public void mousePressed(MouseEvent e) {}
        @Override
        public void mouseReleased(MouseEvent e) {}
        });

        window.setVisible(true);

        FPSAnimator animator = new FPSAnimator(window, 60);
        animator.start();

        window.addWindowListener(new WindowAdapter() {
            public void windowDestroyNotify(WindowEvent e) {
                animator.stop();
                System.exit(0);
            }
        });
    }
    public void desenhaTexto(GL2 gl, int xPosicao, int yPosicao, Color cor, String frase){

        gl.glPolygonMode(GL2.GL_FRONT_AND_BACK, GL2.GL_FILL);
        textRenderer.beginRendering(Renderer.screenWidth, Renderer.screenHeight);
        textRenderer.setColor(cor);
        textRenderer.draw(frase, xPosicao, yPosicao);
        textRenderer.endRendering();
        gl.glPolygonMode(GL2.GL_FRONT_AND_BACK, GL2.GL_FILL);
    }

    @Override
    public void reshape(GLAutoDrawable drawable, int x, int y, int width, int height) {
    }

    @Override
    public void dispose(GLAutoDrawable drawable) {
    }
}