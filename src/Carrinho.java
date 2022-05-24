import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

public class Carrinho {
    private List<Alimento> carrinho = new ArrayList<>();
    private double totalCarrinho = 0;

    private static final DecimalFormat df = new DecimalFormat("0.00");

    public String getCarrinho() {
        StringBuilder str = new StringBuilder();
        for (Alimento alimento: carrinho) {
            str.append("\n" + alimento.getNome());
        }
        str.append("\nTotal: R$" + df.format(totalCarrinho));

        return str.toString();
    }

    public void adicionaAlimento(String[] pedidos, Cardapio cardapio){
        int id;
        for (String s: pedidos) {
            id = Integer.parseInt(s);
            for (Alimento alimento: cardapio.getAlimentos()) {
                if(id == alimento.getId()){
                    carrinho.add(alimento);
                    totalCarrinho += alimento.getPreco();
                }
            }
        }
    }
}
