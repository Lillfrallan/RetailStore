package se.kth.iv1350.retailstore.view;

import se.kth.iv1350.retailstore.controller.Controller;

public class View {
    private final Controller controller;

    /**
     * Creates new instance of View.
     * @param controller The controller that is used for all operations.
     */
    public View (Controller controller){
        this.controller = controller;
    }

    /**
     * Represents how the interaction is made with the program.
     */
    public void execution () {
        controller.startNewSale();
        System.out.println(controller.addItem("100", 1));
        System.out.println(controller.addItem("150", 2));
        System.out.println(controller.addItem("200", 3));
        System.out.println(controller.addItem("150", 4));
        System.out.println(controller.addItem("250", 2));
        System.out.println(controller.addItem("300", 5));
        System.out.println(controller.discountRequest("9007182743"));
        System.out.println(controller.addPay(500));
        controller.printReceipt();
    }

}
