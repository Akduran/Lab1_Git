import java.util.concurrent.Semaphore;

class BoundedBuffer {
  final int size = 10;
  double[] buffer = new double[size];
  int inBuf = 0, outBuf = 0;
  Semaphore mutex = new Semaphore(1,true);
  Semaphore numFilled = new Semaphore(0,true);
  Semaphore numEmpty = new Semaphore(size,true);

  public void deposit(double value) throws InterruptedException {
    numEmpty.acquire();        // wait if buffer is full
    mutex.acquire();           // ensures mutual exclusion
    buffer[inBuf] = value; // update the buffer
    inBuf = (inBuf + 1) % size;
    mutex.release();
    numFilled.release();       // notify any waiting consumer
  }

  public double fetch() throws InterruptedException {
    double value;
    numFilled.acquire();       // wait if buffer is empty
    mutex.acquire();           // ensures mutual exclusion
    value = buffer[outBuf]; //read from buffer
    outBuf = (outBuf + 1) % size;
    mutex.release();
    numEmpty.release();        // notify any waiting producer
    return value;
  }
}

