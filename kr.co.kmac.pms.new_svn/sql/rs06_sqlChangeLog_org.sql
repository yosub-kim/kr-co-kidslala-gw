-- ���� ���� ���� ����	2009/12/03		yhyim --
UPDATE smRole SET enable = '0' WHERE LEFT(id,1) = '5' OR LEFT(id,1) = '6'
UPDATE smRole SET enable = '0' WHERE LEFT(id,2) = '03'
UPDATE smRole SET enable = '0' WHERE id IN ('20SRB', '23NRB')
UPDATE smRole SET name = 'Associate��', description = 'Associate��', rate = '0.148' WHERE id = '23NRC'
UPDATE smRole SET name = '������', description = '������' WHERE id = '08CF'

INSERT INTO smRole VALUES('06CA', 'CIO', '1', 'CIO', '0')
INSERT INTO smRole VALUES('10CM', 'IM', '1', 'IM', '0')
INSERT INTO smRole VALUES('23NRD', 'Associate��', '1', 'Associate��', '0.131')
INSERT INTO smRole VALUES('23NRE', 'Associate��', '1', 'Associate��', '0.115')
INSERT INTO smRole VALUES('23NRF', 'Associate��', '1', 'Associate��', '0.107')
INSERT INTO smRole VALUES('23NRG', 'Associate��', '1', 'Associate��', '0.098')


update smRole set rate ='0.42' where id = '15EJR'
update smRole set rate ='0.368' where id = '16TJR'
update smRole set rate ='0.315' where id = '17CJR'
update smRole set rate ='0.273' where id = '18SJR'
update smRole set rate ='0.241' where id = '19JR'
update smRole set rate ='0.21' where id = '20SR'
update smRole set rate ='0.178' where id = '22NR'


UPDATE expertPool SET companyPosition = '15EJR', companyPositionName='' WHERE companyPosition = '50EC'
UPDATE expertPool SET companyPosition = '16TJR', companyPositionName=''  WHERE companyPosition = '51PC'
UPDATE expertPool SET companyPosition = '17CJR', companyPositionName=''  WHERE companyPosition = '52FC'
UPDATE expertPool SET companyPosition = '18SJR', companyPositionName=''  WHERE companyPosition = '53SC'
UPDATE expertPool SET companyPosition = '19JR', companyPositionName=''   WHERE companyPosition = '54NC'
UPDATE expertPool SET companyPosition = '20SR', companyPositionName=''   WHERE companyPosition = '55NCB'
UPDATE expertPool SET companyPosition = '22NR', companyPositionName=''   WHERE companyPosition = '56NC'
UPDATE expertPool SET companyPosition = '23NRC', companyPositionName=''  WHERE companyPosition = '60CT'

UPDATE expertPool SET rate = '0.42'  WHERE companyPosition = '15EJR'
UPDATE expertPool SET rate = '0.368' WHERE companyPosition = '16TJR'
UPDATE expertPool SET rate = '0.315' WHERE companyPosition = '17CJR'
UPDATE expertPool SET rate = '0.273' WHERE companyPosition = '18SJR'
UPDATE expertPool SET rate = '0.241' WHERE companyPosition = '19JR' 
UPDATE expertPool SET rate = '0.21'  WHERE companyPosition = '20SR' 
UPDATE expertPool SET rate = '0.178' WHERE companyPosition = '22NR' 
UPDATE expertPool SET rate = '0.148' WHERE companyPosition = '23NRC'


-- ���� ���� ���� ����	2009/12/03		yhyim --
UPDATE smGroup SET seq = '2' WHERE LEFT(id, 3) = '203'

INSERT INTO smGroup VALUES('2040', '    CIO', '2000', '1', NULL, 'CIO', '2000', '/2000/2040', '1', '3')
INSERT INTO smGroup VALUES('2041', '        ��ȸ����', '2000', '1', NULL, '��ȸ����', '2040', '/2000/2040/2041', '2', '3')
INSERT INTO smGroup VALUES('2042', '        ����������', '2000', '1', NULL, '����������', '2040', '/2000/2040/2042', '2', '3')
INSERT INTO smGroup VALUES('2043', '        ��������/�ڵ���', '2000', '1', NULL, '��������/�ڵ���', '2040', '/2000/2040/2043', '2', '3')
INSERT INTO smGroup VALUES('2044', '        ��������', '2000', '1', NULL, '��������', '2040', '/2000/2040/2044', '2', '3')

INSERT INTO smGroup VALUES('2500', '    �������׷�', '2000', '1', NULL, '�������׷�', '2000', '/2000/2500', '1', '24')
INSERT INTO smGroup VALUES('2501', '        SM�׷�', '2000', '1', NULL, 'SM�׷�', '2500', '/2000/2500/2501', '2', '24')
INSERT INTO smGroup VALUES('2502', '        OM�׷�', '2000', '1', NULL, 'OM�׷�', '2500', '/2000/2500/2502', '2', '24')

INSERT INTO smGroup VALUES('2380', '    ����濵����', '2000', '1', NULL, '����濵����', '2000', '/2000/2380', '1', '14')
INSERT INTO smGroup VALUES('2381', '        ����濵����', '2000', '1', NULL, '����濵����', '2380', '/2000/2380/2381', '2', '14')

INSERT INTO smGroup VALUES('2390', '    SCM����', '2000', '1', NULL, 'SCM����', '2000', '/2000/2390', '1', '15')
INSERT INTO smGroup VALUES('2391', '        SCM����', '2000', '1', NULL, 'SCM����', '2390', '/2000/2390/2391', '2', '15')
INSERT INTO smGroup VALUES('2113', '        �濵����3��', '2000', '1', NULL, '�濵����3��', '2110', '/2000/2110/2113', '2', '4')

UPDATE smGroup SET enabled = '1' WHERE id = '2123'
UPDATE smGroup SET enabled = '1' WHERE id = '2163'

UPDATE smGroup SET seq = '16' WHERE left(id,3) = '240'

UPDATE smGroup SET enabled = '0' WHERE LEFT(id, 1) = '6'

UPDATE smGroup SET name = '    ���İ濵��', description = '���İ濵��' WHERE id = '2180'
UPDATE smGroup SET name = '        ���İ濵1��', description = '���İ濵1��' WHERE id = '2181'
UPDATE smGroup SET name = '        ���İ濵2��', description = '���İ濵2��' WHERE id = '2182'
UPDATE smGroup SET name = '        �������Ŵ��', description = '�������Ŵ��' WHERE id = '2034'

UPDATE smGroup SET name = replace(name, ' BU', '����'), description =  replace(description, ' BU', '����') WHERE name LIKE '% BU%'

UPDATE smGroup SET name = replace(name, ' 1��', '1��'), description =  replace(description, ' 1��', '1��') WHERE name LIKE '% 1��%'
UPDATE smGroup SET name = replace(name, ' 2��', '2��'), description =  replace(description, ' 2��', '2��') WHERE name LIKE '% 2��%'
UPDATE smGroup SET name = replace(name, ' 3��', '3��'), description =  replace(description, ' 3��', '3��') WHERE name LIKE '% 3��%'
UPDATE smGroup SET name = replace(name, ' 4��', '4��'), description =  replace(description, ' 4��', '4��') WHERE name LIKE '% 4��%'


-- �μ� ���� ���� ����(������ �׷�: SM)	2009/12/06		yhyim --
UPDATE expertPool
SET dept = '2501', jobClass = 'J'
WHERE enable = '1' and jobClass in ('B', 'J') and name in (
'�⵿̱',
'�ֹ���',
'������',
'��õ��',
'���ȣ',
'������',
'����',
'�̻�ȣ',
'������',
'�ۻ���',
'��â��',
'�����',
'��ο�',
'������',
'������',
'�ӿ���',
'�̺���',
'�۹̿�',
'�ָ�',
'�ں���',
'�̿�ȣ',
'�ſ���',
'�ڽ¸�',
'ȫ��ǥ',
'ȫ��ǥ',
'�̷���',
'�迵��',
'Ȳ��ȭ',
'�ѽ�',
'���',
'����')

UPDATE expertPool SET companyPosition = '16TJR', rate = '0.368' WHERE jobClass = 'J' AND name IN ('�ֹ���', '������', '���¿�')

-- �μ� ���� ���� ����(������ �׷�: OM)	2009/12/06		yhyim --
UPDATE expertPool
SET dept = '2502', jobClass = 'J'
WHERE enable = '1' and jobClass in ('B', 'J') and name in (
'�輺��',
'����ȣ',
'���¿�',
'�ֿ���',
'���и�',
'�����',
'���߿�',
'�����',
'���⼭',
'���ö',
'������',
'����ȫ',
'�豳��',
'�����',
'������',
'�ֵ���',
'������',
'������',
'�層��',
'������',
'����',
'������',
'������',
'������',
'������',
'������')

-- �μ� ���� ���� ����(������)	2009/12/14		yhyim --
UPDATE expertPool SET jobClass='A', companyPosition='06CA', Role='ROLE2006080116033211358', extRole='ROLE2006080116033211358', dept='2041' WHERE enable='1' AND jobClass IN ('A','B') AND name = '�ۿ���'
UPDATE expertPool SET jobClass='A', companyPosition='06CA', Role='ROLE2006080116033211358', extRole='ROLE2006080116033211358', dept='2042' WHERE enable='1' AND jobClass IN ('A','B') AND name = '��α�'
UPDATE expertPool SET jobClass='A', companyPosition='06CA', Role='ROLE2006080116033211358', extRole='ROLE2006080116033211358', dept='2043' WHERE enable='1' AND jobClass IN ('A','B') AND name = '��ȣ��'
UPDATE expertPool SET jobClass='A', companyPosition='06CA', Role='ROLE2006080116033211358', extRole='ROLE2006080116033211358', dept='2044' WHERE enable='1' AND jobClass IN ('A','B') AND name = '����ö'


UPDATE expertPool SET jobClass='A', companyPosition='08CF', Role='ROLE2006080116033211358', extRole='ROLE2006080116033211358', dept='2110' WHERE enable='1' AND jobClass IN ('A','B') AND name = '�̸�'
UPDATE expertPool SET jobClass='A', companyPosition='08CF', Role='ROLE2006080116033211358', extRole='ROLE2006080116033211358', dept='2310' WHERE enable='1' AND jobClass IN ('A','B') AND name = '���ͼ�'
UPDATE expertPool SET jobClass='A', companyPosition='08CF', Role='ROLE2006080116033211358', extRole='ROLE2006080116033211358', dept='2150' WHERE enable='1' AND jobClass IN ('A','B') AND name = '���'
UPDATE expertPool SET jobClass='A', companyPosition='08CF', Role='ROLE2006080116033211358', extRole='ROLE2006080116033211358', dept='2120' WHERE enable='1' AND jobClass IN ('A','B') AND name = '�ѻ��'
UPDATE expertPool SET jobClass='A', companyPosition='08CF', Role='ROLE2006080116033211358', extRole='ROLE2006080116033211358', dept='2160' WHERE enable='1' AND jobClass IN ('A','B') AND name = '���湬'
UPDATE expertPool SET jobClass='A', companyPosition='09CJ', Role='ROLE2006080116033211358', extRole='ROLE2006080116033211358', dept='2380' WHERE enable='1' AND jobClass IN ('A','B') AND name = '��ö��'
UPDATE expertPool SET jobClass='A', companyPosition='09CJ', Role='ROLE2006080116033211358', extRole='ROLE2006080116033211358', dept='2390' WHERE enable='1' AND jobClass IN ('A','B') AND name = '���¿�'
UPDATE expertPool SET jobClass='A', companyPosition='08CF', Role='ROLE2006080116033211358', extRole='ROLE2006080116033211358', dept='2320' WHERE enable='1' AND jobClass IN ('A','B') AND name = '������'
UPDATE expertPool SET jobClass='A', companyPosition='09CJ', Role='ROLE2006080116033211358', extRole='ROLE2006080116033211358', dept='2360' WHERE enable='1' AND jobClass IN ('A','B') AND name = '�����'


UPDATE expertPool SET jobClass='A', companyPosition='10TM', Role='ROLE2006080116041070759', extRole='ROLE2006080116041070759', dept='2112' WHERE enable='1' AND jobClass IN ('A','B') AND name = '����'
UPDATE expertPool SET jobClass='A', companyPosition='10TM', Role='ROLE2006080116041070759', extRole='ROLE2006080116041070759', dept='2113' WHERE enable='1' AND jobClass IN ('A','B') AND name = '�Ӹ�'
UPDATE expertPool SET jobClass='A', companyPosition='10TM', Role='ROLE2006080116041070759', extRole='ROLE2006080116041070759', dept='2311' WHERE enable='1' AND jobClass IN ('A','B') AND name = '�ۿ���'
UPDATE expertPool SET jobClass='A', companyPosition='10TM', Role='ROLE2006080116041070759', extRole='ROLE2006080116041070759', dept='2312' WHERE enable='1' AND jobClass IN ('A','B') AND name = '���籺'
UPDATE expertPool SET jobClass='A', companyPosition='10TM', Role='ROLE2006080116041070759', extRole='ROLE2006080116041070759', dept='2151' WHERE enable='1' AND jobClass IN ('A','B') AND name = 'Ȳ��ö'
UPDATE expertPool SET jobClass='A', companyPosition='10TM', Role='ROLE2006080116041070759', extRole='ROLE2006080116041070759', dept='2152' WHERE enable='1' AND jobClass IN ('A','B') AND name = '�̻���'
UPDATE expertPool SET jobClass='A', companyPosition='10TM', Role='ROLE2006080116041070759', extRole='ROLE2006080116041070759', dept='2121' WHERE enable='1' AND jobClass IN ('A','B') AND name = '�̻���'
UPDATE expertPool SET jobClass='A', companyPosition='10TM', Role='ROLE2006080116041070759', extRole='ROLE2006080116041070759', dept='2122' WHERE enable='1' AND jobClass IN ('A','B') AND name = '�ǽ�ȯ'
UPDATE expertPool SET jobClass='A', companyPosition='10TM', Role='ROLE2006080116041070759', extRole='ROLE2006080116041070759', dept='2123' WHERE enable='1' AND jobClass IN ('A','B') AND name = '������'
UPDATE expertPool SET jobClass='A', companyPosition='10TM', Role='ROLE2006080116041070759', extRole='ROLE2006080116041070759', dept='2131' WHERE enable='1' AND jobClass IN ('A','B') AND name = '�⵿̱'
UPDATE expertPool SET jobClass='A', companyPosition='10TM', Role='ROLE2006080116041070759', extRole='ROLE2006080116041070759', dept='2132' WHERE enable='1' AND jobClass IN ('A','B') AND name = 'Ȳâȯ'
UPDATE expertPool SET jobClass='A', companyPosition='10TM', Role='ROLE2006080116041070759', extRole='ROLE2006080116041070759', dept='2161' WHERE enable='1' AND jobClass IN ('A','B') AND name = 'Ȳ���'
UPDATE expertPool SET jobClass='A', companyPosition='10TM', Role='ROLE2006080116041070759', extRole='ROLE2006080116041070759', dept='2162' WHERE enable='1' AND jobClass IN ('A','B') AND name = '������'
UPDATE expertPool SET jobClass='A', companyPosition='10TM', Role='ROLE2006080116041070759', extRole='ROLE2006080116041070759', dept='2163' WHERE enable='1' AND jobClass IN ('A','B') AND name = '�輺��'
UPDATE expertPool SET jobClass='A', companyPosition='10TM', Role='ROLE2006080116041070759', extRole='ROLE2006080116041070759', dept='2322' WHERE enable='1' AND jobClass IN ('A','B') AND name = '��ö��'
UPDATE expertPool SET jobClass='A', companyPosition='10TM', Role='ROLE2006080116041070759', extRole='ROLE2006080116041070759', dept='2181' WHERE enable='1' AND jobClass IN ('A','B') AND name = '������'
UPDATE expertPool SET jobClass='A', companyPosition='10TM', dept='2182' WHERE enable='1' AND jobClass IN ('A','B') AND name = '�����'

UPDATE expertPool SET jobClass='A', companyPosition='10CM', Role='ROLE2006080116041070759', extRole='ROLE2006080116041070759', dept='2041'  WHERE enable='1' AND jobClass IN ('A','B') AND name = '������'
UPDATE expertPool SET jobClass='A', companyPosition='10CM', Role='ROLE2006080116041070759', extRole='ROLE2006080116041070759', dept='2042'  WHERE enable='1' AND jobClass IN ('A','B') AND name = '���Ÿ�'
UPDATE expertPool SET jobClass='A', companyPosition='10CM', Role='ROLE2006080116041070759', extRole='ROLE2006080116041070759', dept='2043'  WHERE enable='1' AND jobClass IN ('A','B') AND name = 'ȫ���'

-- �μ� ���� ���� ����(���)	2009/12/06		yhyim --
UPDATE expertPool SET jobClass = 'A', dept='2111', Role='ROLE2006080116061636366', extRole='ROLE2006080116061636366' WHERE enable='1' AND jobClass IN('A', 'B', 'J') AND name = '���ȫ'
UPDATE expertPool SET jobClass = 'A', dept='2111', Role='ROLE2006080116061636366', extRole='ROLE2006080116061636366' WHERE enable='1' AND jobClass IN('A', 'B', 'J') AND name = '�۱�ȣ'
UPDATE expertPool SET jobClass = 'A', dept='2111', Role='ROLE2006080116061636366', extRole='ROLE2006080116061636366' WHERE enable='1' AND jobClass IN('A', 'B', 'J') AND name = '���漮'
UPDATE expertPool SET jobClass = 'A', dept='2112', Role='ROLE2006080116061636366', extRole='ROLE2006080116061636366' WHERE enable='1' AND jobClass IN('A', 'B', 'J') AND name = '�̽���'
UPDATE expertPool SET jobClass = 'A', dept='2112', Role='ROLE2006080116061636366', extRole='ROLE2006080116061636366' WHERE enable='1' AND jobClass IN('A', 'B', 'J') AND name = '�̻��'
UPDATE expertPool SET jobClass = 'A', dept='2112', Role='ROLE2006080116061636366', extRole='ROLE2006080116061636366' WHERE enable='1' AND jobClass IN('A', 'B', 'J') AND name = '�輺��'
UPDATE expertPool SET jobClass = 'A', dept='2113', Role='ROLE2006080116061636366', extRole='ROLE2006080116061636366' WHERE enable='1' AND jobClass IN('A', 'B', 'J') AND name = '���漮'
UPDATE expertPool SET jobClass = 'A', dept='2113', Role='ROLE2006080116061636366', extRole='ROLE2006080116061636366' WHERE enable='1' AND jobClass IN('A', 'B', 'J') AND name = '����'
UPDATE expertPool SET jobClass = 'A', dept='2113', Role='ROLE2006080116061636366', extRole='ROLE2006080116061636366' WHERE enable='1' AND jobClass IN('A', 'B', 'J') AND name = '���缮'
UPDATE expertPool SET jobClass = 'A', dept='2113', Role='ROLE2006080116061636366', extRole='ROLE2006080116061636366' WHERE enable='1' AND jobClass IN('A', 'B', 'J') AND name = '���缺'
UPDATE expertPool SET jobClass = 'A', dept='2113', Role='ROLE2006080116061636366', extRole='ROLE2006080116061636366' WHERE enable='1' AND jobClass IN('A', 'B', 'J') AND name = '�̼�ȣ'
UPDATE expertPool SET jobClass = 'A', dept='2311', companyPosition='17CJR', Rate='0.315', Role='ROLE2006080116061636366', extRole='ROLE2006080116061636366' WHERE enable='1' AND jobClass IN('A', 'B', 'J') AND name = '�����'
UPDATE expertPool SET jobClass = 'A', dept='2311', Role='ROLE2006080116061636366', extRole='ROLE2006080116061636366' WHERE enable='1' AND jobClass IN('A', 'B', 'J') AND name = '������'
UPDATE expertPool SET jobClass = 'A', dept='2311', Role='ROLE2006080116061636366', extRole='ROLE2006080116061636366' WHERE enable='1' AND jobClass IN('A', 'B', 'J') AND name = '������'
UPDATE expertPool SET jobClass = 'A', dept='2311', Role='ROLE2006080116061636366', extRole='ROLE2006080116061636366' WHERE enable='1' AND jobClass IN('A', 'B', 'J') AND name = '������'
UPDATE expertPool SET jobClass = 'A', dept='2311', Role='ROLE2006080116061636366', extRole='ROLE2006080116061636366' WHERE enable='1' AND jobClass IN('A', 'B', 'J') AND name = '�����'
UPDATE expertPool SET jobClass = 'A', dept='2311', Role='ROLE2006080116061636366', extRole='ROLE2006080116061636366' WHERE enable='1' AND jobClass IN('A', 'B', 'J') AND name = '����ȣ'
UPDATE expertPool SET jobClass = 'A', dept='2312', Role='ROLE2006080116061636366', extRole='ROLE2006080116061636366' WHERE enable='1' AND jobClass IN('A', 'B', 'J') AND name = '���ѱ�'
UPDATE expertPool SET jobClass = 'A', dept='2312', Role='ROLE2006080116061636366', extRole='ROLE2006080116061636366' WHERE enable='1' AND jobClass IN('A', 'B', 'J') AND name = '��ȫ��'
UPDATE expertPool SET jobClass = 'A', dept='2312', Role='ROLE2006080116061636366', extRole='ROLE2006080116061636366' WHERE enable='1' AND jobClass IN('A', 'B', 'J') AND name = '�ֺ���'
UPDATE expertPool SET jobClass = 'A', dept='2312', Role='ROLE2006080116061636366', extRole='ROLE2006080116061636366' WHERE enable='1' AND jobClass IN('A', 'B', 'J') AND name = '�輱��'
UPDATE expertPool SET jobClass = 'A', dept='2312', Role='ROLE2006080116061636366', extRole='ROLE2006080116061636366' WHERE enable='1' AND jobClass IN('A', 'B', 'J') AND name = '�赿��'
UPDATE expertPool SET jobClass = 'A', dept='2312', Role='ROLE2006080116061636366', extRole='ROLE2006080116061636366' WHERE enable='1' AND jobClass IN('A', 'B', 'J') AND name = '������'
UPDATE expertPool SET jobClass = 'A', dept='2312', Role='ROLE2006080116061636366', extRole='ROLE2006080116061636366' WHERE enable='1' AND jobClass IN('A', 'B', 'J') AND name = '���±�'
UPDATE expertPool SET jobClass = 'A', dept='2312', Role='ROLE2006080116061636366', extRole='ROLE2006080116061636366' WHERE enable='1' AND jobClass IN('A', 'B', 'J') AND name = '������'
UPDATE expertPool SET jobClass = 'A', dept='2312', Role='ROLE2006080116061636366', extRole='ROLE2006080116061636366' WHERE enable='1' AND jobClass IN('A', 'B', 'J') AND name = '���ٿ�'
UPDATE expertPool SET jobClass = 'A', dept='2151', Role='ROLE2006080116061636366', extRole='ROLE2006080116061636366' WHERE enable='1' AND jobClass IN('A', 'B', 'J') AND name = '�̸�'
UPDATE expertPool SET jobClass = 'A', dept='2151', Role='ROLE2006080116061636366', extRole='ROLE2006080116061636366' WHERE enable='1' AND jobClass IN('A', 'B', 'J') AND name = '������'
UPDATE expertPool SET jobClass = 'A', dept='2151', Role='ROLE2006080116061636366', extRole='ROLE2006080116061636366' WHERE enable='1' AND jobClass IN('A', 'B', 'J') AND name = '������'
UPDATE expertPool SET jobClass = 'A', dept='2152', Role='ROLE2006080116061636366', extRole='ROLE2006080116061636366' WHERE enable='1' AND jobClass IN('A', 'B', 'J') AND name = '��ö��'
UPDATE expertPool SET jobClass = 'A', dept='2152', Role='ROLE2006080116061636366', extRole='ROLE2006080116061636366' WHERE enable='1' AND jobClass IN('A', 'B', 'J') AND name = '������'
UPDATE expertPool SET jobClass = 'A', dept='2152', Role='ROLE2006080116061636366', extRole='ROLE2006080116061636366' WHERE enable='1' AND jobClass IN('A', 'B', 'J') AND name = '����ȯ'
UPDATE expertPool SET jobClass = 'A', dept='2152', Role='ROLE2006080116061636366', extRole='ROLE2006080116061636366' WHERE enable='1' AND jobClass IN('A', 'B', 'J') AND name = '�����'
UPDATE expertPool SET jobClass = 'A', dept='2152', Role='ROLE2006080116061636366', extRole='ROLE2006080116061636366' WHERE enable='1' AND jobClass IN('A', 'B', 'J') AND name = '������'
UPDATE expertPool SET jobClass = 'A', dept='2152', Role='ROLE2006080116061636366', extRole='ROLE2006080116061636366' WHERE enable='1' AND jobClass IN('A', 'B', 'J') AND name = '������'
UPDATE expertPool SET jobClass = 'A', dept='2121', Role='ROLE2006080116061636366', extRole='ROLE2006080116061636366' WHERE enable='1' AND jobClass IN('A', 'B', 'J') AND name = '���Ͻ�'
UPDATE expertPool SET jobClass = 'A', dept='2121', Role='ROLE2006080116061636366', extRole='ROLE2006080116061636366' WHERE enable='1' AND jobClass IN('A', 'B', 'J') AND name = '������'
UPDATE expertPool SET jobClass = 'A', dept='2121', Role='ROLE2006080116061636366', extRole='ROLE2006080116061636366' WHERE enable='1' AND jobClass IN('A', 'B', 'J') AND name = '������'
UPDATE expertPool SET jobClass = 'A', dept='2122', Role='ROLE2006080116061636366', extRole='ROLE2006080116061636366' WHERE enable='1' AND jobClass IN('A', 'B', 'J') AND name = '�迵��'
UPDATE expertPool SET jobClass = 'A', dept='2122', Role='ROLE2006080116061636366', extRole='ROLE2006080116061636366' WHERE enable='1' AND jobClass IN('A', 'B', 'J') AND name = '����'
UPDATE expertPool SET jobClass = 'A', dept='2122', Role='ROLE2006080116061636366', extRole='ROLE2006080116061636366' WHERE enable='1' AND jobClass IN('A', 'B', 'J') AND name = '�̶̹�'
UPDATE expertPool SET jobClass = 'A', dept='2123', Role='ROLE2006080116061636366', extRole='ROLE2006080116061636366' WHERE enable='1' AND jobClass IN('A', 'B', 'J') AND name = '�����'
UPDATE expertPool SET jobClass = 'A', dept='2123', Role='ROLE2006080116061636366', extRole='ROLE2006080116061636366' WHERE enable='1' AND jobClass IN('A', 'B', 'J') AND name = '����ȣ'
UPDATE expertPool SET jobClass = 'A', dept='2123', Role='ROLE2006080116061636366', extRole='ROLE2006080116061636366' WHERE enable='1' AND jobClass IN('A', 'B', 'J') AND name = '����ȸ'
UPDATE expertPool SET jobClass = 'A', dept='2123', Role='ROLE2006080116061636366', extRole='ROLE2006080116061636366' WHERE enable='1' AND jobClass IN('A', 'B', 'J') AND name = '�躸��'
UPDATE expertPool SET jobClass = 'A', dept='2123', Role='ROLE2006080116061636366', extRole='ROLE2006080116061636366' WHERE enable='1' AND jobClass IN('A', 'B', 'J') AND name = '�赿��'
UPDATE expertPool SET jobClass = 'A', dept='2123', Role='ROLE2006080116061636366', extRole='ROLE2006080116061636366' WHERE enable='1' AND jobClass IN('A', 'B', 'J') AND name = '���翵'
UPDATE expertPool SET jobClass = 'A', dept='2123', Role='ROLE2006080116061636366', extRole='ROLE2006080116061636366' WHERE enable='1' AND jobClass IN('A', 'B', 'J') AND name = '��⿭'
UPDATE expertPool SET jobClass = 'A', dept='2131', Role='ROLE2006080116061636366', extRole='ROLE2006080116061636366' WHERE enable='1' AND jobClass IN('A', 'B', 'J') AND name = '��â��'
UPDATE expertPool SET jobClass = 'A', dept='2131', Role='ROLE2006080116061636366', extRole='ROLE2006080116061636366' WHERE enable='1' AND jobClass IN('A', 'B', 'J') AND name = '������'
UPDATE expertPool SET jobClass = 'A', dept='2131', Role='ROLE2006080116061636366', extRole='ROLE2006080116061636366' WHERE enable='1' AND jobClass IN('A', 'B', 'J') AND name = '������'
UPDATE expertPool SET jobClass = 'A', dept='2131', Role='ROLE2006080116061636366', extRole='ROLE2006080116061636366' WHERE enable='1' AND jobClass IN('A', 'B', 'J') AND name = '�����'
UPDATE expertPool SET jobClass = 'A', dept='2132', Role='ROLE2006080116061636366', extRole='ROLE2006080116061636366' WHERE enable='1' AND jobClass IN('A', 'B', 'J') AND name = '����ö'
UPDATE expertPool SET jobClass = 'A', dept='2132', Role='ROLE2006080116061636366', extRole='ROLE2006080116061636366' WHERE enable='1' AND jobClass IN('A', 'B', 'J') AND name = '���ֿ�'
UPDATE expertPool SET jobClass = 'A', dept='2132', Role='ROLE2006080116061636366', extRole='ROLE2006080116061636366' WHERE enable='1' AND jobClass IN('A', 'B', 'J') AND name = '���߶�'
UPDATE expertPool SET jobClass = 'A', dept='2141', Role='ROLE2006080116061636366', extRole='ROLE2006080116061636366' WHERE enable='1' AND jobClass IN('A', 'B', 'J') AND name = '���'
UPDATE expertPool SET jobClass = 'A', dept='2141', Role='ROLE2006080116061636366', extRole='ROLE2006080116061636366' WHERE enable='1' AND jobClass IN('A', 'B', 'J') AND name = '������'
UPDATE expertPool SET jobClass = 'A', dept='2141', Role='ROLE2006080116061636366', extRole='ROLE2006080116061636366' WHERE enable='1' AND jobClass IN('A', 'B', 'J') AND name = '�����'
UPDATE expertPool SET jobClass = 'A', dept='2141', Role='ROLE2006080116061636366', extRole='ROLE2006080116061636366' WHERE enable='1' AND jobClass IN('A', 'B', 'J') AND name = '���翵'
UPDATE expertPool SET jobClass = 'A', dept='2142', Role='ROLE2006080116061636366', extRole='ROLE2006080116061636366' WHERE enable='1' AND jobClass IN('A', 'B', 'J') AND name = '����ȣ'
UPDATE expertPool SET jobClass = 'A', dept='2142', Role='ROLE2006080116061636366', extRole='ROLE2006080116061636366' WHERE enable='1' AND jobClass IN('A', 'B', 'J') AND name = '�躴��'
UPDATE expertPool SET jobClass = 'A', dept='2142', Role='ROLE2006080116061636366', extRole='ROLE2006080116061636366' WHERE enable='1' AND jobClass IN('A', 'B', 'J') AND name = '����'
UPDATE expertPool SET jobClass = 'A', dept='2143', Role='ROLE2006080116061636366', extRole='ROLE2006080116061636366' WHERE enable='1' AND jobClass IN('A', 'B', 'J') AND name = '������'
UPDATE expertPool SET jobClass = 'A', dept='2143', Role='ROLE2006080116061636366', extRole='ROLE2006080116061636366' WHERE enable='1' AND jobClass IN('A', 'B', 'J') AND name = '�ȿ���'
UPDATE expertPool SET jobClass = 'A', dept='2143', Role='ROLE2006080116061636366', extRole='ROLE2006080116061636366' WHERE enable='1' AND jobClass IN('A', 'B', 'J') AND name = '������'
UPDATE expertPool SET jobClass = 'A', dept='2143', Role='ROLE2006080116061636366', extRole='ROLE2006080116061636366' WHERE enable='1' AND jobClass IN('A', 'B', 'J') AND name = '��ȸ��'
UPDATE expertPool SET jobClass = 'A', dept='2371', Role='ROLE2006080116061636366', extRole='ROLE2006080116061636366' WHERE enable='1' AND jobClass IN('A', 'B', 'J') AND name = '�ռ���'
UPDATE expertPool SET jobClass = 'A', dept='2371', Role='ROLE2006080116061636366', extRole='ROLE2006080116061636366' WHERE enable='1' AND jobClass IN('A', 'B', 'J') AND name = '�赿��'
UPDATE expertPool SET jobClass = 'A', dept='2161', Role='ROLE2006080116061636366', extRole='ROLE2006080116061636366' WHERE enable='1' AND jobClass IN('A', 'B', 'J') AND name = '�̼���'
UPDATE expertPool SET jobClass = 'A', dept='2161', Role='ROLE2006080116061636366', extRole='ROLE2006080116061636366' WHERE enable='1' AND jobClass IN('A', 'B', 'J') AND name = '������'
UPDATE expertPool SET jobClass = 'A', dept='2162', Role='ROLE2006080116061636366', extRole='ROLE2006080116061636366' WHERE enable='1' AND jobClass IN('A', 'B', 'J') AND name = '����'
UPDATE expertPool SET jobClass = 'A', dept='2162', Role='ROLE2006080116061636366', extRole='ROLE2006080116061636366' WHERE enable='1' AND jobClass IN('A', 'B', 'J') AND name = '�躴��'
UPDATE expertPool SET jobClass = 'A', dept='2162', Role='ROLE2006080116061636366', extRole='ROLE2006080116061636366' WHERE enable='1' AND jobClass IN('A', 'B', 'J') AND name = '���汸'
UPDATE expertPool SET jobClass = 'A', dept='2163', Role='ROLE2006080116061636366', extRole='ROLE2006080116061636366' WHERE enable='1' AND jobClass IN('A', 'B', 'J') AND name = '����'
UPDATE expertPool SET jobClass = 'A', dept='2163', Role='ROLE2006080116061636366', extRole='ROLE2006080116061636366' WHERE enable='1' AND jobClass IN('A', 'B', 'J') AND name = '�ּ�ȣ'
UPDATE expertPool SET jobClass = 'A', dept='2163', Role='ROLE2006080116061636366', extRole='ROLE2006080116061636366' WHERE enable='1' AND jobClass IN('A', 'B', 'J') AND name = '������'
UPDATE expertPool SET jobClass = 'A', dept='2163', Role='ROLE2006080116061636366', extRole='ROLE2006080116061636366' WHERE enable='1' AND jobClass IN('A', 'B', 'J') AND name = '�̹ο�'
UPDATE expertPool SET jobClass = 'A', dept='2381', Role='ROLE2006080116061636366', extRole='ROLE2006080116061636366' WHERE enable='1' AND jobClass IN('A', 'B', 'J') AND name = '�뺴��'
UPDATE expertPool SET jobClass = 'A', dept='2381', Role='ROLE2006080116061636366', extRole='ROLE2006080116061636366' WHERE enable='1' AND jobClass IN('A', 'B', 'J') AND name = '�Źμ�'
UPDATE expertPool SET jobClass = 'A', dept='2381', Role='ROLE2006080116061636366', extRole='ROLE2006080116061636366' WHERE enable='1' AND jobClass IN('A', 'B', 'J') AND name = '������'
UPDATE expertPool SET jobClass = 'A', dept='2321', Role='ROLE2006080116061636366', extRole='ROLE2006080116061636366' WHERE enable='1' AND jobClass IN('A', 'B', 'J') AND name = '������'
UPDATE expertPool SET jobClass = 'A', dept='2321', Role='ROLE2006080116061636366', extRole='ROLE2006080116061636366' WHERE enable='1' AND jobClass IN('A', 'B', 'J') AND name = '�����'
UPDATE expertPool SET jobClass = 'A', dept='2321', Role='ROLE2006080116061636366', extRole='ROLE2006080116061636366' WHERE enable='1' AND jobClass IN('A', 'B', 'J') AND name = '������'
UPDATE expertPool SET jobClass = 'A', dept='2321', Role='ROLE2006080116061636366', extRole='ROLE2006080116061636366' WHERE enable='1' AND jobClass IN('A', 'B', 'J') AND name = '�̻��'
UPDATE expertPool SET jobClass = 'A', dept='2322', Role='ROLE2006080116061636366', extRole='ROLE2006080116061636366' WHERE enable='1' AND jobClass IN('A', 'B', 'J') AND name = '�����'
UPDATE expertPool SET jobClass = 'A', dept='2322', Role='ROLE2006080116061636366', extRole='ROLE2006080116061636366' WHERE enable='1' AND jobClass IN('A', 'B', 'J') AND name = '�̰���'
UPDATE expertPool SET jobClass = 'A', dept='2322', Role='ROLE2006080116061636366', extRole='ROLE2006080116061636366' WHERE enable='1' AND jobClass IN('A', 'B', 'J') AND name = '������'
UPDATE expertPool SET jobClass = 'A', dept='2322', Role='ROLE2006080116061636366', extRole='ROLE2006080116061636366' WHERE enable='1' AND jobClass IN('A', 'B', 'J') AND name = '����ö'
UPDATE expertPool SET jobClass = 'A', dept='2322', Role='ROLE2006080116061636366', extRole='ROLE2006080116061636366' WHERE enable='1' AND jobClass IN('A', 'B', 'J') AND name = '�����'
UPDATE expertPool SET jobClass = 'A', dept='2322', Role='ROLE2006080116061636366', extRole='ROLE2006080116061636366' WHERE enable='1' AND jobClass IN('A', 'B', 'J') AND name = '������'
UPDATE expertPool SET jobClass = 'A', dept='2361', Role='ROLE2006080116061636366', extRole='ROLE2006080116061636366' WHERE enable='1' AND jobClass IN('A', 'B', 'J') AND name = '�����'
UPDATE expertPool SET jobClass = 'A', dept='2361', Role='ROLE2006080116061636366', extRole='ROLE2006080116061636366' WHERE enable='1' AND jobClass IN('A', 'B', 'J') AND name = '������'
UPDATE expertPool SET jobClass = 'A', dept='2361', Role='ROLE2006080116061636366', extRole='ROLE2006080116061636366' WHERE enable='1' AND jobClass IN('A', 'B', 'J') AND name = '������'
UPDATE expertPool SET jobClass = 'A', dept='2361', Role='ROLE2006080116061636366', extRole='ROLE2006080116061636366' WHERE enable='1' AND jobClass IN('A', 'B', 'J') AND name = '����ȣ'
UPDATE expertPool SET jobClass = 'A', dept='2181', Role='ROLE2006080116061636366', extRole='ROLE2006080116061636366' WHERE enable='1' AND jobClass IN('A', 'B', 'J') AND name = 'ȫ����'
UPDATE expertPool SET jobClass = 'A', dept='2181', Role='ROLE2006080116061636366', extRole='ROLE2006080116061636366' WHERE enable='1' AND jobClass IN('A', 'B', 'J') AND name = '�����'
UPDATE expertPool SET jobClass = 'A', dept='2181', Role='ROLE2006080116061636366', extRole='ROLE2006080116061636366' WHERE enable='1' AND jobClass IN('A', 'B', 'J') AND name = '��ȿ��'
UPDATE expertPool SET jobClass = 'A', dept='2181', Role='ROLE2006080116061636366', extRole='ROLE2006080116061636366' WHERE enable='1' AND jobClass IN('A', 'B', 'J') AND name = '���ο�'
UPDATE expertPool SET jobClass = 'A', dept='2182', Role='ROLE2006080116061636366', extRole='ROLE2006080116061636366' WHERE enable='1' AND jobClass IN('A', 'B', 'J') AND name = '�ӿ���'
UPDATE expertPool SET jobClass = 'A', dept='2182', Role='ROLE2006080116061636366', extRole='ROLE2006080116061636366' WHERE enable='1' AND jobClass IN('A', 'B', 'J') AND name = '������'
UPDATE expertPool SET jobClass = 'A', dept='2191', Role='ROLE2006080116061636366', extRole='ROLE2006080116061636366' WHERE enable='1' AND jobClass IN('A', 'B', 'J') AND name = '�߱���'
UPDATE expertPool SET jobClass = 'A', dept='2191', Role='ROLE2006080116061636366', extRole='ROLE2006080116061636366' WHERE enable='1' AND jobClass IN('A', 'B', 'J') AND name = '�����'
UPDATE expertPool SET jobClass = 'A', dept='2391', Role='ROLE2006080116061636366', extRole='ROLE2006080116061636366' WHERE enable='1' AND jobClass IN('A', 'B', 'J') AND name = '����ȯ'

-- �μ���, ������ ������Ʈ	2009/12/21		yhyim --
UPDATE expertPool 
SET deptName=(SELECT description FROM smGroup WHERE id=dept), companyPositionName=(SELECT description FROM smRole WHERE id=companyPosition)
WHERE enable='1' and jobClass in ('A', 'J')

-- �̿� ������ ���� ����� ���� ���ֱ�
UPDATE expertPool SET role=NULL, extRole=NULL WHERE enable = '0' AND role <> '' AND role IS NOT NULL

-- �ΰǺ� ��� ���� �߰� ����	2009/12/08		yhyim --
INSERT INTO ProjectStdCost VALUES('15EJR','��Ʈ��','1500000','0.42','BTA')
INSERT INTO ProjectStdCost VALUES('16TJR','�̱���Ƽ��','1300000','0.368','BTA')
INSERT INTO ProjectStdCost VALUES('17CJR','��������','1150000','0.315','BTA')
INSERT INTO ProjectStdCost VALUES('18SJR','������','1000000','0.273','BTA')
INSERT INTO ProjectStdCost VALUES('19JR','ġ��','850000','0.241','BTA')
INSERT INTO ProjectStdCost VALUES('20SR','�ôϾ�','750000','0.21','BTA')
INSERT INTO ProjectStdCost VALUES('22NR','������Ʈ','650000','0.178','BTA')
INSERT INTO ProjectStdCost VALUES('23NRC','Associate��','450000','0.148','BTA')
INSERT INTO ProjectStdCost VALUES('23NRD','Associate��','450000','0.131','BTA')
INSERT INTO ProjectStdCost VALUES('23NRE','Associate��','450000','0.115','BTA')
INSERT INTO ProjectStdCost VALUES('23NRF','Associate��','450000','0.107','BTA')
INSERT INTO ProjectStdCost VALUES('23NRG','Associate��','450000','0.098','BTA')

INSERT INTO ProjectStdCost VALUES('15EJR','��Ʈ��','330000','0.42','BTE')
INSERT INTO ProjectStdCost VALUES('16TJR','�̱���Ƽ��','280000','0.368','BTE')
INSERT INTO ProjectStdCost VALUES('17CJR','��������','240000','0.315','BTE')
INSERT INTO ProjectStdCost VALUES('18SJR','������','210000','0.273','BTE')
INSERT INTO ProjectStdCost VALUES('19JR','ġ��','180000','0.241','BTE')
INSERT INTO ProjectStdCost VALUES('20SR','�ôϾ�','160000','0.21','BTE')
INSERT INTO ProjectStdCost VALUES('22NR','������Ʈ','140000','0.178','BTE')



-- ����� ���̺� ���� ����	2009/12/03		yhyim --


INSERT INTO cmtabledata(TABLE_NAME, KEY_1, KEY_2, KEY_3, DATA_1, DATA_2, DATA_3, DATA_4) VALUES('EDU_SALARY', '0.42',  'J', 'COST', '158700', '121800', '98200', '85000')
INSERT INTO cmtabledata(TABLE_NAME, KEY_1, KEY_2, KEY_3, DATA_1, DATA_2, DATA_3, DATA_4) VALUES('EDU_SALARY', '0.368', 'J', 'COST', '138900', '103900', '86000', '74300')
INSERT INTO cmtabledata(TABLE_NAME, KEY_1, KEY_2, KEY_3, DATA_1, DATA_2, DATA_3, DATA_4) VALUES('EDU_SALARY', '0.315', 'J', 'COST', '119000', '90300',  '73700', '63700')
INSERT INTO cmtabledata(TABLE_NAME, KEY_1, KEY_2, KEY_3, DATA_1, DATA_2, DATA_3, DATA_4) VALUES('EDU_SALARY', '0.273', 'J', 'COST', '103200', '78700',  '63800', '55200')
INSERT INTO cmtabledata(TABLE_NAME, KEY_1, KEY_2, KEY_3, DATA_1, DATA_2, DATA_3, DATA_4) VALUES('EDU_SALARY', '0.241', 'J', 'COST', '91200',  '69300',  '56400', '48800')
INSERT INTO cmtabledata(TABLE_NAME, KEY_1, KEY_2, KEY_3, DATA_1, DATA_2, DATA_3, DATA_4) VALUES('EDU_SALARY', '0.21',  'J', 'COST', '79300',  '60900',  '49100', '42500')
INSERT INTO cmtabledata(TABLE_NAME, KEY_1, KEY_2, KEY_3, DATA_1, DATA_2, DATA_3, DATA_4) VALUES('EDU_SALARY', '0.178', 'J', 'COST', '67400',  '50400',  '41600', '36100')

UPDATE cmtabledata SET KEY_1 = '0.178' WHERE TABLE_NAME = 'EDU_SALARY' AND KEY_3 = 'COST' AND KEY_2 = 'B' AND KEY_1 = '0.105'
UPDATE cmtabledata SET KEY_1 = '0.21'  WHERE TABLE_NAME = 'EDU_SALARY' AND KEY_3 = 'COST' AND KEY_2 = 'B' AND KEY_1 = '0.126'
UPDATE cmtabledata SET KEY_1 = '0.241' WHERE TABLE_NAME = 'EDU_SALARY' AND KEY_3 = 'COST' AND KEY_2 = 'B' AND KEY_1 = '0.147'
UPDATE cmtabledata SET KEY_1 = '0.273' WHERE TABLE_NAME = 'EDU_SALARY' AND KEY_3 = 'COST' AND KEY_2 = 'B' AND KEY_1 = '0.168'
UPDATE cmtabledata SET KEY_1 = '0.315' WHERE TABLE_NAME = 'EDU_SALARY' AND KEY_3 = 'COST' AND KEY_2 = 'B' AND KEY_1 = '0.189'
UPDATE cmtabledata SET KEY_1 = '0.368' WHERE TABLE_NAME = 'EDU_SALARY' AND KEY_3 = 'COST' AND KEY_2 = 'B' AND KEY_1 = '0.2205'
UPDATE cmtabledata SET KEY_1 = '0.42'  WHERE TABLE_NAME = 'EDU_SALARY' AND KEY_3 = 'COST' AND KEY_2 = 'B' AND KEY_1 = '0.252'





-- �¶��� ���� �ý��� ����� ���
SELECT 'INSERT INTO DW_EMPAUTH(SEQNO, EMPNO, AUTHCODE, EMPNAME, USEYN, INPUTEMP, INPUTDATE) VALUES(' , '''' + ssn + ''', ''A03'', ''' + name + ''', ''1'', ''7409191018915'', ''2010-01-01 00:00:00.000'')'
AS query
FROM expertPool
WHERE 1=1
AND companyPosition IN (
'10TM',
'09CJ',
'08CF',
'07CC',
'01HC',
'05CB',
'06CA'
) AND enable = '1' AND jobClass = 'A'
order by dept

SELECT 'INSERT INTO DW_EMPAUTH(SEQNO, EMPNO, AUTHCODE, EMPNAME, USEYN, INPUTEMP, INPUTDATE) VALUES(' , '''' + ssn + ''', ''A02'', ''' + name + ''', ''1'', ''7409191018915'', ''2010-01-01 00:00:00.000'')'
AS query
FROM expertPool
WHERE 1=1
AND companyPosition IN (
'15EJR',
'16TJR',
'17CJR',
'18SJR',
'19JR',
'20SR',
'22NR') 
AND enable = '1' AND jobClass = 'A' AND dept not in ('2182', '2191', '2192') AND right(dept,1) <> '3'
order by dept
