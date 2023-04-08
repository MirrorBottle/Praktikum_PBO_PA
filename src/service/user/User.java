package service.user;
import service.ServiceInterface;

import java.io.IOException;

import helper.*;
public class User implements ServiceInterface {
  public String table = "users";

  public static void index() throws IOException {
    Helper.banner("Manajemen Pengguna");
    Helper.keypress();
  };

  public static void show() throws IOException {
    Helper.banner("Daftar Pengguna");
    Helper.keypress();
  };

  public static void show(String id) {

  };

  public static void create() throws IOException {
    Helper.banner("Buat Pengguna Baru");
    Helper.keypress();

  };

  public static void edit() throws IOException {
    Helper.banner("Ubah Pengguna");
    Helper.keypress();


  };

  public static void delete() throws IOException {
    Helper.banner("Hapus Pengguna");
    Helper.keypress();
  };
}
