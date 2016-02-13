package controle;

import java.util.ArrayList;
import java.util.List;

import modelo.Requisicao;

public class MonitorSCE
{
    private final List<Integer>          posicaoInicialDosElevadores  = new ArrayList<Integer>();
    private final List<List<Requisicao>> requisicoesOrdenadasPorAndar = new ArrayList<List<Requisicao>>();

    private int                          numeroDeRequisicoes;

    private int                          quantidadeDeAndares;
    private int                          quantidadeDeElevadores;
    private int                          maximoDeUsuariosPorElevador;

    public synchronized List<Requisicao> atenderRequisicao(int andar)
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

        decrementarNumeroDeRequisicoes(requisicoes);

        return requisicoes;
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
    
    public boolean existemRequisicoes()
    {
        return numeroDeRequisicoes > 0;
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
