package enigmacamp.submission.javadb;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class TransactionQuery {
    public static void insertQuery(Transactions transactions) throws SQLException {
        Connection connection = ConnectionUtil.getDataSource().getConnection();

        String insertQuery = """
                INSERT INTO transactions (bill_no, branch_code, transaction_date, type) VALUES
                (?, ?, ?, ?)
                """;

        PreparedStatement statement = connection.prepareStatement(insertQuery);
        statement.setString(1, transactions.getBillNo());
        statement.setString(2, transactions.getBranchNo());
        statement.setString(3, transactions.getTransactionDate());
        statement.setString(4, transactions.getTypeTransaction());

        int insert = statement.executeUpdate();
        if (insert == 1){
            System.out.println("Add data success");
        }

        statement.close();
        connection.close();
    }

    public static void updateQuery(Transactions transactions) throws SQLException {
        Connection connection = ConnectionUtil.getDataSource().getConnection();

        String insertQuery = """
                UPDATE transactions SET branch_code = ?, transaction_date = ?, type = ?
                WHERE bill_no = ?
                """;

        PreparedStatement statement = connection.prepareStatement(insertQuery);
        statement.setString(1, transactions.getBranchNo());
        statement.setString(2, transactions.getTransactionDate());
        statement.setString(3, transactions.getTypeTransaction());
        statement.setString(4, transactions.getBillNo());

        int update = statement.executeUpdate();
        if (update == 1){
            System.out.println("Update data success");
        }

        statement.close();
        connection.close();
    }
}
