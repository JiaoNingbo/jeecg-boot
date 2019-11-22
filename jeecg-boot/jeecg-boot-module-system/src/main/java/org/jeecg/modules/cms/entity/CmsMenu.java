package org.jeecg.modules.cms.entity;

import java.io.Serializable;
import java.util.Date;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.TableField;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;
import com.fasterxml.jackson.annotation.JsonFormat;
import org.springframework.format.annotation.DateTimeFormat;
import org.jeecgframework.poi.excel.annotation.Excel;

/**
 * @Description: CMD菜单
 * @Author: jeecg-boot
 * @Date:   2019-11-23
 * @Version: V1.0
 */
@Data
@TableName("cms_menu")
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@ApiModel(value="cms_menu对象", description="CMD菜单")
public class CmsMenu {
    
	/**id*/
	@TableId(type = IdType.ID_WORKER_STR)
    @ApiModelProperty(value = "id")
	private java.lang.Integer id;
	/**name*/
	@Excel(name = "name", width = 15)
    @ApiModelProperty(value = "name")
	private java.lang.String name;
	/**imageUrl*/
	@Excel(name = "imageUrl", width = 15)
    @ApiModelProperty(value = "imageUrl")
	private java.lang.String imageUrl;
	/**serial*/
	@Excel(name = "serial", width = 15)
    @ApiModelProperty(value = "serial")
	private java.lang.Integer serial;
	/**type*/
	@Excel(name = "type", width = 15)
    @ApiModelProperty(value = "type")
	private java.lang.Integer type;
	/**hide*/
	@Excel(name = "hide", width = 15)
    @ApiModelProperty(value = "hide")
	private java.lang.Integer hide;
}
