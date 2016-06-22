class GMLAnalyzer {

  GMLAnalyzer() {
  }


  void draw(Graffiti graf, int x, int y) {

    pushMatrix();
    pushStyle();
    strokeWeight(1);
    translate(10, 10);
    int n = 0;


    for (Stroke s : graf.strokes) {
      for (GraffitiPoint p : s.points) {
        // Speed
        stroke(255);      
        line(n, 0, n, map(p.speedDiff, graf.minSpeedDiff, graf.maxSpeedDiff, 0, 200));
        // Angle
        stroke(255, 0, 0);      
        line(n, 400, n, 400 - map(p.angleDiff, 0, 2, 0, 200));
        n++;
      }
    }
    popStyle();
    popMatrix();
  }
}

