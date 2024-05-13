package com.enigma.wmb_sb.specification;

import com.enigma.wmb_sb.model.dto.request.SearchCustomerRequest;
import com.enigma.wmb_sb.model.entity.Customer;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import org.springframework.data.jpa.domain.Specification;

import java.util.ArrayList;
import java.util.List;

public class CustomerSpecification {
    public static Specification<Customer> getSpecification(SearchCustomerRequest request){
        return (root, query, criteriaBuilder) -> {

            List<Predicate> predicates = new ArrayList<>();
            if(request.getName() != null){
                Predicate namePredicate = criteriaBuilder.like(criteriaBuilder.lower(root.get("name")), "%" + request.getName().toLowerCase() + "%");
                predicates.add(namePredicate);
            }

            if(request.getPhone() != null){
                Predicate phoneNumberPredicate = criteriaBuilder.equal(root.get("phoneNumber"), request.getPhone());
                predicates.add(phoneNumberPredicate);
            }

            if(request.getMemberStatus() != null){
                Predicate statusPredicate = criteriaBuilder.equal(root.get("isMember"), request.getMemberStatus());
                predicates.add(statusPredicate);
            }

            return query.where(criteriaBuilder.or(predicates.toArray(new Predicate[]{}))).getRestriction();
        };

    }
}
