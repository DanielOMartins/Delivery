import java.util.ArrayList;
import java.util.List;

public class Cardapio {
    List<Alimento> alimentos = new ArrayList<>(); //Lista de alimentos

    public Cardapio(){ //Quando o cardapio for instaciado, todos os alimentos serão criados e adicionados a lista
        alimentos.add(new Alimento(1, "Big Mac", 15.50));
        alimentos.add(new Alimento(2, "Quarteirão", 17.50));
        alimentos.add(new Alimento(3, "Cheddar", 18.00));

        alimentos.add(new Alimento(4, "Coca Cola", 8.00));
        alimentos.add(new Alimento(5, "Guaraná", 9.00));
        alimentos.add(new Alimento(6, "Fanta Laranja", 7.00));

        alimentos.add(new Alimento(7, "Sorvete", 4.00));
        alimentos.add(new Alimento(8, "Torta de maça", 3.00));
        alimentos.add(new Alimento(9, "Mc Flury", 7.00));
    }

    public List<Alimento> getAlimentos() { //Retorna a lista de alimentos
        return this.alimentos;
    }

    public String getCardapio(){ //Faz a formatação do cardapio e retorna uma string dela
        StringBuilder opcoesCardapio = new StringBuilder();
        for (Alimento alimento : alimentos) {
            opcoesCardapio.append("\n" + alimento.getAlimento());
        }
        opcoesCardapio.append("\nGostaria de realizar seu pedido agora? s ou n");
        return opcoesCardapio.toString();
    }
}