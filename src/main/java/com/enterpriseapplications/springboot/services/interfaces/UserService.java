package com.enterpriseapplications.springboot.services.interfaces;

import com.enterpriseapplications.springboot.data.dto.input.update.UpdateUserDto;
import com.enterpriseapplications.springboot.data.UserDetailsDto;
import com.enterpriseapplications.springboot.data.entities.User;
import com.enterpriseapplications.springboot.data.entities.enums.UserVisibility;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.hateoas.PagedModel;

public interface UserService
{
    UserDetailsDto getUserDetails(Long userID);
    UserDetailsDto updateUser(UpdateUserDto updateUserDto);
    PagedModel<UserDetailsDto> getUsers(Pageable pageable);
    PagedModel<UserDetailsDto> getUsersBySpec(Specification<User> specification, Pageable pageable);
    UserVisibility[] getVisibilities();
    void deleteUser(Long userID);
}
