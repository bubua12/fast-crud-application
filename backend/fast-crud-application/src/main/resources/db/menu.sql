-- ============================================================
-- 菜单表 sys_menu
-- ============================================================
-- 设计说明：
--   采用"邻接表"模型，通过 parent_id 字段表示父子关系
--   parent_id = 0 表示顶级菜单
--   前端通过 GET /system/menu/tree 获取树形结构
-- ============================================================

CREATE DATABASE IF NOT EXISTS `fca` DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci;
USE `fca`;

DROP TABLE IF EXISTS `sys_menu`;
CREATE TABLE `sys_menu` (
    `id`        BIGINT       NOT NULL AUTO_INCREMENT COMMENT '菜单ID（主键，自增）',
    `parent_id` BIGINT       NOT NULL DEFAULT 0      COMMENT '父菜单ID（0 = 顶级菜单）',
    `name`      VARCHAR(64)  NOT NULL                COMMENT '菜单名称（页面上显示的文字）',
    `path`      VARCHAR(128) NOT NULL                COMMENT '路由路径（如 /home、/system/user）',
    `icon`      VARCHAR(64)  DEFAULT NULL            COMMENT '图标标识（前端根据此字段渲染图标）',
    `component` VARCHAR(128) DEFAULT NULL            COMMENT '前端组件路径（对应 src/views/ 下的文件，如 system/user/index）',
    `sort`      INT          NOT NULL DEFAULT 0      COMMENT '排序号（数字越小越靠前）',
    `create_time` DATETIME   DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    `update_time` DATETIME   DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='系统菜单表';

-- ============================================================
-- 初始数据（和原来 mock 数据一致）
-- ============================================================

-- 一级菜单
INSERT INTO `sys_menu` (`id`, `parent_id`, `name`, `path`, `icon`, `component`, `sort`) VALUES
(1, 0, '首页',     '/home',   'HomeIcon',    'Home',            1),
(2, 0, '系统管理', '/system', 'SettingIcon', NULL,              2),
(5, 0, '关于',     '/about',  'InfoIcon',    'About',           3);

-- 二级菜单（系统管理的子菜单）
INSERT INTO `sys_menu` (`id`, `parent_id`, `name`, `path`, `icon`, `component`, `sort`) VALUES
(3, 2, '用户管理', '/system/user', 'UserIcon', 'system/user/index', 1),
(4, 2, '角色管理', '/system/role', 'RoleIcon', 'system/role/index', 2),
(6, 2, '菜单管理', '/system/menu', 'MenuIcon', 'system/menu/index', 3);
