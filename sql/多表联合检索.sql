-- 使用 union all 替换 or，执行计划更高效，出现重复行时，使用 union 去重。
SELECT *
  FROM dmhr.employee
 WHERE employee_id = 2002
UNION ALL
SELECT *
  FROM dmhr.employee
 WHERE employee_name = '常鹏程';
 
-- 差集函数
-- EXCEPT 用于从一个表（原表）里查找出某个目标表里不存在的值。
-- 创建 dept 表，示例语句如下所示：
CREATE TABLE dmhr.dept AS SELECT department_id FROM dmhr.employee;
-- 插入 employee 表中不存在的部门号，示例语句如下所示：
INSERT INTO dmhr.dept VALUES (888),(999),(3004); COMMIT;
-- 得出 department 表不存在的部门号，示例语句如下所示：
SELECT department_id FROM dmhr.dept EXCEPT SELECT department_id FROM dmhr.employee;

-- IN、NOT IN、EXISTS
-- 查询含有 null 值的行时，如果包含 IN、NOT IN 要注意两者的区别。IN 相当于 OR, 而 NOT IN 相当于 AND。示例语句如下所示：
SELECT * FROM dmhr.employee WHERE employee_id IN (1002, 1005, NULL);
-- 返回 2 行数据记录，示例语句如下所示：
SELECT * FROM dmhr.employee WHERE employee_id NOT IN (1002, 1005, NULL);
-- 返回记录为空，因为 NOT IN 的逻辑是 1002 AND 1005 AND NULL。当 NOT IN 后面跟的子查询返回的列存在 NULL 值，可能得不到正确的结果。

-- 例如，查找员工所在部门编号，在部门表中不存在的数据。
CREATE TABLE dmhr.emp
AS
   SELECT employee_id,
          employee_name,
          identity_card,
          salary,
          department_id
     FROM dmhr.employee
    WHERE employee_id IN (1109,1110,1111,1112,1113);

UPDATE dmhr.emp
   SET department_id = 999
 WHERE employee_id = 1112;

UPDATE dmhr.emp
   SET department_id = NULL
 WHERE employee_id = 1113;

COMMIT;
-- 执行如下 SQL，返回结果为空。
SELECT * 
  FROM dmhr.emp t WHERE t.department_id NOT IN (SELECT department_id FROM dmhr.dept);
-- 执行如下 SQL，返回结果不为空。
SELECT *
  FROM dmhr.emp t
 WHERE NOT EXISTS
          (SELECT 1
             FROM dmhr.dept t1
            WHERE t.department_id = t1.department_id);

-- 连接类型:连接包括：内连接、左连接、右连接、全连接、自连接 5 种类型，以上连接类型 DM 数据库都支持
-- 数据准备，示例语句如下所示：
CREATE TABLE dmhr.join_emp
AS
   SELECT employee_name, department_id
     FROM dmhr.employee
    WHERE     employee_id IN (  SELECT MIN (employee_id)
                                  FROM dmhr.employee
                              GROUP BY department_id)
          AND ROWNUM < 10;

INSERT INTO dmhr.join_emp
     VALUES ('DM2020', 999);

COMMIT;
SELECT * FROM dmhr.join_emp;
-- 内连接:结果完全满足连接条件的记录。例如，如需显示员工名称和对应的部门名称。
-- 方法一：
SELECT je.employee_name, d.department_name
  FROM dmhr.join_emp je, dmhr.department d
 WHERE je.department_id = d.department_id;
-- 方法二：
SELECT je.employee_name, d.department_name
  FROM dmhr.join_emp je
       JOIN dmhr.department d ON (je.department_id = d.department_id);
-- 左外连接:结果不仅包含满足条件的记录，还包含位于左表中不满足条件的记录，此时右表的记录显示为 NULL。示例语句如下所示：
SELECT je.employee_name, d.department_name
  FROM dmhr.join_emp je
       LEFT OUTER JOIN dmhr.department d
          ON (je.department_id = d.department_id);
-- 右外连接：结果不仅包含满足条件的记录，还包含位于右表中不满足条件的记录，对应的左表的记录显示为 NULL。示例语句如下所示：
SELECT je.employee_name, d.department_name
  FROM dmhr.join_emp je
       RIGHT OUTER JOIN dmhr.department d
          ON (je.department_id = d.department_id);

-- 全外连接：结果不仅包含满足条件的记录，还会包含位于两边表中所有不满足条件的记录，对应的两边表的记录显示为 NULL。示例语句如下所示：
SELECT je.employee_name, d.department_name
  FROM dmhr.join_emp je
       FULL OUTER JOIN dmhr.department d
          ON (je.department_id = d.department_id);

-- 自连接：表和自身进行连接。例如，需查询显示所有员工的部门经理的名字。示例语句如下所示：
SELECT je.employee_name, je.department_id, d.employee_name
  FROM dmhr.employee je, dmhr.employee d
 WHERE (je.manager_id = d.employee_id);

-- 聚集与内连接：首先建立案例用表，示例语句如下所示：
CREATE TABLE dmhr.emp_bonus
(
   employee_id   NUMBER,
   received      DATE,
   TYPE          NUMBER
);
INSERT INTO dmhr.emp_bonus
     VALUES (1137, '2020-1-1 8:00', 1);
INSERT INTO dmhr.emp_bonus
     VALUES (1137, '2020-3-1 8:00', 2);
INSERT INTO dmhr.emp_bonus
     VALUES (1138, '2020-1-1 8:00', 3);
INSERT INTO dmhr.emp_bonus
     VALUES (1139, '2020-1-1 8:00', 1);
INSERT INTO dmhr.emp_bonus
     VALUES (1140, '2020-1-1 8:00', 1);
COMMIT;
-- 以上是员工奖金发放表，type 列决定了奖金的数额。若 type=1，则奖金是工资的 10%；若 type=2，则奖金是工资的 20%; type=3，则奖金是工资的 30%。要求返回上述（部门编号是 105）员工工资和奖金的总额。
-- 先关联再聚合，示例语句如下所示：
SELECT e.employee_id,e.employee_name,e.salary,e.department_id,e.salary
       * CASE WHEN eb.TYPE = 1 THEN .1 WHEN eb.TYPE = 2 THEN .2 ELSE .3 END
          AS bonus
  FROM dmhr.employee e, dmhr.emp_bonus eb
 WHERE e.employee_id = eb.employee_id;
-- 聚合后，示例语句如下所示：
  SELECT department_id, SUM (salary) AS total_sal, SUM (bonus) AS total_bonus
    FROM (SELECT e.employee_id,e.employee_name,e.salary,e.department_id,e.salary
                 * CASE
                      WHEN eb.TYPE = 1 THEN .1
                      WHEN eb.TYPE = 2 THEN .2
                      ELSE .3
                   END
                    AS bonus
            FROM dmhr.employee e, dmhr.emp_bonus eb
           WHERE e.employee_id = eb.employee_id) y
GROUP BY y.department_id;
-- 聚合后奖金总额正确，工资总额不对，应该为 38560。示例语句如下所示：
SELECT SUM (SALARY)
  FROM dmhr.employee
 WHERE employee_id IN (1137,1138,1139,1140);
-- 因员工陈国红有两次奖励，其工资重复计算了两次。正确的做法是先把奖金按员工汇总（先聚合），再与员工表关联。示例语句如下所示：
  SELECT department_id,
         SUM (salary) AS total_sal,
         SUM (bonus * salary) AS total_bonus
    FROM dmhr.employee e,
         (  SELECT employee_id,
                   SUM (
                      CASE
                         WHEN TYPE = 1 THEN .1
                         WHEN TYPE = 2 THEN .2
                         ELSE .3
                      END)
                      AS bonus
              FROM dmhr.emp_bonus
          GROUP BY employee_id) eb
   WHERE e.employee_id = eb.employee_id
GROUP BY e.department_id;
-- 聚集与外连接
-- 若要返回所有部门员工的工资和奖金且奖金数据中只包含部门号为 105 的数据，使用 LEFT JOIN 可以实现。示例语句如下所示：
  SELECT department_id,
         SUM (salary) AS total_sal,
         SUM (bonus * salary) AS total_bonus
    FROM dmhr.employee e
         LEFT JOIN
         (  SELECT employee_id,
                   SUM (
                      CASE
                         WHEN TYPE = 1 THEN .1
                         WHEN TYPE = 2 THEN .2
                         ELSE .3
                      END)
                      AS bonus
              FROM dmhr.emp_bonus
          GROUP BY employee_id) eb
            ON e.employee_id = eb.employee_id
GROUP BY e.department_id
ORDER BY 1;

-- 比较两个表差异的全外连接
-- 准备两张表，表一：水果表 (fruits)；表二：颜色表 (colors)。示例语句如下所示：
--创建水果表
CREATE TABLE dmhr.fruits
(
   fruit   VARCHAR (12),
   f_num   NUMBER
);
INSERT INTO dmhr.fruits
     VALUES ('APPLE', 1)
,('BANANA',2),('CHERRY',3),
('GRAPE',4),('ORANGE', 5),('STRAWBERRY',1);
COMMIT;

--创建颜色表
CREATE TABLE dmhr.colors
(
   c_num   NUMBER,
   color   VARCHAR (12)
);

INSERT INTO dmhr.colors
     VALUES (1, 'RED')
,(2,'YELLOW'),(1,'GREEN'),(5,'ORANGE'),
(6,'WHITE');
COMMIT;
-- 两张表按 f_num=c_cum 的条件做全外连接。示例语句如下所示：
SELECT f.*, c.* 
  FROM dmhr.fruits f FULL OUTER JOIN dmhr.colors c ON (f.f_num = c.c_num);








