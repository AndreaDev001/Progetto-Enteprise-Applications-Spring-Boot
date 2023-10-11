package com.enterpriseapplications.springboot.services.implementations.reports;

import com.enterpriseapplications.springboot.data.dao.ProductDao;
import com.enterpriseapplications.springboot.data.dao.UserDao;
import com.enterpriseapplications.springboot.data.dao.reports.ProductReportDao;
import com.enterpriseapplications.springboot.data.dto.output.reports.ProductReportDto;
import com.enterpriseapplications.springboot.data.entities.Product;
import com.enterpriseapplications.springboot.data.entities.reports.ProductReport;
import com.enterpriseapplications.springboot.data.entities.reports.Report;
import com.enterpriseapplications.springboot.services.GenericTestImp;
import org.junit.Assert;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.hateoas.PagedModel;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.mockito.BDDMockito.given;

@RunWith(MockitoJUnitRunner.class)
@ExtendWith(MockitoExtension.class)
class ProductReportServiceImpTest extends GenericTestImp<ProductReport,ProductReportDto> {

    private ProductReportServiceImp productReportServiceImp;
    @Mock
    private ProductReportDao productReportDao;
    @Mock
    private UserDao userDao;
    @Mock
    private ProductDao productDao;


    @Override
    protected void init() {
        super.init();
        productReportServiceImp = new ProductReportServiceImp(productReportDao,userDao,productDao,modelMapper,pagedResourcesAssembler);
        List<Report> reports = ReportServiceImpTest.createReports();
        Product firstProduct = Product.builder().id(UUID.randomUUID()).build();
        Product secondProduct = Product.builder().id(UUID.randomUUID()).build();
        this.firstElement = new ProductReport(reports.get(0));
        this.secondElement = new ProductReport(reports.get(1));
        this.firstElement.setProduct(firstProduct);
        this.secondElement.setProduct(secondProduct);
        this.elements = List.of(firstElement,secondElement);
    }

    @BeforeEach
    public void before() {
        init();
    }


    public boolean valid(ProductReport productReport, ProductReportDto productReportDto) {
        Assert.assertTrue(ReportServiceImpTest.baseValid(productReport,productReportDto));
        Assert.assertEquals(productReport.getProduct().getId(),productReportDto.getProduct().getId());
        return true;
    }

    @Test
    void getReport() {
        given(this.productReportDao.findById(this.firstElement.getId())).willReturn(Optional.of(firstElement));
        given(this.productReportDao.findById(this.secondElement.getId())).willReturn(Optional.of(secondElement));
        ProductReportDto firstReport = this.productReportServiceImp.getReport(this.firstElement.getId());
        ProductReportDto secondReport = this.productReportServiceImp.getReport(this.secondElement.getId());
        Assert.assertTrue(valid(this.firstElement,firstReport));
        Assert.assertTrue(valid(this.secondElement,secondReport));
    }

    @Test
    void getReports() {
        PageRequest pageRequest = PageRequest.of(0,20);
        given(this.productReportDao.findAll(pageRequest)).willReturn(new PageImpl<>(elements,pageRequest,2));
        PagedModel<ProductReportDto> productReports = this.productReportServiceImp.getReports(pageRequest);
        Assert.assertTrue(compare(elements,productReports.getContent().stream().toList()));
    }

    @Test
    void getProductReports() {
        PageRequest pageRequest = PageRequest.of(0,20);
        Product product = Product.builder().id(UUID.randomUUID()).build();
        given(this.productReportDao.getProductReports(product.getId(),pageRequest)).willReturn(new PageImpl<>(elements,pageRequest,2));
        PagedModel<ProductReportDto> productReports = this.productReportServiceImp.getReports(product.getId(),pageRequest);
        Assert.assertTrue(compare(elements,productReports.getContent().stream().toList()));
    }
    @Test
    void updateProductReport() {
    }
}