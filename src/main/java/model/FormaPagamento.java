package model;

public enum FormaPagamento {
    PIX("Pix"),
    CREDITO("Cartão de crédito"),
    DEBITO("Cartão de débito"),
    DINHEIRO("Dinheiro");

    private final String descricao;

    FormaPagamento(String descricao) {
        this.descricao = descricao;
    }

    @Override
    public String toString() {
        return descricao;
    }

    public static FormaPagamento fromPostgres(String nomePostgres) {
        try {
            return FormaPagamento.valueOf(nomePostgres.toUpperCase());
        } catch (IllegalArgumentException e) {
            throw new IllegalArgumentException("Forma de pagamento inválida: " + nomePostgres);
        }
    }
    public String getNomePostgres() {
        return this.name().toLowerCase(); // Pq no banco está tudo em minúsculo: pix, credito, etc.
    }
}
