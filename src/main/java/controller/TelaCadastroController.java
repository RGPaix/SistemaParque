package controller;

import config.Sessao;
import dao.ClienteDAO;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;
import model.Cliente;

import java.sql.SQLException;

public class TelaCadastroController {

    @FXML private TextField textNome;
    @FXML private TextField textCPF;
    @FXML private TextField textEndereco;
    @FXML private TextField textTelefone;
    @FXML private PasswordField textSenha;
    @FXML private Button bttnSenha;

    private ClienteDAO clienteDAO = new ClienteDAO();

    @FXML
    private void initialize() {
        bttnSenha.setOnAction(e -> cadastrar());
    }

    @FXML
    private void cadastrar() {
        String nome = textNome.getText().trim();
        String cpf = textCPF.getText().trim();
        String endereco = textEndereco.getText().trim();
        String telefone = textTelefone.getText().trim();
        String senha = textSenha.getText().trim();

        // Validação dos campos obrigatórios
        if (nome.isEmpty() || cpf.isEmpty() || senha.isEmpty()) {
            StringBuilder camposVazios = new StringBuilder("Por favor, preencha os seguintes campos obrigatórios:\n");

            if (nome.isEmpty()) camposVazios.append("- Nome\n");
            if (cpf.isEmpty()) camposVazios.append("- CPF\n");
            if (senha.isEmpty()) camposVazios.append("- Senha\n");

            mostrarAlerta("Campos obrigatórios", camposVazios.toString(), Alert.AlertType.WARNING);
            return; // impede o cadastro se houver campos obrigatórios vazios
        }

        try {
            Cliente novoUsuario = new Cliente(nome, cpf, endereco, telefone, senha);
            Cliente usuarioCriado = clienteDAO.create(novoUsuario);

            if (usuarioCriado != null) {
                Sessao.setUsuarioLogado(usuarioCriado);
                mostrarAlerta("Sucesso", "Cliente cadastrado com sucesso!", Alert.AlertType.INFORMATION);
                irParaBilheteria();
            } else {
                mostrarAlerta("Erro", "Não foi possível cadastrar o cliente.", Alert.AlertType.ERROR);
            }

        } catch (SQLException e) {
            mostrarAlerta("Erro", e.getMessage(), Alert.AlertType.ERROR);
        }
    }

    private void mostrarAlerta(String titulo, String mensagem, Alert.AlertType tipo) {
        Alert alert = new Alert(tipo);
        alert.setTitle(titulo);
        alert.setHeaderText(null);
        alert.setContentText(mensagem);
        alert.showAndWait();
    }

    @FXML
    private void irParaBilheteria() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/TelaBilheteria.fxml"));
            Scene scene = new Scene(loader.load());
            Stage stage = (Stage) bttnSenha.getScene().getWindow();
            stage.setScene(scene);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}