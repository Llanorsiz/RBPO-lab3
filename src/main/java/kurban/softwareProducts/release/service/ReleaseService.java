package kurban.softwareProducts.release.service;

import kurban.softwareProducts.common.error.ConflictException;
import kurban.softwareProducts.common.error.NotFoundException;
import kurban.softwareProducts.developmentTask.repository.DevelopmentTaskRepository;
import kurban.softwareProducts.release.mapper.ReleaseMapper;
import kurban.softwareProducts.release.model.Release;
import kurban.softwareProducts.release.repository.ReleaseRepository;
import kurban.softwareProducts.release.web.request.CreateReleaseRequest;
import kurban.softwareProducts.release.web.request.UpdateReleaseRequest;
import kurban.softwareProducts.release.web.response.ReleaseResponse;
import kurban.softwareProducts.releaseCheck.repository.ReleaseCheckRepository;
import kurban.softwareProducts.softwareProduct.model.SoftwareProduct;
import kurban.softwareProducts.softwareProduct.repository.SoftwareProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ReleaseService {
    private final ReleaseMapper releaseMapper;

    private final ReleaseRepository releaseRepository;
    private final SoftwareProductRepository softwareProductRepository;
    private final ReleaseCheckRepository releaseCheckRepository;
    private final DevelopmentTaskRepository developmentTaskRepository;

    public List<ReleaseResponse> getReleases() {
        return releaseRepository.findAll()
                .stream()
                .map(releaseMapper::toReleaseResponse)
                .toList();
    }

    @Transactional
    public ReleaseResponse createRelease(CreateReleaseRequest request) {
        Release release = releaseMapper.fromCreateReleaseRequest(request);
        release.setCreatedAt(LocalDate.now());
        releaseRepository.save(release);
        return releaseMapper.toReleaseResponse(release);
    }

    @Transactional
    public ReleaseResponse updateRelease(Long releaseId, UpdateReleaseRequest request) {
        Release release = releaseRepository.findById(releaseId).get();
        releaseMapper.updateFromUpdateReleaseRequest(request, release);
        return releaseMapper.toReleaseResponse(release);
    }

    @Transactional
    public void deleteRelease(Long releaseId) {
        Release release = releaseRepository.findById(releaseId)
                .orElseThrow(() -> new NotFoundException("Релиз не найден"));

        if (developmentTaskRepository.existsByReleaseId(releaseId)) {
            throw new ConflictException("Нельзя удалить релиз, к которому привязаны задачи");
        }

        SoftwareProduct product = softwareProductRepository.findById(release.getProductId())
                .orElseThrow(() -> new NotFoundException("Программный продукт не найден"));

        if(releaseId.equals(product.getCurrentReleaseId())){
            throw new ConflictException("Нельзя удалить релиз, который является текущим для продукта");
        }

        if(!releaseCheckRepository.findByReleaseId(releaseId).isEmpty()){
            throw new ConflictException("Нельзя отменить релиз с существующими чек-листами");
        }

        releaseRepository.delete(release);
    }
}