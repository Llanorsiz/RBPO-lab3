package kurban.softwareProducts.releaseCheck.web.request;

import kurban.softwareProducts.releaseCheck.model.ReleaseCheckType;
import lombok.Builder;
import lombok.Getter;
import lombok.extern.jackson.Jacksonized;

@Getter
@Builder
@Jacksonized
public class CreateReleaseCheckRequest {
    private String name;
    private ReleaseCheckType type;
    private String result;
}
