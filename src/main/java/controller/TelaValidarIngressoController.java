package controller;

import dao.Atracao_ClienteDAO;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import model.Atracao_Cliente;
import java.time.format.DateTimeFormatter;

import java.time.LocalDateTime;

public class TelaValidarIngressoController {

    @FXML
    private TextField textNumeroingresso;

    @FXML
    private Button bttnValidarAtracao;

    private Long idAtracao;

    private final Atracao_ClienteDAO dao = new Atracao_ClienteDAO();

    public void initialize() {
        bttnValidarAtracao.setOnAction(e -> validarIngresso());
    }

    public void setIdAtracao(Long idAtracao) {
        this.idAtracao = idAtracao;
    }

    private void validarIngresso() {
        try {
            Long idIngresso = Long.parseLong(textNumeroingresso.getText());

            // Verifica se já foi usado nesta atração
            boolean jaUsado = dao.ingressoJaUsadoNaAtracao(idAtracao, idIngresso);
            if (jaUsado) {
                mostrarMensagem("Ingresso já utilizado nesta atração.");
                return;
            }

            // Salva uso
            Atracao_Cliente registro = new Atracao_Cliente();
            registro.setId_atracao(idAtracao);
            registro.setId_ingresso(idIngresso);
            registro.setHorario_uso(LocalDateTime.now());

            dao.registrarUso(registro);

            LocalDateTime agora = LocalDateTime.now();
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");
            String horarioFormatado = agora.format(formatter);

            mostrarMensagem("Atração validada no ingresso ID " + idIngresso + " às " + horarioFormatado);

        } catch (NumberFormatException e) {
            mostrarMensagem("Digite um número de ingresso válido.");
        } catch (Exception e) {
            mostrarMensagem("Erro ao validar ingresso: " + e.getMessage());
        }
    }

    private void mostrarMensagem(String msg) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setHeaderText(null);
        alert.setContentText(msg);
        alert.showAndWait();
    }
}