import java.util.ArrayList;
import java.util.UUID;

public class PhysicalVolume extends Volume{
    private Drive assignedDrive;
    public PhysicalVolume (String name, int size, Drive drive) {
        super(name, size);
        drive.setPhysicalVolume(this);
        this.assignedDrive = drive;
    }
    public Drive getAssignedDrive() {return assignedDrive;}
}
