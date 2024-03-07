import java.util.*;
import java.io.*;


public class Driver {


    //Assumptions made in test: All pallets are the same size, pallets can contain multiple different objects
    static ArrayList<Container> containers = new ArrayList<>();


    public static void main(String[] args) {
        Scanner scan = new Scanner(System.in);
        boolean run = true;
        List<Items> items = orderItems();
        Container.setItemList(items);
        makeContainers();
        System.out.println("[Order Optimizer]");


        do {
            System.out.println("What would you like to do?\n" +
                    "1) Enter a Custom Order\n" +
                    "2) Generate an Optimal Order\n" +
                    "3) Add a New Container\n" +
                    "4) Reset to Default Order\n" +
                    "5) Quit");
            int choice = scan.nextInt();
            switch (choice) {
                case 1:
                    boolean valid2 = true;
                    do {
                        System.out.println("What would you like to do?\n" +
                                "1) Input Quantity Directly\n" +
                                "2) Input Sales Forecast and Calculate");
                        int choice3 = scan.nextInt();
                        switch (choice3) {
                            case 1:
                                for (Items i : items) {
                                    System.out.println("How many hundreds of " + i.getName() + " would you like? ");
                                    i.setIdealPurchase((int) (scan.nextFloat() * 100));
                                }
                                valid2 = true;
                                break;


                            case 2:
                                for (Items i : items) {
                                    System.out.println("What is the sales forecast of " + i.getName() + "?");
                                    i.setSalesForecast(scan.nextFloat());
                                }
                                valid2 = true;
                                break;
                            default:
                                System.out.println("Please enter a valid choice.");
                                valid2 = false;
                                break;
                        }
                    } while (!valid2);
                    do {


                    } while (!valid2);


                    for (Items i : items) {
                        i.findPallets();
                    }
                case 2:
                    String type;
                    System.out.println("What are you doing?\n" +
                            "1) Transporting Items\n" +
                            "2) Storing Items");
                    int choice2 = scan.nextInt();
                    boolean valid = true;
                    boolean miles = false;
                    boolean buy = false;
                    float time = 0;
                    do {
                        switch (choice2) {
                            case 1:
                                System.out.println("How many miles are you traveling?");
                                time = scan.nextFloat();
                                type = "miles";
                                miles = true;
                                valid = true;
                                buy = false;
                                break;
                            case 2:
                                buy = true;
                                valid = true;
                                miles = false;
                                break;
                            default:
                                System.out.println("Sorry, that choice is not valid. Please try again.");
                                valid = false;
                                break;
                        }
                    } while (!valid);


//Index Numbers
                    boolean foundCon = false;
                    int leastSpace = 0;
                    int bestPrice = 0;
                    for (Container c : containers) {
                        c.findPallets();
                        if (c.canFit) { //If it can fit, figure out what has the least space and the best price
                            if (c.getRemainingArea() < containers.get(leastSpace).getRemainingArea()) {
                                if (miles) {
                                    if (c.pricePerMile != 0) {
                                        leastSpace = containers.indexOf(c);
                                        foundCon = true;
                                    }
                                } else {
                                    leastSpace = containers.indexOf(c);
                                    foundCon = true;
                                }


                                if (miles) {
                                    if ((c.pricePerMile < containers.get(bestPrice).pricePerMile) && c.pricePerMile != 0) {
                                        bestPrice = containers.indexOf(c);
                                        foundCon = true;
                                    }
                                } else {
                                    if (c.toBuy < containers.get(bestPrice).toBuy) {
                                        bestPrice = containers.indexOf(c);
                                        foundCon = true;
                                    }
                                }


                            }
                        }
                    }


                    float optPrice = 0;
                    float cheapPrice = 0;
                    Container optCon = containers.get(leastSpace);
                    Container cheapCon = containers.get(bestPrice);
                    if (!foundCon) {
                        optCon = biggestArea(miles);
                        cheapCon = biggestArea(miles);
                    }


                    if (miles) {
                        optPrice = optCon.getPricePerMile() * time;
                        cheapPrice = cheapCon.getPricePerMile() * time;
                    } else {
                        optPrice = optCon.getToBuy();
                        cheapPrice = cheapCon.getToBuy();
                    }


                    if (optPrice == 0) {
                        optCon = cheapCon;
                    }


                    if (cheapPrice - optPrice > 0) {
                        float percentFilled = 100 - ((optCon.remainingArea / optCon.getTotalArea()) * 100);
                        System.out.println(optCon.getName() + " is the most optimal. You will be using " + percentFilled + "% of the container.");
                        percentFilled = 100 - ((cheapCon.remainingArea / cheapCon.getTotalArea()) * 100);
                        float saved = cheapPrice - optPrice;
                        float savedPer = (cheapPrice / optPrice) * 100;


                        System.out.println(cheapCon.getName() + " is the cheapest choice. You save $" + saved + ", or " + savedPer + "% compared to the optimal choice. " + "However, only " + percentFilled + "% is filled.");
                    } else {
                        if (optCon.more3.equals("")) {
                            float percentFilled = 100 - ((optCon.remainingArea / optCon.getTotalArea()) * 100);
                            System.out.println("The optimal container is " + optCon.getName() + ". You will use " + percentFilled + "% of the container.\n" +
                                    "It will cost approximately $" + optPrice + ".");
                            if (optCon.leftArea != 0) {
                                Container secCon = calculateSecond(optCon.leftArea);
                                float newFull = 100 - ((optCon.leftArea / secCon.getTotalArea()) * 100);
                                float secPrice;
                                if (buy) {
                                    secPrice = secCon.toBuy;
                                } else {
                                    secPrice = secCon.getPricePerMile() * time;
                                }
                                System.out.println(secCon.name + " is the best container for the remaining items. It will be " + newFull + "% full.");
                                System.out.println("The second container will cost about $" + secPrice + ".");
                                System.out.println("The total shipping cost will be about $" + (secPrice + optPrice) + ".");
                            }


                            System.out.print(optCon.toString());
                        } else {
                            System.out.println(optCon.more3);
                        }
                    }




                    break;
                case 3:
                    System.out.println("Enter the following data about your container in feet/ pounds/ dollars. You may use decimals as needed.");
                    System.out.println("What is your container's:");
                    System.out.print("Name: ");
                    String name = scan.nextLine();
                    name = scan.nextLine();
                    System.out.print("Length: ");
                    float l = scan.nextFloat();
                    System.out.print("Height: ");
                    float h = scan.nextFloat();
                    System.out.print("Width: ");
                    float w = scan.nextFloat();
                    System.out.print("Maximum Weight: ");
                    float mw = scan.nextFloat();
                    System.out.print("Price per Mile: ");
                    float ppm = scan.nextFloat();
                    System.out.print("Price to Buy: ");
                    float tb = scan.nextFloat();
                    containers.add(new Container(l, h, w, mw, ppm, tb, name));
                    break;
                case 4:
                    System.out.println("Resetting to default order...");
                    items = orderItems();
                    break;
                case 5:
                    System.out.println("Thank you for shopping with us!");
                    run = false;
                    break;
                default:
                    System.out.println("That choice is invalid, please try again.");
                    break;
            }
        } while (run);
    }


    public static void makeContainers() {
//Trucks in pricePerMile, Containers in price per month
        Container boxCar = new Container(24, 8, 8, 26000, 2.41f, 135000, "Box Truck");
        Container shippingContainer40 = new Container(40, 8.5f, 8, 59200, 4, 4500, "Shipping Container (40 ft.)");
        Container shippingContainer20 = new Container(20, 8.5f, 8, 59200, 4, 1700, "Shipping Container (20 ft.)");
        Container shippingContainer10 = new Container(10, 8.5f, 8, 59200, 4, 1400, "Shipping Container (10 ft.)");
        Container storageUnit = new Container(20, 8, 10, 0, 0, 4000, "Storage Unit");


        containers.add(boxCar);
        containers.add(shippingContainer10);
        containers.add(shippingContainer20);
        containers.add(shippingContainer40);
        containers.add(storageUnit);
    }


    public static List<Items> orderItems() {
        try {
            List<Items> items = new ArrayList<>();
            File orderList = new File("C:\\Users\\cjohn\\Downloads\\Excel-of-products-for-the-Hackathon.csv");
            Scanner fileScan = new Scanner(orderList);
            fileScan.nextLine();


//Add each item to list
            while (fileScan.hasNextLine()) {
                String[] newItem = fileScan.nextLine().split(",");
                String name = newItem[0].toString();
                float width = Float.parseFloat(newItem[1]);
                float length = Float.parseFloat(newItem[2]);
                float height = Float.parseFloat(newItem[3]);
                float weight = Float.parseFloat(newItem[4]);
                String handling = newItem[5];
                boolean haz = Boolean.parseBoolean(newItem[6]);
                String hazDes = newItem[7];
                float sf = Float.parseFloat(newItem[8]);
                float price = Float.parseFloat(newItem[9]);
                items.add(new Items(name, height, length, width, weight, haz, handling, hazDes, sf, price));
            }


            Container.setItemList(items);
            return items;
        } catch (IOException e) {
            System.out.println("I'm sorry, your order could not be found. Please try again.");
            return null;
        }
    }


    public static Container calculateSecond(float leftArea) {
        Container bestCon = containers.get(0);
        float lastpercentFull = 100 - ((leftArea / bestCon.getTotalArea()) * 100);
        for (Container c : containers) {
            if (c.getTotalArea() < leftArea) {
                float percentFull = 100 - ((leftArea / c.getTotalArea()) * 100);
                if (percentFull >= lastpercentFull) {
                    lastpercentFull = percentFull;
                    bestCon = c;
                }


            }
        }
        return bestCon;
    }


    public static Container biggestArea(boolean miles) {
        Container biggest = containers.get(0);
        for (Container c : containers) {
            if (c.getTotalArea() > biggest.getTotalArea()) {
                if (miles) {
                    if (c.pricePerMile != 0) {
                        biggest = c;
                    }
                } else {
                    biggest = c;
                }
            }
        }
        return biggest;
    }
}
