package ru.danmax.soa_lab2_first_service;

import jakarta.ws.rs.ApplicationPath;
import jakarta.ws.rs.core.Application;
import ru.danmax.soa_lab2_first_service.resources.DragonResource;

import java.util.HashSet;
import java.util.Set;

/**
 * Configures RESTful Web Services for the application.
 */
@ApplicationPath("")
public class RestConfiguration extends Application {
    private Set<Object> singletons = new HashSet<Object>();
    private Set<Class<?>> empty = new HashSet<Class<?>>();

    public RestConfiguration() throws ClassNotFoundException {
        Class.forName("org.postgresql.Driver");
        singletons.add(new HelloWorldResource());
        singletons.add(new DragonResource());
        singletons.add(new CorsFilter());
    }

    @Override
    public Set<Class<?>> getClasses() {
        return empty;
    }

    @Override
    public Set<Object> getSingletons() {
        return singletons;
    }
}
