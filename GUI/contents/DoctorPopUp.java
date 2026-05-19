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

public class DoctorPopUp extends JDialog {

    private int patientId;
    private DoctorAppointments parentPanel;

    // Form fields
    private JTextField nameField;
    private JTextField ageField;
    private JTextField genderField;
    private JTextField emailField;
    private JTextField dateField;
    private JTextField timeField;
    private JTextArea concernArea;
    
    // Doctor Input fields
    private JComboBox<String> prescriptionDropdown;
    private JTextArea notesArea;

    // Clinic Theme Colors
    private final Color primaryTeal = new Color(44, 62, 80);
    private final Color lightGray = new Color(248, 249, 250);
    private final Color borderGray = new Color(220, 224, 226);

    public DoctorPopUp(JFrame parentFrame, int patientId, DoctorAppointments parentPanel) {
        super(parentFrame, "", true);
        this.patientId = patientId;
        this.parentPanel = parentPanel;

        /* =========================================================
           MAIN DIALOG SETTINGS (LANDSCAPE ORIENTATION)
           ========================================================= */
        setSize(900, 600);
        setLocationRelativeTo(parentFrame);
        setResizable(false);
        setLayout(new BorderLayout());
        getContentPane().setBackground(Color.WHITE);

        /* =========================================================
           HEADER PANEL
           ========================================================= */
        JPanel headerPanel = new JPanel(new BorderLayout());
        headerPanel.setBackground(primaryTeal);
        headerPanel.setBorder(new EmptyBorder(20, 35, 20, 35));

        JLabel headerLabel = new JLabel("DOCTOR MEDICAL ASSESSMENT");
        headerLabel.setFont(new Font("Segoe UI", Font.BOLD, 22));
        headerLabel.setForeground(Color.WHITE);
        
        headerPanel.add(headerLabel, BorderLayout.WEST);
        add(headerPanel, BorderLayout.NORTH);

        /* =========================================================
           SPLIT WIDESCREEN BODY CONTAINER
           ========================================================= */
        JPanel bodyContainer = new JPanel(new GridLayout(1, 2, 30, 0));
        bodyContainer.setBackground(Color.WHITE);
        bodyContainer.setBorder(new EmptyBorder(25, 35, 25, 35));

        /* ---------------------------------------------------------
           LEFT COLUMN: PATIENT INFORMATION CARD
           --------------------------------------------------------- */
        JPanel leftPanel = new JPanel(new GridBagLayout());
        leftPanel.setBackground(Color.WHITE);
        GridBagConstraints gbcL = new GridBagConstraints();
        gbcL.fill = GridBagConstraints.HORIZONTAL;
        gbcL.weightx = 1.0;

        // Patient Name (Full Width)
        gbcL.gridx = 0; gbcL.gridy = 0; gbcL.gridwidth = 2; gbcL.insets = new Insets(0, 0, 3, 0);
        leftPanel.add(createLabel("Patient Name"), gbcL);
        nameField = createTextField();
        nameField.setEditable(false);
        gbcL.gridy = 1; gbcL.insets = new Insets(0, 0, 12, 0);
        leftPanel.add(nameField, gbcL);

        // Patient Age (Left Half) & Gender (Right Half)
        gbcL.gridwidth = 1; gbcL.weightx = 0.5;
        gbcL.gridx = 0; gbcL.gridy = 2; gbcL.insets = new Insets(0, 0, 3, 10);
        leftPanel.add(createLabel("Age"), gbcL);
        gbcL.gridx = 1; gbcL.insets = new Insets(0, 10, 3, 0);
        leftPanel.add(createLabel("Gender"), gbcL);

        ageField = createTextField();
        ageField.setEditable(false);
        gbcL.gridx = 0; gbcL.gridy = 3; gbcL.insets = new Insets(0, 0, 12, 10);
        leftPanel.add(ageField, gbcL);

        genderField = createTextField();
        genderField.setEditable(false);
        gbcL.gridx = 1; gbcL.insets = new Insets(0, 10, 12, 0);
        leftPanel.add(genderField, gbcL);

        // Patient Email (Full Width)
        gbcL.gridx = 0; gbcL.gridy = 4; gbcL.gridwidth = 2; gbcL.weightx = 1.0; gbcL.insets = new Insets(0, 0, 3, 0);
        leftPanel.add(createLabel("Patient Email"), gbcL);
        emailField = createTextField();
        emailField.setEditable(false);
        gbcL.gridy = 5; gbcL.insets = new Insets(0, 0, 12, 0);
        leftPanel.add(emailField, gbcL);

        // Appointment Date (Left Half) & Time (Right Half)
        gbcL.gridwidth = 1; gbcL.weightx = 0.5;
        gbcL.gridx = 0; gbcL.gridy = 6; gbcL.insets = new Insets(0, 0, 3, 10);
        leftPanel.add(createLabel("Appointment Date"), gbcL);
        gbcL.gridx = 1; gbcL.insets = new Insets(0, 10, 3, 0);
        leftPanel.add(createLabel("Appointment Time"), gbcL);

        dateField = createTextField();
        dateField.setEditable(false);
        gbcL.gridx = 0; gbcL.gridy = 7; gbcL.insets = new Insets(0, 0, 12, 10);
        leftPanel.add(dateField, gbcL);

        timeField = createTextField();
        timeField.setEditable(false);
        gbxl: gbcL.gridx = 1; gbcL.insets = new Insets(0, 10, 12, 0);
        leftPanel.add(timeField, gbcL);

        // Patient Concern (Full Width)
        gbcL.gridx = 0; gbcL.gridy = 8; gbcL.gridwidth = 2; gbcL.weightx = 1.0; gbcL.insets = new Insets(0, 0, 3, 0);
        leftPanel.add(createLabel("Patient Reported Concern"), gbcL);
        concernArea = createTextArea(true);
        JScrollPane concernScroll = new JScrollPane(concernArea);
        concernScroll.setBorder(BorderFactory.createLineBorder(borderGray));
        concernScroll.setPreferredSize(new Dimension(0, 75));
        gbcL.gridy = 9; gbcL.insets = new Insets(0, 0, 0, 0);
        leftPanel.add(concernScroll, gbcL);

        /* ---------------------------------------------------------
           RIGHT COLUMN: DOCTOR WORKSPACE (PRESCRIPTION & NOTES)
           --------------------------------------------------------- */
        JPanel rightPanel = new JPanel(new GridBagLayout());
        rightPanel.setBackground(Color.WHITE);
        GridBagConstraints gbcR = new GridBagConstraints();
        gbcR.fill = GridBagConstraints.HORIZONTAL;
        gbcR.gridx = 0;
        gbcR.weightx = 1.0;

        // Doctor Prescription Dropdown
        gbcR.gridy = 0; gbcR.insets = new Insets(0, 0, 3, 0);
        rightPanel.add(createLabel("Doctor Prescription Selection"), gbcR);
        
        String[] prescriptionOptions = {"Medicine Example 1", "Medicine Example 2"};
        prescriptionDropdown = new JComboBox<>(prescriptionOptions);
        prescriptionDropdown.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        prescriptionDropdown.setBackground(Color.WHITE);
        prescriptionDropdown.setPreferredSize(new Dimension(0, 38));
        prescriptionDropdown.setBorder(BorderFactory.createLineBorder(borderGray, 1));
        
        gbcR.gridy = 1; gbcR.insets = new Insets(0, 0, 18, 0);
        rightPanel.add(prescriptionDropdown, gbcR);

        // Doctor Notes Text Area
        gbcR.gridy = 2; gbcR.insets = new Insets(0, 0, 3, 0);
        rightPanel.add(createLabel("Doctor Treatment Notes"), gbcR);
        notesArea = createTextArea(false); // Editable workspace
        JScrollPane notesScroll = new JScrollPane(notesArea);
        notesScroll.setBorder(BorderFactory.createLineBorder(borderGray));
        
        // Let it expand to dynamically fill the remaining height layout nicely
        gbcR.gridy = 3; 
        gbcR.weighty = 1.0; 
        gbcR.fill = GridBagConstraints.BOTH;
        gbcR.insets = new Insets(0, 0, 0, 0);
        rightPanel.add(notesScroll, gbcR);

        // Add both columns to the wide body view
        bodyContainer.add(leftPanel);
        bodyContainer.add(rightPanel);
        add(bodyContainer, BorderLayout.CENTER);

        /* =========================================================
           BOTTOM BUTTON PANEL
           ========================================================= */
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT, 20, 15));
        buttonPanel.setBackground(lightGray);
        buttonPanel.setBorder(BorderFactory.createMatteBorder(1, 0, 0, 0, borderGray));

        JButton closeButton = createStyledButton("Close", new Color(149, 165, 166), new Color(127, 140, 141));
        JButton saveButton = createStyledButton("Confirm & Save", new Color(39, 174, 96), new Color(46, 204, 113));

        buttonPanel.add(closeButton);
        buttonPanel.add(saveButton);
        add(buttonPanel, BorderLayout.SOUTH);

        /* =========================================================
           LOAD LIVE DATA & ACTION LISTENERS
           ========================================================= */
        loadPatientData();

        saveButton.addActionListener(e -> {
            String doctorNotes = notesArea.getText().trim();
            String selectedPrescription = (String) prescriptionDropdown.getSelectedItem();
            
            AppointmentDashboard dashboard = new AppointmentDashboard();
            dashboard.completeDoctorAssessment(this, this.patientId, doctorNotes, selectedPrescription);
            
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
        String query = "SELECT * FROM doctor_appointments WHERE patient_id = ?";

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
                    
                    // Prepopulate notes if medical record has entries
                    if (rs.getString("doctor_notes") != null) {
                        notesArea.setText(rs.getString("doctor_notes"));
                    }

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
        field.setPreferredSize(new Dimension(0, 38)); 
        field.setBackground(lightGray);
        field.setForeground(new Color(40, 40, 40));
        field.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(borderGray, 1),
                BorderFactory.createEmptyBorder(0, 10, 0, 10)
        ));
        return field;
    }

    private JTextArea createTextArea(boolean isReadOnly) {
        JTextArea area = new JTextArea();
        area.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        area.setLineWrap(true);
        area.setWrapStyleWord(true);
        area.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10)); 
        
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
        button.setPreferredSize(new Dimension(180, 42));
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