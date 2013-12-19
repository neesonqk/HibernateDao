package org.hibernatedao.pojo;

import org.hibernatedao.annotation.Name;

import javax.persistence.*;

/**
 * Created by Nesson on 12/19/13.
 */
@Entity
@Table(name = "pet")
public class Pet {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;

    @Name
    @Column(unique = true)
    private String name;

}
