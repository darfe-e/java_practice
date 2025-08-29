package org.example.interf;

import org.example.exceptions.ModelPriceOutOfBoundsException;
import org.example.exceptions.NoSuchModelNameException;

public interface Transport {
    String getMark();


    void setMark(String mark);
    void addModel(String name, float coast);
    void changeNameOfModel(String prevName, String newName) throws NoSuchModelNameException;

    String[] returnAllModelNames ();
    float[] returnAllModelCoast ();

    void deleteByNameAndCoast(String name, float coast) throws NoSuchModelNameException;
    int getSizeOfModels();
    void changeCostByName(String name, float newCost) throws NoSuchModelNameException, ModelPriceOutOfBoundsException;
    float getCoastByName(String name) throws NoSuchModelNameException;
}
