package for_patient;

import system.DatabaseConnection; 
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class PatientAppointmentLogic {

    /**
     * Validates and inserts a new patient appointment.
     */
    public boolean createAppointment(String name, int age, String gender, String email, String concern) {
        
        // --- 1. DATA LAYER VALIDATION ---
        if (age < 18) return false; 
        if (!email.contains("@")) return false;

        String query = "INSERT INTO nurse_appointments "
                     + "(patient_name, patient_age, patient_gender, patient_email, patient_concern, nurse_notes, appointment_status) "
                     + "VALUES (?, ?, ?, ?, ?, ?, ?)";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(query)) {

            pstmt.setString(1, name);
            pstmt.setInt(2, age);
            pstmt.setString(3, gender);
            pstmt.setString(4, email);
            pstmt.setString(5, concern);
            pstmt.setString(6, "");        
            pstmt.setString(7, "Pending"); 

            int rowsAffected = pstmt.executeUpdate();
            return rowsAffected > 0; 

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
}