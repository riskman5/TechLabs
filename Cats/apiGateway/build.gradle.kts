plugins {
    id("java")
}

group = "ru.babenko"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
}

dependencies {
    implementation(project(":apiGateway:kafka"))
    implementation(project(":apiGateway:dal"))
    implementation(project(":apiGateway:service"))
    implementation(project(":apiGateway:controller"))
    implementation(project(":apiGateway:security"))
    implementation("org.springframework.boot:spring-boot-starter-web:3.2.4")
    implementation("org.springframework.boot:spring-boot-starter-security:3.2.4")
    implementation("org.springframework.kafka:spring-kafka:3.1.4")
    testImplementation(project(":apiGateway:kafka"))
    testImplementation(project(":apiGateway:dal"))
    testImplementation(project(":catsMicroservice:catsClient"))
    testImplementation(project(":catsMicroservice:catsModel"))
    testImplementation(project(":ownersMicroservice:ownersClient"))
    testImplementation(project(":ownersMicroservice:ownersModel"))
    testImplementation("com.h2database:h2:2.2.224")
    testImplementation("org.mockito:mockito-junit-jupiter:4.0.0")
    testImplementation("org.springframework.security:spring-security-test:6.2.4")
    testImplementation("io.jsonwebtoken:jjwt:0.12.5")
    testImplementation(platform("org.junit:junit-bom:5.10.0"))
    testImplementation("org.junit.jupiter:junit-jupiter")
    testImplementation("org.springframework.boot:spring-boot-starter-test:3.2.4")
    testImplementation("org.mockito:mockito-junit-jupiter:4.0.0")
    testImplementation(platform("org.junit:junit-bom:5.10.0"))
    testImplementation("org.junit.jupiter:junit-jupiter")
    testCompileOnly("org.projectlombok:lombok:1.18.32")
    testAnnotationProcessor("org.projectlombok:lombok:1.18.32")
}

tasks.test {
    useJUnitPlatform()
}