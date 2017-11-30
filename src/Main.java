import java.util.Scanner;
import java.util.Locale;
import java.security.KeyStore.Entry;
import java.util.ArrayList;

public class Main {
	
	private static void showMainMenu() {		
		System.out.println("Choose one of the following options (1-4):");
		System.out.println("---> 1) LIST ALL ENTRIES ");
		System.out.println("---> 2) ADD NEW ENTRY    ");
		System.out.println("---> 3) REMOVE ENTRY     ");
		System.out.println("---> 4) CALCULATE PROFIT ");
		System.out.println("---> 0) EXIT SCRIPT\n");
	};
	
	private static float getFloatInput(Scanner userInput, String message) {
		while(true) {	
			try {
				System.out.println(message);
				float cryptoFloatInput = Float.parseFloat(userInput.nextLine());
				return cryptoFloatInput;
			} catch (Exception e) { 
				continue;
			}
		}
	};
	
	private static int chooseOption(Scanner userInput) {
		while(true) {	
			try {
				showMainMenu();
				int option = Integer.parseInt(userInput.nextLine());
				if (0 <= option && option < 5) return option;
			} catch (Exception e) { 
				continue;
			}
		}
	};
	
	private static String getCryptoName(Scanner userInput, String message) {
		while(true) {	
			System.out.println(message);
			String cryptoName = userInput.nextLine().toUpperCase();
			return cryptoName;
		}
	};
			
	private static ArrayList<CryptoCurrencyEntry> enterNewPurchase() {
		String cryptoName;
		float currentPricePerUnitInUSD;
		float totalAcquiredAmount;
		float minSellingPrice;
		Scanner userInput = new Scanner(System.in);
		
		cryptoName = getCryptoName(userInput, "Enter the name of the Crypto currency:\n");		
		currentPricePerUnitInUSD = getFloatInput(userInput, "Enter current price per unit in USD:\n");
		totalAcquiredAmount = getFloatInput(userInput, "Enter ac 	quired amount:\n");
		minSellingPrice = getFloatInput(userInput, "Enter minimal selling price in USD for acquired amount:\n");

		CryptoCurrencyEntry entry = new CryptoCurrencyEntry(cryptoName, currentPricePerUnitInUSD, totalAcquiredAmount, minSellingPrice);
		
		ArrayList<CryptoCurrencyEntry> entriesList = new ArrayList<CryptoCurrencyEntry>();
		entriesList.add(entry);
		
		return entriesList;
	};
	
	private static String floatToString(float value) {
		return String.format(Locale.US, "%.4f", value);
	}
	
	private static void showCryptoCurrencyEntry(ArrayList<CryptoCurrencyEntry> entriesList, int index) {
		System.out.println("*************** ENTRY " + index + " ***************\n");
		System.out.println("CURRENCY: " + entriesList.get(index).cryptoName);
		System.out.println("CURRENT PRICE PER UNIT IN USD: " + floatToString(entriesList.get(index).currentPricePerUnitInUSD));
		System.out.println("TOTAL ACQUIRED AMOUNT: " + floatToString(entriesList.get(index).totalAcquiredAmount));
		System.out.println("MIN SELLING PRICE: " + floatToString(entriesList.get(index).minSellingPrice));
		System.out.println("***************************************************\n");
	}
	
	private static ArrayList<CryptoCurrencyEntry> removeEntry(ArrayList<CryptoCurrencyEntry> entriesList, int index) {
		entriesList.remove(index);
		return entriesList;
	}
	
	public static void main(String[] args) { 
		char haveNewEntry = ' ';
		int option;
		ArrayList<CryptoCurrencyEntry> entriesList = null;
		Scanner userInput = new Scanner(System.in);
		
		while(true) {
			option = chooseOption(userInput);
			switch (option) {
				case 1:
					if (entriesList == null || entriesList.isEmpty()) {
						System.out.println("No entries found!\n");
						break;
					}
					for (int entryIndex=0; entryIndex<entriesList.size(); entryIndex++) {
						showCryptoCurrencyEntry(entriesList, entryIndex);
					}				
					break;
				case 2:
					while(true) {
						entriesList = enterNewPurchase();	
						do{
							System.out.println("Continue with new entry (y/n)?");
							haveNewEntry = userInput.next().charAt(0);
						} while(haveNewEntry != 'n' && haveNewEntry != 'y');
						if (haveNewEntry == 'n') break;
					}
					break;
				case 3:
					removeEntry(entriesList, 0);
					break;
				case 4:
					break;
				case 0:
					System.out.println("Script exited");
					userInput.close();
					System.exit(0);
				default:
					break;
			}
		}
	}
}
