import java.text.DecimalFormat;

public class Alimento {
    private int id;
    private String nome;
    private double preco;

    private static final DecimalFormat df = new DecimalFormat("0.00");

    public Alimento(int id, String nome, double preco){
        this.nome = nome;
        this.id = id;
        this.preco = preco;
    }

    public String getNome() {
        return this.nome;
    }

    public int getId() {
        return this.id;
    }

    public double getPreco() {
        return this.preco;
    }

    public String getAlimento() {
        return this.id + " - " + this.nome + ": R$" + df.format(this.preco);
    }
}