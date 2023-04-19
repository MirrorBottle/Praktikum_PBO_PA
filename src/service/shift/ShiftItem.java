package service.shift;

import java.util.ArrayList;

import service.ServiceItemAbstract;

public class ShiftItem extends ServiceItemAbstract {
  public String id;
  public String user_id;
  public String day;
  public String hour;
  public String expired_at;
  public String username;

  public ShiftItem(ArrayList<String> data) {
    this.id = data.get(0);
    this.user_id = data.get(1);
    this.day = data.get(2);
    this.hour = data.get(3);
    this.expired_at = data.get(4);
    this.username = data.get(5);
  }

  public String getDayName() {
    String dayName = "";
    switch (this.day) {
      case "1":
        dayName = "Senin";
        break;
      case "2":
        dayName = "Selasa";
        break;
      case "3":
        dayName = "Rabu";
        break;
      case "4":
        dayName = "Kamis";
        break;
      case "5":
        dayName = "Jumat";
        break;
      case "6":
        dayName = "Sabtu";
        break;
      case "7":
        dayName = "Minggu";
        break;
      default:
        break;
    }
    return dayName;
  }

  public String getExpiredAt() {
    return this.expired_at == null ? "Seterusnya" : this.expired_at;
  }

  public ArrayList<String> string() {
    ArrayList<String> shift = new ArrayList<>();
    shift.add(this.id);
    shift.add(this.username);
    shift.add(this.getDayName());
    shift.add(this.hour);
    shift.add(this.getExpiredAt());
    return shift;
  };

  public ArrayList<String> user() {
    ArrayList<String> shift = new ArrayList<>();
    shift.add(this.id);
    shift.add(this.getDayName());
    shift.add(this.hour);
    shift.add(this.getExpiredAt());
    return shift;
  };
}
