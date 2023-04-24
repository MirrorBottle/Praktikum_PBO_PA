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
  public static String TABLE = "leave_requests";
  public static String[] HEADERS = { "ID", "Karyawan", "Alasan", "Dari Tgl.", "Sampai Tgl.", "Status", "Tgl. Dibuat" };

  public static void list() throws IOException, SQLException {
    Helper.banner("Daftar Izin");
    ArrayList<ArrayList<String>> result = Query
        .select("SELECT leave_requests.*, users.username FROM leave_requests JOIN users ON leave_requests.user_id=users.id ORDER BY id DESC");
    ArrayList<ArrayList<String>> leave_requests = new ArrayList<>();
    result.forEach(row -> {
      LeaveItem leave_request = new LeaveItem(row);
      leave_requests.add(leave_request.admin());
    });
    Table table = Query.datatable(HEADERS, leave_requests);
    table.print();
    Helper.keypress();
  };

  public static void create() throws IOException {
    Helper.banner("Buat Izin Baru");
    String reason = Helper.input("Masukkan alasan izin: ");
    String from_date = Helper.input("Masukkan tanggal mulai izin (y-m-d): ");
    String until_date = Helper.input("Masukkan tanggal akhir izin (y-m-d): ");
    String created_at = Helper.waktu();
    Query.store(
        "leave_requests",
        new String[] { "user_id", "reason", "status", "from_date", "until_date", "created_at" },
        new String[] { Service.authId, reason, "1", from_date, until_date, created_at });
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

    ArrayList<ArrayList<String>> result = Query.select(
        "SELECT leave_requests.*, users.username FROM leave_requests JOIN users ON leave_requests.user_id=users.id WHERE leave_requests.user_id="
            + Service.authId);
    ArrayList<ArrayList<String>> leave_requests = new ArrayList<>();
    result.forEach(row -> {
      LeaveItem leave = new LeaveItem(row);
      leave_requests.add(leave.string());
    });
    Table table = Query.datatable(headers, leave_requests);
    table.print();
    Helper.keypress();
  };

  public static void approval() throws IOException, SQLException {

    while (true) {
      Helper.banner("Approval Izin");
      String[] headers = { "ID", "Karyawan", "Alasan", "Dari Tgl.", "Sampai Tgl." };
      ArrayList<ArrayList<String>> result = Query.select(
          "SELECT leave_requests.*, users.username FROM leave_requests JOIN users ON leave_requests.user_id=users.id WHERE status=1 ORDER BY id DESC");

      if (result.isEmpty()) {
        Helper.keypress("info", "Tidak ada izin yang perlu approval . . . ");
      } else {
        ArrayList<ArrayList<String>> leave_requests = new ArrayList<>();
        result.forEach(row -> {
          LeaveItem leave = new LeaveItem(row);
          leave_requests.add(leave.approval());
        });

        Table table = Query.datatable(headers, leave_requests);
        table.print();

        String id = Helper.input("Masukkan ID Izin (0 = kembali): ");
        if (!id.equals("0")) {
          ArrayList<String> leave = Query.find(TABLE, Integer.parseInt(id));

          if (!leave.isEmpty()) {
            String status = Helper.input("Masukkan approval (2 = Setuju, 3 = TIdak Setuju): ");

            if (status.equals("2") || status.equals("3")) {
              Query.update(
                  TABLE,
                  id,
                  new String[] { "status" },
                  new String[] { status });

              Helper.keypress("success", "Berhasil melakukan approval izin!");
            }
          } else {
            Helper.keypress("error", "Izin tidak ditemukan!");
          }
        }
      }
      break;
    }
  };

  public static void index() throws IOException, SQLException {
    boolean isRunning = true;

    while (isRunning) {
      Helper.banner("Manajemen Izin");
      String choice = Helper
          .menus(new String[] { "Lihat Daftar Shift", "Tambah Shift", "Ubah Shift",
              "Hapus Shift", "Kembali" });
      switch (choice) {
        case "1":
          Leave.list();
          break;
        case "2":
          Leave.create();
          break;
        case "3":
          Leave.edit();
          break;
        case "4":
          Leave.delete();
          break;
        default:
          isRunning = false;
          break;
      }
    }
  };
}
