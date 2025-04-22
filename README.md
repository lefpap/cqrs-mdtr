# cqrs-mdtr (CQRS - Mediator)

A lightweight Java library to simplify building applications based on the Command Query 
Responsibility Segregation (CQRS) pattern, utilizing the mediator pattern to dispatch 
commands and queries effectively.

## Overview

This library provides clear separation of concerns, enabling a clean and maintainable architecture. It includes interfaces and default implementations to handle commands, queries, and contexts, along with flexible ways to register handlers.

Key benefits:

- Simplifies application design by clearly separating commands (state changes) from queries (data retrieval).
- Decouples your business logic from infrastructure concerns.
- Facilitates better testability and maintainability.

## Installation

Add this library as a dependency in your Maven project:

```xml
<dependency>
    <groupId>com.github.lefpap</groupId>
    <artifactId>cqrs-mdtr</artifactId>
    <version>1.0-SNAPSHOT</version>
</dependency>
```

## Usage

### Defining Commands and Queries

Define your business operations clearly using Command and Query interfaces:

```java
// A command example (represents a state-changing operation)
public record CreateUserCommand(
    String email,
    String password
) implements Command<UserCreatedResult> {}

// A cqrsQuery example (represents a data retrieval operation)
public record GetUserQuery(
    String userId
) implements Query<UserDto> {}
```

### Defining Command and Query Handlers

Implement command/cqrsQuery handlers:

```java
public class CreateUserCommandHandler implements CommandHandler<CreateUserCommand, UserCreatedResult> {

    @Override
    public UserCreatedResult handle(CreateUserCommand command) {
        // your business logic here
        return new UserCreatedResult(/* result data */);
    }
}

public class GetUserQueryHandler implements QueryHandler<GetUserQuery, UserDto> {

    @Override
    public UserDto handle(GetUserQuery cqrsQuery) {
        // retrieve data here
        return new UserDto(/* result data */);
    }
}
```

### Registering Handlers

Use the built-in registry (`InMemoryHandlerRegistry`) or implement your own:

```java
HandlerRegistry registry = new InMemoryHandlerRegistry();

// Manually registering handlers
registry.registerCommandHandler(CreateUserCommand.class, new CreateUserCommandHandler());
registry.registerQueryHandler(GetUserQuery.class, new GetUserQueryHandler());

// Instantiate mediator
CqrsMediator mediator = new DefaultCqrsMediator(registry);
```

### Dispatching Commands and Queries

Use the mediator to dispatch commands and send queries:

```java
// Dispatch command
CreateUserCommand command = new CreateUserCommand("john@example.com", "john123");
UserCreatedResult result = mediator.dispatch(command);

// Send cqrsQuery
GetUserQuery cqrsQuery = new GetUserQuery("user-123");
UserDto user = mediator.send(cqrsQuery);
```

## Integrating with Spring Boot

Implement command/cqrsQuery handlers using the provided annotations:

```java
@Component
@HandlesCommand(CreateUserCommand.class)
public class CreateUserCommandHandler implements CommandHandler<CreateUserCommand, UserCreatedResult> {

    @Override
    public UserCreatedResult handle(CreateUserCommand command) {
        // your business logic here
        return new UserCreatedResult(/* result data */);
    }
}

@Component
@HandlesQuery(GetUserQuery.class)
public class GetUserQueryHandler implements QueryHandler<GetUserQuery, UserDto> {

    @Override
    public UserDto handle(GetUserQuery cqrsQuery) {
        // retrieve data here
        return new UserDto(/* result data */);
    }
}
```

To integrate this library into your Spring Boot application, you can use the following 
configuration, or create your own:

```java
@Configuration
public class CqrsMediatorConfig {

    @Bean
    @SuppressWarnings({"rawtypes", "unchecked"})
    public HandlerRegistry handlerRegistry(ApplicationContext context) {
        InMemoryHandlerRegistry registry = new InMemoryHandlerRegistry();

        context.getBeansWithAnnotation(HandlesCommand.class).forEach((name, handler) -> {
            HandlesCommand annotation = handler.getClass().getAnnotation(HandlesCommand.class);
            registry.registerCommandHandler((Class) annotation.value(), (CommandHandler) handler);
        });

        context.getBeansWithAnnotation(HandlesQuery.class).forEach((name, handler) -> {
            HandlesQuery annotation = handler.getClass().getAnnotation(HandlesQuery.class);
            registry.registerQueryHandler((Class) annotation.value(), (QueryHandler) handler);
        });

        return registry;
    }

    @Bean
    public CqrsMediator cqrsMediator(HandlerRegistry registry) {
        return new DefaultCqrsMediator(registry);
    }
}
```

Inject and use the mediator within your Spring components:

```java
@RestController
public class UserController {

    private final CqrsMediator mediator;

    public UserController(CqrsMediator mediator) {
        this.mediator = mediator;
    }

    @PostMapping("/users")
    public UserCreatedResult createUser(@RequestBody CreateUserCommand command) {
        return mediator.dispatch(command);
    }

    @GetMapping("/users/{id}")
    public UserDto getUser(@PathVariable String id) {
        return mediator.send(new GetUserQuery(id));
    }
}
```

## Contributing
If you'd like to contribute:

- Fork this repository.
- Create a branch (feature/<feature-name>, fix/<bug-fix>).
- Make your changes and commit clearly.
- Open a Pull Request explaining your changes.

## License

This project is licensed under the MIT License – see the LICENSE file for details.