package system;

import java.sql.*;

import javax.swing.*;

import GUI.LogInGUI;
import GUI.SignUpGUI;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class ClinicAccountSystem {

    private ClinicAccountSystem() {
        initializeDefaultAccounts();
    }

    /* =========================================================
       HARD CODED DEFAULT ACCOUNTS
       ========================================================= */
    private void initializeDefaultAccounts() {

        try (Connection conn = DatabaseConnection.getConnection()) {

            if (isAccountsEmpty(conn)) {

                insertDefault(
                        conn,
                        "DoctorClinic",
                        "DoctorPassword",
                        "Doctor"
                );

                insertDefault(
                        conn,
                        "NurseClinic",
                        "NursePassword",
                        "Nurse"
                );
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /* =========================================================
       CHECK IF ACCOUNT TABLE IS EMPTY
       ========================================================= */
    private boolean isAccountsEmpty(Connection conn)
            throws SQLException {

        String sql = "SELECT COUNT(*) FROM accounts";

        try (Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            return rs.next() && rs.getInt(1) == 0;
        }
    }

    /* =========================================================
       INSERT DEFAULT ACCOUNTS
       ========================================================= */
    private void insertDefault(
            Connection conn,
            String username,
            String password,
            String authority
    ) throws SQLException {

        String sql =
                "INSERT INTO accounts (username, password, authority) " +
                "VALUES (?, ?, ?)";

        try (PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, username);
            pstmt.setString(2, hashPassword(password));
            pstmt.setString(3, authority);

            pstmt.executeUpdate();
        }
    }

    /* =========================================================
       CREATE SYSTEM INSTANCE
       ========================================================= */
    public static ClinicAccountSystem createSystem() {
        return new ClinicAccountSystem();
    }

    /* =========================================================
       LOG IN FUNCTION
       ========================================================= */
    public void logInFunction(
            JFrame frame,
            JTextField usernameField,
            JPasswordField passwordField
    ) {

        String username = usernameField.getText().trim();

        String password =
                new String(passwordField.getPassword());

        String sql =
                "SELECT authority FROM accounts " +
                "WHERE username = ? AND password = ?";

        try (Connection conn = DatabaseConnection.getConnection();

             PreparedStatement pstmt =
                     conn.prepareStatement(sql)) {

            pstmt.setString(1, username);

            pstmt.setString(2, hashPassword(password));

            try (ResultSet rs = pstmt.executeQuery()) {

                if (rs.next()) {

                    String authority =
                            rs.getString("authority");

                    NoticeFunctions.success(
                            frame,
                            "Login successful!"
                    );

                    frame.dispose();

                    new ClinicDashboard(
                            this,
                            username,
                            authority
                    );

                } else {

                    NoticeFunctions.notice(
                            frame,
                            "Invalid username or password!"
                    );
                }
            }

        } catch (SQLException e) {

            e.printStackTrace();

            NoticeFunctions.error(
                    frame,
                    "Database error occurred!"
            );
        }
    }

    /* =========================================================
       GET ACCOUNT INFORMATION
       ========================================================= */
    public String[] getAccountInformation(String username) {

        String sql =
                "SELECT username, authority " +
                "FROM accounts WHERE username = ?";

        try (Connection conn = DatabaseConnection.getConnection();

             PreparedStatement pstmt =
                     conn.prepareStatement(sql)) {

            pstmt.setString(1, username);

            try (ResultSet rs = pstmt.executeQuery()) {

                if (rs.next()) {

                    return new String[] {
                            rs.getString("username"),
                            rs.getString("authority")
                    };
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return null;
    }

    /* =========================================================
       PASSWORD HASHER
       ========================================================= */
    private String hashPassword(String password) {

        try {

            MessageDigest md =
                    MessageDigest.getInstance("SHA-256");

            byte[] hashed =
                    md.digest(password.getBytes());

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
       OPEN SIGN UP FORM
       ========================================================= */
    public void openSignUpForm(JFrame frame) {
        new SignUpGUI(frame, this);
    }

    /* =========================================================
       SIGN UP FUNCTION
       ========================================================= */
    public void signUpFunction(
            JDialog dialog,
            JTextField usernameField,
            JComboBox<String> authorityBox,
            JPasswordField passwordField,
            JPasswordField confirmField
    ) {

        String username =
                usernameField.getText().trim();

        String authority =
                authorityBox.getSelectedItem() != null
                        ? authorityBox.getSelectedItem().toString()
                        : "";

        String password =
                new String(passwordField.getPassword());

        String confirmPassword =
                new String(confirmField.getPassword());

        /* =========================================================
           EMPTY FIELD CHECKER
           ========================================================= */
        if (username.isEmpty()
                || password.isEmpty()
                || confirmPassword.isEmpty()) {

            NoticeFunctions.notice(
                    dialog,
                    "Please complete all fields!"
            );

            return;
        }

        /* =========================================================
           PASSWORD MATCHING
           ========================================================= */
        if (!password.equals(confirmPassword)) {

            NoticeFunctions.notice(
                    dialog,
                    "Passwords do not match!"
            );

            return;
        }

        /* =========================================================
           CREATE ACCOUNT
           ========================================================= */
        boolean success =
                signUpAccount(
                        username,
                        password,
                        authority
                );

        if (success) {

            NoticeFunctions.success(
                    dialog,
                    "Account created successfully!"
            );

            dialog.dispose();

        } else {

            NoticeFunctions.notice(
                    dialog,
                    "Username already exists!"
            );
        }
    }

    /* =========================================================
       CREATE ACCOUNT DATABASE FUNCTION
       ========================================================= */
    public boolean signUpAccount(
            String username,
            String password,
            String authority
    ) {

        String checkSql =
                "SELECT username FROM accounts WHERE username = ?";

        String insertSql =
                "INSERT INTO accounts (username, password, authority) " +
                "VALUES (?, ?, ?)";

        try (Connection conn = DatabaseConnection.getConnection()) {

            /* =========================================================
               CHECK DUPLICATE USERNAME
               ========================================================= */
            try (PreparedStatement checkStmt =
                         conn.prepareStatement(checkSql)) {

                checkStmt.setString(1, username);

                try (ResultSet rs = checkStmt.executeQuery()) {

                    if (rs.next()) {
                        return false;
                    }
                }
            }

            /* =========================================================
               INSERT ACCOUNT
               ========================================================= */
            try (PreparedStatement insertStmt =
                         conn.prepareStatement(insertSql)) {

                insertStmt.setString(1, username);

                insertStmt.setString(
                        2,
                        hashPassword(password)
                );

                insertStmt.setString(3, authority);

                return insertStmt.executeUpdate() > 0;
            }

        } catch (SQLException e) {

            e.printStackTrace();

            return false;
        }
    }

    /* =========================================================
       ACCOUNT MANAGEMENT (UPDATE CURRENT ACCOUNT)
       ========================================================= */
    public boolean updateAccount(
            String currentUsername,
            String newUsername,
            String newPassword
    ) {

        String checkSql =
                "SELECT username FROM accounts " +
                "WHERE username = ? AND username != ?";

        String updateSql =
                "UPDATE accounts " +
                "SET username = ?, password = ? " +
                "WHERE username = ?";

        try (Connection conn = DatabaseConnection.getConnection()) {

            /* =========================================================
               CHECK DUPLICATE USERNAME
               ========================================================= */
            try (PreparedStatement checkStmt =
                         conn.prepareStatement(checkSql)) {

                checkStmt.setString(1, newUsername);

                checkStmt.setString(2, currentUsername);

                try (ResultSet rs = checkStmt.executeQuery()) {

                    if (rs.next()) {
                        return false;
                    }
                }
            }

            /* =========================================================
               UPDATE ACCOUNT
               ========================================================= */
            try (PreparedStatement updateStmt =
                         conn.prepareStatement(updateSql)) {

                updateStmt.setString(1, newUsername);

                updateStmt.setString(
                        2,
                        hashPassword(newPassword)
                );

                updateStmt.setString(3, currentUsername);

                return updateStmt.executeUpdate() > 0;
            }

        } catch (SQLException e) {

            e.printStackTrace();

            return false;
        }
    }

    /* =========================================================
       CANCEL SIGN UP
       ========================================================= */
    public void cancelSignUp(JDialog dialog) {
        dialog.dispose();
    }

    /* =========================================================
       LOG OUT FUNCTION
       ========================================================= */
    public void logOutFunction(JFrame frame) {

        boolean confirm =
                NoticeFunctions.confirm(
                        frame,
                        "Are you sure you want to log out?"
                );

        if (confirm) {

            frame.dispose();

            new LogInGUI(this);
        }
    }
}