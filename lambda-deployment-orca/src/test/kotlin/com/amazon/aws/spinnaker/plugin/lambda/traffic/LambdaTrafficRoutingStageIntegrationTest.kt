package com.amazon.aws.spinnaker.plugin.lambda.traffic

import com.amazon.aws.spinnaker.plugin.lambda.traffic.model.LambdaBlueGreenStrategyInput
import com.amazon.aws.spinnaker.plugin.lambda.traffic.model.LambdaTrafficUpdateInput
import com.amazon.aws.spinnaker.plugin.lambda.upsert.model.LambdaConcurrencyInput
import com.amazon.aws.spinnaker.plugin.lambda.utils.LambdaDefinition
import com.fasterxml.jackson.module.kotlin.readValue
import com.netflix.spinnaker.orca.api.test.orcaFixture
import dev.minutest.junit.JUnit5Minutests
import org.springframework.http.MediaType
import dev.minutest.rootContext
import org.springframework.test.web.servlet.get
import org.springframework.test.web.servlet.post
import strikt.api.expect
import strikt.assertions.isEqualTo

class LambdaTrafficRoutingStageIntegrationTest: JUnit5Minutests {

    fun tests() = rootContext<OrcaPluginsFixture> {
        context("a running Orca instance") {
            this.orcaFixture {
                OrcaPluginsFixture()
            }

            test("LambdaTrafficRoutingStage extension is resolved to the correct type") {
                val stageDefinitionBuilder = stageResolver.getStageDefinitionBuilder(
                    LambdaTrafficRoutingStage::class.java.name, "Aws.LambdaTrafficRoutingStage")

                expect {
                    that(stageDefinitionBuilder.type).isEqualTo("Aws.LambdaTrafficRoutingStage")
                }
            }

//            test("LambdaTrafficRoutingStage can be executed as a stage within a live pipeline execution") {
//                val response = mockMvc.post("/orchestrate") {
//                    contentType = MediaType.APPLICATION_JSON
//                    content = mapper.writeValueAsString(mapOf(
//                        "application" to "lambda",
//                        "stages" to listOf(mapOf(
//                            "refId" to "1",
//                            "type" to "Aws.LambdaTrafficRoutingStage",
//                            "functionName" to "lambda-myLambda",
//                            "region" to "us-west-2",
//                            "deploymentStrategy" to "\$BLUEGREEN",
//                            "payload" to "payload",
//                            "timeout" to 30,
//                            "provisionedConcurrentExecutions" to 12
//                        ))
//                    ))
//                }.andReturn().response
//
//                expect {
//                    that(response.status).isEqualTo(200)
//                }
//
//                val ref = mapper.readValue<ExecutionRef>(response.contentAsString).ref
//
//                var execution: Execution
//                do {
//                    execution = mapper.readValue(mockMvc.get(ref).andReturn().response.contentAsString)
//                } while (execution.status != "SUCCEEDED")
//
//                expect {
//                    that(execution)
//                        .get { stages.first() }
//                        .and {
//                            get { type }.isEqualTo("Aws.LambdaTrafficRoutingStage")
//                            get { status }.isEqualTo("SUCCEEDED")
//                            get { context.functionName }.isEqualTo("lambda-myLambda")
//                            get { context.region }.isEqualTo("us-west-2")
//                            get { context.deploymentStrategy }.isEqualTo("\$BLUEGREEN")
//                            get { context.payload }.isEqualTo("payload")
//                            get { context.timeout }.isEqualTo(30)
//                            get { concurrency.provisionedConcurrentExecutions }.isEqualTo(12)
//                        }
//                }
//            }
        }
    }

    data class ExecutionRef(val ref: String)
    data class Execution(val status: String, val stages: List<Stage>)
    data class Stage(val status: String, val context: LambdaTrafficUpdateInput, val concurrency:LambdaConcurrencyInput, val type: String)
}