package org.example.UserInterface;

import org.example.BackendLogic.DrugManager;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Date;

public class PurchaseFrame extends JFrame {
        public PurchaseFrame() {
            setTitle("Purchase Drug");
            setSize(400, 300);
            setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
            setLocationRelativeTo(null);

            Container container = getContentPane();
            container.setLayout(new GridLayout(5, 2));

            JLabel purchaseLabel = new JLabel("PurchaseId:");
            final JTextField purchaseIdField = new JTextField();

            JLabel drugIdLabel = new JLabel("DrugID:");
            final JTextField drugIdField = new JTextField();

            JLabel stockLabel = new JLabel("PurchaseDate:");
            final JTextField purchaseDateField = new JTextField();

            JLabel quantityLabel = new JLabel("Quantity:");
            final JTextField quantityField = new JTextField();

            JLabel amountLabel = new JLabel("TotalAmount:");
            final JTextField totalAmountField = new JTextField();

            JLabel buyerNameLabel = new JLabel("BuyerName:");
            final JTextField buyerNameField = new JTextField();


            JButton addButton = new JButton("Purchase Drug");

            container.add(purchaseIdField);
            container.add(drugIdField);
            container.add(purchaseDateField);
            container.add(quantityField);
            container.add(totalAmountField);
            container.add(buyerNameField);
            container.add(addButton);

            addButton.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    int purchaseId = Integer.parseInt(purchaseIdField.getText());
                    int drugId = Integer.parseInt(drugIdField.getText());
                    String purchaseDate =  purchaseDateField.getText();
                    int quantity = Integer.parseInt(quantityField.getText());
                    double totalAmount = Double.parseDouble(totalAmountField.getText());
                    String buyerName = buyerNameField.getText();



                    DrugManager.purchaseDrug(purchaseId, drugId, purchaseDate,quantity,totalAmount,buyerName);
                    JOptionPane.showMessageDialog(null, "Drug added successfully!");
                    dispose();
                }
            });

            setVisible(true);
        }
    }




