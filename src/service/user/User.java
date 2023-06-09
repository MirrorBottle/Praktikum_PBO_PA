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
  public static String[] HEADERS = { "ID", "Username", "Hak Akses" };

  public static void list() throws IOException, SQLException {
    ArrayList<ArrayList<String>> result = Query.select("SELECT * FROM " + TABLE + " ORDER BY id DESC");
    ArrayList<ArrayList<String>> users = new ArrayList<>();
    result.forEach(row -> {
      UserItem user = new UserItem(row);
      users.add(user.string());
    });
    Table table = Query.datatable(HEADERS, users);
    table.print();
  };

  public static UserItem find() throws IOException, SQLException, NoSuchAlgorithmException {
    while (true) {
      String id = Helper.input("Masukkan ID Karyawan: ", "number");
      ArrayList<String> userData = Query.find(TABLE, Integer.parseInt(id));
      if (!userData.isEmpty()) {
        UserItem user = new UserItem(userData);
        return user;
      } else {
        Helper.keypress("error", "Karyawan tidak ada!");
        System.out.print(String.format("\033[%dA", 1)); // Move up
        System.out.print("\033[2K");
        System.out.flush();
      }
    }
  };

  public static void create() throws IOException, SQLException, NoSuchAlgorithmException {
    while (true) {
      Helper.banner("Buat Pengguna Baru");
      String username = Helper.input("Masukkan Username: ", "required");
      String password = Helper.input("Masukkan Password: ", "required");
      String role = Helper.input("Masukkan Hak Akses (1 = admin, 2 = karyawan): ", "number");
      ArrayList<String> user = Query.find(TABLE, String.format("WHERE username='%s'", username));

      if (user.isEmpty()) {
        password = Helper.hash(password);
        role = role.equals("1") ? "admin" : "user";
        Query.store(
            TABLE,
            new String[] { "username", "password", "role" },
            new String[] { username, password, role });
        Helper.keypress("success", "Pengguna berhasil ditambah!");
        break;
      }
      Helper.keypress("error", "Username sudah ada!");
    }
  };

  public static void edit() throws IOException, SQLException, NoSuchAlgorithmException {
    while (true) {
      Helper.banner("Ubah Pengguna");
      User.list();
      UserItem user = User.find();
      String username = Helper.input(String.format("Masukkan username (%s): ", user.username), "required");
      String password = Helper.input("Masukkan password: ", "required");
      String role = Helper.input("Masukkan hak akses (1 = admin, 2 = karyawan): ", "number");

      ArrayList<String> userFindSameUsername = Query.find(TABLE,
          String.format("WHERE username='%s' AND id<>%s", username, user.id));

      if (userFindSameUsername.isEmpty()) {
        password = Helper.hash(password);
        role = role.equals("1") ? "admin" : "user";
        Query.update(
            TABLE,
            user.id,
            new String[] { "username", "role" },
            new String[] { username, role });
        Helper.keypress("success", "Pengguna berhasil diubah!");
        break;
      }
      Helper.keypress("error", "Username sudah ada!");
    }

  };

  public static void delete() throws IOException, SQLException, NoSuchAlgorithmException {
    while (true) {
      Helper.banner("Hapus Pengguna");
      User.list();
      String id = Helper.input("Masukkan ID Pengguna: ", "number");
      ArrayList<String> userData = Query.find(TABLE, Integer.parseInt(id));
      if (!userData.isEmpty()) {
        UserItem user = new UserItem(userData);
        System.out.println("Username pengguna: " + user.username);
        Boolean isConfirmed = Helper.confirm();
        if (isConfirmed) {
          Query.delete(TABLE, user.id);
          Helper.keypress("success", "Pengguna berhasil dihapus!");
        }
        break;
      }
      Helper.keypress("error", "Pengguna tidak ada!");
    }
  };

  public static void changePass() throws IOException, SQLException, NoSuchAlgorithmException {
    Helper.banner("Ubah Password");
    String oldPassword = Helper.input("Masukkan password lama: ", "required");
    String newPassword = Helper.input("Masukkan password baru: ", "required");
    String hashedOldPass = Helper.hash(oldPassword);
    ArrayList<String> user = Query.find(TABLE,
        String.format("WHERE id='%s' AND password='%s'", Service.authId, hashedOldPass));
    if (!user.isEmpty()) {
      String hashedNewPass = Helper.hash(newPassword);
      Query.update(
          TABLE,
          Service.authId,
          new String[] { "password" },
          new String[] { hashedNewPass });

      System.out.println("Password Berhasil Diubah");
      Helper.keypress("success");

    } else {
      System.out.println("Password Lama Anda Salah");
      Helper.keypress("error");
    }
  }

  public static void index() throws IOException, SQLException, NoSuchAlgorithmException {
    boolean isRunning = true;
    while (isRunning) {
      Helper.banner("Manajemen Pengguna");
      String choice = Helper
          .menus(new String[] { "Lihat Daftar Pengguna", "Tambah Pengguna", "Ubah Pengguna",
              "Hapus Pengguna", "Kembali" });
      switch (choice) {
        case "1":
          Helper.banner("Daftar Pengguna");
          User.list();
          Helper.keypress();
          break;
        case "2":
          User.create();
          break;
        case "3":
          User.edit();
          break;
        case "4":
          User.delete();
          break;
        default:
          isRunning = false;
          break;
      }
    }
  };

}
