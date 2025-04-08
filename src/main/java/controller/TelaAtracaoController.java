package controller;

import dao.AtracaoDAO;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;
import model.Atracao;

import java.io.IOException;
import java.util.List;

public class TelaAtracaoController {

    @FXML
    private ChoiceBox<Atracao> choiceAtracao;

    @FXML
    private ListView<String> lviewAtracao;

    @FXML
    private Button bttnAtracao;

    private final AtracaoDAO atracaoDAO = new AtracaoDAO();

    @FXML
    public void initialize() {
        List<Atracao> atracoes = atracaoDAO.findAll();

        if (atracoes == null || atracoes.isEmpty()) {
            Alert alert = new Alert(Alert.AlertType.ERROR, "Erro ao carregar atrações. A tela será fechada.");
            alert.showAndWait();

            // Fecha a janela atual
            javafx.stage.Window window = choiceAtracao.getScene().getWindow();
            if (window instanceof Stage stage) {
                stage.close();
            }
            return;
        }
        choiceAtracao.getItems().addAll(atracoes);

        // Evento para mudar listView ao selecionar uma atração
        choiceAtracao.setOnAction(e -> exibirDetalhes());

        // Evita seleção no ListView
        lviewAtracao.setMouseTransparent(true);
        lviewAtracao.setFocusTraversable(false);
    }

    private void exibirDetalhes() {
        Atracao atracao = choiceAtracao.getValue();
        if (atracao != null) {
            lviewAtracao.getItems().clear();
            lviewAtracao.getItems().add("Nome: " + atracao.getNome());
            lviewAtracao.getItems().add(atracao.getDescricao());
            lviewAtracao.getItems().add("Horário: " + atracao.getHorario_inicio() + " - " + atracao.getHorario_fim());
            lviewAtracao.getItems().add("Capacidade: " + atracao.getCapacidade());
        }
    }

    @FXML
    private void registrarParticipacao(ActionEvent event) {
        Atracao selecionada = choiceAtracao.getValue();
        if (selecionada != null) {
            abrirTelaValidarIngresso(selecionada.getId());
        } else {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Atenção");
            alert.setHeaderText(null);
            alert.setContentText("Selecione uma atração antes de continuar.");
            alert.showAndWait();
        }
    }

    private void abrirTelaValidarIngresso(Long idAtracao) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/TelaValidarIngresso.fxml"));
            Parent root = loader.load();

            TelaValidarIngressoController controller = loader.getController();
            controller.setIdAtracao(idAtracao);

            Stage stage = new Stage();
            stage.setTitle("Validação de Ingresso");
            stage.setScene(new Scene(root));
            stage.show();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
