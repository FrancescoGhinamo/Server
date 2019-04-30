package server.in;

/**
 * Implementazione di un menu per CLI
 * @author franc
 *
 */
public class Menu {
	
	/**
	 * Voci del menu
	 */
	private String[] voci;
	/**
	 * creazione di un menu con le proprie opzioni memorizzate in un array
	 * @param opzioni
	 */
	public Menu(String[] opzioni) {
		this.setVoci(opzioni);
	}
	/**
	 * permette la scelta di una opzione da menu e ne restituisce il valore corrispondente
	 * @return
	 */
	public int sceltaMenu() {
		int scelta = 0;
		Keyboard t = new Keyboard();
		stampaMenu();
		System.out.print("\nInserire la propria scelta: ");
		scelta = t.readInt();
		return scelta;
	}
	/**
	 * stampa a video l'intero menu
	 */
	public void stampaMenu() {
		for(int i = 1; i <= voci.length; i++) {
			stampaVoceMenu(i);
		}
	}
	/**
	 * stampa a video una singola voce del menu, contraddistinta da un numero
	 * @param n
	 */
	public void stampaVoceMenu(int n) {
		System.out.print(" - ");
		System.out.print(n);
		System.out.print(" === ");
		if(n - 1 < voci.length) {
			//n - 1 a causa degli indici dell'array
			System.out.println(voci[n - 1]);
		}
		else {
			System.out.println("\tErrore nella lunghezza del menu");
		}
	}
	
	public String[] getVoci() {
		return voci;
	}

	public void setVoci(String[] voci) {
		this.voci = voci;
	}
	
	
	
}
