package controle;

import controle.util.ContadorDeTempo;

public class Main
{
    public static void main(String[] args)
    {
        System.out.println("Inicio Programa.");

        SCE sistemaControladorDeElevadores = new SCE();
        ContadorDeTempo contarTempo = new ContadorDeTempo();

        contarTempo.iniciarMarcacaoDeTempo();

        sistemaControladorDeElevadores.lerArgumentos(args);
        sistemaControladorDeElevadores.criarElevadores();
        sistemaControladorDeElevadores.iniciarElevadores();
        sistemaControladorDeElevadores.esperarElevadoresTerminarem();
        
        Double tempoDecorrido = contarTempo.finalizarMarcacaoDeTempo();
        
        System.out.println("Fim de Programa.");
        System.out.println("Tempo Total: " + tempoDecorrido + "s");
    }
}
