package pl.sulazula.autolib.impl;

import javafx.fxml.FXMLLoader;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.io.InputStream;

@Component
public class SpringFXMLLoader {

    private final ApplicationContext context;

    public SpringFXMLLoader(ApplicationContext context) {

        this.context = context;
    }

    public Object load(String fxml) {
        try (InputStream fxmlStream = getClass().getResourceAsStream(fxml)) {
            FXMLLoader loader = new FXMLLoader();
            loader.setControllerFactory(context::getBean);
            loader.setLocation(getClass().getResource(fxml));

            return loader.load(fxmlStream);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

}
