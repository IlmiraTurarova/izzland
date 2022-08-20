package species.plants;

import animalHierarchy.Alive;
import lombok.Data;

@Data
public class Plant implements Alive {
    private int weight=1;
    private int x;
    private int y;

}