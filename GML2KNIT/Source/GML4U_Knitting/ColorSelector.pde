class ColorSelector {

  int colorIndex = 0;
  //int colors[] = new int[16];
  int colors[] = new int[1];

  color c = color(255);
  color curColor;
  int red = 255;
  int green = 255;
  int blue = 255;

  color previewedColor;

  float targetX = 0;

  float easing = 0.075;

  float x;
  float y;

  int w;

  ColorSelector(float y) {
    this.w = (int) (width/colors.length)/2;
    println(w);
    this.targetX = width/2;
    this.x = targetX;
    this.y = y;
    setDefaultColors();
  }

  void draw() {
    update();
    drawPalette();
  }

  void drawPalette() {
    /*
    pushStyle();
     
     fill(0);
     text(colorIndex, 20, 20);
     
     rectMode(CENTER);
     stroke(255);
     
     pushMatrix();
     
     translate(x, y, 5);
     
     for (int i=0; i<colors.length; i++) {
     fill(colors[i]);
     rect(i*w, 0, w*.8, w*.8, 7);
     if (i == colorIndex) {
     fill(255);
     rect(i*w, -w*.8, w*.33, w*.33);
     }
     }
     popMatrix();
     
     popStyle();
     */
  }


  void preview() {
    pushMatrix();
    translate(width-120, y);
    pushStyle();
    stroke(255);
    strokeWeight(2);
    fill(colors[colorIndex]);
    rect(-2, -2, 104, 104, 7);
    popStyle();
    popMatrix();
  }

  void previewCustom() {
    pushMatrix();
    translate(width/2, y);
    pushStyle();
    stroke(255);
    fill(previewedColor);
    rect(-2, -2, 104, 104, 7);
    popStyle();
    popMatrix();
  }

  void  setPreviewRed(int r) {
    red = r;
    previewedColor = color(red, green, blue);
  } 

  void setPreviewGreen(int g) {
    green = g;
    previewedColor = color(red, green, blue);
  } 

  void setPreviewBlue(int b) {
    blue = b;
    previewedColor = color(red, green, blue);
  } 

  color getColor() {
    return colors[colorIndex];
  }

  void setColor(int i) {
    if (i >= 0 && i<colors.length) {
      colors[i] = previewedColor;
    }
  }

  void shift(int s) {
    targetX += (int) s*w;
    if (s > 0) {
      colorIndex--;
      if (colorIndex < 0) {
        colorIndex = colors.length-1;
        targetX = width/2-(colors.length-1)*w;
      }
    } else {
      colorIndex++; 
      if (colorIndex == colors.length) {
        colorIndex = 0;
        targetX = width/2;
      }
    }
  }


  void setDefaultColors() {
    colors[0] = color(255);
    /*
    colors[1] = color(253, 240, 122); // Yellow superlight
     colors[2] = color(244, 232, 40); // Yellow
     colors[3] = color(179, 146, 96); // Brown 
     colors[4] = color(214, 5, 62); // Red
     colors[5] = color(255, 0, 0); // Red
     colors[6] = color(185, 224, 220); // Blue superlight
     colors[7] = color(96, 161, 184); // Blue light
     colors[8] = color(197, 221, 168); // Green superlight
     colors[9] = color(126, 177, 37); // Green
     colors[10] = color(20, 166, 133); // Green
     colors[11] = color(198, 198, 198); // Gray
     colors[12] = color(210, 193, 221); // Violet superlight
     colors[13] = color(169, 153, 198); // Violet light
     colors[14] = color(142, 19, 95); // Purple
     colors[15] = color(0, 0, 0); // Green
     */
  }


  void update() {

    float dx = targetX - x;
    if (abs(dx) > 1) {
      x += dx * easing;
    }
  }
}

