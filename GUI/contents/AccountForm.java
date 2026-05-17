package GUI.contents;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import system.NoticeFunctions;

public class AccountForm extends JDialog {

    private JTextField usernameField;
    private JComboBox<String> authorityBox;
    private JPasswordField passwordField;
    private JButton saveButton;
    private JButton cancelButton;

    // FIX: Updated constructor to accept current details for pre-population
    public AccountForm(JFrame parentFrame, String currentUsername, String currentAuthority) {

        super(parentFrame, "Account Management", true);

        /* =========================================================
           DIALOG SETTINGS
           ========================================================= */
        setSize(450, 500);

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
        JLabel headerLabel =
                new JLabel("EDIT ACCOUNT");

        headerLabel.setFont(
                new Font("Arial", Font.BOLD, 24)
        );

        headerLabel.setForeground(
                new Color(40, 40, 40)
        );

        headerLabel.setHorizontalAlignment(
                JLabel.CENTER
        );

        gbc.gridy = 0;

        gbc.insets = new Insets(20, 20, 20, 20);

        mainPanel.add(headerLabel, gbc);

        /* =========================================================
           USERNAME
           ========================================================= */
        JLabel usernameLabel =
                new JLabel("Username:");

        usernameLabel.setFont(
                new Font("Arial", Font.BOLD, 14)
        );

        usernameLabel.setForeground(
                new Color(60, 60, 60)
        );

        gbc.gridy = 1;

        gbc.insets = new Insets(25, 20, 5, 20);

        mainPanel.add(usernameLabel, gbc);

        // FIX: Passed currentUsername into constructor to pre-populate text field
        usernameField = new JTextField(currentUsername);

        usernameField.setFont(
                new Font("Arial", Font.PLAIN, 14)
        );

        usernameField.setPreferredSize(
                new Dimension(200, 35)
        );

        usernameField.setBackground(Color.WHITE);

        gbc.gridy = 2;

        gbc.insets = new Insets(0, 20, 12, 20);

        mainPanel.add(usernameField, gbc);

        /* =========================================================
           AUTHORITY
           ========================================================= */
        JLabel authorityLabel =
                new JLabel("Authority:");

        authorityLabel.setFont(
                new Font("Arial", Font.BOLD, 14)
        );

        authorityLabel.setForeground(
                new Color(60, 60, 60)
        );

        gbc.gridy = 3;

        gbc.insets = new Insets(5, 20, 5, 20);

        mainPanel.add(authorityLabel, gbc);

        String[] roles = {
                "Doctor",
                "Nurse"
        };

        authorityBox =
                new JComboBox<>(roles);

        authorityBox.setFont(
                new Font("Arial", Font.PLAIN, 14)
        );

        authorityBox.setBackground(Color.WHITE);

        authorityBox.setPreferredSize(
                new Dimension(200, 35)
        );
        
        // FIX: Pre-select current user authority role
        authorityBox.setSelectedItem(currentAuthority);

        gbc.gridy = 4;

        gbc.insets = new Insets(0, 20, 12, 20);

        mainPanel.add(authorityBox, gbc);

        /* =========================================================
           PASSWORD
           ========================================================= */
        JLabel passwordLabel =
                new JLabel("Password:");

        passwordLabel.setFont(
                new Font("Arial", Font.BOLD, 14)
        );

        passwordLabel.setForeground(
                new Color(60, 60, 60)
        );

        gbc.gridy = 5;

        gbc.insets = new Insets(5, 20, 5, 20);

        mainPanel.add(passwordLabel, gbc);

        passwordField =
                new JPasswordField();

        passwordField.setFont(
                new Font("Arial", Font.PLAIN, 14)
        );

        passwordField.setPreferredSize(
                new Dimension(200, 35)
        );

        passwordField.setBackground(Color.WHITE);

        gbc.gridy = 6;

        gbc.insets = new Insets(0, 20, 25, 20);

        mainPanel.add(passwordField, gbc);

        /* =========================================================
           BUTTON PANEL
           ========================================================= */
        JPanel buttonPanel =
                new JPanel(new GridBagLayout());

        buttonPanel.setBackground(mainBg);

        GridBagConstraints btnGbc =
                new GridBagConstraints();

        btnGbc.insets =
                new Insets(5, 10, 5, 10);

        saveButton =
                createStyledButton("Save");

        cancelButton =
                createStyledButton("Cancel");

        saveButton.setPreferredSize(
                new Dimension(100, 35)
        );

        cancelButton.setPreferredSize(
                new Dimension(100, 35)
        );

        btnGbc.gridx = 0;

        buttonPanel.add(saveButton, btnGbc);

        btnGbc.gridx = 1;

        buttonPanel.add(cancelButton, btnGbc);

        gbc.gridy = 7;

        gbc.insets = new Insets(10, 20, 30, 20);

        mainPanel.add(buttonPanel, gbc);

        /* =========================================================
           CANCEL BUTTON ACTION
           ========================================================= */
        cancelButton.addActionListener(e -> {
            dispose();
        });

        /* =========================================================
           SAVE BUTTON ACTION
           ========================================================= */
        // FIX: Moved inside the constructor block so it compiles properly
        saveButton.addActionListener(e -> {

            String username =
                    usernameField.getText().trim();

            String authority =
                    authorityBox.getSelectedItem().toString();

            String password =
                    String.valueOf(passwordField.getPassword()).trim();

            /* =========================================================
               VALIDATION
               ========================================================= */
            if (username.isEmpty() || password.isEmpty()) {

                system.NoticeFunctions.error(
                        this,
                        "Please complete all fields."
                );

                return;
            }

            /* =========================================================
               CONFIRMATION
               ========================================================= */
            boolean confirmed =
                    system.NoticeFunctions.confirm(
                            this,
                            "Save account changes?"
                    );

            if (!confirmed) {
                return;
            }

            /* =========================================================
               TEMPORARY SUCCESS NOTICE
               ========================================================= */
            system.NoticeFunctions.success(
                    this,
                    "Account updated successfully."
            );

            dispose();
        });

        // FIX: Moved to the very end of constructor to prevent layout/execution freeze
        setVisible(true);
    }

    /* =========================================================
       STYLED BUTTON CREATOR
       ========================================================= */
    private JButton createStyledButton(String text) {

        JButton button =
                new JButton(text);

        button.setFocusable(false);

        button.setFont(
                new Font("Arial", Font.BOLD, 13)
        );

        button.setForeground(Color.WHITE);

        button.setBackground(
                new Color(55, 55, 55)
        );

        button.setCursor(
                new Cursor(Cursor.HAND_CURSOR)
        );

        button.setFocusPainted(false);

        button.setBorder(
                BorderFactory.createLineBorder(
                        new Color(55, 55, 55),
                        1,
                        true
                )
        );

        button.addMouseListener(
                new MouseAdapter() {

                    public void mouseEntered(MouseEvent e) {

                        button.setBackground(
                                new Color(110, 170, 255)
                        );

                        button.setBorder(
                                BorderFactory.createLineBorder(
                                        new Color(110, 170, 255),
                                        1,
                                        true
                                )
                        );
                    }

                    public void mouseExited(MouseEvent e) {

                        button.setBackground(
                                new Color(55, 55, 55)
                        );

                        button.setBorder(
                                BorderFactory.createLineBorder(
                                        new Color(55, 55, 55),
                                        1,
                                        true
                                )
                        );
                    }
                }
        );

        return button;
    }

    /* =========================================================
       GETTERS
       ========================================================= */
    public JTextField getUsernameField() {
        return usernameField;
    }

    public JComboBox<String> getAuthorityBox() {
        return authorityBox;
    }

    public JPasswordField getPasswordField() {
        return passwordField;
    }

    public JButton getSaveButton() {
        return saveButton;
    }

    public JButton getCancelButton() {
        return cancelButton;
    }
}