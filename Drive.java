import java.util.ArrayList;
import java.util.UUID;

public class Drive extends Volume{
    private PhysicalVolume assignedVolume = null;
    public Drive(String name, int size, Server s) {
        super(name, size, s);
    }
    public void setPhysicalVolume(PhysicalVolume PV) {
        this.assignedVolume = PV;
    }

    public PhysicalVolume getPhysicalVolume(){return assignedVolume;}
}
