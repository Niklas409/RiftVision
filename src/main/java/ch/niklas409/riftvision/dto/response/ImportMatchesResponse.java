package ch.niklas409.riftvision.dto.response;

public class ImportMatchesResponse {

    private int imported;
    private int skipped;

    public ImportMatchesResponse(int imported, int skipped) {
        this.imported = imported;
        this.skipped = skipped;
    }

    public int getImported() {
        return imported;
    }

    public int getSkipped() {
        return skipped;
    }

    public void setImported(int imported) {
        this.imported = imported;
    }

    public void setSkipped(int skipped) {
        this.skipped = skipped;
    }
}
