package test.integration;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import se.kth.iv1350.retailstore.integration.ExternalInventory;
import se.kth.iv1350.retailstore.model.Item;
import se.kth.iv1350.retailstore.util.ItemInformation;


import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

class ExternalInventoryTest {
    private ExternalInventory externalInventory;

    @BeforeEach
    void setUp() {
        externalInventory = new ExternalInventory();
    }

    @AfterEach
    void tearDown() {
        externalInventory = null;
    }

    @Test
    void testSearchExistingItem() {
        String eraser = "250";
        boolean expectedResult = true;
        Item existingItem = new Item(externalInventory.searchItem(eraser));
        boolean actualResult = existingItem.getIdentifier().equals(eraser);
        assertEquals(expectedResult,actualResult,"Wrong object returned");

    }

    @Test
    void testSearchNonExistingItem() {
        String unknownIdentifier = "255";
        assertNull(externalInventory.searchItem(unknownIdentifier), "Unknown item is not returned as null");
    }

    @Test
    void testUpdateInventory() {
        int indexOfItemInStock = 0;
        int noOfItemsToBuy = 3;
        ArrayList<ItemInformation> itemsInStock = externalInventory.getListOfItemsInStock();
        ArrayList<ItemInformation> itemToBuy = new ArrayList<>();
        itemToBuy.add(new ItemInformation(itemsInStock.get(indexOfItemInStock).getItemInList(),3));
        int quantityOfItemsInStock = itemsInStock.get(indexOfItemInStock).getQuantity();
        int expectedQuantityLeftInStock = quantityOfItemsInStock - noOfItemsToBuy;
        externalInventory.updateInventory(itemToBuy);
        int actualNoOfItemsInStock = externalInventory.getListOfItemsInStock().get(indexOfItemInStock).getQuantity();
        assertEquals(actualNoOfItemsInStock,expectedQuantityLeftInStock, "Inventory wasn't correctly updated");
    }
}