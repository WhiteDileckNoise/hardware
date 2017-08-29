import oscP5.*;
import netP5.*;

OscP5 oscP5;
NetAddress remote;

boolean newStroke = false;

DrawerManager drawerMgr = new DrawerManager();

boolean line = true;

float time;
boolean resetTime = true;
boolean interval = false;
boolean dripping = false;
boolean loop = false;

int cnt = 0;
int delay = 200;

float angle;

UserManager users;
GMLAnalyzer ga;
GMLExporter ge;

boolean export;
boolean help;


void setup() {
  size(800, 600, OPENGL);
  smooth(4);
  hint(DISABLE_DEPTH_MASK);

  oscP5 = new OscP5(this, 3333);
  remote = new NetAddress("127.0.0.1", 3333);

  users = new UserManager();
  ga = new GMLAnalyzer();
  ge = new GMLExporter();


  Drawer drawer = new DrawerBrush();
  drawer.siz = 20;

  drawerMgr.addStyle(0, drawer);
  time = millis();
}

void draw() {
  background(0);
  frame.setTitle("Frame rate "+frameRate); 

  //pointLight(255, 255, 255, 1000, 1000, -100);


  boolean isEmpty = true;
  for (String k : users.users.keySet ()) {
    if (users.getGraff(k).hasStroke()) {
      isEmpty = false;
      break;
    }
  }
  if (isEmpty) {
    time = millis();
  }


  pushMatrix();
  translate(width/2, height/2);
  //rotateY(angle);
  angle += .02;
  translate(-width/2, -height/2);
  for (String k : users.users.keySet ()) {
    drawerMgr.draw(users.getGraff(k), interval ? millis()-time-1000 : 0, millis()-time);
    textAlign(RIGHT, TOP);
    text("Total points: "+users.getGraff(k).nPoints, width-75, 75);
    users.drawEffects(k);
  }
  popMatrix();

  users.showMenu();

  if (!isEmpty) {
    for (String k : users.users.keySet ()) {
      ga.draw(users.getGraff(k), 0, 0);
      if (export) {
        ge.export(users.getGraff(k));
      }
    }
    export = false;
  }

  pushStyle();
  textAlign(RIGHT, BOTTOM);
  stroke(255);
  text("Hit \"K\" to export, \"H\" to toggle help", width-75, height-75);
  popStyle();

  if (help) {
    pushStyle();
    pushMatrix();
    textAlign(RIGHT, BOTTOM);
    stroke(255);
    int x = width/4;
    int y = 100;
    text("SPACE = clear all", x, y+=25);
    text("X = delete last stroke", x, y+=25);
    text("R = replay", x, y+=25);
    text("S = screenshot", x, y+=25);
    text("K = export knitting pattern", x, y+=25);
    text("H = toggle help", x, y+=25);
    popMatrix();
    popStyle();
  }
}

void oscEvent(OscMessage msg) {

  String pattern = msg.addrPattern();

  String user = msg.address();
  //users.checkUserExists(user);

  if (pattern.equals("/action/color/1")) { // Red
    users.setPreviewRed(user, (int) msg.get(0).floatValue());
  } else if (pattern.equals("/action/color/2")) { // Green
    users.setPreviewGreen(user, (int) msg.get(0).floatValue());
  } else if (pattern.equals("/action/color/3")) { // Blue
    users.setPreviewBlue(user, (int) msg.get(0).floatValue());
  } else if (pattern.contains("/action/setcolor")) {
    users.setColor(user, Integer.parseInt(pattern.replaceAll("/action/setcolor", ""))-1);
  } else if (pattern.equals("/action/viewmenu") && msg.get(0).floatValue() == 1) {
    users.toggleMenu(user);
  } else if (pattern.equals("/action/defaultcolors") && msg.get(0).floatValue() == 1) {  
    users.setDefaultColors(user);
  } else if (pattern.equals("/action/nextcolor") && msg.get(0).floatValue() == 1) {
    users.nextColor(user);
  } else if (pattern.equals("/action/prevcolor") && msg.get(0).floatValue() == 1) {
    users.prevColor(user);
  } else if (pattern.equals("/action/prevstyle") && msg.get(0).floatValue() > 0) {
    users.prevStyle(user);
  } else if (pattern.equals("/action/nextstyle") && msg.get(0).floatValue() > 0) {
    users.nextStyle(user);
  } else if (pattern.equals("/action/clearlast") && msg.get(0).floatValue() == 1) {
    users.clearLast(user);
  } else if (pattern.equals("/action/clearall") && msg.get(0).floatValue() == 1) {
    users.clearAll(user);
  } else if (pattern.equals("/action/save") && msg.get(0).floatValue() == 1) {
    screenshot();
  } else if (pattern.equals("/action/replay") && msg.get(0).floatValue() == 1) {
    replay();
  } else if (pattern.equals("/action/interval") && msg.get(0).floatValue() == 1) {
    replayInterval();
  } else if (pattern.equals("/action/dripping") && msg.get(0).floatValue() == 1) {
    users.toggleDripping(user);
  } else if (pattern.equals("/action/loop") && msg.get(0).floatValue() == 1) {
    toggleLoop();
  } else if (pattern.equals("/action/angle")  && msg.checkTypetag("f")) {
    users.setRotation(user, PI * msg.get(0).floatValue());
    // TODO visual feedback
  } else if (pattern.equals("/cursor/xy") && msg.checkTypetag("ff")) {
    float x = msg.get(0).floatValue();  
    float y = msg.get(1).floatValue();
    PVector v = new PVector(y*width, x*height, 0);
    if (millis() > users.getLastOSC(user) + 500) {
      users.addStroke(user);
    } 
    users.addPoint(user, v, millis()-time);
    users.setLastOSC(user, millis());
  }

  // CCV TUIO OSC
  else if (msg.checkAddrPattern("/tuio/2Dcur")) {
    if (msg.checkTypetag ("sifffffff")) { // Set id x y ? ? ? w h 

      float x = (msg.get(2).floatValue());
      float y = (msg.get(3).floatValue());

      PVector v = new PVector((1-y)*width, (1-x)*height, 0);
      if (millis() > users.getLastOSC(user) + 500) {
        users.addStroke(user);
      } 
      users.addPoint(user, v, millis()-time);
      users.setLastOSC(user, millis());
    }

    if (msg.checkTypetag ("sifffff")) { // Set id x y ? ? ?  sifffffff
      float x = (msg.get(2).floatValue());
      float y = (msg.get(3).floatValue());

      println(x, y);

      PVector v = new PVector((1-y)*width, (1-x)*height, 0);
      if (millis() > users.getLastOSC(user) + 500) {
        users.addStroke(user);
      } 
      users.addPoint(user, v, millis()-time);
      users.setLastOSC(user, millis());
    }
  }
}


void mouseDragged() {
  PVector v = new PVector(mouseX, mouseY, 0);
  if (millis() > users.getLastOSC("mouse") + 500) {
    users.addStroke("mouse");
  } 
  users.addPoint("mouse", v, millis()-time);
  users.setLastOSC("mouse", millis());
}

void keyPressed() {

  if (keyCode == UP) {
    users.nextColor("mouse");
  } else if (keyCode == DOWN) {
    users.prevColor("mouse");
  } else if (keyCode == RIGHT) {
    users.nextStyle("mouse");
  } else if (keyCode == LEFT) {
    users.prevStyle("mouse");
  } else if (key == ' ') {
    users.clearEverything();
  } else if (key == 'x') {
    users.clearLast("mouse");
  } else if (key == 'r' || key == 'R') {
    replay();
  } else if (key == 's' || key =='S') {
    screenshot();
  } else if (key == 'd' || key == 'D') {
    users.toggleDripping("mouse");
  } else if (key == 'n' || key == 'N') {
    users.nextStyle("mouse");
  } else if (key == 'e' || key == 'E') {
    users.toggleEffects("mouse");
  } else if (key == 'l' || key == 'L') {
    toggleLoop();
  } else if (key == 'p' || key == 'P') {
    users.prevStyle("mouse");
  } else if (key == 'i' || key == 'I') {
    replayInterval();
  } else if (key == 'v' || key == 'V') {
    users.toggleMenu("mouse");
  } else if (key== 'k' || key == 'K') {
    export = true;
  } else if (key== 'h' || key == 'H') {
    help = !help;
  }
}


void replay() {
  interval = false;
  time = millis();
}

void replayInterval() {
  interval = true;
  time = millis();
}


void toggleLoop() {
  loop = !loop;
}


void screenshot() {
  String filename = year()+"-"+nf(month(), 2)+"-"+nf(day(), 2)+"_"+nf(hour(), 2)+"-"+nf(minute(), 2)+"-"+nf(second(), 2)+"_"+nf(millis(), 2);
  saveFrame(sketchPath("screenshots")+"/"+filename+".png");
}

