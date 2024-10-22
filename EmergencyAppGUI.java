package EmergencyApp;
import javax.swing.*;

import java.awt.event.MouseEvent;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.util.List;

public class EmergencyAppGUI extends JFrame {
    private Controller controller;
    private JLabel titleLabel, missionLabel,locationLabel,personnelLabel;
    private JTextField locationField, personnelField;
    private JButton createMissionButton, finishMissionButton, logoutButton;
    private JList<String> missionList;
    private DefaultListModel<String> missionListModel;
    private Manager authenticatedManager;
    private JLabel addPersonnelLabel;

    public EmergencyAppGUI(Controller controller, Manager authenticatedManager) {
        super("Cookies EmergencyApp");
        this.controller = controller;
        this.authenticatedManager = authenticatedManager;
        this.missionListModel = new DefaultListModel<>();
        this.missionList = new JList<>(missionListModel);

        // Initialize components
        titleLabel = new JLabel("Welcome to Cookies EmergencyApp", SwingConstants.CENTER);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 24));
        missionLabel= new JLabel("Fill the form to create a mission: ",SwingConstants.CENTER);
        locationLabel = new JLabel("Location:");
        personnelLabel = new JLabel("Personnel Needed:");
        locationField = new JTextField();
        personnelField = new JTextField();
        createMissionButton = new JButton("Create Mission");
        finishMissionButton = new JButton("Finish Mission");
        logoutButton = new JButton("Log Out");
        addPersonnelLabel = new JLabel("<html><u>Do you want to add/remove personnel?</u></html>");
        addPersonnelLabel.setForeground(Color.BLUE);

        Font buttonFont = new Font("Arial", Font.BOLD, 14);
        Color buttonBackground = new Color(70, 130, 180); // SteelBlue
        Color buttonForeground = Color.WHITE;

        createMissionButton.setBackground(buttonBackground);
        createMissionButton.setForeground(buttonForeground);
        createMissionButton.setFont(buttonFont);

        finishMissionButton.setBackground(buttonBackground);
        finishMissionButton.setForeground(buttonForeground);
        finishMissionButton.setFont(buttonFont);

        logoutButton.setBackground(buttonBackground);
        logoutButton.setForeground(buttonForeground);
        logoutButton.setFont(buttonFont);

        // Set layout
        setLayout(new BorderLayout());

        // Panel for create mission fields
        JPanel createMissionPanel = new JPanel(new GridLayout(10, 1, 15, 10));
        createMissionPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        createMissionPanel.add(missionLabel);
        createMissionPanel.add(locationLabel);
        createMissionPanel.add(locationField);
        createMissionPanel.add(personnelLabel);
        createMissionPanel.add(personnelField);
        createMissionPanel.add(createMissionButton);
        createMissionPanel.add(addPersonnelLabel);
        createMissionPanel.add(new JLabel()); // Empty space for alignment
        createMissionPanel.add(new JLabel()); // Empty space for alignment
        createMissionPanel.add(new JLabel()); // Empty space for alignment

        // Panel for active mission list and finish mission button
        JPanel missionPanel = new JPanel(new BorderLayout());
        missionPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        missionPanel.add(new JScrollPane(missionList), BorderLayout.CENTER);
        missionPanel.add(finishMissionButton, BorderLayout.SOUTH);

        // Panel for logout button
        JPanel logoutPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        logoutPanel.add(logoutButton);

        // Add components to frame
        add(titleLabel, BorderLayout.NORTH);
        add(createMissionPanel, BorderLayout.WEST);
        add(missionPanel, BorderLayout.CENTER);
        add(logoutPanel, BorderLayout.SOUTH);

        // Set frame properties
        setSize(700, 500);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setVisible(true);


        // Add action listeners
        createMissionButton.addActionListener(e -> createMission());
        finishMissionButton.addActionListener(e -> finishMission());

        // Initialize mission list
        updateMissionList();
        
        logoutButton.addActionListener(e-> logout());
        addPersonnelLabel.addMouseListener(new MouseAdapter() {

    public void mouseClicked(MouseEvent e) {
        // Open the Add/Remove Personnel GUI when the label is clicked
        addPersonnel();
    }
});
 }

    private void createMission() {
        try {
            // Check if a manager is authenticated
            if (authenticatedManager != null) {
                // Proceed with creating the mission using the authenticated manager's ID
                String location = locationField.getText().trim();
                String personnelNeeded = personnelField.getText().trim();
                if (!location.isEmpty() && !personnelNeeded.isEmpty()) {
                    int needed = Integer.parseInt(personnelNeeded);
                    List<String> personnel = controller.getAvailablePersonnel(needed);
                    if (personnel.size() >= needed) {
                        String missionID = "MissionID" + (controller.getMissions().size() + 1);
                        String managerID = authenticatedManager.getId(); // Use the authenticated manager's ID
                        controller.getMissions().add(new Mission(missionID, location, "In progress", personnel, managerID));
                        updateMissionList();
                        controller.writeMissionsToFile();
                        JOptionPane.showMessageDialog(this, "Mission created successfully!");
                    } else {
                        JOptionPane.showMessageDialog(this, "Not enough available personnel.");
                    }
                } else {
                    JOptionPane.showMessageDialog(this, "Please fill in all fields.");
                }
            } else {
                JOptionPane.showMessageDialog(this, "Please log in first.");
            }
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "Personnel needed must be a valid integer.");
        }
    }
    
    private void finishMission() {
        String selectedMissionString = missionList.getSelectedValue();
        if (selectedMissionString != null) {
        String selectedMissionID = selectedMissionString.split(":")[1].split(",")[0].trim();
            
            // Find the mission with the selected ID
            Mission selectedMission = null;
            for (Mission mission : controller.getMissions()) {
                if (mission.getMissionID().equals(selectedMissionID)) {
                    selectedMission = mission;
                    break;
                }
            }
            
            if (selectedMission != null) {
                // Update mission status
                selectedMission.setStatus("Finished");
                controller.writeMissionsToFile();
                
                // Update personnel status and mission ID
                controller.updatePersonnelStatus(selectedMission.getPersonnel(), "Available");
                for (String personnelID : selectedMission.getPersonnel()) {
                    controller.updatePersonnelMission(personnelID, selectedMissionID);
                }
                
                // Update manager's mission list
                controller.updateManagerMission(selectedMission.getManagerID(), selectedMissionID);
                JOptionPane.showMessageDialog(this, "Mission finished successfully!");
                updateMissionList();
            } else {
                JOptionPane.showMessageDialog(this, "Selected mission not found.");
            }
        } else {
            JOptionPane.showMessageDialog(this, "Please select a mission.");
        }
    } 

    private void updateMissionList() {
        missionListModel.clear();
        String managerID = authenticatedManager.getId();
        for (Mission mission : controller.getMissions()) {
            if (mission.getManagerID().equals(managerID) && mission.getStatus().equals("In progress")) {
                missionListModel.addElement("Mission ID: " + mission.getMissionID() + ", Location: " + mission.getLocation());
            }
        }
    }

    private void logout() {
        // Close the current EmergencyAppGUI window
        dispose();
        
        // Open the login page
        SwingUtilities.invokeLater(() -> {
            LoginGUI loginGUI = new LoginGUI(controller);
            loginGUI.setVisible(true);
        });
    }

    private void addPersonnel() {
        dispose();
        SwingUtilities.invokeLater(() -> {
            AddPersonnelGUI addPersonnelGUI = new AddPersonnelGUI(controller,this);
            addPersonnelGUI.setVisible(true);
        });
    }
    
        public static void main(String[] args) {
            Controller controller = new Controller();
            SwingUtilities.invokeLater(() -> {
                LoginGUI loginGUI = new LoginGUI(controller);
                loginGUI.setVisible(true);
            });
        }
    }