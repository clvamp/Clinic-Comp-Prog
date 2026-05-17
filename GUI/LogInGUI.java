package GUI;

import java.awt.Font;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import java.awt.Color;
import java.awt.BorderLayout;
import javax.swing.JPanel;
import java.awt.GridBagLayout;
import java.awt.GridBagConstraints;
import java.awt.Insets;
import java.awt.Dimension;
import java.awt.Cursor;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import system.ClinicAccountSystem;

public class LogInGUI {

    private ClinicAccountSystem system;

    public LogInGUI(ClinicAccountSystem system) {

        this.setSystem(system);

        /* =========================================================
           MAIN FRAME
           ========================================================= */
        JFrame frame = new JFrame("Clinic Application");

        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(800, 600);
        frame.setLayout(new BorderLayout());
        frame.setResizable(true);
        frame.setLocationRelativeTo(null);

        Color mainBg = new Color(245, 245, 245);

        frame.getContentPane().setBackground(mainBg);

        /* =========================================================
           LEFT PANEL, LOGIN
           ========================================================= */
        JPanel leftPanel = new JPanel(new GridBagLayout());

        leftPanel.setBackground(mainBg);
        leftPanel.setPreferredSize(new Dimension(260, 600));

        frame.add(leftPanel, BorderLayout.WEST);

        /* =========================================================
           RIGHT PANEL, IMAGE
           ========================================================= */
        JPanel rightPanel = new JPanel(new BorderLayout());

        rightPanel.setBackground(new Color(230, 233, 238));

        frame.add(rightPanel, BorderLayout.CENTER);

        /* =========================================================
           IMAGE
           ========================================================= */
        ImageIcon rightImage = new ImageIcon(
                getClass().getResource("logoapp.png")
        );

        JLabel imageLabel = new JLabel(rightImage);

        imageLabel.setHorizontalAlignment(JLabel.CENTER);

        rightPanel.add(imageLabel, BorderLayout.CENTER);

        /* =========================================================
           FORM PANEL
           ========================================================= */
        JPanel formPanel = new JPanel(new GridBagLayout());

        formPanel.setBackground(mainBg);

        GridBagConstraints leftGbc = new GridBagConstraints();

        leftGbc.gridx = 0;
        leftGbc.gridy = 0;
        leftGbc.anchor = GridBagConstraints.NORTH;
        leftGbc.insets = new Insets(30, 10, 10, 10);

        leftPanel.add(formPanel, leftGbc);

        GridBagConstraints gbc = new GridBagConstraints();

        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.gridx = 0;
        gbc.fill = GridBagConstraints.HORIZONTAL;

        /* =========================================================
           LOGO
           ========================================================= */
        ImageIcon accLogo = new ImageIcon(
                getClass().getResource("acclogo.png")
        );

        JLabel logoLabel = new JLabel(accLogo);

        logoLabel.setHorizontalAlignment(JLabel.CENTER);

        gbc.gridy = 0;

        formPanel.add(logoLabel, gbc);

        /* =========================================================
           HEADER
           ========================================================= */
        JLabel labelHeader = new JLabel("LOGIN FORM");

        labelHeader.setFont(new Font("Arial", Font.BOLD, 22));
        labelHeader.setForeground(new Color(40, 40, 40));
        labelHeader.setHorizontalAlignment(JLabel.CENTER);

        gbc.gridy = 1;

        formPanel.add(labelHeader, gbc);

        /* =========================================================
           USERNAME
           ========================================================= */
        JPanel userPanel = new JPanel(new GridBagLayout());

        userPanel.setBackground(mainBg);

        GridBagConstraints uGbc = new GridBagConstraints();

        uGbc.gridx = 0;
        uGbc.fill = GridBagConstraints.HORIZONTAL;

        JLabel labelName = new JLabel("Username:");

        labelName.setFont(new Font("Arial", Font.BOLD, 14));
        labelName.setForeground(new Color(60, 60, 60));

        uGbc.insets = new Insets(2, 5, 2, 5);
        uGbc.gridy = 0;

        userPanel.add(labelName, uGbc);

        JTextField nameInput = new JTextField();

        nameInput.setFont(new Font("Arial", Font.PLAIN, 14));
        nameInput.setPreferredSize(new Dimension(200, 30));

        nameInput.setBackground(Color.WHITE);

        uGbc.insets = new Insets(10, 5, 5, 5);
        uGbc.gridy = 1;

        userPanel.add(nameInput, uGbc);

        gbc.gridy = 2;

        formPanel.add(userPanel, gbc);

        /* =========================================================
           PASSWORD
           ========================================================= */
        JPanel passPanel = new JPanel(new GridBagLayout());

        passPanel.setBackground(mainBg);

        GridBagConstraints pGbc = new GridBagConstraints();

        pGbc.gridx = 0;
        pGbc.fill = GridBagConstraints.HORIZONTAL;

        JLabel labelPass = new JLabel("Password:");

        labelPass.setFont(new Font("Arial", Font.BOLD, 14));
        labelPass.setForeground(new Color(60, 60, 60));

        pGbc.insets = new Insets(2, 5, 2, 5);
        pGbc.gridy = 0;

        passPanel.add(labelPass, pGbc);

        JPasswordField passInput = new JPasswordField();

        passInput.setFont(new Font("Arial", Font.PLAIN, 14));
        passInput.setPreferredSize(new Dimension(200, 30));

        passInput.setBackground(Color.WHITE);

        pGbc.insets = new Insets(10, 5, 5, 5);
        pGbc.gridy = 1;

        passPanel.add(passInput, pGbc);

        gbc.gridy = 3;

        formPanel.add(passPanel, gbc);

        /* =========================================================
           BUTTON PANEL
           ========================================================= */
        JPanel buttonPanel = new JPanel(new GridBagLayout());

        buttonPanel.setBackground(mainBg);

        GridBagConstraints bGbc = new GridBagConstraints();

        bGbc.insets = new Insets(5, 10, 5, 10);

        JButton loginButton = createStyledButton("Login");
        JButton signupButton = createStyledButton("Sign Up");

        loginButton.setPreferredSize(new Dimension(90, 30));
        signupButton.setPreferredSize(new Dimension(90, 30));

        bGbc.gridx = 0;
        buttonPanel.add(loginButton, bGbc);

        bGbc.gridx = 1;
        buttonPanel.add(signupButton, bGbc);

        gbc.gridy = 4;

        formPanel.add(buttonPanel, gbc);

        /* =========================================================
           ACTION LISTENERS
           ========================================================= */
        loginButton.addActionListener(e -> {
            system.logInFunction(frame, nameInput, passInput);
        });

        signupButton.addActionListener(e -> {
            system.openSignUpForm(frame);
        });

        frame.setVisible(true);
    }

    /* =========================================================
       BUTTON DESIGN
       ========================================================= */
    private JButton createStyledButton(String text) {

        JButton button = new JButton(text);

        button.setFocusable(false);

        button.setFont(new Font("Arial", Font.BOLD, 13));

        button.setForeground(Color.WHITE);

        button.setBackground(new Color(55, 55, 55));

        button.setCursor(new Cursor(Cursor.HAND_CURSOR));

        button.setFocusPainted(false);

        button.setBorder(
                javax.swing.BorderFactory.createLineBorder(
                        new Color(55, 55, 55),
                        1,
                        true
                )
        );

        /*
           HOVER EFFECT
        */
        button.addMouseListener(new MouseAdapter() {

            public void mouseEntered(MouseEvent e) {

                button.setBackground(new Color(110, 170, 255));

                button.setBorder(
                        javax.swing.BorderFactory.createLineBorder(
                                new Color(110, 170, 255),
                                1,
                                true
                        )
                );
            }

            public void mouseExited(MouseEvent e) {

                button.setBackground(new Color(55, 55, 55));

                button.setBorder(
                        javax.swing.BorderFactory.createLineBorder(
                                new Color(55, 55, 55),
                                1,
                                true
                        )
                );
            }
        });

        return button;
    }

    public ClinicAccountSystem getSystem() {
        return system;
    }

    public void setSystem(ClinicAccountSystem system) {
        this.system = system;
    }
}