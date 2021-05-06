package se.kth.iv1350.retailstore.controller;

import se.kth.iv1350.retailstore.model.*;
import se.kth.iv1350.retailstore.integration.*;

/**
 * The program is controlled with Controller.
 * All calls is passed through here are from called from <Code>View</Code>
 */
public class Controller {
    private final ExternalAccount externalAccount;
    private final ExternalInventory externalInventory;
    private final CashRegister cashRegister;
    private final MemberCatalog memberCatalog;
    private final DiscountCatalogDTO discountCatalogDTO;
    private ReceiptPrinter receiptPrinter;
    private SaleInformation saleInformation;

    /**
     * Creates new instance.
     */
    public Controller(){
        this.externalAccount = new ExternalAccount();
        this.externalInventory = new ExternalInventory();
        this.cashRegister = new CashRegister();
        this.receiptPrinter = new ReceiptPrinter();
        this.memberCatalog = new MemberCatalog();
        this.discountCatalogDTO = new DiscountCatalogDTO();
        this.receiptPrinter = new ReceiptPrinter();
    }

    /**
     * Creates a new instance of a sale.
     */
    public void startNewSale(){
        this.saleInformation = new SaleInformation();
    }

    /**
     * Searches the inventory for the scanned barcode and adds the item to sale.
     * @param identifier The <Code>String</Code> to identify an existing object with.
     * @param quantity The number of objects to be added.
     * @return The updated <code>String</code> of sale to print.
     */
    public String addItem (String identifier, int quantity){
        Item item = externalInventory.searchItem(identifier);
        saleInformation.addItem(item, quantity);
        return saleInformation.toString();
    }

    /**
     * A discount request is made and confirms membership in <code>MemberCatalog</code>
     * and forwards the result and a <code>DiscountCatalogDTO</code> the sale.
     * @param memberID The <code>String</code> to search membership with.
     * @return  The updated <code>String</code> of sale to print.
     */
    public String discountRequest (String memberID){
        boolean isMember = memberCatalog.searchMember(memberID);
        saleInformation.applyDiscount(discountCatalogDTO, isMember);
        return saleInformation.toString();
    }

    /**
     * Adds the paid amount to <code>SaleInformation</code>
     * Sends the total cost of sale to <code>CashRegister</code>
     * Adds sale to <code>ExternalAccount</code>
     * Updates the goods left in stock.
     * @param amountPaid The amount paid from customer.
     * @return The updated <code>String</code> of sale to print.
     */
    public String addPay (double amountPaid){
        saleInformation.upDatePayment(amountPaid);
        cashRegister.updateRegister(saleInformation.getTotalCost());
        externalAccount.addSaleToAccount(saleInformation);
        externalInventory.updateInventory(saleInformation.getListOfItemsInSale());
        return saleInformation.toString();
    }

    /**
     * Fetches in <code>SaleInformation</code> the receipt to send for printing.
     */
    public void printReceipt (){
         receiptPrinter.printReceipt(saleInformation.printReceipt());
    }
}
