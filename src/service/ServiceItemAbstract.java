package service;

import java.util.ArrayList;

public abstract class ServiceItemAbstract {
  public abstract void init(ArrayList<String> data);
  public abstract void show();
  public abstract void store();
  public abstract void update();
  public abstract void destroy();
  public abstract String[] string();
}
