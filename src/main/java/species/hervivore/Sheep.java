package species.hervivore;


import animalHierarchy.Alive;
import animalHierarchy.Animal;
import animalHierarchy.AnimalType;
import animalHierarchy.Herbivore;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import species.carnivore.Wolf;
import species.dump.Dump;
import species.plants.Plant;

import java.util.ArrayList;
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
    public synchronized void eat() {
        double tillFull = 0;
        double eaten = 0;
        for (AnimalType type : Dump.species) {
            if (type.name().equalsIgnoreCase("Sheep")) {
                tillFull = type.getEatTillFull();
            }
        }
        List<Alive> alivezincell=new ArrayList<>();
        alivezincell.addAll(Dump.animalIsland[x][y].animals);
        ListIterator<Alive> iter = Dump.animalIsland[x][y].animals.listIterator();
        while(iter.hasNext()) {
            Alive alive=iter.next();
            if (eaten >= tillFull) {
                this.weight += tillFull;
                break;
            } else {
                this.weight += eaten;
            }
            double weightBeginningToHunt=this.weight;
            if (alive instanceof Plant) {
                eaten += ((Plant) alive).getWeight();
                    alivezincell.remove(alive);
            }
            double weightEndOfHunt=this.weight+eaten;
            if(weightBeginningToHunt==weightEndOfHunt){
                this.weight=this.weight-(this.weight*0.01);
            }
            Dump.animalIsland[x][y].animals= alivezincell;
        }
    }

    @Override
    public synchronized void move() {
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
        Double idealWeight=0.0;
        for(AnimalType type: Dump.species){
            if(type.name().equalsIgnoreCase("Sheep")){
                idealWeight=type.getEatTillFull();
            }
        }
        if(this.weight<(idealWeight*0.5)){
            synchronized (Dump.animalIsland[x][y]) {
                Dump.animalIsland[x][y].animals.removeIf(x -> x == this);
            }
        }
    }

    public void multiply(){
        int couple=0;
        for (int i = 0; i < Dump.animalIsland[x][y].animals.size(); i++) {
            if(Dump.animalIsland[x][y].animals.get(i) ==this) {
                couple++;
            }
            if(Dump.animalIsland[x][y].animals.get(i) !=this
                    &&  Dump.animalIsland[x][y].animals.get(i) instanceof Sheep){
                couple++;
            }
        }
        if(couple==2){
            synchronized (Dump.animalIsland[x][y]) {
                Dump.animalIsland[x][y].animals.add(this);
            }
        }
    }

}