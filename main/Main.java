package main;

import GUI.LogInGUI;
import system.ClinicAccountSystem;
import system.ClinicDashboard; // Imported this to handle the dashboard initialization

public class Main {
	public static void main(String[] args) {
		
		ClinicAccountSystem system = ClinicAccountSystem.createSystem();
        // new LogInGUI(system);
		
		// Call the controller instead of the GUI directly
		new ClinicDashboard(system, "DoctorClinic", "Doctor");	
        
	}
}