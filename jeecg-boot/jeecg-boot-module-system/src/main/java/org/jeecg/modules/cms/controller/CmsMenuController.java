package org.jeecg.modules.demo.org.jeecg.modules.cms.controller;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.jeecg.common.api.vo.Result;
import org.jeecg.common.system.query.QueryGenerator;
import org.jeecg.common.aspect.annotation.AutoLog;
import org.jeecg.common.util.oConvertUtils;
import org.jeecg.modules.demo.org.jeecg.modules.cms.entity.CmsMenu;
import org.jeecg.modules.demo.org.jeecg.modules.cms.service.ICmsMenuService;
import java.util.Date;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import lombok.extern.slf4j.Slf4j;
import org.jeecg.common.system.base.controller.JeecgController;
import org.jeecgframework.poi.excel.ExcelImportUtil;
import org.jeecgframework.poi.excel.def.NormalExcelConstants;
import org.jeecgframework.poi.excel.entity.ExportParams;
import org.jeecgframework.poi.excel.entity.ImportParams;
import org.jeecgframework.poi.excel.view.JeecgEntityExcelView;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.servlet.ModelAndView;
import com.alibaba.fastjson.JSON;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

 /**
 * @Description: CMS菜单
 * @Author: jeecg-boot
 * @Date:   2019-11-23
 * @Version: V1.0
 */
@Slf4j
@Api(tags="CMS菜单")
@RestController
@RequestMapping("/org.jeecg.modules.cms/cmsMenu")
public class CmsMenuController extends JeecgController<CmsMenu, ICmsMenuService> {
	@Autowired
	private ICmsMenuService cmsMenuService;
	
	/**
	 * 分页列表查询
	 *
	 * @param cmsMenu
	 * @param pageNo
	 * @param pageSize
	 * @param req
	 * @return
	 */
	@AutoLog(value = "CMS菜单-分页列表查询")
	@ApiOperation(value="CMS菜单-分页列表查询", notes="CMS菜单-分页列表查询")
	@GetMapping(value = "/list")
	public Result<?> queryPageList(CmsMenu cmsMenu,
								   @RequestParam(name="pageNo", defaultValue="1") Integer pageNo,
								   @RequestParam(name="pageSize", defaultValue="10") Integer pageSize,
								   HttpServletRequest req) {
		QueryWrapper<CmsMenu> queryWrapper = QueryGenerator.initQueryWrapper(cmsMenu, req.getParameterMap());
		Page<CmsMenu> page = new Page<CmsMenu>(pageNo, pageSize);
		IPage<CmsMenu> pageList = cmsMenuService.page(page, queryWrapper);
		return Result.ok(pageList);
	}
	
	/**
	 * 添加
	 *
	 * @param cmsMenu
	 * @return
	 */
	@AutoLog(value = "CMS菜单-添加")
	@ApiOperation(value="CMS菜单-添加", notes="CMS菜单-添加")
	@PostMapping(value = "/add")
	public Result<?> add(@RequestBody CmsMenu cmsMenu) {
		cmsMenuService.save(cmsMenu);
		return Result.ok("添加成功！");
	}
	
	/**
	 * 编辑
	 *
	 * @param cmsMenu
	 * @return
	 */
	@AutoLog(value = "CMS菜单-编辑")
	@ApiOperation(value="CMS菜单-编辑", notes="CMS菜单-编辑")
	@PutMapping(value = "/edit")
	public Result<?> edit(@RequestBody CmsMenu cmsMenu) {
		cmsMenuService.updateById(cmsMenu);
		return Result.ok("编辑成功!");
	}
	
	/**
	 * 通过id删除
	 *
	 * @param id
	 * @return
	 */
	@AutoLog(value = "CMS菜单-通过id删除")
	@ApiOperation(value="CMS菜单-通过id删除", notes="CMS菜单-通过id删除")
	@DeleteMapping(value = "/delete")
	public Result<?> delete(@RequestParam(name="id",required=true) String id) {
		cmsMenuService.removeById(id);
		return Result.ok("删除成功!");
	}
	
	/**
	 * 批量删除
	 *
	 * @param ids
	 * @return
	 */
	@AutoLog(value = "CMS菜单-批量删除")
	@ApiOperation(value="CMS菜单-批量删除", notes="CMS菜单-批量删除")
	@DeleteMapping(value = "/deleteBatch")
	public Result<?> deleteBatch(@RequestParam(name="ids",required=true) String ids) {
		this.cmsMenuService.removeByIds(Arrays.asList(ids.split(",")));
		return Result.ok("批量删除成功！");
	}
	
	/**
	 * 通过id查询
	 *
	 * @param id
	 * @return
	 */
	@AutoLog(value = "CMS菜单-通过id查询")
	@ApiOperation(value="CMS菜单-通过id查询", notes="CMS菜单-通过id查询")
	@GetMapping(value = "/queryById")
	public Result<?> queryById(@RequestParam(name="id",required=true) String id) {
		CmsMenu cmsMenu = cmsMenuService.getById(id);
		return Result.ok(cmsMenu);
	}

  /**
   * 导出excel
   *
   * @param request
   * @param cmsMenu
   */
  @RequestMapping(value = "/exportXls")
  public ModelAndView exportXls(HttpServletRequest request, CmsMenu cmsMenu) {
      return super.exportXls(request, cmsMenu, CmsMenu.class, "CMS菜单");
  }

  /**
   * 通过excel导入数据
   *
   * @param request
   * @param response
   * @return
   */
  @RequestMapping(value = "/importExcel", method = RequestMethod.POST)
  public Result<?> importExcel(HttpServletRequest request, HttpServletResponse response) {
      return super.importExcel(request, response, CmsMenu.class);
  }

}
