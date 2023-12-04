package cena;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class MenuPong extends JFrame {

    public MenuPong() {
        setTitle("Menu Pong");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(300, 200);

        JPanel panel = new JPanel();
        panel.setLayout(new BorderLayout());

        JLabel titleLabel = new JLabel("Pong Menu");
        titleLabel.setHorizontalAlignment(SwingConstants.CENTER);
        panel.add(titleLabel, BorderLayout.NORTH);

        JButton startButton = new JButton("Iniciar Jogo");
        startButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                iniciarJogo();
            }
        });
        panel.add(startButton, BorderLayout.CENTER);

        add(panel);
        setLocationRelativeTo(null);
        setVisible(true);
    }

    private void iniciarJogo() {
        Renderer renderer = new Renderer();
        Renderer.init();
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                new MenuPong();
            }
        });
    }
}