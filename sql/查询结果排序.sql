
-- 按字段顺序排序，2指的是查询结果中的第二个字段，3指的查询结果中第三个字段 
SELECT employee_name, hire_date, salary
    FROM dmhr.employee
   WHERE ROWNUM < 5
ORDER BY 2 ASC, 3 DESC;
SELECT employee_name, hire_date, salary
    FROM dmhr.employee
   WHERE ROWNUM < 5
ORDER BY hire_date ASC, salary DESC;


-- 按子串排序，查询结果中包含子串的，示例中第二个为 【尾号】字段
SELECT EMPLOYEE_NAME AS 姓名, SUBSTR (PHONE_NUM, -4) AS 尾号
    FROM dmhr.employee
   WHERE ROWNUM < 5
ORDER BY 2;

-- 字符串替换：对应字符逐个替换：结果 12你好2314567 TRANSLATE(expr,from_string,to_string)
SELECT TRANSLATE ('ab你好bcadefg', 'abcdefg', '1234567') AS new_str FROM DUAL;
-- 如果 to_string 为空，则直接返回空值。示例语句如下所示：
SELECT TRANSLATE('ab你好bcadefg','abcdefg','') AS new_str FROM DUAL;

-- 按数字和字母混合字符串中的字母排序
-- 为了实现混合字符串排序，首先需创建如下视图：
CREATE OR REPLACE VIEW v AS
   SELECT postal_code || ' ' || city_id AS data FROM dmhr.location;
-- 查询视图信息，示例语句如下所示：
SELECT * FROM v;
-- 可以使用 translate 替换功能，把数字和空格都替换为空，然后再进行排序。示例语句如下所示：
  SELECT data, TRANSLATE (data, '- 0123456789', '-') AS oper_type
    FROM v
   WHERE ROWNUM < 5
ORDER BY 2;
-- 根据指定条件排序
-- 如需将工资在 6000~8000 之间的员工排列在靠前位置，以便优先查看。我们可以在查询中新生成一列，实现指定条件排序。示例语句如下所示：
  SELECT job_title AS 职务,
         CASE WHEN min_salary >= 6000 AND min_salary <= 8000 THEN 1 ELSE 2 END
            AS 级别,
         min_salary AS 工资
    FROM dmhr.job
   WHERE ROWNUM < 5
ORDER BY 2, 3;














