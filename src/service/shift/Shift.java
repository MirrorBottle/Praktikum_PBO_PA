package service.shift;

import service.Service;
import service.ServiceInterface;
import service.user.User;
import service.user.UserItem;

import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import java.sql.SQLException;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.temporal.TemporalAdjusters;

import helper.*;

public class Shift implements ServiceInterface {
  public static String TABLE = "shifts";
  public static String[] HEADERS = { "ID", "Karyawan", "Hari", "Jam", "Berlaku Sampai" };

  public static void list() throws IOException, SQLException {
    Helper.banner("Daftar Shift");
    ArrayList<ArrayList<String>> result = Query
        .select("SELECT shifts.*, users.username FROM shifts JOIN users ON shifts.user_id=users.id ORDER BY id DESC");
    ArrayList<ArrayList<String>> shifts = new ArrayList<>();
    result.forEach(row -> {
      ShiftItem shift = new ShiftItem(row);
      shifts.add(shift.string());
    });
    Table table = Query.datatable(HEADERS, shifts);
    table.print();
    Helper.keypress();
  };

  public static Table weekly(LocalDate firstDayOfTheWeek, LocalDate lastDayOfTheWeek) throws IOException, SQLException {
    ArrayList<String> days = new ArrayList<String>(
        Arrays.asList("Senin", "Selasa", "Rabu", "Kamis", "Jumat", "Sabtu", "Minggu"));
    ArrayList<String> headers = new ArrayList<String>();
    headers.add("Jam");
    Table table = new Table();
    table.setShowVerticalLines(true);

    int idx = 0;
    for (String day : days) {
      headers.add(day + " (" + Helper.format(firstDayOfTheWeek.plusDays(idx), "dd/MM") + ")");
      idx++;
    }
    table.setHeaders(headers.stream().toArray(String[]::new));

    ArrayList<ArrayList<String>> shifts = Query.select(
        "SELECT shifts.*, users.username FROM shifts JOIN users ON shifts.user_id=users.id WHERE expired_at IS NULL OR expired_at > "
            + lastDayOfTheWeek);
    ArrayList<String> hours = new ArrayList<String>();
    for (ArrayList<String> shift : shifts) {
      hours.add(shift.get(3));
    }

    HashSet<String> hoursSet = new HashSet<String>(hours);
    hours = new ArrayList<String>(hoursSet);

    for (String hour : hours) {
      ArrayList<String> hourDays = new ArrayList<String>();
      hourDays.add(hour);
      // * GET WORKERS BY HOUR AND DAYS
      int dayIdx = 1;
      for (String day : days) {
        ArrayList<String> dayWorkers = new ArrayList<String>();
        for (ArrayList<String> shift : shifts) {
          if (shift.get(2).equals(Integer.toString(dayIdx)) && shift.get(3).equals(hour)) {
            dayWorkers.add(shift.get(5));
          }
          ;
        }
        hourDays.add(String.join(", ", dayWorkers));
        dayIdx++;
      }
      table.addRow(hourDays.stream().toArray(String[]::new));
      table.addRow(new String[] { "", "", "", "", "", "", "", "" });
    }
    return table;
  }

  public static void monthly() throws IOException, SQLException {
    Helper.banner("Shift (Bulanan)");
    LocalDate today = LocalDate.now();

    LocalDate firstMonday = today.with(TemporalAdjusters.firstInMonth(DayOfWeek.MONDAY));
    LocalDate secondMonday = firstMonday.plusDays(7);
    LocalDate thirdMonday = secondMonday.plusDays(7);
    LocalDate fourthMonday = thirdMonday.plusDays(7);

    LocalDate firstWeekEnd = firstMonday.plusDays(6);
    LocalDate secondWeekEnd = firstMonday.plusDays(6);
    LocalDate thirdWeekEnd = firstMonday.plusDays(6);
    LocalDate fourthWeekEnd = firstMonday.plusDays(6);

    Table firstWeek = Shift.weekly(firstMonday, firstWeekEnd);
    Table secondWeek = Shift.weekly(secondMonday, secondWeekEnd);
    Table thirdWeek = Shift.weekly(thirdMonday, thirdWeekEnd);
    Table fourthWeek = Shift.weekly(fourthMonday, fourthWeekEnd);

    System.out
        .println("Tgl. " + Helper.format(firstMonday, "dd/MM/yy") + " s/d " + Helper.format(fourthWeekEnd, "dd/MM/yy"));
    firstWeek.print();
    secondWeek.print();
    thirdWeek.print();
    fourthWeek.print();

    Helper.keypress();
  };

  public static void create() throws IOException, SQLException, NoSuchAlgorithmException {
    while (true) {
      Helper.banner("Buat Shift Baru");
      UserItem user = User.find();
      System.out.println("Karyawan: " + user.username);
      String day = Helper.input("Masukkan hari (1 = Senin ... 7 = Minggu): ", "required");
      String hour = Helper.input("Masukkan jam kerja (exa: 10:00 - 12:00): ", "time");
      String expired_at = Helper.input("Masukkan masa berlaku shift (yyyy-mm-dd) (kosongkan apabila seterusnya): ");

      if (Integer.parseInt(day) > 0 && Integer.parseInt(day) <= 7) {
        if (!expired_at.isEmpty()) {
          Pattern pattern = Pattern.compile("^\\d{4}-\\d{2}-\\d{2}$");
          Matcher matcher = pattern.matcher(expired_at);
          if (!matcher.matches()) {
            Helper.keypress("error", "Format masa berlaku tidak sesuai!");
            continue;
          }
        } else {
          expired_at = "NULL";
        }
        Query.store(
            TABLE,
            new String[] { "user_id", "day", "hour", "expired_at" },
            new String[] { user.id, day, hour, expired_at });
        Helper.keypress("success", "Shift berhasil ditambah!");
        break;
      }
      Helper.keypress("error", "Hari tidak ada!");
    }

  };

  public static void edit() throws IOException, SQLException, NoSuchAlgorithmException {
    while (true) {
      Helper.banner("Ubah Shift");

      String id = Helper.input("Masukkan ID Shift: ", "number");
      ArrayList<String> shift = Query.find(TABLE, Integer.parseInt(id));

      if (!shift.isEmpty()) {
        UserItem user = User.find();
        System.out.println("Karyawan: " + user.username);
        String day = Helper.input("Masukkan hari (1 = Senin ... 7 = Minggu): ", "required");
        String hour = Helper.input("Masukkan jam kerja (exa: 10:00 - 12:00): ", "time");
        String expired_at = Helper.input("Masukkan masa berlaku shift (yyyy-mm-dd) (kosongkan apabila seterusnya): ");

        if (Integer.parseInt(day) > 0 && Integer.parseInt(day) <= 7) {
          if (!expired_at.isEmpty()) {
            Pattern pattern = Pattern.compile("^\\d{4}-\\d{2}-\\d{2}$");
            Matcher matcher = pattern.matcher(expired_at);
            if (!matcher.matches()) {
              Helper.keypress("error", "Format masa berlaku tidak sesuai!");
              continue;
            }
          } else {
            expired_at = "NULL";
          }
          Query.update(
              TABLE,
              shift.get(0),
              new String[] { "user_id", "day", "hour", "expired_at" },
              new String[] { user.id, day, hour, expired_at });
          Helper.keypress("success", "Shift berhasil diubah!");
          break;
        }
        Helper.keypress("error", "Hari tidak ada!");
      } else {
        Helper.keypress("error", "Shift tidak ada!");
      }
    }

  };

  public static void delete() throws IOException, SQLException, NoSuchAlgorithmException {
    while (true) {
      Helper.banner("Hapus Shift");

      String id = Helper.input("Masukkan ID Shift: ", "number");
      ArrayList<String> shift = Query.find(TABLE, Integer.parseInt(id));

      if (!shift.isEmpty()) {
        Boolean isConfirmed = Helper.confirm();
        if (isConfirmed) {
          Query.delete(TABLE, shift.get(0));
          Helper.keypress("success", "Shift berhasil dihapus!");
        }
        break;
      } else {
        Helper.keypress("error", "Shift tidak ada!");
      }
    }
  };

  public static void user() throws IOException, SQLException {
    Helper.banner("Jadwal Shift");
    String[] headers = { "ID", "Hari", "Jam", "Berlaku Sampai" };
    ArrayList<ArrayList<String>> result = Query.select(
        "SELECT shifts.*, users.username FROM shifts JOIN users ON shifts.user_id=users.id WHERE shifts.user_id="
            + Service.authId + " ORDER BY id DESC");
    ArrayList<ArrayList<String>> shifts = new ArrayList<>();
    result.forEach(row -> {
      ShiftItem shift = new ShiftItem(row);
      shifts.add(shift.user());
    });
    Table table = Query.datatable(headers, shifts);
    table.print();
    Helper.keypress();
  };

  public static void index() throws IOException, SQLException, NoSuchAlgorithmException {
    boolean isRunning = true;

    while (isRunning) {
      Helper.banner("Manajemen Shift");
      String choice = Helper
          .menus(new String[] { "Lihat Daftar Shift", "Tambah Shift", "Ubah Shift",
              "Hapus Shift", "Kembali" });
      switch (choice) {
        case "1":
          Shift.list();
          break;
        case "2":
          Shift.create();
          break;
        case "3":
          Shift.edit();
          break;
        case "4":
          Shift.delete();
          break;
        default:
          isRunning = false;
          break;
      }
    }
  };
}
