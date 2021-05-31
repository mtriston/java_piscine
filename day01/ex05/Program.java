public class Program {
    public static void main(String[] args)
    {
        Menu menu;
        if (args.length == 0) {
            menu = new Menu(ProfileType.PRODUCTION);

        } else if (args.length == 1 && args[0].equals("-config=dev")) {
            menu = new Menu(ProfileType.DEV);
        } else {
            System.err.println("Invalid argument. Program will start in production mode");
            menu = new Menu(ProfileType.PRODUCTION);
        }
        menu.start();
        menu.finish();
    }
}
