package helper;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

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

  public static String color(String label, String type) throws IOException {
    String words = label;
    switch (type) {
      case "success":
        words = ANSI_GREEN + label + ANSI_RESET;
        break;
      case "error":
        words = ANSI_RED + label + ANSI_RESET;
        break;
      case "warning":
        words = ANSI_YELLOW + label + ANSI_RESET;
        break;
      case "info":
        words = ANSI_CYAN + label + ANSI_RESET;
        break;
    }
    return words;
  }

  public static String input(String label) throws IOException {
    System.out.print(label);
    String val = br.readLine();
    return val;
  }

  public static String input(String label, String type) throws IOException {
    String labelColored = Helper.color("Apakah anda yakin (y/n): ", type);
    System.out.print(labelColored);
    String val = br.readLine();
    return val;
  }

  public static Boolean confirm() throws IOException {
    String val = Helper.input("Apakah anda yakin (y/n)", "warning");
    return val.equals("y");
  }

  public static void keypress() throws IOException {
    System.out.print("Tekan untuk melanjutkan . . . ");
    br.readLine();
  }

  public static void keypress(String type) throws IOException {
    String words = "";
    switch (type) {
      case "success":
        words = "Berhasil! Tekan untuk melanjutkan . . . ";
        break;
      case "error":
        words = "Gagal! Tekan untuk melanjutkan . . . ";
        break;
    }
    words = Helper.color(words, type);
    System.out.println(words);
    br.readLine();
  }

  public static void keypress(String type, String message) throws IOException {
    String labelColored = Helper.color(message, type);
    System.out.print(labelColored);
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
      choice = Helper.input("Masukkan pilihan: ");
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

  public static String waktu() throws IOException {
    DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss \n");
    LocalDateTime getNow = LocalDateTime.now();
    String waktu = dateTimeFormatter.format(getNow);
    return waktu;
  }

  public static String waktu(String format) throws IOException {
    DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern(format);
    LocalDateTime getNow = LocalDateTime.now();
    String waktu = dateTimeFormatter.format(getNow);
    return waktu;
  }

  public static String format(LocalDate date, String format) {
    return DateTimeFormatter.ofPattern(format).format(date);
  }

  public static String format(String date) {
    LocalDate parsedDate = LocalDate.parse(date);
    return DateTimeFormatter.ofPattern("dd/MM/yy").format(parsedDate);
  }
}