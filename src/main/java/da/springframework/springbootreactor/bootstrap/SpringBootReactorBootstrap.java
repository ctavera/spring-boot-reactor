package da.springframework.springbootreactor.bootstrap;

import da.springframework.springbootreactor.model.User;
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
        Flux<User> names = Flux.just("Andrew", "Peter", "Marie", "Derek", "Jhon")
                .map(name -> new User(name.toUpperCase(), null))
                .doOnNext(user -> {
                    if (user == null) {
                        throw new RuntimeException("Nombres no pueden ser vacíos.");
                    }
                    System.out.println(user.getFirstName());
                })
                .map(user -> {
                    String name = user.getFirstName().toLowerCase();
                    user.setFirstName(name);
                    return user;
                });

        names.subscribe(user -> log.info(user.toString()),
                error -> log.error(error.getMessage()),
                () -> log.info("Ha finalizado la ejecución del observable con éxito!")); //new Runnable() {@Override public void run() {...}});
    }
}
