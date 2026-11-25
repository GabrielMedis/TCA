package projeto_damas;

import java.util.Scanner;

class Peca {
    char simbolo;
    boolean ehRainha;

    Peca(char simbolo) {
        this.simbolo = simbolo;
        this.ehRainha = (simbolo == 'X' || simbolo == 'O');
    }

    boolean ehBranca() { return simbolo == 'o' || simbolo == 'O'; }
    boolean ehPreta() { return simbolo == 'x' || simbolo == 'X'; }

    boolean ehDoJogador(char jogador) {
        if (jogador == 'o') return ehBranca();
        if (jogador == 'x') return ehPreta();
        return false;
    }

    void virarRainha() {
        ehRainha = true;
        if (simbolo == 'x') simbolo = 'X';
        if (simbolo == 'o') simbolo = 'O';
    }
}

public class damas {

    static int TAM;
    static Peca[][] tab;
    static Scanner sc = new Scanner(System.in);
    static char vez = 'o';

    static boolean temQueContinuarComendo = false;
    static int continuaLinha = -1;
    static int continuaColuna = -1;

    static final String RESET = "\u001B[0m";
    static String COR_JOGADOR_1 = "\u001B[40m\u001B[37m";
    static String COR_JOGADOR_2 = "\u001B[47m\u001B[30m";
    static final String CASA_ESCURA = "\u001B[100m\u001B[30m";
    static final String AMARELO_TEXTO = "\u001B[33m";
    static final String CYAN_TEXTO = "\u001B[36m";
    static final String VERMELHO_TEXTO = "\u001B[31m";
    static final String VERDE_TEXTO = "\u001B[32m";
    static final String ROXO_TEXTO = "\u001B[35m";

    static void limparTela() {
        try {
            String so = System.getProperty("os.name").toLowerCase();
            if (so.contains("win")) {
                new ProcessBuilder("cmd", "/c", "cls").inheritIO().start().waitFor();
            } else {
                System.out.print("\033[H\033[3J\033[2J");
                System.out.flush();
            }
        } catch (Exception e) {
            System.out.print("\033[H\033[3J\033[2J");
            System.out.flush();
        }
        System.out.print("\033[H");
        System.out.flush();
    }

    public static void main(String[] args) {
        String[] opcoes = {
            "INICIAR JOGO",
            "CONFIGURACOES (mudar cores)",
            "COMO JOGAR DAMAS",
            "SAIR / TERMINAR"
        };
        int selecionado = 0;

        while (true) {
            mostrarMenu(selecionado, opcoes);

            System.out.println("  Digita " + VERDE_TEXTO + "W" + RESET + " e aperta ENTER pra subir");
            System.out.println("  Digita " + VERDE_TEXTO + "S" + RESET + " e aperta ENTER pra descer");
            System.out.println("  Aperta so " + VERDE_TEXTO + "ENTER" + RESET + " pra entrar no que ta com >");
            System.out.println("  Tu ta em: " + VERDE_TEXTO + opcoes[selecionado] + RESET);
            System.out.print("  W / S / ENTER: ");

            String digitado = sc.nextLine().trim().toLowerCase();

            if (digitado.equals("w")) {
                selecionado--;
                if (selecionado < 0) selecionado = opcoes.length - 1;
                continue;
            }
            if (digitado.equals("s")) {
                selecionado++;
                if (selecionado >= opcoes.length) selecionado = 0;
                continue;
            }
            if (digitado.equals("")) {
                if (selecionado == 0) {
                    jogar();
                } else if (selecionado == 1) {
                    mostrarConfiguracoes();
                } else if (selecionado == 2) {
                    mostrarComoJogar();
                } else if (selecionado == 3) {
                    limparTela();
                    System.out.println(ROXO_TEXTO + "========================================" + RESET);
                    System.out.println("  Valeu por jogar! Ate a proxima! :)");
                    System.out.println("========================================" + RESET);
                    break;
                }
            } else {
                System.out.println("Opa! So digita W, S ou so ENTER!");
                esperarEnter();
            }
        }
    }

    static void mostrarMenu(int selecionado, String[] opcoes) {
        limparTela();
        System.out.println(CYAN_TEXTO + "==================================================" + RESET);
        System.out.println(CYAN_TEXTO + "   ____    _    __  __    _    ____              " + RESET);
        System.out.println(CYAN_TEXTO + "  |  _ \\  / \\  |  \\/  |  / \\  / ___|   " + RESET);
        System.out.println(CYAN_TEXTO + "  | | | |/ _ \\ | |\\/| | / _ \\ \\___ \\   " + RESET);
        System.out.println(CYAN_TEXTO + "  | |_| / ___ \\| |  | |/ ___ \\ ___) |  " + RESET);
        System.out.println(CYAN_TEXTO + "  |____/_/   \\_\\_|  |_/_/   \\_\\____/   " + RESET);
        System.out.println(CYAN_TEXTO + "==================================================" + RESET);
        System.out.println(AMARELO_TEXTO + "           JOGO DE DAMAS - PROJETO" + RESET);
        System.out.println();

        for (int i = 0; i < opcoes.length; i++) {
            String cor = RESET;
            if (i == 0) cor = VERDE_TEXTO;
            if (i == 1) cor = AMARELO_TEXTO;
            if (i == 2) cor = CYAN_TEXTO;
            if (i == 3) cor = VERMELHO_TEXTO;

            if (i == selecionado) {
                System.out.println("  " + "\u001B[42m\u001B[30m" + " > " + opcoes[i] + " < " + RESET);
            } else {
                System.out.println("    " + cor + opcoes[i] + RESET);
            }
        }
        System.out.println();
        System.out.println(CYAN_TEXTO + "==================================================" + RESET);
        System.out.println("  Jogador 1 (x): " + COR_JOGADOR_1 + "xxxxxx" + RESET + "   Jogador 2 (o): " + COR_JOGADOR_2 + "oooooo" + RESET);
        System.out.println(CYAN_TEXTO + "==================================================" + RESET);
        System.out.println();
    }

    static void mostrarConfiguracoes() {
        limparTela();
        System.out.println(AMARELO_TEXTO + "==================================================" + RESET);
        System.out.println(AMARELO_TEXTO + "              CONFIGURACOES" + RESET);
        System.out.println(AMARELO_TEXTO + "==================================================" + RESET);
        System.out.println("  Aqui tu muda a cor das pecas!");
        System.out.println("  Jogador 1 = quem joga com 'x' (comeca em cima)");
        System.out.println("  Jogador 2 = quem joga com 'o' (comeca embaixo)");
        System.out.println();
        System.out.println("  Cores disponiveis:");
        System.out.println("  [ 1 ] Preto " + "\u001B[40m\u001B[37m" + "xxxxxx" + RESET);
        System.out.println("  [ 2 ] Branco " + "\u001B[47m\u001B[30m" + "xxxxxx" + RESET);
        System.out.println("  [ 3 ] Vermelho " + "\u001B[41m\u001B[37m" + "xxxxxx" + RESET);
        System.out.println("  [ 4 ] Azul " + "\u001B[44m\u001B[37m" + "xxxxxx" + RESET);
        System.out.println("  [ 5 ] Verde " + "\u001B[42m\u001B[30m" + "xxxxxx" + RESET);
        System.out.println("  [ 6 ] Amarelo " + "\u001B[43m\u001B[30m" + "xxxxxx" + RESET);
        System.out.println("  [ 7 ] Roxo " + "\u001B[45m\u001B[37m" + "xxxxxx" + RESET);
        System.out.println("  [ 8 ] Ciano " + "\u001B[46m\u001B[30m" + "xxxxxx" + RESET);
        System.out.println();

        System.out.print("Qual a cor do JOGADOR 1 (x)? (1 a 8): ");
        int cor1 = lerInt();
        COR_JOGADOR_1 = pegarCor(cor1);

        System.out.print("Qual a cor do JOGADOR 2 (o)? (1 a 8): ");
        int cor2 = lerInt();
        COR_JOGADOR_2 = pegarCor(cor2);

        if (cor1 == cor2) {
            System.out.println(VERMELHO_TEXTO + "Opa! Os dois ficaram com a mesma cor, vai confundir!" + RESET);
        } else {
            System.out.println(VERDE_TEXTO + "Prontinho! Cores trocadas!" + RESET);
        }
        System.out.println("  Jogador 1 (x): " + COR_JOGADOR_1 + "xxxxxx" + RESET + "   Jogador 2 (o): " + COR_JOGADOR_2 + "oooooo" + RESET);
        esperarEnter();
    }

    static String pegarCor(int numero) {
        if (numero == 1) return "\u001B[40m\u001B[37m";
        if (numero == 2) return "\u001B[47m\u001B[30m";
        if (numero == 3) return "\u001B[41m\u001B[37m";
        if (numero == 4) return "\u001B[44m\u001B[37m";
        if (numero == 5) return "\u001B[42m\u001B[30m";
        if (numero == 6) return "\u001B[43m\u001B[30m";
        if (numero == 7) return "\u001B[45m\u001B[37m";
        if (numero == 8) return "\u001B[46m\u001B[30m";
        return "\u001B[40m\u001B[37m";
    }

    static void mostrarComoJogar() {
        limparTela();
        System.out.println(CYAN_TEXTO + "==================================================" + RESET);
        System.out.println(CYAN_TEXTO + "              COMO JOGAR DAMAS" + RESET);
        System.out.println(CYAN_TEXTO + "==================================================" + RESET);
        System.out.println();
        System.out.println("  OBJETIVO:");
        System.out.println("  Comer todas as pecas do outro jogador ou travar ele");
        System.out.println("  sem ele conseguir andar. Quem ficar sem peca, perde!");
        System.out.println();
        System.out.println("  COMO ANDA:");
        System.out.println("  - As pecas so andam na diagonal, 1 casa por vez, pra frente.");
        System.out.println("  - Jogador 1 (x) desce: Y aumenta. Jogador 2 (o) sobe: Y diminui.");
        System.out.println("  - Tu digita X (coluna, pro lado) e Y (linha, pra baixo).");
        System.out.println("  - Depois digita Lado: 1 = esquerda, 2 = direita.");
        System.out.println();
        System.out.println("  COMO COME:");
        System.out.println("  - Se tiver um inimigo na diagonal da frente e a casa depois");
        System.out.println("    dele estiver vazia, tu E OBRIGADO A COMER!");
        System.out.println("  - A peca pula por cima do inimigo e o inimigo some.");
        System.out.println("  - Se der pra comer de novo com a MESMA peca, continua com ela!");
        System.out.println();
        System.out.println("  RAINHA (QQQQQQ):");
        System.out.println("  - Quando uma peca chega no final do outro lado, vira RAINHA.");
        System.out.println("  - Rainha anda quantas casas quiser, pra qualquer diagonal.");
        System.out.println("  - Tu escolhe o Lado: 1 = esq. cima, 2 = dir. cima, 3 = esq. baixo, 4 = dir. baixo.");
        System.out.println("  - Se tiver inimigo nesse lado, ela JA PULA SOZINHA e come,");
        System.out.println("    parando BEM NA FRENTE do bicho que ela comeu.");
        System.out.println("  - Se nao tiver inimigo, tu escolhe quantas casas ela anda.");
        System.out.println();
        System.out.println("  OUTRAS REGRAS:");
        System.out.println("  - Se tu digitar X = -1 no meio do jogo, volta pro menu.");
        System.out.println("  - O tabuleiro limpa sozinho a cada jogada.");
        System.out.println();
        System.out.println(CYAN_TEXTO + "==================================================" + RESET);
        esperarEnter();
    }

    static void esperarEnter() {
        System.out.println();
        System.out.print("Aperta ENTER pra voltar pro menu...");
        sc.nextLine();
    }

    static int escolherComSetinha(String titulo, String[] opcoes) {
        int selecionado = 0;
        while (true) {
            limparTela();
            System.out.println(CYAN_TEXTO + "==================================================" + RESET);
            System.out.println(CYAN_TEXTO + "  " + titulo + RESET);
            System.out.println(CYAN_TEXTO + "==================================================" + RESET);
            System.out.println();
            for (int i = 0; i < opcoes.length; i++) {
                if (i == selecionado) {
                    System.out.println("  " + "\u001B[42m\u001B[30m" + " > " + opcoes[i] + " < " + RESET);
                } else {
                    System.out.println("    " + opcoes[i]);
                }
            }
            System.out.println();
            System.out.println(CYAN_TEXTO + "==================================================" + RESET);
            System.out.println("  Digita W e ENTER pra subir, S e ENTER pra descer, so ENTER pra entrar");
            System.out.println("  Tu ta em: " + VERDE_TEXTO + opcoes[selecionado] + RESET);
            System.out.print("  W / S / ENTER: ");
            String digitado = sc.nextLine().trim().toLowerCase();
            if (digitado.equals("w")) {
                selecionado--;
                if (selecionado < 0) selecionado = opcoes.length - 1;
                continue;
            }
            if (digitado.equals("s")) {
                selecionado++;
                if (selecionado >= opcoes.length) selecionado = 0;
                continue;
            }
            if (digitado.equals("")) {
                return selecionado;
            }
        }
    }

    static void jogar() {
        int modo = escolherComSetinha("COMO TU QUER JOGAR?", new String[]{
            "TRADICIONAL - 8x8 ou 12x12",
            "CUSTOMIZADO - tu escolhe o tamanho",
            "VOLTAR PRO MENU"
        });

        if (modo == 2) return;

        if (modo == 0) {
            int trad = escolherComSetinha("TRADICIONAL - ESCOLHE O TAMANHO", new String[]{
                "8x8 - CLASSICO (o normal de damas)",
                "12x12 - GRANDE (tabuleiro maior, mais pecas)",
                "VOLTAR"
            });
            if (trad == 2) return;
            if (trad == 0) TAM = 8;
            if (trad == 1) TAM = 12;
        } else {
            limparTela();
            System.out.println(CYAN_TEXTO + "==================================================" + RESET);
            System.out.println(CYAN_TEXTO + "  CUSTOMIZADO - ESCOLHE O TAMANHO" + RESET);
            System.out.println(CYAN_TEXTO + "==================================================" + RESET);
            System.out.println("  Tem que ser numero PAR, tipo 6, 8, 10, 12, 14...");
            System.out.println("  Se for impar, nao da pra montar as pecas certinho!");
            System.out.println("  Minimo 4, maximo 20 pra nao ficar gigante.");
            System.out.println();
            while (true) {
                System.out.print("Qual o tamanho do tabuleiro? (ex: 10): ");
                TAM = lerInt();
                if (TAM < 4) {
                    System.out.println("Opa! Muito pequeno! Tem que ser pelo menos 4!");
                    continue;
                }
                if (TAM > 20) {
                    System.out.println("Opa! Muito grande! Maximo 20, senao nao cabe na tela!");
                    continue;
                }
                if (TAM % 2 == 1) {
                    System.out.println("Opa! Tem que ser PAR! Tenta " + (TAM + 1) + " ou " + (TAM - 1) + "!");
                    continue;
                }
                break;
            }
        }

        tab = new Peca[TAM][TAM];
        for (int i = 0; i < TAM; i++)
            for (int j = 0; j < TAM; j++)
                tab[i][j] = null;

        int fileiras = 3;
        if (TAM == 12) fileiras = 4;
        if (TAM >= 16) fileiras = 4;

        for (int i = 0; i < fileiras; i++)
            for (int j = 0; j < TAM; j++)
                if ((i + j) % 2 == 1) tab[i][j] = new Peca('x');

        for (int i = TAM - fileiras; i < TAM; i++)
            for (int j = 0; j < TAM; j++)
                if ((i + j) % 2 == 1) tab[i][j] = new Peca('o');

        vez = 'o';
        temQueContinuarComendo = false;
        continuaLinha = -1;
        continuaColuna = -1;

        while (true) {
            limparTela();
            mostrarTabuleiro();

            if (vez == 'o') System.out.println("\n" + COR_JOGADOR_2 + "--- VEZ DOS BRANCOS (o) ---" + RESET);
            else System.out.println("\n" + COR_JOGADOR_1 + "--- VEZ DOS PRETOS (x) ---" + RESET);
            System.out.println("Tabuleiro " + TAM + "x" + TAM + " - (Digita X = -1 pra voltar pro menu)");

            if (temQueContinuarComendo) {
                System.out.println(VERMELHO_TEXTO + "!!! VOCE TEM QUE CONTINUAR COMENDO com a peca em (X=" + continuaColuna + ", Y=" + continuaLinha + ") !!!" + RESET);
            } else if (temCapturaObrigatoria(vez)) {
                System.out.println(VERMELHO_TEXTO + "!!! ATENCAO: VOCE E OBRIGADO A COMER! !!!" + RESET);
                mostrarOndePodeComer(vez);
            }

            boolean moveu = false;
            boolean querVoltarMenu = false;
            while (!moveu) {
                System.out.print("X (coluna, pro lado): ");
                int x = lerInt();
                if (x == -1) { querVoltarMenu = true; break; }
                System.out.print("Y (linha, pra baixo): ");
                int y = lerInt();

                if (y < 0 || y >= TAM || x < 0 || x >= TAM || tab[y][x] == null) {
                    System.out.println("IMPOSSIVEL: nao tem peca ai!");
                    continue;
                }
                Peca pecaEscolhida = tab[y][x];
                if (!pecaEscolhida.ehDoJogador(vez)) {
                    if (vez == 'o') {
                        System.out.println("IMPOSSIVEL: agora e a vez dos BRANCOS (o)");
                    } else {
                        System.out.println("IMPOSSIVEL: agora e a vez dos PRETOS (x)");
                    }
                    continue;
                }
                if (pecaEscolhida.ehRainha) {
                    System.out.println("Essa e uma RAINHA! (QQQQQQ)");
                    System.out.println("Lado: 1 = esquerda pra cima, 2 = direita pra cima, 3 = esquerda pra baixo, 4 = direita pra baixo");
                    System.out.print("Lado (1 a 4): ");
                    int lado = lerInt();
                    moveu = moverRainha(y, x, lado);
                } else {
                    System.out.print("Lado (1 = esquerda, 2 = direita): ");
                    int lado = lerInt();
                    moveu = moverPeca(y, x, lado);
                }
                if (!moveu) System.out.println(">>> Tenta de novo! <<<");
            }

            if (querVoltarMenu) {
                System.out.println("Voltando pro menu...");
                esperarEnter();
                return;
            }

            // >>> NOVO: VERIFICA SE ALGUEM GANHOU <<<
            // Conta as pecas depois da jogada
            if (contarPecas('o') == 0) {
                limparTela();
                mostrarTabuleiro();
                System.out.println();
                System.out.println(VERDE_TEXTO + "========================================" + RESET);
                System.out.println(VERDE_TEXTO + "  FIM DE JOGO! PRETOS (x) GANHARAM!" + RESET);
                System.out.println(VERDE_TEXTO + "  As brancas ficaram sem pecas!" + RESET);
                System.out.println(VERDE_TEXTO + "========================================" + RESET);
                esperarEnter();
                return;
            }
            if (contarPecas('x') == 0) {
                limparTela();
                mostrarTabuleiro();
                System.out.println();
                System.out.println(VERDE_TEXTO + "========================================" + RESET);
                System.out.println(VERDE_TEXTO + "  FIM DE JOGO! BRANCOS (o) GANHARAM!" + RESET);
                System.out.println(VERDE_TEXTO + "  As pretas ficaram sem pecas!" + RESET);
                System.out.println(VERDE_TEXTO + "========================================" + RESET);
                esperarEnter();
                return;
            }
            // >>> FIM DO NOVO <<<

            if (!temQueContinuarComendo) {
                if (vez == 'o') vez = 'x';
                else vez = 'o';
            }
        }
    }

    static int lerInt() {
        while (!sc.hasNextInt()) {
            System.out.println("Opa! Digita so numero, sem letra!");
            sc.next();
            System.out.print("Tenta de novo: ");
        }
        int valor = sc.nextInt();
        sc.nextLine();
        return valor;
    }

    static boolean essaPecaPodeComer(int linha, int coluna) {
        Peca peca = tab[linha][coluna];
        if (peca == null) return false;
        char jogadorDaPeca;
        if (peca.ehBranca()) {
            jogadorDaPeca = 'o';
        } else {
            jogadorDaPeca = 'x';
        }
        if (peca.ehRainha) {
            for (int dLinha : new int[]{-1, 1}) {
                for (int dColuna : new int[]{-1, 1}) {
                    int l = linha + dLinha;
                    int c = coluna + dColuna;
                    while (l >= 0 && l < TAM && c >= 0 && c < TAM && tab[l][c] == null) {
                        l += dLinha; c += dColuna;
                    }
                    if (l < 0 || l >= TAM || c < 0 || c >= TAM) continue;
                    Peca meio = tab[l][c];
                    if (meio != null && !meio.ehDoJogador(jogadorDaPeca)) {
                        int depoisLinha = l + dLinha;
                        int depoisColuna = c + dColuna;
                        if (depoisLinha >= 0 && depoisLinha < TAM && depoisColuna >= 0 && depoisColuna < TAM && tab[depoisLinha][depoisColuna] == null) return true;
                    }
                }
            }
            return false;
        }
        int dirLinha;
        if (peca.ehPreta()) {
            dirLinha = 1;
        } else {
            dirLinha = -1;
        }
        for (int dirColuna : new int[]{-1, 1}) {
            int meioLinha = linha + dirLinha;
            int meioColuna = coluna + dirColuna;
            int puloLinha = meioLinha + dirLinha;
            int puloColuna = meioColuna + dirColuna;
            if (meioLinha < 0 || meioLinha >= TAM || meioColuna < 0 || meioColuna >= TAM) continue;
            if (puloLinha < 0 || puloLinha >= TAM || puloColuna < 0 || puloColuna >= TAM) continue;
            Peca meio = tab[meioLinha][meioColuna];
            if (meio != null && !meio.ehDoJogador(jogadorDaPeca) && tab[puloLinha][puloColuna] == null) return true;
        }
        return false;
    }

    static boolean temCapturaObrigatoria(char jogador) {
        for (int i = 0; i < TAM; i++)
            for (int j = 0; j < TAM; j++)
                if (tab[i][j] != null && tab[i][j].ehDoJogador(jogador) && essaPecaPodeComer(i, j)) return true;
        return false;
    }

    // >>> NOVO: CONTA QUANTAS PECAS UM JOGADOR AINDA TEM <<<
    static int contarPecas(char jogador) {
        int total = 0;
        for (int i = 0; i < TAM; i++) {
            for (int j = 0; j < TAM; j++) {
                if (tab[i][j] != null && tab[i][j].ehDoJogador(jogador)) {
                    total++;
                }
            }
        }
        return total;
    }

    static void mostrarOndePodeComer(char jogador) {
        System.out.print("Voce tem que comer com: ");
        for (int i = 0; i < TAM; i++)
            for (int j = 0; j < TAM; j++)
                if (tab[i][j] != null && tab[i][j].ehDoJogador(jogador) && essaPecaPodeComer(i, j))
                    System.out.print("(X=" + j + ", Y=" + i + ")  ");
        System.out.println();
    }

    static boolean moverPeca(int linha, int coluna, int lado) {
        Peca peca = tab[linha][coluna];
        if (peca == null) return false;
        if (!peca.ehDoJogador(vez)) return false;
        if (temQueContinuarComendo && (linha != continuaLinha || coluna != continuaColuna)) return false;
        if (lado != 1 && lado != 2) return false;
        int dirLinha;
        if (peca.ehPreta()) {
            dirLinha = 1;
        } else {
            dirLinha = -1;
        }
        int dirColuna;
        if (lado == 1) {
            dirColuna = -1;
        } else {
            dirColuna = 1;
        }
        int meioLinha = linha + dirLinha;
        int meioColuna = coluna + dirColuna;
        if (meioLinha < 0 || meioLinha >= TAM || meioColuna < 0 || meioColuna >= TAM) return false;
        Peca meio = tab[meioLinha][meioColuna];
        if (meio == null) {
            if (temQueContinuarComendo) return false;
            if (temCapturaObrigatoria(vez)) {
                System.out.println("IMPOSSIVEL: VOCE E OBRIGADO A COMER!");
                mostrarOndePodeComer(vez);
                return false;
            }
            apagarEColocar(linha, coluna, meioLinha, meioColuna, peca);
            temQueContinuarComendo = false;
            return true;
        }
        if (meio.ehDoJogador(vez)) {
            System.out.println("IMPOSSIVEL: tem uma peca tua no caminho!");
            return false;
        }
        int puloLinha = meioLinha + dirLinha;
        int puloColuna = meioColuna + dirColuna;
        if (puloLinha < 0 || puloLinha >= TAM || puloColuna < 0 || puloColuna >= TAM) return false;
        if (tab[puloLinha][puloColuna] != null) return false;
        System.out.println("COMEU! A peca em (X=" + meioColuna + ", Y=" + meioLinha + ") morreu!");
        tab[meioLinha][meioColuna] = null;
        apagarEColocar(linha, coluna, puloLinha, puloColuna, peca);
        if (essaPecaPodeComer(puloLinha, puloColuna)) {
            temQueContinuarComendo = true;
            continuaLinha = puloLinha;
            continuaColuna = puloColuna;
        } else temQueContinuarComendo = false;
        return true;
    }

    static boolean moverRainha(int linha, int coluna, int lado) {
        Peca rainha = tab[linha][coluna];
        if (rainha == null || !rainha.ehRainha) return false;
        if (!rainha.ehDoJogador(vez)) return false;
        if (temQueContinuarComendo && (linha != continuaLinha || coluna != continuaColuna)) return false;
        if (lado < 1 || lado > 4) return false;
        int passoLinha = 0, passoColuna = 0;
        if (lado == 1) { passoLinha = -1; passoColuna = -1; }
        if (lado == 2) { passoLinha = -1; passoColuna = 1; }
        if (lado == 3) { passoLinha = 1; passoColuna = -1; }
        if (lado == 4) { passoLinha = 1; passoColuna = 1; }
        int l = linha + passoLinha;
        int c = coluna + passoColuna;
        int primeiraLinha = -1, primeiraColuna = -1;
        Peca primeira = null;
        while (l >= 0 && l < TAM && c >= 0 && c < TAM) {
            if (tab[l][c] != null) {
                primeiraLinha = l;
                primeiraColuna = c;
                primeira = tab[l][c];
                break;
            }
            l += passoLinha;
            c += passoColuna;
        }
        if (primeira != null && !primeira.ehDoJogador(vez)) {
            int pousoLinha = primeiraLinha + passoLinha;
            int pousoColuna = primeiraColuna + passoColuna;
            if (pousoLinha < 0 || pousoLinha >= TAM || pousoColuna < 0 || pousoColuna >= TAM) return false;
            if (tab[pousoLinha][pousoColuna] != null) return false;
            System.out.println("RAINHA VIU INIMIGO em (X=" + primeiraColuna + ", Y=" + primeiraLinha + ") e JA PULOU SOZINHA!");
            tab[primeiraLinha][primeiraColuna] = null;
            apagarEColocar(linha, coluna, pousoLinha, pousoColuna, rainha);
            if (essaPecaPodeComer(pousoLinha, pousoColuna)) {
                temQueContinuarComendo = true;
                continuaLinha = pousoLinha;
                continuaColuna = pousoColuna;
            } else temQueContinuarComendo = false;
            return true;
        }
        if (primeira != null && primeira.ehDoJogador(vez)) return false;
        if (temQueContinuarComendo) return false;
        if (temCapturaObrigatoria(vez)) {
            System.out.println("IMPOSSIVEL: VOCE E OBRIGADO A COMER!");
            mostrarOndePodeComer(vez);
            return false;
        }
        System.out.print("Nao tem inimigo nesse lado. Quantas casas ela anda?: ");
        int casas = lerInt();
        if (casas <= 0) return false;
        int destLinha = linha + passoLinha * casas;
        int destColuna = coluna + passoColuna * casas;
        if (destLinha < 0 || destLinha >= TAM || destColuna < 0 || destColuna >= TAM) return false;
        for (int passo = 1; passo <= casas; passo++) {
            int ll = linha + passoLinha * passo;
            int cc = coluna + passoColuna * passo;
            if (tab[ll][cc] != null) return false;
        }
        apagarEColocar(linha, coluna, destLinha, destColuna, rainha);
        temQueContinuarComendo = false;
        return true;
    }

    static void apagarEColocar(int linhaAntiga, int colunaAntiga, int linhaNova, int colunaNova, Peca peca) {
        tab[linhaAntiga][colunaAntiga] = null;
        tab[linhaNova][colunaNova] = peca;
        if (!peca.ehRainha) {
            if (peca.ehPreta() && linhaNova == TAM - 1) {
                peca.virarRainha();
                System.out.println("!!! VIROU RAINHA! !!!");
            }
            if (peca.ehBranca() && linhaNova == 0) {
                peca.virarRainha();
                System.out.println("!!! VIROU RAINHA! !!!");
            }
        }
        System.out.println("Moveu de (X=" + colunaAntiga + ", Y=" + linhaAntiga + ") pra (X=" + colunaNova + ", Y=" + linhaNova + ")");
    }

    static void mostrarTabuleiro() {
        System.out.println();
        System.out.print("    ");
        for (int j = 0; j < TAM; j++) {
            if (j < 10) {
                System.out.print(AMARELO_TEXTO + "   " + j + "   " + RESET);
            } else {
                System.out.print(AMARELO_TEXTO + "   " + j + "  " + RESET);
            }
        }
        System.out.println(CYAN_TEXTO + " X" + RESET);
        for (int i = 0; i < TAM; i++) {
            for (int sub = 0; sub < 3; sub++) {
                if (sub == 1) {
                    if (i < 10) {
                        System.out.print(AMARELO_TEXTO + " " + i + " |" + RESET);
                    } else {
                        System.out.print(AMARELO_TEXTO + i + " |" + RESET);
                    }
                } else {
                    System.out.print("   |");
                }
                for (int j = 0; j < TAM; j++) {
                    Peca p = tab[i][j];
                    boolean escura = (i + j) % 2 == 1;
                    if (p != null && p.ehPreta() && p.ehRainha) System.out.print(COR_JOGADOR_1 + "QQQQQQ" + RESET + " ");
                    else if (p != null && p.ehBranca() && p.ehRainha) System.out.print(COR_JOGADOR_2 + "QQQQQQ" + RESET + " ");
                    else if (p != null && p.ehPreta()) System.out.print(COR_JOGADOR_1 + "xxxxxx" + RESET + " ");
                    else if (p != null && p.ehBranca()) System.out.print(COR_JOGADOR_2 + "oooooo" + RESET + " ");
                    else if (escura) System.out.print(CASA_ESCURA + "......" + RESET + " ");
                    else System.out.print("       ");
                }
                System.out.println();
            }
        }
        System.out.println(CYAN_TEXTO + " Y" + RESET);
        System.out.flush();
    }
}