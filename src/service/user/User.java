package service.user;
import service.Service;
import service.ServiceInterface;

import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import java.sql.SQLException;
import java.util.ArrayList;

import helper.*;
public class User implements ServiceInterface {
  public static String TABLE = "users";
  public static String[] HEADERS = { "ID", "Username", "Hak Akses"};

  public static void list() throws IOException, SQLException {
    Helper.banner("Daftar Pengguna");
    ArrayList<ArrayList<String>> result = Query.select("SELECT * FROM users");
    ArrayList<ArrayList<String>> users = new ArrayList<>();
    result.forEach(row -> {
      UserItem user = new UserItem(row);
      users.add(user.string());
    });
    Table table = Query.datatable(HEADERS, users);
    table.print();
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

  public static void changePass() throws IOException, SQLException, NoSuchAlgorithmException{
    Helper.banner("Ubah Password");
    String oldPassword = Helper.insert("Masukkan password lama: ");
    String newPassword = Helper.insert("Masukkan password baru: ");
    String hashedOldPass = Helper.hash(oldPassword);
    ArrayList<String> user = Query.find("users", String.format("WHERE id='%s' AND password='%s'", Service.authId, hashedOldPass));
    if(!user.isEmpty()) {
      String hashedNewPass = Helper.hash(newPassword);
      Query.update(
        "users",
        Service.authId,
        new String[]{"password"},
        new String[]{hashedNewPass}
      );

      System.out.println("Password Berhasil Diubah");
      Helper.keypress("success");

    } else {
      System.out.println("Password Lama Anda Salah");
      Helper.keypress("error");
    }
  }

  public static void index() throws IOException, SQLException {
    boolean isRunning = true;

    while (isRunning) {
      Helper.banner("Manajemen Pengguna");
      String choice = Helper
            .menus(new String[] { "Lihat Daftar Pengguna", "Tambah Pengguna", "Ubah Pengguna",
                "Hapus Pengguna", "Kembali" });
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
