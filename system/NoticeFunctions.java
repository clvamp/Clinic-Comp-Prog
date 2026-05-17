package system;

import GUI.ConfirmGUI;
import GUI.NoticeGUI;

import java.awt.*;

public class NoticeFunctions {

    public static void success(Window parent, String message) {
        new NoticeGUI(parent,"SUCCESS",message,new Color(46, 125, 50));
    }

    public static void error(Window parent, String message) {
        new NoticeGUI(parent,"ERROR",message,new Color(198, 40, 40));
    }

    public static void notice(Window parent, String message) {
        new NoticeGUI(parent,"NOTICE",message,new Color(33, 150, 243));
    }
    
    
    // ===== Log Out Notice
    public static boolean confirm(Window parent, String message) {
        ConfirmGUI confirmGUI = new ConfirmGUI(parent,"CONFIRMATION",message,new Color(180, 40, 50));
        return confirmGUI.isConfirmed();
    }
}