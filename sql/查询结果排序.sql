
-- ���ֶ�˳������2ָ���ǲ�ѯ����еĵڶ����ֶΣ�3ָ�Ĳ�ѯ����е������ֶ� 
SELECT employee_name, hire_date, salary
    FROM dmhr.employee
   WHERE ROWNUM < 5
ORDER BY 2 ASC, 3 DESC;
SELECT employee_name, hire_date, salary
    FROM dmhr.employee
   WHERE ROWNUM < 5
ORDER BY hire_date ASC, salary DESC;


-- ���Ӵ����򣬲�ѯ����а����Ӵ��ģ�ʾ���еڶ���Ϊ ��β�š��ֶ�
SELECT EMPLOYEE_NAME AS ����, SUBSTR (PHONE_NUM, -4) AS β��
    FROM dmhr.employee
   WHERE ROWNUM < 5
ORDER BY 2;

-- �ַ����滻����Ӧ�ַ�����滻����� 12���2314567 TRANSLATE(expr,from_string,to_string)
SELECT TRANSLATE ('ab���bcadefg', 'abcdefg', '1234567') AS new_str FROM DUAL;
-- ��� to_string Ϊ�գ���ֱ�ӷ��ؿ�ֵ��ʾ�����������ʾ��
SELECT TRANSLATE('ab���bcadefg','abcdefg','') AS new_str FROM DUAL;

-- �����ֺ���ĸ����ַ����е���ĸ����
-- Ϊ��ʵ�ֻ���ַ������������贴��������ͼ��
CREATE OR REPLACE VIEW v AS
   SELECT postal_code || ' ' || city_id AS data FROM dmhr.location;
-- ��ѯ��ͼ��Ϣ��ʾ�����������ʾ��
SELECT * FROM v;
-- ����ʹ�� translate �滻���ܣ������ֺͿո��滻Ϊ�գ�Ȼ���ٽ�������ʾ�����������ʾ��
  SELECT data, TRANSLATE (data, '- 0123456789', '-') AS oper_type
    FROM v
   WHERE ROWNUM < 5
ORDER BY 2;
-- ����ָ����������
-- ���轫������ 6000~8000 ֮���Ա�������ڿ�ǰλ�ã��Ա����Ȳ鿴�����ǿ����ڲ�ѯ��������һ�У�ʵ��ָ����������ʾ�����������ʾ��
  SELECT job_title AS ְ��,
         CASE WHEN min_salary >= 6000 AND min_salary <= 8000 THEN 1 ELSE 2 END
            AS ����,
         min_salary AS ����
    FROM dmhr.job
   WHERE ROWNUM < 5
ORDER BY 2, 3;














