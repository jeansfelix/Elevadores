package controle;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

import org.junit.Assert;
import org.junit.Test;

public class SCEUnitTest
{
    private static final String DATASET_ENTRADA_MAIN = "test/controle/dataset/entrada";

    @Test
    public void testMain_DeveArmazenarValoresCorretos() throws Exception
    {
        String[] args = prepararEntrada();

        SCE.main(args);

        Assert.assertEquals(5, SCE.quantidade_andares);
        Assert.assertEquals(2, SCE.quantidade_elevadores);
        Assert.assertEquals(6, SCE.maximo_usuarios_por_elevador);

        int posicao_inicial_primeiro_elevador = 0;
        int posicao_inicial_segundo_elevador = 4;

        Assert.assertEquals(posicao_inicial_primeiro_elevador, SCE.posicao_inicial_elevadores.get(0).intValue());
        Assert.assertEquals(posicao_inicial_segundo_elevador, SCE.posicao_inicial_elevadores.get(1).intValue());

        Integer[] andaresEsperados = { 1, 4, 2, 3, 3, 4 };
        int quantidadeDePessoasNoPrimeiroAndar = 6;

        Assert.assertEquals(quantidadeDePessoasNoPrimeiroAndar, SCE.destino_usuarios_por_andar.get(0).length);
        Assert.assertArrayEquals(andaresEsperados, SCE.destino_usuarios_por_andar.get(0));
    }

    private String[] prepararEntrada() throws FileNotFoundException
    {
        String[] entrada = new String[36];

        File arq_entrada = new File(DATASET_ENTRADA_MAIN);

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
