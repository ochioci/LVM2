import java.util.ArrayList;
import java.util.UUID;

public class Volume {
    private String name;
    private int size;
    private UUID id;

    public Volume(String name, int size) {
        this.name = name;
        this.size = size;
        this.id = UUID.randomUUID();
    }

    public String getName() {return name;}
    public int getSize() {return size;}
    public UUID getId () {return id;}
}
