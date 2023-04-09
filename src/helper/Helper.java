package helper;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public final class Helper {
  public static final String ANSI_RESET = "\u001B[0m";
  public static final String ANSI_BLACK = "\u001B[30m";
  public static final String ANSI_RED = "\u001B[31m";
  public static final String ANSI_GREEN = "\u001B[32m";
  public static final String ANSI_YELLOW = "\u001B[33m";
  public static final String ANSI_BLUE = "\u001B[34m";
  public static final String ANSI_PURPLE = "\u001B[35m";
  public static final String ANSI_CYAN = "\u001B[36m";
  public static final String ANSI_WHITE = "\u001B[37m";

  static final BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

  public static void flush() {
    System.out.print("\033[H\033[2J");
    System.out.flush();
  }

  public static void banner(String label) {
    flush();
    System.out.println("  ____  _                   ");
    System.out.println(" / ___|(_) __ _ _ __  _ __  ");
    System.out.println(" \\___ \\| |/ _` | '_ \\| '_ \\ ");
    System.out.println("  ___) | | (_| | |_) | |_) |");
    System.out.println(" |____/|_|\\__,_| .__/| .__/ ");
    System.out.println("               |_|   |_|    ");
    System.out.println("\n" + label);
    System.out.println("+++++++++++++++++++++++++++++++++++");
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

  public static void keypress(String type) throws IOException {
    switch (type) {
      case "success":
        System.out.print(ANSI_GREEN + "Berhasil! Tekan untuk melanjutkan . . . " + ANSI_RESET);
        break;
      case "error":
        System.out.print(ANSI_RED + "Gagal! Tekan untuk melanjutkan . . . " + ANSI_RESET);
        break;
    }
    br.readLine();
  }

  public static String hash(String password) throws NoSuchAlgorithmException {
    MessageDigest md = MessageDigest.getInstance("MD5");
    md.update(password.getBytes());
    byte[] bytes = md.digest();
    StringBuilder sb = new StringBuilder();
    for (int i = 0; i < bytes.length; i++) {
      sb.append(Integer.toString((bytes[i] & 0xff) + 0x100, 16).substring(1));
    }
    String hashed = sb.toString();
    return hashed;
  }

  public static String menus(String[] menus) throws IOException {
    String choice = "";
    boolean isCorrect = false;
    while (!isCorrect) {
      int idx = 1;
      for (String menu : menus) {
        System.out.println(idx + ". " + menu);
        idx++;
      }
      choice = insert("Masukkan pilihan: ");
      isCorrect = Integer.parseInt(choice) <= menus.length && Integer.parseInt(choice) > 0;
      if (!isCorrect) {
        int lines = menus.length + 1;
        System.out.print(String.format("\033[%dA", lines)); // Move up
        System.out.print("\033[2K");
        System.out.flush();
      }
    }
    return choice;
  }
}