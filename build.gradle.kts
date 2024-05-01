plugins {
    java
    id("org.springframework.boot") version "3.2.4"
    id("io.spring.dependency-management") version "1.1.4"
    jacoco
}

group = "id.ac.ui.cs.advprog.eshop"
version = "0.0.1-SNAPSHOT"
java.sourceCompatibility = JavaVersion.VERSION_17
java {
    sourceCompatibility = JavaVersion.VERSION_21
}

configurations {
    compileOnly {
        extendsFrom(configurations.annotationProcessor.get())
    }
}

repositories {
    mavenCentral()
}

val junitJupiterVersion = "5.9.1"

dependencies {
    implementation("org.springframework.boot:spring-boot-starter-thymeleaf")
    implementation("org.springframework.boot:spring-boot-starter-web")
    compileOnly("org.projectlombok:lombok")
    developmentOnly("org.springframework.boot:spring-boot-devtools")
    annotationProcessor("org.springframework.boot:spring-boot-configuration-processor")
    annotationProcessor("org.projectlombok:lombok")


    implementation("org.springframework.boot:spring-boot-starter-data-jpa")
    runtimeOnly("com.h2database:h2")
    runtimeOnly("org.postgresql:postgresql")


    testImplementation("org.springframework.boot:spring-boot-starter-test")
    testImplementation("org.junit.jupiter:junit-jupiter-api:$junitJupiterVersion")
    testRuntimeOnly("org.junit.jupiter:junit-jupiter-engine:$junitJupiterVersion")

    implementation("org.springframework.boot:spring-boot-starter-security")
    testImplementation("org.springframework.security:spring-security-test")
    implementation("org.springframework.boot:spring-boot-starter-security")
}

// dependencies {
//     implementation("org.springframework.boot:spring-boot-starter-data-jdbc")
//     implementation("org.springframework.boot:spring-boot-starter-data-jpa")
//     implementation("org.springframework.boot:spring-boot-starter-jdbc")
//     implementation("org.springframework.boot:spring-boot-starter-oauth2-authorization-server")
//     implementation("org.springframework.boot:spring-boot-starter-oauth2-client")
//     implementation("org.springframework.boot:spring-boot-starter-oauth2-resource-server")
// //    implementation("org.springframework.boot:spring-boot-starter-security")
//     implementation("org.springframework.boot:spring-boot-starter-thymeleaf")
//     implementation("org.springframework.boot:spring-boot-starter-web")
// //    implementation("org.thymeleaf.extras:thymeleaf-extras-springsecurity6")
//     compileOnly("org.projectlombok:lombok")
//     developmentOnly("org.springframework.boot:spring-boot-devtools")
//     annotationProcessor("org.springframework.boot:spring-boot-configuration-processor")
//     annotationProcessor("org.projectlombok:lombok")
//     testImplementation("org.springframework.boot:spring-boot-starter-test")
// //    testImplementation("org.springframework.security:spring-security-test")
//     runtimeOnly("org.postgresql:postgresql")
// }

tasks.withType<Test> {
    useJUnitPlatform()
}
tasks.test {
    useJUnitPlatform()
    finalizedBy(tasks.jacocoTestReport) // report is always generated after tests run
}
tasks.jacocoTestReport {
    classDirectories.setFrom(
        files(
            classDirectories.files.map {
                fileTree(it) { 
                    exclude("**/*Application**") 
                    }
            }
        )
    )

    dependsOn(tasks.test) // tests are required to run before generating the report
    
    reports {
        xml.required.set(false)
        csv.required.set(false)
        html.outputLocation.set(
            layout.buildDirectory.dir("jacocoHtml")
        )
    }
}