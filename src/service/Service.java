package service;

import java.io.IOException;

import helper.Helper;
import service.attendance.Attendance;
import service.leave.Leave;
import service.shift.Shift;
import service.user.User;

public class Service {



  public static void admin() throws IOException {
    boolean isRunning = true;
    while (isRunning) {
      Helper.banner("Selamat datang, Admin!");
      String choice = Helper
          .menus(new String[] { "Manajemen Shift", "Manajemen Kehadiran", "Manajemen Izin", "Manajemen Pengguna",
              "Log Out" });
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

  public static void user() throws IOException {
    boolean isRunning = true;
    while (isRunning) {
      Helper.banner("Selamat datang, Pengguna!");
      String choice = Helper
          .menus(new String[] { "Absen Hari Ini", "Jadwal Shift", "Histori Absensi", "Histori Izin", "Log Out" });
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
          isRunning = false;
          break;
      }
    }
  }

  public static void login() throws IOException {
    Helper.banner("Login");
    String username = Helper.insert("Masukkan username: ");
    String password = Helper.insert("Masukkan password: ");
    Service.admin();
  }

  public static void shift() {
    Helper.banner("Daftar Shift");
  }
}
