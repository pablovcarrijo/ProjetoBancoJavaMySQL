package application;

import java.util.Locale;
import java.util.Scanner;

import model.entities.NewAccount;
import model.entities.RemoveAccount;
import model.entities.Transferencia;
import model.entities.ViewAccounts;

public class App {

	public static void main(String[] args) {
		
		Scanner sc = new Scanner(System.in).useLocale(Locale.US);
		
		int n;
		do {
			System.out.println("1 - Criar conta");
			System.out.println("2 - Excluir conta");
			System.out.println("3 - Realizar transferencia");
			System.out.println("4 - Mostrar contas");
			System.out.println("0 - Sair do programa");
			n = sc.nextInt();
			sc.nextLine();
			switch(n) {
				case 1:
					System.out.print("Digite o nome do titular: ");
					String titularNameAdd = sc.nextLine();
					System.out.print("Digite o saldo: ");
					Double saldoAdd = sc.nextDouble();
					sc.nextLine();
					
					NewAccount acc = new NewAccount(titularNameAdd, saldoAdd);
					acc.newAccount();
					
					System.out.println();
					break;
				case 2:
					System.out.print("Digite o nome do titular: ");
					String titularNameRemove = sc.nextLine();
					
					RemoveAccount.removeAccount(titularNameRemove);
					
					System.out.println();
					break;
				case 3:
					System.out.print("Conta de saque: ");
					Integer contaSaque = sc.nextInt();
					sc.nextLine();
					System.out.print("Conta de deposito: ");
					Integer contaDeposito = sc.nextInt();
					sc.nextLine();
					
					System.out.print("Valor da transferencia: ");
					Double value = sc.nextDouble();
					
					Transferencia.transfer(contaSaque, contaDeposito, value);
					System.out.println();
					break;
				case 4:
					ViewAccounts.view();
					System.out.println();
					break;
				case 0:
					System.out.println("Programa encerrado...");
					break;
				default:
					System.out.println("Opcao invalida, tente novamente: ");
					break;
			}
		}while(n != 0);
		
		
	}
	
}
