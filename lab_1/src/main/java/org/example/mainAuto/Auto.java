package org.example.mainAuto;

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

    public void changeNameOfModel(String prevName, String newName){
        for(Model model: arrayOfModels){
            if (model.getName().equals(prevName)) model.setName(newName);
        }
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

    public void deleteByNameAndCoast (String name, float coast){
        for(int i = 0; i < arrayOfModels.length; i++){
            if (arrayOfModels[i].getName().equals(name) && (Float.compare(arrayOfModels[i].getCoast(), coast) == 0)) {
                Model[] newModels = new Model[SizeOfModels - 1];
                if (i == 0){
                    System.arraycopy(arrayOfModels, 1, newModels, 0, --SizeOfModels);
                    arrayOfModels = newModels;
                }
                else if (i == arrayOfModels.length - 1){
                    System.arraycopy(arrayOfModels, 0, newModels, 0, --SizeOfModels);
                    arrayOfModels = newModels;
                }
                else {
                    System.arraycopy(arrayOfModels, 0, newModels, 0, i);
                    System.arraycopy(arrayOfModels, i + 1, newModels, i, --SizeOfModels - i);
                    arrayOfModels = newModels;
                }
                return;
            }
        }
    }

    public int getSizeOfModels() {
        return SizeOfModels;
    }

    public void changeCostByName (String name, float newCost){
        for(Model model: arrayOfModels){
            if (model.getName().equals(name)) model.setCoast(newCost);
        }
    }

    public float getCoastByName (String name){
        for(Model model: arrayOfModels){
            if (model.getName().equals(name)) return model.getCoast();
        }
        System.out.println("There is no such model");
        return 0;
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
