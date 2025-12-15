package gui;

import api.Employee;
import api.Rental;
import api.Validations;

import javax.swing.*;
import java.awt.*;
import java.util.Arrays;

public class UserPanel extends JPanel {
    private Employee employee;
    private JList<String> userList = new JList<>();
    public UserPanel(JFrame frame){
        super();
        setLayout(new BoxLayout(this,BoxLayout.Y_AXIS));

        initUI(frame);
    }

    private void initUI(JFrame frame){
        JPanel userPanel = new JPanel();
        userPanel.setBackground(Color.DARK_GRAY);
        userPanel.setLayout(new BoxLayout(userPanel,BoxLayout.Y_AXIS));
        userPanel.setBorder(BorderFactory.createEmptyBorder(10,10,10,10));

        employee = Employee.getEmployeeByUsername(frame.getTitle().split("\\|")[1].trim());
        JLabel curUserLabel = Labels("Current user:");
        curUserLabel.setFont(new Font("Arial",Font.BOLD,40));
        this.add(curUserLabel);
        this.add(Labels(employee.getFullname()));
        this.add(Labels(employee.getEmail()));
        this.add(Labels("Ενοικιάσεις: " + Employee.totalRents(employee) + " / " + Rental.totalRents()));
        this.add(Box.createVerticalGlue());
        this.add(addUserButton(frame));
        this.add(Box.createVerticalStrut(20));
        this.add(deleteUserButton(frame));
        this.add(Box.createVerticalStrut(50));
        this.add(logoutButton(frame));
        this.add(Box.createVerticalGlue());

    }

    private JButton addUserButton(JFrame frame){
        JButton add = new JButton("Add User");
        add.setFont(new Font("Arial",Font.BOLD,30));
        add.setAlignmentX(Component.CENTER_ALIGNMENT);
        add.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        add.setFocusable(false);
        add.addActionListener(e -> {
            addUserDialog(frame);
        });
        return add;
    }

    private JButton deleteUserButton(JFrame frame){
        JButton delete = new JButton("Delete User");
        delete.setFont(new Font("Arial",Font.BOLD,30));
        delete.setAlignmentX(Component.CENTER_ALIGNMENT);
        delete.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        delete.setFocusable(false);
        delete.addActionListener(e -> {
            deleteUserDialog(frame);
        });
        return delete;
    }

    private JButton logoutButton(JFrame frame){
        JButton logout = new JButton("Logout");
        logout.setFont(new Font("Arial",Font.BOLD,15));
        logout.setAlignmentX(Component.CENTER_ALIGNMENT);
        logout.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        logout.setFocusable(false);
        logout.addActionListener(e -> {
            JOptionPane.showMessageDialog(frame,"Αποσύνδεση από τον χρήστη " + frame.getTitle().split("\\|")[1].trim());
            frame.dispose();
            loginWindow loginWindow = new loginWindow();
        });
        return logout;
    }

    private JDialog addUserDialog(JFrame frame){
        JDialog dialog = new JDialog(frame,"Add User",true);
        dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
        dialog.setLocationRelativeTo(frame);
        dialog.setSize(300,500);
        dialog.setResizable(false);
        dialog.setLocationRelativeTo(frame);

        JPanel userPanel = new JPanel();
        userPanel.setLayout(new BoxLayout(userPanel,BoxLayout.Y_AXIS));
        userPanel.setBorder(BorderFactory.createEmptyBorder(10,10,10,10));

        JTextField nameField = Fields();
        JTextField surnameField = Fields();
        JTextField usernameField = Fields();
        JPasswordField passwordField = PassFields();
        JPasswordField confirmPassField = PassFields();
        JTextField emailField = Fields();

        JLabel nameLabel = Labels("Όνομα");
        JLabel surnameLabel = Labels("Επίθετο");
        JLabel usernameLabel = Labels("Username");
        JLabel passwordLabel = Labels("Password");
        JLabel confirmPassLabel = Labels("Επιβεβαίωση Password");
        JLabel emailLabel = Labels("Email");

        userPanel.add(nameLabel);
        userPanel.add(nameField);
        userPanel.add(Box.createVerticalStrut(10));

        userPanel.add(surnameLabel);
        userPanel.add(surnameField);
        userPanel.add(Box.createVerticalStrut(10));

        userPanel.add(usernameLabel);
        userPanel.add(usernameField);
        userPanel.add(Box.createVerticalStrut(10));

        userPanel.add(passwordLabel);
        userPanel.add(passwordField);
        userPanel.add(Box.createVerticalStrut(10));

        userPanel.add(confirmPassLabel);
        userPanel.add(confirmPassField);
        userPanel.add(Box.createVerticalStrut(10));

        userPanel.add(emailLabel);
        userPanel.add(emailField);
        userPanel.add(Box.createVerticalStrut(10));


        JButton addButton = new JButton("Add User");
        addButton.setFont(new Font("Arial",Font.BOLD,15));
        addButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        addButton.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        addButton.setFocusable(false);
        addButton.addActionListener(e -> {
            String[] data = new String[]{
                    nameField.getText().trim(),
                    surnameField.getText().trim(),
                    usernameField.getText().trim(),
                    passwordField.getText().trim(),
                    confirmPassField.getText().trim(),
                    emailField.getText().trim()
            };
            String fullName = data[0].trim() + " " + data[1].trim();
            String username = data[2];
            String password = data[3];
            String confirmPass = data[4];
            String email = data[5];

            boolean filledBlanks = true;
            for(int i = 0; i < data.length; i++){
                if(data[i].isEmpty()){
                    filledBlanks = false;
                    break;
                }
            }
            if(!filledBlanks){
                JOptionPane.showMessageDialog(dialog,"Παρακαλώ συμπληρώστε όλα τα κενά.");
                return;
            }
            if(!Validations.confirmPassword(password, confirmPass)){
                JOptionPane.showMessageDialog(dialog,"Οι κωδικοί δεν συμβαδίζουν.");
                return;
            }
            if(!Validations.isValidEmail(email)){
                JOptionPane.showMessageDialog(dialog,"Το email που δώσατε δεν αντιστοιχεί σε email.");
                return;
            }
            Employee employee = new Employee(fullName,username,password,email);
            if(Employee.addEmployee(employee)){
                JOptionPane.showMessageDialog(dialog,"Ο Χρήστης " + fullName + "με username " + username + " καταχωρήθηκε με επιτυχία");
                dialog.dispose();

            }else{
                JOptionPane.showMessageDialog(dialog,"Δεν μπορεσε να ολοκληρωθει η διαδικασια. Ο χρηστης πιθανο να υπαρχει ηδη με το ιδιο username");
            }

        });

        userPanel.add(addButton);

        dialog.setContentPane(userPanel);
        dialog.setVisible(true);
        return dialog;
    }

    private JDialog deleteUserDialog(JFrame frame){
        JDialog dialog = new JDialog(frame,"Delete user",true);
        dialog.setSize(500,200);
        dialog.setResizable(false);
        dialog.setLocationRelativeTo(frame);
        dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);


        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel,BoxLayout.Y_AXIS));
        panel.setBorder(BorderFactory.createEmptyBorder(10,10,10,10));

        userList.setListData(DashBoard.updateList(Employee.allEmployees()));
        userList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        panel.add(new JScrollPane(userList));

        JButton deleteButton = new JButton("Delete");
        deleteButton.setFont(new Font("Arial",Font.BOLD,15));
        deleteButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        deleteButton.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        deleteButton.setFocusable(false);
        deleteButton.addActionListener(e -> {
            String selected = userList.getSelectedValue();
            if(selected == null){
                JOptionPane.showMessageDialog(dialog,"Επιλέξτε κάποιον χρήστη για διαγραφή");
                return;
            }
            String[] data = selected.trim().split("\\|");
            String fullName = data[0].trim() + " " + data[1].trim();
            String username = data[2].trim();
            String email = data[3].trim();
            String password = data[4].trim();
            Employee employee = new Employee(fullName,username,password,email);
            int choice = JOptionPane.showConfirmDialog(dialog,"Είστε σίγουροι ότι θέλετε να διαγράψετε τον χρήστη " + username + " απο το σύστημα;","Επιβεβαίωση διαγραφής",JOptionPane.YES_NO_OPTION);
            if(choice == JOptionPane.YES_OPTION){
                if(Employee.deleteEmployee(employee)){
                    JOptionPane.showMessageDialog(dialog,"Ο χρήστης " + username + " διαγράφηκε με επιτυχία");
                    userList.setListData(DashBoard.updateList(Employee.allEmployees()));
                    dialog.dispose();
                }
                else{
                    JOptionPane.showMessageDialog(dialog,"Δεν μπόρεσε να διαγραφεί ο χρήστης");
                }
            }else{
                JOptionPane.showMessageDialog(dialog,"Ακυρώθηκε η διαδικασία");
            }

        });

        panel.add(Box.createVerticalStrut(15));
        panel.add(deleteButton);

        dialog.setContentPane(panel);
        dialog.setVisible(true);
        return dialog;

    }

    private JLabel Labels(String textLabel){
        JLabel label = new JLabel(textLabel);
        label.setAlignmentX(Component.CENTER_ALIGNMENT);
        label.setFont(new Font("Arial",Font.BOLD,20));
        label.setFocusable(false);
        return label;

    }

    private JTextField Fields(){
        JTextField field = new JTextField();
        field.setAlignmentX(Component.CENTER_ALIGNMENT);
        field.setPreferredSize(new Dimension(150,field.getPreferredSize().height));
        field.setMaximumSize(new Dimension(150,25));
        field.setFont(new Font("Arial",Font.BOLD,20));
        return field;
    }

    private JPasswordField PassFields(){
        JPasswordField passField = new JPasswordField();
        passField.setAlignmentX(Component.CENTER_ALIGNMENT);
        passField.setPreferredSize(new Dimension(150,passField.getPreferredSize().height));
        passField.setMaximumSize(new Dimension(150,25));
        passField.setFont(new Font("Arial",Font.BOLD,20));
        return passField;
    }



}
