package logic;

import java.util.List;

enum Status {CONTINUE, EGAIN, LOST, WIN}

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
                Status status = Status.EGAIN;
                while (status == Status.EGAIN) {
                    map.print();
                    status = unit.makeStep();
                    if (status == Status.WIN || status == Status.LOST) {
                        System.out.printf("YOU %s!\n", status);
                        System.exit(0);
                    }
                }
            }
        }
    }
}