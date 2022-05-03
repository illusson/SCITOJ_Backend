import org.gradle.internal.os.OperatingSystem

/** 打包后自动定位打包文件 */
tasks.named("bootJar") {
    val path = File(projectDir, "build/libs/asoj-springboot.jar")
    doFirst {
        // 删除之前构建的 jar
        if (path.exists()) path.delete()
    }
    doLast {
        if (!path.exists()) return@doLast
        println("jar build successfully: $path")
        when (true) {
            OperatingSystem.current().isWindows ->
                Runtime.getRuntime().exec("explorer.exe /select, $path")
        }
    }
}