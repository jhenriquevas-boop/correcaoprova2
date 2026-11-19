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
    char tipo; // {'D', 'S', 'T'}
    double valor;
}

public class prova1 {
    public static final int TAM_CONTAS = 50;
    public static final int TAM_OP = 1000;

    public static int buscaId(Conta[] v, int tam, int x) {
        for (int i = 0; i < tam; i++) {
            if (v[i].id == x) {
                return i;
            }
        }
        return -1;
    }

    // q1
    public static int cadastrarConta(Conta[] v, int tam) {
        Scanner sc = new Scanner(System.in);

        if (tam < TAM_CONTAS) {
            Conta c = new Conta();

            System.out.print("Digite o id: ");
            c.id = sc.nextInt();
            sc.nextLine();
            System.out.print("Digite o cliente: ");
            c.cliente = sc.nextLine();
            System.out.print("Digite o saldo: ");
            c.saldo = sc.nextDouble();
            System.out.print("Digite o limite: ");
            c.limite = sc.nextDouble();

            if (buscaId(v, tam, c.id) == -1 && c.saldo >= 0 && c.limite >= 0) {
                v[tam] = c;
                tam++;
            }
        }

        return tam;
    }

    // q2
    public static int buscaBinariaCliente(Conta[] v, int tam, String x) {
        int inicio = 0;
        int fim = tam - 1;
        int pos = -1;

        while (inicio <= fim && pos == -1) {
            int meio = (inicio + fim) / 2;
            int comp = v[meio].cliente.compareTo(x);

            if (comp == 0) {
                pos = meio;
            } else if (comp < 0) {
                inicio = meio + 1;
            } else {
                fim = meio - 1;
            }
        }

        return pos;
    }

    // q3
    public static int insertionSortCliente(Conta[] v, int tam) {
        for (int i = 1; i < tam; i++) {
            Conta chave = v[i];
            int j = i - 1;

            while (j >= 0 && v[j].cliente.compareTo(chave.cliente) > 0) {
                v[j + 1] = v[j];
                j--;
            }
            v[j + 1] = chave;
        }

        return tam;
    }

    // q4
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

    // q5
    public static void extrato(Conta[] vContas, int tamConta, Operacao[] vOp, int tamOp) {
        Scanner sc = new Scanner(System.in);
        System.out.print("Digite o id da conta: ");
        int id = sc.nextInt();

        int pos = buscaId(vContas, tamConta, id);

        if (pos != -1) {
            Operacao[] vOpFiltrado = new Operacao[TAM_OP];
            int tamFiltrado = filtraOperacoes(vOp, tamOp, vOpFiltrado, id);

            int inicio = 0;
            if (tamFiltrado > 5) {
                inicio = tamFiltrado - 5;
            }

            double saldo = 0;
            for (int i = 0; i < inicio; i++) {
                saldo += vOpFiltrado[i].valor;
            }

            System.out.println("CONTA N. " + vContas[pos].id);
            System.out.println("CLIENTE: " + vContas[pos].cliente);

            if (saldo < 0) {
                System.out.println("Saldo Anterior: " + (-saldo) + "D");
            } else {
                System.out.println("Saldo Anterior: " + saldo + "C");
            }

            System.out.println("ID | OPERACAO | VALOR | SALDO");

            for (int i = inicio; i < tamFiltrado; i++) {
                saldo += vOpFiltrado[i].valor;

                String nomeOp = "";
                if (vOpFiltrado[i].tipo == 'D') {
                    nomeOp = "Deposito";
                } else if (vOpFiltrado[i].tipo == 'S') {
                    nomeOp = "Saque";
                } else if (vOpFiltrado[i].tipo == 'T') {
                    nomeOp = "Transferencia";
                }

                double valorAbs = vOpFiltrado[i].valor;
                char tipoValor = 'C';
                if (valorAbs < 0) {
                    valorAbs = -valorAbs;
                    tipoValor = 'D';
                }

                double saldoAbs = saldo;
                char tipoSaldo = 'C';
                if (saldoAbs < 0) {
                    saldoAbs = -saldoAbs;
                    tipoSaldo = 'D';
                }

                System.out.println(vOpFiltrado[i].id + " | " + nomeOp + " | " + valorAbs + tipoValor + " | " + saldoAbs + tipoSaldo);
            }

            if (saldo < 0) {
                System.out.println("Saldo Atual: " + (-saldo) + "D");
            } else {
                System.out.println("Saldo Atual: " + saldo + "C");
            }
        }
    }

    public static void main(String[] args) {
        Conta[] contas = new Conta[TAM_CONTAS];
        int tamContas = 0;

        Operacao[] ops = new Operacao[TAM_OP];
        int tamOp = 0;

        Conta c1 = new Conta();
        c1.id = 2;
        c1.cliente = "Ana Beatriz";
        c1.saldo = 2000.0;
        c1.limite = 1000.0;
        contas[tamContas++] = c1;

        Conta c2 = new Conta();
        c2.id = 3;
        c2.cliente = "Carlos Silva";
        c2.saldo = 1500.0;
        c2.limite = 700.0;
        contas[tamContas++] = c2;

        Conta c3 = new Conta();
        c3.id = 1;
        c3.cliente = "Maria Jose Santos";
        c3.saldo = 1000.0;
        c3.limite = 500.0;
        contas[tamContas++] = c3;

        Operacao op1 = new Operacao();
        op1.id = 10; op1.idConta = 1; op1.tipo = 'D'; op1.valor = 2437.55;
        ops[tamOp++] = op1;

        Operacao op2 = new Operacao();
        op2.id = 25; op2.idConta = 1; op2.tipo = 'D'; op2.valor = 1000.00;
        ops[tamOp++] = op2;

        Operacao op3 = new Operacao();
        op3.id = 37; op3.idConta = 1; op3.tipo = 'D'; op3.valor = 237.44;
        ops[tamOp++] = op3;

        Operacao op4 = new Operacao();
        op4.id = 83; op4.idConta = 1; op4.tipo = 'T'; op4.valor = -4000.00;
        ops[tamOp++] = op4;

        Operacao op5 = new Operacao();
        op5.id = 95; op5.idConta = 1; op5.tipo = 'S'; op5.valor = -250.00;
        ops[tamOp++] = op5;

        Operacao op6 = new Operacao();
        op6.id = 112; op6.idConta = 1; op6.tipo = 'T'; op6.valor = 1000.00;
        ops[tamOp++] = op6;

        // q1
        System.out.println("--- Q1 ---");
        tamContas = cadastrarConta(contas, tamContas);
        System.out.println("Tamanho: " + tamContas);

        // q2
        System.out.println("\n--- Q2 ---");
        int pos = buscaBinariaCliente(contas, tamContas, "Carlos Silva");
        System.out.println("Posicao: " + pos);

        // q3
        System.out.println("\n--- Q3 ---");
        insertionSortCliente(contas, tamContas);
        for (int i = 0; i < tamContas; i++) {
            System.out.println(contas[i].id + " - " + contas[i].cliente);
        }

        // q4
        System.out.println("\n--- Q4 ---");
        Operacao[] opsFiltradas = new Operacao[TAM_OP];
        int tamFiltrado = filtraOperacoes(ops, tamOp, opsFiltradas, 1);
        System.out.println("Tamanho filtrado: " + tamFiltrado);

        // q5
        System.out.println("\n--- Q5 ---");
        extrato(contas, tamContas, ops, tamOp);
    }
}