package com.example.springwebfluxrest.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

/**
 * @author <a href="pulkit.aggarwal@upgrad.com">Pulkit Aggarwal</a>
 * @version 1.0
 * @since 03/03/22
 */
@Data
@Document
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Vendor {
    @Id
    private String id;

    private String firstName;

    private String lastName;
}
