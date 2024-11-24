import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Questions {

    private static final String[] QUESTIONS = {
            "What is the default value of a boolean variable in Java?",
            "Which of the following is used to declare a constant in Java?",
            "Which method in Java is used to start a thread?",
            "Which keyword is used to inherit a class in Java?",
            "Which of the following is not a valid data type in Java?",
            "What will be the output of the following code?\n\nint x = 5;\nSystem.out.println(x++);",
            "Which of the following is used to handle exceptions in Java?",
            "What is the size of an int data type in Java?",
            "What is the default value of an object reference variable in Java?",
            "What does the 'super' keyword refer to in Java?"
    };

    private static final String[][] OPTIONS = {
            {"true", "false", "null", "0"},
            {"final", "constant", "static", "immutable"},
            {"start()", "run()", "execute()", "initialize()"},
            {"inherit", "extends", "super", "implements"},
            {"int", "char", "float", "real"},
            {"4", "5", "6", "Error"},
            {"try-catch", "throw-throws", "try-finally", "All of the above"},
            {"2 bytes", "4 bytes", "8 bytes", "16 bytes"},
            {"null", "0", "undefined", "false"},
            {"The current class", "The parent class", "The main method", "An object"}
    };

    private static final int[] CORRECT_ANSWERS = {1, 0, 0, 1, 3, 1, 3, 1, 0, 1}; // Correct answer indexes

    private int currentQuestion = 0;
    private int score = 0;

    private JFrame frame;
    private JTextArea questionArea;
    private JRadioButton[] optionsButtons = new JRadioButton[4];
    private ButtonGroup optionsGroup;
    private JButton nextButton;

    public Questions() {
        frame = new JFrame("Java Quiz");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(1000, 600);
        frame.setLayout(new BorderLayout());

        JPanel topPanel = new JPanel(new BorderLayout());
        topPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        questionArea = new JTextArea();
        questionArea.setEditable(false);
        questionArea.setWrapStyleWord(true);
        questionArea.setLineWrap(true);
        questionArea.setText(formatQuestion(currentQuestion));
        questionArea.setFont(new Font("Arial", Font.BOLD, 18)); // Bold and big font
        questionArea.setMargin(new Insets(10, 10, 10, 10)); // Added padding
        questionArea.setBackground(new Color(245, 245, 245)); // Light background for visibility
        questionArea.setForeground(new Color(24, 57, 43)); // Text color #18392B
        topPanel.add(questionArea, BorderLayout.CENTER);

        frame.add(topPanel, BorderLayout.NORTH);

        // Options panel
        JPanel optionsPanel = new JPanel();
        optionsPanel.setLayout(new GridLayout(4, 1, 10, 10)); // Space between options

        optionsGroup = new ButtonGroup();
        for (int i = 0; i < 4; i++) {
            optionsButtons[i] = new JRadioButton();
            optionsButtons[i].setFont(new Font("Arial", Font.PLAIN, 16)); // Font size for options
            optionsButtons[i].setText(OPTIONS[currentQuestion][i]);
            optionsGroup.add(optionsButtons[i]);
            optionsPanel.add(optionsButtons[i]);
        }

        frame.add(optionsPanel, BorderLayout.CENTER);

        // Next button
        nextButton = new JButton("Next");
        nextButton.setFont(new Font("Arial", Font.BOLD, 14)); // Button font size
        nextButton.setPreferredSize(new Dimension(100, 30)); // Smaller button size
        nextButton.setBackground(new Color(24, 57, 43)); // Custom background color
        nextButton.setForeground(Color.WHITE); // White text for contrast
        nextButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (!isOptionSelected()) {
                    JOptionPane.showMessageDialog(frame, "Please select an option before proceeding.", "Error", JOptionPane.ERROR_MESSAGE);
                } else {
                    checkAnswer();
                    if (currentQuestion < QUESTIONS.length - 1) {
                        currentQuestion++;
                        updateQuestion();
                    } else {
                        showResults();
                    }
                }
            }
        });

        // Add the button at the bottom
        JPanel bottomPanel = new JPanel();
        bottomPanel.add(nextButton);
        frame.add(bottomPanel, BorderLayout.SOUTH);

        frame.setVisible(true);
    }

    private String formatQuestion(int questionIndex) {
        return (questionIndex + 1) + ". " + QUESTIONS[questionIndex];
    }

    private boolean isOptionSelected() {
        for (JRadioButton button : optionsButtons) {
            if (button.isSelected()) {
                return true;
            }
        }
        return false;
    }

    private void checkAnswer() {
        for (int i = 0; i < 4; i++) {
            if (optionsButtons[i].isSelected() && i == CORRECT_ANSWERS[currentQuestion]) {
                score++;
            }
        }
    }

    private void updateQuestion() {
        questionArea.setText(formatQuestion(currentQuestion));
        for (int i = 0; i < 4; i++) {
            optionsButtons[i].setText(OPTIONS[currentQuestion][i]);
        }
        optionsGroup.clearSelection(); // Clear previous selection
    }

    private void showResults() {
        JOptionPane.showMessageDialog(frame, "Your score: " + score + "/" + QUESTIONS.length);
        frame.dispose();
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                new Questions();
            }
        });
    }
}
