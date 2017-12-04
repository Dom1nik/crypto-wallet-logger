import java.util.Scanner;
import java.util.Locale;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.security.KeyStore.Entry;
import java.util.ArrayList;
import java.io.*;

public class Main {
	
	private static void showMainMenu() {		
		System.out.println("Choose one of the following options (1-4):");
		System.out.println("---> 1) LIST ALL ENTRIES ");
		System.out.println("---> 2) ADD NEW ENTRY    ");
		System.out.println("---> 3) REMOVE ENTRY     ");
		System.out.println("---> 4) CALCULATE PROFIT ");
		System.out.println("---> 0) EXIT SCRIPT\n");
	}
	
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
	}
	
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
	}
	
	private static String getCryptoName(Scanner userInput, String message) {
		while(true) {	
			System.out.println(message);
			String cryptoName = userInput.nextLine().toUpperCase();
			return cryptoName;
		}
	}
			
	private static ArrayList<CryptoCurrencyEntry> enterNewPurchase() {
		String cryptoName;
		float currentPricePerUnitInUSD;
		float totalAcquiredAmount;
		float minSellingPrice;
		Scanner userInput = new Scanner(System.in);
		
		cryptoName = getCryptoName(userInput, "Enter the name of the Crypto currency:\n");		
		currentPricePerUnitInUSD = getFloatInput(userInput, "Enter current price per unit in USD:\n");
		totalAcquiredAmount = getFloatInput(userInput, "Enter acquired amount:\n");
		minSellingPrice = getFloatInput(userInput, "Enter minimal selling price in USD for acquired amount:\n");

		CryptoCurrencyEntry entry = new CryptoCurrencyEntry(cryptoName, currentPricePerUnitInUSD, totalAcquiredAmount, minSellingPrice);
		
		ArrayList<CryptoCurrencyEntry> entriesList = new ArrayList<CryptoCurrencyEntry>();
		entriesList.add(entry);
		
		return entriesList;
	}
	
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
	
	private static ArrayList<CryptoCurrencyEntry> removeEntry(ArrayList<CryptoCurrencyEntry> entriesList, Scanner userInput) {
		int entryIndex;
		
		if (entriesList == null || entriesList.isEmpty()) {
			System.out.println("Entry list is empty!\n");
			return null;
		}
		
		while(true) {
			System.out.println("Enter ordinal number of entry you want to delete");
			entryIndex = Integer.parseInt(userInput.nextLine());

			if ((entryIndex-1) < entriesList.size() && (entryIndex-1) <= 0) {
				entriesList.remove(entryIndex);
				return entriesList;
			}						
		}
	}
	
	private static void calculateProfit(ArrayList<CryptoCurrencyEntry> entriesList, Scanner userInput) {
		int entryIndex;
		float profitPercentage;
		float profitInUSD;
		float currentCryptoValue;
		
		while(true) {
			System.out.println("Choose stored entry");
			entryIndex = Integer.parseInt(userInput.nextLine());

			if ((entryIndex-1) < entriesList.size()) {
				currentCryptoValue = getFloatInput(userInput, "Enter current value of crypto currency in USD");		
				profitPercentage = 1 - (entriesList.get(entryIndex-1)).currentPricePerUnitInUSD/currentCryptoValue;
				profitInUSD =  (entriesList.get(entryIndex-1)).totalAcquiredAmount * currentCryptoValue
						- (entriesList.get(entryIndex-1)).totalAcquiredAmount * (entriesList).get(entryIndex-1).currentPricePerUnitInUSD;
				
				System.out.printf("Profit for selling this currency at this moment would be %.2f", profitPercentage*100);
				System.out.println("%");
				System.out.printf("Current profit in USD is %.2f\n", profitInUSD);
				return;
			}						
		}
	}
	
	public static void main(String[] args) { 
		char haveNewEntry = ' ';
		int option;
		ArrayList<CryptoCurrencyEntry> entriesList = null;
		
	    try {
	        FileInputStream fileIn = new FileInputStream("t.ser");
	        ObjectInputStream in = new ObjectInputStream(fileIn);
	        entriesList = (ArrayList<CryptoCurrencyEntry>) in.readObject();
	        in.close();
	        fileIn.close();
	    	System.out.println("Previous list of entries found and loaded\n");

	    } catch (IOException i) {
	    	System.out.println("Previous list of entries not found\n");
	    } catch (ClassNotFoundException c) {
	    	System.out.println("List of files found but corrupted!\n");
	    }
	    
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
					removeEntry(entriesList, userInput);
					break;
				case 4:
					calculateProfit(entriesList, userInput);
					break;
				case 0:
					System.out.println("Script exited");
					userInput.close();
					FileOutputStream fos;
					try {
						fos = new FileOutputStream("t.ser");
						ObjectOutputStream oos = new ObjectOutputStream(fos);
						oos.writeObject(entriesList);
						oos.close();
					} catch (FileNotFoundException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				
					System.exit(0);
				default:
					break;
			}
		}
	}
}
