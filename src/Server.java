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
        String [] itens = new String[10];//Array para guardar os itens do pedido

        //Aqui são declarados os status do cliente
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

            //Aqui verifica se é a primeira interação do cliente ou se ele solicitou o menu, e retorna o menu para ele
            if( textoRecebido.equals("menu") || textoRecebido.equals("n") || primeiraInteracao) {
                primeiraInteracao = false;
                finalizandoPedido = false;
                Conexao.enviar(socketClient, menu);
            }

            //Verifca se o cliente solicitou o cardapio e não está realizando pedido nem excluindo item, e retorna o cardapio para ele
            if (textoRecebido.equals("1") && !realizandoPedido && !excluindoItem) {
                Conexao.enviar(socketClient, cardapio.getCardapio());
            }

            //verifica se o cliente quer realizar o pedido ou se já está realizando
            if(textoRecebido.equals("2") && !excluindoItem || realizandoPedido || textoRecebido.equals("s") && !finalizandoPedido){

                if(realizandoPedido) { //Se o status do cliente for realizando pedido, vamos adicionar o pedido ao carrinho e retorna uma mensagem de sucesso
                    itens = textoRecebido.split("-"); //Pegamos o pedido enviado e colocamos dentro do array de itens
                    Conexao.enviar(socketClient, carrinho.adicionaItens(itens, cardapio));
                    realizandoPedido = false; //Após finzalizar a adição do pedido ao carrinho, informamos que o status atual do cliente não é mais realizando pedido
                }
                else{ //Se status do cliente não for realizando pedido, retornamos para ele como o pedido deve ser feito
                    Conexao.enviar(socketClient, "O pedido deve ser feito dessa forma: 1-2-3");
                    realizandoPedido = true; //E aqui atualizamos o status atual dele para realizando pedido
                }
            }

            //Verificamos se o cliente solicitou o carrinho e não esta nem realizando pedido ou excluindo item
            if(textoRecebido.equals("3") && !realizandoPedido && !excluindoItem){
                Conexao.enviar(socketClient, carrinho.getCarrinho()); //Retornamos o carrinho com os itens e o total do carrinho
            }
            
            //Verifica se o cliente solicitou a opção de excluir itens e não está realizando pedido ou se o status atual é excluindo item
            if(textoRecebido.equals("4") && !realizandoPedido || excluindoItem){

                if(excluindoItem){ //Se o status do cliente for excluindo item, vamos excluir o item do carrinho e retorna uma mensagem de sucesso
                    itens = textoRecebido.split("-"); //Pegamos o pedido enviado e colocamos dentro do array de itens
                    Conexao.enviar(socketClient, carrinho.excluirItens(itens));
                    excluindoItem = false; //Após excluir os itens do carrinho, informamos que o status atual do cliente não é mais excluindo item
                }
                else{ //Se status do cliente não for excluindo item, retornamos para ele como a exclusão deve ser feita
                    Conexao.enviar(socketClient, "Remover um item deve ser dessa forma: 1-2-3");
                    excluindoItem = true; //E aqui atualizamos o status atual dele para excluindo item
                }
            }

            //Vericamos se o cliente solicitou para finzalizar pedido ou se ele tem certeza que pode finalizar
            if(textoRecebido.equals("5") && !realizandoPedido && !excluindoItem || textoRecebido.equals("s") && finalizandoPedido){
                if(finalizandoPedido){ //Se o status atual dele for finalizando pedido
                    TimeUnit.SECONDS.sleep(20);
                    Conexao.enviar(socketClient,"Pedido finalizado e pronto para ser retirado!"); //Enviamos uma resposta quando estiver pronto
                    
                    //E preparamos o Server para receber um novo cliente 
                    carrinho.excluiCarrinho();
                    finalizandoPedido = false;
                    primeiraInteracao = true;
                }
                else{ //Se status do cliente não for finalizando pedido, retornamos para ele o carrinho para verificar se tem certeza
                    Conexao.enviar(socketClient, "Podemos preparar seu pedido? s ou n" + carrinho.getCarrinho());
                    finalizandoPedido = true; //E atualizamos o status do cliente para finalizando pedido
                }
            }

            socketClient.close();
        }
    }
}