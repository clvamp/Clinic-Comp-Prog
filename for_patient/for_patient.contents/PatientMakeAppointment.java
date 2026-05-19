package for_patient.for_patient.contents;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import for_patient.PatientAppointmentLogic;
import for_patient.PatientLogic;
import GUI.NoticeGUI;
import GUI.ConfirmGUI;

public class PatientMakeAppointment extends JPanel {
    private static final long serialVersionUID = 1L;

    private PatientLogic logic;
    private String currentUsername;

    // Form Input Fields
    private JTextField patientNameField;
    private JTextField patientAgeField;
    private JComboBox<String> patientGenderBox;
    private JTextField patientEmailField;
    private JTextArea patientConcernArea;

    // Layout/Theme Matching Palette
    private final Color MAIN_BG = new Color(235, 245, 250);       // Soft medical blue
    private final Color HEADER_BG = new Color(245, 247, 250);     // Light grayish-blue header
    private final Color CARD_BG = Color.WHITE;
    private final Color TEXT_DARK = new Color(34, 40, 49);
    private final Color TEXT_MUTED = new Color(90, 90, 90);
    private final Color BUTTON_DEFAULT = new Color(22, 41, 56);   // Rich deep teal
    private final Color BUTTON_HOVER = new Color(34, 65, 87);     // Active accent blue

    // Notice Colors
    private final Color COLOR_ERROR = new Color(231, 76, 60);     // Red
    private final Color COLOR_SUCCESS = new Color(46, 204, 113);  // Green
    private final Color COLOR_INFO = new Color(52, 152, 219);     // Blue

    public PatientMakeAppointment(PatientLogic logic, String currentUsername) {
        this.logic = logic;
        this.currentUsername = currentUsername;

        setLayout(new BorderLayout());
        setBackground(MAIN_BG);

        initializeUI();
    }

    private void initializeUI() {
        /* =========================================================
           HEADER SECTION
           ========================================================= */
        JPanel headerPanel = new JPanel(new BorderLayout());
        headerPanel.setBackground(HEADER_BG);
        headerPanel.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createMatteBorder(0, 0, 1, 0, new Color(220, 225, 230)),
                new EmptyBorder(30, 40, 30, 40)
        ));

        JLabel welcomeLabel = new JLabel("Book an Appointment");
        welcomeLabel.setFont(new Font("Segoe UI", Font.BOLD, 32));
        welcomeLabel.setForeground(TEXT_DARK);

        JLabel subtitle = new JLabel("Please fill out your clinical data and description of concerns accurately.");
        subtitle.setFont(new Font("Segoe UI", Font.PLAIN, 16));
        subtitle.setForeground(TEXT_MUTED);

        JPanel textContainer = new JPanel();
        textContainer.setLayout(new BoxLayout(textContainer, BoxLayout.Y_AXIS));
        textContainer.setBackground(headerPanel.getBackground());

        textContainer.add(welcomeLabel);
        textContainer.add(Box.createRigidArea(new Dimension(0, 10)));
        textContainer.add(subtitle);

        headerPanel.add(textContainer, BorderLayout.WEST);
        add(headerPanel, BorderLayout.NORTH);

        /* =========================================================
           RESPONSIVE FORM CONTENT WRAPPER
           ========================================================= */
        JPanel contentScrollBody = new JPanel(new GridBagLayout());
        contentScrollBody.setBackground(MAIN_BG);
        contentScrollBody.setBorder(new EmptyBorder(40, 40, 40, 40));

        // Central Form Card Container
        JPanel formCard = new JPanel(new GridBagLayout());
        formCard.setBackground(CARD_BG);
        formCard.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(210, 220, 230), 1, true),
                new EmptyBorder(35, 45, 35, 45)
        ));

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.weightx = 1.0;

        Font labelFont = new Font("Segoe UI", Font.BOLD, 14);
        Font fieldFont = new Font("Segoe UI", Font.PLAIN, 14);

        // --- Row 0: Patient Name ---
        gbc.gridx = 0; gbc.gridy = 0;
        formCard.add(createStyledLabel("Patient Name:", labelFont), gbc);
        
        patientNameField = new JTextField();
        patientNameField.setFont(fieldFont);
        patientNameField.setPreferredSize(new Dimension(0, 38));
        gbc.gridy = 1;
        formCard.add(patientNameField, gbc);

        // --- Row 1: Patient Age ---
        gbc.gridy = 2;
        formCard.add(createStyledLabel("Patient Age:", labelFont), gbc);
        
        patientAgeField = new JTextField();
        patientAgeField.setFont(fieldFont);
        patientAgeField.setPreferredSize(new Dimension(0, 38));
        gbc.gridy = 3;
        formCard.add(patientAgeField, gbc);

        // --- Row 2: Patient Gender Dropdown ---
        gbc.gridy = 4;
        formCard.add(createStyledLabel("Patient Gender:", labelFont), gbc);
        
        String[] genders = {"Select Gender", "Male", "Female", "Other"};
        patientGenderBox = new JComboBox<>(genders);
        patientGenderBox.setFont(fieldFont);
        patientGenderBox.setBackground(Color.WHITE);
        patientGenderBox.setPreferredSize(new Dimension(0, 38));
        gbc.gridy = 5;
        formCard.add(patientGenderBox, gbc);

        // --- Row 3: Patient Email ---
        gbc.gridy = 6;
        formCard.add(createStyledLabel("Patient Email Address:", labelFont), gbc);
        
        patientEmailField = new JTextField();
        patientEmailField.setFont(fieldFont);
        patientEmailField.setPreferredSize(new Dimension(0, 38));
        gbc.gridy = 7;
        formCard.add(patientEmailField, gbc);

        // --- Row 4: Patient Concern Text Area ---
        gbc.gridy = 8;
        formCard.add(createStyledLabel("Patient Concern Description:", labelFont), gbc);
        
        patientConcernArea = new JTextArea();
        patientConcernArea.setFont(fieldFont);
        patientConcernArea.setLineWrap(true);
        patientConcernArea.setWrapStyleWord(true);
        
        JScrollPane textScroll = new JScrollPane(patientConcernArea);
        textScroll.setPreferredSize(new Dimension(0, 120));
        gbc.gridy = 9;
        formCard.add(textScroll, gbc);

        // --- Row 5: Action Button Panel ---
        JPanel actionButtonPanel = new JPanel(new GridLayout(1, 2, 20, 0));
        actionButtonPanel.setOpaque(false);

        JButton submitBtn = createFormButton("Send Request", BUTTON_DEFAULT, BUTTON_HOVER);
        JButton resetBtn = createFormButton("Reset", new Color(110, 120, 130), new Color(130, 140, 150));

        actionButtonPanel.add(submitBtn);
        actionButtonPanel.add(resetBtn);

        gbc.gridy = 10;
        gbc.insets = new Insets(25, 10, 5, 10);
        formCard.add(actionButtonPanel, gbc);

        /* =========================================================
           RESPONSIVE STRUCTURAL WRAPPING
           ========================================================= */
        GridBagConstraints centerConstraints = new GridBagConstraints();
        centerConstraints.gridx = 0;
        centerConstraints.gridy = 0;
        centerConstraints.weightx = 1.0;
        centerConstraints.weighty = 1.0;
        centerConstraints.fill = GridBagConstraints.HORIZONTAL;
        // Restricts component maximum expansion box width for high-resolution screens
        centerConstraints.ipadx = 250; 

        contentScrollBody.add(formCard, centerConstraints);

        JScrollPane mainFormScroll = new JScrollPane(contentScrollBody);
        mainFormScroll.setBorder(null);
        mainFormScroll.getVerticalScrollBar().setUnitIncrement(16);
        add(mainFormScroll, BorderLayout.CENTER);

        /* =========================================================
           BUTTON TRIGGERS
           ========================================================= */
        resetBtn.addActionListener(e -> {
            Window parentWindow = SwingUtilities.getWindowAncestor(this);
            ConfirmGUI confirm = new ConfirmGUI(parentWindow, "Clear Form", "Are you sure you want to clear all fields?", COLOR_ERROR);
            
            if (confirm.isConfirmed()) {
                clearFormFields();
            }
        });

        submitBtn.addActionListener(e -> {
            Window parentWindow = SwingUtilities.getWindowAncestor(this);

            String name = patientNameField.getText().trim();
            String ageText = patientAgeField.getText().trim();
            String gender = (String) patientGenderBox.getSelectedItem();
            String email = patientEmailField.getText().trim();
            String concern = patientConcernArea.getText().trim();

            if (name.isEmpty() || ageText.isEmpty() || gender.equals("Select Gender") || email.isEmpty() || concern.isEmpty()) {
                new NoticeGUI(parentWindow, "Validation Error", "Please fulfill all required fields.", COLOR_ERROR);
            } else {
                
                // Parse Age safely to ensure the user didn't type text
                int age;
                try {
                    age = Integer.parseInt(ageText);
                } catch (NumberFormatException ex) {
                    new NoticeGUI(parentWindow, "Invalid Input", "Patient Age must be a valid number.", COLOR_ERROR);
                    return; // Stop execution if age is not a number
                }

                ConfirmGUI confirm = new ConfirmGUI(parentWindow, "Submit Request", "Are you sure you want to send this appointment request?", COLOR_INFO);
                
                if (confirm.isConfirmed()) {
                    // Instantiate the logic class
                    PatientAppointmentLogic appLogic = new PatientAppointmentLogic();
                    
                    // Execute the SQL insertion
                    boolean isSuccess = appLogic.createAppointment(name, age, gender, email, concern);
                    
                    if (isSuccess) {
                        new NoticeGUI(parentWindow, "Success", "Appointment request sent successfully!", COLOR_SUCCESS);
                        clearFormFields();
                    } else {
                        new NoticeGUI(parentWindow, "Database Error", "Failed to submit appointment. Please check your connection.", COLOR_ERROR);
                    }
                }
            }
        });
    }

    private JLabel createStyledLabel(String labelText, Font font) {
        JLabel label = new JLabel(labelText);
        label.setFont(font);
        label.setForeground(TEXT_DARK);
        return label;
    }

    private JButton createFormButton(String text, Color baseColor, Color hoverColor) {
        JButton button = new JButton(text);
        button.setFont(new Font("Segoe UI", Font.BOLD, 15));
        button.setForeground(Color.WHITE);
        button.setBackground(baseColor);
        button.setPreferredSize(new Dimension(0, 45));
        button.setFocusable(false);
        button.setCursor(new Cursor(Cursor.HAND_CURSOR));
        button.setBorderPainted(false);

        button.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                button.setBackground(hoverColor);
            }
            @Override
            public void mouseExited(MouseEvent e) {
                button.setBackground(baseColor);
            }
        });

        return button;
    }

    private void clearFormFields() {
        patientNameField.setText("");
        patientAgeField.setText("");
        patientGenderBox.setSelectedIndex(0);
        patientEmailField.setText("");
        patientConcernArea.setText("");
    }
}