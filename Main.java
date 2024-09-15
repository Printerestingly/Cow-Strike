//65050131
public class Main {
    public static void main(String[] args) {
        Game game = new Game();
        CowBowlingView view = new CowBowlingView();
        CowBowlingController controller = new CowBowlingController(game, view);
        
        controller.playGame();
    }
}
