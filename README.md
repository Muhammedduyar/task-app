# Task Manager App

Spring Boot kullanarak geliştirilmiş basit bir görev yönetim uygulaması.

---

## Özellikler

- Kullanıcı kimlik doğrulama (JWT ile)
- Görev CRUD işlemleri (Oluşturma, Okuma, Güncelleme, Silme)
- Görev durumları ve önceliklerin yönetimi
- RESTful API mimarisi

---

## Teknolojiler

- Java 17+
- Spring Boot
- Spring Security + JWT
- Spring Data JPA (Hibernate)
- PostgreSQL (veya tercih edilen başka bir veritabanı)
- Lombok

## Kurulum ve Çalıştırma

1. Projeyi klonla:

   ```bash
   git clone <proje-repo-url>
   cd task-manager-app

2. Veritabanı ayarlarını yap application.yml
  spring:
  application:
    name: Task Manager Application
  datasource:
    url: jdbc:postgresql://localhost:5432/task_db
    username: postgres
    password: 1234

application:
  security:
    jwt:
      secret-key: Z3JvdXBzdXBwb3NlaGVhbHRoYXJlZHJpdmluZ3BvZXRhdHRhY2hlZHJpc2luZ2Rpc3Q=
      expiration: 86400000
      refresh-token:
        expiration: 604800000
3. Projeyi çalıştır
4. Uygulama localhost:8080 de çalışacaktır

Giriş yaptığınızda JWT token alınır.
Yetkilendirilmiş endpointlerde Authorization header'ına Bearer <token> ekleyerek istek atılır.

List all api örneği
![image](https://github.com/user-attachments/assets/eb835ed6-0664-410e-8bf0-825e7fc9fdc5)



