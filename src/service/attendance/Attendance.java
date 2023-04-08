package service.attendance;
import service.ServiceInterface;

import java.io.IOException;

import helper.*;
public class Attendance implements ServiceInterface {
  public String table = "attendances";

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
}
