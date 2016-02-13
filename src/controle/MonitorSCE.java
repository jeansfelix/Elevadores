package controle;

import java.util.ArrayList;
import java.util.List;

import modelo.TuplaAndarRequisicoes;
import modelo.Requisicao;

public class MonitorSCE
{
    private final List<Integer>          posicaoInicialDosElevadores  = new ArrayList<Integer>();
    private final List<List<Requisicao>> requisicoesOrdenadasPorAndar = new ArrayList<List<Requisicao>>();

    private int                          numeroDeRequisicoes;

    private int                          quantidadeDeAndares;
    private int                          quantidadeDeElevadores;
    private int                          maximoDeUsuariosPorElevador;

    public synchronized TuplaAndarRequisicoes obterPessoasNoAndarComRequisicaoMaisProximo(int andarAtual)
    {
        int andarDestino = buscarAndarComRequisicaoMaisProximo(andarAtual);
        
        List<Requisicao> requisicoes = new ArrayList<Requisicao>();

        for (int i = 0; i < maximoDeUsuariosPorElevador; i++)
        {
            if (requisicoesOrdenadasPorAndar.get(andarDestino).isEmpty())
            {
                break;
            }

            requisicoes.add(requisicoesOrdenadasPorAndar.get(andarDestino).remove(0));
        }

        decrementarNumeroDeRequisicoes(requisicoes);

        return new TuplaAndarRequisicoes(andarDestino, requisicoes);
    }

    protected Integer buscarAndarComRequisicaoMaisProximo(int andarAtual)
    {
        int andarDestinoAcima = andarAtual;
        int andarDestinoAbaixo = andarAtual;
        Integer andarDestino = null;

        while (!(andarDestinoAbaixo < 0 && andarDestinoAcima >= quantidadeDeAndares))
        {
            andarDestino = escolherAndarComRequisicaoMaisProximo(andarDestinoAcima, andarDestinoAbaixo);

            if (andarDestino != null) return andarDestino;

            if (andarDestinoAcima < quantidadeDeAndares
                    && requisicoesOrdenadasPorAndar.get(andarDestinoAcima).isEmpty())
            {
                andarDestinoAcima = andarDestinoAcima + 1;
            }

            if (andarDestinoAbaixo >= 0 && requisicoesOrdenadasPorAndar.get(andarDestinoAbaixo).isEmpty())
            {
                andarDestinoAbaixo = andarDestinoAbaixo - 1;
            }
        }

        return null;
    }

    private Integer escolherAndarComRequisicaoMaisProximo(int andarDestinoAcima, int andarDestinoAbaixo)
    {
        Integer andarDestino = null;

        if (andarDestinoAcima < quantidadeDeAndares && verificarSeAndarTemRequisicao(andarDestinoAcima))
        {
            andarDestino = andarDestinoAcima;
        }

        if (andarDestinoAbaixo >= 0 && verificarSeAndarTemRequisicao(andarDestinoAbaixo))
        {
            if (andarDestino != null)
            {
                return obterAndarComMaisPessoasEsperando(andarDestinoAbaixo, andarDestino);
            }

            andarDestino = andarDestinoAbaixo;
        }

        return andarDestino;
    }

    private int obterAndarComMaisPessoasEsperando(int andarDestinoAbaixo, Integer andarDestino)
    {
        if (requisicoesOrdenadasPorAndar.get(andarDestinoAbaixo).size() > requisicoesOrdenadasPorAndar.get(andarDestino)
                .size())
        {
            return andarDestinoAbaixo;
        }

        return andarDestino;
    }

    private boolean verificarSeAndarTemRequisicao(int andar)
    {
        return !requisicoesOrdenadasPorAndar.get(andar).isEmpty();
    }
    
    public synchronized boolean existemRequisicoes()
    {
        return numeroDeRequisicoes > 0;
    }

    public void incrementarNumeroDeRequisicoes(List<Requisicao> destinos)
    {
        if (!destinos.isEmpty())
        {
            numeroDeRequisicoes += destinos.size();
        }
    }

    public void decrementarNumeroDeRequisicoes(List<Requisicao> requisicoes)
    {
        numeroDeRequisicoes -= requisicoes.size();
    }

    public int getNumeroDeRequisicoes()
    {
        return numeroDeRequisicoes;
    }

    public void setNumeroDeRequisicoes(int numeroDeRequisicoes)
    {
        this.numeroDeRequisicoes = numeroDeRequisicoes;
    }

    public int getQuantidadeDeAndares()
    {
        return quantidadeDeAndares;
    }

    public void setQuantidadeDeAndares(int quantidadeDeAndares)
    {
        this.quantidadeDeAndares = quantidadeDeAndares;
    }

    public int getQuantidadeDeElevadores()
    {
        return quantidadeDeElevadores;
    }

    public void setQuantidadeDeElevadores(int quantidadeDeElevadores)
    {
        this.quantidadeDeElevadores = quantidadeDeElevadores;
    }

    public int getMaximoDeUsuariosPorElevador()
    {
        return maximoDeUsuariosPorElevador;
    }

    public void setMaximoDeUsuariosPorElevador(int maximoDeUsuariosPorElevador)
    {
        this.maximoDeUsuariosPorElevador = maximoDeUsuariosPorElevador;
    }

    public List<Integer> getPosicaoInicialDosElevadores()
    {
        return posicaoInicialDosElevadores;
    }

    public List<List<Requisicao>> getRequisicoesOrdenadasPorAndar()
    {
        return requisicoesOrdenadasPorAndar;
    }
}
