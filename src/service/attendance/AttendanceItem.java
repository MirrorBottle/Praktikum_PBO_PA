package service.attendance;

import java.util.ArrayList;

import service.ServiceItemAbstract;

public class AttendanceItem extends ServiceItemAbstract {
    public String id;
    public String user_id;
    public String status;
    public String attendance_at;
    public String note;
    public String user_name;

    public AttendanceItem(ArrayList<String> data) {
        this.id = data.get(0);
        this.user_id = data.get(1);
        this.status = data.get(2);
        this.attendance_at = data.get(3);
        this.note = data.get(4);
        this.user_name = data.get(5);
    }

    public String getStatusName() {
        return this.status.equals("1") ? "Tepat Waktu" : "Terlambat";
    }

    public ArrayList<String> string() {
        ArrayList<String> shift = new ArrayList<>();
        shift.add(this.id);
        shift.add(this.getStatusName());
        shift.add(this.attendance_at);
        return shift;
    };

}
