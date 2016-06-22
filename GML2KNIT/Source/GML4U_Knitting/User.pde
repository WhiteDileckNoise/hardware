class User {

  Graffiti graffiti = new Graffiti();

  ColorSelector cos;
  StyleSelector sts;

  Effects effects;
  PVector prev;


  boolean viewMenu;
  boolean preview;
  boolean previewCustomColor;
  boolean useEffects = true;

  boolean clearLast;
  boolean clearAll;

  int previewStartTime;
  int previewDuration = 1000;

  GraffitiPoint pt = null;

  int lastOSC;

  public User(float y) {
    cos = new ColorSelector(y);
    sts = new StyleSelector(y+100);
    previewStartTime = millis();
    effects = new Effects();
  }


  Graffiti getGraff() {
    return graffiti;
  }

  public void draw() {

    try {
      if (null != pt) {
        if (newStroke) {
          graffiti.addStroke(getStyle(), getColor(), getRotation(), dripping);
          newStroke = false;
        }
        if (resetTime) {
          time = millis();
          resetTime = false;
        }
        graffiti.addPoint(pt.v, millis()-time, pt.rotation);
        // Add particle
        if (useEffects) {
          effects.addParticle(pt.v);
        }
        // 
        pt = null;
      }


      if (clearAll) {
        clearAll();
      }

      if (clearLast) {
        clearLast();
      }
    }
    catch (Exception e) {
    }


    // PreviewCustom
    if (previewCustomColor) {

      cos.previewCustom();

      if (previewStartTime + previewDuration < millis()) {
        previewCustomColor = false;
      }
    }
    // View menu
    else if (viewMenu) {
      cos.draw();
      sts.draw();
    }
    // Preview
    else if (preview) {
      cos.preview();
      sts.preview();
      if (previewStartTime + previewDuration < millis()) {
        preview = false;
      }
    }
  }

  void drawEffects() {
    effects.draw();
  }

  void addStroke() {
    newStroke = true;
  }

  void addPoint(PVector v, float t) {
    pt = new GraffitiPoint(v, millis()-time, getRotation());
  }


  void setClearAll() {
    clearAll = true;
  }

  void setClearLast() {
    clearLast = true;
  }


  void clearAll() {
    graffiti.clear();
    effects.clear();
    newStroke = true;
    clearAll = false;
  }

  void clearLast() {
    if (graffiti.strokes.size() >= 1) {
      graffiti.strokes.remove(graffiti.strokes.size()-1);
    }
    clearLast = false;
  }


  int getLastOSC() {
    return lastOSC;
  }

  void setLastOSC(int t) {
    lastOSC = t;
  }

  void setRotation(float r) {
    sts.setRotation(r);
  }

  float getRotation() {
    return sts.getRotation();
  }

  void setPreviewRed(int v) {
    cos.setPreviewRed(v);
    previewCustomColor();
  }

  void setPreviewGreen(int v) {
    cos.setPreviewGreen(v);
    previewCustomColor();
  }

  void setPreviewBlue(int v) {
    cos.setPreviewBlue(v);
    previewCustomColor();
  }

  void previewCustomColor() {
    previewCustomColor = true;
    previewStartTime = millis();
  }

  void previewColor() {
    preview = true;
    previewStartTime = millis();
  }

  void previewStyle() {
    preview = true;
    previewStartTime = millis();
  }


  void setColor(int index) {
    cos.setColor(index);
  }

  color getColor() {
    return cos.getColor();
  }

  void setDefaultColors() {
    cos.setDefaultColors();
  }


  void prevColor() {
    cos.shift(1);
    previewColor();
  }

  void nextColor() {
    cos.shift(-1);
    previewColor();
  }

  color getColor(String id) {
    return cos.getColor();
  }

  void prevStyle() {
    sts.shift(1);
    previewStyle();
  }

  void nextStyle() {
    sts.shift(-1);
    previewStyle();
  }

  int getStyle() {
    return sts.getStyle();
  }

  void toggleDripping() {
    dripping = !dripping;
  }

  void toggleMenu() {
    viewMenu = !viewMenu;
    if (!viewMenu) {
      preview = false;
    }
  }

  void toggleEffects() {
    useEffects = !useEffects;
  }
}

