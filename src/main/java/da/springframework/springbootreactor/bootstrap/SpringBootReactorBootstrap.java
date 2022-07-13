package da.springframework.springbootreactor.bootstrap;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Flux;

@Slf4j
@Component
public class SpringBootReactorBootstrap implements CommandLineRunner {

    @Override
    public void run(String... args) throws Exception {
        //Creates an Stream Flux Observable
        Flux<String> names = Flux.just("Andrew", "Peter", "", "Derek", "Jhon")
                .doOnNext(name -> {
                    if (name.isEmpty()) {
                        throw new RuntimeException("Nombres no pueden ser vacíos.");
                    }
                    System.out.println(name);
                });

        names.subscribe(name -> log.info(name), error -> log.error(error.getMessage()));
    }
}
