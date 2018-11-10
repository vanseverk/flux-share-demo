package be.reactiveprogrammming.demo.fluxsharing;

import java.time.Duration;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;
import reactor.core.publisher.Flux;

public class FluxShareTestApplication {

  public static void main(String[] args) throws InterruptedException {
    fluxShareMultipleSubscribers(50000);
  }

  private static void fluxShareMultipleSubscribers(int numberOfSubscriptions) throws InterruptedException {
    Flux<Long> startFlux = Flux.interval(Duration.ofMillis(1000)).share();
    
    for (int i = 0; i < numberOfSubscriptions; i++) {
      final int subscriptionNumber = i;
      Flux outputFlux = Flux.from(startFlux);
      outputFlux.subscribe(out -> System.out.println("Flux " + subscriptionNumber + " " + out));
    }

    new CountDownLatch(1).await(100, TimeUnit.SECONDS);
  }

}
