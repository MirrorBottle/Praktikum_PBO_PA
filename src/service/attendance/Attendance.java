package service.attendance;
import service.Service;
import service.ServiceInterface;

import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import java.sql.SQLException;
import java.time.LocalTime;

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

  public static void present() throws IOException, SQLException, NoSuchAlgorithmException {
    Helper.banner("Presensi Kehadiran");
    String waktu = Helper.waktu();
    System.out.println("Waktu :" + waktu );

    String noted = Helper.insert("Masukkan Catatan:"); 
    String status = "1";
    Query.store( 
      "attendances",
      new String[]{"user_id","status","attendance_at","note"},
      new String[]{Service.authId,status,waktu,noted}
    );
    System.out.println("Absensi Berhasil");
    Helper.keypress();
  };

  public static void history() throws IOException {
    Helper.banner("Histori Kehadiran");
    Helper.keypress();
  };
}
