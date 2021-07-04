package game.logic;

import chase.*;

import java.util.List;
import java.util.Locale;
import java.util.Scanner;

public class Game {

    private final Properties props;
    private final Map map;

    public Game(Properties props) {
        this.props = props;
        this.map = new Map(props);
    }

    public void run() {
        Scanner scanner = new Scanner(System.in);
        List<AUnit> units = map.generateRandom();
        while (true) {
            for (AUnit unit : units) {
                Status status = Status.AGAIN;
                while (status == Status.AGAIN) {
                    map.print();
                    if (!unit.isPlayer && props.getProfile().equalsIgnoreCase("dev")) {
                        System.out.println("Enter 8 for allow next step");
                        String cmd = scanner.next();
                        if (!cmd.equals("8"))
                            continue;
                    }
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