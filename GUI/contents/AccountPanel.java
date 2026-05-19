package GUI.contents;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import system.ClinicAccountSystem;

public class AccountPanel extends JPanel {

    private ClinicAccountSystem system;
    private String username;
    private String authority;

    // FIX: Converted to class instance fields so they can be modified dynamically later
    private JTextField usernameField;
    private JComboBox<String> authorityBox;

    // FIX: Added ClinicAccountSystem parameter to constructor
    public AccountPanel(ClinicAccountSystem system, String username, String authority) {
        this.system = system;
        this.username = username;
        this.authority = authority;

        setLayout(new BorderLayout());
        setBackground(Color.WHITE);

        initializeUI();
    }

    /* =========================================================
       DYNAMIC REFRESH LISTENER (NEW)
       ========================================================= */
    public void updateAccountDisplay(String newUsername, String newAuthority) {
        this.username = newUsername;
        this.authority = newAuthority;
        
        if (usernameField != null) {
            usernameField.setText(newUsername);
        }
        if (authorityBox != null) {
            authorityBox.setSelectedItem(newAuthority);
        }
    }

    /* =========================================================
       MAIN UI
       ========================================================= */
    private void initializeUI() {
        /* =========================================================
           HEADER
           ========================================================= */
        JPanel headerPanel = new JPanel(new BorderLayout());
        headerPanel.setBackground(new Color(245, 247, 250));
        headerPanel.setBorder(new EmptyBorder(30, 40, 30, 40));

        JLabel titleLabel = new JLabel("Account Management");
        titleLabel.setFont(new Font("Arial", Font.BOLD, 32));
        titleLabel.setForeground(new Color(34, 40, 49));

        JLabel subtitleLabel = new JLabel("Manage your account information and security settings.");
        subtitleLabel.setFont(new Font("Arial", Font.PLAIN, 16));
        subtitleLabel.setForeground(new Color(90, 90, 90));

        JPanel textPanel = new JPanel();
        textPanel.setLayout(new BoxLayout(textPanel, BoxLayout.Y_AXIS));
        textPanel.setBackground(headerPanel.getBackground());
        textPanel.add(titleLabel);
        textPanel.add(Box.createRigidArea(new Dimension(0, 10)));
        textPanel.add(subtitleLabel);

        headerPanel.add(textPanel, BorderLayout.WEST);
        add(headerPanel, BorderLayout.NORTH);

        /* =========================================================
           MAIN CONTENT WRAPPER
           ========================================================= */
        JPanel wrapperPanel = new JPanel(new BorderLayout());
        wrapperPanel.setBackground(Color.WHITE);
        wrapperPanel.setBorder(new EmptyBorder(40, 40, 40, 40));

        /* =========================================================
           MAIN CONTENT PANEL
           ========================================================= */
        JPanel contentPanel = new JPanel(new GridBagLayout());
        contentPanel.setOpaque(false);

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.fill = GridBagConstraints.BOTH;
        gbc.anchor = GridBagConstraints.NORTH;

        /* =========================================================
           LEFT PANEL
           ========================================================= */
        JPanel accountInfoPanel = new JPanel();
        accountInfoPanel.setLayout(new BoxLayout(accountInfoPanel, BoxLayout.Y_AXIS));
        accountInfoPanel.setBackground(new Color(248, 249, 250));
        accountInfoPanel.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(220, 220, 220), 1),
                new EmptyBorder(25, 25, 25, 25)
        ));
        accountInfoPanel.setPreferredSize(new Dimension(420, 500));

        JLabel accountTitle = new JLabel("    Account Information");
        accountTitle.setFont(new Font("Arial", Font.BOLD, 24));
        accountTitle.setForeground(new Color(34, 40, 49));
        accountInfoPanel.add(accountTitle);
        accountInfoPanel.add(Box.createRigidArea(new Dimension(80, 60)));

        /* =========================================================
           USERNAME FIELD
           ========================================================= */
        JLabel usernameLabel = createFieldLabel("Username");
        usernameField = createTextField(username); // Changed to assign directly to instance field

        /* =========================================================
           AUTHORITY FIELD
           ========================================================= */
        JLabel authorityLabel = createFieldLabel("Authority");
        String[] roles = {"Doctor", "Nurse"};
        authorityBox = new JComboBox<>(roles); // Changed to assign directly to instance field
        authorityBox.setSelectedItem(authority);
        authorityBox.setEnabled(false);
        authorityBox.setFont(new Font("Arial", Font.PLAIN, 14));
        authorityBox.setBackground(Color.WHITE);
        authorityBox.setMaximumSize(new Dimension(300, 42));
        authorityBox.setPreferredSize(new Dimension(300, 42));
        authorityBox.setMinimumSize(new Dimension(300, 42));
        authorityBox.setAlignmentX(Component.LEFT_ALIGNMENT);

        /* =========================================================
           PASSWORD FIELD
           ========================================================= */
        JLabel passwordLabel = createFieldLabel("Password");
        JPasswordField passwordField = createPasswordField("••••••••");

        /* =========================================================
           EDIT BUTTON
           ========================================================= */
        JButton editButton = new JButton("Edit Account");
        editButton.setFocusPainted(false);
        editButton.setCursor(new Cursor(Cursor.HAND_CURSOR));
        editButton.setBackground(new Color(57, 62, 70));
        editButton.setForeground(Color.WHITE);
        editButton.setFont(new Font("Arial", Font.BOLD, 15));
        editButton.setMaximumSize(new Dimension(300, 45));
        editButton.setPreferredSize(new Dimension(300, 45));
        editButton.setAlignmentX(Component.LEFT_ALIGNMENT);

        /* =========================================================
           OPEN ACCOUNT FORM (LINKED TO BACKEND PIPELINE)
           ========================================================= */
        editButton.addActionListener(e -> {
            JFrame parentFrame = (JFrame) SwingUtilities.getWindowAncestor(this);
            // FIX: Form constructor now links panel and backend system together
            new AccountForm(
                    parentFrame,
                    system,
                    this,
                    username,
                    authority
            );
        });

        /* =========================================================
           ADD COMPONENTS TO LEFT PANEL
           ========================================================= */
        accountInfoPanel.add(usernameLabel);
        accountInfoPanel.add(Box.createRigidArea(new Dimension(0, 8)));
        accountInfoPanel.add(usernameField);
        accountInfoPanel.add(Box.createRigidArea(new Dimension(0, 22)));

        accountInfoPanel.add(authorityLabel);
        accountInfoPanel.add(Box.createRigidArea(new Dimension(0, 8)));
        accountInfoPanel.add(authorityBox);
        accountInfoPanel.add(Box.createRigidArea(new Dimension(0, 22)));

        accountInfoPanel.add(passwordLabel);
        accountInfoPanel.add(Box.createRigidArea(new Dimension(0, 8)));
        accountInfoPanel.add(passwordField);
        accountInfoPanel.add(Box.createRigidArea(new Dimension(0, 35)));
        accountInfoPanel.add(editButton);

        /* =========================================================
           RIGHT PANEL
           ========================================================= */
        JPanel rightPanel = new JPanel(new GridBagLayout());
        rightPanel.setOpaque(false);

        GridBagConstraints rightGbc = new GridBagConstraints();
        rightGbc.insets = new Insets(10, 10, 10, 10);
        rightGbc.fill = GridBagConstraints.BOTH;
        rightGbc.weightx = 1;
        rightGbc.weighty = 1;

        JPanel securityCard = createCard("Security Settings", "Update password and maintain account protection.");
        JPanel accessCard = createCard("System Access", "Review authority access and clinic permissions.");
        JPanel activityCard = createCard("Account Activity", "Monitor recent account actions and security logs.");
        JPanel clinicCard = createCard("Clinic Dashboard", "Manage clinic dashboard access and workflow.");

        rightGbc.gridx = 0; rightGbc.gridy = 0;
        rightPanel.add(securityCard, rightGbc);
        rightGbc.gridx = 1;
        rightPanel.add(accessCard, rightGbc);
        rightGbc.gridx = 0; rightGbc.gridy = 1;
        rightPanel.add(activityCard, rightGbc);
        rightGbc.gridx = 1;
        rightPanel.add(clinicCard, rightGbc);

        /* =========================================================
           ADD TO WRAPPER
           ========================================================= */
        gbc.gridx = 0; gbc.gridy = 0; gbc.weightx = 0.32; gbc.weighty = 1;
        contentPanel.add(accountInfoPanel, gbc);

        gbc.gridx = 1; gbc.weightx = 0.68;
        contentPanel.add(rightPanel, gbc);

        wrapperPanel.add(contentPanel, BorderLayout.NORTH);

        JScrollPane scrollPane = new JScrollPane(wrapperPanel);
        scrollPane.setBorder(null);
        scrollPane.getVerticalScrollBar().setUnitIncrement(16);
        add(scrollPane, BorderLayout.CENTER);
    }

    private JLabel createFieldLabel(String text) {
        JLabel label = new JLabel(text);
        label.setFont(new Font("Arial", Font.BOLD, 15));
        label.setForeground(new Color(50, 50, 50));
        label.setAlignmentX(Component.LEFT_ALIGNMENT);
        return label;
    }

    private JTextField createTextField(String value) {
        JTextField field = new JTextField(value);
        field.setEditable(false);
        field.setFont(new Font("Arial", Font.PLAIN, 14));
        field.setBackground(Color.WHITE);
        field.setMaximumSize(new Dimension(300, 42));
        field.setPreferredSize(new Dimension(300, 42));
        field.setMinimumSize(new Dimension(300, 42));
        field.setAlignmentX(Component.LEFT_ALIGNMENT);
        return field;
    }

    private JPasswordField createPasswordField(String value) {
        JPasswordField field = new JPasswordField(value);
        field.setEditable(false);
        field.setFont(new Font("Arial", Font.PLAIN, 14));
        field.setBackground(Color.WHITE);
        field.setMaximumSize(new Dimension(300, 42));
        field.setPreferredSize(new Dimension(300, 42));
        field.setMinimumSize(new Dimension(300, 42));
        field.setAlignmentX(Component.LEFT_ALIGNMENT);
        return field;
    }

    private JPanel createCard(String title, String description) {
        JPanel card = new JPanel(new BorderLayout());
        card.setBackground(new Color(248, 249, 250));
        card.setPreferredSize(new Dimension(250, 180));
        card.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(220, 220, 220), 1),
                new EmptyBorder(20, 20, 20, 20)
        ));

        JLabel titleLabel = new JLabel(title);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 20));
        titleLabel.setForeground(new Color(34, 40, 49));

        JTextArea descArea = new JTextArea(description);
        descArea.setWrapStyleWord(true);
        descArea.setLineWrap(true);
        descArea.setEditable(false);
        descArea.setOpaque(false);
        descArea.setFont(new Font("Arial", Font.PLAIN, 14));
        descArea.setForeground(new Color(80, 80, 80));

        card.add(titleLabel, BorderLayout.NORTH);
        JPanel descPanel = new JPanel(new BorderLayout());
        descPanel.setOpaque(false);
        descPanel.setBorder(new EmptyBorder(15, 0, 0, 0));
        descPanel.add(descArea, BorderLayout.CENTER);
        card.add(descPanel, BorderLayout.CENTER);

        return card;
    }
}