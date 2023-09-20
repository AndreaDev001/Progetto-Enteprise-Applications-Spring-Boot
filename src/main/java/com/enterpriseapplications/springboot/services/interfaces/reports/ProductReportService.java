package com.enterpriseapplications.springboot.services.interfaces.reports;

import com.enterpriseapplications.springboot.data.dto.input.create.CreateReportDto;
import com.enterpriseapplications.springboot.data.dto.input.update.UpdateProductDto;
import com.enterpriseapplications.springboot.data.dto.input.update.UpdateReportDto;
import com.enterpriseapplications.springboot.data.dto.output.reports.ProductReportDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface ProductReportService
{
    Page<ProductReportDto> getReports(Long productID, Pageable pageable);
    ProductReportDto createProductReport(CreateReportDto createReportDto,Long productID);
    ProductReportDto updateProductReport(UpdateReportDto updateReportDto);
    void deleteProductReport(Long productReportID);
}
