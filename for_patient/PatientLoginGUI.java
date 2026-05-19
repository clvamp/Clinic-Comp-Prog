
package for_patient;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
public class PatientLoginGUI {
// FIX: Swapped to PatientLogic as the primary controller for the patient portal
private PatientLogic logic;

// --- PATIENT THEME COLORS ---
private final Color PATIENT_BG = new Color(235, 245, 250);
private final Color HEADER_TEXT = new Color(0, 102, 120);
private final Color LABEL_TEXT = new Color(70, 90, 100);
private final Color HOVER_BLUE = new Color(0, 150, 200);

/* =========================================================
   CONSTRUCTOR
   ========================================================= */
public PatientLoginGUI(PatientLogic logic) {
    this.logic = logic;

    /* =========================================================
       MAIN FRAME
       ========================================================= */
    JFrame patientFrame = new JFrame("Patient Portal");
    patientFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    patientFrame.setSize(800, 600);
    patientFrame.setLayout(new BorderLayout());
    patientFrame.setResizable(true);
    patientFrame.setLocationRelativeTo(null);
    patientFrame.getContentPane().setBackground(PATIENT_BG);

    /* =========================================================
       LEFT PANEL
       ========================================================= */
    JPanel leftPanel = new JPanel(new GridBagLayout());
    leftPanel.setBackground(PATIENT_BG);
    leftPanel.setPreferredSize(new Dimension(260, 600));
    patientFrame.add(leftPanel, BorderLayout.WEST);

    /* =========================================================
       RIGHT PANEL
       ========================================================= */
    JPanel rightPanel = new JPanel(new BorderLayout());
    rightPanel.setBackground(new Color(225, 238, 245));
    patientFrame.add(rightPanel, BorderLayout.CENTER);

    /* =========================================================
       IMAGE
       ========================================================= */
    ImageIcon rightImage = new ImageIcon(getClass().getResource("/GUI/logoapp.png"));
    JLabel imageLabel = new JLabel(rightImage);
    imageLabel.setHorizontalAlignment(JLabel.CENTER);
    rightPanel.add(imageLabel, BorderLayout.CENTER);

    /* =========================================================
       FORM PANEL
       ========================================================= */
    JPanel formPanel = new JPanel(new GridBagLayout());
    formPanel.setBackground(PATIENT_BG);

    GridBagConstraints leftGbc = new GridBagConstraints();
    leftGbc.gridx = 0;
    leftGbc.gridy = 0;
    leftGbc.anchor = GridBagConstraints.NORTH;
    leftGbc.insets = new Insets(30, 10, 10, 10);
    leftPanel.add(formPanel, leftGbc);

    GridBagConstraints gbc = new GridBagConstraints();
    gbc.insets = new Insets(10, 10, 10, 10);
    gbc.gridx = 0;
    gbc.fill = GridBagConstraints.HORIZONTAL;

    /* =========================================================
       GO BACK BUTTON
       ========================================================= */
    JButton backButton = new JButton("← Back");

    backButton.setFocusable(false);

    backButton.setFont(new Font("Arial", Font.BOLD, 12));

    backButton.setForeground(new Color(0, 102, 120));

    backButton.setBackground(new Color(255, 255, 255, 100));

    backButton.setOpaque(true);

    backButton.setContentAreaFilled(true);

    backButton.setPreferredSize(new Dimension(85, 28));

    backButton.setBorder(BorderFactory.createEmptyBorder(6, 14, 6, 14));

    backButton.setCursor(new Cursor(Cursor.HAND_CURSOR));

    backButton.addMouseListener(new MouseAdapter() {

        @Override
        public void mouseEntered(MouseEvent e) {

            backButton.setBackground(
                    new Color(255, 255, 255, 150)
            );
        }

        @Override
        public void mouseExited(MouseEvent e) {

            backButton.setBackground(
                    new Color(255, 255, 255, 100)
            );
        }
    });

    GridBagConstraints backGbc = new GridBagConstraints();

    backGbc.gridx = 0;
    backGbc.gridy = 0;
    backGbc.anchor = GridBagConstraints.WEST;
    backGbc.insets = new Insets(0, 0, 10, 0);

    formPanel.add(backButton, backGbc);

    /* =========================================================
       LOGO
       ========================================================= */
    ImageIcon accLogo = new ImageIcon(getClass().getResource("/GUI/acclogo.png"));
    JLabel logoLabel = new JLabel(accLogo);
    logoLabel.setHorizontalAlignment(JLabel.CENTER);
    gbc.gridy = 1;
    formPanel.add(logoLabel, gbc);

    /* =========================================================
       HEADER
       ========================================================= */
    JLabel labelHeader = new JLabel("PATIENT LOGIN");
    labelHeader.setFont(new Font("Segoe UI", Font.BOLD, 22));
    labelHeader.setForeground(HEADER_TEXT);
    labelHeader.setHorizontalAlignment(JLabel.CENTER);
    gbc.gridy = 2;
    formPanel.add(labelHeader, gbc);

    /* =========================================================
       USERNAME
       ========================================================= */
    JPanel userPanel = new JPanel(new GridBagLayout());
    userPanel.setBackground(PATIENT_BG);

    GridBagConstraints uGbc = new GridBagConstraints();
    uGbc.gridx = 0;
    uGbc.fill = GridBagConstraints.HORIZONTAL;

    JLabel labelName = new JLabel("Username:");
    labelName.setFont(new Font("Arial", Font.BOLD, 14));
    labelName.setForeground(LABEL_TEXT);
    uGbc.insets = new Insets(2, 5, 2, 5);
    uGbc.gridy = 0;
    userPanel.add(labelName, uGbc);

    JTextField usernameInput = new JTextField();
    usernameInput.setFont(new Font("Arial", Font.PLAIN, 14));
    usernameInput.setPreferredSize(new Dimension(200, 35));
    usernameInput.setBackground(Color.WHITE);
    usernameInput.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(new Color(200, 215, 220)),
            BorderFactory.createEmptyBorder(0, 10, 0, 10)
    ));
    uGbc.insets = new Insets(10, 5, 5, 5);
    uGbc.gridy = 1;
    userPanel.add(usernameInput, uGbc);

    gbc.gridy = 3;
    formPanel.add(userPanel, gbc);

    /* =========================================================
       PASSWORD
       ========================================================= */
    JPanel passPanel = new JPanel(new GridBagLayout());
    passPanel.setBackground(PATIENT_BG);

    GridBagConstraints pGbc = new GridBagConstraints();
    pGbc.gridx = 0;
    pGbc.fill = GridBagConstraints.HORIZONTAL;

    JLabel labelPass = new JLabel("Password:");
    labelPass.setFont(new Font("Arial", Font.BOLD, 14));
    labelPass.setForeground(LABEL_TEXT);
    pGbc.insets = new Insets(2, 5, 2, 5);
    pGbc.gridy = 0;
    passPanel.add(labelPass, pGbc);

    JPasswordField passwordInput = new JPasswordField();
    passwordInput.setFont(new Font("Arial", Font.PLAIN, 14));
    passwordInput.setPreferredSize(new Dimension(200, 35));
    passwordInput.setBackground(Color.WHITE);
    passwordInput.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(new Color(200, 215, 220)),
            BorderFactory.createEmptyBorder(0, 10, 0, 10)
    ));
    pGbc.insets = new Insets(10, 5, 5, 5);
    pGbc.gridy = 1;
    passPanel.add(passwordInput, pGbc);

    gbc.gridy = 4;
    formPanel.add(passPanel, gbc);

    /* =========================================================
       BUTTON PANEL
       ========================================================= */
    JPanel buttonPanel = new JPanel(new GridBagLayout());
    buttonPanel.setBackground(PATIENT_BG);

    GridBagConstraints bGbc = new GridBagConstraints();
    bGbc.insets = new Insets(5, 10, 5, 10);

    JButton loginButton = createStyledButton("Login");
    JButton registerButton = createStyledButton("Register");

    loginButton.setPreferredSize(new Dimension(90, 32));
    registerButton.setPreferredSize(new Dimension(90, 32));

    bGbc.gridx = 0;
    buttonPanel.add(loginButton, bGbc);

    bGbc.gridx = 1;
    buttonPanel.add(registerButton, bGbc);

    gbc.gridy = 5;
    formPanel.add(buttonPanel, gbc);

    /* =========================================================
       ACTION LISTENERS
       ========================================================= */

    // FIX: Wired to patientLogInFunction inside PatientLogic
    loginButton.addActionListener(e -> {
        logic.patientLogInFunction(patientFrame, usernameInput, passwordInput);
    });

    // FIX: Wired to openPatientSignUp inside PatientLogic
    registerButton.addActionListener(e -> {
        logic.openPatientSignUp(patientFrame);
    });

    backButton.addActionListener(e -> {
        patientFrame.dispose();
        logic.backToRoleSelector(patientFrame);
    });

    patientFrame.setVisible(true);
}

/* =========================================================
   BUTTON DESIGN
   ========================================================= */
private JButton createStyledButton(String text) {
    JButton button = new JButton(text);
    button.setFocusable(false);
    button.setFont(new Font("Arial", Font.BOLD, 13));
    button.setForeground(Color.WHITE);
    button.setBackground(new Color(45, 60, 70));
    button.setCursor(new Cursor(Cursor.HAND_CURSOR));
    button.setFocusPainted(false);
    button.setBorder(BorderFactory.createLineBorder(new Color(45, 60, 70), 1, true));

    button.addMouseListener(new MouseAdapter() {
        public void mouseEntered(MouseEvent e) {
            button.setBackground(HOVER_BLUE);
            button.setBorder(BorderFactory.createLineBorder(HOVER_BLUE, 1, true));
        }

        public void mouseExited(MouseEvent e) {
            button.setBackground(new Color(45, 60, 70));
            button.setBorder(BorderFactory.createLineBorder(new Color(45, 60, 70), 1, true));
        }
    });

    return button;
}

/* =========================================================
   GETTERS & SETTERS
   ========================================================= */
public PatientLogic getLogic() {
    return logic;
}

public void setLogic(PatientLogic logic) {
    this.logic = logic;
}
}
