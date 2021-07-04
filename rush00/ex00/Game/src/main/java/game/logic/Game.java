package game.logic;

import chase.*;

import java.util.List;

public class Game {

    private final Properties props;
    private final Map map;

    public Game(Properties props) {
        this.props = props;
        this.map = new Map(props);
    }

    public void run() {
        List<AUnit> units = map.generateRandom();
        while (true) {
            for (AUnit unit : units) {
                Status status = Status.AGAIN;
                while (status == Status.AGAIN) {
                    map.print();
                    status = unit.makeStep();
                    if (status == Status.WIN || status == Status.LOSE) {
                        System.out.printf("YOU %s!\n", status);
                        System.exit(0);
                    }
                }
            }
        }
    }
}