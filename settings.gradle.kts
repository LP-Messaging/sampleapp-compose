pluginManagement {
    repositories {
        google()
        mavenCentral()
        gradlePluginPortal()
    }
}
dependencyResolutionManagement {
    repositoriesMode.set(RepositoriesMode.FAIL_ON_PROJECT_REPOS)
    repositories {
        google()
        mavenCentral()
        mavenLocal()
        flatDir {

            dirs(File("../aars"))
        }
    }
}

rootProject.name = "SampleApp"
include(":common")
include(":common-ui")
include(":external-auth")
include(":sample")
