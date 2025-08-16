package org.example.interf;

public class TransportUtils {
  
    public static float calculateAveragePrice(Transport vehicle) {
        float[] prices = vehicle.returnAllModelCoast();
        if (prices == null || prices.length == 0) {
            return 0.0f;
        }

        float sum = 0.0f;
        for (float price : prices) {
            sum += price;
        }
        return sum / prices.length;
    }

    public static void printAllModels(Transport vehicle) {
        String[] models = vehicle.returnAllModelNames();
        if (models == null || models.length == 0) {
            System.out.println("No models available");
            return;
        }

        System.out.println("Model list:");
        for (String model : models) {
            System.out.println("- " + model);
        }
    }

    public static void printAllPrices(Transport vehicle) {
        float[] prices = vehicle.returnAllModelCoast();
        if (prices == null || prices.length == 0) {
            System.out.println("No prices available");
            return;
        }

        System.out.println("Price list:");
        String[] modelNames = vehicle.returnAllModelNames();
        for (int i = 0; i < prices.length; i++) {
            String name = (modelNames != null && i < modelNames.length)
                    ? modelNames[i] : "Model #" + (i+1);
            System.out.printf("- %s: %.2f%n", name, prices[i]);
        }
    }
}
