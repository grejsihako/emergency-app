package EmergencyApp;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class LoginGUI extends JFrame {
    private JTextField usernameField;
    private JPasswordField passwordField;
    private JButton loginButton;

    public LoginGUI(Controller controller) {
        super("Login");

        // Labels
        JLabel titleLabel = new JLabel("Login Page", SwingConstants.CENTER);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 24));
        JLabel usernameLabel = new JLabel("Username:", SwingConstants.CENTER);
        JLabel passwordLabel = new JLabel("Password:", SwingConstants.CENTER);

        
        usernameField = new JTextField(15); 
        passwordField = new JPasswordField(10); 

        loginButton = new JButton("Login");
        loginButton.setBackground(new Color(70, 130, 180));
        loginButton.setForeground(Color.WHITE);
        loginButton.setFont(new Font("Arial", Font.BOLD, 16));

        JPanel panel = new JPanel(new GridLayout(7, 1, 10, 10));
        panel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        panel.add(titleLabel);
        panel.add(usernameLabel);
        panel.add(usernameField);
        panel.add(passwordLabel);
        panel.add(passwordField);
        panel.add(loginButton);

        // Set layout for the frame's content pane
        getContentPane().setLayout(new BorderLayout());
        getContentPane().add(panel, BorderLayout.CENTER);

        // Action listener for login button
        loginButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String id = usernameField.getText();
                String password = new String(passwordField.getPassword());
                boolean authenticated = controller.authenticateManager(id, password);
                if (authenticated) {
                    // Close the login window
                    dispose();
                    // Open the missions page
                    Manager authenticatedManager = controller.getAuthenticatedManager(id);
                    new EmergencyAppGUI(controller, authenticatedManager).setVisible(true);
                } else {
                    JOptionPane.showMessageDialog(LoginGUI.this, "Invalid username or password.");
                }
            }
        });

        // Set frame properties
        setSize(700, 500);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setVisible(true);
    }

    public static void main(String[] args) {
        Controller controller = new Controller();
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                new LoginGUI(controller).setVisible(true);
            }
        });
    }
}
