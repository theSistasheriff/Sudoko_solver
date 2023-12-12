
import java.awt.*;
/*
import java.awt.Color;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
*/
import javax.swing.*;

//import java.awt.BorderLayout;

public class MrUI {
    private Sudoko board;
    private JTextField[][] textField;

    public MrUI(Sudoko board, String title, int width, int height) { // kollad
        this.board = board;
        textField = new JTextField[9][9];

        SwingUtilities.invokeLater(() -> createWindow(title, width, height));
    }

    private void createWindow(String title, int width, int height) { // kollad
        JFrame frame = new JFrame(title);
        Container pane = frame.getContentPane();
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        frame.setPreferredSize(new Dimension(width, height));
        // frame.setResizable(false);
        frame.pack();
        frame.setVisible(true);
        // ett fönster med övre del 9x9 JTextField för siffror

        pane.add(createField(), BorderLayout.NORTH);
        pane.add(Buttons(frame), BorderLayout.SOUTH);
    }

    private JPanel Buttons(JFrame frame) {
        // nedre del 2 knappar CLEAR och SOLVE
        JPanel btns = new JPanel();
        JButton btn1 = new JButton("CLEAR");
        JButton btn2 = new JButton("SOLVE");

        btns.add(btn1);
        btns.add(btn2);

        // btn1 actionListener för att aktivera clear
        btn1.addActionListener(e -> {
            clearSquares();
        });
        // btn 2 actionListener för att aktivera Solve
        btn2.addActionListener(e -> {
            solveBoard(frame);
        });
        return btns;
    }

    private JPanel createField() { // Kollad
        JPanel square = new JPanel();
        // GridPanel gridPanel = new GridPanel(9, 9);
        square.setLayout(new GridLayout(9, 9));
        // Font font = new Font("SansSerif", Font.BOLD, 20);

        for (int row = 0; row < 9; row++) {
            for (int col = 0; col < 9; col++) {
                textField[row][col] = new JTextField("", 1);
                // textField[row][col].setText("");
                // textField[row][col].setHorizontalAlignment(JTextField.CENTER);
                // textField[row][col].setFont(font);
                textField[row][col].setForeground(Color.BLUE);

                if (((row < 3 || row > 5) && (col < 3 || col > 5)) || ((row > 2 && row < 6) && (col > 2 && col < 6))) {
                    textField[row][col].setBackground(new Color(178, 170, 170));
                } else {
                    textField[row][col].setBackground(new Color(95, 166, 208));
                }
                square.add(textField[row][col], BorderLayout.CENTER);

            }
        }
        return square;
    }

    private void solveBoard(Frame frame) { // Halv kollad
        boolean error = false;

        for (int i = 0; i < 9; i++) {
            for (int j = 0; j < 9; j++) {
                String input = textField[i][j].getText();

                if (input.equals("")) {
                    board.set(i, j, 0);
                } else {
                    try {
                        int x = Integer.parseInt(input);

                        if (Integer.parseInt(input) < 1 || Integer.parseInt(input) > 9) {
                            error = true;
                            textField[i][j].setText("");
                        } else {
                            board.set(i, j, x);
                        }
                    } catch (NumberFormatException e) {
                        error = true;
                        textField[i][j].setText("");
                    }
                }
            }
        }

        if (error) {
            JOptionPane.showMessageDialog(frame, "Input i rutor ska endast vara en siffra av 1 till och med 9");
        } else {
            if (!board.solve()) {
                JOptionPane.showMessageDialog(frame,
                        "Sudokot med nuvarande siffror är olösligt \n testa några andra siffror");
            } else {
                for (int i = 0; i < 9; i++) {
                    for (int j = 0; j < 9; j++) {
                        textField[i][j].setText(((Integer) (board.get(i, j))).toString());
                    }
                }
            }
        }

    }

    private void clearSquares() {
        for (int i = 0; i < 9; i++) {
            for (int j = 0; j < 9; j++) {
                textField[i][j].setText("");
            }
        }
        board.clear();
    }

    /*
     * 
     * @return true
     * If legal input in textField
     * 
     *
     * private boolean checkText() {
     * for (int y = 0; y < 9; y++) {
     * for (int x = 0; x < 9; x++) {
     * if (!textField[x][y].getText.equals("")) {
     * try {
     * int digit = Integer.parseInt(textField[x][y].getText());
     * if (digit <= 0 || digit >= 10) {
     * return false;
     * }
     * } catch (NumberFormatExeption e) {
     * return false;
     * }
     * }
     * }
     * }
     * return true;
     * }
     */
}
