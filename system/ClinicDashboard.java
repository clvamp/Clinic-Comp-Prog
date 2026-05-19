package system;

import GUI.DashboardGUI;
import GUI.contents.HomePanel;
import GUI.contents.NurseAppointments;
import GUI.contents.AccountPanel;

import javax.swing.JPanel;

public class ClinicDashboard {

    private String username;
    private String authority;

    private ClinicAccountSystem system;

    public ClinicDashboard(
            ClinicAccountSystem system,
            String username,
            String authority
    ) {

        this.system = system;

        this.username = username;
        this.authority = authority;

        openDashboard();
    }

    private void openDashboard() {
        new DashboardGUI(system, this);
    }

    /* =========================================================
       GETTERS
       ========================================================= */

    public String getUsername() {
        return username;
    }

    public String getAuthority() {
        return authority;
    }

    /* =========================================================
       PANEL LOADERS
       ========================================================= */

    public JPanel loadHomePanel() {
        return new HomePanel(authority);
    }
    
    public JPanel loadNurseAppointments() {
        return new NurseAppointments(authority);
    }

    public JPanel loadAccountPanel() {
        return new AccountPanel(system, username, authority);
    }
}