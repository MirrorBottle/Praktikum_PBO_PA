package service.shift;

import service.ServiceInterface;

import java.io.IOException;

import helper.*;

public class Shift implements ServiceInterface {
  public String table = "shifts";

  public static void index() throws IOException {
    Helper.banner("Manajemen Shift");
    Helper.keypress();
  };

  public static void show() throws IOException {
    Helper.banner("Daftar Shift");
    Helper.keypress();
  };

  public static void show(String id) {

  };

  public static void create() throws IOException {
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
}
