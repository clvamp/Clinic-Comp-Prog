package GUI;

import javax.swing.*;

import system.ClinicAccountSystem;
import system.ClinicDashboard;

import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class DashboardGUI {

    private ClinicAccountSystem system;

    public DashboardGUI(ClinicAccountSystem system, ClinicDashboard dashboard) {
        this.system = system;

        // GET AUTHORITY FROM DASHBOARD
        String authority = dashboard.getAuthority();

        // MAIN FRAME
        JFrame frame = new JFrame("Clinic Application - " + authority);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(1250, 750);
        frame.setResizable(true);
        frame.setLocationRelativeTo(null);
        frame.getContentPane().setBackground(Color.white);
        frame.setLayout(new BorderLayout());

        /* =========================================================
           SIDEBAR PANEL (Width 250)
           ========================================================= */
        JPanel sideBar = new JPanel();
        sideBar.setPreferredSize(new Dimension(250, 0));
        sideBar.setBackground(new Color(34, 40, 49));
        sideBar.setLayout(new BorderLayout());

        /* =========================================================
           SIDEBAR TITLE
           ========================================================= */
        JLabel labelHead = new JLabel("Dashboard");
        labelHead.setFont(new Font("Arial", Font.BOLD, 28));
        labelHead.setForeground(Color.WHITE);
        labelHead.setHorizontalAlignment(JLabel.CENTER);
        labelHead.setBorder(BorderFactory.createEmptyBorder(30, 0, 30, 0));
        sideBar.add(labelHead, BorderLayout.NORTH);

        /* =========================================================
           CENTER BUTTON PANEL
           ========================================================= */
        JPanel buttonContainer = new JPanel();
        buttonContainer.setBackground(new Color(34, 40, 49));
        buttonContainer.setLayout(new GridLayout(10, 1, 0, 0));

        Font buttonFont = new Font("Arial", Font.BOLD, 16);

        JButton homeBtn = createSidebarButton("Home", buttonFont);
        buttonContainer.add(homeBtn);

        JButton dataBtn = createSidebarButton("Patient Data", buttonFont);
        buttonContainer.add(dataBtn);

        JButton concernsBtn = createSidebarButton("Concerns", buttonFont);
        buttonContainer.add(concernsBtn);

        JButton appointmentsBtn = createSidebarButton("Appointments", buttonFont);
        buttonContainer.add(appointmentsBtn);

        JButton accManageBtn = createSidebarButton("Account", buttonFont);
        buttonContainer.add(accManageBtn);

        sideBar.add(buttonContainer, BorderLayout.CENTER);

        /* =========================================================
           LOGOUT BUTTON
           ========================================================= */
        JPanel bottomPanel = new JPanel();
        bottomPanel.setBackground(new Color(34, 40, 49));
        bottomPanel.setLayout(new BorderLayout());
        bottomPanel.setBorder(null);

        JButton logOutBtn = createSidebarButton("Log Out", buttonFont);
        logOutBtn.setBackground(new Color(180, 40, 50));
        logOutBtn.setForeground(Color.WHITE);
        logOutBtn.setBorder(BorderFactory.createEmptyBorder(15, 0, 15, 0));

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
        
     // ACCOUNT BUTTON
        accManageBtn.addActionListener(e -> {
            
            // FIX: Explicitly handle additions/removals through the Content Pane container layer
            Container contentPane = frame.getContentPane();

            // NOTE: If mainContent is nested inside a separate panel wrapper (e.g. bodyPanel), 
            // change 'contentPane' below directly to your 'bodyPanel' object name!
            contentPane.remove(mainContent[0]);

            mainContent[0] = dashboard.loadAccountPanel();

            contentPane.add(mainContent[0], BorderLayout.CENTER);

            // Forces layout calculations and component hierarchy redraw instantly
            contentPane.revalidate();
            contentPane.repaint();
        });

        // LOGOUT BUTTON
        logOutBtn.addActionListener(e -> {
            system.logOutFunction(frame);
        });
    }

    /* =========================================================
       SIDEBAR BUTTON DESIGN METHOD
       ========================================================= */
    private JButton createSidebarButton(String text, Font font) {
        JButton button = new JButton(text);

        button.setFocusable(false);
        button.setFont(font);
        button.setForeground(new Color(238, 238, 238));
        button.setBackground(new Color(57, 62, 70));

        button.setBorderPainted(false);
        button.setContentAreaFilled(true);
        button.setAlignmentX(Component.CENTER_ALIGNMENT);

        button.setCursor(new Cursor(Cursor.HAND_CURSOR));

        /* =========================================================
           HOVER EFFECT
           ========================================================= */
        button.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                button.setBackground(new Color(48, 71, 94));
            }

            @Override
            public void mouseExited(MouseEvent e) {
                if (text.equals("Log Out")) {
                    button.setBackground(new Color(180, 40, 50));
                } else {
                    button.setBackground(new Color(57, 62, 70));
                }
            }
        });

        return button;
    }
}