package ec.com.jaapz.controlador;

import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.control.RadioButton;
import javafx.scene.control.ToggleGroup;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;

public class LecturasResultadosC {
	@FXML private AnchorPane ventana;
	@FXML private VBox vboxGroup;
	@FXML private Button btnVisualizarInforme;
	
	String estilo = "-fx-font-family: 'Yu Mincho Demibold'; -fx-font-size: 14px;-fx-font-weight: bold;";
	public void initialize() {
		try {
			final ToggleGroup group = new ToggleGroup();
			RadioButton rb1 = new RadioButton("Lecturas no Ingresadas");
			rb1.setToggleGroup(group);
			rb1.setStyle(estilo);
			rb1.setSelected(true);
			RadioButton rb2 = new RadioButton("Lecturas con inconsistencia");
			rb2.setStyle(estilo);
			rb2.setToggleGroup(group);
			RadioButton rb3 = new RadioButton("Problemas de lecturas");
			rb3.setStyle(estilo);
			rb3.setToggleGroup(group);
			vboxGroup.getChildren().addAll(rb1,rb2,rb3);
			vboxGroup.setSpacing(25);
			vboxGroup.setPadding(new Insets(20, 10, 10, 20));
			//ventana.getChildren().add(vboxGroup);
		}catch(Exception ex) {
			ex.printStackTrace();
		}
	}
	public void visualizarInforme(){
		
	}
}
