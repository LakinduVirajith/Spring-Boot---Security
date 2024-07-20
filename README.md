# Spring-Boot---Security

<div style="display: flex;">
    <!-- SPRING BOOT LOGO -->
    <a href="https://spring.io/projects/spring-boot/">
            <img src="https://4.bp.blogspot.com/-ou-a_Aa1t7A/W6IhNc3Q0gI/AAAAAAAAD6Y/pwh44arKiuM_NBqB1H7Pz4-7QhUxAgZkACLcBGAs/s1600/spring-boot-logo.png" alt="SPRING BOOT LOGO" height="55" />
    </a>&nbsp;
    <!-- MYSQL LOGO -->
    <a href="https://www.mysql.com/">
        <img src="https://sujanbyanjankar.com.np/wp-content/uploads/2023/06/mysql-ar21.png" alt="MYSQL LOGO" height="55" />
    </a>
</div>

---

# PROJECT STRUCTURE

## Config

The Config package is where you store configuration classes for your Spring Boot application. These classes are responsible for setting up various configurations, such as database connections, security settings, beans, and other application-wide settings. Configurations are usually annotated with @Configuration and contain methods annotated with @Bean. It's a good practice to keep related configuration logic together in this package to maintain a modular and organized codebase.

## Entity

The Entity package holds the classes that represent your application's data model. These classes are typically Java objects that map to the database tables, and they define the structure of the data you want to store or retrieve. These classes are annotated with @Entity, and their properties are mapped to database columns. For example, if you have a User class, it would represent the user entity in your application, and each instance of this class would correspond to a user record in the database.

## Model

In some projects, you may find a separate Model package alongside the Entity package. The Model package usually contains classes that serve as data transfer objects (DTOs) or view models. These classes are used to transfer data between different layers of the application or between the backend and frontend. They are simple Java objects that hold data and do not have any business logic. In some cases, people may use the term Model interchangeably with Entity, so you may not always find a separate Model package in all projects.

## Repository

The Repository package is where you define the interfaces or classes responsible for interacting with the database. These components are responsible for performing CRUD (Create, Read, Update, Delete) operations on the database tables related to your Entity classes. Spring Data JPA, which is a part of Spring Boot, simplifies this by providing powerful repository interfaces that handle common database operations without you having to write much boilerplate code. Repositories are typically annotated with @Repository.

## Service

The Service package contains the business logic of your application. These classes provide services to the rest of the application and act as intermediaries between the controller (which handles HTTP requests) and the repository (which interacts with the database). The service layer is responsible for handling complex business rules, data processing, validation, and coordinating multiple repositories if needed. Services are usually annotated with @Service.

## Event

The "event" package in your Spring Boot project contains classes related to domain-specific events that occur within your application. These events represent significant occurrences or actions specific to your application's business logic. The goal of the "event" package is to organize and manage these events separately from other components, making it easier to handle and respond to them.

---

# SPRING BOOT ANNOTATIONS

## Bean

The @Bean annotation is used to declare methods that produce and define beans, which are objects managed by the Spring container. Beans represent the various components or objects in your application that Spring manages and injects where needed.

#### 1. Creating Beans

When you annotate a method with @Bean, you are telling Spring that this method is responsible for creating and providing a bean. The method should return an instance of the object you want to be managed by Spring.

#### 2. Dependency Injection

Beans are a fundamental concept in the Spring framework that enables dependency injection. When you define a bean using @Bean, you are essentially creating a blueprint for an object, and Spring takes care of instantiating and wiring it up to other components in your application.

#### Example

```
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

@Component
public class AppConfig {

    @Bean
    public MyService myService() {
        return new MyService();
    }
}
```

In this example, we have a configuration class called AppConfig. The method myService() is annotated with @Bean, indicating that it will create and provide a MyService bean. Whenever another component in your application needs an instance of MyService, Spring will use this method to obtain it.

## Configuration

The @Configuration annotation is used to indicate that a class defines one or more beans (components) that should be registered with the Spring application context. It is part of the Spring framework's annotation-based configuration and plays a crucial role in setting up the application's configuration.

Here's a brief overview of how the @Configuration annotation works and its significance

#### 1. Defining Configuration Classes

When you annotate a class with @Configuration, you are telling Spring that this class contains configuration methods that define beans. These beans will be managed and instantiated by the Spring container. These configuration methods are regular Java methods that are annotated with @Bean.

#### 2. Configuration Methods

Inside a @Configuration class, you define one or more methods annotated with @Bean. Each of these methods returns an object that represents a bean. The method name is used as the bean name by default, but you can customize it using the name attribute of the @Bean annotation.

### 3. Bean Scopes

By default, the beans created by the configuration methods are singletons. That means Spring will create and manage only one instance of each bean. However, you can also customize the scope of the bean using the @Scope annotation on the configuration method.

#### 4. Component Scan

To make sure Spring recognizes the @Configuration class and processes its configuration methods, you need to include the package containing the configuration class in the component scanning path. Alternatively, you can specify the configuration class explicitly while creating the application context.

Here's an example of a simple configuration class

```
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class MyConfiguration {

    @Bean
    public MyBean myBean() {
        return new MyBean();
    }

    @Bean(name = "customBeanName")
    public AnotherBean anotherBean() {
        return new AnotherBean();
    }
}
```

In the above example, we have defined a configuration class MyConfiguration using the @Configuration annotation. It has two configuration methods, myBean() and anotherBean(), which define beans of type MyBean and AnotherBean, respectively.

These beans can be then used throughout the application context by injecting them into other components using annotations like @Autowired or by retrieving them directly from the Spring application context.

Using @Configuration along with @Bean provides a powerful way to define and manage beans in a Spring Boot application without the need for XML-based configurations.

## EnableWebSecurity

The @EnableWebSecurity annotation is used to enable and configure the web security features provided by Spring Security in your application. Spring Security is a powerful framework that helps you secure your web applications by handling authentication, authorization, and other security-related tasks.

When you add the @EnableWebSecurity annotation to one of your configuration classes, Spring Boot will automatically configure Spring Security for your web application with some default settings. It essentially activates the Spring Security filter chain, which intercepts incoming HTTP requests and applies security rules based on the configuration.

Here's a basic explanation of the @EnableWebSecurity annotation and its purpose:

#### 1. Enabling Web Security

By adding the @EnableWebSecurity annotation, you are telling Spring to enable the web security features for your application. Without this annotation, Spring Security will not be active, and the security-related configurations you provide won't take effect.

#### 2. Custom Security Configuration

While using @EnableWebSecurity provides basic security configurations out-of-the-box, you can further customize and define your security rules by creating a class that extends WebSecurityConfigurerAdapter. This class can override methods to configure various aspects of web security, such as authentication, authorization, form login, and more.

Here's an example of a basic security configuration class

```
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

@Configuration
@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
            .authorizeRequests()
                .antMatchers("/public/**").permitAll()
                .antMatchers("/admin/**").hasRole("ADMIN")
                .anyRequest().authenticated()
                .and()
            .formLogin()
                .loginPage("/login")
                .defaultSuccessUrl("/dashboard")
                .permitAll()
                .and()
            .logout()
                .logoutSuccessUrl("/login?logout")
                .permitAll();
    }
}
```

In this example, we have created a configuration class WebSecurityConfig that extends WebSecurityConfigurerAdapter. The configure method is overridden to customize the security rules. We have configured rules to permit all requests to URLs starting with "/public/", require the "ADMIN" role for URLs starting with "/admin/", and allow any authenticated user for all other URLs.

Keep in mind that this is just a simple example, and there are many more advanced security features and configurations that Spring Security provides. You can tailor the security rules according to your application's specific requirements.

---

# IMPORTANT METHODS

```
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@Configuration
public class SecurityConfig {

    @Bean
    public PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder(12);
    }
}
```

#### 1. Purpose

The purpose of this method is to provide a PasswordEncoder bean to Spring, which will be used to encode (hash) passwords for user authentication.

#### 2. PasswordEncoder

The PasswordEncoder is a part of the Spring Security framework and is used to securely encode passwords before storing them in the database. When a user registers or changes their password, Spring will use this PasswordEncoder to hash the password for security reasons.

#### 3. BCryptPasswordEncoder

In this case, we are using the BCryptPasswordEncoder, which is a popular and secure implementation of the PasswordEncoder interface. It uses the BCrypt hashing algorithm to generate strong password hashes. The constructor parameter 12 is the strength or log rounds of the BCrypt hashing, which determines the computational cost of hashing. Higher values provide stronger security but also require more processing time.

#### 4. Bean Creation

By annotating the method with @Bean, we are telling Spring that this method creates a bean of type PasswordEncoder, and Spring should manage it. The @Bean annotation ensures that the PasswordEncoder is available for dependency injection throughout the application.

#### 5. Usage

Once this @Bean method is defined in a configuration class (usually annotated with @Configuration), we can use the PasswordEncoder bean in other parts of the application through dependency injection.

```
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    private final PasswordEncoder passwordEncoder;

    @Autowired
    public UserService(PasswordEncoder passwordEncoder) {
        this.passwordEncoder = passwordEncoder;
    }

    public void registerUser(String username, String password) {
        String encodedPassword = passwordEncoder.encode(password);
        // Save the encodedPassword to the database or do other actions
    }
}
```

In this example, the UserService class uses constructor injection to receive the PasswordEncoder bean, and then it can use the encode() method of the PasswordEncoder to securely hash the user's password before storing it or performing other actions.

---

# PROJECT DEPENDENCIES

Spring Web

Spring Data JPA

MySQL Driver

Lombok

Spring Security
