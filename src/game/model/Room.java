package game.model;

public abstract class Room {

    protected boolean completed = false;

    public abstract String getRoomName();
    public abstract String getRoomDescription();
    public abstract String[] getActions();
    public abstract String performAction(Player player, int actionIndex);

    public boolean isCompleted() { return completed; }
}
