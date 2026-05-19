
package for_patient.for_patient.contents;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;

import for_patient.PatientDashboard;
import for_patient.PatientLogic;

public class PatientAccount extends JPanel {
    private static final long serialVersionUID = 1L;

    private PatientLogic logic;
    private String username;

    // Instance fields for dynamic updates
    private JTextField usernameField;
    private JTextField accountTypeField;
    private PatientDashboard dashboard;
    private JLabel usernameLabel;

    public PatientAccount(PatientLogic logic, PatientDashboard dashboard, String username) {
        this.logic = logic;
        this.dashboard = dashboard;
        this.username = username;
        setLayout(new BorderLayout());
        setBackground(Color.WHITE);
        initializeUI();
    }

    /* =========================================================
       DYNAMIC REFRESH LISTENER
       ========================================================= */
    public void updateAccountDisplay(String username) {
        this.username = username;
        usernameLabel.setText(username);
        usernameField.setText(username);
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

        JLabel titleLabel = new JLabel("My Account");
        titleLabel.setFont(new Font("Arial", Font.BOLD, 32));
        titleLabel.setForeground(new Color(34, 40, 49));

        JLabel subtitleLabel = new JLabel("Manage your personal profile information and portal security settings.");
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
           LEFT PANEL (PATIENT INFO)
           ========================================================= */
        JPanel accountInfoPanel = new JPanel();
        accountInfoPanel.setLayout(new BoxLayout(accountInfoPanel, BoxLayout.Y_AXIS));
        accountInfoPanel.setBackground(new Color(248, 249, 250));
        accountInfoPanel.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(220, 220, 220), 1),
                new EmptyBorder(25, 25, 25, 25)
        ));
        accountInfoPanel.setPreferredSize(new Dimension(420, 500));

        JLabel accountTitle = new JLabel("  Patient Profile");
        accountTitle.setFont(new Font("Arial", Font.BOLD, 24));
        accountTitle.setForeground(new Color(34, 40, 49));
        accountInfoPanel.add(accountTitle);
        accountInfoPanel.add(Box.createRigidArea(new Dimension(80, 60)));

        /* =========================================================
           USERNAME FIELD
           ========================================================= */
        usernameLabel = createFieldLabel("Username");
        usernameField = createTextField(username);

        /* =========================================================
           ACCOUNT TYPE FIELD
           ========================================================= */
        JLabel accountTypeLabel = createFieldLabel("Account Classification");
        accountTypeField = createTextField("Registered Clinic Patient");

        /* =========================================================
           PASSWORD PLACEHOLDER
           ========================================================= */
        JLabel passwordLabel = createFieldLabel("Portal Password");
        JPasswordField passwordField = createPasswordField("••••••••");

        /* =========================================================
           EDIT BUTTON
           ========================================================= */
        JButton editButton = new JButton("Edit Profile");
        editButton.setFocusPainted(false);
        editButton.setCursor(new Cursor(Cursor.HAND_CURSOR));
        editButton.setBackground(new Color(57, 62, 70));
        editButton.setForeground(Color.WHITE);
        editButton.setFont(new Font("Arial", Font.BOLD, 15));
        editButton.setMaximumSize(new Dimension(300, 45));
        editButton.setPreferredSize(new Dimension(300, 45));
        editButton.setAlignmentX(Component.LEFT_ALIGNMENT);

        /* =========================================================
           OPEN ACCOUNT FORM LINKAGE
           ========================================================= */
        editButton.addActionListener(e -> {

            JFrame parentFrame =
                    (JFrame) SwingUtilities.getWindowAncestor(this);

            new PatientAccountForm(
			        parentFrame,
			        logic,
			        dashboard,
			        username
			);

            /* =========================================================
               REFRESH ACCOUNT DISPLAY AFTER FORM CLOSES
               ========================================================= */
            updateAccountDisplay(
                    dashboard.getCurrentUsername()
            );
        });

        /* =========================================================
           ADD COMPONENTS TO PROFILE PANEL
           ========================================================= */
        accountInfoPanel.add(usernameLabel);
        accountInfoPanel.add(Box.createRigidArea(new Dimension(0, 8)));
        accountInfoPanel.add(usernameField);
        accountInfoPanel.add(Box.createRigidArea(new Dimension(0, 22)));

        accountInfoPanel.add(accountTypeLabel);
        accountInfoPanel.add(Box.createRigidArea(new Dimension(0, 8)));
        accountInfoPanel.add(accountTypeField);
        accountInfoPanel.add(Box.createRigidArea(new Dimension(0, 22)));

        accountInfoPanel.add(passwordLabel);
        accountInfoPanel.add(Box.createRigidArea(new Dimension(0, 8)));
        accountInfoPanel.add(passwordField);
        accountInfoPanel.add(Box.createRigidArea(new Dimension(0, 35)));
        accountInfoPanel.add(editButton);

        /* =========================================================
           RIGHT PANEL (PATIENT PORTAL CARDS)
           ========================================================= */
        JPanel rightPanel = new JPanel(new GridBagLayout());
        rightPanel.setOpaque(false);

        GridBagConstraints rightGbc = new GridBagConstraints();
        rightGbc.insets = new Insets(10, 10, 10, 10);
        rightGbc.fill = GridBagConstraints.BOTH;
        rightGbc.weightx = 1;
        rightGbc.weighty = 1;

        JPanel securityCard = createCard("Security Settings", "Update your account authorization password and secure credential protections.");
        JPanel privacyCard = createCard("Medical Privacy", "Review your patient record disclosures and data protection privacy standards.");
        JPanel historyCard = createCard("Activity History", "Monitor account authentication timestamps and profile modification history logs.");
        JPanel supportCard = createCard("Clinic Assistance", "Access help desks or reach out to administration regarding health profile discrepancies.");

        rightGbc.gridx = 0; rightGbc.gridy = 0;
        rightPanel.add(securityCard, rightGbc);
        rightGbc.gridx = 1;
        rightPanel.add(privacyCard, rightGbc);
        rightGbc.gridx = 0; rightGbc.gridy = 1;
        rightPanel.add(historyCard, rightGbc);
        rightGbc.gridx = 1;
        rightPanel.add(supportCard, rightGbc);

        /* =========================================================
           ADD PANELS TO WRAPPER
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

    /* =========================================================
       HELPER GENERATOR METHODS
       ========================================================= */
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
