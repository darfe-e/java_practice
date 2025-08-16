package org.example.mainAuto;

import org.example.exceptions.ModelPriceOutOfBoundsException;
import org.example.exceptions.NoSuchModelNameException;

import java.util.Arrays;

public class Auto {
    private String mark;
    private int SizeOfModels;
    private Model[] arrayOfModels;

    public Auto (String mark, int SizeOfModels){
        this.mark = mark;
        this.SizeOfModels = SizeOfModels;
        this.arrayOfModels = new Model[SizeOfModels];
    }

    public String getMark() {
        return mark;
    }

    public void setMark(String mark) {
        this.mark = mark;
    }

    public void addModel(String name, float coast){
        if (arrayOfModels.length == 0 || arrayOfModels[arrayOfModels.length - 1] != null){
            arrayOfModels = Arrays.copyOf(arrayOfModels, ++SizeOfModels);
        }
        Model model = new Model(name, coast);
        for (int i = 0; i < arrayOfModels.length; i++) {
            if (arrayOfModels[i] == null) {
                arrayOfModels[i] = model;
                break;
            }
        }
    }

    public void changeNameOfModel(String prevName, String newName) throws NoSuchModelNameException {
        if (arrayOfModels == null) {
            throw new NoSuchModelNameException(prevName);
        }
        for (Model model : arrayOfModels) {
            if (model != null && model.getName().equals(prevName)) {
                model.setName(newName);
                return;
            }
        }
        throw new NoSuchModelNameException(prevName);
    }

    public String[] returnAllModelNames (){
        String[] ModelsNames = new String[SizeOfModels];
        for (int i = 0; i < arrayOfModels.length; i++){
            ModelsNames[i] = arrayOfModels[i].getName();
        }
        return ModelsNames;
    }

    public float[] returnAllModelCoast (){
        float[] ModelsCoast = new float[SizeOfModels];
        for (int i = 0; i < arrayOfModels.length; i++){
            ModelsCoast[i] = arrayOfModels[i].getCoast();
        }
        return ModelsCoast;
    }

    public void deleteByNameAndCoast(String name, float coast) throws NoSuchModelNameException {
        if (arrayOfModels == null || arrayOfModels.length == 0) {
            throw new NoSuchModelNameException(name);
        }

        for (int i = 0; i < arrayOfModels.length; i++) {
            if (arrayOfModels[i] == null) {
                continue; // Пропускаем null элементы
            }

            boolean nameMatches = arrayOfModels[i].getName().equals(name);
            boolean coastMatches = Float.compare(arrayOfModels[i].getCoast(), coast) == 0;

            if (nameMatches && coastMatches) {
                Model[] newModels = new Model[arrayOfModels.length - 1];
                System.arraycopy(arrayOfModels, 0, newModels, 0, i);
                System.arraycopy(arrayOfModels, i + 1, newModels, i, arrayOfModels.length - i - 1);

                arrayOfModels = newModels;
                SizeOfModels--;
                return;
            }
            else if (nameMatches) {
                System.out.println("\nЦена не соответствует указанному имени модели.");
                return;
            }
        }

        throw new NoSuchModelNameException(name);
    }

    public int getSizeOfModels() {
        return SizeOfModels;
    }

    public void changeCostByName(String name, float newCost)
            throws NoSuchModelNameException, ModelPriceOutOfBoundsException {

        if (newCost <= 0) {
            throw new ModelPriceOutOfBoundsException(newCost);
        }

        if (arrayOfModels == null || arrayOfModels.length == 0) {
            throw new NoSuchModelNameException(name);
        }

        for (Model model : arrayOfModels) {
            if (model != null && model.getName().equals(name)) {
                model.setCoast(newCost);
                return;
            }
        }

        throw new NoSuchModelNameException(name);
    }

    public float getCoastByName(String name) throws NoSuchModelNameException {
        if (arrayOfModels == null || arrayOfModels.length == 0) {
            throw new NoSuchModelNameException(name);
        }

        for (Model model : arrayOfModels) {
            if (model != null && model.getName().equals(name)) {
                return model.getCoast();
            }
        }
        throw new NoSuchModelNameException(name);
    }

    private class Model {
        private String name;
        private float coast;

         Model(String name, float coast){
            this.coast = coast;
            this.name = name;
        }

        void setName(String name) {
            this.name = name;
        }

        void setCoast(float coast) {
            this.coast = coast;
        }

        public float getCoast() {
            return coast;
        }

        public String getName() {
            return name;
        }
    }
}
