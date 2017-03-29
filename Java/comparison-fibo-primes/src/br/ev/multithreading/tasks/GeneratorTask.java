package br.ev.multithreading.tasks;

import java.util.Iterator;
import java.util.Queue;
import java.util.concurrent.CountDownLatch;

/**
 * Created by evandromillian on 29/03/2017.
 */
public class GeneratorTask implements Runnable {

    private final Iterator<Long> generator;
    private final Queue<Long> queue;
    private final long numElements;
    private final CountDownLatch latch;

    public GeneratorTask(Iterator<Long> gen, Queue<Long> queue, CountDownLatch latch, long numElements) {
        this.generator = gen;
        this.queue = queue;
        this.latch = latch;
        this.numElements = numElements;
    }

    @Override
    public void run() {
        Long f;
        while (generator.hasNext() &&  (f = generator.next()) < numElements) {
            queue.offer(f);
            //System.out.format("Escrevi fibo %d\n", f);
        }

        latch.countDown();
    }
}
