package ch.niklas409.riftvision.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties(prefix = "riot")
public class RiotApiProperties {

    private String apiKey;
    private String accountBaseUrl;
    private String matchBaseUrl;

    public String getApiKey() {
        return apiKey;
    }

    public String getAccountBaseUrl() {
        return accountBaseUrl;
    }

    public String getMatchBaseUrl() {
        return matchBaseUrl;
    }

    public void setApiKey(String apiKey) {
        this.apiKey = apiKey;
    }

    public void setAccountBaseUrl(String accountBaseUrl) {
        this.accountBaseUrl = accountBaseUrl;
    }

    public void setMatchBaseUrl(String matchBaseUrl) {
        this.matchBaseUrl = matchBaseUrl;
    }
}
