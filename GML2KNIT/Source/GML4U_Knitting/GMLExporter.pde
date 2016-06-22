class GMLExporter {


  GMLExporter() {
  }

  void export(Graffiti graf) {


    int w = 200;
    int h = graf.nPoints;

    PGraphics  pg = createGraphics(w, h);

    pg.beginDraw();
    pg.background(255);
    pg.stroke(0);      

    int n = 0;

    for (Stroke s : graf.strokes) {
      for (GraffitiPoint p : s.points) {
        // Speed
        pg.line(0, n, map(p.speedDiff, graf.minSpeedDiff, graf.maxSpeedDiff, 0, 100), n);
        // Angle
        pg.line(200, n, 200 - map(p.angleDiff, 0, 2, 0, 100), n);
        n++;
      }
    }
    pg.endDraw();

    String filename = year()+"-"+nf(month(), 2)+"-"+nf(day(), 2)+"_"+nf(hour(), 2)+"-"+nf(minute(), 2)+"-"+nf(second(), 2)+"_"+nf(millis(), 2);
    pg.save(sketchPath("knitting")+"/"+filename+".png");
  }
}

