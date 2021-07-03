package logic;

public class Enemy extends AUnit {
    private final AUnit player;
    public Enemy(int x, int y, Map map, AUnit player) {
        super(x, y, map);
        this.player = player;
    }

    @Override
    public Status makeStep() {
        return Status.CONTINUE;
    }
}
