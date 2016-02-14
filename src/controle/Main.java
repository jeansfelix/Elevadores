package controle;

import controle.util.ContadorDeTempo;
import controle.util.GeradorLog;
import modelo.excecoes.ArgumentosIncorretos;

public class Main
{
    private static GeradorLog geradorLog = new GeradorLog("main");

    public static void main(String[] args)
    {
        System.out.println("Inicio Programa.");

        SCE sistemaControladorDeElevadores = new SCE();
        
        ContadorDeTempo.iniciarMarcacaoDeTempo();

        geradorLog.escreverLog("Lendo argumentos.");
        
        if (args.length != 1)
        {
            throw new ArgumentosIncorretos("Numero de argumentos incorreto.");
        }
        
        sistemaControladorDeElevadores.lerArgumentos(args[0]);
        
        geradorLog.escreverLog("Construindo threads elevadores.");
        sistemaControladorDeElevadores.criarElevadores();
        
        geradorLog.escreverLog("Startando threads elevadores.");
        sistemaControladorDeElevadores.iniciarElevadores();
        
        geradorLog.escreverLog("Iniciando esperando elevadores terminarem.");
        sistemaControladorDeElevadores.esperarElevadoresTerminarem();
        
        Long tempoDecorrido = ContadorDeTempo.tempDecorrido();
        
        String localDosLogs =  Main.class.getProtectionDomain().getCodeSource().getLocation().getPath();
        
        System.out.println("Os logs do programa se encontram no diretorio: " + localDosLogs);
        System.out.println("Fim de Programa.");
        System.out.println("Tempo Total: " + tempoDecorrido + "ms");
    }
}
