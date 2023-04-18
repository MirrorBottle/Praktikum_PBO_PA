package service.attendance;
import service.Service;
import service.ServiceInterface;

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

  public static void index() throws IOException {
    Helper.banner("Manajemen Absensi");
    Helper.keypress();
  };

  public static void show() throws IOException {
    Helper.banner("Daftar Absensi");
    Helper.keypress();
  };

  public static void show(String id) {

  };

  public static void create() throws IOException {
    Helper.banner("Buat Absensi Baru");
    Helper.keypress();

  };

  public static void edit() throws IOException {
    Helper.banner("Ubah Absensi");
    Helper.keypress();


  };

  public static void delete() throws IOException {
    Helper.banner("Hapus Absensi");
    Helper.keypress();
  };

  public static void today() throws IOException, SQLException, NoSuchAlgorithmException  {
    Helper.banner("Absensi Hari Ini");
    ArrayList<ArrayList<String>> users = Query.select("SELECT users.id, users.username, IF((SELECT COUNT(*) FROM attendances WHERE attendances.user_id=users.id AND DATE(attendances.attendance_at) = CURDATE() > 0), 'HADIR', 'TIDAK HADIR') AS status FROM users WHERE users.role = 'user'");
    Table table = Query.datatable(new String[] { "ID", "Username", "Status Kehadiran" }, users);
    table.print();
    Helper.keypress();
  }

  public static void present() throws IOException, SQLException, NoSuchAlgorithmException, ParseException {
    Helper.banner("Presensi Absensi");

    ArrayList<String> absent = Query.find("attendances", String.format("WHERE user_id=%s AND DATE(attendance_at) = CURDATE()", Service.authId));

    if(absent.isEmpty()) {
      String waktu = Helper.waktu();
      String hour = Helper.waktu("HH:mm");

      SimpleDateFormat sdf = new SimpleDateFormat("hh:mm");
      Date d1 = sdf.parse("07:00");
      Date d2 = sdf.parse(hour);

      System.out.println(d2.getTime() - d1.getTime());
      System.out.println("Waktu :" + waktu );
  
      String noted = Helper.input("Masukkan Catatan: "); 
      String status = d2.getTime() - d1.getTime() > 0 ? "2" : "1";
      Query.store( 
        TABLE,
        new String[]{"user_id","status","attendance_at","note"},
        new String[]{Service.authId,status,waktu,noted}
      );
      Helper.keypress("success", "Absensi berhasil!");
    } else {
      Helper.keypress("info", "Anda sudah absen untuk hari ini, selamat bekerja!");
    }
  };

  public static void history() throws IOException, SQLException {
    Helper.banner("History Absensi");
    String[] headers = { "ID", "Status", "Tanggal/Waktu" };

    ArrayList<ArrayList<String>> result = Query.select("SELECT attendances.*, users.username FROM attendances JOIN users ON attendances.user_id=users.id WHERE attendances.user_id=" + Service.authId);
    ArrayList<ArrayList<String>> attendances = new ArrayList<>();
    result.forEach(row -> {
      AttendanceItem attendance = new AttendanceItem(row);
      attendances.add(attendance.string());
    });
    Table table = Query.datatable(headers, attendances);
    table.print();
    Helper.keypress();
  };
}
