package service.attendance;
import service.ServiceInterface;

import java.io.IOException;

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

  public static void history() throws IOException {
    Helper.banner("Histori Kehadiran");
    Helper.keypress();
  };
}
