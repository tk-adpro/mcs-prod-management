plugins {
    java
    id("org.springframework.boot") version "3.2.4"
    id("io.spring.dependency-management") version "1.1.4"
    jacoco
    id("org.sonarqube") version "4.4.1.3373"
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
    implementation("org.springframework.boot:spring-boot-starter-actuator")
    implementation("io.micrometer:micrometer-registry-prometheus")

    implementation("org.springframework.boot:spring-boot-starter-data-jpa")
    runtimeOnly("com.h2database:h2")
    runtimeOnly("org.postgresql:postgresql")


    testImplementation("org.springframework.boot:spring-boot-starter-test")
    testImplementation("org.springframework.security:spring-security-test")

    testImplementation("org.junit.jupiter:junit-jupiter-api:$junitJupiterVersion")
    testRuntimeOnly("org.junit.jupiter:junit-jupiter-engine:$junitJupiterVersion")

    implementation("org.springframework.boot:spring-boot-starter-security")
    implementation("org.springframework.boot:spring-boot-starter-webflux")
}

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
        html.required = true
        xml.required = true
        html.outputLocation.set(
            layout.buildDirectory.dir("jacocoHtml")
        )
    }
}
sonar {
    properties {
        property("sonar.projectKey", "tk-adpro_mcs-prod-management")
        property("sonar.organization", "tk-adpro-1")
        property("sonar.host.url", "https://sonarcloud.io")
        property("sonar.junit.reportPaths", "${project.buildDir}/test-results/test")
    }
}