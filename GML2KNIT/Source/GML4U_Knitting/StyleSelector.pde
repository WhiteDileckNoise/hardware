class StyleSelector {

  int styleIndex = 0;
  //PImage styles[] = new PImage[7];
  PImage styles[] = new PImage[1];

  float rotation =  -PI/4;
  boolean drip = true;


  float targetX = 0;

  float easing = 0.075;

  float x;
  float y;

  int w;
  float spacing = 10;

  StyleSelector(float y) {
    this.w = (int) ((width/(styles.length))/2-spacing);
    println(w);
    this.targetX = width/2;
    this.x = targetX;
    this.y = y;
    setDefaultColors();
  }

  void draw() {
    update();
    drawPalette(y);
  }

  void preview() {
    pushMatrix();
    translate(width-styles[styleIndex].width-20, y+10);
    pushStyle();
    stroke(255);
    rect(-2, -2, styles[styleIndex].width+4, styles[styleIndex].height+4, 7);
    image(styles[styleIndex], 0, 0);
    popStyle();
    popMatrix();
  }

  void shift(int s) {
    targetX += (int) s*w;
    if (s > 0) {
      styleIndex--;
      if (styleIndex < 0) {
        styleIndex = styles.length-1;
        targetX = width/2-(styles.length-1)*w;
      }
    } else {
      styleIndex++; 
      if (styleIndex == styles.length) {
        styleIndex = 0;
        targetX = width/2;
      }
    }
  }


  void setDefaultColors() {
    styles[0] = loadImage(sketchPath("shaders")+"/texture.png");
    /*
    styles[1] = loadImage(sketchPath("resources")+"/brush.png");
     styles[2] = loadImage(sketchPath("resources")+"/lines.png");
     styles[3] = loadImage(sketchPath("resources")+"/spraycan.png");
     styles[4] = loadImage(sketchPath("resources")+"/shapes.png");
     styles[5] = loadImage(sketchPath("resources")+"/hairy.png");
     styles[6] = loadImage(sketchPath("resources")+"/petals.png");
     */
  }

  int getStyle() {
    return styleIndex;
  }

  void setRotation(float r) {
    rotation = r;
  }

  float getRotation() {
    return rotation;
  }


  void update() {

    float dx = targetX - x;
    if (abs(dx) > 1) {
      x += dx * easing;
    }
  }


  void drawPalette(float yPos) {
    /*
    pushStyle();
     
     fill(0);
     text(styleIndex, 20, 20);
     
     imageMode(CENTER);
     rectMode(CENTER);
     stroke(255);
     
     pushMatrix();
     float xPos = x;
     translate(xPos, yPos);
     
     for (int i=0; i<styles.length; i++) {
     //rect(i*w, 0, styles[i].width+2, styles[i].height+2, 7);
     rect(i*w, 0, w, w, 7);
     image(styles[i], i*w, 0, w, w);
     if (i == styleIndex) {
     fill(255);
     rect(i*w, w*.8, w*.33, w*.33);
     }
     }
     popMatrix();
     
     popStyle();
     */
  }
}

