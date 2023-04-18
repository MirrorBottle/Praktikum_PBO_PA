package service.attendance;
import service.Service;
import service.ServiceInterface;

import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import java.sql.SQLException;
import java.util.ArrayList;

import helper.*;
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

  public static void present() throws IOException, SQLException, NoSuchAlgorithmException {
    Helper.banner("Presensi Absensi");
    String waktu = Helper.waktu();
    System.out.println("Waktu :" + waktu );

    String noted = Helper.input("Masukkan Catatan:"); 
    String status = "1";
    Query.store( 
      TABLE,
      new String[]{"user_id","status","attendance_at","note"},
      new String[]{Service.authId,status,waktu,noted}
    );
    System.out.println("Absensi Berhasil");
    Helper.keypress();
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
