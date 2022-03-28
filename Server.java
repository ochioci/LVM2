import java.util.ArrayList;
import java.util.UUID;

public class Server {
    private String name;
    private ArrayList<Drive> Drives;
    private ArrayList<LogicalVolume> LogicalVolumes;
    private ArrayList<VolumeGroup> VolumeGroups;
    private ArrayList<PhysicalVolume> PhysicalVolumes;

    public Server(String name) {
        this.name = name;
    }

    //accessor methods
    public ArrayList<Drive> getDrives() {return Drives;}
    public ArrayList<LogicalVolume> getLogicalVolumes() {return LogicalVolumes;}
    public ArrayList<VolumeGroup> getVolumeGroups() {return VolumeGroups;}
    public ArrayList<PhysicalVolume> getPhysicalVolumes() {return PhysicalVolumes;}



    public boolean installDrive(Drive d) {
        Drives.add(d);
        return true;
    }


    //assorted volume creator methods (these return true to indicate a success, false to indicate a failure)


    public boolean createVG(ArrayList<UUID> pvIDs, String name) { //create volume group
        ArrayList<PhysicalVolume> pvsToUse = new ArrayList<PhysicalVolume>();

        //make sure to add a check so that the physical volume isn't already used
        for (int i = 0; i < PhysicalVolumes.size(); i++) { //search through all available physical volumes for the supplied UUDIs
            for (int n = 0; n < pvIDs.size(); n++) {
                if (PhysicalVolumes.get(i).getUUID().equals(pvIDs.get(n))) {
                    if (!PhysicalVolumes.get(i).getAssignedVG().equals(null)) {
                        return false; //one of the physical volumes is already assigned to a volume group, request should fail.
                    }
                    pvsToUse.add(PhysicalVolumes.get(i));
                }
            }
        }

        if (pvsToUse.size() != pvIDs.size()) {return false;} //this means not every UUID was found, so the request should fail

        VolumeGroup vg = new VolumeGroup(name, pvsToUse, this);
        VolumeGroups.add(vg);
        for (int i = 0; i < vg.getPVs().size(); i++) {
            vg.getPVs().get(i).setAssignedVG(vg);//for each physical volume newly assigned to this VG, set that object's assigned VG to this
        }
        return true;
    }



    public boolean createLV(UUID targetGroupU, int size, String name) {
        VolumeGroup chosenVG = null;

        boolean found = false;
        for (int i = 0; i < VolumeGroups.size(); i++) {
            if (VolumeGroups.get(i).getUUID().equals(targetGroupU)) {
                chosenVG = VolumeGroups.get(i);

            }
        }
        if (chosenVG.equals(null)) {return false;}


        if (chosenVG.getSize() - chosenVG.getStorageUsed() < size) { //specified size is too big
            return false;
        }

        // finish here, add initialization

        return true;
    }


    public boolean createPV(UUID targetDriveU, int size, String name) { //create physical volume
        Drive targetDrive = null;

        //search for a drive with the target UUID
        for (int i = 0; i < Drives.size(); i++) {
            if (Drives.get(i).getUUID().equals(targetDriveU)) {
                targetDrive = Drives.get(i);
            }
        }

        if (targetDrive.equals(null)) {return false;} //if the UUID is not found

        if (targetDrive.getSize() < size) {return false;} //if the specified size is too big

        PhysicalVolume newVol = new PhysicalVolume(name, size, targetDrive, this);
//        targetDrive.setPhysicalVolume(newVol); //sets the target drive's assigned physical volume to the new one // wait this is done automatically
        PhysicalVolumes.add(newVol); //at this point the parameters have passed all of the tests, so, you can intitialize and add the new physical volume

        return true;
    }



}
