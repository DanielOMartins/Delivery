import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class Carrinho {
    private List<Alimento> carrinho = new ArrayList<>(); //Lista do carrinho para guardar alimentos nele
    private double totalCarrinho = 0; //Soma do preço de cada alimento no carrinho

    private static final DecimalFormat df = new DecimalFormat("0.00");

    //Retorna o carrinho formatado com os alimentos nele e o preço total
    public String getCarrinho() {
        StringBuilder str = new StringBuilder();
        for (Alimento alimento: carrinho) {
            str.append("\n"+ alimento.getId() + " - " + alimento.getNome());
        }
        str.append("\nTotal: R$" + df.format(totalCarrinho));

        return str.toString();
    }

    //Exclui tudo que etá no carrinho e reseta o total do carrinho para zero
    public void excluiCarrinho(){
        carrinho.removeAll(carrinho);
        totalCarrinho = 0;
    }

    //Responsável por adicionar itens ao carrinho
    public String adicionaItens(String [] itens, Cardapio cardapio){
        int id;
        boolean existeNoCardapio; //Verifica se o item escolhido existe no cardapio.

        //For para encontrar o item selecionado no cardapio e adicionar ele no carrinho
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
            if(!existeNoCardapio){ //Se o item selecionado não for igual a nenhum do cardapio, retorna essa mensagem.
                return "Um dos itens solicitados não existe no cardapio, somente os itens existentes foram adicionadas.";
            }
        }
        return "Pedido adicionado ao carrinho com sucesso";
    }

    //Responsável por excluir itens do carrinho
    public String excluirItens(String[] itens) {
        int id;
        boolean existeNoCarrinho;
        Iterator<Alimento> it;

        //For para encontrar o item escolhido no carrinho e excluir ele
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
            if(!existeNoCarrinho){ //Se o item selecionado não for igual a nenhum do carrinho, retorna essa mensagem.
                return "Um dos itens selecionados não existe no carrinho, somente itens existentes foram excluídos.";
            }
        }
        return "Item(s) removido com sucesso.";
    }
}