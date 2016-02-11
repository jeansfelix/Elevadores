package controle;

import java.util.ArrayList;
import java.util.List;

import modelo.excecoes.EntradaIncorretaExcetion;

public class SCE
{
    protected final static List<Integer>   posicao_inicial_elevadores = new ArrayList<Integer>();
    protected final static List<Integer[]> destino_usuarios_por_andar = new ArrayList<Integer[]>();

    protected static int                   quantidade_andares;
    protected static int                   quantidade_elevadores;
    protected static int                   maximo_usuarios_por_elevador;

    public static void main(String[] args)
    {
        lerArgumentos(args);
    }

    private static void lerArgumentos(String[] args)
    {
        quantidade_andares = Integer.parseInt(args[0]);
        quantidade_elevadores = Integer.parseInt(args[1]);
        maximo_usuarios_por_elevador = Integer.parseInt(args[2]);

        int inicio_declaracao_posicao_inicial_elevadores = 3;

        for (int i = 0; i < quantidade_elevadores; i++)
        {
            posicao_inicial_elevadores.add(Integer.parseInt(args[i + inicio_declaracao_posicao_inicial_elevadores]));
        }

        int inicio_declaracao_usuarios_em_cada_andar = quantidade_elevadores
                + inicio_declaracao_posicao_inicial_elevadores;

        int posicaoNoArrayDaQuantidadePessoasNoAndar = inicio_declaracao_usuarios_em_cada_andar;

        for (int i = 0; i < quantidade_andares; i++)
        {
            int quantidadeDePessoasNoAndar = Integer.parseInt(args[posicaoNoArrayDaQuantidadePessoasNoAndar]);
            Integer[] destinos = new Integer[quantidadeDePessoasNoAndar];

            for (int j = 0; j < destinos.length; j++)
            {
                int andarDestino = Integer.parseInt(args[posicaoNoArrayDaQuantidadePessoasNoAndar + j + 1]);
                
                if (andarDestino > (quantidade_andares - 1)) 
                {
                    throw new EntradaIncorretaExcetion(EntradaIncorretaExcetion.ANDAR_DESTINO_INCORRETO);
                }
                
                destinos[j] = andarDestino;
            }

            destino_usuarios_por_andar.add(destinos);
            posicaoNoArrayDaQuantidadePessoasNoAndar = posicaoNoArrayDaQuantidadePessoasNoAndar
                    + quantidadeDePessoasNoAndar + 1;
        }
    }
}
