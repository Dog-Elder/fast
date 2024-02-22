
-- 初始化菜单
INSERT INTO sys_menu(`name`, `parent_id`, `order_num`, `url`, `target`, `menu_type`, `visible`, `is_refresh`, `perms`, `icon`, `create_by`, `create_time`, `update_by`, `update_time`, `remark`, `version`, `del_flag`)VALUES ('体重记录用户', 0, 0, 'manage/weighUser/index', 'menuItem', 'C', '1', '0', 'manage.weighUser.page', '#', '1', now(), '', now(), '', 1, '0');

INSERT INTO sys_menu(`name`, `parent_id`, `order_num`, `url`, `target`, `menu_type`, `visible`, `is_refresh`, `perms`, `icon`, `create_by`, `create_time`, `update_by`, `update_time`, `remark`, `version`, `del_flag`)VALUES ('体重记录用户查看', (SELECT max(id) from (SELECT * from sys_menu ) as temp where `name` = '体重记录用户'), 6, '', 'menuItem', 'F', '1', '0', 'manage.weighUser.page', '#', '1', now(), '', now(), '', 1, '0');
INSERT INTO sys_menu(`name`, `parent_id`, `order_num`, `url`, `target`, `menu_type`, `visible`, `is_refresh`, `perms`, `icon`, `create_by`, `create_time`, `update_by`, `update_time`, `remark`, `version`, `del_flag`)VALUES ('体重记录用户新增', (SELECT max(id) from (SELECT * from sys_menu ) as temp where `name` = '体重记录用户'), 5, '', 'menuItem', 'F', '1', '0', 'manage.weighUser.save', '#', '1', now(), '', now(), '', 1, '0');
INSERT INTO sys_menu(`name`, `parent_id`, `order_num`, `url`, `target`, `menu_type`, `visible`, `is_refresh`, `perms`, `icon`, `create_by`, `create_time`, `update_by`, `update_time`, `remark`, `version`, `del_flag`)VALUES ('体重记录用户详情', (SELECT max(id) from (SELECT * from sys_menu ) as temp where `name` = '体重记录用户'), 4, '', 'menuItem', 'F', '1', '0', 'manage.weighUser.info', '#', '1', now(), '', now(), '', 1, '0');
INSERT INTO sys_menu(`name`, `parent_id`, `order_num`, `url`, `target`, `menu_type`, `visible`, `is_refresh`, `perms`, `icon`, `create_by`, `create_time`, `update_by`, `update_time`, `remark`, `version`, `del_flag`)VALUES ('体重记录用户修改', (SELECT max(id) from (SELECT * from sys_menu ) as temp where `name` = '体重记录用户'), 3, '', 'menuItem', 'F', '1', '0', 'manage.weighUser.update', '#', '1', now(), '', now(), '', 1, '0');
INSERT INTO sys_menu(`name`, `parent_id`, `order_num`, `url`, `target`, `menu_type`, `visible`, `is_refresh`, `perms`, `icon`, `create_by`, `create_time`, `update_by`, `update_time`, `remark`, `version`, `del_flag`)VALUES ('体重记录用户删除', (SELECT max(id) from (SELECT * from sys_menu ) as temp where `name` = '体重记录用户'), 2, '', 'menuItem', 'F', '1', '0', 'manage.weighUser.delete', '#', '1', now(), '', now(), '', 1, '0');
INSERT INTO sys_menu(`name`, `parent_id`, `order_num`, `url`, `target`, `menu_type`, `visible`, `is_refresh`, `perms`, `icon`, `create_by`, `create_time`, `update_by`, `update_time`, `remark`, `version`, `del_flag`)VALUES ('体重记录用户导出', (SELECT max(id) from (SELECT * from sys_menu ) as temp where `name` = '体重记录用户'), 1, '', 'menuItem', 'F', '1', '0', 'manage.weighUser.export', '#', '1', now(), '', now(), '', 1, '0');
