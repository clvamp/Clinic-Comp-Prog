package GUI;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;

import for_patient.PatientLoginGUI;
import for_patient.PatientLogic; // ADDED: Import for patient subsystem logic
import system.ClinicAccountSystem;

public class NoticeGUI extends JDialog {

    /* =========================================================
       ORIGINAL NOTICE DIALOG
       ========================================================= */
    public NoticeGUI(Window parent,
                     String title,
                     String message,
                     Color accentColor) {

        super(parent instanceof Frame ? (Frame) parent : null);

        setModal(true);
        setUndecorated(true);
        setSize(330, 220);
        setLocationRelativeTo(parent);
        setLayout(new BorderLayout());

        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new BorderLayout());
        mainPanel.setBackground(new Color(245, 245, 245));
        mainPanel.setBorder(BorderFactory.createLineBorder(accentColor, 3));

        add(mainPanel);

        JPanel topPanel = new JPanel(new BorderLayout());
        topPanel.setBackground(accentColor);
        topPanel.setPreferredSize(new Dimension(400, 50));

        JLabel titleLabel = new JLabel(title);
        titleLabel.setForeground(Color.WHITE);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 18));
        titleLabel.setBorder(new EmptyBorder(0, 15, 0, 0));

        topPanel.add(titleLabel, BorderLayout.WEST);

        mainPanel.add(topPanel, BorderLayout.NORTH);

        JPanel centerPanel = new JPanel(new GridBagLayout());
        centerPanel.setBackground(new Color(245, 245, 245));

        JLabel messageLabel = new JLabel(
                "<html><div style='width:240px; text-align:center;'>"
                        + message
                        + "</div></html>"
        );

        messageLabel.setFont(new Font("Arial", Font.PLAIN, 15));
        messageLabel.setForeground(new Color(50, 50, 50));

        centerPanel.add(messageLabel);

        mainPanel.add(centerPanel, BorderLayout.CENTER);

        JPanel buttonPanel = new JPanel();
        buttonPanel.setBackground(new Color(245, 245, 245));
        buttonPanel.setBorder(new EmptyBorder(0, 0, 15, 0));

        JButton okButton = new JButton("OK");

        okButton.setFocusable(false);
        okButton.setCursor(new Cursor(Cursor.HAND_CURSOR));
        okButton.setFont(new Font("Arial", Font.BOLD, 13));
        okButton.setForeground(Color.WHITE);
        okButton.setBackground(accentColor);
        okButton.setPreferredSize(new Dimension(100, 35));
        okButton.setBorderPainted(false);

        okButton.addActionListener(e -> dispose());

        buttonPanel.add(okButton);

        mainPanel.add(buttonPanel, BorderLayout.SOUTH);

        setVisible(true);
    }

    /* =========================================================
       NEW ROLE SELECTOR NOTICE
       ========================================================= */
    public NoticeGUI(Window parent,
                     ClinicAccountSystem system) {

        super(parent instanceof Frame ? (Frame) parent : null);

        Color accentColor = new Color(52, 152, 219);

        setModal(true);
        setUndecorated(true);
        setSize(450, 280);
        setLocationRelativeTo(parent);
        setLayout(new BorderLayout());

        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new BorderLayout());
        mainPanel.setBackground(new Color(245, 245, 245));
        mainPanel.setBorder(BorderFactory.createLineBorder(accentColor, 3));

        add(mainPanel);

        /* =========================================================
           TOP BAR
           ========================================================= */
        JPanel topPanel = new JPanel(new BorderLayout());
        topPanel.setBackground(accentColor);
        topPanel.setPreferredSize(new Dimension(450, 55));

        JLabel titleLabel = new JLabel("Clinic System Access");
        titleLabel.setForeground(Color.WHITE);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 20));
        titleLabel.setBorder(new EmptyBorder(0, 15, 0, 0));

        topPanel.add(titleLabel, BorderLayout.WEST);
        mainPanel.add(topPanel, BorderLayout.NORTH);

        /* =========================================================
           CENTER MESSAGE
           ========================================================= */
        JPanel centerPanel = new JPanel(new GridBagLayout());
        centerPanel.setBackground(new Color(245, 245, 245));

        JLabel messageLabel = new JLabel(
                "<html><div style='width:300px; text-align:center;'>"
                        + "Are you a <b>Patient</b> or an <b>Admin</b>?"
                        + "</div></html>"
        );

        messageLabel.setFont(new Font("Arial", Font.PLAIN, 18));
        messageLabel.setForeground(new Color(50, 50, 50));

        centerPanel.add(messageLabel);

        mainPanel.add(centerPanel, BorderLayout.CENTER);

        /* =========================================================
           BUTTON PANEL
           ========================================================= */
        JPanel buttonPanel = new JPanel();
        buttonPanel.setBackground(new Color(245, 245, 245));
        buttonPanel.setBorder(new EmptyBorder(10, 0, 20, 0));

        JButton patientButton = new JButton("Patient");
        JButton adminButton = new JButton("Admin");

        styleButton(patientButton, new Color(46, 204, 113));
        styleButton(adminButton, new Color(231, 76, 60));

        // FIX: Instantiated PatientLoginGUI with PatientLogic instead of ClinicAccountSystem
        patientButton.addActionListener(e -> {
            dispose();
            new PatientLoginGUI(new PatientLogic());
        });

        adminButton.addActionListener(e -> {
            dispose();
            new LogInGUI(system);
        });

        buttonPanel.add(patientButton);
        buttonPanel.add(Box.createRigidArea(new Dimension(20, 0)));
        buttonPanel.add(adminButton);

        mainPanel.add(buttonPanel, BorderLayout.SOUTH);

        setVisible(true);
    }

    /* =========================================================
       BUTTON STYLING FUNCTION
       ========================================================= */
    private void styleButton(JButton button, Color color) {
        button.setFocusable(false);
        button.setCursor(new Cursor(Cursor.HAND_CURSOR));
        button.setFont(new Font("Arial", Font.BOLD, 14));
        button.setForeground(Color.WHITE);
        button.setBackground(color);
        button.setPreferredSize(new Dimension(130, 40));
        button.setBorderPainted(false);
    }
}