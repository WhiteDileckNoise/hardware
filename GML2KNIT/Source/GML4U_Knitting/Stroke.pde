public class Stroke {

  private ArrayList<GraffitiPoint> points = new ArrayList<GraffitiPoint>();

  int style;
  int c;
  //int siz;


  float rotation;
  boolean dripping;

  public Stroke(int style, int c) {
    this.style = style;
    this.c = c;
  }

  public Stroke(int style, int c, float r, boolean d) {
    this.style = style;
    this.c = c;
    //this.siz = siz;
    this.rotation = r;
    this.dripping = d;
  }

  public void addPoint(GraffitiPoint point) {
    points.add(point);
  }

  public GraffitiPoint getPoint(int index) {
    return points.get(index);
  }
}

