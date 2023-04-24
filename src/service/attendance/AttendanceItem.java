package service.attendance;

import java.util.ArrayList;
import java.util.Objects;

import helper.Helper;
import service.ServiceItemAbstract;

public class AttendanceItem extends ServiceItemAbstract {
    public String id;
    public String user_id;
    public String status;
    public String attendance_at;
    public String note;
    public String username;

    public AttendanceItem(ArrayList<String> data) {
        this.id = data.get(0);
        this.user_id = data.get(1);
        this.status = data.get(2);
        this.attendance_at = data.get(3);
        this.note = data.get(4);
        this.username = data.get(5);
    }

    public String getStatusName() {
        return this.status.equals("1") ? "Tepat Waktu" : "Terlambat";
    }

    public String getNote() {
        return Objects.isNull(this.note) ? "-" : this.note;
    }

    public ArrayList<String> string() {
        ArrayList<String> attendance = new ArrayList<>();
        attendance.add(this.id);
        attendance.add(this.getStatusName());
        attendance.add(Helper.format(this.attendance_at, "yyyy-MM-dd HH:mm:ss", "dd/MM/yy HH:mm"));
        attendance.add(this.getNote());
        return attendance;
    };

    public ArrayList<String> admin() {
        ArrayList<String> attendance = new ArrayList<>();
        attendance.add(this.id);
        attendance.add(this.username);
        attendance.add(this.getStatusName());
        attendance.add(Helper.format(this.attendance_at, "yyyy-MM-dd HH:mm:ss", "dd/MM/yy HH:mm"));
        attendance.add(this.getNote());
        return attendance;
    };

}
