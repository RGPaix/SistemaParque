package model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor

public class Ingresso {
    private Long id;
    private Long id_cliente;
    private Long id_bilheteria;
    private LocalDate data_venda;
    private FormaPagamento pagamento;

    public FormaPagamento getPagamento() {
        return pagamento;
    }

    public void setPagamento(FormaPagamento pagamento) {
        this.pagamento = pagamento;
    }

    public Ingresso(Long idCliente, Long idBilheteria, LocalDate dataVenda, String pagamento) {
        this.id_cliente = idCliente;
        this.id_bilheteria = idBilheteria;
        this.data_venda = dataVenda;
        this.pagamento = FormaPagamento.fromPostgres(pagamento);
    }
}
