package helper;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.sql.DriverManager;
import java.sql.Connection;
import java.sql.Statement;
import java.util.ArrayList;

import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;

public final class Query {
  static final BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

  static final String JDBC_DRIVER = "com.mysql.cj.jdbc.Driver";
  static final String DB_URL = "jdbc:mysql://localhost/s4_siapp";
  static final String USER = "root";
  static final String PASS = "";

  static Connection conn;
  static Statement stmt;

  public static void init() {
    try {
      Class.forName(JDBC_DRIVER);
      conn = DriverManager.getConnection(DB_URL, USER, PASS);
    } catch (Exception e) {
      e.printStackTrace();
    }
  }

  public static ArrayList<String> transform(ResultSet res, Integer totalColumns) throws SQLException {
    ArrayList<String> data = new ArrayList<String>();
    for (int columnIdx = 1; columnIdx <= totalColumns; columnIdx++) {
      data.add(res.getString(columnIdx));
    }
    return data;
  }

  public static ArrayList<ArrayList<String>> select(String query) throws SQLException {
    stmt = conn.createStatement();
    ArrayList<ArrayList<String>> data = new ArrayList<ArrayList<String>>();
    ResultSet result = stmt.executeQuery(query);
    ResultSetMetaData rsmd = result.getMetaData();
    while (result.next()) {
      ArrayList<String> row = transform(result, rsmd.getColumnCount());
      data.add(row);
    }
    stmt.close();
    return data;
  }

  public static ArrayList<String> find(String table, Integer id) throws SQLException {
    ArrayList<ArrayList<String>> result = Query.select("SELECT * FROM " + table + " WHERE id=" + id);
    ArrayList<String> data = result.isEmpty() ?  new ArrayList<String>() : result.get(0);
    return data;
  }

  public static ArrayList<String> find(String table, String extraQuery) throws SQLException {
    ArrayList<ArrayList<String>> result = Query.select("SELECT * FROM " + table + " " + extraQuery);
    ArrayList<String> data = result.isEmpty() ?  new ArrayList<String>() : result.get(0);
    return data;
  }

  public static Table datatable(String[] headers) {
    Table table = new Table();
    table.setShowVerticalLines(true);// if false (default) then no vertical lines are shown
    table.setHeaders(headers);// optional - if not used then there will be no header and horizontal lines
    table.addRow("super", "broccoli", "flexible");
    table.addRow("assumption", "announcement", "reflection");
    table.addRow("logic", "pleasant", "wild");
    return table;
  }
}