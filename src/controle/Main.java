package controle;

public class Main
{
    public static void main(String[] args)
    {
        System.out.println("Inicio Programa.");
        ContadorDeTempo contarTempo = new ContadorDeTempo();

        contarTempo.iniciarMarcacaoDeTempo();

        SCE.lerArgumentos(args);
        
        Double tempoDecorrido = contarTempo.finalizarMarcacaoDeTempo();
        System.out.println("Fim de Programa.");
        System.out.println("Tempo Total: " + tempoDecorrido + "s");
    }
}
