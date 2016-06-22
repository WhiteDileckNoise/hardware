public class Graffiti {

  ArrayList<Stroke> strokes = new ArrayList<Stroke>();
  float duration;
  int nPoints = 0;

  float maxSpeed = 0;
  float minSpeedDiff = 0;
  float maxSpeedDiff = 0;

  float maxAngle = 0;

  public Graffiti() {
  }

  boolean hasStroke() {
    return strokes.size() > 0;
  }

  public void addStroke(int style, int c, float r, boolean d) {
    Stroke stroke = new Stroke(style, c, r, d);
    strokes.add(stroke);
  }

  public void addPoint(PVector pos, float t) {
    addPoint(pos, t, 0.);
    //strokes.get(strokes.size()-1).points.add(new GraffitiPoint(v, t));
    //duration = t;
  }


  public void addPoint(PVector pos, float t, float r) {
    float angle = 0;
    float angleDiff = 0;
    float speed = 0;
    float speedDiff = 0;

    //PVector pos = new PVector(x, y, 0);
    if (strokes.get(strokes.size()-1).points.size() >= 1) {
      GraffitiPoint prev = strokes.get(strokes.size()-1).points.get(strokes.get(strokes.size()-1).points.size()-1);

      PVector diff = PVector.sub(pos, prev.v);
      angle = diff.heading();
      angleDiff = abs(cos(prev.angle) - cos(angle));  // cos bc we will calculate a diff and want it continuous

      float distance = pos.dist(prev.v);
      float elapsedTime = t - prev.time;
      if (elapsedTime != 0) { // Avoid division by 0
        speed = distance/elapsedTime;
        speedDiff = speed - prev.speed;
      }
    }

    // Keep track of min and max for later use without calculation each time
    if (speed > maxSpeed) {
      maxSpeed = speed;
    }

    if (speedDiff < minSpeedDiff) {
      minSpeedDiff = speedDiff;
    } else if (speedDiff > maxSpeedDiff) {
      maxSpeedDiff = speedDiff;
    }
    strokes.get(strokes.size()-1).addPoint(new GraffitiPoint(pos, t, r, angle, angleDiff, speed, speedDiff));

    nPoints++;
    duration = t;
  }


  public void clear() {
    nPoints = 0;
    strokes.clear();
  }
}

