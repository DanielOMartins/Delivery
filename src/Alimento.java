import java.text.DecimalFormat;

public class Alimento { //É o modelo de cada alimento
    private int id;
    private String nome;
    private double preco;

    private static final DecimalFormat df = new DecimalFormat("0.00");

    //Diz que sempre que um alimento for instaciado precisa passar as caracteristicas dele
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

    public String getAlimento() { //Retorna o alimento com id, nome e preço formatados
        return this.id + " - " + this.nome + ": R$" + df.format(this.preco);
    }
}