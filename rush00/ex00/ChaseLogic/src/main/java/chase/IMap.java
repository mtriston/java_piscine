package chase;

public interface IMap {
    public void moveUnit(Player unit, int newX, int newY);
    public void moveUnit(Enemy unit, int newX, int newY);
    public boolean isCanGo(int x, int y);
    public boolean isEmpty(int x, int y);
    public boolean isWall(int x, int y);
    public boolean isUnit(int x, int y);
}
