package service.user;
import service.Service;
import service.ServiceInterface;

import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import java.sql.SQLException;
import java.util.ArrayList;

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

  public static void changePass() throws IOException, SQLException, NoSuchAlgorithmException{
    Helper.banner("Ubah Password");
    String oldPassword = Helper.insert("Masukkan password lama: ");
    String newPassword = Helper.insert("Masukkan password baru: ");
    String hashedOldPass = Helper.hash(oldPassword);
    ArrayList<String> user = Query.find("users", String.format("WHERE id='%s' AND password='%s'", Service.authId, hashedOldPass));
    if(!user.isEmpty()) {
      
    } else {
      System.out.println("Password Lama Anda Salah");
      Helper.keypress("error");
    }

    
  }
}
