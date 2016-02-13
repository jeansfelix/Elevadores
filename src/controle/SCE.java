package controle;

import java.util.ArrayList;
import java.util.List;

import controle.util.GeradorDeIndentificadores;
import modelo.Requisicao;
import modelo.excecoes.EntradaIncorretaExcetion;

public class SCE
{
    private final GeradorDeIndentificadores geradorDeIndentificadores = new GeradorDeIndentificadores();
    private final MonitorSCE                monitorSCE                = new MonitorSCE();
    private List<Thread>                    elevadores                = new ArrayList<Thread>();

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
                e.printStackTrace();
            }
        }

    }

    public void lerArgumentos(String[] args)
    {
        monitorSCE.setQuantidadeDeAndares(Integer.parseInt(args[0]));
        monitorSCE.setQuantidadeDeElevadores(Integer.parseInt(args[1]));
        monitorSCE.setMaximoDeUsuariosPorElevador(Integer.parseInt(args[2]));

        int inicio_declaracao_posicao_inicial_elevadores = 3;

        obterPosicaoInicialElevadores(args, inicio_declaracao_posicao_inicial_elevadores);

        int inicio_declaracao_usuarios_em_cada_andar = monitorSCE.getQuantidadeDeElevadores()
                + inicio_declaracao_posicao_inicial_elevadores;

        obterRequisicaoPorAndar(args, inicio_declaracao_usuarios_em_cada_andar);
    }

    private void obterPosicaoInicialElevadores(String[] args, int inicio_declaracao_posicao_inicial_elevadores)
    {
        for (int i = 0; i < monitorSCE.getQuantidadeDeElevadores(); i++)
        {
            monitorSCE.getPosicaoInicialDosElevadores()
                    .add(Integer.parseInt(args[i + inicio_declaracao_posicao_inicial_elevadores]));
        }
    }

    private void obterRequisicaoPorAndar(String[] args, int inicio_declaracao_usuarios_em_cada_andar)
    {
        int posicaoNoArrayDaQuantidadePessoasNoAndar = inicio_declaracao_usuarios_em_cada_andar;

        for (int i = 0; i < monitorSCE.getQuantidadeDeAndares(); i++)
        {
            List<Requisicao> destinos = new ArrayList<Requisicao>();
            String stringQuantidadeDePessoasNoAndar = args[posicaoNoArrayDaQuantidadePessoasNoAndar];

            if (stringQuantidadeDePessoasNoAndar == null || stringQuantidadeDePessoasNoAndar.isEmpty())
            {
                monitorSCE.getRequisicoesOrdenadasPorAndar().add(new ArrayList<Requisicao>());
                continue;
            }

            int quantidadeDePessoasNoAndar = Integer.parseInt(stringQuantidadeDePessoasNoAndar);

            for (int j = 0; j < quantidadeDePessoasNoAndar; j++)
            {
                int andarDestino = Integer.parseInt(args[posicaoNoArrayDaQuantidadePessoasNoAndar + j + 1]);

                if (andarDestino > (monitorSCE.getQuantidadeDeAndares() - 1))
                {
                    throw new EntradaIncorretaExcetion(EntradaIncorretaExcetion.ANDAR_DESTINO_INCORRETO);
                }

                destinos.add(new Requisicao(geradorDeIndentificadores.pegarNovoIdentificador(), andarDestino));
            }

            monitorSCE.incrementarNumeroDeRequisicoes(destinos);

            monitorSCE.getRequisicoesOrdenadasPorAndar().add(destinos);
            posicaoNoArrayDaQuantidadePessoasNoAndar = posicaoNoArrayDaQuantidadePessoasNoAndar
                    + quantidadeDePessoasNoAndar + 1;
        }
    }

    public MonitorSCE getMonitorSCE()
    {
        return monitorSCE;
    }
}
