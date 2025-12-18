package gui;

import api.Car;
import filemanager.Reader;
import filemanager.UpdateCar;

import javax.swing.*;
import java.awt.*;
import java.util.Arrays;
import java.util.Objects;


public class DashBoard {
    public DashBoard(String username){
        JFrame frame = new JFrame();
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setTitle("DashBoard | " + username);
        frame.setResizable(false);
        frame.setSize(1000,800);
        frame.setLayout(new BorderLayout());

        JTabbedPane tabbedPane = new JTabbedPane();




        tabbedPane.addChangeListener(e ->{
            int selectedIndex =  tabbedPane.getSelectedIndex();
            String selectedTitle = tabbedPane.getTitleAt(selectedIndex);
            switch (selectedTitle){
                case "Cars":
                    CarsPanel carsPanel = (CarsPanel) tabbedPane.getComponentAt(selectedIndex);
                    carsPanel.refreshCarsList();
                    break;
                case "Customer":
                    CustomerPanel customerPanel = (CustomerPanel) tabbedPane.getComponentAt(selectedIndex);
                    customerPanel.refreshList();
                    break;
                case "Rent":
                    RentsPanel rentsPanel = (RentsPanel) tabbedPane.getComponentAt(selectedIndex);
                    rentsPanel.refreshLists();
                    break;
                case "User":
                    UserPanel userPanel = (UserPanel) tabbedPane.getComponentAt(selectedIndex);
                    userPanel.refreshStats();
            }
        });


        tabbedPane.addTab("Cars",new CarsPanel(frame));
        tabbedPane.addTab("Customer",new CustomerPanel(frame));
        tabbedPane.addTab("Rent",new RentsPanel(frame));
        tabbedPane.addTab("User",new UserPanel(frame));
        frame.add(tabbedPane, BorderLayout.CENTER);


        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }



    public static String[] updateList(String[][] data){
        String[] formatted = new String[data.length];

        for (int i = 0; i < data.length; i++) {
            formatted[i] = String.join(" | ", data[i]);
        }

        return formatted;
    }


}
