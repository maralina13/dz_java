package ru.itis.shop.app;

import jakarta.servlet.ServletContext;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRegistration;
import org.springframework.web.WebApplicationInitializer;
import org.springframework.web.context.ContextLoaderListener;
import org.springframework.web.context.support.AnnotationConfigWebApplicationContext;
import org.springframework.web.servlet.DispatcherServlet;
import ru.itis.shop.config.ApplicationConfig;

public class ApplicationInitializer implements WebApplicationInitializer {

    @Override
    public void onStartup(ServletContext servletContext) throws ServletException {
        AnnotationConfigWebApplicationContext springContext = new AnnotationConfigWebApplicationContext();
        springContext.register(ApplicationConfig.class);
        ContextLoaderListener contextLoaderListener = new ContextLoaderListener(springContext);
        servletContext.addListener(contextLoaderListener);
        ServletRegistration.Dynamic dispatcher = servletContext.addServlet("dispatcher",
                new DispatcherServlet(springContext));
        dispatcher.setLoadOnStartup(1);
        dispatcher.addMapping("/");
    }
}
