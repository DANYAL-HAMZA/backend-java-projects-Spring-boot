package org.example.BackendLogic;


    import java.sql.*;

public class DrugManager {
       // private static final String JDBC_URL = "jdbc:derby:pharmacyDB";
    private static final String jdbc_url = "jdbc:mysql://localhost:3306/students";
    private static final String user = "root";
    private static final String password = "epiSode1";

        public static void addDrug(String name, int supplierID, int stock, double price) {
            String insertSQL = "INSERT INTO Drugs (Name, SupplierID, Stock, Price) VALUES (?, ?, ?, ?)";

            try {
                Connection conn = DriverManager.getConnection(jdbc_url,user,password);

                 Class.forName("com.mysql.cj.jdbc.Driver");

                 PreparedStatement pstmt = conn.prepareStatement(insertSQL);

                pstmt.setString(1, name);
                pstmt.setInt(2, supplierID);
                pstmt.setInt(3, stock);
                pstmt.setDouble(4, price);
                pstmt.executeUpdate();

                System.out.println("Drug added successfully!");

            } catch (SQLException e) {
                e.printStackTrace();
            } catch (ClassNotFoundException e) {
                throw new RuntimeException(e);
            }
        }

    public static void purchaseDrug(int purchaseId, int drugId,String purchaseDate, int quantity,
                                    double totalAmount, String buyerName) {
        String insertSQL = "INSERT INTO PurchaseHistory (PurchaseId, DrugID, PurchaseDate, Quantity,TotalAmount,BuyerName) VALUES (?, ?, ?, ?,?,?)";

        try {
            Connection conn = DriverManager.getConnection(jdbc_url,user,password);

            Class.forName("com.mysql.cj.jdbc.Driver");

            PreparedStatement pstmt = conn.prepareStatement(insertSQL);

            pstmt.setInt(1, purchaseId);
            pstmt.setInt(2, drugId);
            pstmt.setString(3, purchaseDate);
            pstmt.setInt(4, quantity);
            pstmt.setDouble(5, totalAmount);
            pstmt.setString(6, buyerName);


            pstmt.executeUpdate();

            System.out.println("Drug purchased successfully!");

        } catch (SQLException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }



    public static void searchDrug(String name) {
                String querySQL = "SELECT * FROM Drugs WHERE Name LIKE ?";

                try (Connection conn = DriverManager.getConnection(jdbc_url,user,password);
                     PreparedStatement pstmt = conn.prepareStatement(querySQL)) {

                    pstmt.setString(1, "%" + name + "%");
                    ResultSet rs = pstmt.executeQuery();

                    while (rs.next()) {
                        System.out.println("DrugID: " + rs.getInt("DrugID"));
                        System.out.println("Name: " + rs.getString("Name"));
                        System.out.println("SupplierID: " + rs.getInt("SupplierID"));
                        System.out.println("Stock: " + rs.getInt("Stock"));
                        System.out.println("Price: " + rs.getDouble("Price"));
                        System.out.println("-----------------------");
                    }

                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }




