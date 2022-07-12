/*
 * Copyright 2022 Netflix, Inc.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *   http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.amazon.aws.spinnaker.plugin.lambda.traffic;

import com.netflix.spinnaker.orca.StageResolver;
import com.netflix.spinnaker.orca.api.pipeline.graph.StageDefinitionBuilder;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.MockitoAnnotations;
import javax.annotation.Resource;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class LambdaTrafficRoutingIntegrationTest {
    private StageResolver stageResolver;

    @Resource
    @InjectMocks
    private LambdaTrafficRoutingStage lambdaTrafficRoutingStage;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);
        stageResolver = mock(StageResolver.class);

        when(stageResolver.getStageDefinitionBuilder(LambdaTrafficRoutingStage.class.getSimpleName(),
                "Aws.LambdaTrafficRoutingStage")).thenReturn(lambdaTrafficRoutingStage);
    }

    @Test
    public void resolveToCorrectTypeTest() {
        StageDefinitionBuilder stageDefinitionBuilder = stageResolver.getStageDefinitionBuilder(
            LambdaTrafficRoutingStage.class.getSimpleName(), "Aws.LambdaTrafficRoutingStage");

        assertTrue(stageDefinitionBuilder.aliases().contains("Aws.LambdaTrafficRoutingStage"), "Expected stageDefinitionBuilder to contain Aws.LambdaTrafficRoutingStage");
        assertEquals(stageDefinitionBuilder.getType(), "lambdaTrafficRouting" , "Expected stageDefinitionBuilder to be of type lambdaTrafficRouting");
    }

    @Test
    public void LambdaTrafficRoutingStageIntegrationTest() {
    }
}
