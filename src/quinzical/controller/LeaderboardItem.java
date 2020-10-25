package quinzical.controller;

/**
 * Each time a player completes a game in game mode, a new LeaderboardItem will
 * be added to the leaderboard.
 * @author dj5822
 *
 */
public class LeaderboardItem {
	
	private String username;
	private int score;
	
	public LeaderboardItem(String username, int score) {
		this.username = username;
		this.score = score;
	}
	
	public String getUsername() {
		return username;
	}
	
	public void setUsername(String username) {
		this.username = username;
	}
	
	public int getScore() {
		return score;
	}
	
	public void setScore(int score) {
		this.score = score;
	}
}
