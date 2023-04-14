
-- 查看表结构
SELECT DBMS_METADATA.GET_DDL('TABLE','EMPLOYEE','DMHR') FROM dual;
SP_TABLEDEF('DMHR','EMPLOYEE');

-- 如需查询所有员工信息，可使用 SQL 语句 select *，返回表中所有的列。示例语句如下所示：
SELECT * FROM dmhr.employee;

-- 如需按员工入职时间进行筛选，查询 2015 年 1 月 1 日后入职的员工。示例语句如下所示：
SELECT * FROM dmhr.employee WHERE hire_date > '2015-01-01';

-- 如需查询工资数据为空的员工
-- NULL 不支持加、减、乘、除、大小、相等比较，所有查询结果都为空。
SELECT *  FROM dmhr.employee WHERE commission_pct IS NULL;

-- NULL值处理:转换函数 (nvl)，只能转换 null 值为同类型或者可以隐式转换成同类型的值。
SELECT employee_name, employee_id,NVL(commission_pct, 0) AS commission_pct FROM dmhr.employee;

-- 空值与函数:函数对空值的处理方式各不一样，有些会返回空值
SELECT GREATEST(16,NULL) FROM dual;	-- null

-- 空值与函数:函数对空值的处理方式各不一样，有些会返回预期值
SELECT REPLACE('123456',3,NULL) FROM dual; -- 12456

-- 查找满足多个条件的行
SELECT * FROM dmhr.employee WHERE (department_id = 102
        OR salary > 20000
        OR (department_id = 105 AND salary > 9000));
        
-- 使用列别名
SELECT employee_id AS "员工编号", employee_name AS "员工姓名" FROM dmhr.employee;
SELECT *  FROM (SELECT employee_id emid, email emna, salary sal FROM dmhr.employee) WHERE sal > 10000;

-- 列拼接:使用 || 可以把字符串拼接起来。示例语句如下所示：
SELECT employee_name || ' salary is ' || salary AS col1 FROM dmhr.employee;
-- 列拼接:也可以将需要批量执行的 SQL 语句拼接起来。示例语句如下所示：
SELECT 'TRUNCATE TABLE ' || schema_name || '.' || table_name as delTables
  FROM ALL_TABLES_DIS_INFO
 WHERE schema_name = 'DMHR';
-- 列拼接：concat函数
SELECT CONCAT ('ABC','BCD','DDD','BBB') AS "OUTPUT" FROM DUAL;

-- select 语句中使用条件逻辑
SELECT employee_name, salary,
	CASE
         WHEN salary <= 4000  THEN 'low'
         WHEN salary >= 12000 THEN 'high'
         ELSE 'ok'
       END AS salary_status
FROM dmhr.employee;

-- 如何限制返回的行数
-- 方法一：在查询的时候并不是每次都要求返回所有的行，比如抽查的时候只要求返回 10 行，可以使用伪列 rownum 来过滤。
SELECT *
  FROM (SELECT ROWNUM AS rn, t.*
          FROM dmhr.employee t
         WHERE ROWNUM <= 10)
 WHERE rn = 2;
 
-- 方法二：使用 LIMIT 子句返回两行。示例语句如下所示：
SELECT  * FROM dmhr.employee LIMIT 2;










