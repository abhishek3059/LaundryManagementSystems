package com.final_project.LaundryManagementSystem.serviceImpl;

import com.final_project.LaundryManagementSystem.dto.LaundryServiceRequest;
import com.final_project.LaundryManagementSystem.model.LaundryService;
import com.final_project.LaundryManagementSystem.repo.LaundryServiceRepo;
import com.final_project.LaundryManagementSystem.service.LaundryServices;
import lombok.Data;
import org.springframework.stereotype.Service;
import java.util.List;

@Data
@Service
public class LaundryServicesImpl implements LaundryServices {

    private final LaundryServiceRepo laundryServiceRepo;
    @Override
    public boolean saveService(LaundryServiceRequest request) {
        if(request.getId() != null && laundryServiceRepo.existsById(request.getId())){
            //already in the database
            return false;
        }
        LaundryService laundryService  = LaundryService.builder()
                .serviceName(request.getServiceName())
                .description(request.getDescription())
                .basePrice(request.getBasePrice())
                .estimatedTimeInMinutes(request.getEstimatedTimeInMinutes())
                .build();
        laundryServiceRepo.save(laundryService);
        return true;
    }

    @Override
    public List<LaundryService> getAllServices() {
        return laundryServiceRepo.findAll();
    }


}
