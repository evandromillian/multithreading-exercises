package br.ev.multithreading.tasks;

import java.util.Iterator;
import java.util.Queue;

/**
 * Created by evandromillian on 29/03/2017.
 */
public class GeneratorTask implements Runnable {

    private final Iterator<Long> generator;
    private final Queue<Long> queue;
    private final long numElements;

    public GeneratorTask(Iterator<Long> gen, Queue<Long> queue, long numElements) {
        this.generator = gen;
        this.queue = queue;
        this.numElements = numElements;
    }

    @Override
    public void run() {
        Long f;
        while (generator.hasNext() &&  (f = generator.next()) < numElements) {
            queue.offer(f);
            //System.out.format("Escrevi fibo %d\n", f);
        }

        queue.offer(Long.valueOf(-1));
    }
}
