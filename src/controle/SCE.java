package controle;

import java.util.ArrayList;
import java.util.List;

import modelo.Requisicao;
import modelo.excecoes.EntradaIncorretaExcetion;

public class SCE
{
    protected final static List<Integer>          posicaoInicialDosElevadores  = new ArrayList<Integer>();
    protected final static List<List<Requisicao>> requisicoesOrdenadasPorAndar = new ArrayList<List<Requisicao>>();

    protected static int                          numeroDeRequisicoes           = 0;

    protected static int                          quantidadeDeAndares;
    protected static int                          quantidadeDeElevadores;
    protected static int                          maximoDeUsuariosPorElevador;

    private static GeradorDeIndentificadores      geradorDeIndentificadores    = new GeradorDeIndentificadores();

    public static void lerArgumentos(String[] args)
    {
        quantidadeDeAndares = Integer.parseInt(args[0]);
        quantidadeDeElevadores = Integer.parseInt(args[1]);
        maximoDeUsuariosPorElevador = Integer.parseInt(args[2]);

        int inicio_declaracao_posicao_inicial_elevadores = 3;

        obterPosicaoInicialElevadores(args, inicio_declaracao_posicao_inicial_elevadores);

        int inicio_declaracao_usuarios_em_cada_andar = quantidadeDeElevadores
                + inicio_declaracao_posicao_inicial_elevadores;

        obterRequisicaoPorAndar(args, inicio_declaracao_usuarios_em_cada_andar);
    }

    public static synchronized List<Requisicao> atenderRequisicao(int andar)
    {
        List<Requisicao> requisicoes = new ArrayList<Requisicao>();
        
        for (int i = 0; i < maximoDeUsuariosPorElevador; i++)
        {
            if (requisicoesOrdenadasPorAndar.get(andar).isEmpty()) 
            {
                break;
            }
            
            requisicoes.add(requisicoesOrdenadasPorAndar.get(andar).remove(0));
        } 
        
        return requisicoes;
    }

    private static void obterRequisicaoPorAndar(String[] args, int inicio_declaracao_usuarios_em_cada_andar)
    {
        int posicaoNoArrayDaQuantidadePessoasNoAndar = inicio_declaracao_usuarios_em_cada_andar;

        for (int i = 0; i < quantidadeDeAndares; i++)
        {
            List<Requisicao> destinos = new ArrayList<Requisicao>();
            String stringQuantidadeDePessoasNoAndar = args[posicaoNoArrayDaQuantidadePessoasNoAndar];

            if (stringQuantidadeDePessoasNoAndar == null || stringQuantidadeDePessoasNoAndar.isEmpty())
            {
                requisicoesOrdenadasPorAndar.add(new ArrayList<Requisicao>());
                continue;
            }

            int quantidadeDePessoasNoAndar = Integer.parseInt(stringQuantidadeDePessoasNoAndar);

            for (int j = 0; j < quantidadeDePessoasNoAndar; j++)
            {
                int andarDestino = Integer.parseInt(args[posicaoNoArrayDaQuantidadePessoasNoAndar + j + 1]);

                if (andarDestino > (quantidadeDeAndares - 1))
                {
                    throw new EntradaIncorretaExcetion(EntradaIncorretaExcetion.ANDAR_DESTINO_INCORRETO);
                }

                destinos.add(new Requisicao(geradorDeIndentificadores.pegarNovoIdentificador(), andarDestino));
            }

            if (!destinos.isEmpty())
            {
                numeroDeRequisicoes += destinos.size();
            }

            requisicoesOrdenadasPorAndar.add(destinos);
            posicaoNoArrayDaQuantidadePessoasNoAndar = posicaoNoArrayDaQuantidadePessoasNoAndar
                    + quantidadeDePessoasNoAndar + 1;
        }
    }

    private static void obterPosicaoInicialElevadores(String[] args, int inicio_declaracao_posicao_inicial_elevadores)
    {
        for (int i = 0; i < quantidadeDeElevadores; i++)
        {
            posicaoInicialDosElevadores.add(Integer.parseInt(args[i + inicio_declaracao_posicao_inicial_elevadores]));
        }
    }
}
