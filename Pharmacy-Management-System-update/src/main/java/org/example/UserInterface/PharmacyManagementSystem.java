package org.example.UserInterface;

import javax.swing.*;

    import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

    public class PharmacyManagementSystem extends JFrame {
        public PharmacyManagementSystem() {
            setTitle("Pharmacy Management System");
            setSize(800, 600);
            setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            setLocationRelativeTo(null);

            Container container = getContentPane();
            container.setLayout(new BorderLayout());

            JPanel panel = new JPanel();
            panel.setLayout(new GridLayout(5, 1));

            JButton addDrugButton = new JButton("Add Drug");
            JButton searchDrugButton = new JButton("Search Drug");
            JButton viewDrugsButton = new JButton("View All Drugs");
            JButton viewPurchaseHistoryButton = new JButton("View Purchase History");
            JButton exitButton = new JButton("Exit");
            JButton purchaseDrugButton= new JButton("Purchase Drug");


            panel.add(addDrugButton);
            panel.add(purchaseDrugButton);
            panel.add(searchDrugButton);
            panel.add(viewDrugsButton);
            panel.add(viewPurchaseHistoryButton);
            panel.add(exitButton);

            container.add(panel, BorderLayout.CENTER);

            addDrugButton.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    new AddDrugFrame();
                }
            });

            purchaseDrugButton.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    new PurchaseFrame();
                }
            });

            searchDrugButton.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    new SearchDrugFrame();
                }
            });

            viewDrugsButton.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    new ViewDrugsFrame();
                }
            });

            viewPurchaseHistoryButton.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    new ViewPurchaseHistoryFrame();
                }
            });

            exitButton.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    System.exit(0);
                }
            });


            setVisible(true);
        }

        public static void main(String[] args) {
            new PharmacyManagementSystem();
        }
    }


