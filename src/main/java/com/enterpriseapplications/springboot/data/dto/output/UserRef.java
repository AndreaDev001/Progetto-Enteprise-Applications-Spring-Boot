package com.enterpriseapplications.springboot.data.dto.output;


import com.enterpriseapplications.springboot.controllers.UserController;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@EqualsAndHashCode(callSuper = false)
@Data
@AllArgsConstructor
public class UserRef extends GenericOutput<UserRef>
{
    private Long id;
    private String username;

    @Override
    void addLinks() {
        this.add(linkTo(methodOn(UserController.class).getUserDetails(id)).withSelfRel());
    }
}
