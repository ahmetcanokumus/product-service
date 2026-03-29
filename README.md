# Product Service

HT mikroservis mimarisinde ürün verilerini yöneten Spring Boot servisi. Ürünleri hiyerarşik olarak (temel ürün → stil varyantı → beden varyantı) saklar ve Eureka üzerinden keşfedilebilir.

## Özellikler

- **REST API**: Ürün oluşturma/güncelleme (inbound), koda göre sorgulama, silme
- **PostgreSQL**: JPA/Hibernate ile kalıcılık (`ddl-auto: update`)
- **Spring Cloud**: Eureka Client (servis kaydı), OpenFeign bağımlılığı
- **Haritalama**: MapStruct ile entity ↔ DTO dönüşümleri
- **Doğrulama**: Spring Validation

## Gereksinimler

- JDK (üst proje Gradle sürümüne uygun)
- PostgreSQL
- Çalışan bir **Eureka Server** (varsayılan: `http://localhost:8761/eureka/`)

## Yapılandırma

`src/main/resources/application.yml` içinde:

| Ayar | Açıklama |
|------|----------|
| `server.port` | HTTP port (varsayılan: **9095**) |
| `spring.datasource.*` | PostgreSQL bağlantısı (`ProductDB` vb.) |
| `eureka.client.service-url.defaultZone` | Eureka adresi; ortam değişkeni: `EUREKA_URI` |
| `jwt.secret` | JWT ile ilgili yapılandırma |

Üretim ortamında veritabanı şifresi ve JWT gizli anahtarını ortam değişkenleri veya güvenli bir yapılandırma kaynağı ile yönetin.

## Çalıştırma

Üst HT mikroservis deposundan bu modülü tanımlayan Gradle komutlarıyla derleyip çalıştırın (örnek):

```bash
./gradlew :product-service:bootRun
```

Komut modül adına göre değişebilir; kök `settings.gradle` ve üst `build.gradle` dosyalarına bakın.

## API Özeti

Temel yol: `/v1/product`

| Metot | Yol | Açıklama |
|--------|-----|----------|
| `POST` | `/v1/product/inbound-product` | Toplu inbound ürün oluşturma/güncelleme (`InboundProductRequestDTO`) |
| `GET` | `/v1/product/{code}` | `code` ile ürün detayı (bulunamazsa 404) |
| `DELETE` | `/v1/product/{code}` | Ürün silme |

## Veri modeli

- **BASE_PRODUCT**: Ana ürün
- **STYLE_VARIANT**: Stil varyantı (ör. renk), üstü temel ürün
- **SIZE_VARIANT**: Beden varyantı, üstü stil varyantı

Kod alanları (`code`) benzersizdir.

## Testler

```bash
./gradlew :product-service:test
```

(Modül yolu proje yapılandırmasına göre ayarlanmalıdır.)

## Notlar

- Controller içinde bazı uç noktalarda `@PreAuthorize` yorum satırıdır; yetkilendirme ihtiyacına göre etkinleştirilebilir.
- Bu README yalnızca `product-service` modülünü kapsar; ortak kütüphaneler ve sürüm bilgisi üst repodaki Gradle dosyalarında tanımlıdır.
