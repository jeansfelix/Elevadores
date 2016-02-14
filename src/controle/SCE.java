package controle;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import controle.util.GeradorDeIndentificadores;
import modelo.Requisicao;
import modelo.excecoes.EntradaIncorretaExcetion;
import modelo.excecoes.EsperarElevadorFinalizarException;

public class SCE
{
    private final GeradorDeIndentificadores geradorDeIndentificadores = new GeradorDeIndentificadores();
    private final MonitorSCE                monitorSCE                = new MonitorSCE();
    private List<Thread>                    elevadores                = new ArrayList<Thread>();

    public void lerArgumentos(String arquivo_entrada)
    {
        Integer[] argumentos_entrada = lerArquivoDeEntrada(arquivo_entrada);

        monitorSCE.setQuantidadeDeAndares(argumentos_entrada[0]);
        monitorSCE.setQuantidadeDeElevadores(argumentos_entrada[1]);
        monitorSCE.setMaximoDeUsuariosPorElevador(argumentos_entrada[2]);

        int inicio_declaracao_posicao_inicial_elevadores = 3;

        obterPosicaoInicialElevadores(argumentos_entrada, inicio_declaracao_posicao_inicial_elevadores);

        int inicio_declaracao_usuarios_em_cada_andar = monitorSCE.getQuantidadeDeElevadores()
                + inicio_declaracao_posicao_inicial_elevadores;

        obterRequisicaoPorAndar(argumentos_entrada, inicio_declaracao_usuarios_em_cada_andar);
    }

    public void criarElevadores()
    {
        int identificador = 0;
        for (Integer andarInicial : monitorSCE.getPosicaoInicialDosElevadores())
        {
            Elevador elevador = new Elevador(identificador, andarInicial, monitorSCE);
            elevadores.add(new Thread(elevador));
            identificador++;
        }
    }

    public void iniciarElevadores()
    {
        for (Thread elevador : elevadores)
        {
            elevador.start();
        }
    }

    public void esperarElevadoresTerminarem()
    {
        for (Thread elevador : elevadores)
        {
            try
            {
                elevador.join();
            }
            catch (InterruptedException e)
            {
                throw new EsperarElevadorFinalizarException("Erro ao esperar threads terminarem.", e);
            }
        }

    }

    private Integer[] lerArquivoDeEntrada(String arquivo)
    {
        Scanner leitor;
        try
        {
            leitor = new Scanner(new File(arquivo));
            List<Integer> argumentos = new ArrayList<Integer>();
            while (leitor.hasNextInt())
            {
                argumentos.add(leitor.nextInt());
            }
            leitor.close();

            Integer[] argumentos_entrada = argumentos.toArray(new Integer[argumentos.size()]);

            return argumentos_entrada;
        }
        catch (FileNotFoundException e)
        {
            e.printStackTrace();
        }

        return null;
    }

    private void obterPosicaoInicialElevadores(Integer[] argumentos_entrada,
            int inicio_declaracao_posicao_inicial_elevadores)
    {
        for (int i = 0; i < monitorSCE.getQuantidadeDeElevadores(); i++)
        {
            monitorSCE.getPosicaoInicialDosElevadores()
                    .add(argumentos_entrada[i + inicio_declaracao_posicao_inicial_elevadores]);
        }
    }

    private void obterRequisicaoPorAndar(Integer[] argumentos_entrada, int inicio_declaracao_usuarios_em_cada_andar)
    {
        int posicaoNoArrayDaQuantidadePessoasNoAndar = inicio_declaracao_usuarios_em_cada_andar;

        for (int i = 0; i < monitorSCE.getQuantidadeDeAndares(); i++)
        {
            List<Requisicao> destinos = new ArrayList<Requisicao>();

            if (posicaoNoArrayDaQuantidadePessoasNoAndar >= argumentos_entrada.length)
            {
                monitorSCE.getRequisicoesOrdenadasPorAndar().add(new ArrayList<Requisicao>());
                continue;
            }

            Integer quantidadeDePessoasNoAndar = argumentos_entrada[posicaoNoArrayDaQuantidadePessoasNoAndar];

            for (int j = 0; j < quantidadeDePessoasNoAndar; j++)
            {
                int andarDestino = argumentos_entrada[posicaoNoArrayDaQuantidadePessoasNoAndar + j + 1];

                if (andarDestino > (monitorSCE.getQuantidadeDeAndares() - 1))
                {
                    throw new EntradaIncorretaExcetion(EntradaIncorretaExcetion.ANDAR_DESTINO_INCORRETO);
                }

                destinos.add(new Requisicao(geradorDeIndentificadores.pegarNovoIdentificador(), andarDestino));
            }

            monitorSCE.incrementarNumeroDeRequisicoes(destinos);

            monitorSCE.getRequisicoesOrdenadasPorAndar().add(destinos);
            posicaoNoArrayDaQuantidadePessoasNoAndar += quantidadeDePessoasNoAndar + 1;
        }
    }

    public MonitorSCE getMonitorSCE()
    {
        return monitorSCE;
    }
}
