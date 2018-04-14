package matrixdeity.rosello;

import matrixdeity.rosello.core.RoselloBot;

public class BotLauncher {

    public static void main(String[] args) {
        RoselloBot rosello = RoselloBot.getInstance();
        System.out.println("My number is " + rosello.getClientID());
    }

}
