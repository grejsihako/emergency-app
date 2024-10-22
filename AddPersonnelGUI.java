package EmergencyApp;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class AddPersonnelGUI extends JFrame {
    private JTextField idFieldAdd, nameField, contactField;
    private JButton addButton;
    
    private JTextField idFieldRemove;
    private JButton removeButton;
    
    private JButton goBackButton;
    private JButton logoutButton;

    public AddPersonnelGUI(Controller controller, EmergencyAppGUI emergencyAppGUI) {
        super("Add/Remove Personnel");

        try {
            UIManager.setLookAndFeel("javax.swing.plaf.nimbus.NimbusLookAndFeel");
        } catch (Exception e) {
            e.printStackTrace();
        }


        // Panel for adding personnel
        JPanel addPanel = new JPanel(new GridLayout(0, 1, 5, 5));
        addPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 10));
        JLabel titleLabelAdd = new JLabel("Add Personnel", SwingConstants.CENTER);
        titleLabelAdd.setFont(new Font("Arial", Font.BOLD, 24));
        JLabel idLabelAdd = new JLabel("ID:");
        JLabel nameLabel = new JLabel("Name:");
        JLabel contactLabel = new JLabel("Contact:");
        idFieldAdd = new JTextField(20);
        nameField = new JTextField(20);
        contactField = new JTextField(20);
        addButton = new JButton("Add");
        goBackButton = new JButton("Go Back");
        goBackButton.setFont(new Font("Arial", Font.PLAIN, 16)); 
        goBackButton.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10)); 
        JPanel goBackPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        goBackPanel.add(goBackButton);
        addPanel.add(goBackPanel, BorderLayout.NORTH);
        addPanel.add(titleLabelAdd);
        addPanel.add(idLabelAdd);
        addPanel.add(idFieldAdd);
        addPanel.add(nameLabel);
        addPanel.add(nameField);
        addPanel.add(contactLabel);
        addPanel.add(contactField);
        addPanel.add(addButton);

        // Panel for removing personnel
        JPanel removePanel = new JPanel(new BorderLayout());
        removePanel.setBorder(BorderFactory.createEmptyBorder(20, 10, 20, 20));
        JLabel titleLabelRemove = new JLabel("Remove Personnel", SwingConstants.CENTER);
        titleLabelRemove.setFont(new Font("Arial", Font.BOLD, 24));
        JLabel removeLabel = new JLabel("Write the ID of the person you want to remove:");
        JLabel idLabelRemove = new JLabel("ID:");
        idFieldRemove = new JTextField(20);
        removeButton = new JButton("Remove");
        JPanel removeTopPanel = new JPanel(new GridLayout(0, 1, 5, 5));
        removeTopPanel.add(titleLabelRemove);
        removeTopPanel.add(removeLabel);
        removeTopPanel.add(idLabelRemove);
        removeTopPanel.add(idFieldRemove);
        removeTopPanel.add(new JLabel()); // Empty label for spacing
        removeTopPanel.add(removeButton);
        removePanel.add(removeTopPanel, BorderLayout.CENTER);
        
        // Go Back button (top left)
        
        
        // Logout button (top right)
        logoutButton = new JButton("Logout");
        logoutButton.setFont(new Font("Arial", Font.PLAIN, 16)); 
        logoutButton.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        JPanel logoutPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        logoutPanel.add(logoutButton);
        removePanel.add(logoutPanel, BorderLayout.NORTH);

        // Set layout for the entire frame
        setLayout(new GridLayout(1, 2, 10, 0)); 
        add(addPanel);
        add(removePanel);

        // Style buttons
        addButton.setBackground(new Color(0, 153, 0));
        addButton.setForeground(Color.WHITE);
        removeButton.setBackground(new Color(204, 0, 0));
        removeButton.setForeground(Color.WHITE);

        // Adjust text field height
        Font font = idFieldAdd.getFont();
        idFieldAdd.setFont(font.deriveFont(Font.PLAIN, 14f));
        nameField.setFont(font.deriveFont(Font.PLAIN, 14f));
        contactField.setFont(font.deriveFont(Font.PLAIN, 14f));
        idFieldRemove.setFont(font.deriveFont(Font.PLAIN, 14f));

        // Add action listener to add button
        addButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String id = idFieldAdd.getText().trim();
                String name = nameField.getText().trim();
                String contact = contactField.getText().trim();
                if (!id.isEmpty() && !name.isEmpty() && !contact.isEmpty()) {
                    controller.addPersonnel(id, name, contact);
                    JOptionPane.showMessageDialog(AddPersonnelGUI.this, "Personnel added successfully!");
                } else {
                    JOptionPane.showMessageDialog(AddPersonnelGUI.this, "Please fill in all fields.");
                }
            }
        });

        removeButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String id = idFieldRemove.getText().trim();
                if (!id.isEmpty()) {
                    controller.removePersonnel(id);
                    JOptionPane.showMessageDialog(AddPersonnelGUI.this, "Personnel removed successfully!");
                } else {
                    JOptionPane.showMessageDialog(AddPersonnelGUI.this, "Please enter the personnel ID to remove.");
                }
            }
        });
        

        goBackButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Code to go back to Emergency GUI
                setVisible(false);
                emergencyAppGUI.setVisible(true);
            }
        });


        logoutButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                
                dispose();
        
                // Open the login page
                SwingUtilities.invokeLater(() -> {
                    LoginGUI loginGUI = new LoginGUI(controller);
                    loginGUI.setVisible(true);
                });
            }
        });

        
        setSize(700, 500);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE); 
        setVisible(true);
     }
}
