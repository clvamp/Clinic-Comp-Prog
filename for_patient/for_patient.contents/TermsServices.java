package for_patient.for_patient.contents;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class TermsServices extends JDialog {
    private static final long serialVersionUID = 1L;

    // Clinic Theme Colors
    private final Color primaryTeal = new Color(44, 62, 80);
    private final Color lightGray = new Color(248, 249, 250);
    private final Color borderGray = new Color(220, 224, 226);

    // Modified Constructor: Added the Runnable callback parameter
    public TermsServices(Window parentWindow, Runnable onAcknowledge) {
        super(parentWindow, "Terms and Services", ModalityType.APPLICATION_MODAL);

        /* =========================================================
           MAIN DIALOG SETTINGS
           ========================================================= */
        setSize(500, 600);
        setLocationRelativeTo(parentWindow);
        setResizable(false);
        setLayout(new BorderLayout());
        getContentPane().setBackground(Color.WHITE);

        /* =========================================================
           HEADER PANEL
           ========================================================= */
        JPanel headerPanel = new JPanel(new BorderLayout());
        headerPanel.setBackground(primaryTeal);
        headerPanel.setBorder(new EmptyBorder(20, 30, 20, 30));

        JLabel headerLabel = new JLabel("CLINICAL TERMS & PRIVACY POLICY");
        headerLabel.setFont(new Font("Segoe UI", Font.BOLD, 18));
        headerLabel.setForeground(Color.WHITE);
        
        headerPanel.add(headerLabel, BorderLayout.WEST);
        add(headerPanel, BorderLayout.NORTH);

        /* =========================================================
           MAIN TEXT CONTENT AREA
           ========================================================= */
        JPanel mainPanel = new JPanel(new BorderLayout());
        mainPanel.setBackground(Color.WHITE);
        mainPanel.setBorder(new EmptyBorder(20, 30, 20, 30));

        JTextArea termsTextArea = new JTextArea();
        termsTextArea.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        termsTextArea.setLineWrap(true);
        termsTextArea.setWrapStyleWord(true);
        termsTextArea.setEditable(false);
        termsTextArea.setBackground(Color.WHITE);
        termsTextArea.setForeground(new Color(50, 50, 50));

        termsTextArea.setText(getClinicalTermsText());

        JScrollPane scrollPane = new JScrollPane(termsTextArea);
        scrollPane.setBorder(BorderFactory.createLineBorder(borderGray));
        scrollPane.getVerticalScrollBar().setUnitIncrement(14);
        
        mainPanel.add(scrollPane, BorderLayout.CENTER);
        add(mainPanel, BorderLayout.CENTER);

        /* =========================================================
           BOTTOM ACTION PANEL
           ========================================================= */
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT, 15, 15));
        buttonPanel.setBackground(lightGray);
        buttonPanel.setBorder(BorderFactory.createMatteBorder(1, 0, 0, 0, borderGray));

        JButton acknowledgeButton = createStyledButton("I Understand", primaryTeal, new Color(52, 73, 94));
        buttonPanel.add(acknowledgeButton);
        add(buttonPanel, BorderLayout.SOUTH);

        /* =========================================================
           ACTION LISTENERS
           ========================================================= */
        acknowledgeButton.addActionListener(e -> {
            // Trigger the callback to check the box
            if (onAcknowledge != null) {
                onAcknowledge.run();
            }
            dispose();
        });

        setVisible(true);
    }

    /* =========================================================
       CLINICAL TERMS TEXT GENERATOR
       ========================================================= */
    private String getClinicalTermsText() {
        return "CLINICAL DATA MANIFEST & PRIVACY AGREEMENT\n"
             + "Last Updated: May 2026\n\n"
             + "Please thoroughly review this clinical framework agreement prior to executing or managing operations within your dashboard profile.\n\n"
             + "1. DATA COLLECTION & PROTECTED HEALTH INFORMATION (PHI)\n"
             + "By utilizing this portal, you explicitly authorize the intake, digital mapping, and internal processing of your Personally Identifiable Information (PII) and Protected Health Information (PHI). This encompasses clinical descriptors, age, biological gender vectors, contact credentials, and descriptions of primary ailments or physiological concerns.\n\n"
             + "2. PROTOCOLS FOR DIGITAL PRIVACY & DATA ENCRYPTION\n"
             + "All clinical information processed through this framework is bound by strict technical, physical, and administrative safeguards aligned with industrial and digital health data security parameters. Records are fully encrypted both during data-in-transit states and data-at-rest configurations inside our relational database clusters to secure records against external extraction or unauthorized viewing.\n\n"
             + "3. CLINICAL TRIAGING & INTER-DEPARTMENTAL DATA TRANSFER\n"
             + "You understand and agree that your registered medical requests follow a structural clinical pipeline. The information provided is initially evaluated by medical intake/nursing practitioners for secondary data aggregation (Nurse Notes) before being programmatically forwarded to assigned clinical specialists (Doctors) for complete assessment, treatment architecture, and pharmaceutical prescription generation.\n\n"
             + "4. MEDICAL INTEGRITY RESPONSIBILITIES\n"
             + "The user holds absolute accountability for rendering accurate, clean, and factual systemic medical details. Providing fraudulent statistics, misrepresenting physical health indicators, or presenting deceptive identification elements will trigger systematic voiding of scheduled bookings, database profile suspension, or complete denial of medical software services.\n\n"
             + "5. ARCHIVAL RETENTION & PATIENT RIGHTS\n"
             + "All electronic health profiles are persistently maintained under institutional medical record retention timelines. Users reserve the explicit right to adjust authentication details and query live appointment states directly via their customized interface instances.";
    }

    /* =========================================================
       UI COMPONENT FACTORY METHODS
       ========================================================= */
    private JButton createStyledButton(String text, Color bgColor, Color hoverColor) {
        JButton button = new JButton(text);
        button.setFocusable(false);
        button.setFont(new Font("Segoe UI", Font.BOLD, 14));
        button.setForeground(Color.WHITE);
        button.setBackground(bgColor);
        button.setCursor(new Cursor(Cursor.HAND_CURSOR));
        button.setFocusPainted(false);
        button.setPreferredSize(new Dimension(150, 40));
        button.setBorder(BorderFactory.createEmptyBorder()); 

        button.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                button.setBackground(hoverColor);
            }
            @Override
            public void mouseExited(MouseEvent e) {
                button.setBackground(bgColor);
            }
        });
        return button;
    }
}