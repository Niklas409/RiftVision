package ch.niklas409.riftvision.service;

import ch.niklas409.riftvision.dto.PlayerStatsResponse;
import ch.niklas409.riftvision.model.Match;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class MatchService {

    private final List<Match> matches = new ArrayList<>();

    public void addMatch(Match match) {
        matches.add(match);
    }

    public List<Match> getAllMatches() {
        return List.copyOf(matches);
    }

    public PlayerStatsResponse calculateStats(String playerId) {
        int matches = (int) countMatches(playerId);
        int wins = (int) countWins(playerId);
        int losses = matches - wins;
        int kills = sumKills(playerId);
        int deaths = sumDeaths(playerId);
        int assists = sumAssists(playerId);
        double kda = (kills + assists) / (double) Math.max(1, deaths);
        return new PlayerStatsResponse(playerId, matches, wins, losses, kills, deaths, assists, kda);
    }

    public long countMatches(String playerId) {
        return matches.stream().filter(match -> match.getPlayerId().equals(playerId)).count();
    }

    public long countWins(String playerId) {
        return matches.stream().filter(match -> match.getPlayerId().equals(playerId)).filter(Match::isWin).count();
    }

    public int sumKills(String playerId) {
        return matches.stream().filter(match -> match.getPlayerId().equals(playerId)).mapToInt(Match::getKills).sum();
    }

    public int sumDeaths(String playerId) {
        return matches.stream().filter(match -> match.getPlayerId().equals(playerId)).mapToInt(Match::getDeaths).sum();
    }

    public int sumAssists(String playerId) {
        return matches.stream().filter(match -> match.getPlayerId().equals(playerId)).mapToInt(Match::getAssists).sum();
    }

}
