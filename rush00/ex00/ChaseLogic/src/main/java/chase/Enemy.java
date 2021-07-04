package chase;

public class Enemy extends AUnit {
    private final AUnit player;
    public Enemy(int x, int y, IMap map, AUnit player, boolean isPlayer) {
        super(x, y, map, isPlayer);
        this.player = player;
    }

    @Override
    public Status makeStep() {
        Direction newDir = LeeAlgorithm.BFS(this.map, this.location.x, this.location.y, player.location.x, this.player.location.y, false);

        if (map.isPlayer(this.location.x + newDir.getDx(), this.location.y + newDir.getDy()))
            return Status.LOSE;
        if (map.isEmpty(this.location.x + newDir.getDx(), this.location.y + newDir.getDy())) {
            map.moveUnit(this, this.location.x + newDir.getDx(), this.location.y + newDir.getDy());
            return Status.CONTINUE;
        }
        return Status.CONTINUE;
    }
}
