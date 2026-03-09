package ch.niklas409.riftvision.client.riot;

import ch.niklas409.riftvision.config.RiotApiProperties;
import ch.niklas409.riftvision.dto.riot.response.RiotAccountResponse;
import ch.niklas409.riftvision.dto.riot.response.RiotMatchResponse;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClient;

import java.util.List;

@Service
public class RiotApiClient {

    private final RiotApiProperties riotApiProperties;
    private final RestClient restClient;

    public RiotApiClient(RiotApiProperties riotApiProperties) {
        this.riotApiProperties = riotApiProperties;
        this.restClient = RestClient.builder().build();
    }

    public RiotAccountResponse getAccountByRiotId(String gameName, String tagLine) {
        String url = riotApiProperties.getAccountBaseUrl()
                + "/riot/account/v1/accounts/by-riot-id/"
                + gameName + "/" + tagLine;
        return restClient.get()
                .uri(url)
                .header("X-Riot-Token", riotApiProperties.getApiKey())
                .retrieve()
                .body(RiotAccountResponse.class);
    }

    public List<String> getMatchIdsByPuuid(String puuid, int count) {
        String url = riotApiProperties.getMatchBaseUrl()
                + "/lol/match/v5/matches/by-puuid/"
                + puuid + "/ids?count=" + count;

        return restClient.get()
                .uri(url)
                .header("X-Riot-Token", riotApiProperties.getApiKey())
                .retrieve()
                .body(new ParameterizedTypeReference<>() {});
    }

    public RiotMatchResponse getMatchById(String matchId) {
        String url = riotApiProperties.getMatchBaseUrl()
                + "/lol/match/v5/matches/" + matchId;

        return restClient.get()
                .uri(url)
                .header("X-Riot-Token", riotApiProperties.getApiKey())
                .retrieve()
                .body(RiotMatchResponse.class);
    }

}
