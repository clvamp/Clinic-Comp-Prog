
package for_patient;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import for_patient.for_patient.contents.PatientAccount;
import for_patient.for_patient.contents.PatientMakeAppointment;

public class PatientDashboard extends JFrame {
    private static final long serialVersionUID = 1L;

    private String username;
    private PatientLogic logic;
    private JLabel labelAuth;

    // --- LUXURY CLINIC THEME COLORS ---
    private final Color SIDEBAR_BG = new Color(22, 41, 56);
    private final Color SIDEBAR_HOVER = new Color(34, 65, 87);
    private final Color TEXT_COLOR = new Color(240, 248, 255);
    private final Color LOGOUT_BG = new Color(160, 50, 60);
    private final Color LOGOUT_HOVER = new Color(185, 65, 75);
    private final Color MAIN_BG = new Color(235, 245, 250);

    public PatientDashboard(String username) {
        this.username = username;
        this.logic = new PatientLogic();

        setTitle("Clinic Patient Portal");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(1250, 750);
        setResizable(true);
        setLocationRelativeTo(null);
        getContentPane().setBackground(MAIN_BG);
        setLayout(new BorderLayout());

        /* =========================================================
           SIDEBAR PANEL
           ========================================================= */
        JPanel sideBar = new JPanel();
        sideBar.setPreferredSize(new Dimension(260, 0));
        sideBar.setBackground(SIDEBAR_BG);
        sideBar.setLayout(new BorderLayout());
        sideBar.setBorder(BorderFactory.createMatteBorder(0, 0, 0, 1, new Color(15, 30, 40)));

        /* =========================================================
           SIDEBAR HEADER
           ========================================================= */
        JPanel headerPanel = new JPanel();
        headerPanel.setLayout(new BoxLayout(headerPanel, BoxLayout.Y_AXIS));
        headerPanel.setBackground(SIDEBAR_BG);
        headerPanel.setBorder(BorderFactory.createEmptyBorder(40, 0, 40, 0));

        JLabel labelHead = new JLabel("PATIENT PORTAL");
        labelHead.setFont(new Font("Segoe UI", Font.BOLD, 22));
        labelHead.setForeground(Color.WHITE);
        labelHead.setAlignmentX(Component.CENTER_ALIGNMENT);

        labelAuth = new JLabel(username.toUpperCase());
        labelAuth.setFont(new Font("Segoe UI", Font.BOLD, 12));
        labelAuth.setForeground(new Color(120, 160, 180));
        labelAuth.setAlignmentX(Component.CENTER_ALIGNMENT);
        labelAuth.setBorder(BorderFactory.createEmptyBorder(5, 0, 0, 0));

        headerPanel.add(labelHead);
        headerPanel.add(labelAuth);
        sideBar.add(headerPanel, BorderLayout.NORTH);

        /* =========================================================
           CENTER BUTTON PANEL
           ========================================================= */
        JPanel buttonContainer = new JPanel();
        buttonContainer.setBackground(SIDEBAR_BG);
        buttonContainer.setLayout(new BoxLayout(buttonContainer, BoxLayout.Y_AXIS));

        Font buttonFont = new Font("Segoe UI", Font.BOLD, 15);

        JButton homeBtn = createSidebarButton("Home", buttonFont, SIDEBAR_BG, SIDEBAR_HOVER);
        JButton makeAppBtn = createSidebarButton("Make Appointment", buttonFont, SIDEBAR_BG, SIDEBAR_HOVER);
        JButton oldAppBtn = createSidebarButton("Old Appointments", buttonFont, SIDEBAR_BG, SIDEBAR_HOVER);
        JButton accountBtn = createSidebarButton("Account", buttonFont, SIDEBAR_BG, SIDEBAR_HOVER);

        buttonContainer.add(homeBtn);
        buttonContainer.add(makeAppBtn);
        buttonContainer.add(oldAppBtn);
        buttonContainer.add(accountBtn);

        sideBar.add(buttonContainer, BorderLayout.CENTER);

        /* =========================================================
           LOGOUT BUTTON
           ========================================================= */
        JPanel bottomPanel = new JPanel(new BorderLayout());
        bottomPanel.setBackground(SIDEBAR_BG);
        bottomPanel.setBorder(BorderFactory.createEmptyBorder(0, 0, 30, 0));

        JButton logOutBtn = createSidebarButton("Log Out", buttonFont, LOGOUT_BG, LOGOUT_HOVER);
        logOutBtn.setForeground(Color.WHITE);

        bottomPanel.add(logOutBtn, BorderLayout.CENTER);
        sideBar.add(bottomPanel, BorderLayout.SOUTH);

        /* =========================================================
           MAIN CONTENT AREA
           ========================================================= */
        final JPanel[] mainContent = { loadHomePanel() };

        add(sideBar, BorderLayout.WEST);
        add(mainContent[0], BorderLayout.CENTER);

        setVisible(true);

        /* =========================================================
           ACTION LISTENERS
           ========================================================= */
        homeBtn.addActionListener(e -> switchPanel(mainContent, loadHomePanel()));
        makeAppBtn.addActionListener(e -> switchPanel(mainContent, loadMakeAppointmentPanel()));
        oldAppBtn.addActionListener(e -> switchPanel(mainContent, loadOldAppointmentsPanel()));
        accountBtn.addActionListener(e -> switchPanel(mainContent, loadAccountPanel()));
        logOutBtn.addActionListener(e -> logic.patientLogOut(this));
    }

    private void switchPanel(JPanel[] mainContent, JPanel newPanel) {
        getContentPane().remove(mainContent[0]);
        mainContent[0] = newPanel;
        add(mainContent[0], BorderLayout.CENTER);
        revalidate();
        repaint();
    }

    private JButton createSidebarButton(String text, Font font, Color defaultColor, Color hoverColor) {
        JButton button = new JButton(text);

        button.setFocusable(false);
        button.setFont(font);
        button.setForeground(TEXT_COLOR);
        button.setBackground(defaultColor);
        button.setBorderPainted(false);
        button.setContentAreaFilled(true);
        button.setCursor(new Cursor(Cursor.HAND_CURSOR));
        button.setHorizontalAlignment(SwingConstants.LEFT);
        button.setBorder(BorderFactory.createEmptyBorder(16, 35, 16, 20));
        button.setMaximumSize(new Dimension(Integer.MAX_VALUE, 55));
        button.setAlignmentX(Component.CENTER_ALIGNMENT);

        button.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                button.setBackground(hoverColor);
            }

            @Override
            public void mouseExited(MouseEvent e) {
                button.setBackground(defaultColor);
            }
        });

        return button;
    }

    private JPanel loadHomePanel() {
        return createPlaceholderPanel("Welcome Home, " + username + "!");
    }

    private JPanel loadMakeAppointmentPanel() {
        return new PatientMakeAppointment(logic, username);
    }

    private JPanel loadOldAppointmentsPanel() {
        return createPlaceholderPanel("Historical Patient Appointment Logs");
    }

    private JPanel loadAccountPanel() {
        return new PatientAccount(logic, this, username);
    }

    public void refreshDashboard(String newUsername) {
        this.username = newUsername;
        labelAuth.setText(newUsername.toUpperCase());
    }
    
    public String getCurrentUsername() {
        return username;
    }

    public void updateUsername(String newUsername) {
        this.username = newUsername;
        labelAuth.setText(newUsername.toUpperCase());
    }

    private JPanel createPlaceholderPanel(String titleText) {
        JPanel panel = new JPanel(new GridBagLayout());
        panel.setBackground(MAIN_BG);
        JLabel label = new JLabel(titleText);
        label.setFont(new Font("Segoe UI", Font.BOLD, 20));
        label.setForeground(new Color(70, 90, 100));
        panel.add(label);
        return panel;
    }
}
