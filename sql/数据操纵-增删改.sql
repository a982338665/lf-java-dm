-- 插入新行
INSERT INTO dmhr.employee VALUES 
(11146,'Dameng','220103198501166001','whdm@dameng.com','13712346385',
'2020-10-26','52',9500.00,0,11005,1105);

-- 插入多行
CREATE TABLE dmhr.test AS
   SELECT employee_id,
          employee_name,
          identity_card,
          salary,
          department_id
     FROM dmhr.employee
    WHERE 1 = 2;
-- 插入多行并查询，示例语句如下所示：
INSERT INTO dmhr.test
     VALUES
(1109, '程东生', '410107197103252999', 4400, 102),
(1110, '王金玉', '410107197103258999', 4300, 102),
(1111, '程东生', '410107197103252999', 4400, 102);
COMMIT;
SELECT * FROM dmhr.test;

-- 选择性插入行可以按指定列插入行，未指定值的列上若定义了默认值，则插入默认值。没有指定默认值，为 NULL，则插入 NULL 值。示例语句如下所示：
-- 创建测试表
CREATE TABLE dmhr.t1
(
   id         INTEGER PRIMARY KEY,
   name       VARCHAR (12) DEFAULT 'dm2020',
   class_id   INTEGER NOT NULL,
   tp         TIMESTAMP DEFAULT SYSDATE
);
-- 插入数据
INSERT INTO dmhr.t1 (id, class_id) VALUES (1, 103);

-- 复制表结构 如需快速复制表结构且不需要数据，示例语句如下所示：
-- 使用 SP_TABLEDEF 过程查看 t2 的结构，所有定义在 t1 列上的约束均没有被新表继承。
CREATE TABLE dmhr.t2 AS SELECT * FROM dmhr.t1 WHERE 1 = 0;
-- 需使用如下语句添加各类约束：
ALTER TABLE dmhr.t2  ADD PRIMARY KEY (id);
ALTER TABLE dmhr.t2  ALTER COLUMN name SET DEFAULT 'dm2020';
ALTER TABLE dmhr.t2  ALTER COLUMN class_id SET NOT NULL;
ALTER TABLE dmhr.t2  ALTER COLUMN tp SET DEFAULT SYSDATE;

-- 多表插入
-- 使用上述表 t1 和 t2 演示多表插入。为了方便演示先创建一个序列，示例语句如下所示：
CREATE SEQUENCE dmhr.seq_id START WITH 1 INCREMENT BY 1 MAXVALUE 20000 NOCYCLE;
-- 一次性向两张表中插入多条数据，存在默认值同样可以省略。示例语句如下所示：
INSERT ALL
  INTO dmhr.t1 (id,name,class_id,tp)
  INTO dmhr.t2 (id,name,class_id,tp)
SELECT dmhr.seq_id.nextval， DBMS_RANDOM.STRING('X',4),
CEIL(DBMS_RANDOM.VALUE(100,106)), SYSDATE
FROM DUAL CONNECT BY LEVEL <10;
-- 注意 用 managerid=null 查询无效（测试表中没有 null 数据，需插入 null）


-- MERGE INTO 操作
-- 使用 MERGE INTO 语法可合并 UPDATE 和 INSERT 语句。通过 MERGE 语句，根据一张表（或视图）的连接条件对另外一张表（或视图）进行查询，连接条件匹配上的进行 UPDATE（可能含有 DELETE），无法匹配的执行 INSERT。
-- 使用 MERGE 可以实现记录“存在则 update，不存在则 insert”的逻辑。
-- 创建两张表，示例语句如下所示：
CREATE TABLE dmhr.dup_emp
(
   employee_id     INTEGER PRIMARY KEY,
   employee_name   VARCHAR2 (12),
   identity_card   VARCHAR2 (18),
   salary          INTEGER,
   department_id   INTEGER
);
CREATE TABLE dmhr.emp_salary
(
   employee_id   INTEGER PRIMARY KEY,
   new_salary    INTEGER
);
INSERT INTO dmhr.dup_emp
   SELECT employee_id,employee_name,identity_card,salary,department_id
     FROM dmhr.employee
    WHERE department_id = 102 AND salary < 9000;
INSERT INTO dmhr.emp_salary
   SELECT employee_id, salary + 2000 FROM dmhr.dup_emp;
INSERT INTO dmhr.emp_salary
     VALUES (1108, 4100);
     
-- 匹配到的更新，否则新增，如果 emp_salary 中有多行和目标表匹配成功，将会报如下图所示错误：没有一组稳定的行
MERGE INTO dmhr.dup_emp
     USING dmhr.emp_salary
        ON (dmhr.dup_emp.employee_id = dmhr.emp_salary.employee_id)
WHEN MATCHED
THEN
   UPDATE SET dmhr.dup_emp.salary = dmhr.emp_salary.new_salary
WHEN NOT MATCHED
THEN
   INSERT VALUES (dmhr.emp_salary.employee_id,
                      'dm2020',
                      410107197103257999,
                      dmhr.emp_salary.new_salary,
                      102);

-- 处理违反参照完整性的记录：有外键约束的，删除外键或者添加记录
-- 删除重复记录：
-- 实际工作中经常遇到表内包含重复数据的情况，下面介绍几种删除重复数据的方法。
-- 准备数据，使用上面创建的 dup_emp 表，插入重复数据:
INSERT INTO dmhr.dup_emp VALUES
(1109, '程东生', '410107197103252999', 4400, 102),
(1110, '王金玉', '410107197103258999', 4300, 102),
(1111, '程东生', '410107197103252999', 4400, 102);
COMMIT;	
-- 方法一：通过 group by + having 子句分组查询的方式，查找员工名称相同的记录，示例语句如下所示：
SELECT employee_name, count(*) FROM dmhr.dup_emp GROUP BY employee_name HAVING COUNT(*) > 1;
-- 方法二：通过 group by + rowid 的方式，查找员工名称重复的记录，示例语句如下所示：
SELECT * FROM dmhr.dup_emp
 WHERE ROWID NOT IN (  SELECT MAX (ROWID) FROM dmhr.dup_emp GROUP BY employee_name);
-- 可在查找到重复记录后直接删除，示例语句如下所示：
DELETE FROM dmhr.dup_emp WHERE ROWID NOT IN (  SELECT MAX (ROWID) FROM dmhr.dup_emp GROUP BY employee_name);
DELETE FROM dmhr.dup_emp t WHERE ROWID <> (SELECT MAX (ROWID)
FROM dmhr.dup_emp
WHERE employee_name = t.employee_name);
SELECT * FROM dmhr.dup_emp;

-- 多表 update 容易犯的错误，场景示例：使用新表中的数据更新源表中的数据，通过编写关联 SQL 语句完成，新建两张表并准备数据。
-- 源表
CREATE TABLE dmhr.test
AS
   SELECT employee_id,
          employee_name,
          identity_card,
          salary,
          department_id
     FROM dmhr.employee
    WHERE 1 = 2;

SELECT * FROM dmhr.test;

INSERT INTO dmhr.test
     VALUES
(1109, '程东', '410107197103252999', 4400, 102),
(1110, '王金玉', '410107197103258999', 5300, 102),
(1111, '陈仙', '410107197103252999', 2400, 102),
(1112, '张晓中', '410107197103252999', 9000, 102),
(1113, '吴迎', '410107197103252999', 7400, 102);
COMMIT;
SELECT * FROM dmhr.test;
-- 新表
CREATE TABLE dmhr.test_new
AS
   SELECT employee_id,employee_name,identity_card,salary,department_id
     FROM dmhr.employee
    WHERE 1 = 2;

INSERT INTO dmhr.test_new
     VALUES (1111, '陈仙', '410107197103252999', 8500, 104);
COMMIT;
SELECT * FROM dmhr.test_new;
UPDATE dmhr.test ot
   SET (salary, department_id) =
          (SELECT nt.salary, nt.department_id
             FROM dmhr.test_new nt
            WHERE ot.employee_id = nt.employee_id)
 WHERE EXISTS
          (SELECT 1
             FROM dmhr.test_new nt
            WHERE ot.employee_id = nt.employee_id);

SELECT * FROM dmhr.test;

