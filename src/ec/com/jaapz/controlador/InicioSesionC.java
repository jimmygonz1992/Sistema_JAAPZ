package ec.com.jaapz.controlador;

import java.util.List;
import java.util.Optional;

import ec.com.jaapz.modelo.SegUsuario;
import ec.com.jaapz.modelo.SegUsuarioDAO;
import ec.com.jaapz.util.Context;
import ec.com.jaapz.util.ControllerHelper;
import ec.com.jaapz.util.Encriptado;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.control.Tooltip;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.stage.Stage;


public class InicioSesionC {
	Tooltip toolTip;
	@FXML private Button btnCancelar;
	@FXML private Button btnAceptar;
	@FXML private TextField txtUsuario;
	@FXML private PasswordField txtClave;
	ControllerHelper helper = new ControllerHelper();
	Encriptado encriptado = new Encriptado();
	private Stage stage;
	@FXML private ImageView ivLogo;
	@FXML private ImageView ivLogin;
	public void setDialogStage(Stage stage) {
        this.stage = stage;
    }
	public void aceptar() throws Exception {
		if(validarDatos() == false)
			return;
		SegUsuarioDAO usuarioDAO = new SegUsuarioDAO();
		List<SegUsuario> usuario;
		usuario = usuarioDAO.getUsuario(encriptado.Encriptar(txtUsuario.getText()),encriptado.Encriptar(txtClave.getText()));
		if(usuario.size() == 1){
			Context.getInstance().setUsuariosC(usuario.get(0));
			Context.getInstance().setPerfil(usuario.get(0).getSegPerfil().getNombre());
			Context.getInstance().setUsuario(encriptado.Desencriptar(usuario.get(0).getUsuario()));
			Context.getInstance().setIdUsuario(usuario.get(0).getIdUsuario());
			Context.getInstance().setIdPerfil(usuario.get(0).getSegPerfil().getIdPerfil());
			helper.abrirPantallaPrincipal("Principal","/principal/Contenido.fxml",stage);
		}
		else{
			helper.mostrarAlertaError("Clave o Usuario Incorrecto!!!",stage);
		}
	}
	boolean validarDatos() {
		boolean bandera = false;
		if(txtUsuario.getText().equals("")) {
			helper.mostrarAlertaAdvertencia("Debe de Ingresar Usuario", Context.getInstance().getStage());
			txtUsuario.requestFocus();
			return false;
		}
		if(txtClave.getText().equals("")) {
			helper.mostrarAlertaAdvertencia("Debe de Ingresar Clave", Context.getInstance().getStage());
			txtClave.requestFocus();
			return false;
		}
		bandera = true;
		return bandera;
	}
	public void cancelar(){
		Optional<ButtonType> result = helper.mostrarAlertaConfirmacion("Realmente Desea Salir?",stage);
		if(result.get() == ButtonType.OK)
			System.exit(0);
	}
	public void initialize(){
		toolTip = new Tooltip("Ingrese Nombre de usuario");
		txtUsuario.setTooltip(toolTip);
		toolTip = new Tooltip("Ingrese Clave del usuario");
		txtClave.setTooltip(toolTip);
		toolTip = new Tooltip("Aceptar");
		btnAceptar.setTooltip(toolTip);
		toolTip = new Tooltip("Cancelar");
		btnCancelar.setTooltip(toolTip);
		Image image = new Image("logo2.png");
		ivLogo.setImage(image);
		//ivLogo.setFitHeight(100);
		//ivLogo.setFitHeight(100);
		Image image1 = new Image("login.png");
		ivLogin.setImage(image1);
		txtUsuario.setOnKeyPressed(new EventHandler<KeyEvent>() {
			@Override
			public void handle(KeyEvent event) {
				if(event.getCode().equals(KeyCode.ENTER))
					try {
						aceptar();
					} catch (Exception e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
			}
		});
		txtClave.setOnKeyPressed(new EventHandler<KeyEvent>() {
			@Override
			public void handle(KeyEvent event) {
				if(event.getCode().equals(KeyCode.ENTER))
					try {
						aceptar();
					} catch (Exception e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
			}
		});
		txtUsuario.setText("sa");
		txtClave.setText("sa");
	}
}