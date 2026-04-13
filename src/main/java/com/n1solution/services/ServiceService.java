package com.n1solution.services;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import com.n1solution.entities.Service;
import com.n1solution.repositories.ServiceRepository;
//import org.springframework.stereotype.Service;

@org.springframework.stereotype.Service
public class ServiceService {
    @Autowired
    private ServiceRepository serviceRepository;

    public List<Service> getAllServices() {
        return serviceRepository.findAll();
    }

    public Service addService(Service service) {
        return serviceRepository.save(service);
    }

    public Service updateService(Long id, Service updatedService) {
        return serviceRepository.findById(id)
            .map(service -> {
                service.setName(updatedService.getName());
                service.setImageUrl(updatedService.getImageUrl());
                service.setFields(updatedService.getFields());
                return serviceRepository.save(service);
            })
            .orElseThrow(() -> new RuntimeException("Service not found with id " + id));
    }

    public void deleteService(Long id) {
        serviceRepository.deleteById(id);
    }
}