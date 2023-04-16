package service.leave;

import service.ServiceInterface;

import java.io.IOException;

import helper.*;

public class Leave implements ServiceInterface {
  public String table = "leaves";

  public static void index() throws IOException {
    Helper.banner("Manajemen Izin");
    Helper.keypress();
  };

  public static void show() throws IOException {
    Helper.banner("Daftar Izin");
    Helper.keypress();
  };

  public static void show(String id) {

  };

  public static void create() throws IOException {
    Helper.banner("Buat Izin Baru");
    String reason = Helper.insert("Masukkan alasan izin: ");
    String from_date = Helper.insert("Masukkan tanggal mulai izin : ");
    String until_date = Helper.insert("Masukkan tanggal akhir izin: ");
    Helper.keypress();

  };

  public static void edit() throws IOException {
    Helper.banner("Ubah Izin");
    Helper.keypress();

  };

  public static void delete() throws IOException {
    Helper.banner("Hapus Izin");
    Helper.keypress();
  };

  public static void history() throws IOException {
    Helper.banner("Histori Izin");
    Helper.keypress();
  };
}
