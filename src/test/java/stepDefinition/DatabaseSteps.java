package stepDefinition;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class DatabaseSteps {

    private String username = "cpadmin";
    private String password = "N6RVvvqFPF";
    private String dbUrl = "jdbc:mysql://88.99.243.137:3306/cp_export";

    public void establishDbConnection() throws ClassNotFoundException, SQLException {
        Class.forName("com.mysql.jdbc.Driver");
        Connection con = DriverManager.getConnection(dbUrl,username,password);

        //create statement
        Statement stmt = con.createStatement();
        stmt.executeQuery("select *  from employee;");

    }

}
