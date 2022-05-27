import java.net.Socket;
import java.util.Scanner;

public class Client {
    Socket socket;

    public void comunicarComServidor() throws Exception {
        String textoRequisicao = "";
        String textoRecebido = "";
        boolean pedidoFinalizado = false;

        do {
            socket = new Socket("localhost", 9600);

            Scanner input = new Scanner(System.in);
            System.out.print("\nDigite a sua mensagem: ");
            textoRequisicao = input.nextLine();

            // Enviar mensagem para o servidor
            Conexao.enviar(socket, textoRequisicao);

            // Receber mensagem do servidor
            textoRecebido = Conexao.receber(socket);

            System.out.println("Restaurante enviou: " + textoRecebido);

            if(textoRecebido.equals("Pedido finalizado")){
                pedidoFinalizado = true;
            }

        }while(!pedidoFinalizado);
    }


    public static void main(String[] args) {
        try {
            Client cliente = new Client();
            cliente.comunicarComServidor();
        } catch(Exception e){
            e.printStackTrace();
        }
    }
}