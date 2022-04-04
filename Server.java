import java.util.ArrayList;
import java.util.UUID;

public class Server {
    private String error;
    private String name;
    private ArrayList<Drive> Drives = new ArrayList<Drive>();
    private ArrayList<LogicalVolume> LogicalVolumes = new ArrayList<LogicalVolume>();
    private ArrayList<VolumeGroup> VolumeGroups = new ArrayList<VolumeGroup>();
    private ArrayList<PhysicalVolume> PhysicalVolumes = new ArrayList<PhysicalVolume>();

    public Server(String name) {
        this.name = name;
    }

    //accessor methods
    public ArrayList<Drive> getDrives() {return Drives;}
    public ArrayList<LogicalVolume> getLogicalVolumes() {return LogicalVolumes;}
    public ArrayList<VolumeGroup> getVolumeGroups() {return VolumeGroups;}
    public ArrayList<PhysicalVolume> getPhysicalVolumes() {return PhysicalVolumes;}




    public boolean installDrive(String driveName, int size) {
        this.error = "";
        for (int i = 0; i < Drives.size(); i++) {
            if (Drives.get(i).getName().equals(driveName)) {
                error = "That Drive name is already in use!";
                return false;
            }
        }
        Drive d = new Drive(driveName, size, this);
        Drives.add(d);
        return true;
    }


    //assorted volume creator methods (these return true to indicate a success, false to indicate a failure)


    public boolean createVG(String PVName, String name) { //create volume group
//        ArrayList<PhysicalVolume> pvsToUse = new ArrayList<PhysicalVolume>();

        this.error = "";
        PhysicalVolume pvToUse = null;
        //make sure to add a check so that the physical volume isn't already used
        for (int i = 0; i < PhysicalVolumes.size(); i++) {
            if (PhysicalVolumes.get(i).getName().equals(PVName)) {
                pvToUse = PhysicalVolumes.get(i);
            }
        }

        if (pvToUse==null) {
            error = "Physical Volume not found!";
            return false;
        }
        if (pvToUse.getAssignedVG() != null) {
            error = "That Physical Volume is already assigned to a Volume Group";
            return false;
        }

        VolumeGroup vg = new VolumeGroup(name, pvToUse, this);
        VolumeGroups.add(vg);
        pvToUse.setAssignedVG(vg);
        return true;
    }



    public boolean createLV(String targetGroupName, int size, String name) {
        this.error = "";
        VolumeGroup chosenVG = null;

        boolean found = false;
        for (int i = 0; i < VolumeGroups.size(); i++) {
            if (VolumeGroups.get(i).getName().equals(targetGroupName)) {
                chosenVG = VolumeGroups.get(i);

            }
        }
        if (chosenVG == null) {
            error = "That Volume Group could not be found!";
            return false;
        }


        if (chosenVG.getRemainingSpace() < size) { //specified size is too big
            error = "Not enough free space in this Volume Group!";
            return false;
        }

        LogicalVolume newLV = new LogicalVolume(name, size, chosenVG,this);
        LogicalVolumes.add(newLV);
        return true;
    }


    public boolean extendVG(String targetPVName, String targetVGName) {
        this.error = "";
        VolumeGroup targetVG = null;
        PhysicalVolume targetPV = null;
        for (int i = 0; i < VolumeGroups.size(); i++) {
            if (VolumeGroups.get(i).getName().equals(targetVGName)) {
                targetVG = VolumeGroups.get(i);
            }
        } if (targetVG == null) {
            error = "That Volume Group could not be found!";
            return false;}

        for (int i = 0; i < PhysicalVolumes.size(); i++) {
            if (PhysicalVolumes.get(i).getName().equals(targetPVName)) {
                targetPV = PhysicalVolumes.get(i);
            }
        } if (targetPV == null) {
            error = "That Physical Volume could not be found!";
            return false;
        }

        if (targetPV.getAssignedVG() != null) {
            error = "That Physical Volume is already assigned to a Volume Group!";
            return false;
        }


        targetVG.extend(targetPV);
        targetPV.setAssignedVG(targetVG);

        return true;
    }


    public boolean createPV(String targetDriveName, String name) { //create physical volume
        this.error = "";
        Drive targetDrive = null;

        //search for a drive with the target UUID
        for (int i = 0; i < Drives.size(); i++) {
            if (Drives.get(i).getName().equals(targetDriveName)) {
                targetDrive = Drives.get(i);
            }
        }

        if (targetDrive.equals(null)) {
            error = "That Drive could not be found!";
            return false;
        }

        if (targetDrive.getPhysicalVolume() != null) {
            error = "That Drive already has an assigned Physical Volume!";
            return false;
        }//if the UUID is not found

         //if the specified size is too big

        PhysicalVolume newVol = new PhysicalVolume(name, targetDrive.getSize(), targetDrive, this);
//        targetDrive.setPhysicalVolume(newVol); //sets the target drive's assigned physical volume to the new one // wait this is done automatically
        PhysicalVolumes.add(newVol); //at this point the parameters have passed all of the tests, so, you can intitialize and add the new physical volume

        return true;
    }

    public String getError() {
        return this.error;
    }



}
