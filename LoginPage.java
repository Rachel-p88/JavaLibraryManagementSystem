import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.HashMap;

public class LoginPage {

    private static final HashMap<String, String> users = new HashMap<>();  // Store username and password pairs

    // Display the login page
    public static void showLoginPage() {
        JFrame loginFrame = new JFrame("Login");
        loginFrame.setSize(400, 300);
        loginFrame.setLocationRelativeTo(null);
        loginFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(4, 2, 10, 10));
        panel.setBackground(new Color(255, 255, 255));  // White background
        panel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        loginFrame.getContentPane().setBackground(new Color(204, 230, 255)); //light blue background

        JLabel usernameLabel = new JLabel("Username:");
        JTextField usernameField = new JTextField();
        JLabel passwordLabel = new JLabel("Password:");
        JPasswordField passwordField = new JPasswordField();

        usernameLabel.setFont(new Font("Arial", Font.BOLD, 14));
        usernameLabel.setForeground(new Color(76, 0, 153));  // Purple color
        passwordLabel.setFont(new Font("Arial", Font.BOLD, 14));
        passwordLabel.setForeground(new Color(76, 0, 153));  // Purple color

        usernameField.setFont(new Font("Arial", Font.PLAIN, 14));
        passwordField.setFont(new Font("Arial", Font.PLAIN, 14));
        usernameField.setPreferredSize(new Dimension(250, 30));
        passwordField.setPreferredSize(new Dimension(250, 30));

        JButton loginButton = new JButton("Login");
        JButton registerButton = new JButton("Register");

        loginButton.setFont(new Font("Arial", Font.BOLD, 14));
        registerButton.setFont(new Font("Arial", Font.BOLD, 14));
        loginButton.setBackground(new Color(76, 0, 153));
        registerButton.setBackground(new Color(76, 0, 153));
        loginButton.setForeground(Color.WHITE);
        registerButton.setForeground(Color.WHITE);
        loginButton.setFocusPainted(false);
        registerButton.setFocusPainted(false);
        loginButton.setPreferredSize(new Dimension(120, 40));
        registerButton.setPreferredSize(new Dimension(120, 40));

        addButtonHoverEffect(loginButton);
        addButtonHoverEffect(registerButton);

        loginButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String username = usernameField.getText();
                char[] password = passwordField.getPassword();
                String passwordString = new String(password);

                if (users.containsKey(username) && users.get(username).equals(passwordString)) {
                    JOptionPane.showMessageDialog(loginFrame, "Login successful!");
                    loginFrame.dispose();  // Close the login page
                    LibraryManagementSystem.startApp(); // Start the library system after successful login
                } else {
                    JOptionPane.showMessageDialog(loginFrame, "Invalid username or password. Please try again.", "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        });

        registerButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String username = usernameField.getText();
                char[] password = passwordField.getPassword();
                String passwordString = new String(password);

                // If username is already taken
                if (users.containsKey(username)) {
                    JOptionPane.showMessageDialog(loginFrame, "Username already exists. Please choose a different one.", "Error", JOptionPane.ERROR_MESSAGE);
                } else {
                    // Register new user
                    users.put(username, passwordString);
                    JOptionPane.showMessageDialog(loginFrame, "Registration successful! You can now log in.");
                }
            }
        });

        panel.add(usernameLabel);
        panel.add(usernameField);
        panel.add(passwordLabel);
        panel.add(passwordField);
        panel.add(new JLabel());
        panel.add(loginButton);
        panel.add(new JLabel());  
        panel.add(registerButton);

        loginFrame.add(panel);

        loginFrame.setVisible(true);
    }

    // Hover effect for buttons
    private static void addButtonHoverEffect(JButton button) {
        button.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                button.setBackground(new Color(153, 0, 102)); // Dark purple on hover
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                button.setBackground(new Color(76, 0, 153)); // Reset to purple color
            }
        });
    }
}


