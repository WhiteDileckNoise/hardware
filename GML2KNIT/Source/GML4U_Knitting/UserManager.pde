class UserManager {

  HashMap<String, User> users = new HashMap<String, User>();

  public UserManager() {
    //checkUserExists("mouse");
  }

  public Graffiti getGraff(String id) {
    return users.get(id).getGraff();
  }

  public void checkUserExists(String id) {
    if (null == users.get(id)) {
      users.clear(); // Little hack / retriction for this version : keep only one user
      users.put(id, new User(100+ 350 *(users.size())));
      println("New user", id);
    }
  }

  public void drawEffects(String id) {
    users.get(id).drawEffects();
  }


  public void showMenu() {
    for (String k : users.keySet ()) {
      users.get(k).draw();
    }
  }

  public void addStroke(String id) {
    checkUserExists(id);
    users.get(id).addStroke();
  }

  public void addPoint(String id, PVector v, float t) {
    checkUserExists(id);
    users.get(id).addPoint(v, t);
  }

  public int getLastOSC(String id) {
    checkUserExists(id);
    return users.get(id).getLastOSC();
  }

  public void setLastOSC(String id, int t) {
    checkUserExists(id);
    users.get(id).setLastOSC(t);
  }

  void setPreviewRed(String id, int v) {
    checkUserExists(id);
    users.get(id).setPreviewRed(v);
  }

  void setPreviewGreen(String id, int v) {
    checkUserExists(id);
    users.get(id).setPreviewGreen(v);
  }

  void setPreviewBlue(String id, int v) {
    checkUserExists(id);
    users.get(id).setPreviewBlue(v);
  }

  void setColor(String id, int index) {
    checkUserExists(id);
    users.get(id).setColor(index);
  }

  void setDefaultColors(String id) {
    checkUserExists(id);
    users.get(id).setDefaultColors();
  }

  void prevColor(String id) {
    checkUserExists(id);
    users.get(id).prevColor();
  }

  void nextColor(String id) {
    checkUserExists(id);
    users.get(id).nextColor();
  }

  int getColor(String id) {
    checkUserExists(id);
    return users.get(id).getColor();
  }

  void setRotation(String id, float r) {
    checkUserExists(id);
    users.get(id).setRotation(r);
  }

  float getRotation(String id) {
    checkUserExists(id);
    return users.get(id).getRotation();
  }

  void toggleDripping(String id) {
    checkUserExists(id);
    users.get(id).toggleDripping();
  }

  void prevStyle(String id) {
    checkUserExists(id);
    users.get(id).prevStyle();
  }

  void nextStyle(String id) {
    checkUserExists(id);
    users.get(id).nextStyle();
  }

  int getStyle(String id) {
    checkUserExists(id);
    return users.get(id).getStyle();
  }

  void toggleMenu(String id) {
    checkUserExists(id);
    users.get(id).toggleMenu();
  }

  void clearAll(String id) {
    checkUserExists(id);
    users.get(id).setClearAll();
  }

  public void clearEverything() {
    for (String k : users.keySet ()) {
      users.get(k).setClearAll();
    }
  }

  void clearLast(String id) {
    checkUserExists(id);
    users.get(id).setClearLast();
  }

  void toggleEffects(String id) {
    checkUserExists(id);
    users.get(id).toggleEffects();
  }
}

