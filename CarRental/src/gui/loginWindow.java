package gui;

import api.Employee;

import javax.swing.*;
import java.awt.*;

public class loginWindow extends JFrame{
    public loginWindow(){
        JFrame frame = new JFrame();
        Font font = new Font("Arial",Font.BOLD,15);
        frame.setTitle("Login Window");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setResizable(false);
        frame.setSize(300,300);
        frame.setLayout(new BoxLayout(frame.getContentPane(),BoxLayout.Y_AXIS));

        ((JComponent) frame.getContentPane()).setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        JLabel label = new JLabel("Login");
        label.setFont(font);
        label.setAlignmentX(Component.CENTER_ALIGNMENT);
        frame.add(label);

        frame.add(Box.createRigidArea(new Dimension(0, 20)));

        JTextField username = new JTextField();
        username.setAlignmentX(Component.CENTER_ALIGNMENT);
        username.setColumns(15);
        username.setMaximumSize(username.getPreferredSize());
        username.setFont(font);
        frame.add(username);

        frame.add(Box.createRigidArea(new Dimension(0, 20)));

        JPasswordField password = new JPasswordField();
        password.setAlignmentX(Component.CENTER_ALIGNMENT);
        password.setColumns(15);
        password.setMaximumSize(password.getPreferredSize());
        password.setFont(font);
        frame.add(password);

        frame.add(Box.createRigidArea(new Dimension(0, 20)));

        JButton button = new JButton("Login");
        button.setFont(font);
        button.setAlignmentX(Component.CENTER_ALIGNMENT);
        button.addActionListener(e -> {
            String name = username.getText();
            String pass = new String(password.getPassword());
            System.out.println(name+":"+pass);
            String logged = Employee.Login(name,pass);
            if(logged.equals("Not found")){
                JOptionPane.showMessageDialog(frame,"Wrong username or password");
            }else{
                JOptionPane.showMessageDialog(frame,"Welcome, " + name);
                frame.dispose();
                DashBoard dashBoard = new DashBoard(name);
            }

        });
        frame.add(button);

        frame.setLocationRelativeTo(null);
        frame.setVisible(true);

    }
}
