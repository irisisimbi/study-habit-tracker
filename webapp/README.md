# Personal Study Habit Tracker — JSF + Hibernate Practical (Assignment #3, Phase 1)

Student: Isimbi Mushimire Iris (ID: 27121) — AUCA, Software Engineering

## What this implements

Full CRUD (Create, Read, Update, Delete) for **two entities**, built with **JSF (Mojarra)** for
the presentation/controller layer and **Hibernate** for persistence, backed by an embedded
**H2** database (so it runs with zero external database setup):

1. **Subject** (`subject-list.xhtml`, `subject-form.xhtml`, `SubjectBean`, `SubjectDAO`)
2. **StudySession** (`session-list.xhtml`, `session-form.xhtml`, `StudySessionBean`, `StudySessionDAO`)

A supporting `Student` entity is also mapped (see the class diagram in the documentation) but is
not one of the two CRUD-exposed entities for this practical.

## Three validation types (per the assignment requirement)

Demonstrated together on the `Subject` name field and the `StudySession` duration field:

1. **Standard JSF validators** — `<f:validateLength>`, `<f:validateDoubleRange>`, `required="true"`.
2. **Custom JSF validators** — `SubjectNameValidator` (regex/format check) and
   `StudySessionDurationValidator` (business-rule check: single sessions over 8 hours are flagged),
   registered in `faces-config.xml` and attached via `<f:validator validatorId="..."/>`.
3. **Bean Validation (JSR-303/380) via Hibernate Validator** — `@NotBlank`, `@Size`, `@NotNull`,
   `@DecimalMin`, `@DecimalMax`, `@Email` annotations directly on the JPA entities
   (`Subject.java`, `StudySession.java`, `Student.java`). JSF automatically runs these on submit.

A small inline JavaScript check (`checkDuration()` in `session-form.xhtml`) is included as an
additional client-side sanity check layered on top of the three server-side validation types.

## Three CSS types (per the assignment requirement)

1. **External CSS** — `src/main/webapp/resources/css/styles.css`, loaded via
   `<h:outputStylesheet library="css" name="styles.css"/>` in `WEB-INF/template.xhtml`.
2. **Internal CSS** — a `<style>` block inside `<h:head>` in `WEB-INF/template.xhtml`.
3. **Inline CSS** — `style="..."` attributes used directly on elements, e.g. in
   `index.xhtml`, `subject-list.xhtml`, `session-form.xhtml`, and the footer in `template.xhtml`.

## Tech stack

- Java 11, Maven (WAR packaging)
- JSF 2.3 (Mojarra) with Facelets templating
- Hibernate ORM 5.6 (JPA annotations)
- Hibernate Validator 6.2 (JSR-380 Bean Validation)
- H2 embedded database (file-based, auto-created — no manual DB setup needed)
- Deployable to any Servlet 4.0 container (Tomcat 9/10, etc.)

## How to build and run

1. **Prerequisites:** JDK 11+, Maven 3.6+, Apache Tomcat 9.x.
2. Build the WAR:
   ```
   cd webapp
   mvn clean package
   ```
   This produces `target/study-habit-tracker.war`.
3. Deploy the WAR to Tomcat's `webapps/` folder (or use the Tomcat Manager app), then start Tomcat.
4. Open `http://localhost:8080/study-habit-tracker/` in a browser.
5. The H2 database file is created automatically under `data/` the first time the app starts —
   no manual schema creation is required (`hibernate.hbm2ddl.auto=update`).
6. Create a Subject first (e.g. "Mathematics"), then log Study Sessions against it, then view the
   "Progress Summary" (total hours per subject) on the Study Sessions page.

## Project structure

```
webapp/
├── pom.xml
├── src/main/java/com/auca/studytracker/
│   ├── model/        Student.java, Subject.java, StudySession.java  (JPA + Bean Validation)
│   ├── dao/          SubjectDAO.java, StudySessionDAO.java          (Hibernate CRUD)
│   ├── bean/         SubjectBean.java, StudySessionBean.java        (JSF managed beans)
│   ├── validator/    SubjectNameValidator.java, StudySessionDurationValidator.java,
│   │                 SubjectConverter.java
│   └── util/         HibernateUtil.java
├── src/main/resources/
│   ├── hibernate.cfg.xml
│   └── messages.properties
└── src/main/webapp/
    ├── index.xhtml, subject-list.xhtml, subject-form.xhtml,
    │   session-list.xhtml, session-form.xhtml
    ├── WEB-INF/ (web.xml, faces-config.xml, template.xhtml)
    └── resources/css/styles.css
```

## GitHub & video

- GitHub repository link: **[https://github.com/irisisimbi/study-habit-tracker.git]**
- Project proposal / workflow video (Google Vid, screen + camera): **[]**



