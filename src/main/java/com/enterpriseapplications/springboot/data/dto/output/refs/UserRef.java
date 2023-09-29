package com.enterpriseapplications.springboot.data.dto.output.refs;


import com.enterpriseapplications.springboot.controllers.UserController;
import com.enterpriseapplications.springboot.data.dto.output.GenericOutput;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.util.UUID;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@EqualsAndHashCode(callSuper = false)
@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserRef extends GenericOutput<UserRef>
{
    private UUID id;
    private String username;

    @Override
    public void addLinks(Object... params) {
        this.add(linkTo(methodOn(UserController.class).getUserDetails(id)).withRel("details"));
    }
}
