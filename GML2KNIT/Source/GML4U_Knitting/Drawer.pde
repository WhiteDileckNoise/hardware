public abstract class Drawer {

  float timeMax = 10000;

  int z;

  int alpha = 255;

  int i;

  boolean dripping = true;

  float minDripWidth = .5;
  float maxDripWidth = 3.5;
  float minDripHeight = 10;
  float maxDripHeight = 300;
  float minDropSize = 4;
  float maxDropSize = 6;

  int siz = 20;
  protected int c = color(255, 230);

  public Drawer() {
  }

  public void reinit(Graffiti graf) {
    z = -graf.strokes.size();
  }

  public abstract void drawBegin(Stroke stroke, GraffitiPoint pt);

  public abstract void drawInterval(GraffitiPoint prev, GraffitiPoint pt);

  public abstract void drawEnd(GraffitiPoint pt);

  public abstract void setColor(color c);

  public void setDripping(boolean b) {
    this.dripping = b;
  }

  public void draw(Stroke s, int index, float minTime, float time) {
    pushMatrix();

    z++;

    if (s.dripping) {
      translate(0, 0, z++-5);
      drip(s, minTime, time);
      translate(0, 0, 5);
    }

    i = getFirstPoint(s, minTime, time);

    if (i >= 0) {
      GraffitiPoint prev = null;
      pushStyle();
      //GraffitiPoint pt = s.points.get(i).copy();
      //pt.v.x *= width;
      //pt.v.y *= height;
      //drawBegin(s, pt);
      drawBegin(s, s.points.get(i));

      while (i < s.points.size () && s.points.get(i).time <= time) {
        //drawInterval(prev, pt);
        //prev = pt;
        drawInterval(prev, s.points.get(i));
        prev = s.points.get(i);
        i++;
      }
      if (prev != null) { // Last point
        drawEnd(prev);
      }
      popStyle();
    }
    popMatrix();
  }


  public float getTargetRatio(float timeTarget, float time) {
    if (time < timeMax) {
      return constrain((-pow( time/timeTarget-1, 2)+1), 0, 1);
    } else {
      return 1;
    }
  }

  public int getFirstPoint(Stroke s, float minTime, float time) {

    if (s.points.size() == 0 || s.points.get(0).time > time) {
      return -1;
    } else {
      int i=0;
      while (i<s.points.size () && s.points.get(i).time < minTime) {
        i++;
      }
      if (i == s.points.size()) {
        return -1;
      }
      return i;
    }
  }

  void drip(Stroke s, float minTime, float time) {
    pushStyle();
    stroke(s.c, alpha);
    fill(s.c, alpha);

    for (GraffitiPoint p : s.points) {
      if (p.time < minTime) {
        random(1);
        random(1);
        random(1);
        random(1);
        continue;
      }
      if (p.time > time) break;

      //Draw the drips
      float dripWidth = random(minDripWidth, maxDripWidth);
      float dripHeight = random(minDripHeight, maxDripHeight);
      float dripEnd = random(minDropSize, maxDropSize);
      float dripShift = random(-1, 1);

      strokeWeight(dripWidth);
      float curDripHeight = getTargetRatio(timeMax, time-p.time)*dripHeight;
      line(p.v.x, p.v.y, p.v.x, p.v.y+siz/2+curDripHeight);
      ellipse(p.v.x-dripShift, p.v.y+siz/2+curDripHeight, dripWidth*1.05, dripWidth*dripEnd);
    }
    popStyle();
  }
}

