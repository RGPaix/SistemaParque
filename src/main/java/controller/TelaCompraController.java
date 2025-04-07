package controller;

import config.Sessao;
import dao.BilheteriaDAO;
import dao.IngressoDAO;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import model.Bilheteria;
import model.FormaPagamento;
import model.Ingresso;

import java.io.IOException;
import java.time.LocalDate;

public class TelaCompraController {

    @FXML
    private Label labelPagamento;

    @FXML
    private ChoiceBox<FormaPagamento> choicePagamento;

    @FXML
    private Button btnConfirmarCompra;

    private Bilheteria bilheteriaSelecionada;

    @FXML
    public void initialize() {
        // Adiciona opções de pagamento
        choicePagamento.setItems(FXCollections.observableArrayList(FormaPagamento.values()));

        // Define ação do botão
        btnConfirmarCompra.setOnAction(event -> confirmarCompra());
    }

    public void setBilheteria(Bilheteria bilheteria) {
        this.bilheteriaSelecionada = bilheteria;

        // Se quiser mostrar info da bilheteria na label:
        labelPagamento.setText("Pagamento para bilheteria " + bilheteria.getId() +
                " - R$" + bilheteria.getPreco());
    }

    private void confirmarCompra() {
        FormaPagamento metodo = choicePagamento.getValue();

        if (metodo == null ) {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Pagamento");
            alert.setHeaderText(null);
            alert.setContentText("Por favor, selecione uma forma de pagamento.");
            alert.showAndWait();
            return;
        }

        //Criar ingresso
        IngressoDAO ingressoDAO = new IngressoDAO();
        Long id_bilheteria = bilheteriaSelecionada.getId();
        Long id_cliente = Sessao.getUsuarioLogado().getId();
        LocalDate data_venda = LocalDate.parse(LocalDate.now().toString());
        String pagamento = metodo.name().toLowerCase();
        Ingresso ingresso = new Ingresso(id_cliente, id_bilheteria, data_venda, pagamento);
        Ingresso ingressoSalvo = ingressoDAO.create(ingresso);

        if (ingressoSalvo != null) {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Compra confirmada");
            alert.setHeaderText(null);
            alert.setContentText("Ingresso comprado com sucesso via " + metodo + "!\nID do ingresso: " + ingressoSalvo.getId());
            alert.showAndWait();
        } else {
            Alert erro = new Alert(Alert.AlertType.ERROR, "Erro ao registrar o ingresso.");
            erro.showAndWait();
            return;
        }

        //Atualizar número de ingressos na bilheteria
        BilheteriaDAO dao = new BilheteriaDAO();
        dao.registrarVenda(bilheteriaSelecionada);

        // Abre a telaAtracao.fxml
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/telaAtracao.fxml"));
            Parent root = loader.load();
            Stage stage = new Stage();
            stage.setTitle("Atrações");
            stage.setScene(new Scene(root));
            stage.show();

            // Fecha a janela atual, se quiser
            Stage telaAtual = (Stage) btnConfirmarCompra.getScene().getWindow();
            telaAtual.close();

        } catch (IOException e) {
            e.printStackTrace();
            Alert erro = new Alert(Alert.AlertType.ERROR, "Erro ao carregar a tela de atrações.");
            erro.showAndWait();
        }
    }
}