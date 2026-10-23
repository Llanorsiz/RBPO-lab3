package kurban.softwareProducts.release.web.request;

import kurban.softwareProducts.release.model.ReleaseStatus;
import lombok.Builder;
import lombok.Getter;
import lombok.extern.jackson.Jacksonized;

@Getter
@Builder
@Jacksonized
public class UpdateReleaseRequest {
    private String number;
    private Long productId;
    private ReleaseStatus status;
}
