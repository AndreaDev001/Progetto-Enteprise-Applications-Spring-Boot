package com.enterpriseapplications.springboot.data.dao;


import com.enterpriseapplications.springboot.data.entities.PaymentMethod;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface PaymentMethodDao extends JpaRepository<PaymentMethod, UUID>
{
    @Query("select p from PaymentMethod p where p.owner.id = :requiredID")
    Page<PaymentMethod> getPaymentMethods(@Param("requiredID") UUID ownerID, Pageable pageable);
    @Query("select p from PaymentMethod p where p.owner.id = :requiredID and p.brand = :requiredBrand")
    Page<PaymentMethod> getPaymentMethodsByBrand(@Param("requiredID") UUID ownerID,@Param("requiredBrand") String brand,Pageable pageable);
    @Query("select p from PaymentMethod p where p.owner.id = :requiredID and p.country = :requiredCountry")
    Page<PaymentMethod> getPaymentMethodsByCountry(@Param("requiredID") UUID ownerID,@Param("requiredCountry") String country,Pageable pageable);
    @Query("select p from PaymentMethod p where p.owner.id = :requiredID and p.holderName = :requiredName")
    Page<PaymentMethod> getPaymentMethodsByHolderName(@Param("requiredID") UUID ownerID,@Param("requiredName") String name,Pageable pageable);
}
