package GUI;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import system.ClinicAccountSystem;
import system.ClinicDashboard;

public class DashboardGUI {

    private ClinicAccountSystem system;

    // --- LUXURY CLINIC THEME COLORS ---
    private final Color SIDEBAR_BG = new Color(22, 41, 56);       // Rich Deep Teal/Navy
    private final Color SIDEBAR_HOVER = new Color(34, 65, 87);    // Elegant lighter teal hover
    private final Color TEXT_COLOR = new Color(240, 248, 255);    // Soft Alice Blue for text
    private final Color LOGOUT_BG = new Color(160, 50, 60);       // Muted, premium crimson
    private final Color LOGOUT_HOVER = new Color(185, 65, 75);    // Brighter crimson hover
    private final Color MAIN_BG = new Color(235, 245, 250);       // Soft medical blue for main area

    public DashboardGUI(ClinicAccountSystem system, ClinicDashboard dashboard) {
        this.system = system;

        // GET AUTHORITY FROM DASHBOARD
        String authority = dashboard.getAuthority();

        // MAIN FRAME
        JFrame frame = new JFrame("Clinic Management System");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(1250, 750);
        frame.setResizable(true);
        frame.setLocationRelativeTo(null);
        frame.getContentPane().setBackground(MAIN_BG);
        frame.setLayout(new BorderLayout());

        /* =========================================================
           SIDEBAR PANEL
           ========================================================= */
        JPanel sideBar = new JPanel();
        sideBar.setPreferredSize(new Dimension(260, 0)); // Slightly wider for luxury feel
        sideBar.setBackground(SIDEBAR_BG);
        sideBar.setLayout(new BorderLayout());
        
        // Subtle right border for depth
        sideBar.setBorder(BorderFactory.createMatteBorder(0, 0, 0, 1, new Color(15, 30, 40)));

        /* =========================================================
           SIDEBAR HEADER
           ========================================================= */
        JPanel headerPanel = new JPanel();
        headerPanel.setLayout(new BoxLayout(headerPanel, BoxLayout.Y_AXIS));
        headerPanel.setBackground(SIDEBAR_BG);
        headerPanel.setBorder(BorderFactory.createEmptyBorder(40, 0, 40, 0));

        JLabel labelHead = new JLabel("DASHBOARD");
        labelHead.setFont(new Font("Segoe UI", Font.BOLD, 24));
        labelHead.setForeground(Color.WHITE);
        labelHead.setAlignmentX(Component.CENTER_ALIGNMENT);

        JLabel labelAuth = new JLabel(authority.toUpperCase());
        labelAuth.setFont(new Font("Segoe UI", Font.BOLD, 12));
        labelAuth.setForeground(new Color(120, 160, 180)); // Soft accent color
        labelAuth.setAlignmentX(Component.CENTER_ALIGNMENT);
        labelAuth.setBorder(BorderFactory.createEmptyBorder(5, 0, 0, 0));

        headerPanel.add(labelHead);
        headerPanel.add(labelAuth);
        sideBar.add(headerPanel, BorderLayout.NORTH);

        /* =========================================================
           CENTER BUTTON PANEL
           ========================================================= */
        // BoxLayout gives buttons a natural height and allows spacing
        JPanel buttonContainer = new JPanel();
        buttonContainer.setBackground(SIDEBAR_BG);
        buttonContainer.setLayout(new BoxLayout(buttonContainer, BoxLayout.Y_AXIS));
        buttonContainer.setBorder(BorderFactory.createEmptyBorder(0, 0, 0, 0));

        Font buttonFont = new Font("Segoe UI", Font.BOLD, 15);

        JButton homeBtn = createSidebarButton("Home", buttonFont, SIDEBAR_BG, SIDEBAR_HOVER);
        JButton dataBtn = createSidebarButton("Patient Data", buttonFont, SIDEBAR_BG, SIDEBAR_HOVER);
        JButton concernsBtn = createSidebarButton("Concerns", buttonFont, SIDEBAR_BG, SIDEBAR_HOVER);
        JButton appointmentsBtn = createSidebarButton("Appointments", buttonFont, SIDEBAR_BG, SIDEBAR_HOVER);
        JButton accManageBtn = createSidebarButton("Account", buttonFont, SIDEBAR_BG, SIDEBAR_HOVER);

        buttonContainer.add(homeBtn);
        buttonContainer.add(dataBtn);
        buttonContainer.add(concernsBtn);
        buttonContainer.add(appointmentsBtn);
        buttonContainer.add(accManageBtn);

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
           MAIN CONTENT AREA (DEFAULT HOME PANEL)
           ========================================================= */
        final JPanel[] mainContent = {dashboard.loadHomePanel()};

        frame.add(sideBar, BorderLayout.WEST);
        frame.add(mainContent[0], BorderLayout.CENTER);

        frame.setVisible(true);

        /* =========================================================
           ACTION LISTENERS
           ========================================================= */

        // HOME BUTTON
        homeBtn.addActionListener(e -> {
            frame.getContentPane().remove(mainContent[0]);
            mainContent[0] = dashboard.loadHomePanel();
            frame.add(mainContent[0], BorderLayout.CENTER);
            frame.revalidate();
            frame.repaint();
        });
        
        // APPOINTMENTS BUTTON
        appointmentsBtn.addActionListener(e -> {
            Container contentPane = frame.getContentPane();
            contentPane.remove(mainContent[0]);
            mainContent[0] = dashboard.loadNurseAppointments();
            contentPane.add(mainContent[0], BorderLayout.CENTER);
            contentPane.revalidate();
            contentPane.repaint();
        });
        
        // ACCOUNT BUTTON
        accManageBtn.addActionListener(e -> {
            Container contentPane = frame.getContentPane();
            contentPane.remove(mainContent[0]);
            mainContent[0] = dashboard.loadAccountPanel();
            contentPane.add(mainContent[0], BorderLayout.CENTER);
            contentPane.revalidate();
            contentPane.repaint();
        });

        // LOGOUT BUTTON
        logOutBtn.addActionListener(e -> {
            system.logOutFunction(frame);
        });
    }

    /* =========================================================
       SIDEBAR BUTTON DESIGN METHOD (PREMIUM STYLE)
       ========================================================= */
    private JButton createSidebarButton(String text, Font font, Color defaultColor, Color hoverColor) {
        JButton button = new JButton(text);

        button.setFocusable(false);
        button.setFont(font);
        button.setForeground(TEXT_COLOR);
        button.setBackground(defaultColor);

        // Styling for a web-like luxurious feel
        button.setBorderPainted(false);
        button.setContentAreaFilled(true);
        button.setCursor(new Cursor(Cursor.HAND_CURSOR));
        
        // Left align text with generous padding
        button.setHorizontalAlignment(SwingConstants.LEFT);
        button.setBorder(BorderFactory.createEmptyBorder(16, 35, 16, 20));
        
        // Force button to stretch to full width of the BoxLayout
        button.setMaximumSize(new Dimension(Integer.MAX_VALUE, 55));
        button.setAlignmentX(Component.CENTER_ALIGNMENT);

        /* =========================================================
           HOVER EFFECT
           ========================================================= */
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
}