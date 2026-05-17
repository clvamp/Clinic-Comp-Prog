package GUI;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import system.ClinicAccountSystem;

public class SignUpGUI extends JDialog {

    private ClinicAccountSystem system;

    public SignUpGUI(JFrame parentFrame, ClinicAccountSystem system) {
        super(parentFrame, "Sign Up Form", true);
        this.system = system;

        /* =========================================================
           MAIN DIALOG
           ========================================================= */
        setSize(450, 550); // Adjusted height for layout spacing
        setLocationRelativeTo(parentFrame);
        setResizable(false);
        setLayout(new BorderLayout());

        Color mainBg = new Color(245, 245, 245);
        getContentPane().setBackground(mainBg);

        /* =========================================================
           MAIN PANEL
           ========================================================= */
        JPanel mainPanel = new JPanel(new GridBagLayout());
        mainPanel.setBackground(mainBg);
        add(mainPanel, BorderLayout.CENTER);

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.gridx = 0;

        /* =========================================================
           HEADER
           ========================================================= */
        JLabel headerLabel = new JLabel("CREATE ACCOUNT");
        headerLabel.setFont(new Font("Arial", Font.BOLD, 24));
        headerLabel.setForeground(new Color(40, 40, 40));
        headerLabel.setHorizontalAlignment(JLabel.CENTER);
        
        gbc.gridy = 0;
        gbc.insets = new Insets(20, 20, 20, 20);
        mainPanel.add(headerLabel, gbc);

        /* =========================================================
           USERNAME SECTION
           ========================================================= */
        JLabel usernameLabel = new JLabel("Username:");
        usernameLabel.setFont(new Font("Arial", Font.BOLD, 14));
        usernameLabel.setForeground(new Color(60, 60, 60));
        gbc.gridy = 1;
        gbc.insets = new Insets(30, 20, 5, 20);
        mainPanel.add(usernameLabel, gbc);

        JTextField usernameField = new JTextField();
        usernameField.setFont(new Font("Arial", Font.PLAIN, 14));
        usernameField.setPreferredSize(new Dimension(200, 30));
        usernameField.setBackground(Color.WHITE);
        gbc.gridy = 2;
        gbc.insets = new Insets(0, 20, 10, 20);
        mainPanel.add(usernameField, gbc);

        /* =========================================================
           AUTHORITY SECTION
           ========================================================= */
        JLabel authorityLabel = new JLabel("Authority:");
        authorityLabel.setFont(new Font("Arial", Font.BOLD, 14));
        authorityLabel.setForeground(new Color(60, 60, 60));
        gbc.gridy = 3;
        gbc.insets = new Insets(5, 20, 5, 20);
        mainPanel.add(authorityLabel, gbc);

        String[] roles = { "Doctor", "Nurse" };
        JComboBox<String> authorityBox = new JComboBox<>(roles);
        authorityBox.setFont(new Font("Arial", Font.PLAIN, 14));
        authorityBox.setBackground(Color.WHITE);
        authorityBox.setPreferredSize(new Dimension(200, 30));
        gbc.gridy = 4;
        gbc.insets = new Insets(0, 20, 10, 20);
        mainPanel.add(authorityBox, gbc);

        /* =========================================================
           PASSWORD SECTION
           ========================================================= */
        JLabel passwordLabel = new JLabel("Password:");
        passwordLabel.setFont(new Font("Arial", Font.BOLD, 14));
        passwordLabel.setForeground(new Color(60, 60, 60));
        gbc.gridy = 5;
        gbc.insets = new Insets(5, 20, 5, 20);
        mainPanel.add(passwordLabel, gbc);

        JPasswordField passwordField = new JPasswordField();
        passwordField.setFont(new Font("Arial", Font.PLAIN, 14));
        passwordField.setPreferredSize(new Dimension(200, 30));
        passwordField.setBackground(Color.WHITE);
        gbc.gridy = 6;
        gbc.insets = new Insets(0, 20, 10, 20);
        mainPanel.add(passwordField, gbc);

        /* =========================================================
           CONFIRM PASSWORD SECTION
           ========================================================= */
        JLabel confirmLabel = new JLabel("Confirm Password:");
        confirmLabel.setFont(new Font("Arial", Font.BOLD, 14));
        confirmLabel.setForeground(new Color(60, 60, 60));
        gbc.gridy = 7;
        gbc.insets = new Insets(5, 20, 5, 20);
        mainPanel.add(confirmLabel, gbc);

        JPasswordField confirmField = new JPasswordField();
        confirmField.setFont(new Font("Arial", Font.PLAIN, 14));
        confirmField.setPreferredSize(new Dimension(200, 30));
        confirmField.setBackground(Color.WHITE);
        gbc.gridy = 8;
        gbc.insets = new Insets(0, 20, 20, 20);
        mainPanel.add(confirmField, gbc);

        /* =========================================================
           BUTTON PANEL (MATCHES LOGIN GUI)
           ========================================================= */
        JPanel buttonPanel = new JPanel(new GridBagLayout());
        buttonPanel.setBackground(mainBg);
        GridBagConstraints btnGbc = new GridBagConstraints();
        btnGbc.insets = new Insets(5, 10, 5, 10);

        JButton registerButton = createStyledButton("Register");
        JButton cancelButton = createStyledButton("Cancel");

        registerButton.setPreferredSize(new Dimension(90, 30));
        cancelButton.setPreferredSize(new Dimension(90, 30));

        btnGbc.gridx = 0;
        buttonPanel.add(registerButton, btnGbc);

        btnGbc.gridx = 1;
        buttonPanel.add(cancelButton, btnGbc);

        gbc.gridy = 9;
        gbc.insets = new Insets(10, 20, 30, 20);
        mainPanel.add(buttonPanel, gbc);

        /* =========================================================
           ACTION LISTENERS
           ========================================================= */
        registerButton.addActionListener(e -> {
            system.signUpFunction(
                    this,
                    usernameField,
                    authorityBox,
                    passwordField,
                    confirmField
            );
        });

        cancelButton.addActionListener(e -> {
            system.cancelSignUp(this);
        });

        setVisible(true);
    }

    private JButton createStyledButton(String text) {
        JButton button = new JButton(text);
        button.setFocusable(false);
        button.setFont(new Font("Arial", Font.BOLD, 13));
        button.setForeground(Color.WHITE);
        button.setBackground(new Color(55, 55, 55));
        button.setCursor(new Cursor(Cursor.HAND_CURSOR));
        button.setFocusPainted(false);
        button.setBorder(BorderFactory.createLineBorder(new Color(55, 55, 55), 1, true));

        button.addMouseListener(new MouseAdapter() {
            public void mouseEntered(MouseEvent e) {
                button.setBackground(new Color(110, 170, 255));
                button.setBorder(BorderFactory.createLineBorder(new Color(110, 170, 255), 1, true));
            }
            public void mouseExited(MouseEvent e) {
                button.setBackground(new Color(55, 55, 55));
                button.setBorder(BorderFactory.createLineBorder(new Color(55, 55, 55), 1, true));
            }
        });
        return button;
    }
}