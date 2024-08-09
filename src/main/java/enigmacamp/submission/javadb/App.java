package enigmacamp.submission.javadb;

import java.sql.*;
import java.util.Scanner;

public class App {
    private static final Scanner scanner = new Scanner(System.in);

    public static void main( String[] args ) {
        while (true) {
            System.out.println("1. Insert Transaction");
            System.out.println("2. Update Transaction");
            System.out.println("3. Show Branch Code");
            System.out.println("4. Show Product");
            System.out.println("5. Show Transaction");
            System.out.println("6. Show All Transaction");
            System.out.println("7. Exit");
            System.out.print("Choose number: ");
            int index = scanner.nextInt();
            scanner.nextLine();

            try {
                switch (index) {
                    case 1:
                        insertData();
                        break;
                    case 2:
                        updateData();
                        break;
                    case 3:
                        getBranch();
                        break;
                    case 4:
                        getProduct();
                        break;
                    case 5:
                        getTransaction();
                        break;
                    case 6:
                        getAll();
                        break;
                    case 7:
                        System.exit(0);
                        break;
                    default:
                        System.out.println("Pilihan tidak valid");
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }

            System.out.println();

            try {
                Thread.sleep(2000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    private static void insertData() throws SQLException{
        Transactions transactions = new Transactions();
        System.out.print("Bill Number: ");
        transactions.setBillNo(scanner.nextLine());
        System.out.print("Transaction Date (YYYY-MM-DD): ");
        transactions.setTransactionDate(scanner.nextLine());
        System.out.print("Branch Code: ");
        transactions.setBranchNo(scanner.nextLine());
        System.out.print("Order Type (EAT_IN, TAKE_AWAY, ONLINE): ");
        transactions.setTypeTransaction(scanner.nextLine());

        TransactionQuery.insertQuery(transactions);
    }

    private static void updateData() throws SQLException{
        Transactions transactions = new Transactions();
        System.out.print("Bill Number: ");
        transactions.setBillNo(scanner.nextLine());
        System.out.print("Transaction Date (YYYY-MM-DD): ");
        transactions.setTransactionDate(scanner.nextLine());
        System.out.print("Branch Code: ");
        transactions.setBranchNo(scanner.nextLine());
        System.out.print("Order Type (EAT_IN, TAKE_AWAY, ONLINE): ");
        transactions.setTypeTransaction(scanner.nextLine());

        TransactionQuery.updateQuery(transactions);
    }

    private static void getBranch() throws SQLException{
        Connection connection = ConnectionUtil.getDataSource().getConnection();
        Statement statement = connection.createStatement();

        String sql = """
                SELECT * FROM branches ORDER BY branch_no;
                """;

        ResultSet resultSet = statement.executeQuery(sql);

        System.out.println("Code     Branch Name");
        System.out.println("==========================");
        while (resultSet.next()){
            String code = resultSet.getString("branch_no");
            String name = resultSet.getString("branch_name");

            System.out.printf("%-8s %-20s\n", code, name);
        }

        resultSet.close();
        statement.close();
        connection.close();
    }

    private static void getProduct() throws SQLException{
        Connection connection = ConnectionUtil.getDataSource().getConnection();
        Statement statement = connection.createStatement();

        String sql = """
                SELECT * FROM products ORDER BY id;
                """;

        ResultSet resultSet = statement.executeQuery(sql);

        System.out.println("Code     Product Name         Price");
        System.out.println("======================================");
        while (resultSet.next()){
            String id = resultSet.getString("id");
            String name = resultSet.getString("product");
            Integer price = resultSet.getInt("price");

            System.out.printf("%-8s %-20s %6d\n", id, name, price);
        }

        resultSet.close();
        statement.close();
        connection.close();
    }

    private static void getTransaction() throws SQLException{
        Connection connection = ConnectionUtil.getDataSource().getConnection();
        Statement statement = connection.createStatement();

        String sql = """
                SELECT * FROM transactions ORDER BY bill_no;
                """;

        ResultSet resultSet = statement.executeQuery(sql);

        System.out.println("Bill            Branch   Transaction Date   Type");
        System.out.println("========================================================");
        while (resultSet.next()){
            String bill = resultSet.getString("bill_no");
            String branch = resultSet.getString("branch_code");
            String date = resultSet.getString("transaction_date");
            String type = resultSet.getString("type");

            System.out.printf("%-15s %-8s %-18s %-10s\n", bill, branch, date, type);
        }

        resultSet.close();
        statement.close();
        connection.close();
    }

    private static void getAll() throws SQLException{
        Connection connection = ConnectionUtil.getDataSource().getConnection();
        Statement statement = connection.createStatement();

        String sql = """
                SELECT t.transaction_date, t.bill_no, b.branch_no, b.branch_name, t.type, dt.product_no, p.product, dt.quantity, p.price, dt.total_price FROM detail_transactions dt
                JOIN submission_javadb.transactions t on t.bill_no = dt.bill_no
                JOIN submission_javadb.branches b on b.branch_no = t.branch_code
                JOIN submission_javadb.products p on p.id = dt.product_no
                ORDER BY t.transaction_date;
                """;

        ResultSet resultSet = statement.executeQuery(sql);

        System.out.println("Tanggal         Bill           Branch No   Branch Name      Type      Product Code    Product Name           Qty   Price  Total");
        System.out.println("===============================================================================================================================");
        while (resultSet.next()){
            String date = resultSet.getString("transaction_date");
            String bill = resultSet.getString("bill_no");
            String branchId = resultSet.getString("branch_no");
            String branchName = resultSet.getString("branch_name");
            String type = resultSet.getString("type");
            String productCode = resultSet.getString("product_no");
            String product = resultSet.getString("product");
            Integer quantity = resultSet.getInt("quantity");
            Integer price = resultSet.getInt("price");
            Integer total = resultSet.getInt("total_price");

            System.out.printf(
                    "%-15s %-15s %-10s %-15s %-10s %-15s %-20s %5d %6d %6d\n",
                    date, bill, branchId, branchName, type, productCode, product, quantity, price, total
            );
        }

        resultSet.close();
        statement.close();

        System.out.println();

        String[] types = {"EAT_IN", "TAKE_AWAY", "ONLINE"};
        for (String t : types) {
            String totalQuery = """
                    SELECT SUM(dt.total_price) as total FROM detail_transactions dt
                    JOIN submission_javadb.transactions t on t.bill_no = dt.bill_no
                    WHERE t.type = ?;
                    """;

            PreparedStatement preparedStatement = connection.prepareStatement(totalQuery);
            preparedStatement.setString(1, t);
            ResultSet totalResultSet = preparedStatement.executeQuery();

            while (totalResultSet.next()) {
                int total = totalResultSet.getInt("total");
                System.out.printf("%-10s : %d\n", t, total);
            }

            totalResultSet.close();
            preparedStatement.close();
        }

        connection.close();
    }
}
