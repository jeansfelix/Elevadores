package modelo;

import java.util.List;

/**
 * Tupla que guarda um andar e suas requisições.
 * @author jfelix
 *
 */
public class TuplaAndarRequisicoes
{
    Integer          andar;
    List<Requisicao> requisicoes;

    public TuplaAndarRequisicoes(Integer andar, List<Requisicao> requisicoes)
    {
        this.andar = andar;
        this.requisicoes = requisicoes;
    }

    public Integer getAndar()
    {
        return andar;
    }

    public List<Requisicao> getRequisicoes()
    {
        return requisicoes;
    }
}
