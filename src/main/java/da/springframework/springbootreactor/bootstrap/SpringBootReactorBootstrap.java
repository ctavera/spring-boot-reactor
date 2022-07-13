package da.springframework.springbootreactor.bootstrap;

import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Flux;

@Component
public class SpringBootReactorBootstrap implements CommandLineRunner {

    @Override
    public void run(String... args) throws Exception {
        //Creates an Stream Flux Observable
        Flux<String> names = Flux.just("Andrew", "Peter", "Derek", "Jhon")
                .doOnNext(System.out::println); //name -> System.out.println(name)

        names.subscribe();
    }
}
