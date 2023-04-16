package service.leave;

import service.Service;
import service.ServiceInterface;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;

import helper.Helper;
import helper.Query;
import helper.Table;

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

  public static void history() throws IOException, SQLException {
    Helper.banner("Histori Izin");
    String[] headers = { "ID", "Alasan", "Dari Tgl.", "Sampai Tgl.", "Status", "Tgl. Dibuat" };

    ArrayList<ArrayList<String>> result = Query.select("SELECT leave_requests.*, users.username FROM leave_requests JOIN users ON leave_requests.user_id=users.id WHERE leave_requests.user_id=" + Service.authId);
    ArrayList<ArrayList<String>> leave_requests = new ArrayList<>();
    result.forEach(row -> {
      LeaveItem leave = new LeaveItem(row);
      leave_requests.add(leave.string());
    });
    Table table = Query.datatable(headers, leave_requests);
    table.print();
    Helper.keypress();
  };
}
