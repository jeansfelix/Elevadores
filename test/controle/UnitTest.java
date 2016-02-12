package controle;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

import org.junit.After;

import controle.SCE;

public class UnitTest
{
    @After
    public void limparSCE()
    {
        SCE.requisicoesOrdenadasPorAndar.clear();
        SCE.posicaoInicialDosElevadores.clear();
        SCE.numeroDeRequisicoes = 0;
    }
    
    public String[] prepararEntrada(String dataset_entrada) throws FileNotFoundException
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
