package helper;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.sql.DriverManager;
import java.sql.Connection;
import java.sql.Statement;
import java.sql.ResultSet;

public final class Query {
  static final BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

  static final String JDBC_DRIVER = "com.mysql.cj.jdbc.Driver";
  static final String DB_URL = "jdbc:mysql://localhost/s4_siapp";
  static final String USER = "root";
  static final String PASS = "";

  static Connection conn;
  static Statement stmt;
  static ResultSet rs;

  public static void init() {
    try {
      Class.forName(JDBC_DRIVER);
      conn = DriverManager.getConnection(DB_URL, USER, PASS);
    } catch (Exception e) {
      e.printStackTrace();
    }
  }

  public static Table datatable(String[] headers) {
    Table table = new Table();
    table.setShowVerticalLines(true);//if false (default) then no vertical lines are shown
    table.setHeaders(headers);//optional - if not used then there will be no header and horizontal lines
    table.addRow("super", "broccoli", "flexible");
    table.addRow("assumption", "announcement", "reflection");
    table.addRow("logic", "pleasant", "wild");
    return table;
  }
}