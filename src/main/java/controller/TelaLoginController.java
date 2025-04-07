package controller;

import config.Sessao;
import dao.ClienteDAO;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;
import model.Cliente;

public class TelaLoginController {

    @FXML private TextField txtUsuario; // CPF
    @FXML private PasswordField txtSenha;
    @FXML private Button bttnLogin;
    @FXML private Button bttnCadastro;

    private ClienteDAO clienteDAO = new ClienteDAO();

    @FXML
    private void initialize() {
        bttnLogin.setOnAction(this::logar);
        bttnCadastro.setOnAction(this::irParaCadastro);
    }

    @FXML
    private void logar(ActionEvent event) {
        String cpf = txtUsuario.getText();
        String senha = txtSenha.getText();

        Cliente cliente = clienteDAO.findByCpfAndSenha(cpf, senha);

        if (cliente != null) {
            Sessao.setUsuarioLogado(cliente);
            irParaBilheteria("Bem-vindo(a), " + cliente.getNome() + "!");
        } else {
            mostrarAlerta("Erro de login", "Usuário/Senha não existe! Volte e cadastre-se.");
        }
    }

    private void irParaCadastro(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/TelaCadastro.fxml"));
            Scene scene = new Scene(loader.load());
            Stage stage = (Stage) bttnCadastro.getScene().getWindow();
            stage.setScene(scene);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void irParaBilheteria(String mensagem) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/TelaBilheteria.fxml"));
            Scene scene = new Scene(loader.load());
            Stage stage = (Stage) bttnLogin.getScene().getWindow();
            stage.setScene(scene);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void mostrarAlerta(String titulo, String msg) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle(titulo);
        alert.setContentText(msg);
        alert.showAndWait();
    }
}