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

09/19
이메일 인증 시, 에러 >> 야믈 파일에 아래 넣기.
```
#이메일
  mail:
    protocol: smtp
    host: smtp.gmail.com
    port: 587
    username: chlrkgms11@gmail.com
    password: jickydvlvsotweri
    properties:
      mail:
          smtp:
            auth: true
            starttls:
              enable: true
              required: true
            connectiontimeout: 5000
            timeout: 5000
            writetimeout: 5000
    auth-code-expiration-millis: 1800000  # 30 * 60 * 1000 == 30분
```

## STS ## 부분에 
*.yml 이 적혀있지 않다면 추가하시면 좋을 듯합니다. 
더불어 저희의 팀 Notion에 TripMaven - 최종 프로젝트 - 각자의 gitignore란을 만들었습니다. 
'최환성'의 토글 목록과 코드조각을 참고하시어 다들 변경, 추가 사항이 있다면 각자의 yml파일을 등재해주시면 감사드립니다. 
