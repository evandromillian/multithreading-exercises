package br.ev.multithreading.tasks;

import java.util.Queue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

/**
 * Created by evandromillian on 29/03/2017.
 */
public class ConsumerTask implements Runnable {

    private final BlockingQueue<Long> primosQueue;
    private final BlockingQueue<Long> fibosQueue;
    private final CountDownLatch latch;

    public ConsumerTask(BlockingQueue<Long> primosQueue, BlockingQueue<Long> fibosQueue, CountDownLatch latch) {
        this.primosQueue = primosQueue;
        this.fibosQueue = fibosQueue;
        this.latch = latch;
    }

    @Override
    public void run() {
        boolean proximoPrimo = true;
        boolean proximoFibo = true;
        Long primeNum = Long.valueOf(0);
        Long fiboNum = Long.valueOf(0);

        while(latch.getCount() > 0) {
            try {
                primeNum = proximoPrimo ? primosQueue.poll(1000, TimeUnit.MILLISECONDS)
                                        : primeNum;
                fiboNum = proximoFibo   ? fibosQueue.poll(1000, TimeUnit.MILLISECONDS)
                                        : fiboNum;

                if (primeNum == null || fiboNum == null) {
                    continue;
                }

                if (fiboNum.equals(primeNum)) {
                    System.out.format("Achado numero %d igual\n", primeNum);
                }

                proximoFibo = fiboNum <= primeNum;
                proximoPrimo = primeNum <= fiboNum;

            } catch(Throwable tw) {
                System.out.println(tw);
            }
        }
    }
}
