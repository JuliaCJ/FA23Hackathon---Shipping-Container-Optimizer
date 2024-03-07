import java.util.*;
public class Items {
    Scanner scan = new Scanner (System.in);
    private String name;
    private float height;
    private float width;
    private float length;
    private int maxStackHeight;
    private float weight;
    private boolean hazard;
    private String handling;
    private float salesForecast;
    private int pallets;
    private float price;
    private int idealPurchase;
    private int maxStackWidth;
    private int maxStackLength;

    private int remainderPurchase;
    private float area;

    private String hazDes;
    private int remainderItems;

    public Items(){
        name = "";
        height = 0;
        length = 0;
        width = 0;
        hazard = false;
        handling = "";
        salesForecast = 0;
        price = 0;
        findArea();
        findIdealPurchase();

    }

    public Items(String n, float h, float l, float w, float wht, boolean haz, String hand, String hd, float sf, float p) {
        name = n;
        height = h / 12;
        length = l / 12;
        width = w / 12;
        weight = wht;
        hazard = haz;
        hazDes = hand;
        salesForecast = sf;
        price = p;
        findArea();
        findIdealPurchase();
//findPallets();
    }


    //Getters and Setters
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


    public float getLength() {
        return length;
    }


    public void setLength(float length) {
        this.length = length;
    }


    public int getMaxStackHeight() {
        return maxStackHeight;
    }


    public void setMaxStackHeight(int maxStackHeight) {
        this.maxStackHeight = maxStackHeight;
    }


    public float getWeight() {
        return weight;
    }


    public void setWeight(float weight) {
        this.weight = weight;
    }


    public boolean isHazard() {
        return hazard;
    }


    public void setHazard(boolean hazard) {
        this.hazard = hazard;
    }


    public void setHazDes() {
        System.out.println("What is the hazard description?");
        hazDes = scan.nextLine();
    }


    public String getHandling() {
        return handling;
    }


    public void setHandling(String handling) {
        this.handling = handling;
    }


    public float getSalesForecast() {
        return salesForecast;
    }


    public void setSalesForecast(float salesForecast) {
        this.salesForecast = salesForecast;
    }


    public int getPallets() {
        return pallets;
    }


    public void setPallets(int pallets) {
        this.pallets = pallets;
    }


    public float getPrice() {
        return price;
    }


    public void setPrice(float price) {
        this.price = price;
    }


    public int getIdealPurchase() {
        return idealPurchase;
    }


    public void setIdealPurchase(int idealPurchase) {
        this.idealPurchase = idealPurchase;
    }


    public int getMaxStackWidth() {
        return maxStackWidth;
    }


    public void setMaxStackWidth(int maxStackWidth) {
        this.maxStackWidth = maxStackWidth;
    }


    public int getMaxStackLength() {
        return maxStackLength;
    }


    public void setMaxStackLength(int maxStackLength) {
        this.maxStackLength = maxStackLength;
    }


    public int getRemainderPurchase() {
        return remainderPurchase;
    }


    public void setRemainderPurchase(int remainderPurchase) {
        this.remainderPurchase = remainderPurchase;
    }


    public float getArea() {
        return area;
    }


    public void setArea(float area) {
        this.area = area;
    }


    public String getName() {
        return name;
    }


    //Calculate ideal purchase Amount
    public void findIdealPurchase() {
//idealPurchase = total units to purchase
        idealPurchase = (int)(salesForecast/ price);
    }


    public void findArea() {
        area = length * width * height;
    }
    //Convert ideal purchase to pallets
    float totalWeight = 0;
    public void findPallets() {
        maxStackHeight = (int)(5/height);
        maxStackLength = (int)(2/length);
        maxStackWidth = (int)(3.33/width);


        int totalUnits = maxStackHeight * maxStackLength * maxStackWidth;


        totalWeight = totalUnits * weight;


//Find how many units per pallet
        while (totalWeight > 4600 || totalUnits > (idealPurchase + (maxStackLength + maxStackWidth))) {
            maxStackHeight--;
            totalUnits = maxStackHeight * maxStackLength * maxStackWidth;
            totalWeight = totalUnits * weight;
        }
//totalUnits = how many per pallet


        if (pallets != 0) {
            pallets = idealPurchase / totalUnits;
            remainderPurchase = idealPurchase - totalUnits;
        }
        else {
            pallets = 0;
            remainderPurchase = idealPurchase;
        }


    }


    //Remaining areas worth of purchases
    public float getRemainderArea() {
        return remainderPurchase * area;
    }


    public int getRemainderItems() {
        return remainderPurchase;
    }


    @Override
    public String toString() {
        String warnings = "";
        if (hazard) {
            warnings = name + " has the following warnings: \n";
            String[] array = hazDes.split("/");
            for (String s : array) {
                warnings += s + "\n";
            }
            if (handling != null && handling != "") {
                warnings += handling + "\n";
            } else {
                warnings += "\n";
            }
        }
        warnings.replace("\"", "");
        return warnings;
    }
}
