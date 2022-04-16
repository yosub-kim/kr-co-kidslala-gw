-- 직위 정보 변경 쿼리	2009/12/03		yhyim --
UPDATE smRole SET enable = '0' WHERE LEFT(id,1) = '5' OR LEFT(id,1) = '6'
UPDATE smRole SET enable = '0' WHERE LEFT(id,2) = '03'
UPDATE smRole SET enable = '0' WHERE id IN ('20SRB', '23NRB')
UPDATE smRole SET name = 'AssociateⅠ', description = 'AssociateⅠ', rate = '0.148' WHERE id = '23NRC'
UPDATE smRole SET name = '본부장', description = '본부장' WHERE id = '08CF'

INSERT INTO smRole VALUES('06CA', 'CIO', '1', 'CIO', '0')
INSERT INTO smRole VALUES('10CM', 'IM', '1', 'IM', '0')
INSERT INTO smRole VALUES('23NRD', 'AssociateⅡ', '1', 'AssociateⅡ', '0.131')
INSERT INTO smRole VALUES('23NRE', 'AssociateⅢ', '1', 'AssociateⅢ', '0.115')
INSERT INTO smRole VALUES('23NRF', 'AssociateⅣ', '1', 'AssociateⅣ', '0.107')
INSERT INTO smRole VALUES('23NRG', 'AssociateⅤ', '1', 'AssociateⅤ', '0.098')


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


-- 조직 정보 변경 쿼리	2009/12/03		yhyim --
UPDATE smGroup SET seq = '2' WHERE LEFT(id, 3) = '203'

INSERT INTO smGroup VALUES('2040', '    CIO', '2000', '1', NULL, 'CIO', '2000', '/2000/2040', '1', '3')
INSERT INTO smGroup VALUES('2041', '        사회서비스', '2000', '1', NULL, '사회서비스', '2040', '/2000/2040/2041', '2', '3')
INSERT INTO smGroup VALUES('2042', '        제조인프라', '2000', '1', NULL, '제조인프라', '2040', '/2000/2040/2042', '2', '3')
INSERT INTO smGroup VALUES('2043', '        현지법인/자동차', '2000', '1', NULL, '현지법인/자동차', '2040', '/2000/2040/2043', '2', '3')
INSERT INTO smGroup VALUES('2044', '        금융서비스', '2000', '1', NULL, '금융서비스', '2040', '/2000/2040/2044', '2', '3')

INSERT INTO smGroup VALUES('2500', '    전문가그룹', '2000', '1', NULL, '전문가그룹', '2000', '/2000/2500', '1', '24')
INSERT INTO smGroup VALUES('2501', '        SM그룹', '2000', '1', NULL, 'SM그룹', '2500', '/2000/2500/2501', '2', '24')
INSERT INTO smGroup VALUES('2502', '        OM그룹', '2000', '1', NULL, 'OM그룹', '2500', '/2000/2500/2502', '2', '24')

INSERT INTO smGroup VALUES('2380', '    기술경영센터', '2000', '1', NULL, '기술경영센터', '2000', '/2000/2380', '1', '14')
INSERT INTO smGroup VALUES('2381', '        기술경영센터', '2000', '1', NULL, '기술경영센터', '2380', '/2000/2380/2381', '2', '14')

INSERT INTO smGroup VALUES('2390', '    SCM센터', '2000', '1', NULL, 'SCM센터', '2000', '/2000/2390', '1', '15')
INSERT INTO smGroup VALUES('2391', '        SCM센터', '2000', '1', NULL, 'SCM센터', '2390', '/2000/2390/2391', '2', '15')
INSERT INTO smGroup VALUES('2113', '        경영전략3팀', '2000', '1', NULL, '경영전략3팀', '2110', '/2000/2110/2113', '2', '4')

UPDATE smGroup SET enabled = '1' WHERE id = '2123'
UPDATE smGroup SET enabled = '1' WHERE id = '2163'

UPDATE smGroup SET seq = '16' WHERE left(id,3) = '240'

UPDATE smGroup SET enabled = '0' WHERE LEFT(id, 1) = '6'

UPDATE smGroup SET name = '    지식경영실', description = '지식경영실' WHERE id = '2180'
UPDATE smGroup SET name = '        지식경영1팀', description = '지식경영1팀' WHERE id = '2181'
UPDATE smGroup SET name = '        지식경영2팀', description = '지식경영2팀' WHERE id = '2182'
UPDATE smGroup SET name = '        생산혁신담당', description = '생산혁신담당' WHERE id = '2034'

UPDATE smGroup SET name = replace(name, ' BU', '본부'), description =  replace(description, ' BU', '본부') WHERE name LIKE '% BU%'

UPDATE smGroup SET name = replace(name, ' 1팀', '1팀'), description =  replace(description, ' 1팀', '1팀') WHERE name LIKE '% 1팀%'
UPDATE smGroup SET name = replace(name, ' 2팀', '2팀'), description =  replace(description, ' 2팀', '2팀') WHERE name LIKE '% 2팀%'
UPDATE smGroup SET name = replace(name, ' 3팀', '3팀'), description =  replace(description, ' 3팀', '3팀') WHERE name LIKE '% 3팀%'
UPDATE smGroup SET name = replace(name, ' 4팀', '4팀'), description =  replace(description, ' 4팀', '4팀') WHERE name LIKE '% 4팀%'


-- 부서 정보 변경 쿼리(전문가 그룹: SM)	2009/12/06		yhyim --
UPDATE expertPool
SET dept = '2501', jobClass = 'J'
WHERE enable = '1' and jobClass in ('B', 'J') and name in (
'이기동',
'최무일',
'오세현',
'양천주',
'김용호',
'김의주',
'김대권',
'이상호',
'임제현',
'송상훈',
'한창모',
'김민정',
'김민영',
'김한필',
'김재일',
'임영빈',
'이복수',
'송미영',
'최림',
'박보영',
'이영호',
'신영기',
'박승립',
'홍권표',
'홍장표',
'이래완',
'김영주',
'황인화',
'한신',
'김신',
'오현')

UPDATE expertPool SET companyPosition = '16TJR', rate = '0.368' WHERE jobClass = 'J' AND name IN ('최무일', '강성기', '강태원')

-- 부서 정보 변경 쿼리(전문가 그룹: OM)	2009/12/06		yhyim --
UPDATE expertPool
SET dept = '2502', jobClass = 'J'
WHERE enable = '1' and jobClass in ('B', 'J') and name in (
'김성욱',
'정재호',
'강태원',
'최용준',
'이학림',
'김원중',
'오중열',
'배덕휴',
'남기서',
'김용철',
'강성기',
'박재홍',
'김교헌',
'차상목',
'유제선',
'최돈식',
'서용진',
'유융석',
'장광석',
'정연승',
'김경승',
'곽은관',
'조중희',
'안진성',
'조원중',
'양인준')

-- 부서 정보 변경 쿼리(보직자)	2009/12/14		yhyim --
UPDATE expertPool SET jobClass='A', companyPosition='06CA', Role='ROLE2006080116033211358', extRole='ROLE2006080116033211358', dept='2041' WHERE enable='1' AND jobClass IN ('A','B') AND name = '송옥현'
UPDATE expertPool SET jobClass='A', companyPosition='06CA', Role='ROLE2006080116033211358', extRole='ROLE2006080116033211358', dept='2042' WHERE enable='1' AND jobClass IN ('A','B') AND name = '고두균'
UPDATE expertPool SET jobClass='A', companyPosition='06CA', Role='ROLE2006080116033211358', extRole='ROLE2006080116033211358', dept='2043' WHERE enable='1' AND jobClass IN ('A','B') AND name = '이호성'
UPDATE expertPool SET jobClass='A', companyPosition='06CA', Role='ROLE2006080116033211358', extRole='ROLE2006080116033211358', dept='2044' WHERE enable='1' AND jobClass IN ('A','B') AND name = '김희철'


UPDATE expertPool SET jobClass='A', companyPosition='08CF', Role='ROLE2006080116033211358', extRole='ROLE2006080116033211358', dept='2110' WHERE enable='1' AND jobClass IN ('A','B') AND name = '이립'
UPDATE expertPool SET jobClass='A', companyPosition='08CF', Role='ROLE2006080116033211358', extRole='ROLE2006080116033211358', dept='2310' WHERE enable='1' AND jobClass IN ('A','B') AND name = '김익성'
UPDATE expertPool SET jobClass='A', companyPosition='08CF', Role='ROLE2006080116033211358', extRole='ROLE2006080116033211358', dept='2150' WHERE enable='1' AND jobClass IN ('A','B') AND name = '김상돈'
UPDATE expertPool SET jobClass='A', companyPosition='08CF', Role='ROLE2006080116033211358', extRole='ROLE2006080116033211358', dept='2120' WHERE enable='1' AND jobClass IN ('A','B') AND name = '한상록'
UPDATE expertPool SET jobClass='A', companyPosition='08CF', Role='ROLE2006080116033211358', extRole='ROLE2006080116033211358', dept='2160' WHERE enable='1' AND jobClass IN ('A','B') AND name = '국경묵'
UPDATE expertPool SET jobClass='A', companyPosition='09CJ', Role='ROLE2006080116033211358', extRole='ROLE2006080116033211358', dept='2380' WHERE enable='1' AND jobClass IN ('A','B') AND name = '윤철산'
UPDATE expertPool SET jobClass='A', companyPosition='09CJ', Role='ROLE2006080116033211358', extRole='ROLE2006080116033211358', dept='2390' WHERE enable='1' AND jobClass IN ('A','B') AND name = '김태완'
UPDATE expertPool SET jobClass='A', companyPosition='08CF', Role='ROLE2006080116033211358', extRole='ROLE2006080116033211358', dept='2320' WHERE enable='1' AND jobClass IN ('A','B') AND name = '유종국'
UPDATE expertPool SET jobClass='A', companyPosition='09CJ', Role='ROLE2006080116033211358', extRole='ROLE2006080116033211358', dept='2360' WHERE enable='1' AND jobClass IN ('A','B') AND name = '지방근'


UPDATE expertPool SET jobClass='A', companyPosition='10TM', Role='ROLE2006080116041070759', extRole='ROLE2006080116041070759', dept='2112' WHERE enable='1' AND jobClass IN ('A','B') AND name = '유훈'
UPDATE expertPool SET jobClass='A', companyPosition='10TM', Role='ROLE2006080116041070759', extRole='ROLE2006080116041070759', dept='2113' WHERE enable='1' AND jobClass IN ('A','B') AND name = '임명섭'
UPDATE expertPool SET jobClass='A', companyPosition='10TM', Role='ROLE2006080116041070759', extRole='ROLE2006080116041070759', dept='2311' WHERE enable='1' AND jobClass IN ('A','B') AND name = '송영욱'
UPDATE expertPool SET jobClass='A', companyPosition='10TM', Role='ROLE2006080116041070759', extRole='ROLE2006080116041070759', dept='2312' WHERE enable='1' AND jobClass IN ('A','B') AND name = '왕재군'
UPDATE expertPool SET jobClass='A', companyPosition='10TM', Role='ROLE2006080116041070759', extRole='ROLE2006080116041070759', dept='2151' WHERE enable='1' AND jobClass IN ('A','B') AND name = '황현철'
UPDATE expertPool SET jobClass='A', companyPosition='10TM', Role='ROLE2006080116041070759', extRole='ROLE2006080116041070759', dept='2152' WHERE enable='1' AND jobClass IN ('A','B') AND name = '이상훈'
UPDATE expertPool SET jobClass='A', companyPosition='10TM', Role='ROLE2006080116041070759', extRole='ROLE2006080116041070759', dept='2121' WHERE enable='1' AND jobClass IN ('A','B') AND name = '이상윤'
UPDATE expertPool SET jobClass='A', companyPosition='10TM', Role='ROLE2006080116041070759', extRole='ROLE2006080116041070759', dept='2122' WHERE enable='1' AND jobClass IN ('A','B') AND name = '권시환'
UPDATE expertPool SET jobClass='A', companyPosition='10TM', Role='ROLE2006080116041070759', extRole='ROLE2006080116041070759', dept='2123' WHERE enable='1' AND jobClass IN ('A','B') AND name = '심정래'
UPDATE expertPool SET jobClass='A', companyPosition='10TM', Role='ROLE2006080116041070759', extRole='ROLE2006080116041070759', dept='2131' WHERE enable='1' AND jobClass IN ('A','B') AND name = '이기동'
UPDATE expertPool SET jobClass='A', companyPosition='10TM', Role='ROLE2006080116041070759', extRole='ROLE2006080116041070759', dept='2132' WHERE enable='1' AND jobClass IN ('A','B') AND name = '황창환'
UPDATE expertPool SET jobClass='A', companyPosition='10TM', Role='ROLE2006080116041070759', extRole='ROLE2006080116041070759', dept='2161' WHERE enable='1' AND jobClass IN ('A','B') AND name = '황재웅'
UPDATE expertPool SET jobClass='A', companyPosition='10TM', Role='ROLE2006080116041070759', extRole='ROLE2006080116041070759', dept='2162' WHERE enable='1' AND jobClass IN ('A','B') AND name = '고정일'
UPDATE expertPool SET jobClass='A', companyPosition='10TM', Role='ROLE2006080116041070759', extRole='ROLE2006080116041070759', dept='2163' WHERE enable='1' AND jobClass IN ('A','B') AND name = '김성용'
UPDATE expertPool SET jobClass='A', companyPosition='10TM', Role='ROLE2006080116041070759', extRole='ROLE2006080116041070759', dept='2322' WHERE enable='1' AND jobClass IN ('A','B') AND name = '오철세'
UPDATE expertPool SET jobClass='A', companyPosition='10TM', Role='ROLE2006080116041070759', extRole='ROLE2006080116041070759', dept='2181' WHERE enable='1' AND jobClass IN ('A','B') AND name = '김종운'
UPDATE expertPool SET jobClass='A', companyPosition='10TM', dept='2182' WHERE enable='1' AND jobClass IN ('A','B') AND name = '우상재'

UPDATE expertPool SET jobClass='A', companyPosition='10CM', Role='ROLE2006080116041070759', extRole='ROLE2006080116041070759', dept='2041'  WHERE enable='1' AND jobClass IN ('A','B') AND name = '최진수'
UPDATE expertPool SET jobClass='A', companyPosition='10CM', Role='ROLE2006080116041070759', extRole='ROLE2006080116041070759', dept='2042'  WHERE enable='1' AND jobClass IN ('A','B') AND name = '강신명'
UPDATE expertPool SET jobClass='A', companyPosition='10CM', Role='ROLE2006080116041070759', extRole='ROLE2006080116041070759', dept='2043'  WHERE enable='1' AND jobClass IN ('A','B') AND name = '홍기옥'

-- 부서 정보 변경 쿼리(상근)	2009/12/06		yhyim --
UPDATE expertPool SET jobClass = 'A', dept='2111', Role='ROLE2006080116061636366', extRole='ROLE2006080116061636366' WHERE enable='1' AND jobClass IN('A', 'B', 'J') AND name = '김미홍'
UPDATE expertPool SET jobClass = 'A', dept='2111', Role='ROLE2006080116061636366', extRole='ROLE2006080116061636366' WHERE enable='1' AND jobClass IN('A', 'B', 'J') AND name = '송광호'
UPDATE expertPool SET jobClass = 'A', dept='2111', Role='ROLE2006080116061636366', extRole='ROLE2006080116061636366' WHERE enable='1' AND jobClass IN('A', 'B', 'J') AND name = '오경석'
UPDATE expertPool SET jobClass = 'A', dept='2112', Role='ROLE2006080116061636366', extRole='ROLE2006080116061636366' WHERE enable='1' AND jobClass IN('A', 'B', 'J') AND name = '이승한'
UPDATE expertPool SET jobClass = 'A', dept='2112', Role='ROLE2006080116061636366', extRole='ROLE2006080116061636366' WHERE enable='1' AND jobClass IN('A', 'B', 'J') AND name = '이상범'
UPDATE expertPool SET jobClass = 'A', dept='2112', Role='ROLE2006080116061636366', extRole='ROLE2006080116061636366' WHERE enable='1' AND jobClass IN('A', 'B', 'J') AND name = '김성원'
UPDATE expertPool SET jobClass = 'A', dept='2113', Role='ROLE2006080116061636366', extRole='ROLE2006080116061636366' WHERE enable='1' AND jobClass IN('A', 'B', 'J') AND name = '정경석'
UPDATE expertPool SET jobClass = 'A', dept='2113', Role='ROLE2006080116061636366', extRole='ROLE2006080116061636366' WHERE enable='1' AND jobClass IN('A', 'B', 'J') AND name = '한희성'
UPDATE expertPool SET jobClass = 'A', dept='2113', Role='ROLE2006080116061636366', extRole='ROLE2006080116061636366' WHERE enable='1' AND jobClass IN('A', 'B', 'J') AND name = '박재석'
UPDATE expertPool SET jobClass = 'A', dept='2113', Role='ROLE2006080116061636366', extRole='ROLE2006080116061636366' WHERE enable='1' AND jobClass IN('A', 'B', 'J') AND name = '박재성'
UPDATE expertPool SET jobClass = 'A', dept='2113', Role='ROLE2006080116061636366', extRole='ROLE2006080116061636366' WHERE enable='1' AND jobClass IN('A', 'B', 'J') AND name = '이세호'
UPDATE expertPool SET jobClass = 'A', dept='2311', companyPosition='17CJR', Rate='0.315', Role='ROLE2006080116061636366', extRole='ROLE2006080116061636366' WHERE enable='1' AND jobClass IN('A', 'B', 'J') AND name = '정우수'
UPDATE expertPool SET jobClass = 'A', dept='2311', Role='ROLE2006080116061636366', extRole='ROLE2006080116061636366' WHERE enable='1' AND jobClass IN('A', 'B', 'J') AND name = '성종필'
UPDATE expertPool SET jobClass = 'A', dept='2311', Role='ROLE2006080116061636366', extRole='ROLE2006080116061636366' WHERE enable='1' AND jobClass IN('A', 'B', 'J') AND name = '지원욱'
UPDATE expertPool SET jobClass = 'A', dept='2311', Role='ROLE2006080116061636366', extRole='ROLE2006080116061636366' WHERE enable='1' AND jobClass IN('A', 'B', 'J') AND name = '주진형'
UPDATE expertPool SET jobClass = 'A', dept='2311', Role='ROLE2006080116061636366', extRole='ROLE2006080116061636366' WHERE enable='1' AND jobClass IN('A', 'B', 'J') AND name = '김상훈'
UPDATE expertPool SET jobClass = 'A', dept='2311', Role='ROLE2006080116061636366', extRole='ROLE2006080116061636366' WHERE enable='1' AND jobClass IN('A', 'B', 'J') AND name = '장정호'
UPDATE expertPool SET jobClass = 'A', dept='2312', Role='ROLE2006080116061636366', extRole='ROLE2006080116061636366' WHERE enable='1' AND jobClass IN('A', 'B', 'J') AND name = '정한규'
UPDATE expertPool SET jobClass = 'A', dept='2312', Role='ROLE2006080116061636366', extRole='ROLE2006080116061636366' WHERE enable='1' AND jobClass IN('A', 'B', 'J') AND name = '정홍주'
UPDATE expertPool SET jobClass = 'A', dept='2312', Role='ROLE2006080116061636366', extRole='ROLE2006080116061636366' WHERE enable='1' AND jobClass IN('A', 'B', 'J') AND name = '최병규'
UPDATE expertPool SET jobClass = 'A', dept='2312', Role='ROLE2006080116061636366', extRole='ROLE2006080116061636366' WHERE enable='1' AND jobClass IN('A', 'B', 'J') AND name = '김선근'
UPDATE expertPool SET jobClass = 'A', dept='2312', Role='ROLE2006080116061636366', extRole='ROLE2006080116061636366' WHERE enable='1' AND jobClass IN('A', 'B', 'J') AND name = '김동현'
UPDATE expertPool SET jobClass = 'A', dept='2312', Role='ROLE2006080116061636366', extRole='ROLE2006080116061636366' WHERE enable='1' AND jobClass IN('A', 'B', 'J') AND name = '차진영'
UPDATE expertPool SET jobClass = 'A', dept='2312', Role='ROLE2006080116061636366', extRole='ROLE2006080116061636366' WHERE enable='1' AND jobClass IN('A', 'B', 'J') AND name = '정승균'
UPDATE expertPool SET jobClass = 'A', dept='2312', Role='ROLE2006080116061636366', extRole='ROLE2006080116061636366' WHERE enable='1' AND jobClass IN('A', 'B', 'J') AND name = '최윤석'
UPDATE expertPool SET jobClass = 'A', dept='2312', Role='ROLE2006080116061636366', extRole='ROLE2006080116061636366' WHERE enable='1' AND jobClass IN('A', 'B', 'J') AND name = '정다운'
UPDATE expertPool SET jobClass = 'A', dept='2151', Role='ROLE2006080116061636366', extRole='ROLE2006080116061636366' WHERE enable='1' AND jobClass IN('A', 'B', 'J') AND name = '이명연'
UPDATE expertPool SET jobClass = 'A', dept='2151', Role='ROLE2006080116061636366', extRole='ROLE2006080116061636366' WHERE enable='1' AND jobClass IN('A', 'B', 'J') AND name = '원승재'
UPDATE expertPool SET jobClass = 'A', dept='2151', Role='ROLE2006080116061636366', extRole='ROLE2006080116061636366' WHERE enable='1' AND jobClass IN('A', 'B', 'J') AND name = '허일정'
UPDATE expertPool SET jobClass = 'A', dept='2152', Role='ROLE2006080116061636366', extRole='ROLE2006080116061636366' WHERE enable='1' AND jobClass IN('A', 'B', 'J') AND name = '하철현'
UPDATE expertPool SET jobClass = 'A', dept='2152', Role='ROLE2006080116061636366', extRole='ROLE2006080116061636366' WHERE enable='1' AND jobClass IN('A', 'B', 'J') AND name = '이태헌'
UPDATE expertPool SET jobClass = 'A', dept='2152', Role='ROLE2006080116061636366', extRole='ROLE2006080116061636366' WHERE enable='1' AND jobClass IN('A', 'B', 'J') AND name = '김정환'
UPDATE expertPool SET jobClass = 'A', dept='2152', Role='ROLE2006080116061636366', extRole='ROLE2006080116061636366' WHERE enable='1' AND jobClass IN('A', 'B', 'J') AND name = '류명욱'
UPDATE expertPool SET jobClass = 'A', dept='2152', Role='ROLE2006080116061636366', extRole='ROLE2006080116061636366' WHERE enable='1' AND jobClass IN('A', 'B', 'J') AND name = '조병민'
UPDATE expertPool SET jobClass = 'A', dept='2152', Role='ROLE2006080116061636366', extRole='ROLE2006080116061636366' WHERE enable='1' AND jobClass IN('A', 'B', 'J') AND name = '서종윤'
UPDATE expertPool SET jobClass = 'A', dept='2121', Role='ROLE2006080116061636366', extRole='ROLE2006080116061636366' WHERE enable='1' AND jobClass IN('A', 'B', 'J') AND name = '노일식'
UPDATE expertPool SET jobClass = 'A', dept='2121', Role='ROLE2006080116061636366', extRole='ROLE2006080116061636366' WHERE enable='1' AND jobClass IN('A', 'B', 'J') AND name = '이정수'
UPDATE expertPool SET jobClass = 'A', dept='2121', Role='ROLE2006080116061636366', extRole='ROLE2006080116061636366' WHERE enable='1' AND jobClass IN('A', 'B', 'J') AND name = '임형주'
UPDATE expertPool SET jobClass = 'A', dept='2122', Role='ROLE2006080116061636366', extRole='ROLE2006080116061636366' WHERE enable='1' AND jobClass IN('A', 'B', 'J') AND name = '김영훈'
UPDATE expertPool SET jobClass = 'A', dept='2122', Role='ROLE2006080116061636366', extRole='ROLE2006080116061636366' WHERE enable='1' AND jobClass IN('A', 'B', 'J') AND name = '강상만'
UPDATE expertPool SET jobClass = 'A', dept='2122', Role='ROLE2006080116061636366', extRole='ROLE2006080116061636366' WHERE enable='1' AND jobClass IN('A', 'B', 'J') AND name = '이미란'
UPDATE expertPool SET jobClass = 'A', dept='2123', Role='ROLE2006080116061636366', extRole='ROLE2006080116061636366' WHERE enable='1' AND jobClass IN('A', 'B', 'J') AND name = '임재민'
UPDATE expertPool SET jobClass = 'A', dept='2123', Role='ROLE2006080116061636366', extRole='ROLE2006080116061636366' WHERE enable='1' AND jobClass IN('A', 'B', 'J') AND name = '양정호'
UPDATE expertPool SET jobClass = 'A', dept='2123', Role='ROLE2006080116061636366', extRole='ROLE2006080116061636366' WHERE enable='1' AND jobClass IN('A', 'B', 'J') AND name = '김현회'
UPDATE expertPool SET jobClass = 'A', dept='2123', Role='ROLE2006080116061636366', extRole='ROLE2006080116061636366' WHERE enable='1' AND jobClass IN('A', 'B', 'J') AND name = '김보성'
UPDATE expertPool SET jobClass = 'A', dept='2123', Role='ROLE2006080116061636366', extRole='ROLE2006080116061636366' WHERE enable='1' AND jobClass IN('A', 'B', 'J') AND name = '김동현'
UPDATE expertPool SET jobClass = 'A', dept='2123', Role='ROLE2006080116061636366', extRole='ROLE2006080116061636366' WHERE enable='1' AND jobClass IN('A', 'B', 'J') AND name = '오재영'
UPDATE expertPool SET jobClass = 'A', dept='2123', Role='ROLE2006080116061636366', extRole='ROLE2006080116061636366' WHERE enable='1' AND jobClass IN('A', 'B', 'J') AND name = '김기열'
UPDATE expertPool SET jobClass = 'A', dept='2131', Role='ROLE2006080116061636366', extRole='ROLE2006080116061636366' WHERE enable='1' AND jobClass IN('A', 'B', 'J') AND name = '박창윤'
UPDATE expertPool SET jobClass = 'A', dept='2131', Role='ROLE2006080116061636366', extRole='ROLE2006080116061636366' WHERE enable='1' AND jobClass IN('A', 'B', 'J') AND name = '신유진'
UPDATE expertPool SET jobClass = 'A', dept='2131', Role='ROLE2006080116061636366', extRole='ROLE2006080116061636366' WHERE enable='1' AND jobClass IN('A', 'B', 'J') AND name = '방지현'
UPDATE expertPool SET jobClass = 'A', dept='2131', Role='ROLE2006080116061636366', extRole='ROLE2006080116061636366' WHERE enable='1' AND jobClass IN('A', 'B', 'J') AND name = '안충근'
UPDATE expertPool SET jobClass = 'A', dept='2132', Role='ROLE2006080116061636366', extRole='ROLE2006080116061636366' WHERE enable='1' AND jobClass IN('A', 'B', 'J') AND name = '조성철'
UPDATE expertPool SET jobClass = 'A', dept='2132', Role='ROLE2006080116061636366', extRole='ROLE2006080116061636366' WHERE enable='1' AND jobClass IN('A', 'B', 'J') AND name = '이주열'
UPDATE expertPool SET jobClass = 'A', dept='2132', Role='ROLE2006080116061636366', extRole='ROLE2006080116061636366' WHERE enable='1' AND jobClass IN('A', 'B', 'J') AND name = '최중락'
UPDATE expertPool SET jobClass = 'A', dept='2141', Role='ROLE2006080116061636366', extRole='ROLE2006080116061636366' WHERE enable='1' AND jobClass IN('A', 'B', 'J') AND name = '김명성'
UPDATE expertPool SET jobClass = 'A', dept='2141', Role='ROLE2006080116061636366', extRole='ROLE2006080116061636366' WHERE enable='1' AND jobClass IN('A', 'B', 'J') AND name = '박정오'
UPDATE expertPool SET jobClass = 'A', dept='2141', Role='ROLE2006080116061636366', extRole='ROLE2006080116061636366' WHERE enable='1' AND jobClass IN('A', 'B', 'J') AND name = '허대중'
UPDATE expertPool SET jobClass = 'A', dept='2141', Role='ROLE2006080116061636366', extRole='ROLE2006080116061636366' WHERE enable='1' AND jobClass IN('A', 'B', 'J') AND name = '정재영'
UPDATE expertPool SET jobClass = 'A', dept='2142', Role='ROLE2006080116061636366', extRole='ROLE2006080116061636366' WHERE enable='1' AND jobClass IN('A', 'B', 'J') AND name = '윤상호'
UPDATE expertPool SET jobClass = 'A', dept='2142', Role='ROLE2006080116061636366', extRole='ROLE2006080116061636366' WHERE enable='1' AND jobClass IN('A', 'B', 'J') AND name = '김병수'
UPDATE expertPool SET jobClass = 'A', dept='2142', Role='ROLE2006080116061636366', extRole='ROLE2006080116061636366' WHERE enable='1' AND jobClass IN('A', 'B', 'J') AND name = '김기민'
UPDATE expertPool SET jobClass = 'A', dept='2143', Role='ROLE2006080116061636366', extRole='ROLE2006080116061636366' WHERE enable='1' AND jobClass IN('A', 'B', 'J') AND name = '진현진'
UPDATE expertPool SET jobClass = 'A', dept='2143', Role='ROLE2006080116061636366', extRole='ROLE2006080116061636366' WHERE enable='1' AND jobClass IN('A', 'B', 'J') AND name = '안영률'
UPDATE expertPool SET jobClass = 'A', dept='2143', Role='ROLE2006080116061636366', extRole='ROLE2006080116061636366' WHERE enable='1' AND jobClass IN('A', 'B', 'J') AND name = '이재희'
UPDATE expertPool SET jobClass = 'A', dept='2143', Role='ROLE2006080116061636366', extRole='ROLE2006080116061636366' WHERE enable='1' AND jobClass IN('A', 'B', 'J') AND name = '김회성'
UPDATE expertPool SET jobClass = 'A', dept='2371', Role='ROLE2006080116061636366', extRole='ROLE2006080116061636366' WHERE enable='1' AND jobClass IN('A', 'B', 'J') AND name = '손수웅'
UPDATE expertPool SET jobClass = 'A', dept='2371', Role='ROLE2006080116061636366', extRole='ROLE2006080116061636366' WHERE enable='1' AND jobClass IN('A', 'B', 'J') AND name = '김동준'
UPDATE expertPool SET jobClass = 'A', dept='2161', Role='ROLE2006080116061636366', extRole='ROLE2006080116061636366' WHERE enable='1' AND jobClass IN('A', 'B', 'J') AND name = '이석범'
UPDATE expertPool SET jobClass = 'A', dept='2161', Role='ROLE2006080116061636366', extRole='ROLE2006080116061636366' WHERE enable='1' AND jobClass IN('A', 'B', 'J') AND name = '심종길'
UPDATE expertPool SET jobClass = 'A', dept='2162', Role='ROLE2006080116061636366', extRole='ROLE2006080116061636366' WHERE enable='1' AND jobClass IN('A', 'B', 'J') AND name = '윤희성'
UPDATE expertPool SET jobClass = 'A', dept='2162', Role='ROLE2006080116061636366', extRole='ROLE2006080116061636366' WHERE enable='1' AND jobClass IN('A', 'B', 'J') AND name = '김병삼'
UPDATE expertPool SET jobClass = 'A', dept='2162', Role='ROLE2006080116061636366', extRole='ROLE2006080116061636366' WHERE enable='1' AND jobClass IN('A', 'B', 'J') AND name = '정경구'
UPDATE expertPool SET jobClass = 'A', dept='2163', Role='ROLE2006080116061636366', extRole='ROLE2006080116061636366' WHERE enable='1' AND jobClass IN('A', 'B', 'J') AND name = '김진'
UPDATE expertPool SET jobClass = 'A', dept='2163', Role='ROLE2006080116061636366', extRole='ROLE2006080116061636366' WHERE enable='1' AND jobClass IN('A', 'B', 'J') AND name = '최선호'
UPDATE expertPool SET jobClass = 'A', dept='2163', Role='ROLE2006080116061636366', extRole='ROLE2006080116061636366' WHERE enable='1' AND jobClass IN('A', 'B', 'J') AND name = '배종욱'
UPDATE expertPool SET jobClass = 'A', dept='2163', Role='ROLE2006080116061636366', extRole='ROLE2006080116061636366' WHERE enable='1' AND jobClass IN('A', 'B', 'J') AND name = '이민웅'
UPDATE expertPool SET jobClass = 'A', dept='2381', Role='ROLE2006080116061636366', extRole='ROLE2006080116061636366' WHERE enable='1' AND jobClass IN('A', 'B', 'J') AND name = '노병주'
UPDATE expertPool SET jobClass = 'A', dept='2381', Role='ROLE2006080116061636366', extRole='ROLE2006080116061636366' WHERE enable='1' AND jobClass IN('A', 'B', 'J') AND name = '신민수'
UPDATE expertPool SET jobClass = 'A', dept='2381', Role='ROLE2006080116061636366', extRole='ROLE2006080116061636366' WHERE enable='1' AND jobClass IN('A', 'B', 'J') AND name = '신정수'
UPDATE expertPool SET jobClass = 'A', dept='2321', Role='ROLE2006080116061636366', extRole='ROLE2006080116061636366' WHERE enable='1' AND jobClass IN('A', 'B', 'J') AND name = '차지현'
UPDATE expertPool SET jobClass = 'A', dept='2321', Role='ROLE2006080116061636366', extRole='ROLE2006080116061636366' WHERE enable='1' AND jobClass IN('A', 'B', 'J') AND name = '남상욱'
UPDATE expertPool SET jobClass = 'A', dept='2321', Role='ROLE2006080116061636366', extRole='ROLE2006080116061636366' WHERE enable='1' AND jobClass IN('A', 'B', 'J') AND name = '민지경'
UPDATE expertPool SET jobClass = 'A', dept='2321', Role='ROLE2006080116061636366', extRole='ROLE2006080116061636366' WHERE enable='1' AND jobClass IN('A', 'B', 'J') AND name = '이상우'
UPDATE expertPool SET jobClass = 'A', dept='2322', Role='ROLE2006080116061636366', extRole='ROLE2006080116061636366' WHERE enable='1' AND jobClass IN('A', 'B', 'J') AND name = '김범주'
UPDATE expertPool SET jobClass = 'A', dept='2322', Role='ROLE2006080116061636366', extRole='ROLE2006080116061636366' WHERE enable='1' AND jobClass IN('A', 'B', 'J') AND name = '이강수'
UPDATE expertPool SET jobClass = 'A', dept='2322', Role='ROLE2006080116061636366', extRole='ROLE2006080116061636366' WHERE enable='1' AND jobClass IN('A', 'B', 'J') AND name = '남현임'
UPDATE expertPool SET jobClass = 'A', dept='2322', Role='ROLE2006080116061636366', extRole='ROLE2006080116061636366' WHERE enable='1' AND jobClass IN('A', 'B', 'J') AND name = '유경철'
UPDATE expertPool SET jobClass = 'A', dept='2322', Role='ROLE2006080116061636366', extRole='ROLE2006080116061636366' WHERE enable='1' AND jobClass IN('A', 'B', 'J') AND name = '김미진'
UPDATE expertPool SET jobClass = 'A', dept='2322', Role='ROLE2006080116061636366', extRole='ROLE2006080116061636366' WHERE enable='1' AND jobClass IN('A', 'B', 'J') AND name = '최정희'
UPDATE expertPool SET jobClass = 'A', dept='2361', Role='ROLE2006080116061636366', extRole='ROLE2006080116061636366' WHERE enable='1' AND jobClass IN('A', 'B', 'J') AND name = '진상기'
UPDATE expertPool SET jobClass = 'A', dept='2361', Role='ROLE2006080116061636366', extRole='ROLE2006080116061636366' WHERE enable='1' AND jobClass IN('A', 'B', 'J') AND name = '형준희'
UPDATE expertPool SET jobClass = 'A', dept='2361', Role='ROLE2006080116061636366', extRole='ROLE2006080116061636366' WHERE enable='1' AND jobClass IN('A', 'B', 'J') AND name = '유보훈'
UPDATE expertPool SET jobClass = 'A', dept='2361', Role='ROLE2006080116061636366', extRole='ROLE2006080116061636366' WHERE enable='1' AND jobClass IN('A', 'B', 'J') AND name = '윤준호'
UPDATE expertPool SET jobClass = 'A', dept='2181', Role='ROLE2006080116061636366', extRole='ROLE2006080116061636366' WHERE enable='1' AND jobClass IN('A', 'B', 'J') AND name = '홍문기'
UPDATE expertPool SET jobClass = 'A', dept='2181', Role='ROLE2006080116061636366', extRole='ROLE2006080116061636366' WHERE enable='1' AND jobClass IN('A', 'B', 'J') AND name = '정재욱'
UPDATE expertPool SET jobClass = 'A', dept='2181', Role='ROLE2006080116061636366', extRole='ROLE2006080116061636366' WHERE enable='1' AND jobClass IN('A', 'B', 'J') AND name = '신효숙'
UPDATE expertPool SET jobClass = 'A', dept='2181', Role='ROLE2006080116061636366', extRole='ROLE2006080116061636366' WHERE enable='1' AND jobClass IN('A', 'B', 'J') AND name = '조인열'
UPDATE expertPool SET jobClass = 'A', dept='2182', Role='ROLE2006080116061636366', extRole='ROLE2006080116061636366' WHERE enable='1' AND jobClass IN('A', 'B', 'J') AND name = '임영훈'
UPDATE expertPool SET jobClass = 'A', dept='2182', Role='ROLE2006080116061636366', extRole='ROLE2006080116061636366' WHERE enable='1' AND jobClass IN('A', 'B', 'J') AND name = '오영택'
UPDATE expertPool SET jobClass = 'A', dept='2191', Role='ROLE2006080116061636366', extRole='ROLE2006080116061636366' WHERE enable='1' AND jobClass IN('A', 'B', 'J') AND name = '추교민'
UPDATE expertPool SET jobClass = 'A', dept='2191', Role='ROLE2006080116061636366', extRole='ROLE2006080116061636366' WHERE enable='1' AND jobClass IN('A', 'B', 'J') AND name = '유상경'
UPDATE expertPool SET jobClass = 'A', dept='2391', Role='ROLE2006080116061636366', extRole='ROLE2006080116061636366' WHERE enable='1' AND jobClass IN('A', 'B', 'J') AND name = '이재환'

-- 부서명, 직위명 업데이트	2009/12/21		yhyim --
UPDATE expertPool 
SET deptName=(SELECT description FROM smGroup WHERE id=dept), companyPositionName=(SELECT description FROM smRole WHERE id=companyPosition)
WHERE enable='1' and jobClass in ('A', 'J')

-- 이용 중이지 않은 사용자 권한 없애기
UPDATE expertPool SET role=NULL, extRole=NULL WHERE enable = '0' AND role <> '' AND role IS NOT NULL

-- 인건비 기대 매출 추가 쿼리	2009/12/08		yhyim --
INSERT INTO ProjectStdCost VALUES('15EJR','파트너','1500000','0.42','BTA')
INSERT INTO ProjectStdCost VALUES('16TJR','이그젝티브','1300000','0.368','BTA')
INSERT INTO ProjectStdCost VALUES('17CJR','프린서플','1150000','0.315','BTA')
INSERT INTO ProjectStdCost VALUES('18SJR','마스터','1000000','0.273','BTA')
INSERT INTO ProjectStdCost VALUES('19JR','치프','850000','0.241','BTA')
INSERT INTO ProjectStdCost VALUES('20SR','시니어','750000','0.21','BTA')
INSERT INTO ProjectStdCost VALUES('22NR','컨설턴트','650000','0.178','BTA')
INSERT INTO ProjectStdCost VALUES('23NRC','AssociateⅠ','450000','0.148','BTA')
INSERT INTO ProjectStdCost VALUES('23NRD','AssociateⅡ','450000','0.131','BTA')
INSERT INTO ProjectStdCost VALUES('23NRE','AssociateⅢ','450000','0.115','BTA')
INSERT INTO ProjectStdCost VALUES('23NRF','AssociateⅣ','450000','0.107','BTA')
INSERT INTO ProjectStdCost VALUES('23NRG','AssociateⅤ','450000','0.098','BTA')

INSERT INTO ProjectStdCost VALUES('15EJR','파트너','330000','0.42','BTE')
INSERT INTO ProjectStdCost VALUES('16TJR','이그젝티브','280000','0.368','BTE')
INSERT INTO ProjectStdCost VALUES('17CJR','프린서플','240000','0.315','BTE')
INSERT INTO ProjectStdCost VALUES('18SJR','마스터','210000','0.273','BTE')
INSERT INTO ProjectStdCost VALUES('19JR','치프','180000','0.241','BTE')
INSERT INTO ProjectStdCost VALUES('20SR','시니어','160000','0.21','BTE')
INSERT INTO ProjectStdCost VALUES('22NR','컨설턴트','140000','0.178','BTE')



-- 강사료 테이블 변경 쿼리	2009/12/03		yhyim --


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





-- 온라인 예산 시스템 사용자 등록
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
