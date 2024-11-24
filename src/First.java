import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.sql.*;
import java.util.regex.*;

public class First {
    private JFrame frame;
    private JPanel cardPanel;
    private JPanel loginPanel;
    private JPanel registerPanel;
    private JTextField usernameField;
    private JPasswordField passwordField;
    private JTextField emailField;

    // Database connection details
    private static final String DB_URL = "jdbc:mysql://127.0.0.1:3306/user_login";
    private static final String DB_USER = "root";
    private static final String DB_PASSWORD = "Hema@595Madhuri"; // Replace with your MySQL password

    public First() {
        // Initialize the frame
        frame = new JFrame("Quiz Bee");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(800, 500);
        frame.setLocationRelativeTo(null);
        frame.getContentPane().setBackground(new Color(0x171717));

        // Initialize card panel
        cardPanel = new JPanel();
        cardPanel.setLayout(new CardLayout());

        // Initialize login and register panels
        loginPanel = new JPanel();
        registerPanel = new JPanel();

        createLoginScreen();
        createRegisterScreen();

        // Add both panels to the card panel
        cardPanel.add(loginPanel, "LoginScreen");
        cardPanel.add(registerPanel, "RegisterScreen");

        frame.add(cardPanel);
        frame.setVisible(true);
    }

    private void createLoginScreen() {
        loginPanel.setLayout(new GridBagLayout());
        loginPanel.setBackground(new Color(0x171717));
        GridBagConstraints gbc = new GridBagConstraints();

        JLabel welcomeLabel = new JLabel("Welcome");
        welcomeLabel.setFont(new Font("Arial", Font.BOLD, 40));
        welcomeLabel.setForeground(Color.WHITE);
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 2;
        gbc.insets = new Insets(10, 10, 20, 10);
        loginPanel.add(welcomeLabel, gbc);

        JLabel quizLabel = new JLabel("Quiz Bee");
        quizLabel.setFont(new Font("Showcard Gothic", Font.BOLD, 50));
        quizLabel.setForeground(new Color(0xDA70D6));
        gbc.gridy = 1;
        loginPanel.add(quizLabel, gbc);

        JLabel usernameLabel = new JLabel("Username:");
        usernameLabel.setForeground(Color.WHITE);
        usernameLabel.setFont(new Font("Arial", Font.PLAIN, 20));
        gbc.gridy = 2;
        gbc.gridwidth = 1;
        loginPanel.add(usernameLabel, gbc);

        usernameField = new JTextField(15);
        usernameField.setFont(new Font("Arial", Font.PLAIN, 20));
        gbc.gridx = 1;
        loginPanel.add(usernameField, gbc);

        JLabel passwordLabel = new JLabel("Password:");
        passwordLabel.setForeground(Color.WHITE);
        passwordLabel.setFont(new Font("Arial", Font.PLAIN, 20));
        gbc.gridx = 0;
        gbc.gridy = 3;
        loginPanel.add(passwordLabel, gbc);

        passwordField = new JPasswordField(15);
        passwordField.setFont(new Font("Arial", Font.PLAIN, 20));
        gbc.gridx = 1;
        loginPanel.add(passwordField, gbc);

        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new FlowLayout(FlowLayout.CENTER, 10, 10));
        buttonPanel.setBackground(new Color(0x171717));

        JButton loginButton = new JButton("Login");
        loginButton.setFont(new Font("Arial", Font.PLAIN, 20));
        loginButton.setBackground(new Color(0x18392b));
        loginButton.setForeground(Color.WHITE);
        loginButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                String username = usernameField.getText();
                String password = new String(passwordField.getPassword());

                if (username.isEmpty() || password.isEmpty()) {
                    JOptionPane.showMessageDialog(frame, "Please enter your details.", "Error", JOptionPane.ERROR_MESSAGE);
                } else if (checkUserExists(username, password)) {
                    JOptionPane.showMessageDialog(frame, "Login successful!", "Success", JOptionPane.INFORMATION_MESSAGE);

                    // Show message box prompting the user to start the quiz
                    JOptionPane.showMessageDialog(frame, "Welcome to Quiz Bee \n"+"Time Limit: You can take as much time as you need to think and answer each question. Relax and answer at your own pace!\n" +
                            "Feel Free to Answer: Answer each question to the best of your ability. There is no rush!\n" +
                            "Automatic Saving: Your answers will be automatically saved. Once you're done, you can submit them at any time.! ", "Start Quiz", JOptionPane.INFORMATION_MESSAGE);

                    // After clicking OK, open the quiz screen
                    new Questions();  // This assumes you have the Questions class
                    frame.dispose();  // Close the login frame
                } else {
                    JOptionPane.showMessageDialog(frame, "User not found. Please register.", "Info", JOptionPane.INFORMATION_MESSAGE);
                    showRegisterScreen();
                }
            }
        });

        JButton registerButton = new JButton("Register");
        registerButton.setFont(new Font("Arial", Font.PLAIN, 20));
        registerButton.setBackground(new Color(0x18392b));
        registerButton.setForeground(Color.WHITE);
        registerButton.addActionListener(e -> showRegisterScreen());

        buttonPanel.add(loginButton);
        buttonPanel.add(registerButton);

        gbc.gridy = 4;
        gbc.gridwidth = 2;
        loginPanel.add(buttonPanel, gbc);
    }

    private void createRegisterScreen() {
        registerPanel.setLayout(new GridBagLayout());
        registerPanel.setBackground(new Color(0x171717));
        GridBagConstraints gbc = new GridBagConstraints();

        JLabel registerLabel = new JLabel("Register New User");
        registerLabel.setFont(new Font("Showcard Gothic", Font.BOLD, 30));
        registerLabel.setForeground(Color.WHITE);
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 2;
        gbc.insets = new Insets(10, 10, 20, 10);
        registerPanel.add(registerLabel, gbc);

        JLabel usernameLabel = new JLabel("Username:");
        usernameLabel.setForeground(Color.WHITE);
        usernameLabel.setFont(new Font("Arial", Font.PLAIN, 20));
        gbc.gridy = 1;
        gbc.gridwidth = 1;
        registerPanel.add(usernameLabel, gbc);

        JTextField regUsernameField = new JTextField(15);
        regUsernameField.setFont(new Font("Arial", Font.PLAIN, 20));
        gbc.gridx = 1;
        registerPanel.add(regUsernameField, gbc);

        JLabel emailLabel = new JLabel("Email:");
        emailLabel.setForeground(Color.WHITE);
        emailLabel.setFont(new Font("Arial", Font.PLAIN, 20));
        gbc.gridx = 0;
        gbc.gridy = 2;
        registerPanel.add(emailLabel, gbc);

        emailField = new JTextField(15);
        emailField.setFont(new Font("Arial", Font.PLAIN, 20));
        gbc.gridx = 1;
        registerPanel.add(emailField, gbc);

        JLabel passwordLabel = new JLabel("Password:");
        passwordLabel.setForeground(Color.WHITE);
        passwordLabel.setFont(new Font("Arial", Font.PLAIN, 20));
        gbc.gridx = 0;
        gbc.gridy = 3;
        registerPanel.add(passwordLabel, gbc);

        JPasswordField regPasswordField = new JPasswordField(15);
        regPasswordField.setFont(new Font("Arial", Font.PLAIN, 20));
        gbc.gridx = 1;
        registerPanel.add(regPasswordField, gbc);

        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new FlowLayout(FlowLayout.CENTER, 10, 10));
        buttonPanel.setBackground(new Color(0x171717));

        JButton registerButton = new JButton("Register");
        registerButton.setFont(new Font("Arial", Font.PLAIN, 20));
        registerButton.setBackground(new Color(0x18392b));
        registerButton.setForeground(Color.WHITE);
        registerButton.addActionListener(e -> {
            String username = regUsernameField.getText();
            String email = emailField.getText();
            String password = new String(regPasswordField.getPassword());

            if (username.isEmpty() || email.isEmpty() || password.isEmpty()) {
                JOptionPane.showMessageDialog(frame, "Please fill all fields.", "Error", JOptionPane.ERROR_MESSAGE);
            } else if (!isValidEmail(email)) {
                JOptionPane.showMessageDialog(frame, "Please enter a valid email.", "Error", JOptionPane.ERROR_MESSAGE);
            } else if (registerUser(username, email, password)) {
                JOptionPane.showMessageDialog(frame, "Registration successful! Please log in.", "Success", JOptionPane.INFORMATION_MESSAGE);
                showLoginScreen();
            } else {
                JOptionPane.showMessageDialog(frame, "Error during registration. Please try again.", "Error", JOptionPane.ERROR_MESSAGE);
            }
        });

        JButton backButton = new JButton("Back");
        backButton.setFont(new Font("Arial", Font.PLAIN, 20));
        backButton.setBackground(new Color(0x18392b));
        backButton.setForeground(Color.WHITE);
        backButton.addActionListener(e -> showLoginScreen());

        buttonPanel.add(registerButton);
        buttonPanel.add(backButton);

        gbc.gridy = 4;
        gbc.gridwidth = 2;
        registerPanel.add(buttonPanel, gbc);
    }

    private boolean checkUserExists(String username, String password) {
        try (Connection conn = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD)) {
            String query = "SELECT * FROM register WHERE username = ? AND password = ?";
            try (PreparedStatement stmt = conn.prepareStatement(query)) {
                stmt.setString(1, username);
                stmt.setString(2, password);
                try (ResultSet rs = stmt.executeQuery()) {
                    return rs.next(); // User found
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    private boolean registerUser(String username, String email, String password) {
        try (Connection conn = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD)) {

            // Ensure the email ends with "@gmail.com"
            if (!email.endsWith("@gmail.com")) {
                email = email + "@gmail.com"; // Append "@gmail.com" if it's not already there
            }

            // First, check if the email already exists in the database
            String checkQuery = "SELECT * FROM register WHERE email = ?";
            try (PreparedStatement checkStmt = conn.prepareStatement(checkQuery)) {
                checkStmt.setString(1, email);
                try (ResultSet rs = checkStmt.executeQuery()) {
                    if (rs.next()) {  // If a record is found with the same email
                        JOptionPane.showMessageDialog(frame, "Email is already registered. Please use a different email.", "Error", JOptionPane.ERROR_MESSAGE);
                        return false;  // Return false if email already exists
                    }
                }
            }

            // If the email does not exist, proceed with inserting the new user
            String query = "INSERT INTO register (username, email, password) VALUES (?, ?, ?)";
            try (PreparedStatement stmt = conn.prepareStatement(query)) {
                stmt.setString(1, username);
                stmt.setString(2, email);
                stmt.setString(3, password);
                return stmt.executeUpdate() > 0; // Return true if insertion is successful
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false; // Return false in case of error
    }



    private boolean isValidEmail(String email) {
        String emailRegex = "^[a-zA-Z0-9_+&*-]+(?:\\.[a-zA-Z0-9_+&*-]+)*@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,7}$";
        Pattern pattern = Pattern.compile(emailRegex);
        return pattern.matcher(email).matches();
    }

    private void showRegisterScreen() {
        ((CardLayout) cardPanel.getLayout()).show(cardPanel, "RegisterScreen");
    }

    private void showLoginScreen() {
        ((CardLayout) cardPanel.getLayout()).show(cardPanel, "LoginScreen");
    }

    public static void main(String[] args) {
        new First();
    }
}
