import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

public class Server {
    Socket socketClient;
    ServerSocket serversocket;

    public boolean connect() {
        try {
            socketClient = serversocket.accept();  // fase de conexao
            return true;
        } catch (IOException e) {
            System.err.println("Nao fez conexao" + e.getMessage());
            return false;
        }
    }

    public static void main(String[] args) {
        try {
            Server servidor = new Server();
            servidor.rodarServidor();
        } catch(Exception e){
            e.printStackTrace();
        }
    }

    public void rodarServidor() throws Exception {
        String textoRecebido = "";
        String menu = "\n1- Cardapio\n" +
                "2- Adicionar ao Carrinho (1-3-7)\n" +
                "3- Exibir carrinho de compra\n" +
                "4- Realizar pedido\n" +
                "4- Retirar pedido\n" +
                "Sempre que quiser voltar ao menu digite 'voltar'";
        boolean teste = true;
        int count = 0;
        int tempRealizaPedido = 0;
        String [] pedidos = new String[10];
        Cardapio cardapio = new Cardapio();
        Carrinho carrinho = new Carrinho();

        serversocket = new ServerSocket(9600);
        System.out.println("Servidor iniciado!");

        while(connect()) {
            textoRecebido = Conexao.receber(socketClient);
            System.out.println("Cliente enviou: " + textoRecebido);

            if(0 == count || textoRecebido.equals("voltar")) {
                count = 1;
                Conexao.enviar(socketClient, menu);
            }

            if (textoRecebido.equals("1")){
                Conexao.enviar(socketClient, cardapio);
            }

            if(textoRecebido.equals("2") || tempRealizaPedido == 2){
                //TODO verificar se o id do pedido está no cardapio, se não retorna mensagem ao cliente
                if(tempRealizaPedido == 2) {
                    pedidos = textoRecebido.split("-");
                    carrinho.adicionaAlimento(pedidos, cardapio);
                    Conexao.enviar(socketClient, "Pedido adicionado ao carrinho com sucesso");
                    tempRealizaPedido = 1;
                }

                if(textoRecebido.equals("2")) {
                    Conexao.enviar(socketClient, "O pedido deve ser feito dessa forma: 1-2-3");
                    tempRealizaPedido = 2;
                }
            }

            if(textoRecebido.equals("3")){
                Conexao.enviar(socketClient, carrinho.getCarrinho());
            }

            socketClient.close();
        }
    }
}
