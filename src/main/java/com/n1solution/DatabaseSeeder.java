package com.n1solution;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import com.n1solution.entities.Service;
import com.n1solution.entities.ServiceField;
import com.n1solution.entities.FAQ;
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
            ),
            Arrays.asList(
                new FAQ("How long does it take to build a website?", "Typically 2–6 weeks depending on complexity."),
                new FAQ("Will my website be mobile-friendly?", "Yes, all our websites are fully responsive."),
                new FAQ("Do you provide post-launch support?", "Yes, we offer maintenance packages.")
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
            ),
            Arrays.asList(
                new FAQ("How long until I see results?", "SEO typically shows results in 3–6 months."),
                new FAQ("Do you guarantee first-page rankings?", "We guarantee best efforts; no ethical agency can guarantee rankings."),
                new FAQ("What do monthly reports include?", "Keyword rankings, traffic analysis, and recommended actions.")
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
            ),
            Arrays.asList(
                new FAQ("How many revisions do I get?", "Unlimited revisions until you're satisfied."),
                new FAQ("What file formats will I receive?", "PNG, SVG, PDF, and AI source files."),
                new FAQ("How long does the process take?", "Initial concepts within 3–5 business days.")
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
            ),
            Arrays.asList(
                new FAQ("What is Google Business Profile?", "It's your free listing on Google Search and Maps."),
                new FAQ("Can you help if my listing was suspended?", "Yes, we handle reinstatement cases."),
                new FAQ("How long until my listing is verified?", "Typically 3–14 days via Google's process.")
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
            ),
            Arrays.asList(
                new FAQ("What is the uptime guarantee?", "We guarantee 99.9% uptime with SLA."),
                new FAQ("Can I migrate my existing website?", "Yes, we provide free website migration."),
                new FAQ("Is SSL included?", "Yes, SSL is free with all hosting plans.")
            )
        );

        // 6. Other Services (Custom Service Entry)
        createServiceIfNotFound(
            "Other Services",
            "/src/assets/Img/otherservices.png",
            Arrays.asList(
                createField("serviceName", "What service do you need?", "text", null),
                createField("requirements", "Your requirements / description", "text", null)
            ),
            Arrays.asList(
                new FAQ("How do you handle custom services?", "Once you submit your requirements, we assign a dedicated specialist to consult with you and draft a custom plan."),
                new FAQ("Is there any obligation after submitting?", "No, the consultation and request are completely free."),
                new FAQ("What's the response time?", "Our team will contact you within 24 hours, usually much sooner.")
            )
        );
    }

    private void createServiceIfNotFound(String name, String imageUrl, List<ServiceField> fields, List<FAQ> faqs) {
        if (!serviceRepository.existsByName(name)) {
            Service service = new Service();
            service.setName(name);
            service.setImageUrl(imageUrl);
            service.setFields(fields);
            service.setFaqs(faqs);
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
