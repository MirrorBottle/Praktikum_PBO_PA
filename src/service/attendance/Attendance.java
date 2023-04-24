package service.attendance;

import service.Service;
import service.ServiceInterface;
import service.user.User;
import service.user.UserItem;

import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import java.sql.SQLException;
import java.util.ArrayList;

import helper.*;
import java.util.Date;
import java.text.ParseException;
import java.text.SimpleDateFormat;

public class Attendance implements ServiceInterface {
  public static String TABLE = "attendances";
  public static String[] HEADERS = { "ID", "Karyawan", "Status", "Waktu", "Catatan" };

  public static void list() throws IOException, SQLException {
    Helper.banner("Daftar Absensi");
    ArrayList<ArrayList<String>> result = Query
        .select("SELECT attendances.*, users.username FROM attendances JOIN users ON attendances.user_id=users.id ORDER BY id DESC");
    ArrayList<ArrayList<String>> attendances = new ArrayList<>();
    result.forEach(row -> {
      AttendanceItem attendance = new AttendanceItem(row);
      attendances.add(attendance.admin());
    });
    Table table = Query.datatable(HEADERS, attendances);
    table.print();
    Helper.keypress();
  };

  public static void create() throws IOException, NoSuchAlgorithmException, SQLException {
    while (true) {
      Helper.banner("Buat Absensi Baru");
      System.out.println(Helper.color("[INPUT ADMIN] akan dimasukkan ke dalam catatan secara otomatis!", "info"));
      UserItem user = User.find();
      System.out.println("Karyawan: " + user.username);
      String attendance_at = Helper.input("Masukkan Waktu (yyyy-mm-dd hh:mm): ", "datetime");
      String status = Helper.input("Masukkan status (1 = Tepat Waktu, 2 = Terlambat): ", "required");
      String note = Helper.input("Masukkan catatan: ");
      note = "[INPUT ADMIN] " + note;
      attendance_at = attendance_at + ":00";
      Query.store(
          TABLE,
          new String[] { "user_id", "status", "attendance_at", "note" },
          new String[] { user.id, status, attendance_at, note });
      Helper.keypress("success", "Absensi berhasil ditambah!");
      break;
    }
  };

  public static void edit() throws IOException {
    Helper.banner("Ubah Absensi");
    Helper.keypress();

  };

  public static void delete() throws IOException {
    Helper.banner("Hapus Absensi");
    Helper.keypress();
  };

  public static void today() throws IOException, SQLException, NoSuchAlgorithmException {
    Helper.banner("Absensi Hari Ini");
    ArrayList<ArrayList<String>> users = Query.select(
        "SELECT users.id, users.username, IF((SELECT COUNT(*) FROM attendances WHERE attendances.user_id=users.id AND DATE(attendances.attendance_at) = CURDATE() > 0), 'HADIR', 'TIDAK HADIR') AS status FROM users WHERE users.role = 'user'");
    Table table = Query.datatable(new String[] { "ID", "Username", "Status Kehadiran" }, users);
    table.print();
    Helper.keypress();
  }

  public static void present() throws IOException, SQLException, NoSuchAlgorithmException, ParseException {
    Helper.banner("Presensi Absensi");

    ArrayList<String> absent = Query.find("attendances",
        String.format("WHERE user_id=%s AND DATE(attendance_at) = CURDATE()", Service.authId));

    if (absent.isEmpty()) {
      String waktu = Helper.waktu();
      String hour = Helper.waktu("HH:mm");

      SimpleDateFormat sdf = new SimpleDateFormat("hh:mm");
      Date d1 = sdf.parse("07:00");
      Date d2 = sdf.parse(hour);
      String status = d2.getTime() - d1.getTime() > 0 ? "2" : "1";
      String statusName = status.equals("2") ? "Terlambat" : "Tepat Waktu";

      System.out.println("Waktu :" + waktu);
      System.out.println("Status Kehadiran: " + statusName);

      String noted = Helper.input("Masukkan Catatan (kosongkan apabila tidak ada): ");
      Query.store(
          TABLE,
          new String[] { "user_id", "status", "attendance_at", "note" },
          new String[] { Service.authId, status, waktu, noted });
      Helper.keypress("success", "Absensi berhasil!");
    } else {
      Helper.keypress("info", "Anda sudah absen untuk hari ini, selamat bekerja!");
    }
  };

  public static void history() throws IOException, SQLException {
    Helper.banner("History Absensi");
    String[] headers = { "ID", "Status", "Tanggal/Waktu", "Catatan" };

    ArrayList<ArrayList<String>> result = Query.select(
        "SELECT attendances.*, users.username FROM attendances JOIN users ON attendances.user_id=users.id WHERE attendances.user_id="
            + Service.authId + " ORDER BY id DESC");
    ArrayList<ArrayList<String>> attendances = new ArrayList<>();
    result.forEach(row -> {
      AttendanceItem attendance = new AttendanceItem(row);
      attendances.add(attendance.string());
    });
    Table table = Query.datatable(headers, attendances);
    table.print();
    Helper.keypress();
  };

  public static void index() throws IOException, SQLException, NoSuchAlgorithmException {
    boolean isRunning = true;

    while (isRunning) {
      Helper.banner("Manajemen Absensi");
      String choice = Helper
          .menus(new String[] { "Lihat Daftar Absensi", "Tambah Absensi", "Ubah Absensi",
              "Hapus Absensi", "Kembali" });
      switch (choice) {
        case "1":
          Attendance.list();
          break;
        case "2":
          Attendance.create();
          break;
        case "3":
          Attendance.edit();
          break;
        case "4":
          Attendance.delete();
          break;
        default:
          isRunning = false;
          break;
      }
    }
  };
}
