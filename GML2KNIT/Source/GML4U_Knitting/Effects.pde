class Effects {

  ArrayList<EffectParticle> particles;
  float dispersion = 25;

  PGraphics buffer;

  String renderer = OPENGL;

  PVector prev;

  Effects() {
    particles = new ArrayList<EffectParticle>();
    buffer = createGraphics(width, height, renderer);
  }

  void clear() {
    try {
      buffer = createGraphics(width, height, renderer);
      buffer.smooth(3);
      particles.clear();
    }
    catch (Exception e) {
    }
  }

  void add(PVector pos, PVector dir, float speed) {
    dir.set(randomize(dir, dispersion));
    particles.add(new EffectParticle(pos, dir, speed, (int) random(5, 10)));
  }

  void draw() {


    for (int i=0; i<particles.size (); i++) {
      particles.get(i).update();
      if (!particles.get(i).stopped) {
        drawParticle(g, particles.get(i));
      } else {
        particles.remove(i);
      }
    }
  }


  PVector randomize(PVector position, float radius) {
    PVector v = new PVector();
    v.set(position);
    float angle = random(TWO_PI);
    float distance = random(radius);
    v.x += cos(angle)*distance;
    v.y += sin(angle)*distance;
    return v;
  }


  void drawParticle(PGraphics pg, EffectParticle p) {
    pg.pushStyle();
    pg.noStroke();
    pg.fill(255, 100);

    pg.ellipse(p.pos.x, p.pos.y, p.size, p.size);

    pg.popStyle();
  }


  void addParticle(PVector v) {

    if (null == prev) {
      prev = new PVector();
      prev.set(v);
    }
    /////////////////////

    PVector o = new PVector();
    o.set(prev);

    PVector d = new PVector();
    d.set(v);

    PVector delta = PVector.sub(d, o);
    o.add(delta);

    delta.mult(2);  
    d.add(delta);

    for (int i=0; i<10; i++) {
      // TODO Speed based on distance between current and previous positions
      float distance = 5; 
      float speed = .1;
      add(o, d, speed);
    }
    /////////////////////
    prev.set(v);
  }
}

