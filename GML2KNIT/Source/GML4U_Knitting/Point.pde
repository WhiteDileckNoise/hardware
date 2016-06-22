public class GraffitiPoint {

  public PVector v;
  public float time;
  public float rotation;
  public float angle;
  public float angleDiff;
  public float speed;
  public float speedDiff;

  public GraffitiPoint(PVector v, float time) {
    this.v = v;
    this.time = time;
    this.rotation = 0;
    this.angle = 0;
    this.speed = 0;
  }

  public GraffitiPoint(PVector v, float time, float rotation) {
    this(v, time);
    this.rotation = rotation;
  }

  public GraffitiPoint(PVector v, float time, float rotation, float angle, float angleDiff, float speed, float speedDiff) {
    this(v, time, rotation);
    this.angle = angle;
    this.angleDiff = angleDiff;
    this.speed = speed;
    this.speedDiff = speedDiff;
  }

  public GraffitiPoint copy() {
    return new GraffitiPoint(new PVector(v.x, v.y, v.z), time, rotation, angle, angleDiff, speed, speedDiff);
  }
}

