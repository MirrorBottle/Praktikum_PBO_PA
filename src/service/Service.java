package service;

import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import java.sql.SQLException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Arrays;

import helper.Helper;
import helper.Query;
import helper.Table;
import service.attendance.Attendance;
import service.leave.Leave;
import service.shift.Shift;
import service.user.User;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.temporal.TemporalAdjusters;
import java.util.HashSet;  

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
          Leave.approval();
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
    LocalDate firstDayOfTheWeek = LocalDate.now().with(TemporalAdjusters.previousOrSame(DayOfWeek.MONDAY));
    LocalDate lastDayOfTheWeek = firstDayOfTheWeek.plusDays(6);

    ArrayList<String> days = new ArrayList<String>(Arrays.asList("Senin", "Selasa", "Rabu", "Kamis", "Jumat", "Sabtu", "Minggu"));
    ArrayList<String> headers = new  ArrayList<String>();
    headers.add("Jam");
    Table table = new Table();
    table.setShowVerticalLines(true);

    int idx = 0;
    for (String day: days) {
      headers.add(day + " (" + Helper.format(firstDayOfTheWeek.plusDays(idx), "dd/MM") + ")");
      idx++;
    }
    table.setHeaders(headers.stream().toArray(String[]::new));    

    ArrayList<ArrayList<String>> shifts = Query.select("SELECT shifts.*, users.username FROM shifts JOIN users ON shifts.user_id=users.id WHERE expired_at IS NULL OR expired_at > " + lastDayOfTheWeek);
    ArrayList<String> hours = new ArrayList<String>();
    for(ArrayList<String> shift: shifts) {
      hours.add(shift.get(3));
    }

    HashSet<String> hoursSet = new HashSet<String>(hours);
    hours = new ArrayList<String>(hoursSet);

    for(String hour: hours) {
      ArrayList<String> hourDays = new ArrayList<String>();
      hourDays.add(hour);
      // * GET WORKERS BY HOUR AND DAYS
      int dayIdx = 1;
      for (String day: days) {
        ArrayList<String> dayWorkers = new ArrayList<String>();
        for (ArrayList<String> shift: shifts) {
          if (shift.get(2).equals(Integer.toString(dayIdx)) && shift.get(3).equals(hour)) {
            dayWorkers.add(shift.get(5));
          };
        }
        hourDays.add(String.join(", ", dayWorkers));
        dayIdx++;
      }
      table.addRow(hourDays.stream().toArray(String[]::new));
      table.addRow(new String[] {"", "", "", "", "", "", "", ""});
    }

    Helper.banner("Jadwal Shift\n" + Helper.format(firstDayOfTheWeek, "dd/MM/yy") + " s/d " + Helper.format(lastDayOfTheWeek, "dd/MM/yy"));    
    table.print();
    Helper.keypress();
  }
}
