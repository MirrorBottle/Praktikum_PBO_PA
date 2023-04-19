package service.leave;

import java.util.ArrayList;

import helper.Helper;

public class LeaveItem {
    public String id;
    public String user_id;
    public String reason;
    public String status;
    public String from_date;
    public String until_date;
    public String created_at;
    public String username;

    public LeaveItem(ArrayList<String> data) {
        this.id = data.get(0);
        this.user_id = data.get(1);
        this.reason = data.get(2);
        this.status = data.get(3);
        this.from_date = data.get(4);
        this.until_date = data.get(5);
        this.created_at = data.get(6);
        this.username = data.get(7);
    }

    public String getStatusName() {
        String statusName = "";
        switch (this.status) {
            case "1":
                statusName = "Menunggu Approval";
                break;
            case "2":
                statusName = "Diterima";
                break;
            case "3":
                statusName = "Ditolak";
                break;
            default:
                break;
        }
        return statusName;
    }

    public ArrayList<String> string() {
        ArrayList<String> leave = new ArrayList<>();
        leave.add(this.id);
        leave.add(this.reason);
        leave.add(this.from_date);
        leave.add(this.until_date);
        leave.add(this.getStatusName());
        leave.add(this.created_at);
        return leave;
    };

    public ArrayList<String> approval() {
        ArrayList<String> leave = new ArrayList<>();
        leave.add(this.id);
        leave.add(this.username);
        leave.add(this.reason);
        leave.add(Helper.format(this.from_date));
        leave.add(Helper.format(this.until_date));
        return leave;
    };
}
