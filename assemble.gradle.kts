import java.io.BufferedReader
import java.io.InputStreamReader
import org.gradle.internal.os.OperatingSystem
import java.util.ArrayList
import java.util.Arrays

fun setupCommand(vararg command: String): Array<String> {
    return when (true) {
        OperatingSystem.current().isWindows -> arrayListOf("cmd", "/c")
        OperatingSystem.current().isLinux -> arrayListOf("shell", "-c")
        else -> throw GradleException("unsupported system type, please run command \"npm run build\" manually.")
    }.also {
        it.addAll(command)
    }.toTypedArray()
}

/** 打包 asoj-react */
tasks.register("assembleAsojReact") {
    doFirst {
        val asoj_react = File(rootDir, "asoj-react")
        val build = Runtime.getRuntime()
            .exec(setupCommand("npm", "run", "build"),
                arrayOf("CGO_ENABLED=0"), asoj_react)
        val reader = BufferedReader(InputStreamReader(build.inputStream))
        var line: String? = "asoj-react start building!"
        while (line != null) {
            println(line)
            line = reader.readLine()
        }
        if (build.waitFor() != 0) {
            throw GradleException("asoj-react may failed to build, please check it and try again!")
        }
    }
}

/** 将 asoj-react 打包文件复制到 asoj-sprintboot 的 res 文件夹 */
tasks.register("packageAsojReact") {
    doFirst {
        val asoj_react = File(rootDir, "asoj-react")
        val asoj_react_build = File(asoj_react, "build")
        if (!asoj_react_build.exists() || !asoj_react_build.isDirectory) {
            throw GradleException("failed to check asoj-react build dir, please make sure that asoj-react is successfully to build!")
        }
        val asoj_springboot = File(rootDir, "asoj-springboot")
        val asoj_springboot_main_res = File(asoj_springboot, "src/main/resources")
        val asmr_static = asoj_springboot_main_res.setupDir("static")
        val asmr_public = asoj_springboot_main_res.setupDir("public")
        if (!File(asoj_react_build, "static").copiesTo(asmr_static, false, arrayOf("map"))
            || !asoj_react_build.copiesTo(asmr_public, true)) {
            asmr_static.delete()
            asmr_public.delete()
            throw GradleException("failed to copy asoj-react build dir to asoj-springboot resource dir!")
        }
    }
}

/** 打包 asoj-springboot */
tasks.register("assembleAsojSpringBoot") {
    if (!project.hasProperty("skipAssembleReact")) {
        // 跳过打包 asoj-react
        dependsOn("assembleAsojReact")
    }
    dependsOn("packageAsojReact", "asoj-springboot:bootJar")
}

/**  */
tasks.register("assembleAsojJudger") {
    val GOOS: String by project
    val GOARCH: String by project
    doFirst {
        val asoj_judger = File(rootDir, "asoj-judger")
        val envp: Array<String> = HashSet<String>().also {
            it.add("CGO_ENABLED=0")
            if (project.hasProperty("GOOS")) {
                it.add("GOOS=$GOOS")
            }
            if (project.hasProperty("GOARCH")) {
                it.add("GOARCH=$GOARCH")
            }
        }.toTypedArray()
        val build = Runtime.getRuntime()
            .exec(setupCommand("go"), envp, asoj_judger)
        val reader = BufferedReader(InputStreamReader(build.inputStream))
        var line: String? = "asoj-judger start building!"
        while (line != null) {
            println(line)
            line = reader.readLine()
        }
        if (build.waitFor() != 0) {
            throw GradleException("asoj-judger may failed to build, please check it and try again!")
        }
    }
}

/**
 * 将此文件/文件夹递归复制
 * @param targetDir 目标目录
 * @param ignoreDir 是否忽略当前当前文件夹中的文件夹
 * @param ignoreTypes 忽略当前文件夹中文件类型
 */
fun File.copiesTo(targetDir: File, ignoreDir: Boolean,
                ignoreTypes: Array<String> = arrayOf()): Boolean {
    copy@for (file: File in (this@copiesTo.listFiles() ?: arrayOf())) {
        if (ignoreDir && file.isDirectory) {
            continue
        }
        if (file.isDirectory) {
            file.copiesTo(targetDir.setupDir(file.name), ignoreDir, ignoreTypes)
            continue
        }
        for (type in ignoreTypes) {
            if (file.name.endsWith(".$type")) {
//                println("$file not copied.")
                continue@copy
            }
        }
        val target = File(targetDir, file.name)
        file.copyTo(target, true)
//        println("$target copied.")
    }
    return true
}

/**
 * 初始化一个子目录，若存在则删除其中所有内容重建
 * @param child 目标子目录名称
 */
fun File.setupDir(child: String): File {
    return File(this@setupDir, child).also {
        if (it.exists()) it.deletes()
        it.mkdirs()
    }
}

/** 递归删除此文件或此文件夹下的所有内容 */
fun File.deletes() {
    for (file: File in (this@deletes.listFiles() ?: arrayOf())) {
        if (file.isDirectory) {
            file.deletes()
        } else {
            file.delete()
//            println("$file deleted.")
        }
    }
    this@deletes.delete()
//    println("${this@deletes} deleted.")
}