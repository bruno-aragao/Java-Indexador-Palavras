import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;

class Node {
    String palavra;
    Node esquerda, direita;

    Node(String item) {
        palavra = item;
        esquerda = direita = null;
    }
}

class BinarySearchTree {
    Node raiz;

    BinarySearchTree() { 
        raiz = null; 
    }

    void inserir(String palavra) {
        raiz = inserirRec(raiz, palavra);
    }

    Node inserirRec(Node raiz, String palavra) {
        if (raiz == null) {
            raiz = new Node(palavra);
            return raiz;
        }
        if (palavra.compareTo(raiz.palavra) < 0)
            raiz.esquerda = inserirRec(raiz.esquerda, palavra);
        else if (palavra.compareTo(raiz.palavra) > 0)
            raiz.direita = inserirRec(raiz.direita, palavra);
        return raiz;
    }

    void buscar(String palavra) {
        Node resultado = buscarRec(raiz, palavra);
        if (resultado != null)
            System.out.println("Palavra encontrada");
        else
            System.out.println("Palavra não encontrada");
    }

    Node buscarRec(Node raiz, String palavra) {
        if (raiz == null || raiz.palavra.equals(palavra))
            return raiz;
        if (raiz.palavra.compareTo(palavra) > 0)
            return buscarRec(raiz.esquerda, palavra);
        return buscarRec(raiz.direita, palavra);
    }

    void buscarSubstring(String substring) {
        buscarSubstringRec(raiz, substring);
    }

    void buscarSubstringRec(Node raiz, String substring) {
        if (raiz != null) {
            if (raiz.palavra.contains(substring))
                System.out.println(raiz.palavra);
            buscarSubstringRec(raiz.esquerda, substring);
            buscarSubstringRec(raiz.direita, substring);
        }
    }

    // Método para calcular a quantidade de palavras armazenadas
    int contarPalavras() {
        return contarPalavrasRec(raiz);
    }

    int contarPalavrasRec(Node raiz) {
        if (raiz == null)
            return 0;
        return 1 + contarPalavrasRec(raiz.esquerda) + contarPalavrasRec(raiz.direita);
    }

    // Método para adicionar novas palavras ao banco de dados
    void adicionarPalavra() {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Digite a palavra que deseja adicionar: ");
        String palavra = scanner.nextLine().toLowerCase(); // converter para minúsculas
        palavra = palavra.replaceAll("[^a-z0-9]", ""); // remover caracteres especiais
        inserir(palavra);
        System.out.println("Palavra adicionada com sucesso!");
    }

    //Método para exibir todas as palavras armazenadas no processo
    void exibirPalavras() {
        exibirPalavrasRec(raiz);
    }
    
    void exibirPalavrasRec(Node raiz) {
        if (raiz != null) {
            exibirPalavrasRec(raiz.esquerda);
            System.out.println(raiz.palavra);
            exibirPalavrasRec(raiz.direita);
        }
    }

    // Método para remover palavras do banco de dados
    void removerPalavra(String palavra) {
        raiz = removerPalavraRec(raiz, palavra);
    }

    Node removerPalavraRec(Node raiz, String palavra) {
        if (raiz == null)
            return raiz;
        if (palavra.compareTo(raiz.palavra) < 0)
            raiz.esquerda = removerPalavraRec(raiz.esquerda, palavra);
        else if (palavra.compareTo(raiz.palavra) > 0)
            raiz.direita = removerPalavraRec(raiz.direita, palavra);
        else {
            if (raiz.esquerda == null)
                return raiz.direita;
            else if (raiz.direita == null)
                return raiz.esquerda;
            raiz.palavra = minValue(raiz.direita);
            raiz.direita = removerPalavraRec(raiz.direita, raiz.palavra);
        }
        return raiz;
    }

    String minValue(Node raiz) {
        String minv = raiz.palavra;
        while (raiz.esquerda != null) {
            minv = raiz.esquerda.palavra;
            raiz = raiz.esquerda;
        }
        return minv;
    }

    // Método para ler as palavras de um arquivo de texto e inseri-las na árvore
    void lerArquivo(String nomeArquivo) {
        try {
            File arquivo = new File(nomeArquivo);
            Scanner scanner = new Scanner(arquivo);
            while (scanner.hasNextLine()) {
                String linha = scanner.nextLine();
                String[] palavras = linha.split("\\s+");
                for (String palavra : palavras) {
                    palavra = palavra.toLowerCase(); // converter para minúsculas
                    palavra = palavra.replaceAll("[^a-z0-9]", ""); // remover caracteres especiais
                    inserir(palavra);
                }
            }
            scanner.close();
        } catch (FileNotFoundException e) {
            System.out.println("Ocorreu um erro ao ler o arquivo.");
            e.printStackTrace();
        }
    }
}

public class App {
    public static void main(String[] args) {
        BinarySearchTree bst = new BinarySearchTree();
        bst.lerArquivo("C:\\Users\\araga\\Documents\\teste.txt");

        Scanner scanner = new Scanner(System.in);
        int opcao;
        do {
            System.out.println("\nEscolha uma opção:");
            System.out.println("1 - Buscar uma palavra em seu banco de dados");
            System.out.println("2 - Buscar palavras a partir de uma substring");
            System.out.println("3 - Adicionar novas palavras ao banco de dados");
            System.out.println("4 - Remover palavras do banco de dados");
            System.out.println("5 - Exibir todas as palavras armazenadas");
            System.out.println("0 - Sair");
            System.out.print("Opção: ");
            opcao = scanner.nextInt();
            scanner.nextLine(); // Consumir a quebra de linha

            switch (opcao) {
                case 1:
                    System.out.print("Digite a palavra que deseja buscar: ");
                    String palavraBuscada = scanner.nextLine().toLowerCase();
                    palavraBuscada = palavraBuscada.replaceAll("[^a-z0-9]", "");
                    bst.buscar(palavraBuscada);
                    break;
                case 2:
                    System.out.print("Digite a substring que deseja buscar: ");
                    String substring = scanner.nextLine().toLowerCase();
                    substring = substring.replaceAll("[^a-z0-9]", "");
                    bst.buscarSubstring(substring);
                    break;
                case 3:
                    bst.adicionarPalavra();
                    break;
                case 4:
                    System.out.print("Digite a palavra que deseja remover: ");
                    String palavraRemover = scanner.nextLine().toLowerCase();
                    palavraRemover = palavraRemover.replaceAll("[^a-z0-9]", "");
                    bst.removerPalavra(palavraRemover);
                    break;
                case 5:
                    bst.exibirPalavras();
                    break;
                case 0:
                    System.out.println("Programa Encerrado!");
                    break;
                default:
                    System.out.println("Opção inválida. Tente novamente.");
            }
        } while (opcao != 0);
    }
}
