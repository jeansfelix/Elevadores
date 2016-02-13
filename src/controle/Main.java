package controle;

import controle.util.ContadorDeTempo;

public class Main
{
    public static void main(String[] args)
    {
        System.out.println("Inicio Programa.");

        SCE sistemaControladorDeElevadores = new SCE();

        ContadorDeTempo.iniciarMarcacaoDeTempo();

        sistemaControladorDeElevadores.lerArgumentos(args);
        sistemaControladorDeElevadores.criarElevadores();
        sistemaControladorDeElevadores.iniciarElevadores();
        sistemaControladorDeElevadores.esperarElevadoresTerminarem();
        
        Long tempoDecorrido = ContadorDeTempo.tempDecorrido();
        
        System.out.println("Fim de Programa.");
        System.out.println("Tempo Total: " + tempoDecorrido + "ms");
    }
}
