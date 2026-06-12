package com.bubua12.fca.service;

import com.bubua12.fca.entity.Menu;
import com.bubua12.fca.mapper.MenuMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

/**
 * 菜单服务（从数据库获取数据）
 *
 * @author bubua12
 * @since 2026/6/9
 */
@Service
@RequiredArgsConstructor
public class MenuService {

    private final MenuMapper menuMapper;

    public void updateMenu(Menu menu) {
        menuMapper.updateById(menu);
    }

    /**
     * 获取菜单树
     * 1. 从数据库查询所有菜单（扁平列表）
     * 2. 将扁平列表转换为树形结构
     */
    public List<Menu> getMenuTree() {
        // 从数据库查询所有菜单，按 sort 排序
        List<Menu> allMenus = menuMapper.selectList(null);

        // 将扁平数据转换为树形结构
        return buildTree(allMenus, 0L);
    }

    /**
     * 新增菜单
     */
    public void addMenu(Menu menu) {
        menuMapper.insert(menu);
    }

    /**
     * 删除菜单
     */
    public void deleteMenu(Long id) {
        menuMapper.deleteById(id);
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
}
