package kurban.softwareProducts.releaseCheck.web;

import kurban.softwareProducts.releaseCheck.service.ReleaseCheckService;
import kurban.softwareProducts.releaseCheck.web.request.CreateReleaseCheckRequest;
import kurban.softwareProducts.releaseCheck.web.request.UpdateReleaseCheckRequest;
import kurban.softwareProducts.releaseCheck.web.response.ReleaseCheckResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/release-checks")
@RequiredArgsConstructor
public class ReleaseCheckController {
    private final ReleaseCheckService releaseCheckService;

    @GetMapping
    public List<ReleaseCheckResponse> getReleaseChecks() {
        return releaseCheckService.getReleaseChecks();
    }

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping("/releases/{releaseId}")
    public ReleaseCheckResponse createReleaseCheck(@PathVariable Long releaseId, @RequestBody CreateReleaseCheckRequest request) {
        return releaseCheckService.createReleaseCheck(releaseId, request);
    }

    @PutMapping("/{releaseCheckId}")
    public ReleaseCheckResponse updateReleaseCheck(@PathVariable Long releaseCheckId, @RequestBody UpdateReleaseCheckRequest request) {
        return releaseCheckService.updateReleaseCheck(releaseCheckId, request);
    }

    @ResponseStatus(HttpStatus.NO_CONTENT)
    @DeleteMapping("/{releaseCheckId}")
    public void deleteReleaseCheck(@PathVariable Long releaseCheckId) {
        releaseCheckService.deleteReleaseCheck(releaseCheckId);
    }
}