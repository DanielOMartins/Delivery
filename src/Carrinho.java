import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Iterator;
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

    public void excluiCarrinho(){
        carrinho.removeAll(carrinho);
        totalCarrinho = 0;
    }

    public String adicionaItens(String [] itens, Cardapio cardapio){
        int id;
        boolean existeNoCardapio;

        for (String s: itens) {
            id = Integer.parseInt(s);
            existeNoCardapio = false;
            for (Alimento alimento: cardapio.getAlimentos()) {
                if(id == alimento.getId()){
                    carrinho.add(alimento);
                    totalCarrinho += alimento.getPreco();
                    existeNoCardapio = true;
                }
            }
            if(!existeNoCardapio){
                return "Um dos itens solicitados não existe no cardapio, realize o pedido novamente.";
            }
        }
        return "Pedido adicionado ao carrinho com sucesso";
    }

    public String excluirItens(String[] itens) {
        int id;
        boolean existeNoCarrinho;
        Iterator<Alimento> it;

        for (String s: itens) {
            id = Integer.parseInt(s);
            existeNoCarrinho = false;
            for (it = carrinho.iterator(); it.hasNext();) {
                Alimento alimento = it.next();
                if(alimento.getId() == id){
                    it.remove();
                    this.totalCarrinho -= alimento.getPreco();
                    existeNoCarrinho = true;
                }
            }
            if(!existeNoCarrinho){
                return "Um dos itens selecionados não existe no carrinho, selecione itens existentes.";
            }
        }
        return "Item(s) removido com sucesso.";
    }
}