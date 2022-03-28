import java.util.ArrayList;
import java.util.UUID;

public class Drive extends Volume{
    private PhysicalVolume assignedVolume;
    public Drive(String name, int size) {
        super(name, size);
    }
    public void setPhysicalVolume(PhysicalVolume PV) {
        this.assignedVolume = PV;
    }

    public PhysicalVolume getPhysicalVolume(){return assignedVolume;}
}
