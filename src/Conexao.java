import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;

public class Conexao {
    public static String receber(Socket socket) throws IOException {
        InputStream in = socket.getInputStream();
        byte[] infoBytes = new byte[500];
        int bytesLidos = in.read(infoBytes);

        if (bytesLidos > 0) {
            return tratamentoMensagem(infoBytes);
        } else {
            return "Mensagem vazia";
        }
    }

    public static void enviar(Socket socket, String textoRequisicao) throws IOException {
        OutputStream out = socket.getOutputStream();
        out.write(textoRequisicao.getBytes());
    }

    //Realiza o tratamento da mensagem recebida em info bytes
    public static String tratamentoMensagem (byte[] infoBytes){
        String mensagem = new String(infoBytes); //Transforma o infoBytes em uma string
        StringBuilder builder = new StringBuilder();//Um string builder para concatenar a mensagem
        for (int i = 0; i < mensagem.length(); i++) { //For para percorrer cada char da mensagem
            if (mensagem.charAt(i) == 0) { //Verifico se aquela posição é um null e se for a mensagem é tudo que vem antes
                break;
            }
            builder.append(mensagem.charAt(i));//Concatena todas as letras novamente na mensagem no string builder
        }
        
        return builder.toString();//Retorna o String builder em formato de string
    }
}