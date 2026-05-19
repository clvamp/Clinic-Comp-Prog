package GUI.contents;

import system.AppointmentDashboard;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class DoctorAppointments extends JPanel {

    private String authority;

    private JTable appointmentsTable;
    private DefaultTableModel tableModel;
    private String[] columns;

    private AppointmentDashboard appointmentDashboard;

    // Save renderers as class variables so they aren't lost on refresh
    private DefaultTableCellRenderer customRenderer;
    private DefaultTableCellRenderer statusRenderer;

    public DoctorAppointments(String authority) {
        this.authority = authority;
        this.appointmentDashboard = new AppointmentDashboard();

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
        headerPanel.setBackground(new Color(248, 249, 250)); 
        headerPanel.setBorder(new EmptyBorder(35, 40, 25, 40));

        JLabel titleLabel = new JLabel("Doctor Appointment List");
        titleLabel.setFont(new Font("Segoe UI", Font.BOLD, 30));
        titleLabel.setForeground(new Color(44, 62, 80)); 

        JLabel subtitleLabel = new JLabel("Manage and review all incoming patient appointments.");
        subtitleLabel.setFont(new Font("Segoe UI", Font.PLAIN, 15));
        subtitleLabel.setForeground(new Color(127, 140, 141)); 

        JPanel textContainer = new JPanel();
        textContainer.setLayout(new BoxLayout(textContainer, BoxLayout.Y_AXIS));
        textContainer.setBackground(headerPanel.getBackground());

        textContainer.add(titleLabel);
        textContainer.add(Box.createRigidArea(new Dimension(0, 6)));
        textContainer.add(subtitleLabel);

        headerPanel.add(textContainer, BorderLayout.WEST);
        add(headerPanel, BorderLayout.NORTH);

        /* =========================
           TABLE SECTION
           ========================= */
        JPanel tablePanel = new JPanel(new BorderLayout());
        tablePanel.setBackground(Color.WHITE);
        tablePanel.setBorder(new EmptyBorder(15, 40, 40, 40));

        columns = new String[]{
                "Patient ID",
                "Patient Name",
                "Age",
                "Gender",
                "Date",
                "Time",
                "Status"
        };

        // Load model directly populated with live data from MySQL
        // Note: Make sure getDoctorAppointmentsModel is defined in AppointmentDashboard
        tableModel = appointmentDashboard.getDoctorAppointmentsModel(columns);

        appointmentsTable = new JTable(tableModel);
        appointmentsTable.setRowHeight(40); 
        appointmentsTable.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        appointmentsTable.setShowGrid(true);
        appointmentsTable.setGridColor(new Color(236, 240, 241)); 
        
        // Force single row selection and explicit selection colors
        appointmentsTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        appointmentsTable.setSelectionBackground(new Color(224, 242, 241)); 
        appointmentsTable.setSelectionForeground(new Color(44, 62, 80));

        setupTableRenderersAndHeaders();

        /* =========================================================
           TABLE CLICK EVENT (OPENS DOCTOR POPUP)
           ========================================================= */
        appointmentsTable.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                // REQUIRES A DOUBLE CLICK TO OPEN
                if (e.getClickCount() == 2) {
                    int selectedRow = appointmentsTable.getSelectedRow();
                    if (selectedRow != -1) {
                        try {
                            int patientId = Integer.parseInt(
                                    tableModel.getValueAt(selectedRow, 0).toString()
                            );

                            // SAFELY locate the parent window to prevent silent ClassCastExceptions
                            Window windowAncestor = SwingUtilities.getWindowAncestor(DoctorAppointments.this);
                            JFrame parentFrame = null;
                            if (windowAncestor instanceof JFrame) {
                                parentFrame = (JFrame) windowAncestor;
                            }

                            // Opens the Doctor-specific popup window
                            new DoctorPopUp(parentFrame, patientId, DoctorAppointments.this);
                            
                        } catch (Exception ex) {
                            ex.printStackTrace(); 
                        }
                    }
                }
            }
        });

        JScrollPane scrollPane = new JScrollPane(appointmentsTable);
        scrollPane.setBorder(BorderFactory.createLineBorder(new Color(228, 233, 237), 1));
        scrollPane.getViewport().setBackground(Color.WHITE);

        tablePanel.add(scrollPane, BorderLayout.CENTER);
        add(tablePanel, BorderLayout.CENTER);
    }

    /* =========================================================
       HELPER: SETUP HEADERS AND RENDERERS
       ========================================================= */
    private void setupTableRenderersAndHeaders() {
        JTableHeader tableHeader = appointmentsTable.getTableHeader();
        tableHeader.setReorderingAllowed(false); // STOPS DRAGGING COLUMNS
        tableHeader.setResizingAllowed(true);
        tableHeader.setFont(new Font("Segoe UI", Font.BOLD, 14));
        tableHeader.setBackground(new Color(44, 62, 80)); 
        tableHeader.setForeground(Color.WHITE);
        tableHeader.setPreferredSize(new Dimension(tableHeader.getPreferredSize().width, 45));

        if (customRenderer == null) {
            customRenderer = new DefaultTableCellRenderer() {
                @Override
                public Component getTableCellRendererComponent(JTable table, Object value, 
                                                               boolean isSelected, boolean hasFocus, 
                                                               int row, int column) {
                    Component c = super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
                    
                    if (column == 0 || column == 2 || column == 3 || column == 4 || column == 5) {
                        setHorizontalAlignment(JLabel.CENTER);
                    } else {
                        setHorizontalAlignment(JLabel.LEFT);
                    }

                    if (isSelected) {
                        c.setBackground(table.getSelectionBackground());
                        c.setForeground(table.getSelectionForeground());
                    } else {
                        if (row % 2 == 0) {
                            c.setBackground(Color.WHITE);
                        } else {
                            c.setBackground(new Color(250, 252, 253)); 
                        }
                        c.setForeground(table.getForeground());
                    }
                    
                    setBorder(BorderFactory.createEmptyBorder(0, 12, 0, 12));
                    return c;
                }
            };
        }

        if (statusRenderer == null) {
            statusRenderer = new DefaultTableCellRenderer() {
                @Override
                public Component getTableCellRendererComponent(JTable table, Object value, 
                                                               boolean isSelected, boolean hasFocus, 
                                                               int row, int column) {
                    Component c = super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
                    setHorizontalAlignment(JLabel.CENTER);
                    setFont(new Font("Segoe UI", Font.BOLD, 13));

                    if (value != null) {
                        String status = value.toString().trim();
                        if (status.equalsIgnoreCase("Pending")) {
                            c.setForeground(new Color(211, 84, 0)); 
                        } else if (status.equalsIgnoreCase("Confirmed")) {
                            c.setForeground(new Color(39, 174, 96)); 
                        } else if (status.equalsIgnoreCase("Ongoing")) {
                            c.setForeground(new Color(41, 128, 185)); 
                        } else {
                            c.setForeground(new Color(127, 140, 141));
                        }
                    }
                    
                    if (isSelected) {
                        c.setBackground(table.getSelectionBackground());
                    } else {
                        if (row % 2 == 0) {
                            c.setBackground(Color.WHITE);
                        } else {
                            c.setBackground(new Color(250, 252, 253));
                        }
                    }
                    return c;
                }
            };
        }

        // Apply renderers
        for (int i = 0; i < appointmentsTable.getColumnCount(); i++) {
            if (appointmentsTable.getColumnName(i).equalsIgnoreCase("Status")) {
                appointmentsTable.getColumnModel().getColumn(i).setCellRenderer(statusRenderer);
            } else {
                appointmentsTable.getColumnModel().getColumn(i).setCellRenderer(customRenderer);
            }
        }
    }

    /* =========================================================
       REFRESH METHOD
       ========================================================= */
    public void refreshTableData() {
        if (appointmentDashboard != null && appointmentsTable != null) {
            // Load fresh data using the Doctor-specific method
            tableModel = appointmentDashboard.getDoctorAppointmentsModel(columns);
            appointmentsTable.setModel(tableModel);
            
            // Reapply styling and fixed headers so they don't break on update
            setupTableRenderersAndHeaders();
        }
    }
}