package org.example.UserInterface;
    import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

    public class SearchDrugFrame extends JFrame {
        private static final String jdbc_url = "jdbc:mysql://localhost:3306/students";
        private static final String user = "root";
        private static final String password = "epiSode1";
        public SearchDrugFrame() {
            setTitle("Search Drug");
            setSize(400, 300);
            setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
            setLocationRelativeTo(null);

            Container container = getContentPane();
            container.setLayout(new GridLayout(3, 2));

            JLabel nameLabel = new JLabel("Drug Name:");
            final JTextField nameField = new JTextField();
            final JTextArea resultArea = new JTextArea();
            resultArea.setEditable(false);

            JButton searchButton = new JButton("Search");

            container.add(nameLabel);
            container.add(nameField);
            container.add(searchButton);
            container.add(new JScrollPane(resultArea));

            searchButton.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    String name = nameField.getText();
                    resultArea.setText("");
                    searchDrug(name, resultArea);
                }
            });

            setVisible(true);
        }

        private void searchDrug(String name, JTextArea resultArea) {

            String querySQL = "SELECT * FROM Drugs WHERE Name LIKE ?";

            try (Connection conn = DriverManager.getConnection(jdbc_url,user,password);
                 PreparedStatement pstmt = conn.prepareStatement(querySQL)) {

                pstmt.setString(1, "%" + name + "%");
                ResultSet rs = pstmt.executeQuery();

                while (rs.next()) {
                    resultArea.append("DrugID: " + rs.getInt("DrugID") + "\n");
                    resultArea.append("Name: " + rs.getString("Name") + "\n");
                    resultArea.append("SupplierID: " + rs.getInt("SupplierID") + "\n");
                    resultArea.append("Stock: " + rs.getInt("Stock") + "\n");
                    resultArea.append("Price: " + rs.getDouble("Price") + "\n");
                    resultArea.append("-----------------------\n");
                }

            } catch (SQLException e) {
                e.printStackTrace();
                JOptionPane.showMessageDialog(null, "Error searching for drug!");
            }
        }
    }


