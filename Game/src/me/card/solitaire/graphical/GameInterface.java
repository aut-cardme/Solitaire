package me.card.solitaire.graphical;

import me.card.solitaire.general.Board;
import me.card.solitaire.general.card.Card;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.util.Arrays;

public class GameInterface extends JPanel{
    /**
     * Launch the application.
     */
    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                try {
                    JFrame frame = new JFrame("Solitaire");
                    frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

                    GameInterface window = new GameInterface();
                    frame.getContentPane().add(window);
                    frame.pack();
                    frame.setVisible(true);
                    frame.setResizable(false);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }

    private Timer updater;
    private Board board;

    public GameInterface() {
        super();
        setLayout(new BorderLayout());
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (ClassNotFoundException | InstantiationException | IllegalAccessException | UnsupportedLookAndFeelException e) {
            e.printStackTrace();
        }
        initialize();
        updater = new Timer(20, this::repaint);
        updater.start();
    }

    private void initialize() {
        board = new Board();
        board.setup();
        BoardPanel panel = new BoardPanel(this);
        TopPanel top = new TopPanel(this);

        add(panel, BorderLayout.SOUTH);
        add(top, BorderLayout.NORTH);
    }

    public Board getBoard() {
        return board;
    }

    public void drawCard(Graphics g, int x, int y, Card card) {
        g.setFont(g.getFont().deriveFont(20f));
        g.setColor(Color.WHITE);
        g.drawRect(x, y, 79, 99);
        g.setColor(Color.BLUE);
        g.fillRect(x + 1, y + 1, 78, 98);


        g.setColor(Color.WHITE);
        if (card == null) {
            g.drawString("??", x + 30, y + 55);
        } else {
            if (card.isHidden()) {
                g.drawString("??", x + 5, y + 20);
            } else {
                g.setColor(card.getSuit().isRed() ? Color.RED : Color.BLACK);
                g.drawString(card.getDisplay(), x + 5, y + 20);
            }
        }
    }

    public void repaint(ActionEvent event) {
        super.repaint();
    }

    @Override
    protected void paintChildren(Graphics g) {
        super.paintChildren(g);
        Arrays.stream(getComponents()).forEach(c->callGraphicsPainter(g, c));
    }

    private void callGraphicsPainter(Graphics g, Component component){
        if(component instanceof Container){
            Arrays.stream(((Container) component).getComponents()).forEach(c -> callGraphicsPainter(g, c));
        }
        if(component instanceof GraphicsPainter){
            ((GraphicsPainter) component).painter(g);
        }
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
    }

    public Point getMouseLocation() {
        java.awt.Point mouse = MouseInfo.getPointerInfo().getLocation();
        java.awt.Point panel = getLocationOnScreen();
        return new Point(mouse.getX() - panel.getX(), mouse.getY() - panel.getY());
    }
}
