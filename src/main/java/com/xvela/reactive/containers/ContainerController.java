package com.xvela.reactive.containers;

import com.xvela.reactive.common.controller.ControllerBase;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/containers")
@CrossOrigin(origins = "http://localhost:4200")
public class ContainerController extends ControllerBase<ContainerVo> {

    private ContainerService containerService;

    public ContainerController(ContainerService containerService) {
        super(containerService);
        this.containerService = containerService;
    }

}
