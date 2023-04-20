import helper.Helper;
import helper.Query;
import service.Service;
import service.shift.Shift;

public class App {
    static boolean isRunning = true;

    public static void main(String[] args) throws Exception {
        Query.init();
        Shift.monthly();
        while (isRunning) {
            Helper.banner("Aplikasi Absensi dan Shift");
            String choice = Helper.menus(new String[] { "Login", "Jadwal Shift Minggu Ini", "Keluar" });

            switch (choice) {
                case "1":
                    Service.login();
                    break;
                case "2":
                    Service.shift();
                    break;
                case "3":
                    System.out.println("Sampai jumpa!");
                    System.exit(0);
                    break;
            }
        }
        System.out.println("Sampai jumpa!");
        System.exit(0);
    }
}
