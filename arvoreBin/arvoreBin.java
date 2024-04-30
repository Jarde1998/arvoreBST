
import java.io.*;

class Nodo {
	int info;
	Nodo esq, dir, pai;

}

public class arvoreBin {
	private Nodo raiz;

	public arvoreBin() {
		raiz = null;
	}

	public Nodo alocarNodo(int valor) {
		Nodo novoNodo = new Nodo();
		novoNodo.info = valor;
		novoNodo.esq = novoNodo.dir = novoNodo.pai = null;
		return novoNodo;
	}

	public void inserir(int valor) {
		raiz = inserir(valor, raiz, null);
	}

	private Nodo inserir(int valor, Nodo raiz, Nodo pai) {
		if (raiz == null) {
	        raiz = alocarNodo(valor);
	        raiz.pai = pai; // Definindo o pai corretamente
	    } else if (valor > raiz.info) {
	        raiz.dir = inserir(valor, raiz.dir, raiz); // Passando raiz como pai do nó à direita
	    } else if (valor < raiz.info) {
	        raiz.esq = inserir(valor, raiz.esq, raiz); // Passando raiz como pai do nó à esquerda
	    }

	    return raiz;

	}

	public void preOrdem() {
		preOrdem(raiz);
	}

	private void preOrdem(Nodo raiz) {
		if (raiz != null) {
			System.out.println(raiz.info + " ");
			preOrdem(raiz.esq);
			preOrdem(raiz.dir);
		}
	}

	public void central() {
		central(raiz);
	}

	private void central(Nodo raiz) {
		if (raiz != null) {
			central(raiz.esq);
			System.out.println(raiz.info + " ");
			central(raiz.dir);
		}
	}

	public void posOrdem() {
		posOrdem(raiz);
	}

	private void posOrdem(Nodo raiz) {
		if (raiz != null) {
			posOrdem(raiz.esq);
			posOrdem(raiz.dir);
			System.out.println(raiz.info + " ");
		}
	}

	public Nodo removerRecursivo(int valor, Nodo raiz) {
		if (raiz == null) {
			return raiz;
		}
		
		// Caso o valor a ser removido seja menor que o valor do nó atual, ir para a subárvore esquerda
		if (valor < raiz.info) {
			raiz.esq = removerRecursivo(valor, raiz.esq);
		
		// Caso o valor a ser removido seja maior que o valor do nó atual, ir para a subárvore direita
		} else if (valor > raiz.info) {
			raiz.dir = removerRecursivo(valor, raiz.dir);
			
		// Se o valor a ser removido for igual ao valor do nó atual
		} else {
			// Nó com apenas um filho ou nenhum filho
			if (raiz.esq == null) {
				return raiz.dir;
			} else if (raiz.dir == null) {
				return raiz.esq;
			}
			
			// Nó com dois filhos: encontrar o menor valor na subárvore direita (sucessor)
	        raiz.info = buscarMin(raiz.dir).info;

	        // Remover o sucessor da subárvore direita
	        raiz.dir = removerRecursivo(raiz.info, raiz.dir);
		}

		return raiz;
	}

	public Nodo buscar(int valor) {
		return buscar(valor, raiz);
	}

	private Nodo buscar(int valor, Nodo raiz) {
		if (raiz == null || valor == raiz.info) {
			return raiz;
		}
		if (valor < raiz.info) {
			return buscar(valor, raiz.esq);
		} else {
			return buscar(valor, raiz.dir);
		}
	}

	private Nodo buscarMin(Nodo raiz) {
		Nodo atual = raiz;
		while (atual.esq != null) {
			atual = atual.esq;
		}
		return raiz;
	}

	public void gerarArqDot(String filename) {
		try (BufferedWriter out = new BufferedWriter(new FileWriter(filename))) {
            out.write("digraph ArvoreBin {\n");
            escreverPreOrdemDot(raiz, out);
            out.write("}\n");
            System.out.println("Arquivo .dot gerado com sucesso: " + filename);
        } catch (IOException e) {
            System.err.println("Erro ao gerar o arquivo .dot: " + e.getMessage());
        }
	}

	private void escreverPreOrdemDot(Nodo raiz, BufferedWriter out) throws IOException {
		if (raiz != null) {
            out.write(String.format("\t%d [label=\"%d\"];\n", raiz.info, raiz.info));
            if (raiz.esq != null) {
                out.write(String.format("\t%d -> %d;\n", raiz.info, raiz.esq.info));
            }
            if (raiz.dir != null) {
                out.write(String.format("\t%d -> %d;\n", raiz.info, raiz.dir.info));
            }
            escreverPreOrdemDot(raiz.esq, out);
            escreverPreOrdemDot(raiz.dir, out);
        }
	}

	public static void main(String[] args) {
		arvoreBin arvore = new arvoreBin();
		arvore.inserir(5);
		arvore.inserir(4);
		arvore.inserir(10);
		arvore.inserir(24);
		arvore.inserir(7);
		for (int i = 0; i < 6; i++) {
			System.out.println();
		}

		System.out.println("Caminhamento pré-ordem:");
		arvore.preOrdem();

		System.out.println("\nCaminhamento em ordem:");
		arvore.central();

		System.out.println("\nCaminhamento pós-ordem:");
		arvore.posOrdem();

		System.out.println("\nBusca:");
		if (arvore.buscar(20) == null) {
			System.out.println("Nao encontrou.");
		} else {
			System.out.println("Achou: " + arvore.buscar(20).info);
		}

		// Salvar no arquivo dot pra visualização
		arvore.gerarArqDot("arvoreBin5.dot");

	}
}
