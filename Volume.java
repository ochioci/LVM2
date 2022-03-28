import java.util.ArrayList;
import java.util.UUID;

public class Volume {
    private int storageUsed = 0;
    private String name;
    private int size;
    private UUID id;
    private Server assignedServer;

    public Volume(String name, int size, Server assignedServer) {
        this.name = name;
        this.assignedServer = assignedServer;
        this.size = size;
        this.id = UUID.randomUUID();
    }

    public void setStorageUsed(int amt) {
        storageUsed = amt;
    }

    public int getStorageUsed(){return storageUsed;}

    public String getName() {return name;}
    public int getSize() {return size;}
    public UUID getUUID () {return id;}
}
