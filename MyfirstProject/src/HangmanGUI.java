import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Random;

public class HangmanGUI extends JFrame {

    private String[] words = {"java", "programming", "hangman", "computer", "algorithm", "developer"};
    private String selectedWord;
    private StringBuilder currentGuess;
    private int maxAttempts = 6;
    private int incorrectGuesses = 0;

    private JLabel wordLabel;
    private JLabel incorrectGuessesLabel;
    private JTextField guessField;
    private JButton guessButton;
    private HangmanPanel hangmanPanel;

    public HangmanGUI() {
        initializeGame();
        setupUI();
    }

    private void initializeGame() {
        selectedWord = words[new Random().nextInt(words.length)];
        currentGuess = new StringBuilder();

        for (int i = 0; i < selectedWord.length(); i++) {
            currentGuess.append("_");
        }
    }

    private void setupUI() {
        setTitle("Hangman Game");
        setSize(400, 400);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        wordLabel = new JLabel("Current Word: " + currentGuess.toString());
        incorrectGuessesLabel = new JLabel("Incorrect Guesses: " + incorrectGuesses);
        guessField = new JTextField(1);
        guessButton = new JButton("Guess");

        hangmanPanel = new HangmanPanel();
        hangmanPanel.setPreferredSize(new Dimension(200, 200));

        guessButton.addActionListener(e -> processGuess());

        JPanel topPanel = new JPanel();
        topPanel.setLayout(new FlowLayout());
        topPanel.add(wordLabel);
        topPanel.add(incorrectGuessesLabel);

        JPanel centerPanel = new JPanel();
        centerPanel.setLayout(new BorderLayout());
        centerPanel.add(topPanel, BorderLayout.NORTH);
        centerPanel.add(hangmanPanel, BorderLayout.CENTER);

        JPanel bottomPanel = new JPanel();
        bottomPanel.setLayout(new FlowLayout());
        bottomPanel.add(guessField);
        bottomPanel.add(guessButton);

        add(centerPanel, BorderLayout.CENTER);
        add(bottomPanel, BorderLayout.SOUTH);

        setVisible(true);
    }

    private void processGuess() {
        if (guessField.getText().isEmpty()) {
            JOptionPane.showMessageDialog(this, "Please enter a guess.");
            return;
        }

        char guess = guessField.getText().charAt(0);

        if (selectedWord.contains(String.valueOf(guess))) {
            updateCurrentGuess(guess);
        } else {
            incorrectGuesses++;
            incorrectGuessesLabel.setText("Incorrect Guesses: " + incorrectGuesses);
            hangmanPanel.addIncorrectGuess();
        }

        updateLabels();

        if (incorrectGuesses >= maxAttempts || currentGuess.toString().equals(selectedWord)) {
            displayGameResult();
        }
    }

    private void updateCurrentGuess(char guess) {
        for (int i = 0; i < selectedWord.length(); i++) {
            if (selectedWord.charAt(i) == guess) {
                currentGuess.setCharAt(i, guess);
            }
        }
        hangmanPanel.setCorrectGuess(currentGuess.toString());
    }

    private void updateLabels() {
        wordLabel.setText("Current Word: " + currentGuess.toString());
    }

    private void displayGameResult() {
        if (currentGuess.toString().equals(selectedWord)) {
            JOptionPane.showMessageDialog(this, "Congratulations! You guessed the word: " + selectedWord);
        } else {
            JOptionPane.showMessageDialog(this, "Sorry, you ran out of attempts. The word was: " + selectedWord);
        }

        int choice = JOptionPane.showConfirmDialog(this, "Do you want to play again?", "Play Again", JOptionPane.YES_NO_OPTION);
        if (choice == JOptionPane.YES_OPTION) {
            resetGame();
        } else {
            dispose();
        }
    }

    private void resetGame() {
        selectedWord = words[new Random().nextInt(words.length)];
        currentGuess = new StringBuilder();

        for (int i = 0; i < selectedWord.length(); i++) {
            currentGuess.append("_");
        }

        incorrectGuesses = 0;

        wordLabel.setText("Current Word: " + currentGuess.toString());
        incorrectGuessesLabel.setText("Incorrect Guesses: " + incorrectGuesses);
        hangmanPanel.reset();
    }

    private class HangmanPanel extends JPanel {
        private String correctGuess = "";
        private int incorrectGuessesCount = 0;

        public void setCorrectGuess(String correctGuess) {
            this.correctGuess = correctGuess;
            repaint();
        }

        public void addIncorrectGuess() {
            incorrectGuessesCount++;
            repaint();
        }

        public void reset() {
            correctGuess = "";
            incorrectGuessesCount = 0;
            repaint();
        }

        @Override
        protected void paintComponent(Graphics g) {
            super.paintComponent(g);

            int width = getWidth();
            int height = getHeight();

            g.setColor(Color.BLACK);

            // Draw Hangman figure based on the number of incorrect guesses
            switch (incorrectGuessesCount) {
                case 1:
                    drawHead(g, width / 2, height / 4);
                    break;
                case 2:
                    drawBody(g, width / 2, height / 4);
                    break;
                case 3:
                    drawLeftArm(g, width / 2, height / 4);
                    break;
                case 4:
                    drawRightArm(g, width / 2, height / 4);
                    break;
                case 5:
                    drawLeftLeg(g, width / 2, height / 4);
                    break;
                case 6:
                    drawRightLeg(g, width / 2, height / 4);
                    break;
            }
        }

        private void drawHead(Graphics g, int x, int y) {
            int diameter = 40;
            g.drawOval(x - diameter / 2, y, diameter, diameter);
        }

        private void drawBody(Graphics g, int x, int y) {
            int bodyLength = 80;
            g.drawLine(x, y + 40, x, y + bodyLength);
        }

        private void drawLeftArm(Graphics g, int x, int y) {
            int armLength = 40;
            g.drawLine(x, y + 40, x - armLength, y + 80);
        }

        private void drawRightArm(Graphics g, int x, int y) {
            int armLength = 40;
            g.drawLine(x, y + 40, x + armLength, y + 80);
        }

        private void drawLeftLeg(Graphics g, int x, int y) {
            int legLength = 60;
            g.drawLine(x, y + 80, x - legLength, y + 140);
        }

        private void drawRightLeg(Graphics g, int x, int y) {
            int legLength = 60;
            g.drawLine(x, y + 80, x + legLength, y + 140);
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(HangmanGUI::new);
    }
}
