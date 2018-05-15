package org.mavenTest.mavenWebApp.restTest;

import java.util.HashSet;
import java.util.Set;

import javax.ws.rs.core.Application;

/* DON'T REALLY NEED THAT FILE */

public class RestApplication extends Application {
    @Override
    public Set<Class<?>> getClasses() {
        Set<Class<?>> classes = new HashSet<Class<?>>();
        classes.add(RestService.class);
        return classes;
    }

}
