plugins {
    java
    id("org.springframework.boot") version "3.3.1"
    id("io.spring.dependency-management") version "1.1.5"
}

group = "com.example"
version = "0.0.1-SNAPSHOT"

java {
    toolchain {
        languageVersion = JavaLanguageVersion.of(17)
    }
}

repositories {
    mavenCentral()
}

dependencies {

    implementation("org.springframework.boot:spring-boot-starter-data-jpa")
    implementation("org.springframework.boot:spring-boot-starter-security")
    implementation("org.springframework.boot:spring-boot-starter-validation")
    implementation("org.springframework.boot:spring-boot-starter-web")
    implementation("org.springframework.boot:spring-boot-starter-mail")

    implementation("org.mapstruct:mapstruct:1.5.3.Final")
    annotationProcessor("org.mapstruct:mapstruct-processor:1.5.3.Final")

    runtimeOnly("org.postgresql:postgresql")
    implementation("org.projectlombok:lombok")

    implementation("org.apache.poi:poi-ooxml:5.2.5")
    implementation("org.apache.poi:poi:5.2.5")
    implementation("org.apache.pdfbox:pdfbox:2.0.13")
    implementation("org.xhtmlrenderer:flying-saucer-pdf:9.1.22")
    implementation("com.deepoove:poi-tl:1.12.2")

    implementation("org.springdoc:springdoc-openapi-starter-webmvc-ui:2.0.4")

    testImplementation("org.springframework.boot:spring-boot-starter-test")
    testImplementation("org.springframework.security:spring-security-test")

    compileOnly("org.projectlombok:lombok")

    implementation("io.jsonwebtoken:jjwt:0.12.5")
    implementation("io.jsonwebtoken:jjwt-api:0.11.5")
    runtimeOnly("io.jsonwebtoken:jjwt-impl:0.11.5")
    runtimeOnly("io.jsonwebtoken:jjwt-jackson:0.11.5")

    implementation("javax.xml.bind:jaxb-api:2.3.1")

    implementation("com.google.zxing:core:3.5.2")

    implementation("org.thymeleaf:thymeleaf:3.1.2.RELEASE")
    implementation("org.thymeleaf:thymeleaf-spring5:3.1.2.RELEASE")
}

tasks.withType<Test> {
    useJUnitPlatform()
}
