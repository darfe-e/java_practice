package org.example.interf;

import org.example.mainAuto.Auto;

import java.io.*;

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

    public static void outputTransport (Transport transport, OutputStream out) throws IOException {
        try (DataOutputStream output = new DataOutputStream(out)){
            output.writeUTF(transport.getMark());
            int modelsSize = transport.getSizeOfModels();
            output.writeInt(modelsSize);

            String[] modelNames = transport.returnAllModelNames();
            float[] allCoasts = transport.returnAllModelCoast();

            for (int i = 0; i < modelsSize; i++){
                output.writeUTF(modelNames[i]);
                output.writeFloat(allCoasts[i]);
            }
        }
    }

    public static Transport inputTransport (InputStream in) throws IOException{
        try(DataInputStream input = new DataInputStream(in)){

            String mark = input.readUTF();
            int sizeOfMark = input.readInt();

            Transport transport = new Auto(mark, sizeOfMark);
            for (int i = 0; i < sizeOfMark; i++){
                String model = input.readUTF();
                float coast = input.readFloat();

                transport.addModel(model, coast);
            }
            return transport;
        }
    }

    public static void writeTransport (Transport transport, Writer out) throws IOException{
        try(PrintWriter writer = new PrintWriter(out)){
            writer.println(transport.getMark());

            int modelsSize = transport.getSizeOfModels();
            writer.println(modelsSize);

            String[] modelNames = transport.returnAllModelNames();
            float[] allCoasts = transport.returnAllModelCoast();

            for (int i = 0; i < modelsSize; i++){
                writer.print(modelNames[i]);
                writer.println(allCoasts[i]);
            }
        }
    }

    public static Transport readTransport(Reader in) throws IOException {
        BufferedReader reader = new BufferedReader(in);

        String mark = reader.readLine();
        if (mark == null) {
            throw new IOException("Неожиданный конец потока при чтении марки");
        }

        String countLine = reader.readLine();
        if (countLine == null) {
            throw new IOException("Неожиданный конец потока при чтении количества моделей");
        }

        int modelCount;
        try {
            modelCount = Integer.parseInt(countLine.trim());
        } catch (NumberFormatException e) {
            throw new IOException("Некорректный формат количества моделей: " + countLine, e);
        }

        Transport vehicle = new Auto(mark, 0); // Сначала создаем с 0 моделей

        for (int i = 0; i < modelCount; i++) {
            String modelLine = reader.readLine();
            if (modelLine == null) {
                throw new IOException("Неожиданный конец потока при чтении модели " + (i + 1));
            }

            String[] parts = modelLine.split(" ", 2);
            if (parts.length != 2) {
                throw new IOException("Некорректный формат данных для модели: " + modelLine);
            }

            String modelName = parts[0];
            float modelPrice;
            try {
                modelPrice = Float.parseFloat(parts[1].trim());
            } catch (NumberFormatException e) {
                throw new IOException("Некорректный формат цены для модели '" + modelName + "': " + parts[1], e);
            }

            vehicle.addModel(modelName, modelPrice);
        }

        return vehicle;
    }
}
