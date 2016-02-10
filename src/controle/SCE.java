package controle;

import java.util.HashMap;
import java.util.Map;

public class SCE
{
    protected final static Map<Integer, Integer>   posicao_inicial_elevadores = new HashMap<Integer, Integer>();
    protected final static Map<Integer, Integer[]> destino_usuarios_por_andar = new HashMap<Integer, Integer[]>();

    protected static int                           quantidade_andares;
    protected static int                           quantidade_elevadores;
    protected static int                           maximo_usuarios_por_elevador;

    public static void main(String[] args)
    {
        quantidade_andares = Integer.parseInt(args[0]);
        quantidade_elevadores = Integer.parseInt(args[1]);
        maximo_usuarios_por_elevador = Integer.parseInt(args[2]);

        int inicio_declaracao_posicao_inicial_elevadores = 3;

        for (int i = 0; i < quantidade_elevadores; i++)
        {
            posicao_inicial_elevadores.put(i, Integer.parseInt(args[i + inicio_declaracao_posicao_inicial_elevadores]));
        }

        int inicio_declaracao_usuarios_em_cada_andar = quantidade_elevadores
                + inicio_declaracao_posicao_inicial_elevadores;

        for (int i = 0; i < quantidade_andares; i++)
        {
            int quantidadeDePessoasNoAndar = Integer.parseInt(args[i + inicio_declaracao_usuarios_em_cada_andar]);
            Integer[] destinos = new Integer[quantidadeDePessoasNoAndar];

            for (int j = 0; j < destinos.length; j++)
            {
                destinos[j] = Integer.parseInt(args[i + 1 + j + inicio_declaracao_usuarios_em_cada_andar]);
            }

            destino_usuarios_por_andar.put(i, destinos);
        }
    }
}
