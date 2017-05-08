package br.edu.iftm.controllers;

import java.util.ArrayList;
import org.newdawn.slick.geom.Point;

import br.edu.iftm.models.entities.Character;
import br.edu.iftm.models.entities.HistoryBar;
import br.edu.iftm.models.entities.LifeBar;
import br.edu.iftm.models.stacks.StackLifeBar;
import br.edu.iftm.models.stacks.flashback.ActionElement;
import br.edu.iftm.models.stacks.flashback.StackFlashback;

/**
 * Classe responsavel por usar a habilidade de voltar no tempo FlashBack.
 *
 */
public class FlashBackSkill {
	// Lista com o heroi em posicoes antigas, herois que sao desenhados durante o trajeto de voltar no tempo, dando o efeito de rastro de movimento
	private ArrayList<Character> oldHeroes;
	// Vetor que contera as duas pilhas
	private StackFlashback[] stacks;
	// Referencia das entidades, para poder gerencia-las
	private Character hero;
	private LifeBar lifebar;
	private HistoryBar histBar;
	// Contador de acoes que sao desempilhadas e a quantidade maxima de acoes que podem ser desempilhadas
	private int contActions, maxActionsReturn;
	// Variaveis que servem como controle para a utilizacao das duas pilhas
	private int stackActive, otherStack;

	/**
	 * Cria a habilidade FlashBackSkill com um tipo de pilha, e passa por referencia as entidades do personagem, barra de vida e barra do historico de icones.
	 * @param stackType Tipo de pilha (estatica ou dinamica)
	 * @param hero Referencia da entidade do heroi
	 * @param lifebar Referencia da entidade da barra de vida
	 * @param histBar Referencia da barra de historico de icones
	 */
	public FlashBackSkill(int stackType, Character hero, LifeBar lifebar, HistoryBar histBar)
	{
		oldHeroes = new ArrayList<Character>();
		stacks = new StackFlashback[2];
		// Adiciono as duas pilhas no vetor
		stacks[0] = new StackFlashback(stackType, 50);
		stacks[1] = new StackFlashback(stackType, 50);
		this.hero = hero;
		this.lifebar = lifebar;
		this.histBar = histBar;
		contActions = 0;
		// Defino a pilha ativa para id 0 e a outra pilha para id 1.
		stackActive = 0;
		otherStack = 1;
		// Quantidade maxima que o personagem pode voltar no tempo
		maxActionsReturn = 50;
	}
	
	/**
	 * Inverte o id da pilha ativa e consequentemente, o id da outra pilha.
	 */
	private void swapStackActive()
	{
		if(stackActive == 0)
		{
			stackActive = 1;
			otherStack = 0;
		}
		else
		{
			stackActive = 0;
			otherStack = 1;
		}
	}
	
	/**
	 * Metodo que verifica se pode limpar uma das pilhas e consquentemente liberar espaco na memoria. Tambem e responsavel em mudar a pilha
	 * ativa caso ela esteja cheia.
	 */
	private void clearOrChangeStacks()
	{
		System.out.println("Stack #" + (stackActive+1) + " " + stacks[stackActive].getSize() + "/" + stacks[stackActive].getSizeMax() + "; maxActionsReturn = " + maxActionsReturn);
		
		if(stacks[stackActive].getSize() >= maxActionsReturn && !stacks[otherStack].isEmpty())
			stacks[otherStack].clear();
		
		if(stacks[stackActive].isFull())
			swapStackActive();
	}
	
	/**
	 * Empilha uma acao do heroi com o tipo 'perca de vida'. Se o jogador perdeu vida, e gerado uma acao contendo a vida atual do personagem(HP) e essa acao e empilhada.
	 * @param hp Quantidade de vida atual do personagem
	 */
	public void addBackup(int hp)
	{
		stacks[stackActive].addCharBackup(hp);
		histBar.addCharLostHp();
		clearOrChangeStacks();	
	}
	
	/**
	 * Empilha uma acao do heroi com o tipo 'movimento'. Se o jogador mudou de posicao, e gerado uma acao contendo o "backup" do personagem que contem a posical atual,
	 * direcao e id do sprite, e essa acao e empilhada.
	 * @param character Backup do personagem contendo a posical atual, direcao e id do sprite
	 */
	public void addBackup(Character character)
	{
		stacks[stackActive].addCharBackup(character.getSpriteOffX(), character.getDir(), new Point(character.getX(), character.getY()), character.getSpeed());
		histBar.addCharMovIcon();
		clearOrChangeStacks();
	}
	
	/**
	 * Reseta o contador de acoes.
	 */
	public void use()
	{
		contActions = 0;
	}
	
	/**
	 * Adiciona o backup do personagem com a posicoes antigas de quando ele estava voltando no tempo. Com esses backups, e possivel criar o efeito de rastro de movimento.
	 */
	private void addOldHeroGraphic()
	{
		Character oldHero = new Character(hero.getX(), hero.getY(), hero.getSpriteSheet(), hero.getDir());
		
		oldHeroes.add(oldHero);
	}
	
	/**
	 * Limpa a lista com o rastro de herois.
	 */
	public void clearOldHeroes()
	{
		oldHeroes.clear();
	}
	
	/**
	 * Desenha o rastro de movimento de herois durante a habilidade.
	 */
	public void drawOldHeroes()
	{
		for(int i=0; i < oldHeroes.size(); i++)
			oldHeroes.get(i).draw();
	}
	
	/**
	 * Desempilha uma acao da pilha informada. Ao desempilhar, adiciona um backup na lista de personagens responsavel em criar o rastro de movimento. Tambem remove
	 * o ultimo icone(icone do topo) da barra de historico de icones. 
	 * @param index Indice da pilha a ser desempilhada
	 * @throws Exception Caso nao seja possivel desempilhar
	 */
	private void popAndRefreshGraphics(int index) throws Exception
	{
		System.out.println("POP Stack #" + (index+1) + " " + stacks[index].getSize() + "/" + stacks[index].getSizeMax() + "; maxActionsReturn = " + maxActionsReturn);
		ActionElement actionElement = (ActionElement) stacks[index].pop();
		addOldHeroGraphic();
		histBar.removeIcon();
		
		// Verifica o tipo de acao, para chamar o metodo responsavel em restaura-la
		if(actionElement.isCharBackup())
			restoreChar(actionElement);
		else
			restoreHp(actionElement);
		contActions++;
	}
	
	/**
	 * Desempilha uma acao da pilha. Esse metodo e chamado varias vezes enquanto a habilidade estiver ativada.
	 * @return True - Caso ainda haja acoes na pilha e se ainda resta movimentos que o personagem pode restaurar. False - pilha vazia ou o personagem atingiu a quantidade maxima de acoes restauradas 
	 */
	public boolean restore()
	{
		// Jogador atingiu a quantidade maxima de acoes restauradas
		if(contActions >= maxActionsReturn) return false;
		
		if(stacks[stackActive].isEmpty())
		{
			if(stacks[otherStack].isEmpty()) // Se as duas pilhas estiverem vazias, nao existe mais acoes para o jogador restaurar
				return false;
			try {
				popAndRefreshGraphics(otherStack);
				swapStackActive(); // Se a pilha ativa estiver vazia, mas se na outra pilha nao esta cheia, entao ira trocar o id da pilha ativa
			} catch (Exception e) {
				e.printStackTrace();
				return false;
			}
		}
		else
		{
			try {
				popAndRefreshGraphics(stackActive);
			} catch (Exception e) {
				e.printStackTrace();
				return false;
			}
			
		}
		
		return true;
	}
	
	/**
	 * Restaura a posicao do personagem.
	 * @param actionElement Acao que contem o backup(antigo) da posicao, id da sprite e direcao
	 */
	private void restoreChar(ActionElement actionElement)
	{
		try {
			hero.setX(actionElement.getX());
			hero.setY(actionElement.getY());
			hero.setSpriteOffX(actionElement.getSpriteOffX());
			hero.setDir(actionElement.getDir());
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}
	
	/**
	 * Restaura a vida do personagem.
	 * @param actionElement Acao que contem a vida antiga antiga do personagem
	 */
	private void restoreHp(ActionElement actionElement)
	{
		try {
			// Verifica a diferenca da vida antiga com a vida atual, e acrescenta com 10, pois a vida do personagem e multiplo de 10.
			int diff = actionElement.getHp() - lifebar.getHp() + 10;
			lifebar.addHp(diff/StackLifeBar.LP_UNIT);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public ArrayList<Character> getOldHeroes() {
		return oldHeroes;
	}

	public void setOldHeroes(ArrayList<Character> oldHeroes) {
		this.oldHeroes = oldHeroes;
	}
	
	
}
