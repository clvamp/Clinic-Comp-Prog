package GUI.contents;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import system.AppointmentDashboard;
import system.DatabaseConnection;

import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class NursePopUp extends JDialog {

    private int patientId;
    private NurseAppointments parentPanel;

    // Form fields to occupy dynamically
    private JTextField nameField;
    private JTextField ageField;
    private JTextField genderField;
    private JTextField emailField;
    private JTextField dateField;
    private JTextField timeField;
    private JTextArea concernArea;
    private JTextArea notesArea;

    // Clinic Theme Colors
    private final Color primaryTeal = new Color(44, 62, 80);
    private final Color lightGray = new Color(248, 249, 250);
    private final Color borderGray = new Color(220, 224, 226);

    public NursePopUp(JFrame parentFrame, int patientId, NurseAppointments parentPanel) {
        super(parentFrame, "", true);
        this.patientId = patientId;
        this.parentPanel = parentPanel;

        /* =========================================================
           MAIN DIALOG SETTINGS
           ========================================================= */
        setSize(500, 750);
        setLocationRelativeTo(parentFrame);
        setResizable(false);
        setLayout(new BorderLayout());
        getContentPane().setBackground(Color.WHITE);
        setUndecorated(false); // Keep OS window controls

        /* =========================================================
           HEADER PANEL
           ========================================================= */
        JPanel headerPanel = new JPanel(new BorderLayout());
        headerPanel.setBackground(primaryTeal);
        headerPanel.setBorder(new EmptyBorder(20, 30, 20, 30));

        JLabel headerLabel = new JLabel("APPOINTMENT DETAILS");
        headerLabel.setFont(new Font("Segoe UI", Font.BOLD, 22));
        headerLabel.setForeground(Color.WHITE);
        headerLabel.setIconTextGap(10);
        
        headerPanel.add(headerLabel, BorderLayout.WEST);
        add(headerPanel, BorderLayout.NORTH);

        /* =========================================================
           MAIN FORM PANEL
           ========================================================= */
        JPanel mainPanel = new JPanel(new GridBagLayout());
        mainPanel.setBackground(Color.WHITE);
        mainPanel.setBorder(new EmptyBorder(10, 0, 10, 0));

        // Use a scroll pane just in case screen size is small
        JScrollPane scrollPane = new JScrollPane(mainPanel);
        scrollPane.setBorder(null);
        scrollPane.getVerticalScrollBar().setUnitIncrement(16);
        add(scrollPane, BorderLayout.CENTER);

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.gridx = 0;
        gbc.weightx = 1;

        /* =========================================================
           FORM FIELDS
           ========================================================= */
        // Name
        gbc.gridy = 0; gbc.insets = new Insets(10, 30, 3, 30);
        mainPanel.add(createLabel("Patient Name"), gbc);
        nameField = createTextField();
        nameField.setEditable(false);
        gbc.gridy = 1; gbc.insets = new Insets(0, 30, 15, 30);
        mainPanel.add(nameField, gbc);

        // Age
        gbc.gridy = 2; gbc.insets = new Insets(0, 30, 3, 30);
        mainPanel.add(createLabel("Patient Age"), gbc);
        ageField = createTextField();
        ageField.setEditable(false);
        gbc.gridy = 3; gbc.insets = new Insets(0, 30, 15, 30);
        mainPanel.add(ageField, gbc);

        // Gender
        gbc.gridy = 4; gbc.insets = new Insets(0, 30, 3, 30);
        mainPanel.add(createLabel("Patient Gender"), gbc);
        genderField = createTextField();
        genderField.setEditable(false);
        gbc.gridy = 5; gbc.insets = new Insets(0, 30, 15, 30);
        mainPanel.add(genderField, gbc);

        // Email
        gbc.gridy = 6; gbc.insets = new Insets(0, 30, 3, 30);
        mainPanel.add(createLabel("Patient Email"), gbc);
        emailField = createTextField();
        emailField.setEditable(false);
        gbc.gridy = 7; gbc.insets = new Insets(0, 30, 15, 30);
        mainPanel.add(emailField, gbc);

        // Appointment Date
        gbc.gridy = 8; gbc.insets = new Insets(0, 30, 3, 30);
        mainPanel.add(createLabel("Appointment Date"), gbc);
        dateField = createTextField();
        dateField.setEditable(false);
        gbc.gridy = 9; gbc.insets = new Insets(0, 30, 15, 30);
        mainPanel.add(dateField, gbc);

        // Appointment Time
        gbc.gridy = 10; gbc.insets = new Insets(0, 30, 3, 30);
        mainPanel.add(createLabel("Appointment Time"), gbc);
        timeField = createTextField();
        timeField.setEditable(false);
        gbc.gridy = 11; gbc.insets = new Insets(0, 30, 15, 30);
        mainPanel.add(timeField, gbc);

        // Patient Concern
        gbc.gridy = 12; gbc.insets = new Insets(0, 30, 3, 30);
        mainPanel.add(createLabel("Patient Concern"), gbc);
        concernArea = createTextArea(true);
        JScrollPane concernScroll = new JScrollPane(concernArea);
        concernScroll.setBorder(BorderFactory.createLineBorder(borderGray));
        concernScroll.setPreferredSize(new Dimension(0, 80));
        gbc.gridy = 13; gbc.insets = new Insets(0, 30, 15, 30);
        mainPanel.add(concernScroll, gbc);

        // Nurse Notes
        gbc.gridy = 14; gbc.insets = new Insets(0, 30, 3, 30);
        mainPanel.add(createLabel("Nurse Notes"), gbc);
        notesArea = createTextArea(false); // Editable
        JScrollPane notesScroll = new JScrollPane(notesArea);
        notesScroll.setBorder(BorderFactory.createLineBorder(borderGray));
        notesScroll.setPreferredSize(new Dimension(0, 80));
        gbc.gridy = 15; gbc.insets = new Insets(0, 30, 25, 30);
        mainPanel.add(notesScroll, gbc);

        /* =========================================================
           BOTTOM BUTTON PANEL
           ========================================================= */
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT, 15, 15));
        buttonPanel.setBackground(lightGray);
        buttonPanel.setBorder(BorderFactory.createMatteBorder(1, 0, 0, 0, borderGray));

        // Emerald Green Confirm, Neutral Gray Close
        JButton closeButton = createStyledButton("Close", new Color(149, 165, 166), new Color(127, 140, 141));
        JButton saveButton = createStyledButton("Confirm Appointment", new Color(39, 174, 96), new Color(46, 204, 113));

        buttonPanel.add(closeButton);
        buttonPanel.add(saveButton);

        add(buttonPanel, BorderLayout.SOUTH);

        /* =========================================================
           LOAD LIVE DATA & ACTION LISTENERS
           ========================================================= */
        loadPatientData();

        saveButton.addActionListener(e -> {
            String nurseNotes = notesArea.getText().trim();
            AppointmentDashboard appointmentDashboard = new AppointmentDashboard();
            
            // Execute confirm operations
            appointmentDashboard.confirmAppointment(this, this.patientId, nurseNotes);
            
            // Sync current modifications back to parent view immediately
            if (this.parentPanel != null) {
                this.parentPanel.refreshTableData();
            }
            
            dispose();
        });

        closeButton.addActionListener(e -> dispose());

        setVisible(true);
    }

    /* =========================================================
       FETCH DATA FROM DATABASE
       ========================================================= */
    private void loadPatientData() {
        String query = "SELECT * FROM nurse_appointments WHERE patient_id = ?";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(query)) {
            
            pstmt.setInt(1, patientId);
            
            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    nameField.setText(rs.getString("patient_name"));
                    ageField.setText(String.valueOf(rs.getInt("patient_age")));
                    genderField.setText(rs.getString("patient_gender"));
                    emailField.setText(rs.getString("patient_email"));
                    concernArea.setText(rs.getString("patient_concern"));
                    notesArea.setText(rs.getString("nurse_notes"));

                    java.sql.Timestamp ts = rs.getTimestamp("appointment_created_at");
                    if (ts != null) {
                        String[] parts = ts.toString().split(" ");
                        dateField.setText(parts[0]);
                        timeField.setText(parts[1].substring(0, 5));
                    } else {
                        dateField.setText("N/A");
                        timeField.setText("N/A");
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /* =========================================================
       UI COMPONENT FACTORY METHODS
       ========================================================= */
    private JLabel createLabel(String text) {
        JLabel label = new JLabel(text);
        label.setFont(new Font("Segoe UI", Font.BOLD, 13));
        label.setForeground(primaryTeal);
        return label;
    }

    private JTextField createTextField() {
        JTextField field = new JTextField();
        field.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        field.setPreferredSize(new Dimension(0, 38)); // Nice tall input boxes
        field.setBackground(lightGray);
        field.setForeground(new Color(40, 40, 40));
        field.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(borderGray, 1),
                BorderFactory.createEmptyBorder(0, 10, 0, 10) // Inner padding
        ));
        return field;
    }

    private JTextArea createTextArea(boolean isReadOnly) {
        JTextArea area = new JTextArea();
        area.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        area.setLineWrap(true);
        area.setWrapStyleWord(true);
        area.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10)); // Inner padding
        
        if (isReadOnly) {
            area.setEditable(false);
            area.setBackground(lightGray);
            area.setForeground(new Color(40, 40, 40));
        } else {
            area.setBackground(Color.WHITE);
        }
        return area;
    }

    private JButton createStyledButton(String text, Color bgColor, Color hoverColor) {
        JButton button = new JButton(text);
        button.setFocusable(false);
        button.setFont(new Font("Segoe UI", Font.BOLD, 14));
        button.setForeground(Color.WHITE);
        button.setBackground(bgColor);
        button.setCursor(new Cursor(Cursor.HAND_CURSOR));
        button.setFocusPainted(false);
        button.setPreferredSize(new Dimension(180, 40));
        button.setBorder(BorderFactory.createEmptyBorder()); // Flat modern button

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