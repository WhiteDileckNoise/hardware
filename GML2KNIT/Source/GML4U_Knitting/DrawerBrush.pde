public class DrawerBrush extends Drawer {

  int alpha = 255;
  PImage tex;
  PShader texShader;

  PShape sh;

  int nPoints;

  public DrawerBrush() {
    tex = loadImage(sketchPath("shaders")+"/"+"texture.png");
    texShader = loadShader(sketchPath("shaders")+"/"+"texfrag.glsl", sketchPath("shaders")+"/"+"texvert.glsl");
  }

  public void reinit(Graffiti graf) {
    super.reinit(graf);
  }

  public void drawBegin(Stroke stroke, GraffitiPoint pt) {
    randomSeed(20);
    sh = createShape();
    sh.beginShape(QUAD_STRIP);
    sh.texture(tex);
    sh.noStroke();
    nPoints = stroke.points.size();
  }

  public void drawInterval(GraffitiPoint prev, GraffitiPoint pt) {
    sh.vertex(pt.v.x-siz*cos(pt.rotation), pt.v.y-siz*sin(pt.rotation), (super.i+1)*tex.width/nPoints, 0); 
    sh.vertex(pt.v.x+siz*cos(pt.rotation), pt.v.y+siz*sin(pt.rotation), (super.i+1)*tex.width/nPoints, tex.height);
  }

  public void drawEnd(GraffitiPoint pt) {
    sh.endShape();

    shader(texShader);
    shape(sh);
    resetShader();
  }

  public void setColor(color c) {
    this.c = color(c, alpha);
    // TODO : colorize texture
  }
}

