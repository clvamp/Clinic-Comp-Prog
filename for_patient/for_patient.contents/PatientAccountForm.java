package for_patient.for_patient.contents;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import for_patient.PatientDashboard;
import for_patient.PatientLogic;
import system.NoticeFunctions;

public class PatientAccountForm extends JDialog {
    private static final long serialVersionUID = 1L;

    private PatientLogic logic;
    private PatientDashboard dashboard;
    private String currentUsername;

    private JTextField usernameField;
    private JPasswordField passwordField;
    private JPasswordField confirmPasswordField;

    private JButton saveButton;
    private JButton cancelButton;

    public PatientAccountForm(JFrame parentFrame,
                              PatientLogic logic,
                              PatientDashboard dashboard,
                              String currentUsername) {

        super(parentFrame, "Account Profile Management", true);

        this.logic = logic;
        this.dashboard = dashboard;
        this.currentUsername = currentUsername;

        setSize(450, 490);
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

        /* =========================================================
           HEADER
           ========================================================= */
        JLabel headerLabel = new JLabel("EDIT PROFILE");

        headerLabel.setFont(new Font("Arial", Font.BOLD, 24));
        headerLabel.setForeground(new Color(40, 40, 40));
        headerLabel.setHorizontalAlignment(JLabel.CENTER);

        gbc.gridy = 0;
        gbc.insets = new Insets(20, 20, 15, 20);

        mainPanel.add(headerLabel, gbc);

        /* =========================================================
           USERNAME FIELD
           ========================================================= */
        JLabel usernameLabel = new JLabel("Username:");

        usernameLabel.setFont(new Font("Arial", Font.BOLD, 14));
        usernameLabel.setForeground(new Color(60, 60, 60));

        gbc.gridy = 1;
        gbc.insets = new Insets(15, 20, 5, 20);

        mainPanel.add(usernameLabel, gbc);

        usernameField = new JTextField(currentUsername);

        usernameField.setFont(new Font("Arial", Font.PLAIN, 14));
        usernameField.setPreferredSize(new Dimension(200, 35));
        usernameField.setBackground(Color.WHITE);

        gbc.gridy = 2;
        gbc.insets = new Insets(0, 20, 12, 20);

        mainPanel.add(usernameField, gbc);

        /* =========================================================
           PASSWORD FIELD
           ========================================================= */
        JLabel passwordLabel = new JLabel("New Password:");

        passwordLabel.setFont(new Font("Arial", Font.BOLD, 14));
        passwordLabel.setForeground(new Color(60, 60, 60));

        gbc.gridy = 3;
        gbc.insets = new Insets(5, 20, 5, 20);

        mainPanel.add(passwordLabel, gbc);

        passwordField = new JPasswordField();

        passwordField.setFont(new Font("Arial", Font.PLAIN, 14));
        passwordField.setPreferredSize(new Dimension(200, 35));
        passwordField.setBackground(Color.WHITE);

        gbc.gridy = 4;
        gbc.insets = new Insets(0, 20, 12, 20);

        mainPanel.add(passwordField, gbc);

        /* =========================================================
           CONFIRM PASSWORD FIELD
           ========================================================= */
        JLabel confirmPasswordLabel = new JLabel("Confirm Password:");

        confirmPasswordLabel.setFont(new Font("Arial", Font.BOLD, 14));
        confirmPasswordLabel.setForeground(new Color(60, 60, 60));

        gbc.gridy = 5;
        gbc.insets = new Insets(5, 20, 5, 20);

        mainPanel.add(confirmPasswordLabel, gbc);

        confirmPasswordField = new JPasswordField();

        confirmPasswordField.setFont(new Font("Arial", Font.PLAIN, 14));
        confirmPasswordField.setPreferredSize(new Dimension(200, 35));
        confirmPasswordField.setBackground(Color.WHITE);

        gbc.gridy = 6;
        gbc.insets = new Insets(0, 20, 25, 20);

        mainPanel.add(confirmPasswordField, gbc);

        /* =========================================================
           BUTTON PANEL
           ========================================================= */
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

        gbc.gridy = 7;
        gbc.insets = new Insets(10, 20, 25, 20);

        mainPanel.add(buttonPanel, gbc);

        /* =========================================================
           CANCEL BUTTON
           ========================================================= */
        cancelButton.addActionListener(e -> {

            boolean confirmCancel = NoticeFunctions.confirm(
                    this,
                    "Are you sure you want to cancel editing?"
            );

            if (confirmCancel) {
                dispose();
            }
        });

        /* =========================================================
           SAVE BUTTON
           ========================================================= */
        saveButton.addActionListener(e -> onSave());

        setVisible(true);
    }

    /* =========================================================
       SAVE LOGIC
       ========================================================= */
    private void onSave() {

        String newUsername = usernameField.getText().trim();
        String newPassword = new String(passwordField.getPassword()).trim();
        String confirmPassword = new String(confirmPasswordField.getPassword()).trim();

        /* =========================================================
           VALIDATIONS
           ========================================================= */
        if (newUsername.isEmpty()) {

            NoticeFunctions.error(
                    this,
                    "Username cannot be empty."
            );

            return;
        }

        if (!newPassword.equals(confirmPassword)) {

            NoticeFunctions.error(
                    this,
                    "Passwords do not match."
            );

            return;
        }

        /* =========================================================
           CONFIRM SAVE
           ========================================================= */
        boolean confirmed = NoticeFunctions.confirm(
                this,
                "Save account changes?"
        );

        if (!confirmed) {
            return;
        }

        /* =========================================================
           UPDATE ACCOUNT
           ========================================================= */
        boolean success = logic.updatePatientAccount(
                currentUsername,
                newUsername,
                newPassword
        );

        /* =========================================================
           SUCCESS
           ========================================================= */
        if (success) {

            dashboard.refreshDashboard(newUsername);

            NoticeFunctions.success(
                    this,
                    "Account updated successfully."
            );

            dispose();
        }

        /* =========================================================
           FAILED
           ========================================================= */
        else {

            NoticeFunctions.error(
                    this,
                    "Failed to update account."
            );
        }
    }

    /* =========================================================
       BUTTON DESIGN
       ========================================================= */
    private JButton createStyledButton(String text) {

        JButton button = new JButton(text);

        button.setFocusable(false);
        button.setFont(new Font("Arial", Font.BOLD, 13));
        button.setForeground(Color.WHITE);
        button.setBackground(new Color(55, 55, 55));
        button.setCursor(new Cursor(Cursor.HAND_CURSOR));
        button.setFocusPainted(false);

        button.setBorder(
                BorderFactory.createLineBorder(
                        new Color(55, 55, 55),
                        1,
                        true
                )
        );

        button.addMouseListener(new MouseAdapter() {

            @Override
            public void mouseEntered(MouseEvent e) {

                button.setBackground(new Color(110, 170, 255));

                button.setBorder(
                        BorderFactory.createLineBorder(
                                new Color(110, 170, 255),
                                1,
                                true
                        )
                );
            }

            @Override
            public void mouseExited(MouseEvent e) {

                button.setBackground(new Color(55, 55, 55));

                button.setBorder(
                        BorderFactory.createLineBorder(
                                new Color(55, 55, 55),
                                1,
                                true
                        )
                );
            }
        });

        return button;
    }

    /* =========================================================
       GETTERS
       ========================================================= */
    public JTextField getUsernameField() {
        return usernameField;
    }

    public JPasswordField getPasswordField() {
        return passwordField;
    }

    public JPasswordField getConfirmPasswordField() {
        return confirmPasswordField;
    }

    public JButton getSaveButton() {
        return saveButton;
    }

    public JButton getCancelButton() {
        return cancelButton;
    }
}