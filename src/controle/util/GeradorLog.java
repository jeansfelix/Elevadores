package controle.util;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;

import modelo.excecoes.ManipularArquivosDeLogException;

public class GeradorLog
{
    BufferedWriter escritor;

    public GeradorLog(String nomeArquivo)
    {
        FileWriter log;
        String caminhoArquivo = getClass().getProtectionDomain().getCodeSource().getLocation().getPath() + nomeArquivo;
        
        try
        {
            log = new FileWriter(caminhoArquivo);
            escritor = new BufferedWriter(log);
        }
        catch (IOException e)
        {
            throw new ManipularArquivosDeLogException(e, "Erro ao tentar abrir arquivo de log:" + nomeArquivo + ".");
        }
    }

    public void escreverLog(String mensagem)
    {
        try
        {
            StringBuilder sb = new StringBuilder("");
            
            Long tempoAtual = ContadorDeTempo.tempDecorrido();

            sb.append(tempoAtual).append("ms: ").append(mensagem);

            escritor.write(sb.toString());
            escritor.newLine();
            escritor.flush();
        }
        catch (IOException e)
        {
            throw new ManipularArquivosDeLogException(e, "Erro ao tentar escrever mensagem: " + mensagem);
        }
    }

    public void fecharArquivos()
    {
        try
        {
            escritor.close();
        }
        catch (IOException e)
        {
            throw new ManipularArquivosDeLogException(e, "Erro ao fechar arquivo.");
        }
    }
}
