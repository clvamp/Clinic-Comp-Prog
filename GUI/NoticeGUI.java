package GUI;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;

public class NoticeGUI extends JDialog {

    public NoticeGUI(Window parent,
                     String title,
                     String message,
                     Color accentColor) {

    	super(parent instanceof Frame ? (Frame) parent : null);

        /* =========================================================
           DIALOG SETTINGS
           ========================================================= */
        setModal(true);
        setUndecorated(true);
        setSize(300, 220);
        setLocationRelativeTo(parent);
        setLayout(new BorderLayout());

        /* =========================================================
           MAIN PANEL
           ========================================================= */
        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new BorderLayout());
        mainPanel.setBackground(new Color(245, 245, 245));
        mainPanel.setBorder(BorderFactory.createLineBorder(accentColor, 3));

        add(mainPanel);

        /* =========================================================
           TOP BAR
           ========================================================= */
        JPanel topPanel = new JPanel(new BorderLayout());
        topPanel.setBackground(accentColor);
        topPanel.setPreferredSize(new Dimension(400, 50));

        JLabel titleLabel = new JLabel(title);
        titleLabel.setForeground(Color.WHITE);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 18));
        titleLabel.setBorder(new EmptyBorder(0, 15, 0, 0));

        topPanel.add(titleLabel, BorderLayout.WEST);

        mainPanel.add(topPanel, BorderLayout.NORTH);

        /* =========================================================
           CENTER MESSAGE
           ========================================================= */
        JPanel centerPanel = new JPanel(new GridBagLayout());
        centerPanel.setBackground(new Color(245, 245, 245));

        JLabel messageLabel = new JLabel(
                "<html><div style='text-align:center;'>"
                        + message
                        + "</div></html>"
        );

        messageLabel.setFont(new Font("Arial", Font.PLAIN, 15));
        messageLabel.setForeground(new Color(50, 50, 50));

        centerPanel.add(messageLabel);

        mainPanel.add(centerPanel, BorderLayout.CENTER);

        /* =========================================================
           BUTTON PANEL
           ========================================================= */
        JPanel buttonPanel = new JPanel();
        buttonPanel.setBackground(new Color(245, 245, 245));
        buttonPanel.setBorder(new EmptyBorder(0, 0, 15, 0));

        JButton okButton = new JButton("OK");

        okButton.setFocusable(false);
        okButton.setCursor(new Cursor(Cursor.HAND_CURSOR));
        okButton.setFont(new Font("Arial", Font.BOLD, 13));
        okButton.setForeground(Color.WHITE);
        okButton.setBackground(accentColor);
        okButton.setPreferredSize(new Dimension(100, 35));
        okButton.setBorderPainted(false);

        okButton.addActionListener(e -> dispose());

        buttonPanel.add(okButton);

        mainPanel.add(buttonPanel, BorderLayout.SOUTH);

        /* =========================================================
           SHOW WINDOW
           ========================================================= */
        setVisible(true);
    }
}