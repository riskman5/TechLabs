plugins {
    id("java")
}

group = "ru.babenko"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
}

dependencies {
    implementation(project(":apiGateway:dal"))
    implementation(project(":apiGateway:security"))
    implementation(project(":apiGateway:kafka"))
    implementation(project(":apiGateway:kafkaMessages"))
    implementation(project(":ownersMicroservice:ownersClient"))
    implementation(project(":catsMicroservice:catsClient"))
    implementation(project(":catsMicroservice:catsModel"))
    implementation(project(":ownersMicroservice:ownersModel"))
    implementation("org.springframework.kafka:spring-kafka:3.1.4")
    compileOnly("org.projectlombok:lombok:1.18.32")
    annotationProcessor("org.projectlombok:lombok:1.18.32")
    implementation("org.springframework.boot:spring-boot-starter-web:3.2.4")
    implementation("org.springframework.boot:spring-boot-starter-data-jpa:3.2.4")
    implementation("org.springframework.boot:spring-boot-starter-security:3.2.4")
    implementation("org.springframework.boot:spring-boot-starter-validation:3.2.4")
    testImplementation(platform("org.junit:junit-bom:5.10.0"))
    testImplementation("org.junit.jupiter:junit-jupiter")
}

tasks.test {
    useJUnitPlatform()
}