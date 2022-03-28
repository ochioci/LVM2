import java.util.ArrayList;
import java.util.UUID;

public class PhysicalVolume extends Volume{
    private Drive assignedDrive;
    private VolumeGroup assignedVG;
    public PhysicalVolume (String name, int size, Drive drive, Server s) {
        super(name, size, s);
        drive.setPhysicalVolume(this);
        this.assignedDrive = drive;
    }
    public Drive getAssignedDrive() {return assignedDrive;}
    public void setAssignedVG(VolumeGroup vg) {this.assignedVG = vg;}
    public VolumeGroup getAssignedVG(){return assignedVG;}
}
