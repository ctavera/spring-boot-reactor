package da.springframework.springbootreactor.bootstrap;

import da.springframework.springbootreactor.model.Comment;
import da.springframework.springbootreactor.model.User;
import da.springframework.springbootreactor.model.UserComment;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.ArrayList;
import java.util.List;

@Slf4j
@Component
public class SpringBootReactorBootstrap implements CommandLineRunner {

    @Override
    public void run(String... args) throws Exception {

//        iterableExample();
//        flatMapExample();
//        toStringExample();
//        collectListExample();
        userCommentFlatMapExample();
    }

    public void iterableExample() throws Exception {

        List<String> userNames = new ArrayList<>();
        userNames.add("Andrew Garfield");
        userNames.add("Peter Parker");
        userNames.add("Marie Curie");
        userNames.add("Derek Smith");
        userNames.add("Jhon Doe");
        userNames.add("Bruce Lee");
        userNames.add("Bruce Willis");

        //Creates an Stream Flux Observable
//        Flux<String> names = Flux.just("Andrew Garfield", "Peter Parker", "Marie Curie", "Derek Smith", "Jhon Doe", "Bruce Lee", "Bruce Willis");
        Flux<String> names = Flux.fromIterable(userNames);

        Flux<User> users = names.map(name -> new User(name.split(" ")[0].toUpperCase(), name.split(" ")[1].toUpperCase()))
                .filter(user -> user.getFirstName().equalsIgnoreCase("bruce"))
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

    public void flatMapExample() throws Exception {

        List<String> userNames = new ArrayList<>();
        userNames.add("Andrew Garfield");
        userNames.add("Peter Parker");
        userNames.add("Marie Curie");
        userNames.add("Derek Smith");
        userNames.add("Jhon Doe");
        userNames.add("Bruce Lee");
        userNames.add("Bruce Willis");

        //Creates an Stream Flux Observable
        Flux.fromIterable(userNames)
                .map(name -> new User(name.split(" ")[0].toUpperCase(), name.split(" ")[1].toUpperCase()))
                .flatMap(user -> {
                    if(user.getFirstName().equalsIgnoreCase("bruce")){
                        return Mono.just(user);
                    } else {
                        return Mono.empty();
                    }
                })
                .map(user -> {
                    String name = user.getFirstName().toLowerCase();
                    user.setFirstName(name);
                    return user;
                })
                .subscribe(user -> log.info(user.toString()));
    }

    public void toStringExample() throws Exception {

        List<User> users = new ArrayList<>();
        users.add(new User("Andrew", "Garfield"));
        users.add(new User("Peter", "Parker"));
        users.add(new User("Marie", "Curie"));
        users.add(new User("Derek", "Smith"));
        users.add(new User("Jhon", "Doe"));
        users.add(new User("Bruce", "Lee"));
        users.add(new User("Bruce", "Willis"));

        //Creates an Stream Flux Observable
        Flux.fromIterable(users)
                .map(user -> user.getFirstName().toUpperCase().concat(" ").concat(user.getLastName().toUpperCase()))
                .flatMap(name -> {
                    if(name.contains("bruce".toUpperCase())){
                        return Mono.just(name);
                    } else {
                        return Mono.empty();
                    }
                })
                .map(name -> name.toLowerCase())
                .subscribe(name -> log.info(name));
    }

    public void collectListExample() throws Exception {

        List<User> users = new ArrayList<>();
        users.add(new User("Andrew", "Garfield"));
        users.add(new User("Peter", "Parker"));
        users.add(new User("Marie", "Curie"));
        users.add(new User("Derek", "Smith"));
        users.add(new User("Jhon", "Doe"));
        users.add(new User("Bruce", "Lee"));
        users.add(new User("Bruce", "Willis"));

        //Creates an Stream Flux Observable
        Flux.fromIterable(users)
                .collectList()
                .subscribe(list -> {
                    list.forEach(item -> {
                        log.info(item.toString());
                    });
                });
    }

    public void userCommentFlatMapExample() throws Exception {

        Mono<User> userMono = Mono.fromCallable(() -> new User("Jhon", "Doe"));

        Mono<Comment> commentMono = Mono.fromCallable(() -> {
            Comment comment = new Comment();
            comment.addComment("This is an example");
            comment.addComment("of combine two Flux");
            comment.addComment("on one Flux");

            return comment;
        });

        userMono.flatMap(user -> commentMono.map(comment -> new UserComment(user, comment)))
                .subscribe(userComment -> log.info(userComment.toString()));
    }
}
