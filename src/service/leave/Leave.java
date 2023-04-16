package service.leave;

import service.Service;
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
    String from_date = Helper.insert("Masukkan tanggal mulai izin (y-m-d): ");
    String until_date = Helper.insert("Masukkan tanggal akhir izin (y-m-d): ");
    String created_at = Helper.waktu();
    
    Query.store( 
      "leave_requests",
      new String[]{"user_id","reason","status","from_date","until_date","created_at"},
      new String[]{Service.authId,reason,"1",from_date,until_date,created_at}
    );
    System.out.println("Izin Berhasil Dibuat");

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
