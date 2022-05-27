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
                "4- Excluir item do carrinho\n" +
                "5- Finalizar pedido\n" +
                "\nPara voltar no menu, digite 'menu'";
        String [] itens = new String[10];
        boolean realizandoPedido = false;
        boolean primeiraInteracao = true;
        boolean excluindoItem = false;
        Cardapio cardapio = new Cardapio();
        Carrinho carrinho = new Carrinho();

        serversocket = new ServerSocket(9600);
        System.out.println("Servidor iniciado!");

        while(connect()) {
            textoRecebido = Conexao.receber(socketClient);
            textoRecebido = textoRecebido.toLowerCase();
            System.out.println("Cliente enviou: " + textoRecebido);

            if( textoRecebido.equals("menu") || textoRecebido.equals("n") || primeiraInteracao) {
                primeiraInteracao = false;
                Conexao.enviar(socketClient, menu);
            }

            if (textoRecebido.equals("1") && !realizandoPedido && !excluindoItem) {
                Conexao.enviar(socketClient, cardapio.getCardapio(cardapio));
            }

            if(textoRecebido.equals("2") || realizandoPedido || textoRecebido.equals("s")){
                if(realizandoPedido) {
                    itens = textoRecebido.split("-");
                    Conexao.enviar(socketClient, carrinho.adicionaAlimento(itens, cardapio));
                    realizandoPedido = false;
                }else{
                    Conexao.enviar(socketClient, "O pedido deve ser feito dessa forma: 1-2-3");
                    realizandoPedido = true;
                }
            }

            if(textoRecebido.equals("3") && !realizandoPedido){
                Conexao.enviar(socketClient, carrinho.getCarrinho());
            }
            
            if(textoRecebido.equals("4") && !realizandoPedido || excluindoItem){
                if(excluindoItem){
                    itens = textoRecebido.split("-");
                    Conexao.enviar(socketClient, carrinho.excluirItens(itens, carrinho));
                    excluindoItem = false;
                }else{
                    Conexao.enviar(socketClient, "Remover um item deve ser dessa forma: 1-2-3");
                    excluindoItem = true;
                }
            }

            socketClient.close();
        }
    }
}