import java.util.ArrayList;
import java.util.List;
import java.util.Random;

enum CowColor {
    WHITE, BLACK, BROWN
}

class Cow {
    private String name;
    private CowColor color;
    private List<Frame> frames;
    private int totalScore;

    public Cow(String name, CowColor color) {
        this.name = name;
        this.color = color;
        this.frames = new ArrayList<>();
        this.totalScore = 0;
    }

    // Getters and setters
    public String getName() {
        return name;
    }

    public CowColor getColor() {
        return color;
    }

    public List<Frame> getFrames() {
        return frames;
    }

    public int getTotalScore() {
        return totalScore;
    }

    public void setTotalScore(int totalScore) {
        this.totalScore = totalScore;
    }

    public void addFrame(Frame frame) {
        frames.add(frame);
    }
}

class Frame {
    private int[] throwScores;
    private String result;

    public Frame() {
        this.throwScores = new int[2];
        this.result = "";
    }

    // Getters and setters
    public int[] getThrowScores() {
        return throwScores;
    }

    public void setThrowScores(int[] throwScores) {
        this.throwScores = throwScores;
    }

    public String getResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = result;
    }
}

class Team {
    private CowColor color;
    private List<Cow> cows;
    private int totalScore;

    public Team(CowColor color) {
        this.color = color;
        this.cows = new ArrayList<>();
        for (int i = 1; i <= 3; i++) {
            cows.add(new Cow(color.name() + " cow " + i, color));
        }
        this.totalScore = 0;
    }

    // Getters
    public CowColor getColor() {
        return color;
    }

    public List<Cow> getCows() {
        return cows;
    }

    public int getTotalScore() {
        return totalScore;
    }

    public void calculateTotalScore() {
        totalScore = cows.stream().mapToInt(Cow::getTotalScore).sum();
    }
}

class Game {
    private List<Team> teams;
    private Random random;

    public Game() {
        this.teams = new ArrayList<>();
        teams.add(new Team(CowColor.WHITE));
        teams.add(new Team(CowColor.BLACK));
        teams.add(new Team(CowColor.BROWN));
        this.random = new Random();
    }

    public List<Team> getTeams() {
        return teams;
    }

    public int bowl(int pinsLeft) {
        return random.nextInt(pinsLeft + 1);
    }

    public boolean shouldCheat(CowColor color) { //Cheat chance
        if (color == CowColor.BLACK) {
            return random.nextDouble() < 0.2; // 20% chance for black cows
        } else if (color == CowColor.WHITE) {
            return random.nextDouble() < 0.1; // 10% chance for white cows
        }
        return false;
    }
}