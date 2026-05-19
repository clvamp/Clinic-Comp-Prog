package for_patient;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class PatientSignUpGUI extends JDialog {

    private PatientLogic logic;

    // --- CLINIC THEME COLORS ---
    private final Color CLINIC_BG = new Color(235, 245, 250);     // Soft medical blue
    private final Color HEADER_TEXT = new Color(0, 102, 120);    // Deep teal
    private final Color LABEL_TEXT = new Color(70, 90, 100);     // Muted slate
    private final Color BUTTON_COLOR = new Color(45, 60, 70);    // Dark slate button
    private final Color HOVER_BLUE = new Color(0, 150, 200);     // Medical blue hover

    public PatientSignUpGUI(JFrame parentFrame, PatientLogic logic) {
        super(parentFrame, "Patient Sign Up Form", true);
        this.logic = logic;

        /* =========================================================
           MAIN DIALOG
           ========================================================= */
        setSize(450, 500); // Scaled down to 500 height since Authority dropdown is removed
        setLocationRelativeTo(parentFrame);
        setResizable(false);
        setLayout(new BorderLayout());

        getContentPane().setBackground(CLINIC_BG);

        /* =========================================================
           MAIN PANEL
           ========================================================= */
        JPanel mainPanel = new JPanel(new GridBagLayout());
        mainPanel.setBackground(CLINIC_BG);
        add(mainPanel, BorderLayout.CENTER);

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.gridx = 0;

        /* =========================================================
           HEADER
           ========================================================= */
        JLabel headerLabel = new JLabel("PATIENT REGISTRATION");
        headerLabel.setFont(new Font("Segoe UI", Font.BOLD, 24)); 
        headerLabel.setForeground(HEADER_TEXT);
        headerLabel.setHorizontalAlignment(JLabel.CENTER);
        
        gbc.gridy = 0;
        gbc.insets = new Insets(20, 20, 20, 20);
        mainPanel.add(headerLabel, gbc);

        /* =========================================================
           USERNAME SECTION
           ========================================================= */
        JLabel usernameLabel = new JLabel("Username:");
        usernameLabel.setFont(new Font("Arial", Font.BOLD, 14));
        usernameLabel.setForeground(LABEL_TEXT);
        gbc.gridy = 1;
        gbc.insets = new Insets(15, 20, 5, 20);
        mainPanel.add(usernameLabel, gbc);

        JTextField usernameField = new JTextField();
        usernameField.setFont(new Font("Arial", Font.PLAIN, 14));
        usernameField.setPreferredSize(new Dimension(250, 38)); 
        usernameField.setBackground(Color.WHITE);
        usernameField.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(new Color(200, 215, 220)),
            BorderFactory.createEmptyBorder(0, 10, 0, 10)
        ));
        gbc.gridy = 2;
        gbc.insets = new Insets(0, 20, 15, 20);
        mainPanel.add(usernameField, gbc);

        /* =========================================================
           PASSWORD SECTION
           ========================================================= */
        JLabel passwordLabel = new JLabel("Password:");
        passwordLabel.setFont(new Font("Arial", Font.BOLD, 14));
        passwordLabel.setForeground(LABEL_TEXT);
        gbc.gridy = 3;
        gbc.insets = new Insets(5, 20, 5, 20);
        mainPanel.add(passwordLabel, gbc);

        JPasswordField passwordField = new JPasswordField();
        passwordField.setFont(new Font("Arial", Font.PLAIN, 14));
        passwordField.setPreferredSize(new Dimension(250, 38));
        passwordField.setBackground(Color.WHITE);
        passwordField.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(new Color(200, 215, 220)),
            BorderFactory.createEmptyBorder(0, 10, 0, 10)
        ));
        gbc.gridy = 4;
        gbc.insets = new Insets(0, 20, 15, 20);
        mainPanel.add(passwordField, gbc);

        /* =========================================================
           CONFIRM PASSWORD SECTION
           ========================================================= */
        JLabel confirmLabel = new JLabel("Confirm Password:");
        confirmLabel.setFont(new Font("Arial", Font.BOLD, 14));
        confirmLabel.setForeground(LABEL_TEXT);
        gbc.gridy = 5;
        gbc.insets = new Insets(5, 20, 5, 20);
        mainPanel.add(confirmLabel, gbc);

        JPasswordField confirmField = new JPasswordField();
        confirmField.setFont(new Font("Arial", Font.PLAIN, 14));
        confirmField.setPreferredSize(new Dimension(250, 38));
        confirmField.setBackground(Color.WHITE);
        confirmField.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(new Color(200, 215, 220)),
            BorderFactory.createEmptyBorder(0, 10, 0, 10)
        ));
        gbc.gridy = 6;
        gbc.insets = new Insets(0, 20, 25, 20);
        mainPanel.add(confirmField, gbc);

        /* =========================================================
           BUTTON PANEL
           ========================================================= */
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 15, 0)); 
        buttonPanel.setBackground(CLINIC_BG);

        JButton registerButton = createStyledButton("Register");
        JButton cancelButton = createStyledButton("Cancel");

        registerButton.setPreferredSize(new Dimension(110, 38)); 
        cancelButton.setPreferredSize(new Dimension(110, 38));

        buttonPanel.add(registerButton);
        buttonPanel.add(cancelButton);

        gbc.gridy = 7;
        gbc.insets = new Insets(10, 20, 25, 20);
        mainPanel.add(buttonPanel, gbc);

        /* =========================================================
           ACTION LISTENERS
           ========================================================= */
        registerButton.addActionListener(e -> {
            logic.patientSignUpFunction(
                    this,
                    usernameField,
                    passwordField,
                    confirmField
            );
        });

        cancelButton.addActionListener(e -> {
            dispose(); // Cleanly closes the registration dialog view frame
        });

        setVisible(true);
    }

    /* =========================================================
       BUTTON DESIGN
       ========================================================= */
    private JButton createStyledButton(String text) {
        JButton button = new JButton(text);
        button.setFocusable(false);
        button.setFont(new Font("Arial", Font.BOLD, 13));
        button.setForeground(Color.WHITE);
        button.setBackground(BUTTON_COLOR);
        button.setCursor(new Cursor(Cursor.HAND_CURSOR));
        button.setFocusPainted(false);
        button.setBorder(BorderFactory.createLineBorder(BUTTON_COLOR, 1, true));

        button.addMouseListener(new MouseAdapter() {
            public void mouseEntered(MouseEvent e) {
                button.setBackground(HOVER_BLUE);
                button.setBorder(BorderFactory.createLineBorder(HOVER_BLUE, 1, true));
            }
            public void mouseExited(MouseEvent e) {
                button.setBackground(BUTTON_COLOR);
                button.setBorder(BorderFactory.createLineBorder(BUTTON_COLOR, 1, true));
            }
        });
        return button;
    }
}