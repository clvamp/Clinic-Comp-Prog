
package for_patient;

import java.sql.*;
import javax.swing.*;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import system.DatabaseConnection;
import system.NoticeFunctions;

public class PatientLogic {

    /* =========================================================
       OPEN SIGN UP FORM
       ========================================================= */
    public void openPatientSignUp(JFrame frame) {
        new PatientSignUpGUI(frame, this);
    }

    /* =========================================================
       PATIENT LOGIN
       ========================================================= */
    public void patientLogInFunction(JFrame frame,
                                     JTextField usernameField,
                                     JPasswordField passwordField) {

        String username = usernameField.getText().trim();
        String password = new String(passwordField.getPassword());

        String sql = "SELECT authority FROM patient_accounts WHERE username = ? AND password = ?";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, username);
            pstmt.setString(2, hashPassword(password));

            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    NoticeFunctions.success(frame, "Patient login successful!");
                    frame.dispose();
                    new PatientDashboard(username);
                } else {
                    NoticeFunctions.notice(frame, "Invalid username or password!");
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
            NoticeFunctions.error(frame, "Database connection failed!");
        }
    }

    /* =========================================================
       PATIENT SIGN UP FUNCTION
       ========================================================= */
    public void patientSignUpFunction(
            JDialog dialog,
            JTextField usernameField,
            JPasswordField passwordField,
            JPasswordField confirmField
    ) {

        String username = usernameField.getText().trim();
        String password = new String(passwordField.getPassword());
        String confirmPassword = new String(confirmField.getPassword());

        /* =====================================================
           EMPTY CHECK
           ===================================================== */
        if (username.isEmpty() || password.isEmpty() || confirmPassword.isEmpty()) {
            NoticeFunctions.error(dialog, "Please complete all fields!");
            return;
        }

        /* =====================================================
           PASSWORD MATCH CHECK
           ===================================================== */
        if (!password.equals(confirmPassword)) {
            NoticeFunctions.error(dialog, "Passwords do not match!");
            return;
        }

        /* =====================================================
           CREATE ACCOUNT
           ===================================================== */
        boolean success = createPatientAccount(username, password);

        if (success) {
            NoticeFunctions.success(dialog, "Patient account created successfully!");
            dialog.dispose();
        } else {
            NoticeFunctions.error(dialog, "Username already exists!");
        }
    }

    /* =========================================================
       CREATE PATIENT ACCOUNT
       ========================================================= */
    public boolean createPatientAccount(String username, String password) {

        String checkSql = "SELECT username FROM patient_accounts WHERE username = ?";
        String insertSql = "INSERT INTO patient_accounts (username, password, authority) VALUES (?, ?, ?)";

        try (Connection conn = DatabaseConnection.getConnection()) {

            /* =================================================
               CHECK DUPLICATE USERNAME
               ================================================= */
            try (PreparedStatement checkStmt = conn.prepareStatement(checkSql)) {
                checkStmt.setString(1, username);
                try (ResultSet rs = checkStmt.executeQuery()) {
                    if (rs.next()) {
                        return false;
                    }
                }
            }

            /* =================================================
               INSERT NEW ACCOUNT
               ================================================= */
            try (PreparedStatement insertStmt = conn.prepareStatement(insertSql)) {
                insertStmt.setString(1, username);
                insertStmt.setString(2, hashPassword(password));
                insertStmt.setString(3, "Patient");

                return insertStmt.executeUpdate() > 0;
            }

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    /* =========================================================
       PATIENT ACCOUNT MANAGEMENT DATABASE WRITER
       ========================================================= */
    public boolean updatePatientAccount(String currentUsername, String newUsername, String newPassword) {
        String checkSql = "SELECT username FROM patient_accounts WHERE username = ? AND username != ?";
        String updateSql = "UPDATE patient_accounts SET username = ?, password = ? WHERE username = ?";

        try (Connection conn = DatabaseConnection.getConnection()) {
            // Check for potential duplicate usernames across patients
            try (PreparedStatement checkStmt = conn.prepareStatement(checkSql)) {
                checkStmt.setString(1, newUsername);
                checkStmt.setString(2, currentUsername);
                try (ResultSet rs = checkStmt.executeQuery()) {
                    if (rs.next()) return false;
                }
            }
            // Execute update logic securely
            try (PreparedStatement updateStmt = conn.prepareStatement(updateSql)) {
                updateStmt.setString(1, newUsername);
                updateStmt.setString(2, hashPassword(newPassword));
                updateStmt.setString(3, currentUsername);
                return updateStmt.executeUpdate() > 0;
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    /* =========================================================
       PASSWORD HASHING
       ========================================================= */
    private String hashPassword(String password) {
        try {
            MessageDigest md = MessageDigest.getInstance("SHA-256");
            byte[] hashed = md.digest(password.getBytes());
            StringBuilder sb = new StringBuilder();

            for (byte b : hashed) {
                sb.append(String.format("%02x", b));
            }
            return sb.toString();

        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        }
    }

    /* =========================================================
       LOG OUT FUNCTION
       ========================================================= */
    public void patientLogOut(JFrame frame) {
        boolean confirm = NoticeFunctions.confirm(frame, "Are you sure you want to log out?");

        if (confirm) {
            frame.dispose();
            new PatientLoginGUI(this);
        }
    }
    
    public void backToRoleSelector(JFrame parent) {
        NoticeFunctions.backToRoleSelector(parent, DatabaseConnection.getSystem());
    }
}
