server:
  port: 9000
#  debug: true

spring:
  application:
    name: bank

  profiles:
    active: prod
---
spring.config.activate.on-profile: local

spring:
  datasource:
    url: jdbc:h2:mem:bank
    driver-class-name: org.h2.Driver
    username: root
    password: 2323
    initialization-mode: always

  h2:
    console:
      enabled: true
      path: /h2-console

  jpa:
    hibernate:
      ddl-auto: update
    show-sql: true
---
spring.config.activate.on-profile: prod

spring:
  config:
    import: optional:file:.env[.properties]

  datasource:
    url: jdbc:mysql://bank-stepprojecttinder.j.aivencloud.com:18547/bank?sessionVariables=sql_require_primary_key=OFF
    driver-class-name: com.mysql.cj.jdbc.Driver
    username: avnadmin
    password: AVNS_saVDccHwLiRVxoM7bs2

    jpa:
      database-platform: org.hibernate.dialect.MySQLDialect
      show-sql: true
      hibernate:
        ddl-auto: update