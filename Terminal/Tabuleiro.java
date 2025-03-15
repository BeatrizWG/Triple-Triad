package projeto;

import java.util.ArrayList;
import java.util.List;

public class Tabuleiro {
	private Carta[][] celulas;
	private List<Integer> posicoesDisponiveis;

	public static final String VERMELHO = "\u001B[31m";
	public static final String AZUL = "\u001B[34m";

	public Tabuleiro() {
		this.celulas = new Carta[3][3];
		this.posicoesDisponiveis = new ArrayList<>();
		for (int i = 1; i <= 9; i++) {
			this.posicoesDisponiveis.add(i);
		}
	}

	public Carta getCelulas(int linha, int coluna) {
		return celulas[linha][coluna];
	}

	public void setCelulas(Carta carta, int linha, int coluna) {
		this.celulas[linha][coluna] = carta;
	}

	public List<Integer> getPosicoesDisponiveis() {
		return posicoesDisponiveis;
	}

	public void posicionarCarta(Carta carta, int posicao) {
		int linha = (posicao - 1) / 3;
		int coluna = (posicao - 1) % 3;

		setCelulas(carta, linha, coluna);

		posicoesDisponiveis.remove(Integer.valueOf(posicao));

		System.out.println("Carta posicionada na célula " + posicao);
		System.out.println();
	}

	public void aplicarBonusTipo(int posicao) {
		Carta cartaJogada = getCartaPosicao(posicao);
		String tipo = cartaJogada.getTipo();
		for (int i = 0; i < 3; i++) {
			for (int j = 0; j < 3; j++) {
				Carta carta = getCelulas(i, j);
				if (carta != null && carta.getTipo().equals(tipo) && carta != cartaJogada) {
					carta.incrementarValores();
				}
			}
		}
	}

	public void exibirTabuleiro() {
		for (int i = 0; i < 3; i++) {
			for (int j = 0; j < 3; j++) {
				Carta carta = getCelulas(i, j);
				if (carta != null) {
					System.out.printf("    %s%s%s    ", carta.getCor(), exibirValorCarta(carta.getValorSuperior()),
							"\u001B[0m");
				} else {
					System.out.print("    " + " " + "    ");
				}
				if (j < 2) {
					System.out.print("|");
				}
			}
			System.out.println();

			for (int j = 0; j < 3; j++) {
				Carta carta = getCelulas(i, j);
				if (carta != null) {
					System.out.printf("  %s%s   %s%s  ", carta.getCor(), exibirValorCarta(carta.getValorEsquerda()),
							exibirValorCarta(carta.getValorDireita()), "\u001B[0m");
				} else {
					System.out.print("         ");
				}
				if (j < 2) {
					System.out.print("|");
				}
			}
			System.out.println();

			for (int j = 0; j < 3; j++) {
				Carta carta = getCelulas(i, j);
				if (carta != null) {
					System.out.printf("    %s%s%s    ", carta.getCor(), exibirValorCarta(carta.getValorInferior()),
							"\u001B[0m");
				} else {
					System.out.print("    " + " " + "    ");
				}
				if (j < 2) {
					System.out.print("|");
				}
			}
			System.out.println();

			if (i < 2) {
				System.out.println("-----------------------------");
			}
		}
	}

	public String exibirValorCarta(int valorCarta) {
		return (valorCarta < 10) ? Integer.toString(valorCarta) : "A";
	}

	public Carta getCartaPosicao(int posicao) {
		if (posicao < 1 || posicao > 9)
			return null;
		return getCelulas((posicao - 1) / 3, (posicao - 1) % 3);
	}

	public void compararCarta(int posicao) {
		Carta cartaJogada = getCartaPosicao(posicao);
		if (posicao > 3) {
			Carta cartaSuperior = getCartaPosicao(posicao - 3);
			if (cartaSuperior != null && cartaJogada.getValorSuperior() > cartaSuperior.getValorInferior()) {
				capturarCarta(cartaJogada.getProprietario(), cartaSuperior);
			}
		}
		if (posicao <= 6) {
			Carta cartaInferior = getCartaPosicao(posicao + 3);
			if (cartaInferior != null && cartaJogada.getValorInferior() > cartaInferior.getValorSuperior()) {
				capturarCarta(cartaJogada.getProprietario(), cartaInferior);
			}
		}

		if (posicao % 3 != 1) {
			Carta cartaEsquerda = getCartaPosicao(posicao - 1);
			if (cartaEsquerda != null && cartaJogada.getValorEsquerda() > cartaEsquerda.getValorDireita()) {
				capturarCarta(cartaJogada.getProprietario(), cartaEsquerda);
			}
		}
		if (posicao % 3 != 0) {
			Carta cartaDireita = getCartaPosicao(posicao + 1);
			if (cartaDireita != null && cartaJogada.getValorDireita() > cartaDireita.getValorEsquerda()) {
				capturarCarta(cartaJogada.getProprietario(), cartaDireita);
			}
		}
	}

	public void capturarCarta(Jogador jogadorAtual, Carta carta) {

		Jogador proprietarioAnterior = carta.getProprietario();

		if (!(proprietarioAnterior.getNome()).equals(jogadorAtual.getNome())) {

			jogadorAtual.modificarPontuacao(1);
			proprietarioAnterior.modificarPontuacao(-1);

			carta.setProprietario(jogadorAtual);

			if (carta.getCor().equals(VERMELHO))
				carta.setCor(AZUL);
			else
				carta.setCor(VERMELHO);

			System.out.println("Carta " + carta.getNome() + " foi capturada por " + jogadorAtual.getNome() + "!");
		}

	}

	public void exibirCarta(Carta carta) {
		System.out.println("NOME: " + carta.getNome() + " - TIPO: " + carta.getTipo() + " - PROPRIETÁRIO: "
				+ carta.getProprietario());
		String valorS = exibirValorCarta(carta.getValorSuperior());
		String valorE = exibirValorCarta(carta.getValorEsquerda());
		String valorD = exibirValorCarta(carta.getValorDireita());
		String valorI = exibirValorCarta(carta.getValorInferior());
		System.out.printf(carta.getCor() + "  %s\n%s   %s\n  %s %s\n", valorS, valorE, valorD, valorI, "\u001B[0m");
	}

	public int contabilizarPontos(Jogador jogador) {
		int quantidade = 0;
		for (int i = 0; i < 3; i++) {
			for (int j = 0; j < 3; j++) {
				Carta carta = getCelulas(i, j);
				if (carta.getProprietario() == jogador) {
					quantidade++;
				}
			}
		}
		return quantidade;
	}
}
