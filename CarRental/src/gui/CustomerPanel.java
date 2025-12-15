package gui;

import api.Customer;
import api.Validations;
import filemanager.Reader;

import javax.swing.*;
import java.awt.*;
import java.util.Objects;

public class CustomerPanel extends JPanel {
    JList<String> customersList = new JList<>();
    public CustomerPanel(JFrame frame) {
        super();
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        setBackground(Color.DARK_GRAY);

        initUI(frame);
    }

    public void initUI(JFrame frame){
        JPanel customerPanel = new JPanel();
        customerPanel.setLayout(new BoxLayout(customerPanel, BoxLayout.Y_AXIS));
        customerPanel.setBackground(Color.DARK_GRAY);

        String[][] Customers = Customer.allCustomers();

        //JList<String> customersList = new JList<>(DashBoard.updateList(Customers));
        customersList.setListData(DashBoard.updateList(Customers));
        customersList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

        JScrollPane scrollPane = new JScrollPane(customersList);
        customerPanel.add(scrollPane);
        this.add(createsearchButton(frame));
        this.add(showAllButton(frame));
        this.add(editCustomerButton(frame));
        this.add(addCustomerButton(frame));
        this.add(customerPanel);


    }



    private JButton createsearchButton(JFrame frame){
        JButton searchButton = new JButton("Search");
        searchButton.setFont(new Font("Arial", Font.BOLD, 15));
        searchButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        searchButton.setFocusable(false);
        searchButton.addActionListener(e -> {
            searchDialog(frame);
        });
        return searchButton;
    }

    private JButton showAllButton(JFrame frame){
        JButton showAll = new JButton("Show All");
        showAll.setFont(new Font("Arial", Font.BOLD, 15));
        showAll.setAlignmentX(Component.CENTER_ALIGNMENT);
        showAll.setFocusable(false);
        showAll.addActionListener(e -> {
            customersList.setListData(DashBoard.updateList(Customer.allCustomers()));
            JOptionPane.showMessageDialog(frame, "All Customers have been shown");
        });
        return showAll;
    }

    private JButton editCustomerButton(JFrame frame){
        JButton editCustomerButton = new JButton("Edit Customer");
        editCustomerButton.setFont(new Font("Arial", Font.BOLD, 15));
        editCustomerButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        editCustomerButton.setFocusable(false);
        editCustomerButton.addActionListener(e -> {
            if(customersList.getSelectedValue() == null){
                JOptionPane.showMessageDialog(frame, "Επέλεξε customer");
            }else{
                String AFM = customersList.getSelectedValue().split("\\|")[0].trim();
                editDialog(frame,AFM);
            }

        });
        return editCustomerButton;
    }

    private JButton addCustomerButton(JFrame frame){
        JButton addCustomerButton = new JButton("Add Customer");
        addCustomerButton.setFont(new Font("Arial", Font.BOLD, 15));
        addCustomerButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        addCustomerButton.setFocusable(false);
        addCustomerButton.addActionListener(e -> {
            addDialog(frame);
        });
        return addCustomerButton;
    }

    private JDialog searchDialog(JFrame frame){
        JDialog searchDialog = new JDialog(frame,"Search",true);
        searchDialog.setSize(300,350);
        searchDialog.setResizable(false);

        JPanel searchPanel = new JPanel();
        searchPanel.setLayout(new BoxLayout(searchPanel, BoxLayout.Y_AXIS));
        searchPanel.setBorder(BorderFactory.createEmptyBorder(10,10,10,10));

        JLabel afmLabel = new JLabel("ΑΦΜ");
        afmLabel.setFont(new Font("Arial", Font.BOLD, 15));
        afmLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        searchPanel.add(afmLabel);

        JTextField AFM = new JTextField();
        AFM.setMaximumSize(new Dimension(Integer.MAX_VALUE, AFM.getPreferredSize().height));
        AFM.setAlignmentX(Component.CENTER_ALIGNMENT);
        searchPanel.add(AFM);
        searchPanel.add(Box.createVerticalStrut(10));

        JLabel nameLabel = new JLabel("Όνομα");
        nameLabel.setFont(new Font("Arial",Font.BOLD,15));
        nameLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        searchPanel.add(nameLabel);

        JTextField name = new JTextField();
        name.setMaximumSize(new Dimension(Integer.MAX_VALUE, name.getPreferredSize().height));
        name.setAlignmentX(Component.CENTER_ALIGNMENT);
        searchPanel.add(name);
        searchPanel.add(Box.createVerticalStrut(10));

        JLabel surnameLabel = new JLabel("Επώνυμο");
        surnameLabel.setFont(new Font("Arial",Font.BOLD,15));
        surnameLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        searchPanel.add(surnameLabel);

        JTextField surname = new JTextField();
        surname.setMaximumSize(new Dimension(Integer.MAX_VALUE, surname.getPreferredSize().height));
        surname.setAlignmentX(Component.CENTER_ALIGNMENT);
        searchPanel.add(surname);
        searchPanel.add(Box.createVerticalStrut(10));

        JLabel phoneLabel = new JLabel("Τηλέφωνο");
        phoneLabel.setFont(new Font("Arial",Font.BOLD,15));
        phoneLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        searchPanel.add(phoneLabel);

        JTextField phone = new JTextField();
        phone.setMaximumSize(new Dimension(Integer.MAX_VALUE, phone.getPreferredSize().height));
        phone.setAlignmentX(Component.CENTER_ALIGNMENT);
        searchPanel.add(phone);
        searchPanel.add(Box.createVerticalStrut(10));

        JLabel emailLabel = new JLabel("Email");
        emailLabel.setFont(new Font("Arial",Font.BOLD,15));
        emailLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        searchPanel.add(emailLabel);

        JTextField email = new JTextField();
        email.setMaximumSize(new Dimension(Integer.MAX_VALUE, email.getPreferredSize().height));
        email.setAlignmentX(Component.CENTER_ALIGNMENT);
        searchPanel.add(email);
        searchPanel.add(Box.createVerticalStrut(10));

        JButton goButton = new  JButton("Go");
        goButton.setFont(new Font("Arial", Font.BOLD,15));
        goButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        goButton.setFocusable(false);
        goButton.addActionListener(e -> {

            String afmText = AFM.getText().trim();
            String nameText = name.getText().trim();
            String surnameText = surname.getText().trim();
            String phoneText = phone.getText();
            String emailText = email.getText().trim();

            String[] criteria = new String[]{
                    afmText,
                    nameText,
                    surnameText,
                    phoneText,
                    emailText
            };

            boolean allNull = true;
            for(int i = 0; i < criteria.length; i++){
                if(!criteria[i].trim().isEmpty()){
                    allNull = false;
                    break;
                }
            }
            if(allNull){
                JOptionPane.showMessageDialog(searchDialog,"Complete at least 1 of the blanks");
            }
            else{
                String[][] all_customers = new Reader("src/customers.csv").read();
                String[][] searchedCustomers = Customer.searchCustomer(all_customers, criteria);
                customersList.setListData(DashBoard.updateList(searchedCustomers));
                searchDialog.dispose();
            }




        });
        searchPanel.add(goButton);



        searchDialog.setContentPane(searchPanel);
        searchDialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
        searchDialog.setLocationRelativeTo(frame);
        searchDialog.setVisible(true);
        return searchDialog;
    }

    private JDialog editDialog(JFrame frame,String customerAFM){
        JDialog editDialog = new JDialog(frame,"Edit Customer",true);
        editDialog.setSize(300,200);
        editDialog.setResizable(false);

        JPanel editPanel = new JPanel();
        editPanel.setLayout(new BoxLayout(editPanel,BoxLayout.Y_AXIS));
        editPanel.setBorder(BorderFactory.createEmptyBorder(10,10,10,10));

        JLabel label = new JLabel("Choose what to edit");
        label.setFont(new Font("Arial",Font.BOLD,15));
        label.setAlignmentX(Component.CENTER_ALIGNMENT);
        editPanel.add(label);
        editPanel.add(Box.createVerticalStrut(10));

        JComboBox comboBox = new JComboBox(new String[]{"ΑΦΜ","Ονοματεπώνυμο","Τηλέφωνο","Email"});
        comboBox.setMaximumSize(new Dimension(150, comboBox.getPreferredSize().height));
        comboBox.setSelectedIndex(0);
        editPanel.add(comboBox);

        editPanel.add(Box.createVerticalStrut(20));

        JTextField new_Item = new JTextField();
        new_Item.setMaximumSize(new Dimension(150, new_Item.getPreferredSize().height));
        new_Item.setAlignmentX(Component.CENTER_ALIGNMENT);
        editPanel.add(new_Item);

        editPanel.add(Box.createVerticalStrut(10));

        JButton doneButton = new JButton("Done");
        doneButton.setFont(new Font("Arial",Font.BOLD,15));
        doneButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        doneButton.addActionListener(e -> {
            if(new_Item.getText().isEmpty()){
                JOptionPane.showMessageDialog(editDialog,"Συμπλήρωσε τα κενά");
            }
            else{
                boolean done = Customer.editCustomer(customerAFM,Objects.requireNonNull(comboBox.getSelectedItem()).toString(), new_Item.getText());
                if(done){
                    JOptionPane.showMessageDialog(editDialog,"Τα στοιχεια ενημερωθηκαν");
                    customersList.setListData(DashBoard.updateList(Customer.allCustomers()));
                    editDialog.dispose();
                }else{
                    JOptionPane.showMessageDialog(editDialog,"Παρουσιαστηκε σφαλμα στην ενημερωση των στοιχειων.");
                }


            }
        });
        editPanel.add(doneButton);


        editDialog.setContentPane(editPanel);
        editDialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
        editDialog.setLocationRelativeTo(frame);
        editDialog.setVisible(true);
        return editDialog;
    }

    private JDialog addDialog(JFrame frame){
        JDialog addDialog = new JDialog(frame,"Add Customer",true);
        addDialog.setSize(300,400);
        addDialog.setResizable(false);

        JPanel addPanel = new JPanel();
        addPanel.setLayout(new BoxLayout(addPanel,BoxLayout.Y_AXIS));
        addPanel.setBorder(BorderFactory.createEmptyBorder(10,10,10,10));

        JLabel afmLabel = new JLabel("ΑΦΜ");
        afmLabel.setFont(new Font("Arial",Font.BOLD,15));
        afmLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        addPanel.add(afmLabel);

        JTextField afmField = new JTextField();
        afmField.setMaximumSize(new Dimension(150, afmField.getPreferredSize().height));
        afmField.setAlignmentX(Component.CENTER_ALIGNMENT);
        addPanel.add(afmField);

        addPanel.add(Box.createVerticalStrut(10));

        JLabel nameLabel = new JLabel("Όνομα");
        nameLabel.setFont(new Font("Arial",Font.BOLD,15));
        nameLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        addPanel.add(nameLabel);

        JTextField nameField = new JTextField();
        nameField.setMaximumSize(new Dimension(150, nameField.getPreferredSize().height));
        nameField.setAlignmentX(Component.CENTER_ALIGNMENT);
        addPanel.add(nameField);

        addPanel.add(Box.createVerticalStrut(10));

        JLabel surnameLabel = new JLabel("Επίθετο");
        surnameLabel.setFont(new Font("Arial",Font.BOLD,15));
        surnameLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        addPanel.add(surnameLabel);

        JTextField surnameField = new JTextField();
        surnameField.setMaximumSize(new Dimension(150, surnameField.getPreferredSize().height));
        surnameField.setAlignmentX(Component.CENTER_ALIGNMENT);
        addPanel.add(surnameField);

        addPanel.add(Box.createVerticalStrut(10));

        JLabel phoneLabel = new JLabel("Τηλέφωνο");
        phoneLabel.setFont(new Font("Arial",Font.BOLD,15));
        phoneLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        addPanel.add(phoneLabel);

        JTextField phoneField = new JTextField();
        phoneField.setMaximumSize(new Dimension(150, phoneField.getPreferredSize().height));
        phoneField.setAlignmentX(Component.CENTER_ALIGNMENT);
        addPanel.add(phoneField);

        addPanel.add(Box.createVerticalStrut(10));

        JLabel emailLabel = new JLabel("Email");
        emailLabel.setFont(new Font("Arial",Font.BOLD,15));
        emailLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        addPanel.add(emailLabel);

        JTextField emailField = new JTextField();
        emailField.setMaximumSize(new Dimension(150, emailField.getPreferredSize().height));
        emailField.setAlignmentX(Component.CENTER_ALIGNMENT);
        addPanel.add(emailField);

        addPanel.add(Box.createVerticalStrut(10));

        JButton addButton = new JButton("Add");
        addButton.setFont(new Font("Arial",Font.BOLD,15));
        addButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        addButton.addActionListener(e -> {
            String[] data = new String[]{
                    afmField.getText().trim(),
                    nameField.getText().trim(),
                    surnameField.getText().trim(),
                    phoneField.getText().trim(),
                    emailField.getText().trim()
            };
            boolean filledBlanks = true;
            for(int i = 0; i < data.length; i++){
                if (data[i].trim().isEmpty()) {
                    filledBlanks = false;
                    break;
                }
            }
            if(!filledBlanks){
                JOptionPane.showMessageDialog(addDialog,"Συμπληρώστε ολα τα κενά");
            }
            else{
                if(Validations.isValidEmail(emailField.getText()) &&  Validations.isValidPhone(phoneField.getText())){
                    String fullName = data[1] + " " + data[2];
                    Customer newCustomer = new Customer(data[0],fullName,data[3],data[4]);
                    Customer.addCustomer(newCustomer);
                    customersList.setListData(DashBoard.updateList(new Reader("src/customers.csv").read()));
                    JOptionPane.showMessageDialog(addDialog,"Ο πελάτης καταχωρήθηκε με επιτυχία!");
                    addDialog.dispose();
                }else if(!Validations.isValidEmail(emailField.getText()) &&  Validations.isValidPhone(phoneField.getText())){
                    JOptionPane.showMessageDialog(addDialog,"Το email που βαλατε ειναι λαθος");
                } else if(Validations.isValidEmail(emailField.getText()) &&  !Validations.isValidPhone(phoneField.getText())){
                    JOptionPane.showMessageDialog(addDialog,"Το τηλέφωνο που βαλατε ειναι λαθος");
                }else{
                    JOptionPane.showMessageDialog(addDialog,"Το τηλεφωνο και το email ειναι λαθος");
                }

            }

        });
        addPanel.add(addButton);



        addDialog.setContentPane(addPanel);
        addDialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
        addDialog.setLocationRelativeTo(frame);
        addDialog.setVisible(true);
        return addDialog;


    }

    public void refreshList(){
        String[][] customers =  new Reader("src/customers.csv").read();
        customersList.setListData(DashBoard.updateList(new Reader("src/customers.csv").read()));
    }

}
