package gui;

import api.Car;
import api.Customer;
import api.Employee;
import api.Rental;
import filemanager.Paths;
import filemanager.Reader;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Arrays;
import java.util.Date;

public class RentsPanel extends JPanel {

    private JList<String> carsList = new JList<>();
    private JList<String> customersList = new JList<>();
    private final String carPath = Paths.getCarPath();
    private final String customersPath = Paths.getCustomersPath();
    private final String rentPath = Paths.getRentsPath();
    private final String[] allAvalailableCars = new String[]{
            "",
            "",
            "",
            "",
            "",
            "",
            "ναι"
    };
    private final String[] allUnavailableCars = new String[]{
            "",
            "",
            "",
            "",
            "",
            "",
            "οχι"
    };


    public RentsPanel(JFrame frame){
        super();
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        setBackground(Color.DARK_GRAY);



        initUI(frame);
    }

    private void initUI(JFrame frame){
        JPanel rentPanel = new JPanel();

        rentPanel.setLayout(new BoxLayout(rentPanel, BoxLayout.Y_AXIS));
        rentPanel.setBackground(Color.DARK_GRAY);
        rentPanel.setBorder(BorderFactory.createEmptyBorder(10,10,10,10));

        String[][] all_cars = new Reader(carPath).read();


        carsList.setListData(DashBoard.updateList(Car.searchCars(all_cars, allAvalailableCars)));
        carsList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        carsList.setPreferredSize(new Dimension(150,carsList.getPreferredSize().height));

        customersList.setListData(DashBoard.updateList(new Reader(customersPath).read()));
        customersList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        customersList.setPreferredSize(new Dimension(150,customersList.getPreferredSize().height));

        this.add(searchCarButton(frame));
        this.add(ViewCarHistoryButton(frame));
        this.add(ViewUnavailableCarsButton(frame));
        this.add(new JScrollPane(carsList));
        this.add(searchCustomersButton(frame));
        this.add(customersHistoryButton(frame));
        this.add(new JScrollPane(customersList));
        this.add(rentButton(frame));
        this.add(rentPanel);

    }

    private JButton searchCarButton(JFrame frame){
        JButton search = new JButton("Search Cars");
        search.setAlignmentX(Component.CENTER_ALIGNMENT);
        search.setFont(new Font("Arial",Font.BOLD,15));
        search.setFocusable(false);
        search.addActionListener(e -> {
            searchCarDialog(frame);
        });
        return search;
    }

    private JButton ViewCarHistoryButton(JFrame frame){
        JButton history = new JButton("View Car History");
        history.setAlignmentX(Component.CENTER_ALIGNMENT);
        history.setFont(new Font("Arial",Font.BOLD,15));
        history.setFocusable(false);
        history.addActionListener(e -> {
            String selectedCar =  carsList.getSelectedValue();
            if(selectedCar == null){
                JOptionPane.showMessageDialog(frame,"Παρακαλώ επιλέξτε αυτοκίνητο");
                return;
            }
            String[] carData = selectedCar.trim().split("\\|");
            Car car = new Car(
                    Integer.parseInt(carData[0].trim()),
                    carData[1].trim(),
                    carData[2].trim(),
                    carData[3].trim(),
                    carData[4].trim(),
                    carData[5].trim(),
                    carData[6].trim(),
                    carData[7].trim().equalsIgnoreCase("Διαθέσιμο")
            );
            carHistoryDialog(frame,car);
        });
        return history;
    }

    private JButton ViewUnavailableCarsButton(JFrame frame){
        JButton unavailable = new JButton("View Unavailable");
        unavailable.setAlignmentX(Component.CENTER_ALIGNMENT);
        unavailable.setFont(new Font("Arial",Font.BOLD,15));
        unavailable.setFocusable(false);
        unavailable.addActionListener(e -> {
            ViewUnavailableDialog(frame);
        });
        return unavailable;
    }

    private JButton searchCustomersButton(JFrame frame){
        JButton search = new JButton("Search Customers");
        search.setAlignmentX(Component.CENTER_ALIGNMENT);
        search.setFont(new Font("Arial",Font.BOLD,15));
        search.setFocusable(false);
        search.addActionListener(e -> {
            searchCustomerDialog(frame);
        });
        return search;
    }

    private JButton customersHistoryButton(JFrame frame){
        JButton history = new JButton("View Customer History");
        history.setAlignmentX(Component.CENTER_ALIGNMENT);
        history.setFont(new Font("Arial",Font.BOLD,15));
        history.setFocusable(false);
        history.addActionListener(e -> {
            String selected =  customersList.getSelectedValue();
            if(selected == null){
                JOptionPane.showMessageDialog(frame,"Επιλέξτε πελάτη");
                return;
            }
            String[] selectedData = selected.trim().split("\\|");
            String[] customerData = new String[]{
                selectedData[0].trim(),
                selectedData[1].trim(),
                selectedData[2].trim(),
                selectedData[3].trim(),
            };
            Customer customer = new Customer(
                    customerData[0].trim(),
                    customerData[1].trim(),
                    customerData[2].trim(),
                    customerData[3].trim()
            );
            customerHistoryDialog(frame,customer);
        });
        return history;
    }

    private JButton rentButton(JFrame frame){
        JButton rent = new JButton("Rent");
        rent.setAlignmentX(Component.CENTER_ALIGNMENT);
        rent.setFont(new Font("Arial",Font.BOLD,15));
        rent.setFocusable(false);
        rent.addActionListener(e -> {
            String carselect =  carsList.getSelectedValue();
            String customerselect = customersList.getSelectedValue();
            if(carselect == null || customerselect == null){
                JOptionPane.showMessageDialog(frame, "Παρακαλώ επιλέξτε πελάτη και αυτοκίνητο για να συνεχιστεί η διαδικασία");
                return;
            }
            rentDialog(frame,carselect,customerselect);

        });
        return rent;
    }


    private JDialog searchCarDialog(JFrame frame){
        JDialog dialog = new JDialog(frame,"Search Cars",true);
        dialog.setLayout(new BoxLayout(dialog,BoxLayout.Y_AXIS));
        dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
        dialog.setSize(300,400);
        dialog.setResizable(false);
        dialog.setLocationRelativeTo(frame);

        JPanel searchPanel = new JPanel();
        searchPanel.setLayout(new BoxLayout(searchPanel,BoxLayout.Y_AXIS));
        searchPanel.setBorder(BorderFactory.createEmptyBorder(10,10,10,10));




        JTextField plateField = DialogFields();
        JTextField brandField = DialogFields();
        JTextField typeField = DialogFields();
        JTextField modelField = DialogFields();
        JTextField yearField = DialogFields();
        JTextField colorField = DialogFields();



        searchPanel.add(DialogLabels("Πινακίδα"));
        searchPanel.add(plateField);
        searchPanel.add(Box.createVerticalStrut(10));

        searchPanel.add(DialogLabels("Μάρκα"));
        searchPanel.add(brandField);
        searchPanel.add(Box.createVerticalStrut(10));

        searchPanel.add(DialogLabels("Τύπος"));
        searchPanel.add(typeField);
        searchPanel.add(Box.createVerticalStrut(10));

        searchPanel.add(DialogLabels("Μοντέλο"));
        searchPanel.add(modelField);
        searchPanel.add(Box.createVerticalStrut(10));

        searchPanel.add(DialogLabels("Χρονολογία"));
        searchPanel.add(yearField);
        searchPanel.add(Box.createVerticalStrut(10));

        searchPanel.add(DialogLabels("Χρώμα"));
        searchPanel.add(colorField);
        searchPanel.add(Box.createVerticalStrut(10));

        JButton goButton = new JButton("Go");
        goButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        goButton.setFont(new Font("Arial",Font.BOLD,15));
        goButton.setFocusable(false);
        goButton.addActionListener(e -> {
            String[] criteria = new String[]{
                    plateField.getText().trim(),
                    brandField.getText().trim(),
                    typeField.getText().trim(),
                    modelField.getText().trim(),
                    yearField.getText().trim(),
                    colorField.getText().trim(),
                    "ναι"
            };

            boolean atLeastOneFilled = false;
            for(int i = 0; i < criteria.length - 1; i++){
                if(criteria[i] != null && !criteria[i].trim().isEmpty()){
                    atLeastOneFilled = true;
                    break;
                }
            }
            if(!atLeastOneFilled){
                carsList.setListData(DashBoard.updateList(Car.searchCars(new Reader(carPath).read(), criteria)));
                JOptionPane.showMessageDialog(dialog,"Εμφανίστηκαν ολα τα διαθέσιμα αυτοκίνητα");
            }
            else{
                carsList.setListData(DashBoard.updateList(Car.searchCars(new Reader(carPath).read(), criteria)));
                JOptionPane.showMessageDialog(dialog,"Η λιστα ενημερώθηκε");
            }


        });
        searchPanel.add(goButton);

        dialog.setContentPane(searchPanel);

        dialog.setVisible(true);
        return dialog;
    }


    private JDialog searchCustomerDialog(JFrame frame){
        JDialog dialog = new JDialog(frame,"Search Customers",true);
        dialog.setLayout(new BoxLayout(dialog,BoxLayout.Y_AXIS));
        dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
        dialog.setSize(300,400);
        dialog.setResizable(false);
        dialog.setLocationRelativeTo(frame);

        JPanel searchPanel = new JPanel();
        searchPanel.setLayout(new BoxLayout(searchPanel,BoxLayout.Y_AXIS));
        searchPanel.setBorder(BorderFactory.createEmptyBorder(10,10,10,10));

        JTextField afmTextField  = DialogFields();
        JTextField nameTextField = DialogFields();
        JTextField surnameTextField = DialogFields();
        JTextField phoneTextField = DialogFields();
        JTextField emailTextField = DialogFields();

        searchPanel.add(DialogLabels("ΑΦΜ"));
        searchPanel.add(afmTextField);
        searchPanel.add(Box.createVerticalStrut(10));

        searchPanel.add(DialogLabels("Όνομα"));
        searchPanel.add(nameTextField);
        searchPanel.add(Box.createVerticalStrut(10));

        searchPanel.add(DialogLabels("Επώνυμο"));
        searchPanel.add(surnameTextField);
        searchPanel.add(Box.createVerticalStrut(10));

        searchPanel.add(DialogLabels("Τηλέφωνο"));
        searchPanel.add(phoneTextField);
        searchPanel.add(Box.createVerticalStrut(10));

        searchPanel.add(DialogLabels("Email"));
        searchPanel.add(emailTextField);
        searchPanel.add(Box.createVerticalStrut(10));

        JButton goButton = new JButton("Go");
        goButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        goButton.setFont(new Font("Arial",Font.BOLD,15));
        goButton.setFocusable(false);
        goButton.addActionListener(e -> {
            String[] criteria = new String[]{
                    afmTextField.getText().trim(),
                    nameTextField.getText().trim(),
                    surnameTextField.getText().trim(),
                    phoneTextField.getText().trim(),
                    emailTextField.getText().trim()
            };

            boolean filledBlanks = false;
            for(int i = 0; i < criteria.length - 1; i++){
                if(criteria[i] != null && !criteria[i].trim().isEmpty()){
                    filledBlanks = true;
                    break;
                }
            }
            if(!filledBlanks){
                customersList.setListData(DashBoard.updateList(new Reader(customersPath).read()));
                JOptionPane.showMessageDialog(dialog,"Εμφανίστηκαν όλοι οι πελάτες");
            }else{
                customersList.setListData(DashBoard.updateList(Customer.searchCustomer(new Reader(customersPath).read(),criteria)));
                JOptionPane.showMessageDialog(dialog,"Η λίστα ενημερώθηκαν");
            }

        });
        searchPanel.add(goButton);


        dialog.setContentPane(searchPanel);
        dialog.setVisible(true);

        return dialog;
    }


    private JDialog rentDialog(JFrame frame,String car, String customer){
        JDialog dialog = new JDialog(frame,"Rent",true);
        dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
        dialog.setSize(300,400);
        dialog.setResizable(false);
        dialog.setLocationRelativeTo(frame);

        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel,BoxLayout.Y_AXIS));
        panel.setBorder(BorderFactory.createEmptyBorder(10,10,10,10));

        String[] carData = car.trim().split("\\|");
        Car rentedCar = new Car(
                Integer.parseInt(carData[0].trim()),
                carData[1].trim(),
                carData[2].trim(),
                carData[3].trim(),
                carData[4].trim(),
                carData[5].trim(),
                carData[6].trim(),
                carData[7].trim().equalsIgnoreCase("Διαθέσιμο")
        );
        String[] customerData = customer.trim().split("\\|");
        Customer rentCustomer = new Customer(
                customerData[0].trim(),
                customerData[1].trim(),
                customerData[2].trim(),
                customerData[3].trim()
        );
        JLabel carLabel = new JLabel("CAR");
        carLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        carLabel.setFont(new Font("Arial",Font.BOLD,25));


        panel.add(carLabel);
        panel.add(DialogLabels(rentedCar.getBrand() + " " + rentedCar.getModel() + " " + rentedCar.getYear()));
        panel.add(DialogLabels(rentedCar.getPlate()));
        panel.add(Box.createVerticalStrut(10));

        JLabel customerLabel = new JLabel("Customer");
        customerLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        customerLabel.setFont(new Font("Arial",Font.BOLD,25));

        panel.add(customerLabel);
        panel.add(DialogLabels(rentCustomer.getFullname()));
        panel.add(DialogLabels("ΑΦΜ: " + rentCustomer.getAFM()));
        panel.add(Box.createVerticalStrut(20));

        panel.add(DialogLabels("Ημερομηνία λήξης"));

        panel.add(Box.createVerticalStrut(10));

        SpinnerDateModel model =  new SpinnerDateModel();
        JSpinner dateSpinner = new JSpinner(model);
        dateSpinner.setEditor(new JSpinner.DateEditor(dateSpinner, "dd-MM-yyyy"));
        dateSpinner.setPreferredSize(new Dimension(150,dateSpinner.getPreferredSize().height));
        dateSpinner.setMaximumSize(dateSpinner.getPreferredSize());
        panel.add(dateSpinner);

        panel.add(Box.createVerticalStrut(20));

        JButton done = new JButton("Done");
        done.setAlignmentX(Component.CENTER_ALIGNMENT);
        done.setFont(new Font("Arial",Font.BOLD,15));
        done.setFocusable(false);
        done.setPreferredSize(new Dimension(150,done.getPreferredSize().height));
        done.setMaximumSize(done.getPreferredSize());
        done.addActionListener(e -> {
            Date selectedDate = (Date) dateSpinner.getValue();
            LocalDate EndDate = selectedDate.toInstant()
                    .atZone(ZoneId.systemDefault())
                    .toLocalDate();
            LocalDate dateNow = LocalDate.now();

            String[] getUsername = frame.getTitle().split("\\|");
            String username = getUsername[1].trim();
            Employee employee = Employee.getEmployeeByUsername(username);
            if(EndDate.isAfter(dateNow)) {
                Rental rental = new Rental(rentedCar,rentCustomer,EndDate,employee);
                rental.Rent();
                JOptionPane.showMessageDialog(frame,"Η Ενοικιαση εγινε με επιτυχια");
                carsList.setListData(DashBoard.updateList(Car.searchCars(new Reader(carPath).read(),allAvalailableCars)));
                dialog.dispose();
            }else{
                JOptionPane.showMessageDialog(frame,"Δεν γινεται η ημερομηνια ληξης να ειναι μικροτερη η ιση με την σημερινη");
            }


        });
        panel.add(done);

        dialog.setContentPane(panel);
        dialog.setVisible(true);
        return dialog;
    }

    private JDialog carHistoryDialog(JFrame frame,Car car){
        JDialog dialog = new JDialog(frame,"Car History",true);
        dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
        dialog.setSize(700,300);
        dialog.setResizable(false);
        dialog.setLocationRelativeTo(frame);

        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel,BoxLayout.Y_AXIS));
        panel.setBorder(BorderFactory.createEmptyBorder(10,10,10,10));

        JList<String> carHistoryList = new JList<>();
        String[][] CarHistory = Car.ViewHistory(car);
        carHistoryList.setListData(DashBoard.updateList(CarHistory));
        panel.add(new JScrollPane(carHistoryList));


        dialog.setContentPane(panel);
        dialog.setVisible(true);
        return dialog;
    }

    private JDialog customerHistoryDialog(JFrame frame, Customer customer){
        JDialog dialog = new JDialog(frame,"Customer History",true);
        dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
        dialog.setSize(700,300);
        dialog.setResizable(false);
        dialog.setLocationRelativeTo(frame);

        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel,BoxLayout.Y_AXIS));
        panel.setBorder(BorderFactory.createEmptyBorder(10,10,10,10));

        JList<String> customerHistoryList = new JList<>();
        String[][] CustomerHistory = Customer.ViewHistory(customer);
        customerHistoryList.setListData(DashBoard.updateList(CustomerHistory));

        JButton returnButton = new JButton("Return");
        returnButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        returnButton.setFont(new Font("Arial",Font.BOLD,15));
        returnButton.setFocusable(false);
        returnButton.setPreferredSize(new Dimension(150,returnButton.getPreferredSize().height));
        returnButton.addActionListener(e -> {
            String selected = customerHistoryList.getSelectedValue();
            if(selected==null){
                JOptionPane.showMessageDialog(dialog,"Επιλέξτε όχημα για επσιτροφή");
                return;
            }
            selected = selected.trim();
            String[] data = selected.trim().split("\\|");
            data = new String[]{
                data[0].trim(), // UUID
                data[1].trim(), // Pinakida
                data[2].trim(), // Customer
                data[3].trim(), // Start date
                data[4].trim(), // End date
                data[5].trim(), // Employee
                data[6].trim(), // Epistrafike
            };
            Car car = Car.getCarByPlate(data[1]);
            boolean done = Car.carReturn(car);
            if(done){
                JOptionPane.showMessageDialog(dialog,"Το αυτοκινητο επιστραφηκε");
                Rental.Returned(car);
                carsList.setListData(DashBoard.updateList(Car.searchCars(Car.allCars(),allAvalailableCars)));
                dialog.dispose();
            }
            else{
                JOptionPane.showMessageDialog(dialog,"Το αυτοκινητο δεν μπορεσε να επιστραφει");
            }
        });



        JButton okay = new JButton("OK");
        okay.setAlignmentX(Component.CENTER_ALIGNMENT);
        okay.setFont(new Font("Arial",Font.BOLD,25));
        okay.setFocusable(false);
        okay.setPreferredSize(new Dimension(150,okay.getPreferredSize().height));
        okay.addActionListener(e -> {dialog.dispose();});

        panel.add(returnButton);
        panel.add(new JScrollPane(customerHistoryList));
        panel.add(okay);

        dialog.setContentPane(panel);
        dialog.setVisible(true);
        return dialog;
    }

    private JDialog ViewUnavailableDialog(JFrame frame){
        JDialog dialog = new JDialog(frame,"Unavailable cars",true);
        dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
        dialog.setSize(500,300);
        dialog.setResizable(false);
        dialog.setLocationRelativeTo(frame);

        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel,BoxLayout.Y_AXIS));
        panel.setBorder(BorderFactory.createEmptyBorder(10,10,10,10));

        JButton ViewCarHistoryButton = ViewCarHistoryButton(frame);

        JList<String> UnavailableCarsList = new JList<>();
        UnavailableCarsList.setListData(DashBoard.updateList(Car.searchCars(Car.allCars(),allUnavailableCars)));
        UnavailableCarsList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);


        for(ActionListener al : ViewCarHistoryButton.getActionListeners()){
            ViewCarHistoryButton.removeActionListener(al);
        }
        ViewCarHistoryButton.addActionListener(e -> {
            String selected =  UnavailableCarsList.getSelectedValue();
            if(selected == null){
                JOptionPane.showMessageDialog(frame,"Παρακαλώ επιλέξτε αυτοκίνητο");
                return;
            }
            String[] carData = selected.trim().split("\\|");
            Car car = new Car(
                    Integer.parseInt(carData[0].trim()),
                    carData[1].trim(),
                    carData[2].trim(),
                    carData[3].trim(),
                    carData[4].trim(),
                    carData[5].trim(),
                    carData[6].trim(),
                    carData[7].trim().equalsIgnoreCase("Διαθέσιμο")
            );
            carHistoryDialog(frame,car);

        });

        JButton returnButton = new JButton("Return");
        returnButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        returnButton.setFont(new Font("Arial",Font.BOLD,14));
        returnButton.setFocusable(false);
        returnButton.addActionListener(e -> {
            String selected = UnavailableCarsList.getSelectedValue();
            if(selected == null){
                JOptionPane.showMessageDialog(dialog,"Επιλέξτε όχημα");
                return;
            }
            String[] selectedData = selected.trim().split("\\|");
            Car car = Car.getCarByPlate(selectedData[1].trim());
            boolean done = Car.carReturn(car);

            if(done){
                Rental.Returned(car);
                UnavailableCarsList.setListData(DashBoard.updateList(Car.searchCars(Car.allCars(),allUnavailableCars)));
                carsList.setListData(DashBoard.updateList(Car.searchCars(Car.allCars(),allAvalailableCars)));
                JOptionPane.showMessageDialog(dialog,"Το αυτοκίνητο επιστράφηκε");
            }else{
                JOptionPane.showMessageDialog(dialog,"Το αυτοκίνητο δεν μπόρεσε να επιστραφεί");
            }
        });


        JButton searchButton = new JButton("Search");
        searchButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        searchButton.setFont(new Font("Arial",Font.BOLD,14));
        searchButton.setFocusable(false);
        searchButton.addActionListener(e -> {
            JDialog dialog1 = new JDialog(frame,"Search",true);
            dialog1.setSize(300,500);
            dialog1.setResizable(false);
            dialog1.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
            dialog1.setLocationRelativeTo(frame);

            JPanel panel1 = new JPanel();
            panel1.setLayout(new BoxLayout(panel1,BoxLayout.Y_AXIS));
            panel1.setBorder(BorderFactory.createEmptyBorder(10,10,10,10));

            JTextField plateField = DialogFields();
            JTextField brandField = DialogFields();
            JTextField typeField = DialogFields();
            JTextField modelField = DialogFields();
            JTextField yearField = DialogFields();
            JTextField colorField = DialogFields();

            panel1.add(DialogLabels("Πινακίδα"));
            panel1.add(plateField);
            panel1.add(Box.createVerticalStrut(10));

            panel1.add(DialogLabels("Μάρκα"));
            panel1.add(brandField);
            panel1.add(Box.createVerticalStrut(10));

            panel1.add(DialogLabels("Τύπος"));
            panel1.add(typeField);
            panel1.add(Box.createVerticalStrut(10));

            panel1.add(DialogLabels("Μοντέλο"));
            panel1.add(modelField);
            panel1.add(Box.createVerticalStrut(10));

            panel1.add(DialogLabels("Χρονολογία"));
            panel1.add(yearField);
            panel1.add(Box.createVerticalStrut(10));

            panel1.add(DialogLabels("Χρώμα"));
            panel1.add(colorField);
            panel1.add(Box.createVerticalStrut(10));

            JButton searchButton1 = new JButton("Search");
            searchButton1.setAlignmentX(Component.CENTER_ALIGNMENT);
            searchButton1.setFont(new Font("Arial",Font.BOLD,14));
            searchButton1.setFocusable(false);
            searchButton1.addActionListener(e1 -> {
                String[] criteria = new String[]{
                    plateField.getText().trim(),
                    brandField.getText().trim(),
                    typeField.getText().trim(),
                    modelField.getText().trim(),
                    yearField.getText().trim(),
                    colorField.getText().trim(),
                    "οχι"
                };
                boolean atLeastOneFilled = false;
                String[][] cars = Car.allCars();
                for(int i = 0; i < criteria.length - 1; i++){
                    if(!criteria[i].isEmpty()){
                        atLeastOneFilled = true;
                    }
                }
                if(!atLeastOneFilled){
                    JOptionPane.showMessageDialog(dialog1,"Εμφανίστηκαν όλα τα αυτοκίνητα");
                    carsList.setListData(DashBoard.updateList(Car.searchCars(Car.allCars(),allAvalailableCars)));
                    UnavailableCarsList.setListData(DashBoard.updateList(Car.searchCars(cars,allUnavailableCars)));
                    return;
                }



                JOptionPane.showMessageDialog(dialog1,"Η λιστα ενημερώθηκε");
                UnavailableCarsList.setListData(DashBoard.updateList(Car.searchCars(cars,criteria)));


            });
            panel1.add(searchButton1);


            dialog1.setContentPane(panel1);
            dialog1.setVisible(true);
        });


        JButton okay = new JButton("OK");
        okay.setAlignmentX(Component.CENTER_ALIGNMENT);
        okay.setFont(new Font("Arial",Font.BOLD,15));
        okay.setFocusable(false);
        okay.setPreferredSize(new Dimension(150,okay.getPreferredSize().height));
        okay.setMaximumSize(okay.getPreferredSize());
        okay.addActionListener(e -> {dialog.dispose();});

        panel.add(searchButton);
        panel.add(Box.createVerticalStrut(5));
        panel.add(ViewCarHistoryButton);
        panel.add(Box.createVerticalStrut(5));
        panel.add(returnButton);
        panel.add(Box.createVerticalStrut(5));
        panel.add(new JScrollPane(UnavailableCarsList));
        panel.add(okay);
        dialog.setContentPane(panel);
        dialog.setVisible(true);
        return dialog;
    }


    private JLabel DialogLabels(String labelName){
        JLabel label = new JLabel();
        label.setText(labelName);
        label.setFont(new Font("Arial",Font.BOLD,15));
        label.setAlignmentX(Component.CENTER_ALIGNMENT);
        return label;
    }



    private JTextField DialogFields(){
        JTextField field = new JTextField();
        field.setAlignmentX(Component.CENTER_ALIGNMENT);
        field.setPreferredSize(new Dimension(150,field.getPreferredSize().height));
        field.setFont(new Font("Arial",Font.BOLD,15));
        return field;
    }

    public void refreshLists(){
        String[][] cars = new Reader(carPath).read();
        carsList.setListData(DashBoard.updateList(Car.searchCars(cars,allAvalailableCars)));
        String[][] customers = new Reader(customersPath).read();
        customersList.setListData(DashBoard.updateList(customers));
    }
}
