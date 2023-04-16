package service.shift;

import service.ServiceInterface;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;

import helper.*;

public class Shift implements ServiceInterface {
  public static String TABLE = "shifts";
  public static String[] HEADERS = { "ID", "Karyawan", "Hari", "Jam", "Berlaku Sampai" };

  public static void list() throws IOException, SQLException {
    Helper.banner("Daftar Shift");
    ArrayList<ArrayList<String>> result = Query.select("SELECT shifts.*, users.username FROM shifts JOIN users ON shifts.user_id=users.id");
    ArrayList<ArrayList<String>> shifts = new ArrayList<>();
    result.forEach(row -> {
      ShiftItem shift = new ShiftItem(row);
      shifts.add(shift.string());
    });
    Table table = Query.datatable(HEADERS, shifts);
    table.print();
    Helper.keypress();
  };

  public static void monthly() throws IOException {
    Helper.banner("Daftar Shift (Bulanan)");
    Helper.keypress();
  };

  public static void show() throws IOException {
    Helper.banner("Daftar Shift");
    Helper.keypress();
  };

  public static void show(String id) {

  };

  public static void create() throws IOException, SQLException {
    Helper.banner("Buat Shift Baru");
    Helper.keypress();

  };

  public static void edit() throws IOException {
    Helper.banner("Ubah Shift");
    Helper.keypress();


  };

  public static void delete() throws IOException {
    Helper.banner("Hapus Shift");
    Helper.keypress();
  };

  public static void user() throws IOException {
    Helper.banner("Jadwal Shift");
    Helper.keypress();
  };

  public static void index() throws IOException, SQLException {
    boolean isRunning = true;

    while (isRunning) {
      Helper.banner("Manajemen Shift");
      String choice = Helper
            .menus(new String[] { "Lihat Daftar Shift", "Lihat Shift (Bulanan)", "Tambah Shift", "Ubah Shift",
                "Hapus Shift", "Kembali" });
      switch (choice) {
        case "1":
            list();
          break;
      
        default:
          break;
      }
    }
  };
}
