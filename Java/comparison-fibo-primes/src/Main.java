import com.despegar.recruiting.util.FibonacciGenerator;
import com.despegar.recruiting.util.PrimeGenerator;
import java.util.concurrent.*;

public class Main {

    public static void main(String[] args) throws InterruptedException {

        final ArrayBlockingQueue<Long> fibos = new ArrayBlockingQueue<Long>(1000);
        Thread fiboThread = new Thread(new Runnable() {
            @Override
            public void run() {
                FibonacciGenerator gen = new FibonacciGenerator();
                Long f;
                while (gen.hasNext() &&  (f = gen.next()) < 1000000) {
                    fibos.offer(f);
                    //System.out.format("Escrevi fibo %d\n", f);
                }
            }
        });

        final ArrayBlockingQueue<Long> primes = new ArrayBlockingQueue<Long>(1000);
        Thread primeThread = new Thread(new Runnable() {
            @Override
            public void run() {
                PrimeGenerator gen = new PrimeGenerator();
                Long f;
                while (gen.hasNext() &&  (f = gen.next()) < 1000010) {
                    primes.offer(f);
                    //System.out.format("Escrevi primo %d\n", f);
                }
            }
        });


        Thread consumerThread = new Thread(new Runnable() {
            @Override
            public void run() {
                boolean proximoPrimo = true;
                boolean proximoFibo = true;
                Long primeNum = Long.valueOf(0);
                Long fiboNum = Long.valueOf(0);
                while(primeNum < 1000000) {
                    try {
                        if (proximoPrimo) {
                            primeNum = primes.poll(1000, TimeUnit.MILLISECONDS);
                            proximoPrimo = false;
                        }
                        if (proximoFibo) {
                            fiboNum = fibos.poll(1000, TimeUnit.MILLISECONDS);
                            proximoFibo = false;
                        }
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }

                    try {
                        if (fiboNum < primeNum) {
                            proximoFibo = true;
                            //System.out.println("Passou em fibo " + fiboNum + " e primo " + primeNum);
                            continue;
                        }
                        if (primeNum < fiboNum) {
                            proximoPrimo = true;
                            //System.out.println("Passou em primo " + primeNum + " e fibo " + fiboNum);
                            continue;
                        }
                        if (fiboNum.equals(primeNum)) {
                            System.out.format("Achado numero %d igual\n", primeNum);
                            proximoFibo = true;
                            proximoPrimo = true;
                        }

                    } catch(Throwable tw) {

                    }
                }

            }
        });

        fiboThread.start();
        primeThread.start();
        consumerThread.start();

        fiboThread.join();
        primeThread.join();
        consumerThread.join();
    }
}
