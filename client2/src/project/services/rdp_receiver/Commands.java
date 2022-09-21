package project.services.rdp_receiver;

public enum Commands {
	
    PRESS_MOUSE(-1),
    RELEASW_MOUSE(-2),
    PRESS_KEY(-3),
    RELEASE_KEY(-4),
    MOVE_MOUSE(-5);
    
    private int Abbrev;
    
    Commands(int abbrev){
        this.Abbrev=abbrev;
    }
    
    public int getAbbrev(){
        return Abbrev;
    } 
}