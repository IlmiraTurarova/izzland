package species.carnivore;

import animalHierarchy.Alive;
import animalHierarchy.AnimalType;
import animalHierarchy.Carnivore;
import iranai.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import species.dump.Dump;
import species.hervivore.*;

import java.util.ArrayList;
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
        List<Alive> alivezincell=new ArrayList<>();
        alivezincell.addAll(Dump.animalIsland[x][y].animals);
        ListIterator<Alive> iter = Dump.animalIsland[x][y].animals.listIterator();
        double weightBeginningToHunt=this.weight;
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
                        alivezincell.remove(alive);
                }
            }else if(alive instanceof Deer){
                if(number<=0.15){
                    eaten+= ((Deer) alive).getWeight();
                        alivezincell.remove(alive);
                }
            }else if(alive instanceof Rabbit){
                if(number<=0.60){
                    eaten+= ((Rabbit) alive).getWeight();
                        alivezincell.remove(alive);
                }
            }else if(alive instanceof Mouse){
                if(number<=0.80){
                    eaten+= ((Mouse) alive).getWeight();
                        alivezincell.remove(alive);
                }
            }else if(alive instanceof Goat){
                if(number<=0.60){
                    eaten+= ((Goat) alive).getWeight();
                        alivezincell.remove(alive);
                }
            }else if(alive instanceof Sheep){
                if(number<=0.70){
                    eaten+= ((Sheep) alive).getWeight();
                        alivezincell.remove(alive);
                }
            }else if(alive instanceof Boar){
                if(number<=0.15){
                    eaten+= ((Boar) alive).getWeight();
                        alivezincell.remove(alive);
                }
            }else if(alive instanceof Buffalo){
                if(number<=0.10){
                    eaten+= ((Buffalo) alive).getWeight();
                        alivezincell.remove(alive);
                }
            }else if(alive instanceof Duck){
                if(number<=0.40){
                    eaten+= ((Duck) alive).getWeight();
                        alivezincell.remove(alive);
                }
            }
            double weightEndOfHunt=this.weight+eaten;
            if(weightBeginningToHunt==weightEndOfHunt){
                this.weight=this.weight-(this.weight*0.01);
            }
            Dump.animalIsland[x][y].animals=alivezincell;
        }

    }

    @Override
    public synchronized void move() {
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
            if(type.name().equalsIgnoreCase("Wolf")){
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
        System.out.println("До размножения "+Dump.animalIsland[x][y].animals
                .stream().filter(animal -> animal instanceof Wolf).count());
        for (int i = 0; i < Dump.animalIsland[x][y].animals.size(); i++) {
            if(Dump.animalIsland[x][y].animals.get(i) ==this) {
                couple++;
            }
            if(Dump.animalIsland[x][y].animals.get(i) !=this
                    &&  Dump.animalIsland[x][y].animals.get(i) instanceof Wolf){
                couple++;
                break;
            }
        }
        if(couple==2){
            synchronized (Dump.animalIsland[x][y]) {
                Dump.animalIsland[x][y].animals.add(new Wolf());
            }
        }
        System.out.println("После размножения "+Dump.animalIsland[x][y].animals
                .stream().filter(animal -> animal instanceof Wolf).count());
    }

}
