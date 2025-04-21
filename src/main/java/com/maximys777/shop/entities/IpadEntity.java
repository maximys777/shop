package com.maximys777.shop.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "ipads")
public class IpadEntity extends ProductEntity {

    @Column(name = "ipad_model")
    private String ipadModel;

    @Column(name = "ipad_color")
    private String ipadColor;

    @Column(name = "ipad_processor")
    private String ipadProcessor;

    @Column(name = "ipad_capacity")
    private Integer ipadCapacity;

    @Column(name = "ipad_os")
    private String ipadOs;

    @Column(name = "ipad_diagonal")
    private Integer ipadDiagonal;

    @Column(name = "ipad_model_year")
    private Integer ipadModelYear;
}
