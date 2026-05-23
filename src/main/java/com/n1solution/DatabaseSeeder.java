package com.n1solution;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import com.n1solution.entities.Service;
import com.n1solution.entities.ServiceField;
import com.n1solution.repositories.ServiceRepository;

@Component
public class DatabaseSeeder implements CommandLineRunner {

    @Autowired
    private ServiceRepository serviceRepository;

    @Override
    public void run(String... args) throws Exception {
        seedServices();
    }

    private void seedServices() {
        // 1. Web Development
        createServiceIfNotFound(
            "Web Development",
            "/src/assets/Img/webdevlopment1.jpg",
            Arrays.asList(
                createField("websiteType", "Website Type", "select", Arrays.asList("Static Website", "Dynamic Website", "E-commerce Website")),
                createField("pages", "Number of Pages", "text", null),
                createField("additionalFeatures", "Additional Features/Requirements", "text", null)
            )
        );

        // 2. SEO
        createServiceIfNotFound(
            "SEO",
            "/src/assets/Img/SEO1.jpg",
            Arrays.asList(
                createField("websiteUrl", "Website URL", "text", null),
                createField("keywords", "Target Keywords (comma-separated)", "text", null),
                createField("competitors", "Key Competitors", "text", null)
            )
        );

        // 3. Logo Design
        createServiceIfNotFound(
            "Logo Design",
            "/src/assets/Img/logodesign1.jpg",
            Arrays.asList(
                createField("companyName", "Company Name", "text", null),
                createField("tagline", "Company Tagline (if any)", "text", null),
                createField("industry", "Industry / Niche", "text", null),
                createField("colorPreferences", "Color Preferences", "text", null)
            )
        );

        // 4. Google Map Listing
        createServiceIfNotFound(
            "Google Map Listing",
            "/src/assets/Img/GoogleMyBusiness1.jpg",
            Arrays.asList(
                createField("businessName", "Business Name", "text", null),
                createField("address", "Full Business Address", "text", null),
                createField("phoneNumber", "Business Phone Number", "text", null),
                createField("website", "Business Website", "text", null)
            )
        );

        // 5. Web Hosting
        createServiceIfNotFound(
            "Web Hosting",
            "/src/assets/Img/webhosting1.jpg",
            Arrays.asList(
                createField("domainName", "Domain Name", "text", null),
                createField("hostingPlan", "Hosting Plan Type", "select", Arrays.asList("Basic Hosting", "Premium Managed Hosting", "Business Cloud Hosting")),
                createField("billingCycle", "Billing Cycle", "select", Arrays.asList("Monthly", "Yearly (10% Off)"))
            )
        );

        // 6. Other Services (Custom Service Entry)
        createServiceIfNotFound(
            "Other Services",
            "/src/assets/Img/otherservices.png",
            Arrays.asList(
                createField("serviceName", "What service do you need?", "text", null),
                createField("requirements", "Your requirements / description", "text", null)
            )
        );
    }

    private void createServiceIfNotFound(String name, String imageUrl, List<ServiceField> fields) {
        if (!serviceRepository.existsByName(name)) {
            Service service = new Service();
            service.setName(name);
            service.setImageUrl(imageUrl);
            service.setFields(fields);
            service.setTimesBought(0);
            serviceRepository.save(service);
            System.out.println("[DatabaseSeeder] Seeded service: " + name);
        }
    }

    private ServiceField createField(String name, String label, String type, List<String> options) {
        ServiceField field = new ServiceField();
        field.setName(name);
        field.setLabel(label);
        field.setType(type);
        field.setOptions(options != null ? options : new ArrayList<>());
        return field;
    }
}
