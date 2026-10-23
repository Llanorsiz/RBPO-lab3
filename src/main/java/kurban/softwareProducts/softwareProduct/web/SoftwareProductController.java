package kurban.softwareProducts.softwareProduct.web;

import kurban.softwareProducts.softwareProduct.service.SoftwareProductService;
import kurban.softwareProducts.softwareProduct.web.request.CreateSoftwareProductRequest;
import kurban.softwareProducts.softwareProduct.web.request.UpdateSoftwareProductRequest;
import kurban.softwareProducts.softwareProduct.web.response.SoftwareProductResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/software-products")
@RequiredArgsConstructor
public class SoftwareProductController {
    private final SoftwareProductService softwareProductService;

    @GetMapping
    public List<SoftwareProductResponse> getSoftwareProducts() {
        return softwareProductService.getSoftwareProducts();
    }

    @PatchMapping("/{productId}/responsible/{newResponsibleUserId}")
    public SoftwareProductResponse changeResponsibleUser(@PathVariable Long productId, @PathVariable Long newResponsibleUserId){
        return softwareProductService.changeResponsibleUser(productId, newResponsibleUserId);
    }

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping
    public SoftwareProductResponse createSoftwareProduct(@RequestBody CreateSoftwareProductRequest request) {
        return softwareProductService.createSoftwareProduct(request);
    }

    @PutMapping("/{softwareProductId}")
    public SoftwareProductResponse updateSoftwareProduct(@PathVariable Long softwareProductId, @RequestBody UpdateSoftwareProductRequest request) {
        return softwareProductService.updateSoftwareProduct(softwareProductId, request);
    }

    @ResponseStatus(HttpStatus.NO_CONTENT)
    @DeleteMapping("/{softwareProductId}")
    public void deleteSoftwareProduct(@PathVariable Long softwareProductId) {
        softwareProductService.deleteSoftwareProduct(softwareProductId);
    }
}
