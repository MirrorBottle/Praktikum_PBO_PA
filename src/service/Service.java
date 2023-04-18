package service;

import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import java.sql.SQLException;
import java.text.ParseException;
import java.util.ArrayList;

import helper.Helper;
import helper.Query;
import helper.Table;
import service.attendance.Attendance;
import service.leave.Leave;
import service.shift.Shift;
import service.shift.ShiftItem;
import service.user.User;

public class Service {

  public static String authId = null;
  public static String authUsername = null;


  public static void master() throws IOException, SQLException, NoSuchAlgorithmException  {
    boolean isRunning = true;
    while (isRunning) {
      Helper.banner("Selamat datang, " + authUsername);
      String choice = Helper
          .menus(new String[] { "Manajemen Shift", "Manajemen Absensi", "Manajemen Izin", "Manajemen Pengguna",
              "Kembali" });
      switch (choice) {
        case "1":
          Shift.index();
          break;
        case "2":
          Attendance.index();
          break;
        case "3":
          Leave.index();
          break;
        case "4":
          User.index();
          break;
        case "5":
          isRunning = false;
          break;
      }
    }
  }
  public static void admin() throws IOException, SQLException, NoSuchAlgorithmException {
    boolean isRunning = true;
    while (isRunning) {
      Helper.banner("Selamat datang, " + authUsername);
      String choice = Helper
          .menus(new String[] { "Absensi Hari Ini", "Shift Bulanan", "Approval Izin", "Master Data",
              "Log Out" });
      switch (choice) {
        case "1":
          Attendance.today();
          break;
        case "2":
          Attendance.index();
          break;
        case "3":
          Leave.index();
          break;
        case "4":
          Service.master();
          break;
        case "5":
          isRunning = false;
          break;
      }
    }
  }

  public static void user() throws IOException, NoSuchAlgorithmException, SQLException, ParseException {
    boolean isRunning = true;
    while (isRunning) {
      Helper.banner("Selamat datang, Pengguna!");
      String choice = Helper
          .menus(new String[] { "Absen Hari Ini", "Jadwal Shift", "Histori Absensi", "Histori Izin", "Buat Izin", "Ubah Password", "Log Out" });
      switch (choice) {
        case "1":
          Attendance.present();
          break;
        case "2":
          Shift.user();
          break;
        case "3":
          Attendance.history();
          break;
        case "4":
          Leave.history();
          break;
        case "5":
          Leave.create();
          break;
        case "6":
          User.changePass();
          break;
        case "7":
          isRunning = false;
          break;
      }
    }
  }

  public static void login() throws IOException {
    boolean isLogin = false;

    while(!isLogin) {
      Helper.banner("Login");
      String username = Helper.input("Masukkan username: ");
      String password = Helper.input("Masukkan password: ");
      try {
        String hashed = Helper.hash(password);
        ArrayList<String> user = Query.find("users", String.format("WHERE username='%s' AND password='%s'", username, hashed));
        if(!user.isEmpty()) {
          isLogin = true;
          authId = user.get(0);
          authUsername = user.get(1);
          Helper.keypress("success");
          if(user.get(3).equals("admin")) {
            Service.admin();
          } else {
            Service.user();
          }
        } else {
          Helper.keypress("error");
        }
      } catch (Exception e) {
        System.out.println(e.getMessage());
      }
    }
  }

  public static void shift() throws IOException, SQLException {
    Helper.banner("Daftar Shift");
    String[] headers = { "ID", "Karyawan", "Hari", "Jam", "Berlaku Sampai" };

    ArrayList<ArrayList<String>> result = Query.select("SELECT shifts.*, users.username FROM shifts JOIN users ON shifts.user_id=users.id");
    ArrayList<ArrayList<String>> shifts = new ArrayList<>();
    result.forEach(row -> {
      ShiftItem shift = new ShiftItem(row);
      shifts.add(shift.string());
    });
    Table table = Query.datatable(headers, shifts);
    table.print();
    Helper.keypress();
  }

  
}
