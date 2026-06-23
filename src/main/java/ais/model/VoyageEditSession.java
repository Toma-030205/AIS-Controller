package ais.model;



public class VoyageEditSession {

    private final OwnShipInfo original;
    private OwnShipInfo workingCopy;

    public VoyageEditSession(OwnShipInfo original) {
        this.original = original;
        this.workingCopy = new OwnShipInfo(original);
    }

    public OwnShipInfo getWorking() {
        return workingCopy;
    }

    public void commit() {
        original.copyFrom(workingCopy);

        workingCopy = new OwnShipInfo(original);
    }

    public void discard() {
        workingCopy = new OwnShipInfo(original);
    }
}
