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
        Flux<String> names = Flux.just("Andrew Garfield", "Peter Parker", "Marie Curie", "Derek Smith", "Jhon Doe", "Bruce Lee", "Bruce Willis");

        Flux<User> users = names.map(name -> new User(name.split(" ")[0].toUpperCase(), name.split(" ")[1].toUpperCase()))
                .filter(user -> user.getFirstName().toLowerCase().equals("bruce"))
                .doOnNext(user -> {
                    if (user == null) {
                        throw new RuntimeException("Nombres no pueden ser vacíos.");
                    }
                    System.out.println(user.getFirstName().concat(" ").concat(user.getLastName()));
                })
                .map(user -> {
                    String name = user.getFirstName().toLowerCase();
                    user.setFirstName(name);
                    return user;
                });

        users.subscribe(user -> log.info(user.toString()),
                error -> log.error(error.getMessage()),
                () -> log.info("Ha finalizado la ejecución del observable con éxito!")); //new Runnable() {@Override public void run() {...}});
    }
}
