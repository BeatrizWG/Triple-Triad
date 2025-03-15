# Triple Triad - Final Fantasy

## Sobre o Projeto
Este projeto é uma implementação do jogo **Triple Triad**, originalmente presente na franquia *Final Fantasy*. Ele foi desenvolvido em **Java**, permitindo que os jogadores joguem tanto no **terminal** quanto por meio de uma **interface gráfica**.

## Como Jogar
1. **Troca de Cartas:** Após a distribuição inicial, os jogadores devem trocar suas cartas antes de iniciar o jogo.
2. **Início da Rodada:** O jogador da vez deve:
   - Selecionar uma carta da sua mão e posicioná-la em um espaço vazio no tabuleiro.
   - Clicar em **"Esconder Cartas"** para que o próximo jogador não possa vê-las.
3. **Turnos Seguintes:**
   - O próximo jogador inicia seu turno clicando em **"Iniciar Turno"** e confirma clicando em **"OK"** para que suas cartas sejam reveladas.
   - Ele então escolhe sua jogada e, ao finalizar, clica novamente em **"Esconder Cartas"** para ocultar as cartas até a próxima rodada.
4. **Finalização do Jogo:** O jogo termina quando todas as cartas forem posicionadas no tabuleiro e a pontuação final for calculada.

## Como Executar
### Modo Terminal
1. Certifique-se de ter o **Java** instalado em sua máquina.
2. Execute o arquivo **Main.java**.

### Modo Interface Gráfica
1. Abra o projeto no **Eclipse**.
2. Importe o projeto como **Maven Project**.
3. Localize a classe application e execute o método `main`.

## Requisitos
- **Java 8+**
- **Eclipse IDE** (para interface gráfica)
- **Maven** (para gerenciamento de dependências)
