package system;

import GUI.ConfirmGUI;
import GUI.NoticeGUI;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class AppointmentDashboard {

    /* =========================================================
       NURSE: FETCH LIVE APPOINTMENTS 
       ========================================================= */
    public DefaultTableModel getNurseAppointmentsModel(String[] columns) {
        DefaultTableModel model = new DefaultTableModel(columns, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };

        String query = "SELECT patient_id, patient_name, patient_age, patient_gender, "
                     + "appointment_created_at, appointment_status "
                     + "FROM nurse_appointments ORDER BY appointment_created_at DESC";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(query);
             ResultSet rs = pstmt.executeQuery()) {

            while (rs.next()) {
                Object[] row = new Object[columns.length];
                row[0] = rs.getInt("patient_id");
                row[1] = rs.getString("patient_name");
                row[2] = rs.getInt("patient_age");
                row[3] = rs.getString("patient_gender");
                
                java.sql.Timestamp ts = rs.getTimestamp("appointment_created_at");
                row[4] = (ts != null) ? ts.toString().split(" ")[0] : "N/A"; 
                row[5] = (ts != null) ? ts.toString().split(" ")[1].substring(0, 5) : "N/A"; 
                
                row[6] = rs.getString("appointment_status");

                model.addRow(row);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return model;
    }

    /* =========================================================
       NURSE: CONFIRM APPOINTMENT & SEND TO DOCTOR
       ========================================================= */
    public void confirmAppointment(Window parent, int patientId, String nurseNotes) {
        if (nurseNotes == null || nurseNotes.trim().isEmpty()) {
            new NoticeGUI(parent, "Missing Notes", "Please input nurse notes before confirming.", new Color(220, 53, 69));
            return;
        }

        ConfirmGUI confirmGUI = new ConfirmGUI(parent, "Confirm Appointment", "Are you sure you want to confirm this appointment?", new Color(40, 167, 69));
        if (!confirmGUI.isConfirmed()) return;

        Connection conn = null;
        PreparedStatement selectStmt = null, updateStmt = null, insertStmt = null;
        ResultSet rs = null;

        try {
            conn = DatabaseConnection.getConnection();

            String selectQuery = "SELECT * FROM nurse_appointments WHERE patient_id = ?";
            selectStmt = conn.prepareStatement(selectQuery);
            selectStmt.setInt(1, patientId);
            rs = selectStmt.executeQuery();

            if (rs.next()) {
                // Update Nurse Table
                String updateQuery = "UPDATE nurse_appointments SET nurse_notes = ?, appointment_status = 'Confirmed' WHERE patient_id = ?";
                updateStmt = conn.prepareStatement(updateQuery);
                updateStmt.setString(1, nurseNotes);
                updateStmt.setInt(2, patientId);
                updateStmt.executeUpdate();

                // Forward to Doctor Table
                String insertQuery = "INSERT INTO doctor_appointments (" +
                        "patient_id, patient_name, patient_age, patient_gender, patient_email, " +
                        "appointment_created_at, appointment_date, appointment_time, patient_concern, " +
                        "nurse_notes, doctor_notes, doctor_prescription, appointment_status" +
                        ") VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

                insertStmt = conn.prepareStatement(insertQuery);
                insertStmt.setInt(1, patientId);
                insertStmt.setString(2, rs.getString("patient_name"));
                insertStmt.setInt(3, rs.getInt("patient_age"));
                insertStmt.setString(4, rs.getString("patient_gender"));
                insertStmt.setString(5, rs.getString("patient_email"));
                insertStmt.setTimestamp(6, rs.getTimestamp("appointment_created_at"));
                insertStmt.setDate(7, rs.getDate("appointment_date"));
                insertStmt.setTime(8, rs.getTime("appointment_time"));
                insertStmt.setString(9, rs.getString("patient_concern"));
                insertStmt.setString(10, nurseNotes);
                insertStmt.setString(11, ""); // Empty doctor notes initially
                insertStmt.setString(12, ""); // Empty prescription initially
                insertStmt.setString(13, "Ongoing");

                insertStmt.executeUpdate();

                new NoticeGUI(parent, "Success", "Appointment confirmed and forwarded to the doctor.", new Color(40, 167, 69));
            } else {
                new NoticeGUI(parent, "Error", "Appointment record not found.", new Color(220, 53, 69));
            }

        } catch (Exception e) {
            e.printStackTrace();
            new NoticeGUI(parent, "Database Error", "An error occurred while confirming.", new Color(220, 53, 69));
        } finally {
            try {
                if (rs != null) rs.close();
                if (selectStmt != null) selectStmt.close();
                if (updateStmt != null) updateStmt.close();
                if (insertStmt != null) insertStmt.close();
                if (conn != null) conn.close();
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }
    }

    /* =========================================================
       DOCTOR: FETCH LIVE APPOINTMENTS 
       ========================================================= */
    public DefaultTableModel getDoctorAppointmentsModel(String[] columns) {
        DefaultTableModel model = new DefaultTableModel(columns, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };

        String query = "SELECT patient_id, patient_name, patient_age, patient_gender, "
                     + "appointment_created_at, appointment_status "
                     + "FROM doctor_appointments ORDER BY appointment_created_at DESC";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(query);
             ResultSet rs = pstmt.executeQuery()) {

            while (rs.next()) {
                Object[] row = new Object[columns.length];
                row[0] = rs.getInt("patient_id");
                row[1] = rs.getString("patient_name");
                row[2] = rs.getInt("patient_age");
                row[3] = rs.getString("patient_gender");
                
                java.sql.Timestamp ts = rs.getTimestamp("appointment_created_at");
                row[4] = (ts != null) ? ts.toString().split(" ")[0] : "N/A"; 
                row[5] = (ts != null) ? ts.toString().split(" ")[1].substring(0, 5) : "N/A"; 
                
                row[6] = rs.getString("appointment_status");

                model.addRow(row);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return model;
    }

    /* =========================================================
       DOCTOR: COMPLETE ASSESSMENT & SAVE NOTES
       ========================================================= */
    public void completeDoctorAssessment(Window parent, int patientId, String doctorNotes, String prescription) {
        if (doctorNotes == null || doctorNotes.trim().isEmpty()) {
            new NoticeGUI(parent, "Missing Information", "Please input treatment notes before saving.", new Color(220, 53, 69));
            return;
        }

        ConfirmGUI confirmGUI = new ConfirmGUI(parent, "Complete Assessment", "Save notes and mark this appointment as completed?", new Color(41, 128, 185));
        if (!confirmGUI.isConfirmed()) return;

        String updateQuery = "UPDATE doctor_appointments SET doctor_notes = ?, doctor_prescription = ?, appointment_status = 'Completed' WHERE patient_id = ?";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement updateStmt = conn.prepareStatement(updateQuery)) {

            updateStmt.setString(1, doctorNotes);
            updateStmt.setString(2, prescription);
            updateStmt.setInt(3, patientId);
            
            int rowsAffected = updateStmt.executeUpdate();

            if (rowsAffected > 0) {
                new NoticeGUI(parent, "Success", "Patient assessment successfully recorded.", new Color(40, 167, 69));
            } else {
                new NoticeGUI(parent, "Error", "Failed to update patient record.", new Color(220, 53, 69));
            }

        } catch (Exception e) {
            e.printStackTrace();
            new NoticeGUI(parent, "Database Error", "An error occurred while saving assessment.", new Color(220, 53, 69));
        }
    }
}