package controle;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

import org.junit.After;
import org.junit.Assert;
import org.junit.Test;

import modelo.excecoes.EntradaIncorretaExcetion;

public class SCEUnitTest
{
    private static final String DATASET_ENTRADA_CORRETA_MAIN   = "test/controle/dataset/entrada_correta";
    private static final String DATASET_ENTRADA_INCORRETA_MAIN = "test/controle/dataset/entrada_incorreta";

    @After
    public void limparSCE()
    {
        SCE.destino_usuarios_por_andar.clear();
        SCE.posicao_inicial_elevadores.clear();
    }
    
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
        Integer[] andaresDestinoEsperadosNoPrimeiroAndar = { 1, 4, 2, 3, 3, 4 };

        Assert.assertEquals(quantidadeDePessoasNoPrimeiroAndar, SCE.destino_usuarios_por_andar.get(0).length);
        Assert.assertArrayEquals(andaresDestinoEsperadosNoPrimeiroAndar, SCE.destino_usuarios_por_andar.get(0));

        int quantidadeDePessoasNoSegundoAndar = 4;
        Integer[] andaresDestinoEsperadosNoSegundoAndar = { 0, 3, 0, 4 };

        Assert.assertEquals(quantidadeDePessoasNoSegundoAndar, SCE.destino_usuarios_por_andar.get(1).length);
        Assert.assertArrayEquals(andaresDestinoEsperadosNoSegundoAndar, SCE.destino_usuarios_por_andar.get(1));
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

    private String[] prepararEntrada(String dataset_entrada) throws FileNotFoundException
    {
        String[] entrada = new String[36];

        File arq_entrada = new File(dataset_entrada);

        Scanner leitor = new Scanner(arq_entrada);

        int i = 0;
        while (leitor.hasNext())
        {
            entrada[i] = leitor.next();
            i++;
        }

        leitor.close();

        return entrada;
    }
}
