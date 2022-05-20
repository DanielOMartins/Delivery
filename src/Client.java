import java.net.Socket;
import java.util.Scanner;

public class Client {
    Socket socket;

    public void comunicarComServidor() throws Exception {
        boolean teste = true;
        do {
            String textoRequisicao = "Conexao estabelecida";
            String textoRecebido = "";
            socket = new Socket("localhost", 9600);

            Scanner input = new Scanner(System.in);
            System.out.print("\nDigite a sua mensagem: ");
            textoRequisicao = input.nextLine();

            // Enviar mensagem para o servidor
            Conexao.enviar(socket, textoRequisicao);

            // Receber mensagem do servidor
            textoRecebido = Conexao.receber(socket);

            System.out.println("Restaurante enviou: " + textoRecebido);

            if ("testeq".equals(textoRecebido))
                teste = false;
        }while(teste);
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
