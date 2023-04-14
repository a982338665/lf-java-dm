
-- 聚集函数
  SELECT job_id,
         AVG (salary) AS 平均值,
         MIN (salary) AS 最小值,
         MAX (salary) AS 最大值,
         SUM (salary) AS 工资合计,
         COUNT (*) AS 总行数
    FROM dmhr.employee
GROUP BY job_id;
-- 当表中没有数据时，不加 group by 会返回一行数据，加了 group by 无数据返回。
-- 建立空表，示例语句如下所示：
CREATE TABLE dmhr.employee2 AS SELECT * FROM dmhr.employee WHERE 1 = 2;
-- 不加 group by，示例语句如下所示：
SELECT COUNT (*) AS cnt, SUM (salary) AS sum_sal
  FROM dmhr.employee2
 WHERE job_id = 11;
-- 输出结果：
-- 没有 group by
-- 增加 group by，示例语句如下所示：
  SELECT COUNT (*) AS cnt, SUM (salary) AS sum_sal
    FROM dmhr.employee2
   WHERE job_id = 11
GROUP BY job_id;


-- 生成累计和
-- 使用分析函数 sum (…) over (order by…) 可以生成累计和。
-- 例如公司查看用人成本，需要对用员工的工资进行累加，了解员工人数与工资支出之间的对应关系。示例语句如下所示：
-- //按员工编号排序对员工的工资进行累加
SELECT employee_id AS 编号,
       employee_name AS 姓名,
       salary AS 人工成本,
       SUM (salary) OVER (ORDER BY employee_id) AS 成本累计
  FROM dmhr.employee
 WHERE job_id = 11;
 
-- 通过结果可以看出，分析函数“sum (salary) over (order by employee_id)”的结果 (168000) 是排序“over (order by employee_id)”后第一行到当前行的所有工资之和。
-- 为了形象地说明这一点，我们用 listagg 模拟出每一行是哪些值相加。示例语句如下所示：
-- //使用 listagg 函数模拟员工总成本的累加值
  SELECT employee_id                                                AS 编号,
         employee_name                                              AS 姓名,
         salary                                                     AS 人工成本,
         SUM (salary) OVER (ORDER BY employee_id)                   AS 成本累计,
         (SELECT LISTAGG (salary, '+') WITHIN GROUP (ORDER BY employee_id)
            FROM dmhr.employee b
           WHERE b.job_id = 11 AND b.employee_id <= a.employee_id)  计算公式
    FROM dmhr.employee a
   WHERE job_id = 11
ORDER BY employee_id;