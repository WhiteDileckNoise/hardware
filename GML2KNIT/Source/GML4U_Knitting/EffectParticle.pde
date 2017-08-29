class EffectParticle {

  PVector origin = new PVector();
  PVector pos = new PVector();
  PVector dest = new PVector();
  float speed; 
  boolean stopped;
  int size;

  EffectParticle(PVector origin, PVector dest, float speed, int size) {
    this.origin.set(origin);
    this.pos.set(origin);
    this.dest.set(dest);
    this.speed = speed;
    this.stopped = false;
    this.size = size;
  }

  void update() {
    pos.lerp(dest, speed);
    if (dest.dist(pos) < 1) {
      stopped = true;
    }
  }
}

