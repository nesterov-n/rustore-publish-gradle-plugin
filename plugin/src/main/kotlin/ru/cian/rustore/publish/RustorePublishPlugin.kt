package ru.cian.rustore.publish

import com.android.build.api.variant.ApplicationAndroidComponentsExtension
import com.android.build.api.variant.ApplicationVariant
import com.android.build.api.variant.VariantSelector
import com.android.build.gradle.AppPlugin
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.api.tasks.TaskProvider
import org.gradle.kotlin.dsl.create
import org.gradle.kotlin.dsl.getByType
import org.gradle.kotlin.dsl.register
import org.gradle.kotlin.dsl.withType

class RustorePublishPlugin : Plugin<Project> {

    override fun apply(project: Project) {
        project.plugins.withType<AppPlugin> {
            configureRustorePublish(project)
        }
    }

    private fun configureRustorePublish(project: Project) {
        val rustorePublishExtension = project.extensions.create<RustorePublishExtension>(
            RustorePublishExtension.MAIN_EXTENSION_NAME,
            project
        )

        val androidComponents = project.extensions.getByType<ApplicationAndroidComponentsExtension>()
        androidComponents.onVariants(androidComponents.selector().all() as VariantSelector) { variant ->
            createTask(project, variant)
        }
    }

    @Suppress("DefaultLocale")
    private fun createTask(
        project: Project,
        variant: ApplicationVariant
    ) {
        val variantName = variant.name.capitalize()
        val publishTaskName = "${RustorePublishTask.TASK_NAME}$variantName"
        project.tasks.register<RustorePublishTask>(publishTaskName, variant).configure {
            setMustRunAfter(
                setOf(
                    project.tasks.named("assemble$variantName"),
                    project.tasks.named("bundle$variantName"),
                )
            )
        }

    }
}
