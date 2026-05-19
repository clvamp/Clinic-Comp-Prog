package GUI;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;

public class ConfirmGUI extends JDialog {

    private boolean confirmed = false;

    public ConfirmGUI(Window parent,
                      String title,
                      String message,
                      Color accentColor) {

        super(parent instanceof Frame ? (Frame) parent : null);

        /* =========================================================
           DIALOG SETTINGS
           ========================================================= */
        setModal(true);
        setUndecorated(true);
        setSize(320, 200);
        setLocationRelativeTo(parent);
        setLayout(new BorderLayout());

        /* =========================================================
           MAIN PANEL
           ========================================================= */
        JPanel mainPanel = new JPanel(new BorderLayout());
        mainPanel.setBackground(new Color(245, 245, 245));
        mainPanel.setBorder(BorderFactory.createLineBorder(accentColor, 3));

        add(mainPanel);

        /* =========================================================
           TOP PANEL
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
           MESSAGE PANEL
           ========================================================= */
        JPanel centerPanel = new JPanel(new GridBagLayout());
        centerPanel.setBackground(new Color(245, 245, 245));

        JLabel messageLabel = new JLabel(
                "<html><div style='width:240px; text-align:center;'>"
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

        JButton yesButton = createButton("YES", accentColor);
        JButton noButton = createButton("NO", new Color(120, 120, 120));

        yesButton.addActionListener(e -> {
            confirmed = true;
            dispose();
        });

        noButton.addActionListener(e -> {
            confirmed = false;
            dispose();
        });

        buttonPanel.add(yesButton);
        buttonPanel.add(noButton);

        mainPanel.add(buttonPanel, BorderLayout.SOUTH);

        setVisible(true);
    }

    /* =========================================================
       BUTTON DESIGN
       ========================================================= */
    private JButton createButton(String text, Color color) {

        JButton button = new JButton(text);

        button.setFocusable(false);
        button.setCursor(new Cursor(Cursor.HAND_CURSOR));
        button.setFont(new Font("Arial", Font.BOLD, 13));
        button.setForeground(Color.WHITE);
        button.setBackground(color);
        button.setPreferredSize(new Dimension(100, 35));
        button.setBorderPainted(false);

        return button;
    }

    /* =========================================================
       RETURN RESULT
       ========================================================= */
    public boolean isConfirmed() {
        return confirmed;
    }
}