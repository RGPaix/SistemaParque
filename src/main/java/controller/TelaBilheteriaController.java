package controller;

import config.ConnectionFactory;
import config.Sessao;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.ListView;
import model.Bilheteria;
import dao.BilheteriaDAO;
import model.Cliente;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;

import static config.Sessao.usuarioLogado;

public class TelaBilheteriaController {

    @FXML
    private ChoiceBox<Bilheteria> choiceBilheteria;

    @FXML
    private ListView<String> lviewBilheteria;

    @FXML
    private Button bttnComprarIngresso;

    @FXML
    private Button bttnJaPossuoIngresso;

    private List<Bilheteria> bilheteriasDisponiveis;

    @FXML
    public void initialize() {
        //Carrega o usuário logado
        Cliente usuario = Sessao.getUsuarioLogado();

        lviewBilheteria.setMouseTransparent(true);
        lviewBilheteria.setFocusTraversable(false);

        // Carregar as bilheterias do banco via DAO
        bilheteriasDisponiveis = BilheteriaDAO.findAll();

        // Preencher o ChoiceBox
        choiceBilheteria.setItems(FXCollections.observableArrayList(bilheteriasDisponiveis));

        // Definir o que acontece quando o usuário seleciona uma bilheteria
        choiceBilheteria.setOnAction(event -> atualizarDetalhesBilheteria());

        // Botão desabilitado até escolher opção válida
        bttnComprarIngresso.setDisable(true);

        // Comprar ingresso
        bttnComprarIngresso.setOnAction(event -> comprarIngresso());
    }

    public static void registrarVenda(Bilheteria bilheteria) {
        bilheteria.setQuantidade_disponivel(bilheteria.getQuantidade_disponivel() - 1);
        update(bilheteria);
    }

    public static void update(Bilheteria bilheteria) {
        try (Connection conn = ConnectionFactory.getConnection()) {
            String sql = "UPDATE bilheteria SET preco = ?, horario_fechamento = ?, funcionamento = ?, quantidade_disponivel = ? WHERE id = ?";
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setDouble(1, bilheteria.getPreco());
            ps.setString(2, bilheteria.getHorario_fechamento());
            ps.setBoolean(3, bilheteria.isFuncionamento());
            ps.setInt(4, bilheteria.getQuantidade_disponivel());
            ps.setLong(5, bilheteria.getId());
            ps.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    private void atualizarDetalhesBilheteria() {
        Bilheteria selecionada = choiceBilheteria.getValue();
        lviewBilheteria.getItems().clear();

        if (selecionada != null) {
            if (selecionada.getQuantidade_disponivel() > 0 && !selecionada.estaFechada()) {
                lviewBilheteria.getItems().add("Ingressos disponíveis: " + selecionada.getQuantidade_disponivel());
                lviewBilheteria.getItems().add("Horário de fechamento: " + selecionada.getHorario_fechamento());
                lviewBilheteria.getItems().add("Preço: R$ " + selecionada.getPreco());
                bttnComprarIngresso.setDisable(false);
            } else {
                lviewBilheteria.getItems().add("Bilheteria indisponível.");
                bttnComprarIngresso.setDisable(true);
            }
        }
    }

    private void comprarIngresso() {
        Bilheteria selecionada = choiceBilheteria.getValue();
        if (selecionada != null && selecionada.getQuantidade_disponivel() > 0 && !selecionada.estaFechada()) {
            try {
                // Carregar a próxima tela (Pagamento)
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/TelaCompra.fxml"));
                Parent root = loader.load();

                // Pegar o controller da TelaPagamento e passar a bilheteria selecionada
                TelaCompraController controller = loader.getController();
                controller.setBilheteria(selecionada);

                // Abrir nova cena
                Stage stage = (Stage) bttnComprarIngresso.getScene().getWindow();
                stage.setScene(new Scene(root));
                stage.show();

            } catch (Exception e) {
                e.printStackTrace();
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Erro ao carregar a tela de pagamento");
                alert.setHeaderText(null);
                alert.setContentText("Ocorreu um erro ao avançar para a próxima tela.");
                alert.showAndWait();
            }
        }
    }
    @FXML
    private void irParaAtracoes(ActionEvent event) throws IOException {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/TelaAtracao.fxml"));
            Parent root = loader.load();

            Scene scene = new Scene(root);
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            stage.setScene(scene);
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
