import java.util.List;

class CowBowlingView {
    public void displayActualPins(Cow cow, int pins, int throwNumber) {
        System.out.printf("%s actually knocked down %d pins on throw %d%n", 
            cow.getName(), pins, throwNumber);
    }

    public void displayCheatingAttempt(Cow cow, boolean increasingScore) {
        if (increasingScore && cow.getColor() == CowColor.BLACK) { //increase only black
            System.out.printf("%s is attempting to cheat by increasing their score!%n", cow.getName());
        } else {
            System.out.printf("%s is attempting to cheat by decreasing their score!%n", cow.getName());
        }
    }

    public void displayFrameResult(Cow cow, Frame frame, int frameNumber) {
        System.out.printf("%s Frame %d: %s (Reported throws: %d, %d)%n", 
            cow.getName(), frameNumber, frame.getResult(), frame.getThrowScores()[0], frame.getThrowScores()[1]);
    }

    public void displayCheatingDetected(Cow cow, boolean increasedScore, boolean isSecondThrow) {
        if (increasedScore) {
            if (cow.getColor() == CowColor.WHITE && isSecondThrow == true) { // Check is white and 2nd throw is Spare
                System.out.printf("Cheating detected! %s falsely reported on second throw. Score adjusted to Spare.%n", cow.getName());
            } else { // check ล้างท่อ is Strike
                System.out.printf("Cheating detected! %s reported a gutter ball but actually knocked down pins. Score adjusted to Strike.%n", cow.getName());
            }
        } else { // Black 
            System.out.printf("Cheating detected! %s reported knocking down all pins but didn't. Score adjusted to 0.%n", cow.getName());
        }
    }

    public void displayFinalScores(List<Cow> allCows) {
        System.out.println("\nFinal Scores:");
        allCows.sort((c1, c2) -> Integer.compare(c2.getTotalScore(), c1.getTotalScore()));
        for (int i = 0; i < allCows.size(); i++) {
            Cow cow = allCows.get(i);
            System.out.printf("%d. %s: %d points%n", i + 1, cow.getName(), cow.getTotalScore());
        }
    }

    public void displayTeamResults(List<Team> teams) {
        System.out.println("\nTeam Results:");
        teams.sort((t1, t2) -> Integer.compare(t2.getTotalScore(), t1.getTotalScore()));
        for (int i = 0; i < teams.size(); i++) {
            Team team = teams.get(i);
            System.out.printf("%d. %s Team: %d points%n", i + 1, team.getColor().name(), team.getTotalScore());
        }
        System.out.printf("The %s Team wins!%n", teams.get(0).getColor().name());
    }
}