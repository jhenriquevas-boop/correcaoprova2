
import java.util.Scanner;

class conta {
    int id;
    String cliente;
    double saldo;
    double limite;
}

class operacao {
    int id;
    int idConta;
    char tipo;
    double valor;
}

public class prova {

    public static final int TAM_CONTAS = 50;
    public static final int TAM_OP = 1000;

    static conta[] contas = new conta[TAM_CONTAS];
    static Scanner sc = new Scanner(System.in);

    /* QUESTAO 1 */
    public static int cadastrarconta(conta[] v, int tam) {

        if (tam >= TAM_CONTAS) {
            System.out.println("Vetor cheio!");
            return tam;
        }

        conta novaconta = new conta();
        novaconta.id = sc.nextInt();

        for (int i = 0; i < tam; i++) {
            if (v[i].id == novaconta.id) {
                return tam;
            }
        }

        sc.nextLine();

        novaconta.cliente = sc.nextLine();
        novaconta.saldo = sc.nextDouble();
        novaconta.limite = sc.nextDouble();

        if (novaconta.saldo < 0 || novaconta.limite < 0) {
            return tam;
        }

        v[tam] = novaconta;
        tam++;

        return tam;
    }

    /* QUESTAO 2 */


    /* QUESTAO 3 */
    public static void Insertionsortcliente(conta[] v, int tam) {

        conta aux;
        int j;

        for (int i = 1; i < tam; i++) {
            aux = v[i];
            j = i - 1;

            while (j >= 0 && v[j].cliente.compareTo(aux.cliente) > 0) {
                v[j + 1] = v[j];
                j--;
            }

            v[j + 1] = aux;
        }
    }

    /* QUESTAO 4 */
    public static int filtraoperacoes(
        operacao[] vop, int tamop,
        operacao[] vopFiltrado, int x) {

        int tamf = 0;

        for (int i = 0; i < tamop; i++) {
            if (vop[i] != null && vop[i].idConta == x) {
                vopFiltrado[tamf] = vop[i];
                tamf++;
            }
        }

        return tamf;
    }

    /* QUESTAO 5 */


    public static void main(String[] args) {

        int tam = 0;

        tam = cadastrarconta(contas, tam);

        Insertionsortcliente(contas, tam);

        operacao[] vop = new operacao[TAM_OP];
        operacao[] vopFiltrado = new operacao[TAM_OP];

        int tamop = 0;

        int tamf = filtraoperacoes(vop, tamop, vopFiltrado, 1);

        System.out.println("tam: " + tam + " | tamf: " + tamf);
    }
}
