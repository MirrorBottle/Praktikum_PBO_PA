package service.user;

import java.util.ArrayList;

import service.ServiceItemAbstract;

public class UserItem extends ServiceItemAbstract {
    public String id;
    public String username;
    public String password;
    public String role;

    public UserItem(ArrayList<String> data) {
        this.id = data.get(0);
        this.username = data.get(1);
        this.password = data.get(2);
        this.role = data.get(3);
    }

    public String getRoleName() {
        return this.role.equals("admin") ? "Admin" : "Karyawan";
    }

    public ArrayList<String> string() {
        ArrayList<String> user = new ArrayList<>();
        user.add(this.id);
        user.add(this.username);
        user.add(this.getRoleName());
        return user;
    };
}
