
public class CryptoCurrencyEntry {
	String cryptoName;
	float currentPricePerUnitInUSD;
	float totalAcquiredAmount;
	float minSellingPrice;
	
	CryptoCurrencyEntry(String cryptoName, float currentPricePerUnitInUSD, float totalAcquiredAmount, float minSellingPrice) {
		this.cryptoName = cryptoName;
		this.currentPricePerUnitInUSD = currentPricePerUnitInUSD;
		this.totalAcquiredAmount = totalAcquiredAmount;
		this.minSellingPrice = minSellingPrice;
	}
}
