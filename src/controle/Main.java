package controle;

import java.nio.file.Files;
import java.nio.file.LinkOption;
import java.nio.file.Path;
import java.nio.file.Paths;

import controle.util.ContadorDeTempo;
import controle.util.GeradorLog;
import modelo.excecoes.ArgumentosIncorretos;

public class Main
{
    public static String      diretorioDestino = "";
    private static GeradorLog geradorLog;

    public static void main(String[] args)
    {
        System.out.println("Inicio Programa.");

        checarArgumentos(args);
        
        geradorLog = new GeradorLog(diretorioDestino, "main");
        
        SCE sistemaControladorDeElevadores = new SCE();

        ContadorDeTempo.iniciarMarcacaoDeTempo();

        geradorLog.escreverLog("Lendo argumentos.");

        sistemaControladorDeElevadores.lerArgumentos(args[0]);

        geradorLog.escreverLog("Construindo threads elevadores.");
        sistemaControladorDeElevadores.criarElevadores();

        geradorLog.escreverLog("Startando threads elevadores.");
        sistemaControladorDeElevadores.iniciarElevadores();

        geradorLog.escreverLog("Esperando elevadores terminarem.");
        sistemaControladorDeElevadores.esperarElevadoresTerminarem();

        geradorLog.fecharArquivos();

        Long tempoDecorrido = ContadorDeTempo.tempDecorrido();

        System.out.println("Fim de Programa.");
        System.out.println("Tempo Total: " + tempoDecorrido + "ms");
        System.out.println();
        System.out.println("Os logs do programa se encontram no diretorio: " + diretorioDestino);
    }

    private static void checarArgumentos(String[] args)
    {
        if (args.length < 2)
        {
            throw new ArgumentosIncorretos(
                    "Numero de argumentos incorreto. Passe o arquivo de entrada e o diretório para os logs.");
        }

        Path diretorio = Paths.get(args[1]).toAbsolutePath();
        
        if (!Files.isDirectory(diretorio, LinkOption.NOFOLLOW_LINKS) || !Files.isWritable(diretorio))
        {
            throw new ArgumentosIncorretos(
                    "O diretório para os logs não é um diretório, não existe ou não tem permissão de escrita.");
        }
        
        diretorioDestino = diretorio.toString();
    }
}
