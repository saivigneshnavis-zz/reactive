package com.xvela.reactive.containers;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.xvela.reactive.common.api.BaseVo;
import lombok.Builder;
import lombok.Value;
import lombok.experimental.Wither;

@Value
@Wither
@Builder(toBuilder = true)
@JsonDeserialize(builder = ContainerVo.ContainerVoBuilder.class)
public class ContainerVo implements BaseVo<ContainerVo> {

    private String uuid;

    private String containerName;

    private String visitState;

    private String freightKind;

    private String category;

    private int grossWeight;
}
