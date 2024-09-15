import java.util.ArrayList;
import java.util.List;

class CowBowlingController {
    private Game game;
    private CowBowlingView view;

    public CowBowlingController(Game game, CowBowlingView view) {
        this.game = game;
        this.view = view;
    }

    public void playGame() {
        for (int frame = 1; frame <= 10; frame++) { //iterate frame 1-10
            System.out.println("\nFrame " + frame);
            for (int cowNumber = 0; cowNumber < 3; cowNumber++) { //iterate cow 0,1,2
                for (Team team : game.getTeams()) { // iterate team
                    Cow cow = team.getCows().get(cowNumber);
                    playFrame(cow, frame);
                }
            }
        }
        calculateFinalScores();
        displayResults();
    }

    private void playFrame(Cow cow, int frameNumber) {
        Frame frame = new Frame();
        int pinsLeft = 10;
        
        // First throw
        int firstThrow = game.bowl(pinsLeft);
        view.displayActualPins(cow, firstThrow, 1);
        
        boolean cheated = game.shouldCheat(cow.getColor());
        
        if (cheated && cow.getColor() == CowColor.BLACK) { // check black cheat
            view.displayCheatingAttempt(cow, true);
            frame.getThrowScores()[0] = 10;
            frame.setResult("Cow Strike");
            view.displayCheatingDetected(cow, false, false);
            frame.getThrowScores()[0] = 0;
            frame.setResult("Cow Open");
        } else if (cheated && cow.getColor() == CowColor.WHITE) { // check white cheat
            view.displayCheatingAttempt(cow, false);
            frame.getThrowScores()[0] = 0;
            if (firstThrow > 0) {
                view.displayCheatingDetected(cow, true, false);
                frame.getThrowScores()[0] = 10;
                frame.setResult("Cow Strike");
            }
        } else {
            frame.getThrowScores()[0] = firstThrow;
            if (firstThrow == 10) {
                frame.setResult("Cow Strike");
            } else {
                pinsLeft -= firstThrow;
                int secondThrow = game.bowl(pinsLeft);
                view.displayActualPins(cow, secondThrow, 2);
                
                // Check for white cow cheating on second throw
                if (cow.getColor() == CowColor.WHITE && game.shouldCheat(cow.getColor())) {
                    view.displayCheatingAttempt(cow, true);
                    if (firstThrow + secondThrow < 10) {
                        view.displayCheatingDetected(cow, true, true);
                        frame.getThrowScores()[1] = 10 - firstThrow;
                        frame.setResult("Cow Spare");
                    } else {
                        frame.getThrowScores()[1] = secondThrow;
                        frame.setResult(firstThrow + secondThrow == 10 ? "Cow Spare" : "Cow Open");
                    }
                } else {
                    frame.getThrowScores()[1] = secondThrow;
                    if (firstThrow + secondThrow == 10) {
                        frame.setResult("Cow Spare");
                    } else {
                        frame.setResult("Cow Open");
                    }
                }
            }
        }

        cow.addFrame(frame);
        view.displayFrameResult(cow, frame, frameNumber);
    }

    private void calculateFinalScores() {
        for (Team team : game.getTeams()) {
            for (Cow cow : team.getCows()) {
                int totalScore = 0;
                List<Frame> frames = cow.getFrames();

                for (int i = 0; i < frames.size(); i++) {
                    Frame frame = frames.get(i);
                    if (frame.getResult().equals("Cow Strike")) { //Strike calculation
                        totalScore += 10;
                        if (i + 1 < frames.size()) {
                            totalScore += frames.get(i + 1).getThrowScores()[0];
                            if (frames.get(i + 1).getResult().equals("Cow Strike") && i + 2 < frames.size()) {
                                totalScore += frames.get(i + 2).getThrowScores()[0];
                            } else if (i + 1 < frames.size()) {
                                totalScore += frames.get(i + 1).getThrowScores()[1];
                            }
                        }
                    } else if (frame.getResult().equals("Cow Spare")) { // Spare calculation
                        totalScore += 10;
                        if (i + 1 < frames.size()) {
                            totalScore += frames.get(i + 1).getThrowScores()[0];
                        }
                    } else { // Open calculation
                        totalScore += frame.getThrowScores()[0] + frame.getThrowScores()[1];
                    }
                }
                cow.setTotalScore(totalScore);
            }
            team.calculateTotalScore();
        }
    }

    private void displayResults() {
        List<Cow> allCows = new ArrayList<>();
        for (Team team : game.getTeams()) {
            allCows.addAll(team.getCows());
        }
        view.displayFinalScores(allCows);
        view.displayTeamResults(game.getTeams());
    }
}