package me.egomaniac.kitpvp.scoreboard.api;

public enum AssembleStyle
{
    KOHI(true, 15), 
    VIPER(true, -1), 
    MODERN(false, 1);
    
    private final boolean descending;
    private final int startNumber;
    
    AssembleStyle(final boolean descending, final int startNumber) {
        this.descending = descending;
        this.startNumber = startNumber;
    }
    
    public boolean isDescending() {
        return this.descending;
    }
    
    public int getStartNumber() {
        return this.startNumber;
    }
}
