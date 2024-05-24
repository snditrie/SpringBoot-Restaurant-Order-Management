package com.enigma.wmb_sb.specification;

import com.enigma.wmb_sb.model.dto.request.SearchBillRequest;
import com.enigma.wmb_sb.model.entity.Bill;
import jakarta.persistence.criteria.Predicate;
import org.springframework.data.jpa.domain.Specification;

import java.util.ArrayList;
import java.util.List;

public class BillSpecification {
    public static Specification<Bill> getSpecification(SearchBillRequest request) {
        return (root, query, criteriaBuilder) -> {

            List<Predicate> predicates = new ArrayList<>();
            if(request.getDate() != null) {
                Predicate datePredicate = criteriaBuilder.equal(root.get("date"), request.getDate());
                predicates.add(datePredicate);
            }

            return query.where(predicates.toArray(new Predicate[]{})).getRestriction();
        };

    }
}
