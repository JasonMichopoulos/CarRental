package gui;

import api.Car;
import filemanager.Reader;
import filemanager.UpdateCar;

import javax.swing.*;
import java.awt.*;
import java.util.Arrays;
import java.util.Objects;

public class CarsPanel extends JPanel {
    JList<String> CarsList = new JList<>();
    public CarsPanel(JFrame frame) {
        super();
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        setBackground(Color.DARK_GRAY);



        initUI(frame);
    }

    private void initUI(JFrame frame){
        JPanel CarsPanel = new JPanel(new BorderLayout());
        CarsPanel.setLayout(new BoxLayout(CarsPanel, BoxLayout.Y_AXIS));
        CarsPanel.setBackground(Color.DARK_GRAY);

        String[][] Cars = Car.allCars();

        String[] CarsData = new String[Cars.length];
        for (int i = 0; i < Cars.length; i++) {
            CarsData[i] = String.join(" | ", Cars[i]);
        }

        CarsList.setListData(CarsData);
        CarsList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

        JScrollPane CarsListPane = new JScrollPane(CarsList);

        JButton searchButton = new JButton("Search Cars");
        searchButton.setFont(new Font("Arial", Font.BOLD, 15));
        searchButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        searchButton.addActionListener(e -> {

            JDialog search = new JDialog(frame, "Search Cars", true);
            search.setSize(400, 400);
            search.setLocationRelativeTo(frame);
            search.setResizable(false);

            JPanel panel = new JPanel(new GridBagLayout());
            GridBagConstraints gbc = new GridBagConstraints();
            gbc.insets = new Insets(5, 5, 5, 5);
            gbc.fill = GridBagConstraints.HORIZONTAL;


            JTextField plateField = new JTextField(20);
            JTextField brandField = new JTextField(20);
            JTextField typeField = new JTextField(20);
            JTextField modelField = new JTextField(20);
            JTextField yearField = new JTextField(20);
            JTextField colorField = new JTextField(20);
            JComboBox<String> statusField = new JComboBox<>(new String[]{"Ναι", "Όχι"});



            int row = 0;

            gbc.gridx = 0; gbc.gridy = row;
            panel.add(new JLabel("Πινακίδα:"), gbc);
            gbc.gridx = 1;
            panel.add(plateField, gbc);
            row++;

            gbc.gridx = 0; gbc.gridy = row;
            panel.add(new JLabel("Μάρκα:"), gbc);
            gbc.gridx = 1;
            panel.add(brandField, gbc);
            row++;

            gbc.gridx = 0; gbc.gridy = row;
            panel.add(new JLabel("Τύπος:"), gbc);
            gbc.gridx = 1;
            panel.add(typeField, gbc);
            row++;

            gbc.gridx = 0; gbc.gridy = row;
            panel.add(new JLabel("Μοντέλο:"), gbc);
            gbc.gridx = 1;
            panel.add(modelField, gbc);
            row++;

            gbc.gridx = 0; gbc.gridy = row;
            panel.add(new JLabel("Έτος:"), gbc);
            gbc.gridx = 1;
            panel.add(yearField, gbc);
            row++;

            gbc.gridx = 0; gbc.gridy = row;
            panel.add(new JLabel("Χρώμα:"), gbc);
            gbc.gridx = 1;
            panel.add(colorField, gbc);
            row++;

            gbc.gridx = 0; gbc.gridy = row;
            panel.add(new JLabel("Κατάσταση:"), gbc);
            gbc.gridx = 1;
            panel.add(statusField, gbc);
            row++;

            // ---- SEARCH BUTTON ----
            JButton searchBtn = new JButton("Search");
            gbc.gridx = 0;
            gbc.gridy = row + 1;
            gbc.gridwidth = 2;
            gbc.fill = GridBagConstraints.CENTER;
            panel.add(searchBtn, gbc);
            searchBtn.addActionListener(e1 ->{
                String plate = plateField.getText();
                String brand = brandField.getText();
                String type = typeField.getText();
                String model = modelField.getText();
                String year = yearField.getText();
                String color = colorField.getText();
                String status = Objects.requireNonNull(statusField.getSelectedItem()).toString().toLowerCase();

                String[] criteria = new String[]{
                        plate,
                        brand,
                        type,
                        model,
                        year,
                        color,
                        status,
                };

                String[][] filtered_cars = Car.searchCars(new Reader("CarRental/src/vehicles_with_plates.csv").read(),criteria);

                CarsList.setListData(DashBoard.updateList(filtered_cars));

                for(String[] car : filtered_cars) {
                    System.out.println(Arrays.toString(car));
                }
                search.dispose();

            });

            search.add(panel);
            search.setVisible(true);


        });

        JButton Show_ALL = new JButton("Show All");
        Show_ALL.setFont(new Font("Arial", Font.BOLD, 15));
        Show_ALL.setAlignmentX(Component.CENTER_ALIGNMENT);
        Show_ALL.addActionListener(e -> {
            CarsList.setListData(DashBoard.updateList(new Reader("CarRental/src/vehicles_with_plates.csv").read()));
        });


        JButton add_CAR = new JButton("Add Car");
        add_CAR.setFont(new Font("Arial", Font.BOLD, 15));
        add_CAR.setAlignmentX(Component.CENTER_ALIGNMENT);
        add_CAR.addActionListener(e -> {
            JDialog addDialog = new JDialog(frame, "Add Car", true);
            addDialog.setSize(400, 400);
            addDialog.setLocationRelativeTo(frame);
            addDialog.setResizable(false);

            JPanel panel = new JPanel(new GridBagLayout());
            GridBagConstraints gbc = new GridBagConstraints();
            gbc.insets = new Insets(5, 5, 5, 5);
            gbc.fill = GridBagConstraints.HORIZONTAL;


            JTextField plateField = new JTextField(20);
            JTextField brandField = new JTextField(20);
            JTextField typeField = new JTextField(20);
            JTextField modelField = new JTextField(20);
            JTextField yearField = new JTextField(20);
            JTextField colorField = new JTextField(20);

            int row = 0;

            gbc.gridx = 0; gbc.gridy = row;
            panel.add(new JLabel("Πινακίδα:"), gbc);
            gbc.gridx = 1;
            panel.add(plateField, gbc);
            row++;

            gbc.gridx = 0; gbc.gridy = row;
            panel.add(new JLabel("Μάρκα:"), gbc);
            gbc.gridx = 1;
            panel.add(brandField, gbc);
            row++;

            gbc.gridx = 0; gbc.gridy = row;
            panel.add(new JLabel("Τύπος:"), gbc);
            gbc.gridx = 1;
            panel.add(typeField, gbc);
            row++;

            gbc.gridx = 0; gbc.gridy = row;
            panel.add(new JLabel("Μοντέλο:"), gbc);
            gbc.gridx = 1;
            panel.add(modelField, gbc);
            row++;

            gbc.gridx = 0; gbc.gridy = row;
            panel.add(new JLabel("Έτος:"), gbc);
            gbc.gridx = 1;
            panel.add(yearField, gbc);
            row++;

            gbc.gridx = 0; gbc.gridy = row;
            panel.add(new JLabel("Χρώμα:"), gbc);
            gbc.gridx = 1;
            panel.add(colorField, gbc);
            row++;


            JButton addButton = new JButton("Add");
            gbc.gridx = 0;
            gbc.gridy = row + 1;
            gbc.gridwidth = 2;
            gbc.fill = GridBagConstraints.CENTER;
            addButton.addActionListener(e1 -> {
                String[] car = new String[]{
                        plateField.getText(),
                        brandField.getText(),
                        typeField.getText(),
                        modelField.getText(),
                        yearField.getText(),
                        colorField.getText(),
                };
                boolean canAdd = true;
                for(int i = 0; i < car.length; i++){
                    if(car[i]==null || car[i].isEmpty()){
                        canAdd = false;
                        break;
                    }
                }
                if(!canAdd){
                    JOptionPane.showMessageDialog(addDialog, "Συμπληρωσε ολα τα κενα και βεβαιωσου οτι τα στοιχεια ειναι σωστα!");
                }else{
                    int car_ID = Car.getNextID();
                    if(Car.addCar(new Car(
                            car_ID,
                            car[0],
                            car[1],
                            car[2],
                            car[3],
                            car[4],
                            car[5],
                            true
                    ))){
                        JOptionPane.showMessageDialog(addDialog,"Το αυτοκινητο καταχωρηθηκε επιτυχως με id: " + car_ID);
                        CarsList.setListData(DashBoard.updateList(new Reader("CarRental/src/vehicles_with_plates.csv").read()));
                        addDialog.dispose();
                    }else{
                        JOptionPane.showMessageDialog(addDialog, "To αυτοκινητο υπαρχει ηδη στη λιστα");

                    }
                }

            });


            panel.add(addButton, gbc);

            addDialog.add(panel);
            addDialog.setVisible(true);
        });

        JButton editButton = new JButton("Edit");
        editButton.setFont(new Font("Arial", Font.BOLD, 14));
        editButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        editButton.addActionListener(e1 -> {
            if(CarsList.getSelectedValue()==null){
                JOptionPane.showMessageDialog(frame,"Choose a car to edit");
            }else{
                String[] car = CarsList.getSelectedValue().split("\\|");
                int car_id = Integer.parseInt(car[0].trim());
                JDialog editDialog = new JDialog();
                editDialog.setSize(300,150);
                editDialog.setResizable(false);
                editDialog.setTitle("EDIT");

                JPanel editPanel = new JPanel();
                editPanel.setLayout(new BoxLayout(editPanel, BoxLayout.Y_AXIS));




                JComboBox whatToEdit = new JComboBox(new String[]{"Brand","Type","Model","Year","Color"});
                whatToEdit.setAlignmentX(Component.CENTER_ALIGNMENT);
                whatToEdit.setMaximumSize(new Dimension(150, whatToEdit.getPreferredSize().height));


                JTextField new_item = new JTextField(20);
                new_item.setAlignmentX(Component.CENTER_ALIGNMENT);
                new_item.setMaximumSize(new Dimension(150, new_item.getPreferredSize().height));


                JButton done = new JButton("Done");
                done.setFont(new Font("Arial", Font.BOLD, 14));
                done.setAlignmentX(Component.CENTER_ALIGNMENT);
                done.setMaximumSize(new Dimension(150, done.getPreferredSize().height));
                done.addActionListener(e -> {
                    String toBeEddited =  Objects.requireNonNull(whatToEdit.getSelectedItem()).toString();
                    String new_item_text = new_item.getText();
                    if(new_item_text.isEmpty()){
                        JOptionPane.showMessageDialog(editDialog,"Give text to edit");

                    }else{
                        UpdateCar.editCars(car_id,toBeEddited,new_item_text);
                        CarsList.setListData(DashBoard.updateList(Car.allCars()));
                        editDialog.dispose();
                    }

                });

                editPanel.add(Box.createVerticalStrut(10));
                editPanel.add(whatToEdit);
                editPanel.add(Box.createVerticalStrut(10));
                editPanel.add(new_item);
                editPanel.add(Box.createVerticalStrut(10));
                editPanel.add(done);



                editDialog.setContentPane(editPanel);
                editDialog.setModal(true);
                editDialog.setLocationRelativeTo(frame);
                editDialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
                editDialog.setVisible(true);

            }


        });

        JButton changeStatus = new JButton("Change Status");
        changeStatus.setFont(new Font("Arial", Font.BOLD, 14));
        changeStatus.setAlignmentX(Component.CENTER_ALIGNMENT);
        changeStatus.addActionListener(e1 -> {
            if(CarsList.getSelectedValue() == null){
                JOptionPane.showMessageDialog(frame,"Choose a car to change status");
            }else {
                String[] car = CarsList.getSelectedValue().split("\\|");
                int car_id = Integer.parseInt(car[0].trim());
                String status = car[7].trim();
                boolean changedStatus;
                if (status.equalsIgnoreCase("Διαθέσιμο")) {
                    changedStatus = false;
                } else {
                    changedStatus = true;
                }
                UpdateCar.carAvailable(car_id, changedStatus);
                CarsList.setListData(DashBoard.updateList(Car.allCars()));
            }
        });




        CarsPanel.add(searchButton);
        CarsPanel.add(Show_ALL);
        CarsPanel.add(add_CAR);
        CarsPanel.add(editButton);
        CarsPanel.add(changeStatus);
        CarsPanel.add(CarsListPane);

        this.add(CarsPanel);
    }

    public void refreshCarsList() {
        String[][] allCars = new Reader("CarRental/src/vehicles_with_plates.csv").read();
        CarsList.setListData(DashBoard.updateList(allCars));
    }

}
