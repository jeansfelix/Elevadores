package controle;

import java.util.List;

import org.junit.Assert;
import org.junit.Test;

import modelo.Requisicao;
import modelo.excecoes.EntradaIncorretaExcetion;

public class SCEUnitTest extends UnitTest
{
    private static final String CAPACIDADE_MAXIMA_TRES_ANDAR_DOIS_COM_QUATRO_REQUISICOES  = "test/controle/dataset/capacidadeMaximaTres_AndarDoisComQuatroRequisicoes";
    private static final String CAPACIDADE_MAXIMA_CINCO_ANDAR_DOIS_COM_QUATRO_REQUISICOES = "test/controle/dataset/capacidadeMaximaCinco_AndarDoisComQuatroRequisicoes";
    private static final String DATASET_ENTRADA_CORRETA_MAIN                              = "test/controle/dataset/entrada_correta";
    private static final String DATASET_ENTRADA_INCORRETA_MAIN                            = "test/controle/dataset/entrada_incorreta";

    @Test
    public void testLerArgumentos_DeveArmazenarValoresCorretos() throws Exception
    {
        String[] args = prepararEntrada(DATASET_ENTRADA_CORRETA_MAIN);

        SCE.lerArgumentos(args);

        Assert.assertEquals(5, SCE.quantidadeDeAndares);
        Assert.assertEquals(2, SCE.quantidadeDeElevadores);
        Assert.assertEquals(6, SCE.maximoDeUsuariosPorElevador);

        int posicao_inicial_primeiro_elevador = 0;
        int posicao_inicial_segundo_elevador = 4;

        Assert.assertEquals(posicao_inicial_primeiro_elevador, SCE.posicaoInicialDosElevadores.get(0).intValue());
        Assert.assertEquals(posicao_inicial_segundo_elevador, SCE.posicaoInicialDosElevadores.get(1).intValue());

        int quantidadeDePessoasNoPrimeiroAndar = 6;
        int[] andaresDestinoEsperadosNoPrimeiroAndar = { 1, 4, 2, 3, 3, 4 };

        Assert.assertEquals(quantidadeDePessoasNoPrimeiroAndar, SCE.requisicoesOrdenadasPorAndar.get(0).size());
        verificarRequisicoes(0, andaresDestinoEsperadosNoPrimeiroAndar);

        int quantidadeDePessoasNoSegundoAndar = 4;
        int[] andaresDestinoEsperadosNoSegundoAndar = { 0, 3, 0, 4 };

        Assert.assertEquals(quantidadeDePessoasNoSegundoAndar, SCE.requisicoesOrdenadasPorAndar.get(1).size());
        verificarRequisicoes(1, andaresDestinoEsperadosNoSegundoAndar);
    }

    @Test
    public void testLerArgumentos_AndarDestinoMaiorQueUltimoAndar_DeveLancarExcecao() throws Exception
    {
        String[] args = prepararEntrada(DATASET_ENTRADA_INCORRETA_MAIN);

        try
        {
            SCE.lerArgumentos(args);
            Assert.fail("Deveria lançar exceção ao ler andar destino maior que o último andar.");
        }
        catch (Exception e)
        {
            Assert.assertEquals(e.getMessage(), EntradaIncorretaExcetion.ANDAR_DESTINO_INCORRETO);
        }
    }

    @Test
    public void testAtenderRequisicao_CapacidadeMaximaTres_AndarDois_RequisicoesInsuficientes_DeveRetornarPrimeirasRequisicoes()
            throws Exception
    {
        String[] args = prepararEntrada(CAPACIDADE_MAXIMA_TRES_ANDAR_DOIS_COM_QUATRO_REQUISICOES);
        SCE.lerArgumentos(args);

        Assert.assertEquals(4, SCE.requisicoesOrdenadasPorAndar.get(2).size());

        List<Requisicao> requisicoes = SCE.atenderRequisicao(2);

        Assert.assertEquals(3, requisicoes.size());

        Assert.assertEquals(1, requisicoes.get(0).getAndar());
        Assert.assertEquals(4, requisicoes.get(1).getAndar());
        Assert.assertEquals(4, requisicoes.get(2).getAndar());

        Assert.assertEquals(1, SCE.requisicoesOrdenadasPorAndar.get(2).size());
    }

    @Test
    public void testAtenderRequisicao_CapacidadeMaximaTres_AndarDois_DeveRetornarTresPrimeirasRequisicoes()
            throws Exception
    {
        String[] args = prepararEntrada(CAPACIDADE_MAXIMA_CINCO_ANDAR_DOIS_COM_QUATRO_REQUISICOES);
        SCE.lerArgumentos(args);

        Assert.assertEquals(4, SCE.requisicoesOrdenadasPorAndar.get(2).size());

        List<Requisicao> requisicoes = SCE.atenderRequisicao(2);

        Assert.assertEquals(4, requisicoes.size());

        Assert.assertEquals(1, requisicoes.get(0).getAndar());
        Assert.assertEquals(4, requisicoes.get(1).getAndar());
        Assert.assertEquals(4, requisicoes.get(2).getAndar());
        Assert.assertEquals(3, requisicoes.get(3).getAndar());

        Assert.assertEquals(0, SCE.requisicoesOrdenadasPorAndar.get(2).size());
    }

    private void verificarRequisicoes(int andar, int[] andaresDestinoEsperados)
    {
        for (int i = 0; i < andaresDestinoEsperados.length; i++)
        {
            List<Requisicao> listaRequisicoes = SCE.requisicoesOrdenadasPorAndar.get(andar);
            Assert.assertEquals(andaresDestinoEsperados[i], listaRequisicoes.get(i).getAndar());
        }
    }

}
