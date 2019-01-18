package com.xvela.reactive.containers;

import com.xvela.reactive.common.entity.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;

@Data
@Entity
@NoArgsConstructor
@EqualsAndHashCode(callSuper = false)
public class Container extends BaseEntity {

    private String containerName;

    private String visitState;

    private String freightKind;

    private String category;

    private int grossWeight;
}
