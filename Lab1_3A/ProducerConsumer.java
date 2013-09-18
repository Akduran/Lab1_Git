import java.util.Random;

class Producer implements Runnable {
  BoundedBuffer bb = null;
  public Producer(BoundedBuffer initbb) {
    bb = initbb;
    new Thread(this).start();
  }

  public void run() {
    double item;
    Random r = new Random();
    while (true) {
      try {
        item = r.nextDouble();
        System.out.println("produced item " + item);
        bb.deposit(item);
        Thread.sleep(50);
      } catch (InterruptedException e) {}
    }
  }
}

class Consumer implements Runnable {
  BoundedBuffer bb = null;
  public Consumer(BoundedBuffer initbb) {
    bb = initbb;
    new Thread(this).start();
  }

  public void run() {
    double item;
    while (true) {
      try {
        item = bb.fetch();
        System.out.println("fetched item " + item);
        Thread.sleep(50);
      } catch (InterruptedException e) {}
    }
  }
}

class ProducerConsumer {
  public static void main(String[] args) {
    BoundedBuffer buffer = new BoundedBuffer();
    Producer producer = new Producer(buffer);
    Consumer consumer = new Consumer(buffer);
  }
}

