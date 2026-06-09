package com.bubua12.fca.controller;

import com.bubua12.fca.entity.Menu;
import com.bubua12.fca.entity.CommonResult;
import com.bubua12.fca.service.MenuService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
}
