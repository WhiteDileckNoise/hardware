class DrawerManager {

  HashMap<Integer, Drawer> drawers = new HashMap<Integer, Drawer>(); 
  int defaultDrawer;


  DrawerManager() {
  }

  void setDefault(int id) {
    if (null != drawers.get(id)) {
      defaultDrawer = id;
    }
  }

  void addStyle(int index, Drawer drawer) {
    drawers.put(index, drawer);
  } 

  void draw(Graffiti graf, float minTime, float time) {

    for (Integer k : drawers.keySet ()) {
      drawers.get(k).reinit(graf);
    }

    for (Stroke s : graf.strokes) {
      if (null != drawers.get(s.style)) {
        drawers.get(s.style).draw(s, graf.strokes.indexOf(s), minTime, time);
      }
    }
  }
}

