package me.card.solitaire.graphical;

import me.card.solitaire.database.SolitaireDatabase;
import me.card.solitaire.general.Board;
import me.card.solitaire.general.card.Card;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.util.Arrays;

public class GameInterface extends JPanel {
    /**
     * Launch the application.
     */
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            try {
                JFrame frame = new JFrame("Solitaire");
                frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

                GameInterface window = new GameInterface();
                frame.getContentPane().add(window);
                frame.pack();
                frame.setVisible(true);
                frame.setResizable(false);
                //SolitaireDatabase db = new SolitaireDatabase("localhost", "1527", "test");
                //db.connect();
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
    }

    private Board board;
    private long started;

    public GameInterface() {
        super();
        setLayout(new BorderLayout());
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (ClassNotFoundException | InstantiationException | IllegalAccessException | UnsupportedLookAndFeelException e) {
            e.printStackTrace();
        }
        initialize();
        Timer updater = new Timer(20, this::repaint);
        updater.start();
    }

    private void initialize() {
        setBackground(new Color(18, 117, 5));
        board = new Board();
        board.setup();
        started = System.currentTimeMillis();
        BoardPanel panel = new BoardPanel(this);
        TopPanel top = new TopPanel(this);

        add(top, BorderLayout.NORTH);
        add(panel, BorderLayout.SOUTH);
    }

    public Board getBoard() {
        return board;
    }

    public void drawCard(Graphics g, int x, int y, Card card) {
        g.setFont(g.getFont().deriveFont(20f));
        g.setColor(Color.BLACK);
        g.drawRect(x, y, 79, 99);
        g.setColor(Color.WHITE);
        g.fillRect(x + 1, y + 1, 78, 98);


        g.setColor(Color.WHITE);
        if (card == null) {
            g.drawString("??", x + 30, y + 55);
            g.drawImage(Texture.CARD_BACK, x, y, null);
        } else {
            if (card.isHidden()) {
                g.drawString("??", x + 5, y + 20);
                g.drawImage(Texture.CARD_BACK, x, y, null);
            } else {
                g.setColor(card.getSuit().isRed() ? Color.RED : Color.BLACK);
                g.drawString(card.getDisplay(), x + 5, y + 20);
                g.drawString(card.getDisplay(), x + 50, y + 90);
            }
        }
    }

    public void repaint(ActionEvent event) {
        super.repaint();
    }

    @Override
    protected void paintChildren(Graphics g) {
        super.paintChildren(g);
        Arrays.stream(getComponents()).forEach(c -> callGraphicsPainter(g, c));
    }

    private void callGraphicsPainter(Graphics g, Component component) {
        if (component instanceof Container) {
            Arrays.stream(((Container) component).getComponents()).forEach(c -> callGraphicsPainter(g, c));
        }
        if (component instanceof GraphicsPainter) {
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

    public void checkFinished(){
        if(board.isFinished()){

        }
    }
}
