package com.amazon.aws.spinnaker.plugin.lambda.traffic

import com.amazon.aws.spinnaker.plugin.lambda.LambdaSpringLoaderPlugin
import com.amazon.aws.spinnaker.plugin.lambda.utils.LambdaCloudDriverUtils
import com.fasterxml.jackson.databind.DeserializationFeature
import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import com.netflix.spinnaker.kork.plugins.internal.PluginJar
import com.netflix.spinnaker.orca.StageResolver
import com.netflix.spinnaker.orca.api.test.OrcaFixture
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.test.context.TestPropertySource
import org.springframework.test.web.servlet.MockMvc
import java.io.File

@TestPropertySource(properties = [
    "spinnaker.extensibility.plugins.Aws.LambdaDeployment.Plugin.enabled=true",
    "spinnaker.extensibility.plugins-root-path=build/plugins"
])
@AutoConfigureMockMvc
 class OrcaPluginsFixture : OrcaFixture() {
    @Autowired
    lateinit var stageResolver: StageResolver

    @Autowired
    lateinit var mockMvc: MockMvc

    val mapper = jacksonObjectMapper().disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES)

    init {
        val pluginId = "Aws.LambdaDeploymentPlugin"
        val plugins = File("build/plugins").also {
            it.delete()
            it.mkdir()
        }

        PluginJar.Builder(plugins.toPath().resolve("$pluginId.jar"), pluginId)
            .pluginClass(LambdaSpringLoaderPlugin::class.java.name)
            .pluginVersion("1.0.0")
            .build()
    }
}