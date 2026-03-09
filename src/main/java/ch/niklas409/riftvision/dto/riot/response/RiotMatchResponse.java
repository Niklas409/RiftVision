package ch.niklas409.riftvision.dto.riot.response;

public class RiotMatchResponse {

    private RiotMatchMetadataResponse metadata;
    private RiotMatchInfoResponse info;

    public RiotMatchMetadataResponse getMetadata() {
        return metadata;
    }

    public RiotMatchInfoResponse getInfo() {
        return info;
    }

    public void setMetadata(RiotMatchMetadataResponse metadata) {
        this.metadata = metadata;
    }

    public void setInfo(RiotMatchInfoResponse info) {
        this.info = info;
    }
}
