package helper;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public final class Helper {
  static final BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

  // * UTILITY
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

  public static String menus(String[] menus) throws IOException {
    String choice = "";
    boolean isCorrect = false;
    while(!isCorrect) {
      int idx = 1;
      for (String menu : menus) {
          System.out.println(idx + ". " + menu);
          idx++;
      }
      choice = insert("Masukkan pilihan: ");
      isCorrect = Integer.parseInt(choice) <= menus.length && Integer.parseInt(choice) > 0;
      if(!isCorrect) {
        int lines =  menus.length + 1;
        System.out.print(String.format("\033[%dA", lines)); // Move up
        System.out.print("\033[2K");
        System.out.flush();
      }
    }
    return choice;
  }
}