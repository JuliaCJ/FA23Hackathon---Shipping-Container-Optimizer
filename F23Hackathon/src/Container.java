import java.util.*;
import java.io.*;

public class Container {
    //List attributes
    float length;
    float height;
    float width;
    float maxWeight;
    float pricePerMile;
    int maxPallets;
    float remainingArea;


    float totalArea;


    float areaUsed;


    static List<Items> itemList;


    static int neededPallets;


    boolean canFit;


    String name;
    float toBuy;


    float leftArea = 0;


    String more3 = "";
    int leftItems = 0;




    public Container() {
        length = 0;
        height = 0;
        width = 0;
        maxWeight = 0;
        pricePerMile = 0;
        toBuy = 0;
        String name = "";
        findArea();
        countPallets();
        findMaxPallets();
        findPallets();




    }


    public Container(float l, float h, float w, float mw, float ppm, float tb, String n) {
        length = l;
        height = h;
        width = w;
        maxWeight = mw;
        pricePerMile = ppm;
        toBuy = tb;
        name = n;


        String name = n;
        findArea();
        findMaxPallets();
        countPallets();
        findPallets();
    }


    public void findMaxPallets() {


        int maxStackHeight = (int)(height/5);
        int maxStackLength = (int)(length/2);
        int maxStackWidth = (int)(width/3.33);


        int totalPallets = maxStackHeight * maxStackLength * maxStackWidth;




        while (totalPallets * 4600 > maxWeight && maxWeight != 0) {
            totalPallets--;
        }
        maxPallets = totalPallets;
    }
    public float getToBuy() {
        return toBuy;
    }
    public float getLength() {
        return length;
    }


    public void setLength(float length) {
        this.length = length;
    }


    public float getHeight() {
        return height;
    }


    public void setHeight(float height) {
        this.height = height;
    }


    public float getWidth() {
        return width;
    }


    public void setWidth(float width) {
        this.width = width;
    }


    public float getMaxWeight() {
        return maxWeight;
    }


    public void setMaxWeight(float maxWeight) {
        this.maxWeight = maxWeight;
    }


    public float getPricePerMile() {
        return pricePerMile;
    }


    public void setPricePerMile(float pricePerMile) {
        this.pricePerMile = pricePerMile;
    }


    public int getMaxPallets() {
        return maxPallets;
    }


    public void setMaxPallets(int maxPallets) {
        this.maxPallets = maxPallets;
    }


    public float getRemainingArea() {
        return remainingArea;
    }


    public void setRemainingArea(float remainingArea) {
        this.remainingArea = remainingArea;
    }


    public void setTotalArea(float area) {
        totalArea = area;
    }


    public float getTotalArea(){
        return totalArea;
    }


    public float getAreaUsed() {
        return areaUsed;
    }


    public void setAreaUsed(float area) {
        areaUsed = area;
    }


    public static void setItemList(List<Items> il) {
        itemList = il;
    }


    public String getName() {
        return name;
    }
    public void findArea() {
        totalArea = length * width * height;
    }


    public void findPallets() {
        int totalPallets = maxPallets;
        float palletArea = 5f * 2f * 3.33f;


        countPallets();
        float totalRemainderItemArea = 0;
        float itemWeight = 0;
        for (Items i : itemList) {
            totalRemainderItemArea += i.getRemainderArea();
            itemWeight += i.getWeight() * (i.getRemainderArea()/i.getArea());
        }


        areaUsed = palletArea * totalPallets;
        remainingArea = totalArea - areaUsed;


        if (totalRemainderItemArea > remainingArea && ((palletWeight + itemWeight) > maxWeight && maxWeight !=0)) {
            canFit = false;
        } else {
            remainingArea = remainingArea - totalRemainderItemArea;
            canFit = true;
            for (Items i : itemList) {
                leftItems += i.getRemainderArea() / i.getArea();
            }
        }


        if (!canFit) {
            int extraItems = 0;
            totalRemainderItemArea = 0;
            List<Float> extraAreas = new ArrayList<>();
            int size = itemList.size() - 1;
            extraAreas.add(itemList.get(size - 1).getRemainderArea());
            int j = 1;
            while (!canFit) {
                for (int i = 0; i < size; i++) {
                    totalRemainderItemArea += itemList.get(i).getRemainderArea();
                    extraItems += itemList.get(i).getRemainderArea() / itemList.get(i).getArea();
                }
                if (totalRemainderItemArea < remainingArea) {
                    canFit = true;
                    leftArea = 0;
                    for (Float a : extraAreas) {
                        leftArea += a;
                    }
//System.out.println("A second container will be needed for the remaining " + extraItems + " items, or " + leftArea + " cubic feet.");
                } else {
                    if (size-1-j < 0) {
                        more3 = "More than 2 containers are required. Please enter a smaller order.";
                        return;
                    }
                    extraAreas.add(itemList.get(size - 1 - j).getRemainderArea());
                    j++;
                }
            }


        }
    }
    float palletWeight = 0;
    public void countPallets() {
        for (Items i : itemList) {
            neededPallets += i.getPallets();
            palletWeight += i.getPallets() * i.totalWeight;
        }
    }


    @Override
    public String toString(){
        String print ="You can fill this container with up to " + maxPallets+ " pallets.\n";
        if (leftItems != 0) {
            print += "The remaining " + leftItems + " items should be stacked outside of pallets.\n";
        }
        print += "You will need the following amounts of each items:\n";
        for (Items i : itemList) {
            print += i.getName() +": " + i.getIdealPurchase() + " units\n";
        }
        print += "\n";
        print += "Please note the following warnings:\n";
        for (Items i: itemList) {
            print += i.toString();
        }


        return print;
    }




}

