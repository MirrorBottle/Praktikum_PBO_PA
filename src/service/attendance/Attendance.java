package service.attendance;
import service.Service;
import service.ServiceInterface;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;

import helper.*;
public class Attendance implements ServiceInterface {
  public String table = "attendances";

  public static void index() throws IOException {
    Helper.banner("Manajemen Kehadiran");
    Helper.keypress();
  };

  public static void show() throws IOException {
    Helper.banner("Daftar Kehadiran");
    Helper.keypress();
  };

  public static void show(String id) {

  };

  public static void create() throws IOException {
    Helper.banner("Buat Kehadiran Baru");
    Helper.keypress();

  };

  public static void edit() throws IOException {
    Helper.banner("Ubah Kehadiran");
    Helper.keypress();


  };

  public static void delete() throws IOException {
    Helper.banner("Hapus Kehadiran");
    Helper.keypress();
  };

  public static void present() throws IOException {
    Helper.banner("Presensi Kehadiran");
    Helper.keypress();
  };

  public static void history() throws IOException, SQLException {
    Helper.banner("History Kehadiran");
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
