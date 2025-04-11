package pl.sulazula.autolib;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.context.ConfigurableApplicationContext;

@SpringBootApplication
public class CarlibApplication extends Application {

	private ConfigurableApplicationContext applicationContext;

	public static void main(String[] args) {
		/*SpringApplication.run(CarlibApplication.class, args);*/
		launch(args);
	}

	@Override
	public void start(Stage stage) throws Exception {
		FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/repair_form.fxml"));

		loader.setControllerFactory(applicationContext::getBean);

		Parent root = loader.load();
		Scene scene = new Scene(root);

		stage.setScene(scene);
		stage.setTitle("carLib App");
		stage.setWidth(500);
		stage.setHeight(500);
		stage.setResizable(false);
		stage.show();
	}

	@Override
	public void init() throws Exception {
		applicationContext = new SpringApplicationBuilder(CarlibApplication.class).run();
	}

	@Override
	public void stop() throws Exception {
		applicationContext.close();
	}
}
