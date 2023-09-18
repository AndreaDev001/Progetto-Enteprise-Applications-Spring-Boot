package com.enterpriseapplications.springboot.data.dao.specifications;

import com.enterpriseapplications.springboot.data.entities.enums.ReportReason;
import com.enterpriseapplications.springboot.data.entities.enums.ReportType;
import com.enterpriseapplications.springboot.data.entities.reports.Report;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.jpa.domain.Specification;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class ReportSpecifications
{
    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    public static class Filter
    {
        private String reporterEmail;
        private String reportedEmail;
        private String reporterUsername;
        private String reportedUsername;
        private String description;
        private ReportReason reason;
        private ReportType type;
    }
    public static Specification<Report> withFilter(Filter filter) {
        return (Root<Report> root, CriteriaQuery<?> criteriaQuery,CriteriaBuilder criteriaBuilder) -> {
            List<Predicate> requiredPredicates = new ArrayList<>();
            if(filter.reporterEmail != null)
                requiredPredicates.add(criteriaBuilder.like(root.get("reporter").get("email"),SpecificationsUtils.likePattern(filter.reporterEmail)));
            if(filter.reportedEmail != null)
                requiredPredicates.add(criteriaBuilder.like(root.get("reported").get("username"),SpecificationsUtils.likePattern(filter.reportedEmail)));
            if(filter.reporterUsername != null)
                requiredPredicates.add(criteriaBuilder.like(root.get("reporter").get("username"),SpecificationsUtils.likePattern(filter.reporterUsername)));
            if(filter.reportedUsername != null)
                requiredPredicates.add(criteriaBuilder.like(root.get("reported").get("username"),SpecificationsUtils.likePattern(filter.reportedUsername)));
            if(filter.description != null)
                requiredPredicates.add(criteriaBuilder.like(root.get("description"),SpecificationsUtils.likePattern(filter.description)));
            if(filter.reason != null)
                requiredPredicates.add(criteriaBuilder.equal(root.get("reason"),filter.reason));
            if(filter.type != null)
                requiredPredicates.add(criteriaBuilder.equal(root.get("type"),filter.type));

            Predicate requiredPredicate = SpecificationsUtils.generatePredicate(criteriaBuilder.isNotNull(root.get("id")),requiredPredicates,criteriaBuilder);
            return criteriaQuery.where(requiredPredicate).getRestriction();
        };
    }
}