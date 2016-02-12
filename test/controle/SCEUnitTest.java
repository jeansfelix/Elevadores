package controle;

import java.util.List;

import org.junit.Assert;
import org.junit.Test;

import modelo.Requisicao;
import modelo.excecoes.EntradaIncorretaExcetion;

public class SCEUnitTest extends UnitTest
{
    private static final String DATASET_ENTRADA_CORRETA_MAIN   = "test/controle/dataset/entrada_correta";
    private static final String DATASET_ENTRADA_INCORRETA_MAIN = "test/controle/dataset/entrada_incorreta";

    @Test
    public void testMain_DeveArmazenarValoresCorretos() throws Exception
    {
        String[] args = prepararEntrada(DATASET_ENTRADA_CORRETA_MAIN);

        SCE.main(args);

        Assert.assertEquals(5, SCE.quantidade_andares);
        Assert.assertEquals(2, SCE.quantidade_elevadores);
        Assert.assertEquals(6, SCE.maximo_usuarios_por_elevador);

        int posicao_inicial_primeiro_elevador = 0;
        int posicao_inicial_segundo_elevador = 4;

        Assert.assertEquals(posicao_inicial_primeiro_elevador, SCE.posicao_inicial_elevadores.get(0).intValue());
        Assert.assertEquals(posicao_inicial_segundo_elevador, SCE.posicao_inicial_elevadores.get(1).intValue());

        int quantidadeDePessoasNoPrimeiroAndar = 6;
        int[] andaresDestinoEsperadosNoPrimeiroAndar = { 1, 4, 2, 3, 3, 4 };

        Assert.assertEquals(quantidadeDePessoasNoPrimeiroAndar, SCE.requisicoes_ordenadas_por_andar.get(0).size());
        verificarRequisicoes(0, andaresDestinoEsperadosNoPrimeiroAndar);

        int quantidadeDePessoasNoSegundoAndar = 4;
        int[] andaresDestinoEsperadosNoSegundoAndar = { 0, 3, 0, 4 };

        Assert.assertEquals(quantidadeDePessoasNoSegundoAndar, SCE.requisicoes_ordenadas_por_andar.get(1).size());
        verificarRequisicoes(1, andaresDestinoEsperadosNoSegundoAndar);
    }

    @Test
    public void testMain_AndarDestinoMaiorQueUltimoAndar_DeveLancarExcecao() throws Exception
    {
        String[] args = prepararEntrada(DATASET_ENTRADA_INCORRETA_MAIN);

        try
        {
            SCE.main(args);
            Assert.fail("Deveria lançar exceção ao ler andar destino maior que o último andar.");
        }
        catch (Exception e)
        {
            Assert.assertEquals(e.getMessage(), EntradaIncorretaExcetion.ANDAR_DESTINO_INCORRETO);
        }
    }

    private void verificarRequisicoes(int andar, int[] andaresDestinoEsperados)
    {
        for (int i = 0; i < andaresDestinoEsperados.length; i++)
        {
            List<Requisicao> listaRequisicoes = SCE.requisicoes_ordenadas_por_andar.get(andar);
            Assert.assertEquals(andaresDestinoEsperados[i], listaRequisicoes.get(i).getAndar());
        }
    }

}
