package me.card.solitaire.graphical;

import me.card.solitaire.database.SolitaireDatabase;
import me.card.solitaire.general.Board;
import me.card.solitaire.general.card.Card;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.Arrays;
import java.util.List;

/**
 * The type Game interface.
 */
public class GameInterface extends JPanel {
    /**
     * Launch the application.
     *
     * @param args the input arguments
     */
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            try {
                JFrame frame = new JFrame("Solitaire");
                frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

                GameInterface window = new GameInterface();
                frame.getContentPane().add(window);
                frame.setJMenuBar(window.getMenuBar());
                frame.pack();
                frame.setVisible(true);
                frame.setResizable(false);
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
    }

    private SolitaireDatabase database;
    private Board board;
    private long started;

    /**
     * Instantiates a new Game interface.
     */
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
        database = new SolitaireDatabase("localhost", "1527", "test");
        database.connect();
    }

    private JMenuBar getMenuBar() {
        JMenuBar menu = new JMenuBar();
        JMenu highscores = new JMenu("Highscores");
        menu.add(highscores);
        highscores.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                List<String> highscores = database.getHighscores();
                StringBuilder info = new StringBuilder();
                for (int i = 0; i < Math.min(10, highscores.size()); i++) {
                    if (i != 0) {
                        info.append("\n");
                    }
                    info.append(highscores.get(i));
                }
                JOptionPane.showMessageDialog(GameInterface.this, info, "Highscores",JOptionPane.INFORMATION_MESSAGE);
            }
        });
        return menu;
    }

    /**
     * Gets board.
     *
     * @return the board
     */
    public Board getBoard() {
        return board;
    }

    /**
     * Draw card.
     *
     * @param g    the g
     * @param x    the x
     * @param y    the y
     * @param card the card
     */
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

    /**
     * Repaint.
     *
     * @param event the event
     */
    public void repaint(ActionEvent event) {
        super.repaint();
    }

    @Override
    protected void paintChildren(Graphics g) {
        super.paintChildren(g);
        g.setColor(Color.BLACK);
        int seconds = (int) (System.currentTimeMillis() - started) / 1000;
        int minutes = seconds / 60;
        seconds = seconds % 60;
        g.setFont(g.getFont().deriveFont(20f));
        g.drawString("Time: " + (minutes == 0 ? "" : minutes + ":") + seconds, 150, 25);
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

    /**
     * Gets mouse location.
     *
     * @return the mouse location
     */
    public Point getMouseLocation() {
        java.awt.Point mouse = MouseInfo.getPointerInfo().getLocation();
        java.awt.Point panel = getLocationOnScreen();
        return new Point(mouse.getX() - panel.getX(), mouse.getY() - panel.getY());
    }

    /**
     * Check finished.
     */
    public void checkFinished() {
        if (board.isFinished()) {
            int seconds = (int) (System.currentTimeMillis() - started) / 1000;
            String name = JOptionPane.showInputDialog(null, "Took " + seconds + " seconds to complete\nEnter name for highscores");
            database.addHighscore(name, seconds);
            board.setup();
            started = System.currentTimeMillis();
        }
    }
}
