import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.TimeUnit;

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
        boolean finalizandoPedido = false;
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
                finalizandoPedido = false;
                Conexao.enviar(socketClient, menu);
            }

            if (textoRecebido.equals("1") && !realizandoPedido && !excluindoItem) {
                Conexao.enviar(socketClient, cardapio.getCardapio());
            }

            if(textoRecebido.equals("2") && !excluindoItem || realizandoPedido || textoRecebido.equals("s") && !finalizandoPedido){
                if(realizandoPedido) {
                    itens = textoRecebido.split("-");
                    Conexao.enviar(socketClient, carrinho.adicionaItens(itens, cardapio));
                    realizandoPedido = false;
                }else{
                    Conexao.enviar(socketClient, "O pedido deve ser feito dessa forma: 1-2-3");
                    realizandoPedido = true;
                }
            }

            if(textoRecebido.equals("3") && !realizandoPedido && !excluindoItem){
                Conexao.enviar(socketClient, carrinho.getCarrinho());
            }
            
            if(textoRecebido.equals("4") && !realizandoPedido || excluindoItem){
                if(excluindoItem){
                    itens = textoRecebido.split("-");
                    Conexao.enviar(socketClient, carrinho.excluirItens(itens));
                    excluindoItem = false;
                }else{
                    Conexao.enviar(socketClient, "Remover um item deve ser dessa forma: 1-2-3");
                    excluindoItem = true;
                }
            }

            if(textoRecebido.equals("5") && !realizandoPedido && !excluindoItem || textoRecebido.equals("s") && finalizandoPedido){
                if(finalizandoPedido){
                    TimeUnit.SECONDS.sleep(20);
                    Conexao.enviar(socketClient,"Pedido finalizado e pronto para ser retirado!");
                    carrinho.excluiCarrinho();
                    finalizandoPedido = false;
                    primeiraInteracao = true;
                }else{
                    Conexao.enviar(socketClient, "Podemos preparar seu pedido? s ou n" + carrinho.getCarrinho());
                    finalizandoPedido = true;
                }
            }

            socketClient.close();
        }
    }
}