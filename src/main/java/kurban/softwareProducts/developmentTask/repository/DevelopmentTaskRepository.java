package kurban.softwareProducts.developmentTask.repository;

import kurban.softwareProducts.developmentTask.model.DevelopmentTask;
import kurban.softwareProducts.developmentTask.model.DevelopmentTaskProgress;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface DevelopmentTaskRepository extends JpaRepository<DevelopmentTask, Long> {

    List<DevelopmentTask> findByProductId(Long productId);

    boolean existsByReleaseId(Long releaseId);

    boolean existsByAssigneeUserId(Long assigneeUserId);

    boolean existsByAssigneeUserIdAndProgress(Long assigneeUserId, DevelopmentTaskProgress progress);
}