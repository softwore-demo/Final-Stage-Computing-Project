package com.bysj.office.common.properties;

import com.bysj.office.common.entity.ImageType;
import lombok.Data;


@Data
public class ValidateCodeProperties {


    private Long time = 120L;

    private String type = ImageType.PNG;

    private Integer width = 130;

    private Integer height = 48;

    private Integer length = 4;

    private Integer charType = 2;
}
