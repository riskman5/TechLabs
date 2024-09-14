plugins {
    id("java")
}

group = "ru.babenko"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
}

dependencies {
    implementation(project(":ownersMicroservice:ownersApp:controller"))
    implementation(project(":ownersMicroservice:ownersApp:service"))
    implementation(project(":ownersMicroservice:ownersApp:dal"))
    implementation(project(":ownersMicroservice:ownersApp:kafka"))
    implementation("org.springframework.boot:spring-boot-starter-web:3.2.4")
    implementation("org.springframework.kafka:spring-kafka:3.1.4")
    testImplementation(platform("org.junit:junit-bom:5.10.0"))
    testImplementation("org.junit.jupiter:junit-jupiter")
}

tasks.test {
    useJUnitPlatform()
}