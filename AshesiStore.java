package tasks;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.PrintWriter;
import java.util.*;

public class AshesiStore {
	private PrintWriter stock = null;
	private PrintWriter backUp = null;
	private String name;
	
	AshesiStore(String storeName){
		
		// Create the stock and back-up files
		try {
			stock = new PrintWriter(new FileOutputStream("essentials_stock.txt", true));
			backUp = new PrintWriter(new FileOutputStream("backup_essentials_stock.txt", true));
		} catch (FileNotFoundException fnfe) {
			fnfe.getMessage();
		}
		
		this.name = storeName;
	}
	
	String getName() {
		return this.name;
	}
	
	PrintWriter getFile() {
		return this.stock;
	}
	
	PrintWriter getBackUp() {
		return this.backUp;
	}
	
	
	class Product {
		private String name;
		private double price;
		private int quantity;
		
		Product(String n, double p, int q) {
			this.name = n;
			this.price = p;
			this.quantity = q;
			
		}
		
		public String getName() {
			return this.name;
		}
		
		public double getPrice() {
			return this.price;
		}
		
		public int getQuantity() {
			return this.quantity;
		}
		
		public void updateQuantity(int q) {
			this.quantity += q;
		}
	}
	
	/**
	 * This method allows an end-user to enter a product,
	 * price and number of units into the store's database
	 * which is a file. The stock is simultaneously backed up.
	 * 
	 * @param N/A
	 * @return N/A
	 */
	void updateStock() {
		Scanner in = new Scanner(System.in);
		String name = "";
		double price = 0;
		int quantity = 0;
		
		// Input product details
		try {
			System.out.print("Enter product name: ");
			name = in.nextLine();
			System.out.print("Enter product price (GHC): ");
			price = in.nextDouble();
			System.out.print("Enter number of units: ");
			quantity = in.nextInt();
		} catch (Exception e) {
			System.out.println("Invalid input.");
		}
			
		// Create new product
		Product newProduct = new Product(name, price, quantity);
			
		// Add item to stock
		this.addItemToStock(this.getFile(), this.getBackUp(), newProduct);
		
		System.out.println();
		
		// Close instance of the Scanner class
		in.close();
	}
	
	
	/**
	 * This method adds a product to the Stock file as well
	 * as backs up automatically to the Back-up file.
	 * 
	 * @param writer
	 * @param writer2
	 * @param product
	 * 
	 * @return N/A
	 */
	void addItemToStock(PrintWriter writer, PrintWriter writer2, Product product) {
		
		// Record product details in the Stock file
		writer.print(product.getName() + " " + product.getQuantity() + " GHC " + product.getPrice() + "\n");
		System.out.println("PRODUCT ADDED: " + product.getName() + " " + product.getQuantity() + " GHC " + product.getPrice());
		
		// Write immediately to the Back-Up file
		writer2.print(product.getName() + " " + product.getQuantity() + " GHC " + product.getPrice() + "\n");
		
		// Close both files to save
		writer.close();
		writer2.close();
	}
	
	
	/**
	 * This method reads from the Stock file and displays
	 * its content on the screen. 
	 * 
	 * @param N/A
	 * @return N/A
	 */
	void viewStock() {
		Calendar calendar = Calendar.getInstance();
		
		System.out.println("ESSENTIALS STORE STOCK");
		
		// Display current date and time
		System.out.println("Date: " + calendar.getTime());
		
		// Display the content of the Stocks file
		try {
			File file = new File("essentials_stock.txt"); 
			Scanner sc = new Scanner(file); 
			while (sc.hasNextLine()) 
				System.out.println(sc.nextLine()); 
			sc.close();
		} catch (FileNotFoundException fnfe) {
			fnfe.getMessage();
		}
			
	}
	
	
	void welcome() {
		System.out.println("Welcome to " + this.getName());
		System.out.println("--------------------------------------------------------------------------------------");
		System.out.println();
	}
	
	
	public static void main(String[] args) {
		AshesiStore essentials = new AshesiStore("Essentials");
		
		essentials.welcome();
		essentials.updateStock();
		
		essentials.viewStock();
	}
}
