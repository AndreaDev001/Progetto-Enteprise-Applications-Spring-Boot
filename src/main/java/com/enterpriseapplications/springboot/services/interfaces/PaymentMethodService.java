package com.enterpriseapplications.springboot.services.interfaces;

import com.enterpriseapplications.springboot.data.dto.output.PaymentMethodDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.hateoas.PagedModel;

import java.util.UUID;

public interface PaymentMethodService
{
    PagedModel<PaymentMethodDto> getPaymentMethods(Pageable pageable);
    PagedModel<PaymentMethodDto> getPaymentMethods(UUID ownerID, Pageable pageable);
    PagedModel<PaymentMethodDto> getPaymentMethodsByBrand(UUID ownerID,String brand,Pageable pageable);
    PagedModel<PaymentMethodDto> getPaymentMethodsByCountry(UUID ownerID,String country,Pageable pageable);
    PagedModel<PaymentMethodDto> getPaymentMethodsByHolderName(UUID ownerID,String name,Pageable pageable);
    void deletePaymentMethod(UUID paymentMethodID);
}
