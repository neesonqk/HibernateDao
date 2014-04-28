package test.test.pojo;

import org.hibernatedao.annotation.Name;

import javax.persistence.*;

/**
 * Created by Nesson on 12/19/13.
 */
@Entity
@Table(name = "pet")
public class Pet {

    public Pet(){}

    public Pet(String name){
        this.name = name;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;

    @Name
    @Column(unique = true)
    private String name;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "master_id", nullable = true)
    private Master master;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Master getMaster() {
        return master;
    }

    public void setMaster(Master master) {
        this.master = master;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
