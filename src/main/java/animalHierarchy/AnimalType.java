package animalHierarchy;

public enum AnimalType {
    WOLF(50,30,3,8),
    BOA(15,30,1,3),
    FOX(8,30,2,2),
    BEAR(500,5,2,80),
    EAGLE(6,20,3,1),
    HORSE(400,20,4,60),
    DEER(300,20,4,50),
    RABBIT(2,150,2,0.45),
    MOUSE(0.05,500,1,0.01),
    GOAT(60,140,3,10),
    SHEEP(70,140,3,15),
    BOAR(400,50,2,50),
    BUFFALO(700,10,3,100),
    DUCK(1,200,4,0.15),
    CATERPILLAR(0.01,1000,0,0);

    private double weight;
    private int maxNumber;
    private int speed;
    private double eatTillFull;


    AnimalType(double weight, int maxNumber, int speed, double eatTillFull) {
        this.weight = weight;
        this.maxNumber = maxNumber;
        this.speed = speed;
        this.eatTillFull = eatTillFull;
    }


    public double getWeight() {
        return weight;
    }

    public void setWeight(double weight) {
        this.weight = weight;
    }

    public int getMaxNumber() {
        return maxNumber;
    }

    public void setMaxNumber(int maxNumber) {
        this.maxNumber = maxNumber;
    }

    public int getSpeed() {
        return speed;
    }

    public void setSpeed(int speed) {
        this.speed = speed;
    }

    public double getEatTillFull() {
        return eatTillFull;
    }

    public void setEatTillFull(double eatTillFull) {
        this.eatTillFull = eatTillFull;
    }
}

