import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

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
                "5- Retirar pedido\n" +
                "Sempre que quiser voltar ao menu digite 'voltar'";
        int count = 0;
        Cardapio cardapio = new Cardapio();

        serversocket = new ServerSocket(9600);
        System.out.println("Servidor iniciado!");

        while(connect()) {
            textoRecebido = Conexao.receber(socketClient);
            System.out.println("Cliente enviou: " + textoRecebido);

            if(0 == count || textoRecebido.equals("voltar")) {
                count++;
                Conexao.enviar(socketClient, menu);
            }

            if (textoRecebido.equals("1")){
                Conexao.enviar(socketClient, cardapio);
            }

            socketClient.close();
        }
    }
}
