package com.enterpriseapplications.springboot.data.dao.specifications;

import com.enterpriseapplications.springboot.data.entities.Ban;
import com.enterpriseapplications.springboot.data.entities.enums.ReportReason;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.jpa.domain.Specification;

import java.util.ArrayList;
import java.util.List;

public class BanSpecifications
{
    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    public static class Filter
    {
        private String bannerEmail;
        private String bannedEmail;
        private String bannerUsername;
        private String bannedUsername;
        private ReportReason reason;
        private Boolean expired;
    }

    public static Specification<Ban> withFilter(Filter filter) {
        return (Root<Ban> root, CriteriaQuery<?> criteriaQuery, CriteriaBuilder criteriaBuilder) -> {

            List<Predicate> requiredPredicates = new ArrayList<>();
            if(filter.bannerEmail != null)
                requiredPredicates.add(criteriaBuilder.like(root.get("banner").get("email"),filter.bannerEmail));
            if(filter.bannedEmail != null)
                requiredPredicates.add(criteriaBuilder.like(root.get("banned").get("email"),filter.bannedEmail));
            if(filter.bannerUsername != null)
                requiredPredicates.add(criteriaBuilder.like(root.get("banner").get("username"),filter.bannerUsername));
            if(filter.bannedUsername != null)
                requiredPredicates.add(criteriaBuilder.like(root.get("banned").get("username"),filter.bannedUsername));
            if(filter.reason != null)
                requiredPredicates.add(criteriaBuilder.equal(root.get("reason"),filter.reason));
            if(filter.expired != null)
                requiredPredicates.add(criteriaBuilder.equal(root.get("expired"),filter.expired));

            Predicate requiredPredicate = SpecificationsUtils.generatePredicate(criteriaBuilder.isNotNull(root.get("id")),requiredPredicates,criteriaBuilder);
            return criteriaQuery.where(requiredPredicate).getRestriction();
        };
    }
}