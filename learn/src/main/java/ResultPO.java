import java.io.Serializable;

public class ResultPO implements Serializable {

    private String id;
    private String region;
    private String hashCode;
    private String code;
    private String spec;
    private String description;
    private String specid;

    public String getSpec() {
        return spec;
    }

    public void setSpec(String spec) {
        this.spec = spec;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getSpecid() {
        return specid;
    }

    public void setSpecid(String specid) {
        this.specid = specid;
    }

    ResultPO() {
        this.code = "";
        this.region = "";
        this.hashCode = "";
        this.id = "";
    }

    String getCode() {
        return code;
    }

    void setCode(String code) {
        this.code = code;
    }

    public String getId() {
        return id;
    }

    void setId(String id) {
        this.id = id;
    }

    String getRegion() {
        return region;
    }

    void setRegion(String region) {
        this.region = region;
    }

    String getHashCode() {
        return hashCode;
    }

    void setHashCode(String hashCode) {
        this.hashCode = hashCode;
    }
}
