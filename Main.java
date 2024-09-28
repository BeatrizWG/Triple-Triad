package projeto;

import java.util.Scanner;

public class Main {

	public static void main(String[] args) {
		Scanner sc = new Scanner(System.in);

		System.out.println("Digite o nome do primeiro jogador: ");
		String nome = sc.nextLine();

		Jogador jogador1 = new Jogador(nome, "\u001B[31m");

		System.out.println("Digite o nome do segundo jogador: ");
		nome = sc.nextLine();

		Jogador jogador2 = new Jogador(nome, "\u001B[34m");

		Jogo jogo = new Jogo(jogador1, jogador2);

		Carta[] listaCartas = jogo.criarLista();

		jogador1.setCartas(jogo.sortearCarta(listaCartas, jogador1, 0, Jogo.VERMELHO));
		jogador2.setCartas(jogo.sortearCarta(listaCartas, jogador2, 5, Jogo.AZUL));

		jogo.trocarCartas(jogador1, jogador2);

		jogo.iniciarJogo();

		sc.close();
	}

}
