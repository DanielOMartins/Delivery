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

    public static String tratamentoMensagem (byte[] infoBytes){
        String mensagem = new String(infoBytes);
        StringBuilder builder = new StringBuilder();
        for (int i = 0; i < mensagem.length(); i++) {
            if (mensagem.charAt(i) == 0) {
                break;
            }
            builder.append(mensagem.charAt(i));
        }
        return builder.toString();
    }
}