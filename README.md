# SJ Aqua Tech – Complete Spring Boot Starter

## Requirements
- JDK 17
- IntelliJ IDEA
- PostgreSQL 14+ (or compatible recent release)
- Internet connection for first Maven dependency download

## 1. Create database
In pgAdmin Query Tool or psql:
```sql
CREATE DATABASE sj_aqua_tech;
```

## 2. Configure database password
Open `src/main/resources/application.properties` and replace `CHANGE_ME` with your PostgreSQL password. You can alternatively set `DB_URL`, `DB_USERNAME`, and `DB_PASSWORD` environment variables.

## 3. Open and run
Open the extracted folder in IntelliJ IDEA as a Maven project. Wait for dependencies to load, then run:
`com.sjaquatech.SjaquatechApplication`

Open `http://localhost:8080`.

## Admin demo login
- URL: `http://localhost:8080/admin/login`
- Username: `admin`
- Password: `Admin@123`

Change the demo password and use persistent admin account storage before public deployment. This starter uses an in-memory demo admin account.

## Routes
- `/` home
- `/about`
- `/services`
- `/products`
- `/products/{id}`
- `/booking`
- `/contact`
- `/admin/login`
- `/admin` dashboard
- `/admin/products`
- `/admin/bookings`
- `/admin/messages`

## Included
- Responsive blue/cyan professional website
- Product catalogue and product details
- PostgreSQL/JPA product, booking and contact-message storage
- Booking form with validation
- Contact form with validation
- Spring Security admin login
- Admin product create/edit/delete
- Admin booking status updates
- Admin contact-message list

## Before publishing
Replace placeholder company contact details and demo product content. Configure HTTPS, secure secrets, backups, rate limiting and persistent admin accounts. Review your business/legal requirements before collecting customer data.
