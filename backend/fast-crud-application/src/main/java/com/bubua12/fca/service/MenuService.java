package com.bubua12.fca.service;

import com.bubua12.fca.entity.Menu;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 菜单服务（模拟数据，无需数据库）
 *
 * @author bubua12
 * @since 2026/6/9
 */
@Service
public class MenuService {

    /**
     * 获取菜单树
     * 模拟数据库查询，返回树形结构
     */
    public List<Menu> getMenuTree() {
        // 模拟数据库中的扁平菜单数据
        List<Menu> allMenus = buildMockMenus();

        // 将扁平数据转换为树形结构
        return buildTree(allMenus, 0L);
    }

    /**
     * 构建模拟菜单数据
     * 这里的数据模拟了数据库中存储的扁平菜单列表
     */
    private List<Menu> buildMockMenus() {
        List<Menu> menus = new ArrayList<>();

        // ========== 一级菜单 ==========
        menus.add(createMenu(1L, 0L, "首页", "/home", "HomeIcon", 1));
        menus.add(createMenu(2L, 0L, "系统管理", "/system", "SettingIcon", 2));
        menus.add(createMenu(5L, 0L, "关于", "/about", "InfoIcon", 3));

        // ========== 二级菜单 - 系统管理的子菜单 ==========
        menus.add(createMenu(3L, 2L, "用户管理", "/system/user", "UserIcon", 1));
        menus.add(createMenu(4L, 2L, "角色管理", "/system/role", "RoleIcon", 2));
        menus.add(createMenu(6L, 2L, "菜单管理", "/system/menu", "MenuIcon", 3));

        return menus;
    }

    /**
     * 递归构建树形结构
     *
     * @param allMenus 所有菜单的扁平列表
     * @param parentId 父级ID
     * @return 当前parentId下的子菜单树
     */
    private List<Menu> buildTree(List<Menu> allMenus, Long parentId) {
        return allMenus.stream()
                .filter(menu -> menu.getParentId().equals(parentId))
                .peek(menu -> menu.setChildren(buildTree(allMenus, menu.getId())))
                .collect(Collectors.toList());
    }

    private Menu createMenu(Long id, Long parentId, String name, String path, String icon, int sort) {
        Menu menu = new Menu();
        menu.setId(id);
        menu.setParentId(parentId);
        menu.setName(name);
        menu.setPath(path);
        menu.setIcon(icon);
        menu.setSort(sort);
        return menu;
    }
}
