package GUI.contents;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import system.ClinicAccountSystem;

public class AccountForm extends JDialog {

    private ClinicAccountSystem system;
    private AccountPanel accountPanel; // NEW: Track display layer panel
    private JTextField usernameField;
    private JComboBox<String> authorityBox;
    private JPasswordField passwordField;
    private JPasswordField confirmPasswordField; 
    private JButton saveButton;
    private JButton cancelButton;

    // FIX: Added AccountPanel inside initialization fields
    public AccountForm(JFrame parentFrame, ClinicAccountSystem system, AccountPanel accountPanel, String currentUsername, String currentAuthority) {
        super(parentFrame, "Account Management", true);
        this.system = system;
        this.accountPanel = accountPanel;

        setSize(450, 560);
        setLocationRelativeTo(parentFrame);
        setResizable(false);
        setLayout(new BorderLayout());

        Color mainBg = new Color(245, 245, 245);
        getContentPane().setBackground(mainBg);

        JPanel mainPanel = new JPanel(new GridBagLayout());
        mainPanel.setBackground(mainBg);
        add(mainPanel, BorderLayout.CENTER);

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.gridx = 0;

        JLabel headerLabel = new JLabel("EDIT ACCOUNT");
        headerLabel.setFont(new Font("Arial", Font.BOLD, 24));
        headerLabel.setForeground(new Color(40, 40, 40));
        headerLabel.setHorizontalAlignment(JLabel.CENTER);
        gbc.gridy = 0;
        gbc.insets = new Insets(20, 20, 20, 20);
        mainPanel.add(headerLabel, gbc);

        JLabel usernameLabel = new JLabel("Username:");
        usernameLabel.setFont(new Font("Arial", Font.BOLD, 14));
        usernameLabel.setForeground(new Color(60, 60, 60));
        gbc.gridy = 1;
        gbc.insets = new Insets(25, 20, 5, 20);
        mainPanel.add(usernameLabel, gbc);

        usernameField = new JTextField(currentUsername);
        usernameField.setFont(new Font("Arial", Font.PLAIN, 14));
        usernameField.setPreferredSize(new Dimension(200, 35));
        usernameField.setBackground(Color.WHITE);
        gbc.gridy = 2;
        gbc.insets = new Insets(0, 20, 12, 20);
        mainPanel.add(usernameField, gbc);

        JLabel authorityLabel = new JLabel("Authority:");
        authorityLabel.setFont(new Font("Arial", Font.BOLD, 14));
        authorityLabel.setForeground(new Color(60, 60, 60));
        gbc.gridy = 3;
        gbc.insets = new Insets(5, 20, 5, 20);
        mainPanel.add(authorityLabel, gbc);

        String[] roles = {"Doctor", "Nurse"};
        authorityBox = new JComboBox<>(roles);
        authorityBox.setFont(new Font("Arial", Font.PLAIN, 14));
        authorityBox.setBackground(Color.WHITE);
        authorityBox.setPreferredSize(new Dimension(200, 35));
        authorityBox.setSelectedItem(currentAuthority);
        gbc.gridy = 4;
        gbc.insets = new Insets(0, 20, 12, 20);
        mainPanel.add(authorityBox, gbc);

        JLabel passwordLabel = new JLabel("Password:");
        passwordLabel.setFont(new Font("Arial", Font.BOLD, 14));
        passwordLabel.setForeground(new Color(60, 60, 60));
        gbc.gridy = 5;
        gbc.insets = new Insets(5, 20, 5, 20);
        mainPanel.add(passwordLabel, gbc);

        passwordField = new JPasswordField();
        passwordField.setFont(new Font("Arial", Font.PLAIN, 14));
        passwordField.setPreferredSize(new Dimension(200, 35));
        passwordField.setBackground(Color.WHITE);
        gbc.gridy = 6;
        gbc.insets = new Insets(0, 20, 12, 20);
        mainPanel.add(passwordField, gbc);

        JLabel confirmPasswordLabel = new JLabel("Confirm Password:");
        confirmPasswordLabel.setFont(new Font("Arial", Font.BOLD, 14));
        confirmPasswordLabel.setForeground(new Color(60, 60, 60));
        gbc.gridy = 7;
        gbc.insets = new Insets(5, 20, 5, 20);
        mainPanel.add(confirmPasswordLabel, gbc);

        confirmPasswordField = new JPasswordField();
        confirmPasswordField.setFont(new Font("Arial", Font.PLAIN, 14));
        confirmPasswordField.setPreferredSize(new Dimension(200, 35));
        confirmPasswordField.setBackground(Color.WHITE);
        gbc.gridy = 8;
        gbc.insets = new Insets(0, 20, 25, 20);
        mainPanel.add(confirmPasswordField, gbc);

        JPanel buttonPanel = new JPanel(new GridBagLayout());
        buttonPanel.setBackground(mainBg);

        GridBagConstraints btnGbc = new GridBagConstraints();
        btnGbc.insets = new Insets(5, 10, 5, 10);

        saveButton = createStyledButton("Save");
        cancelButton = createStyledButton("Cancel");

        saveButton.setPreferredSize(new Dimension(100, 35));
        cancelButton.setPreferredSize(new Dimension(100, 35));

        btnGbc.gridx = 0;
        buttonPanel.add(saveButton, btnGbc);
        btnGbc.gridx = 1;
        buttonPanel.add(cancelButton, btnGbc);

        gbc.gridy = 9;
        gbc.insets = new Insets(10, 20, 30, 20);
        mainPanel.add(buttonPanel, gbc);

        cancelButton.addActionListener(e -> dispose());

        /* =========================================================
           ROUTED SAVE TRIGGER
           ========================================================= */
        saveButton.addActionListener(e -> {
            // FIX: Passes the tracking accountPanel reference into our logic core
            system.updateAccountFunction(
                    this,
                    accountPanel,
                    currentUsername,
                    usernameField,
                    authorityBox,
                    passwordField,
                    confirmPasswordField
            );
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

    public JTextField getUsernameField() { return usernameField; }
    public JComboBox<String> getAuthorityBox() { return authorityBox; }
    public JPasswordField getPasswordField() { return passwordField; }
    public JPasswordField getConfirmPasswordField() { return confirmPasswordField; }
    public JButton getSaveButton() { return saveButton; }
    public JButton getCancelButton() { return cancelButton; }
}