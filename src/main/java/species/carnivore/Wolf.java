package species.carnivore;

import animalHierarchy.Alive;
import animalHierarchy.AnimalType;
import animalHierarchy.Carnivore;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import species.dump.Dump;
import species.hervivore.*;

import java.util.List;
import java.util.ListIterator;
import java.util.concurrent.ThreadLocalRandom;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Wolf extends Carnivore {
    static ThreadLocalRandom randomN = ThreadLocalRandom.current();
    private int x;
    private int y;
    private double weight;
    @Override
    public void eat() {
        double tillFull=0;
        double eaten=0;
        for(AnimalType type: Dump.species){
            if(type.name().equalsIgnoreCase("Wolf")){
                tillFull=type.getEatTillFull();
            }
        }

        Double number=randomN.nextDouble();
        ListIterator<Alive> iter = Dump.animalIsland[x][y].animals.listIterator();
        while(iter.hasNext()) {
            Alive alive=iter.next();
            if(eaten>=tillFull){
                this.weight+=tillFull;
                break;
            }else{
                this.weight+=eaten;
            }
            if(alive instanceof Horse){
                if(number<=0.10){
                    eaten+= ((Horse) alive).getWeight();
                    Dump.animalIsland[x][y].animals.remove(alive);
                }
            }else if(alive instanceof Deer){
                if(number<=0.15){
                    eaten+= ((Deer) alive).getWeight();
                    Dump.animalIsland[x][y].animals.remove(alive);
                }
            }else if(alive instanceof Rabbit){
                if(number<=0.60){
                    eaten+= ((Rabbit) alive).getWeight();
                    Dump.animalIsland[x][y].animals.remove(alive);
                }
            }else if(alive instanceof Mouse){
                if(number<=0.80){
                    eaten+= ((Mouse) alive).getWeight();
                    Dump.animalIsland[x][y].animals.remove(alive);
                }
            }else if(alive instanceof Goat){
                if(number<=0.60){
                    eaten+= ((Goat) alive).getWeight();
                    Dump.animalIsland[x][y].animals.remove(alive);
                }
            }else if(alive instanceof Sheep){
                if(number<=0.70){
                    eaten+= ((Sheep) alive).getWeight();
                    Dump.animalIsland[x][y].animals.remove(alive);
                }
            }else if(alive instanceof Boar){
                if(number<=0.15){
                    eaten+= ((Boar) alive).getWeight();
                    Dump.animalIsland[x][y].animals.remove(alive);
                }
            }else if(alive instanceof Buffalo){
                if(number<=0.10){
                    eaten+= ((Buffalo) alive).getWeight();
                    Dump.animalIsland[x][y].animals.remove(alive);
                }
            }else if(alive instanceof Duck){
                if(number<=0.40){
                    eaten+= ((Duck) alive).getWeight();
                    Dump.animalIsland[x][y].animals.remove(alive);
                }
            }
        }

    }

    @Override
    public void move() {
        int oldx=x;
        int oldy=y;
        int speed=0;
        for(AnimalType type: Dump.species){
            if(type.name().equalsIgnoreCase("Wolf")){
                speed=type.getSpeed();
            }
        }
        int random=(int)(Math.random()*4);
        try{
            if(random==0) {
                x += speed;
                if(x>Dump.animalIsland.length){
                    x -= speed;
                    throw new Exception();
                }
                if (x < 0) {
                    x -= speed;
                    throw new Exception();
                }
            }else if(random==1) {
                y += speed;
                if(y>Dump.animalIsland[0].length){
                    y -= speed;
                    throw new Exception();
                }
                if (y < 0) {
                    y -= speed;
                    throw new Exception();
                }
            }else if(random==2){

                x-=speed;
                if(x>Dump.animalIsland.length){
                    x += speed;
                    throw new Exception();
                }
                if(x<0){
                    x+=speed;
                    throw new Exception();
                }
            }else{
                y-=speed;
                if(y>Dump.animalIsland[0].length){
                    y+=speed;
                    throw new Exception();
                }
                if(y<0){
                    y+=speed;
                    throw new Exception();
                }
            }
            int newx=x;
            int newy=y;

            //Dump.animalIsland[oldx][oldy].animals.remove(this);
            synchronized (Dump.animalIsland[oldx][oldy]) {
                Dump.animalIsland[oldx][oldy].animals.removeIf(x -> x == this);
            }
            synchronized (Dump.animalIsland[newx][newy]) {
                Dump.animalIsland[newx][newy].animals.add(this);
            }


        }catch(Exception e){
            move();
        }
    }

    @Override
    public void starveAndDie() {

    }

    @Override
    public void beEaten() {
        List<Alive> foodAndRivals = Dump.animalIsland[x][y].animals;
        if(!foodAndRivals.contains(this)){
            System.out.println(this.getClass().getSimpleName()+" Eaten");
        }
    }
}
