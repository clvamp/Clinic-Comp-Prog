package for_patient;

import system.DatabaseConnection; 
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class PatientAppointmentLogic {

    /**
     * Inserts a new patient appointment into the nurse_appointments table.
     */
    public boolean createAppointment(String name, int age, String gender, String email, String concern) {
        
        // ADDED: nurse_notes to the query to satisfy the NOT NULL database constraint
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
            pstmt.setString(6, "");        // Fills the required 'nurse_notes' field with an empty string
            pstmt.setString(7, "Pending"); // Automatically sets status to Pending

            int rowsAffected = pstmt.executeUpdate();
            return rowsAffected > 0; // Returns true if insertion was successful

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
}