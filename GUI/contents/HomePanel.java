package GUI.contents;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;

public class HomePanel extends JPanel {

    private String authority;

    public HomePanel(String authority) {
        this.authority = authority;

        setLayout(new BorderLayout());
        setBackground(Color.WHITE);

        initializeUI();
    }

    /* =========================================================
       MAIN UI INITIALIZATION
       ========================================================= */
    private void initializeUI() {

        /* =========================
           HEADER SECTION
           ========================= */
        JPanel headerPanel = new JPanel(new BorderLayout());
        headerPanel.setBackground(new Color(245, 247, 250));
        headerPanel.setBorder(new EmptyBorder(30, 40, 30, 40));

        JLabel welcomeLabel = new JLabel("Welcome to the " + authority + " Dashboard");
        welcomeLabel.setFont(new Font("Arial", Font.BOLD, 32));
        welcomeLabel.setForeground(new Color(34, 40, 49));

        JLabel subtitle = new JLabel("Manage clinic operations efficiently and securely.");
        subtitle.setFont(new Font("Arial", Font.PLAIN, 16));
        subtitle.setForeground(new Color(90, 90, 90));

        JPanel textContainer = new JPanel();
        textContainer.setLayout(new BoxLayout(textContainer, BoxLayout.Y_AXIS));
        textContainer.setBackground(headerPanel.getBackground());

        textContainer.add(welcomeLabel);
        textContainer.add(Box.createRigidArea(new Dimension(0, 10)));
        textContainer.add(subtitle);

        headerPanel.add(textContainer, BorderLayout.WEST);

        add(headerPanel, BorderLayout.NORTH);

        /* =========================
           MAIN BUTTON GRID
           ========================= */
        JPanel contentPanel = new JPanel(new GridBagLayout());
        contentPanel.setBackground(Color.WHITE);
        contentPanel.setBorder(new EmptyBorder(30, 40, 40, 40));

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(15, 15, 15, 15);
        gbc.fill = GridBagConstraints.BOTH;
        gbc.weightx = 1;
        gbc.weighty = 1;

        // Buttons / Feature Cards
        JPanel accountCard = createFeatureCard(
                "Account",
                "Manage your personal account, update credentials, change passwords, and maintain security settings."
        );

        JPanel patientCard = createFeatureCard(
                "Patient Data",
                "Access, organize, and maintain patient records, medical history, and clinical documentation."
        );

        JPanel concernsCard = createFeatureCard(
                "Concerns",
                "Review and address patient concerns, reports, issues, and healthcare inquiries efficiently."
        );

        JPanel appointmentsCard = createFeatureCard(
                "Appointments",
                "Schedule, monitor, confirm, and organize patient appointments with authority-based access."
        );

        JPanel noticesCard = createFeatureCard(
                "Notices",
                "Stay updated with clinic-wide announcements, reminders, and important administrative notifications."
        );

        JPanel dashboardInfoCard = createFeatureCard(
                "Dashboard Overview",
                "Monitor system activity, clinic performance, and overall workflow from one centralized interface."
        );

        // Row 1
        gbc.gridx = 0;
        gbc.gridy = 0;
        contentPanel.add(accountCard, gbc);

        gbc.gridx = 1;
        contentPanel.add(patientCard, gbc);

        gbc.gridx = 2;
        contentPanel.add(concernsCard, gbc);

        // Row 2
        gbc.gridx = 0;
        gbc.gridy = 1;
        contentPanel.add(appointmentsCard, gbc);

        gbc.gridx = 1;
        contentPanel.add(noticesCard, gbc);

        gbc.gridx = 2;
        contentPanel.add(dashboardInfoCard, gbc);

        add(new JScrollPane(contentPanel), BorderLayout.CENTER);
    }

    /* =========================================================
       FEATURE CARD CREATOR
       ========================================================= */
    private JPanel createFeatureCard(String title, String description) {

        JPanel card = new JPanel();
        card.setLayout(new BorderLayout());
        card.setBackground(new Color(248, 249, 250));
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
        card.add(Box.createRigidArea(new Dimension(0, 10)), BorderLayout.CENTER);

        JPanel descPanel = new JPanel(new BorderLayout());
        descPanel.setOpaque(false);
        descPanel.add(descArea, BorderLayout.CENTER);

        card.add(descPanel, BorderLayout.CENTER);

        return card;
    }
}