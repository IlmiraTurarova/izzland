package species.hervivore;


import animalHierarchy.Alive;
import animalHierarchy.Animal;
import animalHierarchy.AnimalType;
import animalHierarchy.Herbivore;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import species.dump.Dump;
import species.plants.Plant;

import java.util.List;
import java.util.ListIterator;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Sheep extends Herbivore implements Animal {
    private int x;
    private int y;
    private double weight;
    @Override
    public void eat() {
        double tillFull = 0;
        double eaten = 0;
        for (AnimalType type : Dump.species) {
            if (type.name().equalsIgnoreCase("Sheep")) {
                tillFull = type.getEatTillFull();
            }
        }

        ListIterator<Alive> iter = Dump.animalIsland[x][y].animals.listIterator();
        while(iter.hasNext()) {
            Alive alive=iter.next();
            if (eaten >= tillFull) {
                this.weight += tillFull;
                break;
            } else {
                this.weight += eaten;
            }
            if (alive instanceof Plant) {
                eaten += ((Plant) alive).getWeight();
                Dump.animalIsland[x][y].animals.remove(alive);
            }
        }
    }

    @Override
    public void move() {
        int oldx=x;
        int oldy=y;
        int speed=0;
        for(AnimalType type: Dump.species){
            if(type.name().equalsIgnoreCase("Sheep")){
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
            Dump.animalIsland[newx][newy].animals.add(this);
            Dump.animalIsland[oldx][oldy].animals.removeIf(x->x==this);


        }catch(Exception e){
            move();
        }
    }

    @Override
    public void beEaten() {
       List<Alive> foodAndRivals = Dump.animalIsland[x][y].animals;
        if(!foodAndRivals.contains(this)){
            System.out.println(this.getClass().getSimpleName()+" Eaten");
        }
    }

    @Override
    public void starveAndDie() {

    }
}