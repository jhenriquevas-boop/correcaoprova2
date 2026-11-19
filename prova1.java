import java.util.Locale;
import java.util.Scanner;

class Conta {
    int id;
    String cliente;
    double saldo;
    double limite;
}

class Operacao {
    int id;
    int idConta;
    char tipo;
    double valor;
}

public class prova1 {

    public static final int TAM_CONTAS = 50;
    public static final int TAM_OP = 1000;

    static Scanner sc = new Scanner(System.in);

    public static int buscaId(Conta[] v, int tam, int x) {
        for (int i = 0; i < tam; i++) {
            if (v[i].id == x) {
                return i;
            }
        }
        return -1;
    }

    public static Conta lerConta() {
        Conta c = new Conta();

        System.out.print("Digite o id: ");
        c.id = sc.nextInt();
        sc.nextLine();
        System.out.print("Digite o nome do cliente: ");
        c.cliente = sc.nextLine();
        System.out.print("Digite o saldo: ");
        c.saldo = sc.nextDouble();
        System.out.print("Digite o limite: ");
        c.limite = sc.nextDouble();
        sc.nextLine();
        return c;
    }

    public static Conta criarConta(int id, String cliente, double saldo, double limite) {
        Conta c = new Conta();
        c.id = id;
        c.cliente = cliente;
        c.saldo = saldo;
        c.limite = limite;
        return c;
    }

    public static Operacao criarOperacao(int id, int idConta, char tipo, double valor) {
        Operacao o = new Operacao();
        o.id = id;
        o.idConta = idConta;
        o.tipo = tipo;
        o.valor = valor;
        return o;
    }

    public static boolean contaValida(Conta[] v, int tam, Conta c) {
        if (buscaId(v, tam, c.id) != -1) {
            System.out.println("Erro: ja existe conta com o id " + c.id + ".");
            return false;
        }
        if (c.saldo < 0) {
            System.out.println("Erro: saldo negativo na conta " + c.id + ".");
            return false;
        }
        if (c.limite < 0) {
            System.out.println("Erro: limite negativo na conta " + c.id + ".");
            return false;
        }
        return true;
    }

    public static int adicionarConta(Conta[] v, int tam, Conta c) {
        if (tam >= TAM_CONTAS) {
            System.out.println("Vetor cheio! Nao e possivel cadastrar.");
            return tam;
        }

        if (contaValida(v, tam, c)) {
            v[tam] = c;
            tam++;
            System.out.println("Conta " + c.id + " cadastrada com sucesso!");
        }
        return tam;
    }

    public static String nomeOperacao(char tipo) {
        if (tipo == 'D') {
            return "Deposito";
        } else if (tipo == 'S') {
            return "Saque";
        } else {
            return "Transferencia";
        }
    }

    public static String formataValor(double valor) {
        if (valor >= 0) {
            return String.format(Locale.US, "%.2fC", valor);
        } else {
            return String.format(Locale.US, "%.2fD", -valor);
        }
    }

    public static void listarContas(Conta[] v, int tam) {
        System.out.println("ID | CLIENTE              | SALDO      | LIMITE");
        for (int i = 0; i < tam; i++) {
            System.out.printf(Locale.US, "%02d | %-20s | %10.2f | %.2f%n",
                    v[i].id, v[i].cliente, v[i].saldo, v[i].limite);
        }
    }

    public static void listarOperacoes(Operacao[] v, int tam) {
        System.out.println("ID   | CONTA | OPERACAO      | VALOR");
        for (int i = 0; i < tam; i++) {
            System.out.printf(Locale.US, "%04d | %03d   | %-13s | %.2f%n",
                    v[i].id, v[i].idConta, nomeOperacao(v[i].tipo), v[i].valor);
        }
    }

    public static int cadastrarConta(Conta[] v, int tam) {
        if (tam >= TAM_CONTAS) {
            System.out.println("Vetor cheio! Nao e possivel cadastrar.");
            return tam;
        }

        Conta c = lerConta();
        return adicionarConta(v, tam, c);
    }

    public static int buscaBinariaCliente(Conta[] v, int tam, String x) {
        int inicio = 0;
        int fim = tam - 1;

        while (inicio <= fim) {
            int meio = (inicio + fim) / 2;
            int comp = v[meio].cliente.compareToIgnoreCase(x);

            if (comp == 0) {
                return meio;
            } else if (comp < 0) {
                inicio = meio + 1;
            } else {
                fim = meio - 1;
            }
        }
        return -1;
    }

    public static int insertionSortCliente(Conta[] v, int tam) {
        for (int i = 1; i < tam; i += 1) {
            Conta aux = v[i];
            int j = i - 1;

            while (j >= 0 && v[j].cliente.compareToIgnoreCase(aux.cliente) > 0) {
                v[j + 1] = v[j];
                j -= 1;
            }
            v[j + 1] = aux;
        }
        return tam;
    }

    public static int filtraOperacoes(Operacao[] vOp, int tamOp, Operacao[] vOpFiltrado, int x) {
        int tamFiltrado = 0;

        for (int i = 0; i < tamOp; i++) {
            if (vOp[i].idConta == x) {
                vOpFiltrado[tamFiltrado] = vOp[i];
                tamFiltrado++;
            }
        }
        return tamFiltrado;
    }

    public static void imprimirExtrato(Conta[] vContas, int tamConta, Operacao[] vOp, int tamOp, int id) {
        int pos = buscaId(vContas, tamConta, id);
        if (pos == -1) {
            System.out.println("Conta " + id + " nao encontrada.");
            return;
        }

        Operacao[] vFiltrado = new Operacao[TAM_OP];
        int tamFiltrado = filtraOperacoes(vOp, tamOp, vFiltrado, id);

        int inicio = tamFiltrado - 5;
        if (inicio < 0) {
            inicio = 0;
        }

        double saldo = 0;
        for (int i = 0; i < inicio; i++) {
            saldo = saldo + vFiltrado[i].valor;
        }

        System.out.println("+----------------------------------------+");
        System.out.printf("|%-40s|%n", "            C O N T A  N." + String.format("%03d", id));
        System.out.println("+----------------------------------------+");
        System.out.printf("|%-40s|%n", "CLIENTE: " + vContas[pos].cliente);
        System.out.println("+----------------------------------------+");
        System.out.println("| ID | O P E R A C A O |  VALOR |  SALDO |");
        System.out.println("+----+-----------------+--------+--------+");
        System.out.printf("|    |%-17s|        |%8s|%n", "Saldo Anterior", formataValor(saldo));

        for (int i = inicio; i < tamFiltrado; i++) {
            saldo = saldo + vFiltrado[i].valor;
            System.out.printf("|%04d|%-17s|%8s|%8s|%n", vFiltrado[i].id,
                    nomeOperacao(vFiltrado[i].tipo),
                    formataValor(vFiltrado[i].valor),
                    formataValor(saldo));
        }

        System.out.println("+----+-----------------+--------+--------+");
        System.out.printf("%-32s|%8s|%n", "Saldo Atual", formataValor(saldo));
        System.out.println(String.format("%32s", "") + "+--------+");
    }

    public static void extrato(Conta[] vContas, int tamConta, Operacao[] vOp, int tamOp) {
        System.out.print("Digite o id da conta: ");
        int id = sc.nextInt();
        sc.nextLine();

        imprimirExtrato(vContas, tamConta, vOp, tamOp, id);
    }

    public static void main(String[] args) {
        Conta[] contas = new Conta[TAM_CONTAS];
        Operacao[] ops = new Operacao[TAM_OP];
        int tamContas = 0;
        int tamOps = 0;

        System.out.println("===== 1) CADASTRO DE CONTAS =====");
        tamContas = adicionarConta(contas, tamContas, criarConta(1, "Maria Jose Santos", 2437.55, 1000));
        tamContas = adicionarConta(contas, tamContas, criarConta(3, "Carlos Souza", 800, 500));
        tamContas = adicionarConta(contas, tamContas, criarConta(2, "Ana Lima", 150.75, 300));
        tamContas = adicionarConta(contas, tamContas, criarConta(4, "Beatriz Alves", 0, 200));
        tamContas = adicionarConta(contas, tamContas, criarConta(1, "Pedro Duplicado", 10, 10));
        tamContas = adicionarConta(contas, tamContas, criarConta(5, "Saldo Negativo", -50, 100));
        tamContas = adicionarConta(contas, tamContas, criarConta(6, "Limite Negativo", 50, -10));

        System.out.println();
        System.out.println("Contas antes de ordenar (tamanho " + tamContas + "):");
        listarContas(contas, tamContas);

        System.out.println();
        System.out.println("===== 3) INSERTION SORT POR CLIENTE =====");
        insertionSortCliente(contas, tamContas);
        listarContas(contas, tamContas);

        System.out.println();
        System.out.println("===== 2) BUSCA BINARIA POR CLIENTE =====");
        System.out.println("Beatriz Alves -> posicao " + buscaBinariaCliente(contas, tamContas, "Beatriz Alves"));
        System.out.println("Maria Jose Santos -> posicao " + buscaBinariaCliente(contas, tamContas, "Maria Jose Santos"));
        System.out.println("Zeca Pagodinho -> posicao " + buscaBinariaCliente(contas, tamContas, "Zeca Pagodinho"));

        ops[tamOps] = criarOperacao(10, 1, 'D', 2437.55);
        tamOps++;
        ops[tamOps] = criarOperacao(11, 2, 'D', 500.00);
        tamOps++;
        ops[tamOps] = criarOperacao(25, 1, 'D', 1000.00);
        tamOps++;
        ops[tamOps] = criarOperacao(37, 1, 'D', 237.44);
        tamOps++;
        ops[tamOps] = criarOperacao(40, 2, 'S', -120.50);
        tamOps++;
        ops[tamOps] = criarOperacao(60, 2, 'D', 80.00);
        tamOps++;
        ops[tamOps] = criarOperacao(70, 3, 'D', 300.00);
        tamOps++;
        ops[tamOps] = criarOperacao(83, 1, 'T', -4000.00);
        tamOps++;
        ops[tamOps] = criarOperacao(95, 1, 'S', -250.00);
        tamOps++;
        ops[tamOps] = criarOperacao(112, 1, 'T', 1000.00);
        tamOps++;

        System.out.println();
        System.out.println("===== OPERACOES CADASTRADAS =====");
        listarOperacoes(ops, tamOps);

        System.out.println();
        System.out.println("===== 4) FILTRA OPERACOES DA CONTA 2 =====");
        Operacao[] filtradas = new Operacao[TAM_OP];
        int tamFiltradas = filtraOperacoes(ops, tamOps, filtradas, 2);
        System.out.println("Quantidade encontrada: " + tamFiltradas);
        listarOperacoes(filtradas, tamFiltradas);

        System.out.println();
        System.out.println("===== 5) EXTRATO DA CONTA 1 =====");
        imprimirExtrato(contas, tamContas, ops, tamOps, 1);

        System.out.println();
        System.out.println("===== 5) EXTRATO DA CONTA 2 =====");
        imprimirExtrato(contas, tamContas, ops, tamOps, 2);

        System.out.println();
        System.out.println("===== 5) EXTRATO DE CONTA INEXISTENTE =====");
        imprimirExtrato(contas, tamContas, ops, tamOps, 99);
    }
}