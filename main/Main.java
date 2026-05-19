package main;

import GUI.DashboardGUI;
import system.ClinicAccountSystem;
import system.ClinicDashboard;
import system.NoticeFunctions; // Imported to access your new chooseRole helper

public class Main {
    public static void main(String[] args) {

        // Initialize the backend core
        ClinicAccountSystem system = ClinicAccountSystem.createSystem();

        /* =========================================================
           OPENS STARTUP ROLE SELECTOR
           ========================================================= 
           We route this through NoticeFunctions and pass 'null' 
           because the application is just booting up and does not 
           have an existing parent window yet. */
         NoticeFunctions.chooseRole(null, system);
        
        //ClinicDashboard dashboard = new ClinicDashboard(system, "DoctorClinic", "Doctor");
        //new DashboardGUI(system, dashboard);

    }
}