plugins {
    base
}

tasks.named("build") {
    dependsOn(":backend:build")
    dependsOn(":frontend:runBuild")
    dependsOn(":frontend:copyWebApp")
}
