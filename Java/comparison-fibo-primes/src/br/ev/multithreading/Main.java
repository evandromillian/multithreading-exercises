package br.ev.multithreading;

import br.ev.multithreading.tasks.ConsumerTask;
import br.ev.multithreading.tasks.GeneratorTask;
import com.despegar.recruiting.util.FibonacciGenerator;
import com.despegar.recruiting.util.PrimeGenerator;

import java.util.concurrent.*;


public class Main {

    public static void main(String[] args) throws InterruptedException {
        BlockingQueue<Long> fibosQueue = new ArrayBlockingQueue<Long>(100);
        BlockingQueue<Long> primosQueue = new ArrayBlockingQueue<Long>(100);
        CountDownLatch latch = new CountDownLatch(2);

        ExecutorService ex = Executors.newCachedThreadPool();
        ex.execute(new GeneratorTask(new FibonacciGenerator(), fibosQueue, latch, 1000000));
        ex.execute(new GeneratorTask(new PrimeGenerator(), primosQueue, latch, 1000000));
        ex.execute(new ConsumerTask(primosQueue, fibosQueue, latch));

        ex.shutdown();
    }
}
