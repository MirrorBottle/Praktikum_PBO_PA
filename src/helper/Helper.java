package helper;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

import java.sql.DriverManager;
import java.sql.Connection;
import java.sql.Statement;
import java.sql.ResultSet;

public final class Helper {
  static final BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

  static final String JDBC_DRIVER = "com.mysql.cj.jdbc.Driver";
  static final String DB_URL = "jdbc:mysql://localhost/s4_siapp";
  static final String USER = "root";
  static final String PASS = "";

  static Connection conn;
  static Statement stmt;
  static ResultSet rs;
  public static void flush() {
    System.out.print("\033[H\033[2J");
    System.out.flush();
  }
  public static void banner() {
    flush();
    System.out.println("  ____  _                   ");
    System.out.println(" / ___|(_) __ _ _ __  _ __  ");
    System.out.println(" \\___ \\| |/ _` | '_ \\| '_ \\ ");
    System.out.println("  ___) | | (_| | |_) | |_) |");
    System.out.println(" |____/|_|\\__,_| .__/| .__/ ");
    System.out.println("               |_|   |_|    ");
    System.out.println("\nAplikasi Absensi dan Shift");
  }

  public static String insert(String label) throws IOException {
    System.out.print(label);
    String val = br.readLine();
    return val;
  }

  public static void keypress() throws IOException {
    System.out.print("Tekan untuk melanjutkan . . . ");
    br.readLine();
  }

  public static void init() {
    try {
      Class.forName(JDBC_DRIVER);
      conn = DriverManager.getConnection(DB_URL, USER, PASS);
    } catch (Exception e) {
      e.printStackTrace();
    }
  }
}