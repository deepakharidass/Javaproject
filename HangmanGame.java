import java.util.Scanner;

public class HangmanGame {

    private static final String[] WORDS = {"hangman", "java", "programming", "code", "computer"};

    private String selectedWord;
    private StringBuilder guessedWord;
    private int incorrectAttempts;

    public HangmanGame() {
        selectedWord = selectRandomWord();
        guessedWord = new StringBuilder("_".repeat(selectedWord.length()));
        incorrectAttempts = 0;
    }

    private String selectRandomWord() {
        int randomIndex = (int) (Math.random() * WORDS.length);
        return WORDS[randomIndex];
    }

    private void displayHangman() {
        System.out.println("Incorrect Attempts: " + incorrectAttempts);

        switch (incorrectAttempts) {
            case 1:
                System.out.println("  O");
                break;
            case 2:
                System.out.println("  O");
                System.out.println("  |");
                break;
            case 3:
                System.out.println("  O");
                System.out.println(" /|");
                break;
            case 4:
                System.out.println("  O");
                System.out.println(" /|\\");
                break;
            case 5:
                System.out.println("  O");
                System.out.println(" /|\\");
                System.out.println(" / ");
                break;
            case 6:
                System.out.println("  O");
                System.out.println(" /|\\");
                System.out.println(" / \\");
                break;
            default:
                break;
        }
    }

    private void displayGuessedWord() {
        System.out.println("Current Word: " + guessedWord);
    }

    private void guessLetter(char letter) {
        boolean found = false;

        for (int i = 0; i < selectedWord.length(); i++) {
            if (selectedWord.charAt(i) == letter) {
                guessedWord.setCharAt(i, letter);
                found = true;
            }
        }

        if (!found) {
            incorrectAttempts++;
        }
    }

    private boolean isGameOver() {
        return incorrectAttempts >= 6 || guessedWord.indexOf("_") == -1;
    }

    public void play() {
        Scanner scanner = new Scanner(System.in);

        while (!isGameOver()) {
            displayHangman();
            displayGuessedWord();

            System.out.print("Enter a letter: ");
            char guess = scanner.next().charAt(0);

            guessLetter(guess);
        }

        displayHangman();
        displayGuessedWord();

        if (guessedWord.indexOf("_") == -1) {
            System.out.println("Congratulations! You guessed the word.");
        } else {
            System.out.println("Sorry! You ran out of attempts. The word was: " + selectedWord);
        }

        scanner.close();
    }

    public static void main(String[] args) {
        HangmanGame hangmanGame = new HangmanGame();
        hangmanGame.play();
    }
}
