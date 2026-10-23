package kurban.softwareProducts.releaseCheck.service;

import kurban.softwareProducts.common.error.ConflictException;
import kurban.softwareProducts.common.error.NotFoundException;
import kurban.softwareProducts.release.model.Release;
import kurban.softwareProducts.release.model.ReleaseStatus;
import kurban.softwareProducts.release.repository.ReleaseRepository;
import kurban.softwareProducts.releaseCheck.mapper.ReleaseCheckMapper;
import kurban.softwareProducts.releaseCheck.model.ReleaseCheck;
import kurban.softwareProducts.releaseCheck.model.ReleaseCheckType;
import kurban.softwareProducts.releaseCheck.repository.ReleaseCheckRepository;
import kurban.softwareProducts.releaseCheck.web.request.CreateReleaseCheckRequest;
import kurban.softwareProducts.releaseCheck.web.request.UpdateReleaseCheckRequest;
import kurban.softwareProducts.releaseCheck.web.response.ReleaseCheckResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ReleaseCheckService {
    private final ReleaseCheckMapper releaseCheckMapper;

    private final ReleaseRepository releaseRepository;
    private final ReleaseCheckRepository releaseCheckRepository;

    public List<ReleaseCheckResponse> getReleaseChecks() {
        return releaseCheckRepository.findAll()
                .stream()
                .map(releaseCheckMapper::toReleaseCheckResponse)
                .toList();
    }

    @Transactional
    public ReleaseCheckResponse createReleaseCheck(Long releaseId, CreateReleaseCheckRequest request) {
        Release release = releaseRepository.findById(releaseId)
                .orElseThrow(() -> new NotFoundException("Релиз не найден"));
        if(release.getStatus() == ReleaseStatus.ACTUAL){
            throw new ConflictException("Нельзя менять чек-листы у актуального релиза");
        }
        if(request.getType() == ReleaseCheckType.REQUIRED && !StringUtils.hasText(request.getResult())){
            throw new IllegalArgumentException("Обязательный чек-лист не может быть без резултата");
        }
        ReleaseCheck releaseCheck = releaseCheckMapper.fromCreateReleaseCheckRequest(request);
        releaseCheck.setReleaseId(releaseId);
        releaseCheckRepository.save(releaseCheck);
        return releaseCheckMapper.toReleaseCheckResponse(releaseCheck);
    }

    @Transactional
    public ReleaseCheckResponse updateReleaseCheck(Long releaseCheckId, UpdateReleaseCheckRequest request) {
        ReleaseCheck releaseCheck = releaseCheckRepository.findById(releaseCheckId).get();
        releaseCheckMapper.updateFromUpdateReleaseCheckRequest(request, releaseCheck);
        return releaseCheckMapper.toReleaseCheckResponse(releaseCheck);
    }

    @Transactional
    public void deleteReleaseCheck(Long releaseCheckId) {
        releaseCheckRepository.deleteById(releaseCheckId);
    }
}
