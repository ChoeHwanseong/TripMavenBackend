서버 실행시 오류가 난다면. 
09/14
UPDATE members SET profile_temp = profile;
ALTER TABLE members DROP COLUMN profile;
ALTER TABLE members RENAME COLUMN profile_temp TO profile;
ALTER TABLE members MODIFY address VARCHAR2(255);

09/15
ALTER TABLE productboard ADD files_temp CLOB;
UPDATE productboard SET files_temp = files;
ALTER TABLE productboard DROP COLUMN files;
ALTER TABLE productboard RENAME COLUMN files_temp TO files;


불철 주야 고생하시는 1팀. 'TripMaven' 학우분들 반갑습니다. 
GitHub 사용에 있어서 yml파일은 올릴 수 없습니다. 
따라서 여러분들도. 해당 .gitignore 파일에서 
## STS ## 부분에 
*.yml 이 적혀있지 않다면 추가하시면 좋을 듯합니다. 
더불어 저희의 팀 Notion에 TripMaven - 최종 프로젝트 - 각자의 gitignore란을 만들었습니다. 
'최환성'의 토글 목록과 코드조각을 참고하시어 다들 변경, 추가 사항이 있다면 각자의 yml파일을 등재해주시면 감사드립니다. 

8/29 야믈 파일: 카카오 오픈 키 추가.
# 카카오 API 키
kakao:
  api:
    key: 7473de86ee433a0814c0bd047ad30e9c

9/12 yaml : JPA 설정 추가.                  가이드랭킹 올리다가 디렉토리가 잘못올라갔습니다 수정좀 해주십쇼
#Spring Data JPA 설정
  jpa:
    hibernate:
      ddl-auto: update #ddl 자동 생성
      naming:
        physical-strategy: org.hibernate.boot.model.naming.PhysicalNamingStrategyStandardImpl #JPA 컬럼명 전략
    properties:
      hibernate:
        format_sql: true #DDL문 가독성 있게 표시
    show-sql: true
    packages-to-scan: com.tripmaven #이 줄 추가
