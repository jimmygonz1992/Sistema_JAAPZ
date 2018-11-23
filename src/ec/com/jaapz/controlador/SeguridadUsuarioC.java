package ec.com.jaapz.controlador;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import ec.com.jaapz.modelo.SegPerfil;
import ec.com.jaapz.modelo.SegPerfilDAO;
import ec.com.jaapz.modelo.SegUsuario;
import ec.com.jaapz.modelo.SegUsuarioDAO;
import ec.com.jaapz.util.Context;
import ec.com.jaapz.util.ControllerHelper;
import ec.com.jaapz.util.Encriptado;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.stage.FileChooser;

public class SeguridadUsuarioC {
	@FXML private TextField txtCodigo;
	@FXML private TextField txtCedula;
	@FXML private TextField txtNombres;
	@FXML private TextField txtApellidos;
	@FXML private CheckBox chkEstado;
	@FXML private ComboBox<SegPerfil> cboPerfil;
	@FXML private TextField txtCargo;
	@FXML private TextField txtTelefono;
	@FXML private TextField txtDireccion;
	@FXML private TextField txtUsuario;
	@FXML private PasswordField txtClave;
	@FXML private ImageView ivFoto;
	@FXML private Button btnGrabar;
	@FXML private Button btnNuevo;
	@FXML private Button btnExaminar;
	@FXML private Button btnQuitar;
	@FXML private Button btnBuscar;
	@FXML private Label lbCodigo;

	ControllerHelper helper = new ControllerHelper();
	Encriptado encriptado = new Encriptado();
	SegUsuarioDAO segUsuarioDAO = new SegUsuarioDAO();
	SegPerfilDAO perfilDAO = new SegPerfilDAO();

	public void initialize(){
		int maxLength = 10;
		limpiar();
		Context.getInstance().setUsuarios(null);
		llenarComboPerfil();
		//solo letras mayusculas
		txtNombres.textProperty().addListener(new ChangeListener<String>() {
			@Override
			public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
				// TODO Auto-generated method stub
				String cadena = txtNombres.getText().toUpperCase();
				txtNombres.setText(cadena);
			}
		});
		txtDireccion.textProperty().addListener(new ChangeListener<String>() {
			@Override
			public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
				// TODO Auto-generated method stub
				String cadena = txtDireccion.getText().toUpperCase();
				txtDireccion.setText(cadena);
			}
		});
		txtCargo.textProperty().addListener(new ChangeListener<String>() {
			@Override
			public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
				// TODO Auto-generated method stub
				String cadena = txtCargo.getText().toUpperCase();
				txtCargo.setText(cadena);
			}
		});
		txtApellidos.textProperty().addListener(new ChangeListener<String>() {
			@Override
			public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
				// TODO Auto-generated method stub
				String cadena = txtApellidos.getText().toUpperCase();
				txtApellidos.setText(cadena);
			}
		});
		//validar solo numeros
		txtCedula.textProperty().addListener(new ChangeListener<String>() {
			@Override
			public void changed(ObservableValue<? extends String> observable, String oldValue, 
					String newValue) {
				if (newValue.matches("\\d*")) {
					//int value = Integer.parseInt(newValue);
				} else {
					txtCedula.setText(oldValue);
				}
			}
		});
		txtTelefono.textProperty().addListener(new ChangeListener<String>() {
			@Override
			public void changed(ObservableValue<? extends String> observable, String oldValue, 
					String newValue) {
				if (newValue.matches("\\d*")) {
					//int value = Integer.parseInt(newValue);
				} else {
					txtTelefono.setText(oldValue);
				}
			}
		});
		//validar solo 10 valores
		txtCedula.textProperty().addListener(new ChangeListener<String>() {
			@Override
			public void changed(final ObservableValue<? extends String> ov, final String oldValue, final String newValue) {
				if (txtCedula.getText().length() > maxLength) {
					String s = txtCedula.getText().substring(0, maxLength);
					txtCedula.setText(s);
				}
			}
		});

		//repetir cedula en usuario y contraseña
		txtCedula.textProperty().addListener(new ChangeListener<String>() {
			@Override
			public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
				txtUsuario.setText(newValue);
				txtClave.setText(newValue);	
			}
		});

		txtCedula.setOnKeyPressed(new EventHandler<KeyEvent>(){
			@Override
			public void handle(KeyEvent ke){
				if (ke.getCode().equals(KeyCode.ENTER)){
					if (validarCedula(txtCedula.getText()) == false){
						helper.mostrarAlertaAdvertencia("El número de cedula es incorrecto!", Context.getInstance().getStage());
						txtCedula.setText("");
						txtCedula.requestFocus();	
						txtUsuario.setText("");
						txtClave.setText("");
					}else
						recuperarDatos(txtCedula.getText());
						//txtNombres.requestFocus();
				}
			}
		});

		txtTelefono.textProperty().addListener(new ChangeListener<String>() {
			@Override
			public void changed(ObservableValue<? extends String> observable, String oldValue, 
					String newValue) {
				if (newValue.matches("\\d*")) {
					//int value = Integer.parseInt(newValue);
				} else {
					txtTelefono.setText(oldValue);
				}
			}
		});

		txtTelefono.textProperty().addListener(new ChangeListener<String>() {
			@Override
			public void changed(final ObservableValue<? extends String> ov, final String oldValue, final String newValue) {
				if (txtTelefono.getText().length() > maxLength) {
					String s = txtTelefono.getText().substring(0, maxLength);
					txtTelefono.setText(s);
				}
			}
		});

		/*txtEmail.setOnKeyPressed(new EventHandler<KeyEvent>(){
	        @Override
	        public void handle(KeyEvent ke)
	        {
	            if (ke.getCode().equals(KeyCode.ENTER))
	            {
	            	if (ValidarEmail(txtEmail.getText()) == false)
	    			{
	    				helper.mostrarAlertaAdvertencia("El email es incorrecto!", Context.getInstance().getStage());
	    				txt_email.requestFocus();
	    			}
	            }
	        }
	    });*/

		txtUsuario.setEditable(false);
		txtClave.setEditable(false);
		txtCodigo.setVisible(false);
		lbCodigo.setVisible(false);
		chkEstado.setSelected(true);
	}

	public void recuperarDatos(String cedula){
		try{
			List<SegUsuario> listaUsuario = new ArrayList<SegUsuario>();
			listaUsuario = segUsuarioDAO.getRecuperaUsuario(cedula);
			for(int i = 0 ; i < listaUsuario.size() ; i ++) {
				txtCedula.setText(listaUsuario.get(i).getCedula());
				txtNombres.setText(listaUsuario.get(i).getNombre());
				txtApellidos.setText(listaUsuario.get(i).getApellido());
				cboPerfil.setValue(listaUsuario.get(i).getSegPerfil());
				txtCargo.setText(listaUsuario.get(i).getCargo());
				txtTelefono.setText(listaUsuario.get(i).getTelefono());
				txtDireccion.setText(listaUsuario.get(i).getDireccion());
				txtUsuario.setText(encriptado.Desencriptar(listaUsuario.get(i).getUsuario()));
				txtClave.setText(encriptado.Desencriptar(listaUsuario.get(i).getClave()));
				if(listaUsuario.get(i).getFoto() != null) {
					String imgString = new String(listaUsuario.get(i).getFoto(), "UTF-8");
					ivFoto.setImage(helper.getImageFromBase64String(imgString).getImage());
				}
				else {
					Image img = new Image("/usuario.jpg");
					ivFoto.setImage(img);
				}
				
				if(listaUsuario.get(i).getEstado().equals("A"))
					chkEstado.setSelected(true);
				else
					chkEstado.setSelected(false);
			}
		}catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	public void grabar(){
		try {
			String estado;
			if(validarDatos() == false)
				return;
			if(chkEstado.isSelected() == true)
				estado = "A";
			else
				estado = "I";
			SegUsuario usuario = new SegUsuario();
			usuario.setEstado(estado);
			usuario.setSegPerfil(cboPerfil.getSelectionModel().getSelectedItem());
			usuario.setCedula(txtCedula.getText());
			usuario.setNombre(txtNombres.getText());
			usuario.setApellido(txtApellidos.getText());
			usuario.setDireccion(txtDireccion.getText());
			usuario.setTelefono(txtTelefono.getText());
			usuario.setCargo(txtCargo.getText());
			usuario.setUsuario(encriptado.Encriptar(txtUsuario.getText()));
			usuario.setClave(encriptado.Encriptar(txtClave.getText()));
			usuario.setFoto(helper.encodeFileToBase64Binary(ivFoto.getImage()).getBytes());
			Optional<ButtonType> result = helper.mostrarAlertaConfirmacion("Desea Grabar los Datos?",Context.getInstance().getStage());
			if(result.get() == ButtonType.OK){
				usuario.setEstado(estado);
				segUsuarioDAO.getEntityManager().getTransaction().begin();
				if(txtCodigo.getText().equals("0")) {//inserta
					usuario.setIdUsuario(null);
					segUsuarioDAO.getEntityManager().persist(usuario);
				}else {//modifica
					usuario.setIdUsuario(Integer.parseInt(txtCodigo.getText()));
					segUsuarioDAO.getEntityManager().merge(usuario);
				}
				segUsuarioDAO.getEntityManager().getTransaction().commit();
				helper.mostrarAlertaInformacion("Datos Grabados Correctamente", Context.getInstance().getStage());
				limpiar();
			}
		}catch(Exception ex) {
			helper.mostrarAlertaError("Error al grabar", Context.getInstance().getStage());
			segUsuarioDAO.getEntityManager().getTransaction().rollback();
			System.out.println(ex.getMessage());
		}
	}

	boolean validarDatos() {
		try {
			if(txtCedula.getText().equals("")) {
				helper.mostrarAlertaAdvertencia("Debe ingresar cedula del Usuario", Context.getInstance().getStage());
				txtCedula.requestFocus();
				return false;
			}
			if(helper.validarDeCedula(txtCedula.getText()) == false) {
				helper.mostrarAlertaAdvertencia("Cédula Ingresada Incorrecta", Context.getInstance().getStage());
				txtCedula.requestFocus();
				return false;
			}
			if(txtNombres.getText().equals("")) {
				helper.mostrarAlertaAdvertencia("Debe ingresar el nombre del Usuario", Context.getInstance().getStage());
				txtNombres.requestFocus();
				return false;
			}
			if(txtUsuario.getText().equals("")) {
				helper.mostrarAlertaAdvertencia("Debe ingresar un Usuario", Context.getInstance().getStage());
				txtUsuario.requestFocus();
				return false;	
			}
			if(txtClave.getText().equals("")) {
				helper.mostrarAlertaAdvertencia("Debe ingresar una clave para el usuario", Context.getInstance().getStage());
				txtClave.requestFocus();
				return false;	
			}
			if(cboPerfil.getValue() == null) {
				helper.mostrarAlertaAdvertencia("Debe Seleccionar un perfil", Context.getInstance().getStage());
				cboPerfil.requestFocus();
				return false;	
			}
			if(validarUsuario() == true) {
				helper.mostrarAlertaAdvertencia("El usuario ya existe!!", Context.getInstance().getStage());
				txtUsuario.requestFocus();
				return false;	
			}
			return true;
		}catch(Exception ex) {
			System.out.println(ex.getMessage());
			return false;
		}
	}

	boolean validarUsuario() {
		try {
			List<SegUsuario> listaUsuario;
			listaUsuario = segUsuarioDAO.getValidarUsuario(encriptado.Encriptar(txtUsuario.getText()), Integer.parseInt(txtCodigo.getText()));
			if(listaUsuario.size() != 0)
				return true;
			else
				return false;
		}catch(Exception ex) {
			System.out.println(ex.getMessage());
			return false;
		}
	}

	public void nuevo() throws IOException{
		limpiar();
	}

	void limpiar() {
		txtCodigo.setText("0");
		txtCodigo.setEditable(false);
		txtCedula.setText("");
		txtNombres.setText("");
		txtApellidos.setText("");
		chkEstado.setSelected(true);
		txtCargo.setText("");
		txtTelefono.setText("");
		txtDireccion.setText("");
		txtUsuario.setText("");
		txtClave.setText("");
		Image img = new Image("/usuario.jpg");
		ivFoto.setImage(img);
		txtCedula.requestFocus();
	}

	public void buscar() {
		try{
			helper.abrirPantallaModal("/seguridad/ListadoUsuarios.fxml","Listado de Usuarios", Context.getInstance().getStage());
			if (Context.getInstance().getUsuarios() != null) {
				SegUsuario datoSeleccionado = Context.getInstance().getUsuarios();
				llenarDatos(datoSeleccionado);
				Context.getInstance().setUsuarios(null);
			}
		}catch(Exception ex){
			System.out.println(ex.getMessage());
		}
	}

	void llenarDatos(SegUsuario datoSeleccionado){
		try {
			txtCodigo.setText(String.valueOf(datoSeleccionado.getIdUsuario()));
			if(datoSeleccionado.getCedula() == null)
				txtCedula.setText("");
			else
				txtCedula.setText(datoSeleccionado.getCedula());

			if(datoSeleccionado.getNombre() == null)
				txtNombres.setText("");
			else
				txtNombres.setText(datoSeleccionado.getNombre());

			if(datoSeleccionado.getApellido() == null)
				txtApellidos.setText("");
			else
				txtApellidos.setText(datoSeleccionado.getApellido());

			if(datoSeleccionado.getEstado().equals("A")) 
				chkEstado.setSelected(true);
			else
				chkEstado.setSelected(false);

			cboPerfil.getSelectionModel().select(datoSeleccionado.getSegPerfil());;

			if(datoSeleccionado.getCargo() == null)
				txtCargo.setText("");
			else
				txtCargo.setText(datoSeleccionado.getCargo());

			if(datoSeleccionado.getTelefono() == null)
				txtTelefono.setText("");
			else
				txtTelefono.setText(datoSeleccionado.getTelefono());

			if(datoSeleccionado.getDireccion() == null)
				txtDireccion.setText("");
			else
				txtDireccion.setText(datoSeleccionado.getDireccion());

			txtUsuario.setText(encriptado.Desencriptar(datoSeleccionado.getUsuario()));
			txtClave.setText(encriptado.Desencriptar(datoSeleccionado.getClave()));
			if(datoSeleccionado.getFoto() != null) {
				String imgString = new String(datoSeleccionado.getFoto(), "UTF-8");
				ivFoto.setImage(helper.getImageFromBase64String(imgString).getImage());
			}
			else {
				Image img = new Image("/usuario.jpg");
				ivFoto.setImage(img);
			}
		}catch(Exception ex) {
			System.out.println(ex.getMessage());
		}
	}

	public void quitar() {
		Image img = new Image("/usuario.jpg");
		ivFoto.setImage(img);
	}
	public void examinar(){
		try {
			FileChooser fileChooser = new FileChooser();
			fileChooser.setTitle("Buscar Imagen");
			// Agregar filtros para facilitar la busqueda
			fileChooser.getExtensionFilters().addAll(
					new FileChooser.ExtensionFilter("Imagen jpg", "*.jpg"),
					new FileChooser.ExtensionFilter("Imagen png", "*.png")
					);
			// Obtener la imagen seleccionada
			File imgFile = fileChooser.showOpenDialog(Context.getInstance().getStage());
			// Mostar la imagen
			if (imgFile != null) {
				Image image = new Image("file:" + imgFile.getAbsolutePath());
				ivFoto.setImage(image);
			}
		}catch(Exception ex) {
			System.out.println(ex.getMessage());
		}
	}
	private void llenarComboPerfil(){
		try{
			cboPerfil.setPromptText("Seleccione Perfil");
			List<SegPerfil> listaPerfiles;
			listaPerfiles = perfilDAO.getListaPerfil();
			ObservableList<SegPerfil> datos = FXCollections.observableArrayList();

			datos.addAll(listaPerfiles);
			cboPerfil.setItems(datos);
		}catch(Exception ex){
			System.out.println(ex.getMessage());
		}
	}

	boolean validarCedula(String cedula) {
		int total = 0;  
		int tamanoLongitudCedula = 10;  
		int[] coeficientes = {2, 1, 2, 1, 2, 1, 2, 1, 2};  
		int numeroProviancias = 24;  
		int tercerdigito = 6;  
		if (cedula.matches("[0-9]*") && cedula.length() == tamanoLongitudCedula) {  
			int provincia = Integer.parseInt(cedula.charAt(0) + "" + cedula.charAt(1));  
			int digitoTres = Integer.parseInt(cedula.charAt(2) + "");  
			if ((provincia > 0 && provincia <= numeroProviancias) && digitoTres < tercerdigito) {  
				int digitoVerificadorRecibido = Integer.parseInt(cedula.charAt(9) + "");  
				for (int i = 0; i < coeficientes.length; i++) {  
					int valor = Integer.parseInt(coeficientes[i] + "") * Integer.parseInt(cedula.charAt(i) + "");  
					total = valor >= 10 ? total + (valor - 9) : total + valor;  
				}  
				int digitoVerificadorObtenido = total >= 10 ? (total % 10) != 0 ? 10 - (total % 10) : (total % 10) : total;  
				if (digitoVerificadorObtenido == digitoVerificadorRecibido) {  
					return true;  
				}  
			}
			return false;
		}
		return false;		  
	}

	boolean ValidarEmail (String correo) {
		Pattern pat = null;
		Matcher mat = null;        
		pat = Pattern.compile("^([0-9a-zA-Z]([_.w]*[0-9a-zA-Z])*@([0-9a-zA-Z][-w]*[0-9a-zA-Z].)+([a-zA-Z]{2,9}.)+[a-zA-Z]{2,3})$");
		mat = pat.matcher(correo);
		if (mat.find()) {
			return true;
		}else{
			return false;
		}        
	}
}