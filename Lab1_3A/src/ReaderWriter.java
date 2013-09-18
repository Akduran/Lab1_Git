import java.util.concurrent.Semaphore;

public class ReaderWriter {
	
	Semaphore mutex = new Semaphore(1);
	Semaphore wlock = new Semaphore(1);
	
	public void startRead() throws InterruptedException{
		mutex.acquire();
		mutex.release();
		
	}

}
