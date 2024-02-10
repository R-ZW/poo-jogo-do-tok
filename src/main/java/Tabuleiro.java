/**
 * A classe representa o tabuleiro do jogo e suas funcionalidades.
 */
public class Tabuleiro {

    /**
     * Representa o tabuleiro do jogo como uma matriz de peças.
     */
    private Peca[][] tabuleiro;

    /**
     * Vetor de resultados que armazena informações sobre a condição de vitória.
     */
    public int[] resultado;

    /**
     * Representa a situação de vitória.
     */
    public static final int SITUACAO_VITORIA = 0;
    public static final int AINDA_NAO_HOUVE_VITORIA = 0;
    public static final int HOUVE_VITORIA = 1;

    /**
     * Representa os resultados possíveis do vetor de resultados.
     */
    public static final int JOGADOR_VENCEDOR = 1;
    public static final int NENHUM_JOGADOR = 0;
    public static final int JOGADOR_1 = 1;
    public static final int JOGADOR_2 = 2;

    /**
     * Representa os tipos de vitória possíveis.
     */

    public static final int TIPO_VITORIA = 2;
    public static final int VITORIA_POR_POSICAO = 1;
    public static final int VITORIA_POR_IMOBILIZACAO = 2;

    /**
     * Construtor padrão que inicializa o tabuleiro e define as posições iniciais das peças.
     */
    public Tabuleiro() {
        this.tabuleiro = new Peca[5][5];
        definirPosicoes();
    }

    /**
     * Inicializa as posições iniciais das peças no tabuleiro.
     */
    private void definirPosicoes(){
        for(int linha = 0; linha < 5; linha++){
            for(int coluna = 0; coluna < 5; coluna++){
                switch (linha) {
                    case 0 -> this.tabuleiro[linha][coluna] = new Peca(linha, coluna, Peca.JOGADOR1);
                    case 4 -> this.tabuleiro[linha][coluna] = new Peca(linha, coluna, Peca.JOGADOR2);
                    case 2 -> {
                        if (coluna == 2) {
                            this.tabuleiro[linha][coluna] = new Peca(linha, coluna, Peca.TOK);
                        } else {
                            this.tabuleiro[linha][coluna] = new Peca(linha, coluna, Peca.SLOT_VAZIO);
                        }
                    }
                    default -> this.tabuleiro[linha][coluna] = new Peca(linha, coluna, Peca.SLOT_VAZIO);
                }
            }
        }
    }

    /**
     * Adiciona uma peça ao tabuleiro.
     *
     * @param peca A peça a ser adicionada.
     * @param tipo O tipo da peça a ser adicionada.
     */
    public void adicionarPeca(Peca peca, int tipo){
        this.tabuleiro[peca.getLinha()][peca.getColuna()] = new Peca(peca.getLinha(), peca.getColuna(), tipo);
    }

    /**
     * Remove uma peça do tabuleiro.
     *
     * @param peca A peça a ser removida.
     */
    public void removerPeca(Peca peca){
        adicionarPeca(peca, Peca.SLOT_VAZIO);
    }

    /**
     * Obtém a peça em uma determinada posição do tabuleiro.
     *
     * @param linha A linha da posição desejada.
     * @param coluna A coluna da posição desejada.
     * @return A peça na posição especificada.
     */
    public Peca getPecaAt(int linha, int coluna){
        return tabuleiro[linha][coluna];
    }

    /**
     * Verifica a condição de vitória do jogo.
     *
     * @param rodada A rodada atual do jogo.
     * @return Um vetor de resultados que contém informações sobre a condição de vitória.
     */
    public int[] verificarCondicaoDeVitoria(Rodada rodada){
        resultado = new int[3];

        if(verificarPosicaoDoTok() != NENHUM_JOGADOR){
            resultado[SITUACAO_VITORIA] = HOUVE_VITORIA;
            resultado[JOGADOR_VENCEDOR] = verificarPosicaoDoTok();
            resultado[TIPO_VITORIA] = VITORIA_POR_POSICAO;

            return resultado;
        } else {
            resultado[SITUACAO_VITORIA] = AINDA_NAO_HOUVE_VITORIA;
        }

        if(verificarDisponibilidadeDoTok()){
            resultado[SITUACAO_VITORIA] = AINDA_NAO_HOUVE_VITORIA;
        } else {
            resultado[SITUACAO_VITORIA] = HOUVE_VITORIA;

            if(rodada.jogadorAtual == JOGADOR_1){
                resultado[JOGADOR_VENCEDOR] = JOGADOR_1;
            } else {
                resultado[JOGADOR_VENCEDOR] = JOGADOR_2;
            }
            resultado[TIPO_VITORIA] = VITORIA_POR_IMOBILIZACAO;

            return resultado;
        }

        return resultado;
    }

    /**
     * Verifica a posição do TOK para definir vitória.
     *
     * @return O jogador vencedor (JOGADOR_1 ou JOGADOR_2) ou NENHUM_JOGADOR se não houver vencedor.
     */
    public int verificarPosicaoDoTok(){
        for(int j=0; j<5; j++){
            Peca peca = getPecaAt(0, j);
            if(peca.getTipo() == Peca.TOK){
                return JOGADOR_1;
            }
        }

        for(int j=0; j<5; j++){
            Peca peca = getPecaAt(4, j);
            if(peca.getTipo() == Peca.TOK){
                return JOGADOR_2;
            }
        }

        return NENHUM_JOGADOR;
    }

    /**
     * Verifica a disponibilidade dos movimentos do tok no tabuleiro.
     *
     * @return true se houver movimentos disponíveis, false caso contrário.
     */
    public boolean verificarDisponibilidadeDoTok(){
        Peca tok = new Peca(0,0,Peca.SLOT_VAZIO);

        for(int linha = 0; linha < 5; linha++){
            for(int coluna = 0; coluna < 5; coluna++){
                Peca peca = getPecaAt(linha, coluna);
                if(peca.getTipo() == Peca.TOK){
                    tok = peca;
                }
            }
        }

        //ACIMA
        if(tok.getLinha() > Peca.LIMITE_SUPERIOR){
            Peca slotAcima = getPecaAt(tok.getLinha() - 1, tok.getColuna());
            if(slotAcima.getTipo() == Peca.SLOT_VAZIO){
                return true;
            }
        }
        //ABAIXO
        if(tok.getLinha() < Peca.LIMITE_INFERIOR){
            Peca slotAbaixo = getPecaAt(tok.getLinha() + 1, tok.getColuna());
            if(slotAbaixo.getTipo() == Peca.SLOT_VAZIO){
                return true;
            }
        }
        //ESQUERDA
        if(tok.getColuna() > Peca.LIMITE_ESQUERDA){
            Peca slotEsquerda = getPecaAt(tok.getLinha(), tok.getColuna() - 1);
            if(slotEsquerda.getTipo() == Peca.SLOT_VAZIO){
                return true;
            }
        }
        //DIREITA
        if(tok.getColuna() < Peca.LIMITE_DIREITA){
            Peca slotDireita = getPecaAt(tok.getLinha(), tok.getColuna() + 1);
            if(slotDireita.getTipo() == Peca.SLOT_VAZIO){
                return true;
            }
        }
        return false;
    }
}
