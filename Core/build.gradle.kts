plugins {
    id("org.springframework.boot")
    id("io.spring.dependency-management")
    kotlin("jvm")
    kotlin("plugin.spring")
    kotlin("plugin.jpa")
}

java.sourceCompatibility = JavaVersion.VERSION_1_8
java.targetCompatibility = JavaVersion.VERSION_1_8

configurations {
    compileOnly {
        extendsFrom(configurations.annotationProcessor.get())
    }
}

dependencies {
    implementation("org.springframework.boot:spring-boot-starter-data-jpa")
    implementation("org.springframework.boot:spring-boot-starter-web")
    implementation("org.springframework.boot:spring-boot-starter-security")
    developmentOnly("org.springframework.boot:spring-boot-devtools")
    annotationProcessor("org.springframework.boot:spring-boot-configuration-processor")
    testImplementation("org.springframework.boot:spring-boot-starter-test")

    implementation("com.fasterxml.jackson.module:jackson-module-kotlin:2.13.3")
    implementation("org.jetbrains.kotlin:kotlin-stdlib-jdk8")
    implementation("org.jetbrains.kotlin:kotlin-reflect")
    runtimeOnly("org.mariadb.jdbc:mariadb-java-client:3.0.4")

    // okhttp 用于网络访问
    implementation("com.squareup.okhttp3:okhttp:5.0.0-alpha.6")
    // json 解析
    implementation("com.google.code.gson:gson:2.9.0")
//    implementation("org.json:json:20220320")
    // html 解析
    implementation("org.jsoup:jsoup:1.14.3")

    // jwt
    val jwt = "0.11.5"
    implementation("io.jsonwebtoken:jjwt-api:$jwt")
    runtimeOnly("io.jsonwebtoken:jjwt-impl:$jwt")
    runtimeOnly("io.jsonwebtoken:jjwt-gson:$jwt")

//    val swagger2 = "3.0.0"
//    implementation("io.springfox:springfox-swagger-ui:$swagger2")
//    implementation("io.springfox:springfox-swagger2:$swagger2")

    val springdoc = "1.6.8"
    implementation("org.springdoc:springdoc-openapi-ui:$springdoc")
    implementation("org.springdoc:springdoc-openapi-security:$springdoc")
    implementation("org.springdoc:springdoc-openapi-kotlin:$springdoc")
}

/** 打包后自动定位打包文件 */
tasks.named("bootJar") {
    val path = File(projectDir, "build/libs/${project.name}.jar")
    val build = File(path.parentFile, "${rootProject.name}.jar")
    doFirst {
        // 删除之前构建的 jar
        if (path.exists()) path.delete()
        if (build.exists()) build.delete()
    }
    doLast {
        path.copyTo(build, true, 2048)
        if (build.exists()) path.delete()
    }
}
