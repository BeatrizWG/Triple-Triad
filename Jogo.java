package projeto;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.Scanner;

public class Jogo {
	private Jogador jogador1;
	private Jogador jogador2;
	private Tabuleiro tabuleiro;
	private Jogador jogadorAtual;
	private boolean fimDeJogo;

	public int[] cartasUsadas = new int[10];

	public static Random random = new Random();
	public static final String VERMELHO = "\u001B[31m";
	public static final String AZUL = "\u001B[34m";
	public static Scanner entrada = new Scanner(System.in);
	public static Scanner validacao = new Scanner(System.in);

	public Jogo(Jogador jogador1, Jogador jogador2) {
		this.jogador1 = jogador1;
		this.jogador2 = jogador2;
		this.tabuleiro = new Tabuleiro();
		this.fimDeJogo = false;
		this.jogadorAtual = random.nextBoolean() ? jogador1 : jogador2;
	}

	private void alternarTurno() {
		this.jogadorAtual = (jogadorAtual == jogador1) ? jogador2 : jogador1;
	}

	public void checarFimDeJogo() {
		fimDeJogo = tabuleiro.getPosicoesDisponiveis().isEmpty();
	}

	public void verificarVencedor() {

		int pontuacaoJogador1 = tabuleiro.contabilizarPontos(jogador1);
		int pontuacaoJogador2 = tabuleiro.contabilizarPontos(jogador2);

		System.out.println("Fim de jogo!");
		System.out.println("Pontuação final:");

		System.out.println(jogador1.getNome() + ": " + pontuacaoJogador1 + " pontos");
		System.out.println(jogador2.getNome() + ": " + pontuacaoJogador2 + " pontos");

		if (pontuacaoJogador1 > pontuacaoJogador2) {
			System.out.println("O vencedor é " + jogador1.getNome() + "!");

		} else if (pontuacaoJogador2 > pontuacaoJogador1) {

			System.out.println("O vencedor é " + jogador2.getNome() + "!");

		} else {
			System.out.println("O jogo terminou em empate!");
		}

	}

	public void iniciarJogo() {
		while (!fimDeJogo) {

			System.out.println("Turno do jogador: " + jogadorAtual.getNome());

			tabuleiro.exibirTabuleiro();

			System.out.println("Pressione Enter se o jogador " + jogadorAtual.getNome() + " estiver posicionado");
			validacao.nextLine();

			jogadorAtual.exibirCartas();

			System.out.println("Informe a carta que quer jogar (índice): ");
			int numCarta = entrada.nextInt();

			System.out.println("Informe a posição onde quer jogar (1 a 9): ");
			int posicao = entrada.nextInt();

			while (!jogadorAtual.jogarCarta(posicao, numCarta - 1, tabuleiro)) {

				System.out.println("Informe a carta que quer jogar (índice): ");
				numCarta = entrada.nextInt();

				System.out.println("Informe a posição onde quer jogar (1 a 9): ");
				posicao = entrada.nextInt();
			}

			tabuleiro.compararCarta(posicao);
			tabuleiro.aplicarBonusTipo(posicao);

			alternarTurno();
			checarFimDeJogo();

		}

		tabuleiro.exibirTabuleiro();
		verificarVencedor();
		entrada.close();
		validacao.close();
	}

	public Carta[] criarLista() {

		Carta[] listaCartas = new Carta[110];
		for (int i = 0; i < 110; i++) {
			listaCartas[i] = new Carta(nomeCartas[i], random.nextInt(10) + 1, random.nextInt(10) + 1,
					random.nextInt(10) + 1, random.nextInt(10) + 1, tiposCartas[random.nextInt(tiposCartas.length)]);
		}
		return listaCartas;
	}

	public List<Carta> sortearCarta(Carta[] lista, Jogador jogador, int inicio, String cor) {

		List<Carta> listaCartas = new ArrayList<Carta>();
		for (int i = inicio; i < inicio + 5; i++) {
			int posicao = random.nextInt(nomeCartas.length);
			if (verificarPosicao(posicao)) {
				i--;
			} else {
				cartasUsadas[i] = posicao;
				Carta carta = lista[posicao];
				carta.setCor(cor);
				carta.setProprietario(jogador);
				listaCartas.add(carta);
			}
		}
		return listaCartas;
	}

	public boolean verificarPosicao(int valor) {
		for (int i = 0; i < 10; i++) {
			if (cartasUsadas[i] == valor) {
				return true;
			}
		}
		return false;
	}

	public void trocarCartas(Jogador jogador1, Jogador jogador2) {

		int posicao = random.nextInt(5);

		Carta carta1 = jogador1.getCartas().get(posicao);
		Carta carta2 = jogador2.getCartas().get(posicao);

		carta1.setCor(Jogo.AZUL);
		carta2.setCor(Jogo.VERMELHO);

		carta1.setProprietario(jogador2);
		carta2.setProprietario(jogador1);

		jogador1.getCartas().set(posicao, carta2);
		jogador2.getCartas().set(posicao, carta1);
	}

	public static String[] nomeCartas = { "Geezard", "Funguar", "Bite Bug", "Red Bat", "Blobra", "Gayla", "Gesper",
			"Fastitocalon-F", "Blood Soul", "Caterchipillar", "Cockatrice", "Grat", "Buel", "Mesmerize", "Glacial Eye",
			"Belhelmel", "Thrustaevis", "Anacondaur", "Creeps", "Grendel", "Jelleye", "Grand Mantis", "Forbidden",
			"Armadodo", "Tri-Face", "Fastitocalon", "Snow Lion", "Ochu", "SAM08G", "Death Claw", "Cactuar", "Tonberry",
			"Abyss Worm", "Turtapod", "Vysage", "T-Rexaur", "Bomb", "Blitz", "Wendigo", "Torama", "Imp", "Blue Dragon",
			"Adamantoise", "Hexadragon", "Iron Giant", "Behemoth", "Chimera", "PuPu", "Elastoid", "GIM47N", "Malboro",
			"Ruby Dragon", "Elnoyle", "Tonberry King", "Wedge, Biggs", "Fujin Raijin", "Elvoret", "X-ATM092",
			"Granaldo", "Gerogero", "Iguion", "Abadon", "Trauma", "Oilboyle", "Shumi", "Krysta", "Propagator",
			"Jumbo Cactuar", "Tri-Point", "Gargantua", "Mobile Type 8", "Sphinxara", "Tiamat", "BGH251F2", "Red Giant",
			"Catoblepas", "Ultima Weapon", "Chubby Chocobo", "Angelo", "Gilgamesh", "MiniMog", "Chicobo", "Quezacotl",
			"Shiva", "Ifrit", "Siren", "Sacred", "Minotaur", "Carbuncle", "Diablos", "Leviathan", "Odin", "Pandemona",
			"Cerberus", "Alexander", "Phoenix", "Bahamut", "Doomtrain", "Eden", "Ward", "Kiros", "Laguna", "Selphie",
			"Quistis", "Irvine", "Zell", "Rinoa", "Edea", "Seifer", "Squall" };

	public static String[] tiposCartas = { "Earth", "Fire", "Holy", "Ice", "Neutral", "Poison", "Thunder", "Water",
			"Wind" };

}
