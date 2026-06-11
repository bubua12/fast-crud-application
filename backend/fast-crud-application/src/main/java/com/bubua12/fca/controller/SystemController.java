package com.bubua12.fca.controller;

import com.bubua12.fca.entity.Menu;
import com.bubua12.fca.entity.CommonResult;
import com.bubua12.fca.service.MenuService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 系统管理接口
 *
 * @author bubua12
 * @since 2026/6/9 19:33
 */
@RestController
@RequestMapping("/system")
@RequiredArgsConstructor
public class SystemController {

    private final MenuService menuService;

    /**
     * 获取菜单树
     * GET /system/menu/tree
     */
    @GetMapping("/menu/tree")
    public CommonResult<List<Menu>> getMenuTree() {
        return CommonResult.success(menuService.getMenuTree());
    }

    /**
     * 新增菜单
     * POST /system/menu
     *
     * 请求体示例（新增一个"日志管理"子菜单到系统管理下）：
     * {
     *   "parentId": 2,
     *   "name": "日志管理",
     *   "path": "/system/log",
     *   "icon": "LogIcon",
     *   "component": "system/log/index",
     *   "sort": 4
     * }
     */
    @PostMapping("/menu")
    public CommonResult<String> addMenu(@RequestBody Menu menu) {
        menuService.addMenu(menu);
        return CommonResult.success("新增成功");
    }

    /**
     * 删除菜单
     * DELETE /system/menu/6
     */
    @DeleteMapping("/menu/{id}")
    public CommonResult<String> deleteMenu(@PathVariable Long id) {
        menuService.deleteMenu(id);
        return CommonResult.success("删除成功");
    }
}
