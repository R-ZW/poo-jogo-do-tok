/**
 * Representa uma rodada no jogo, controlando a etapa, o jogador atual e a peça a ser movida.
 */
public class Rodada {
    public int etapa;
    public int jogadorAtual;
    public int pecaASerMovida;

    /**
     * Representa o jogador na rodada atual.
     */
    public static final int JOGADOR_1 = 1;
    public static final int JOGADOR_2 = 2;

    /**
     * Representa a peça a ser movida na rodada atual padrao ou tok.
     */
    public static final int PECA_PADRAO = 1;
    public static final int PECA_TOK = 2;

    /**
     * Cria uma nova instância de Rodada, inicializando a etapa, com jogador sendo o 2 e a peça a ser movida
     * sendo uma peça comum.
     */
    public Rodada() {
        this.etapa = 0;
        this.jogadorAtual = JOGADOR_2;
        this.pecaASerMovida = PECA_PADRAO;
    }

    /**
     * Passa para a próxima rodada, atualizando a etapa, o jogador atual e a peça a ser movida.
     */
    public void passarRodada() {
        this.etapa++;
        if (jogadorAtual == JOGADOR_1) {
            jogadorAtual = JOGADOR_2;
        } else {
            jogadorAtual = JOGADOR_1;
        }
        pecaASerMovida = PECA_TOK;
    }

    /**
     * Passa para a metade da rodada, alterando a peça a ser movida usada para controlar o tok.
     */
    public void passarMetadeRodada(){
        pecaASerMovida = PECA_PADRAO;
    }
}
