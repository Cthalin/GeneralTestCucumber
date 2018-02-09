package stepDefinition;

import java.sql.*;

public class DatabaseSteps {

//    private String username = "cpadmin";
//    private String password = "N6RVvvqFPF";
    private String username = "test_automation";
    private String password = "yurcEub{Frold1";
    private String dbUrl = "jdbc:mysql://88.99.243.137:3306/cp_export";

    public Connection establishDbConnection() throws ClassNotFoundException, SQLException {
        Class.forName("com.mysql.cj.jdbc.Driver");
        Connection con = DriverManager.getConnection(dbUrl,username,password);
        return con;
    }

    public void createStatementForChecksum(Connection con, String shopId) throws SQLException {
        //create statement
        Statement stmt = con.createStatement();
        ResultSet rs = stmt.executeQuery("select *  from export_feeds where shop_id="+shopId+";");
        long checksum;
        if (!rs.isBeforeFirst() ) {
            System.out.println("No data");
        } else {
            rs.first();
            checksum = rs.getLong("checksum");
           String chk = String.valueOf(checksum);
           System.out.println(checksum);
//           System.out.println(chk);
        }
    }

    public int createStatementForItemCount(Connection con, String shopId) throws SQLException {
        //create statement
        Statement stmt = con.createStatement();
        ResultSet rs = stmt.executeQuery("select *  from export_feeds where shop_id="+shopId+";");
        int itemCount=0;
        if (!rs.isBeforeFirst() ) {
            System.out.println("No data");
        } else {
            rs.first();
            itemCount = rs.getInt("offers_count");
//            String chk = String.valueOf(itemCount);
            System.out.println(itemCount);
//            System.out.println(chk);
        }
        return itemCount;
    }

}
