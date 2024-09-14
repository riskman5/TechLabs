plugins {
    id("java")
}

group = "ru.babenko"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
}

dependencies {
    implementation(project(":catsMicroservice:catsApp:controller"))
    implementation(project(":catsMicroservice:catsApp:dal"))
    implementation(project(":catsMicroservice:catsApp:service"))
    implementation(project(":catsMicroservice:catsApp:kafka"))
    implementation("org.springframework.boot:spring-boot-starter-web:3.2.4")
    implementation("org.springframework.kafka:spring-kafka:3.1.4")
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